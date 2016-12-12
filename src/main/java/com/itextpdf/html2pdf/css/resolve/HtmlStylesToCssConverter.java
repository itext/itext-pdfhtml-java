/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2017 iText Group NV
    Authors: iText Software.

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License version 3
    as published by the Free Software Foundation with the addition of the
    following permission added to Section 15 as permitted in Section 7(a):
    FOR ANY PART OF THE COVERED WORK IN WHICH THE COPYRIGHT IS OWNED BY
    ITEXT GROUP. ITEXT GROUP DISCLAIMS THE WARRANTY OF NON INFRINGEMENT
    OF THIRD PARTY RIGHTS

    This program is distributed in the hope that it will be useful, but
    WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
    or FITNESS FOR A PARTICULAR PURPOSE.
    See the GNU Affero General Public License for more details.
    You should have received a copy of the GNU Affero General Public License
    along with this program; if not, see http://www.gnu.org/licenses or write to
    the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
    Boston, MA, 02110-1301 USA, or download the license from the following URL:
    http://itextpdf.com/terms-of-use/

    The interactive user interfaces in modified source and object code versions
    of this program must display Appropriate Legal Notices, as required under
    Section 5 of the GNU Affero General Public License.

    In accordance with Section 7(b) of the GNU Affero General Public License,
    a covered work must retain the producer line in every PDF that is created
    or manipulated using iText.

    You can be released from the requirements of the license by purchasing
    a commercial license. Buying such a license is mandatory as soon as you
    develop commercial activities involving the iText software without
    disclosing the source code of your own applications.
    These activities include: offering paid services to customers as an ASP,
    serving PDFs on the fly in a web application, shipping iText with a closed
    source product.

    For more information, please contact iText Software Corp. at this
    address: sales@itextpdf.com
 */
package com.itextpdf.html2pdf.css.resolve;

import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.css.CssDeclaration;
import com.itextpdf.html2pdf.css.CssStyleSheet;
import com.itextpdf.html2pdf.css.media.MediaDeviceDescription;
import com.itextpdf.html2pdf.css.parse.CssStyleSheetParser;
import com.itextpdf.html2pdf.html.AttributeConstants;
import com.itextpdf.html2pdf.html.TagConstants;
import com.itextpdf.html2pdf.html.node.IAttribute;
import com.itextpdf.html2pdf.html.node.IElementNode;
import com.itextpdf.html2pdf.html.node.INode;
import com.itextpdf.io.util.ResourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

class HtmlStylesToCssConverter {

    private static final String DEFAULT_CSS_PATH = "com/itextpdf/html2pdf/default.css";
    private static final CssStyleSheet defaultCss;
    private static final Map<String, IAttributeConverter> htmlAttributeConverters;

    static {
        CssStyleSheet parsedStylesheet = new CssStyleSheet();
        try {
            parsedStylesheet = CssStyleSheetParser.parse(ResourceUtil.getResourceStream(DEFAULT_CSS_PATH));
        } catch (IOException exc) {
            Logger logger = LoggerFactory.getLogger(HtmlStylesToCssConverter.class);
            logger.error("Error parsing default.css", exc);
        } finally {
            defaultCss = parsedStylesheet;
        }

        htmlAttributeConverters = new HashMap<>();
        htmlAttributeConverters.put(AttributeConstants.ALIGN, new AlignAttributeConverter());
        htmlAttributeConverters.put(AttributeConstants.BORDER, new BorderAttributeConverter());
        htmlAttributeConverters.put(AttributeConstants.BGCOLOR, new BgColorAttributeConverter());
        htmlAttributeConverters.put(AttributeConstants.COLOR, new FontColorAttributeConverter());
        htmlAttributeConverters.put(AttributeConstants.DIR, new DirAttributeConverter());
        htmlAttributeConverters.put(AttributeConstants.SIZE, new SizeAttributeConverter());
        htmlAttributeConverters.put(AttributeConstants.FACE, new FontFaceAttributeConverter());
        htmlAttributeConverters.put(AttributeConstants.NOSHADE, new NoShadeAttributeConverter());
        htmlAttributeConverters.put(AttributeConstants.TYPE, new TypeAttributeConverter());
        htmlAttributeConverters.put(AttributeConstants.WIDTH, new WidthAttributeConverter());
        htmlAttributeConverters.put(AttributeConstants.HEIGHT, new HeightAttributeConverter());
    }

    public static List<CssDeclaration> convert(IElementNode element) {
        List<CssDeclaration> convertedHtmlStyles = new ArrayList<>();
        List<CssDeclaration> tagCssStyles = defaultCss.getCssDeclarations(element, MediaDeviceDescription.createDefault());
        if (tagCssStyles != null) {
            convertedHtmlStyles.addAll(tagCssStyles);
        }

        for (IAttribute a : element.getAttributes()) {
            IAttributeConverter aConverter = htmlAttributeConverters.get(a.getKey());
            if (aConverter != null && aConverter.isSupportedForElement(element.name())) {
                convertedHtmlStyles.addAll(aConverter.convert(element, a.getValue()));
            }
        }

        return convertedHtmlStyles;
    }


    private interface IAttributeConverter {
        boolean isSupportedForElement(String elementName);
        List<CssDeclaration> convert(IElementNode element, String value);
    }


    // TODO for table border, border attribute affects cell borders as well
    private static class BorderAttributeConverter implements IAttributeConverter {
        @Override
        public boolean isSupportedForElement(String elementName) {
            return TagConstants.IMG.equals(elementName) || TagConstants.TABLE.equals(elementName);
        }
        @Override
        public List<CssDeclaration> convert(IElementNode element, String value) {
            applyBordersToTableCells(element, value);
            return Arrays.asList(new CssDeclaration(CssConstants.BORDER, value + "px solid black"));
        }

        private static void applyBordersToTableCells(IElementNode element, String value) {
            List<INode> nodes = element.childNodes();
            for (INode node : nodes) {
                if (node instanceof IElementNode) {
                    String elementName = ((IElementNode)node).name();
                    if (elementName.equals(TagConstants.TD) || elementName.equals(TagConstants.TH)) {
                        String styleAttribute = ((IElementNode) node).getAttribute(AttributeConstants.STYLE);
                        if (styleAttribute == null) {
                            styleAttribute = "";
                        }
                        if (!styleAttribute.contains(CssConstants.BORDER)) {
                            ((IElementNode) node).getAttributes().setAttribute(AttributeConstants.STYLE, styleAttribute + "; " +
                                    new CssDeclaration(CssConstants.TABLE_CUSTOM_BORDER, value + "px solid black").toString());
                        }
                    } else {
                        applyBordersToTableCells((IElementNode) node, value);
                    }
                }
            }
        }
    }


    private static class BgColorAttributeConverter implements IAttributeConverter {
        private static Set<String> supportedTags = new HashSet<>(
                Arrays.asList(TagConstants.BODY, TagConstants.COL, TagConstants.COLGROUP, TagConstants.MARQUEE,
                        TagConstants.TABLE, TagConstants.TBODY, TagConstants.TFOOT, TagConstants.TD, TagConstants.TH,
                        TagConstants.TR));
        @Override
        public boolean isSupportedForElement(String elementName) {
            return supportedTags.contains(elementName);
        }
        @Override
        public List<CssDeclaration> convert(IElementNode element, String value) {
            return Arrays.asList(new CssDeclaration(CssConstants.BACKGROUND_COLOR, value));
        }
    }


    private static class FontColorAttributeConverter implements IAttributeConverter {
        @Override
        public boolean isSupportedForElement(String elementName) {
            return TagConstants.FONT.equals(elementName);
        }
        @Override
        public List<CssDeclaration> convert(IElementNode element, String value) {
            return Arrays.asList(new CssDeclaration(CssConstants.COLOR, value));
        }
    }

    private static class SizeAttributeConverter implements IAttributeConverter {
        @Override
        public boolean isSupportedForElement(String elementName) {
            return TagConstants.FONT.equals(elementName) || TagConstants.HR.equals(elementName);
        }
        @Override
        public List<CssDeclaration> convert(IElementNode element, String value) {
            String cssValueEquivalent = null;
            String cssPropertyEquivalent = null;
            String elementName = element.name();
            if (TagConstants.FONT.equals(elementName)) {
                cssPropertyEquivalent = CssConstants.FONT_SIZE;
                if("1".equals(value))                       cssValueEquivalent = CssConstants.XX_SMALL;
                else if("2".equals(value))                  cssValueEquivalent = CssConstants.X_SMALL;
                else if("3".equals(value))                  cssValueEquivalent = CssConstants.SMALL;
                else if("4".equals(value))                  cssValueEquivalent = CssConstants.MEDIUM;
                else if("5".equals(value))                  cssValueEquivalent = CssConstants.LARGE;
                else if("6".equals(value))                  cssValueEquivalent = CssConstants.X_LARGE;
                else if("7".equals(value))                  cssValueEquivalent = CssConstants.XX_LARGE;
            } else if (TagConstants.HR.equals(elementName)) {
                cssPropertyEquivalent = CssConstants.HEIGHT;
                cssValueEquivalent = value + CssConstants.PX;
            }
            return Arrays.asList(new CssDeclaration(cssPropertyEquivalent, cssValueEquivalent));
        }
    }

    private static class FontFaceAttributeConverter implements IAttributeConverter {
        @Override
        public boolean isSupportedForElement(String elementName) {
            return TagConstants.FONT.equals(elementName);
        }
        @Override
        public List<CssDeclaration> convert(IElementNode element, String value) {
            return Arrays.asList(new CssDeclaration(CssConstants.FONT_FAMILY, value));
        }
    }

    private static class TypeAttributeConverter implements IAttributeConverter {

        @Override
        public boolean isSupportedForElement(String elementName) {
            return TagConstants.OL.equals(elementName);
        }

        @Override
        public List<CssDeclaration> convert(IElementNode element, String value) {
            String cssEquivalent = null;
            switch (value) {
                case "1":
                    cssEquivalent = CssConstants.DECIMAL;
                    break;
                case "A":
                    cssEquivalent = CssConstants.UPPER_ALPHA;
                    break;
                case "a":
                    cssEquivalent = CssConstants.LOWER_ALPHA;
                    break;
                case "I":
                    cssEquivalent = CssConstants.UPPER_ROMAN;
                    break;
                case "i":
                    cssEquivalent = CssConstants.LOWER_ROMAN;
                    break;
            }
            return Arrays.asList(new CssDeclaration(CssConstants.LIST_STYLE_TYPE, cssEquivalent));
        }
    }

    private static class DirAttributeConverter implements IAttributeConverter {

        @Override
        public boolean isSupportedForElement(String elementName) {
            return true;
        }

        @Override
        public List<CssDeclaration> convert(IElementNode element, String value) {
            return Arrays.asList(new CssDeclaration(CssConstants.DIRECTION, value));
        }
    }

    private static class WidthAttributeConverter implements IAttributeConverter {

        @Override
        public boolean isSupportedForElement(String elementName) {
            return TagConstants.HR.equals(elementName) || TagConstants.IMG.equals(elementName);
        }

        @Override
        public List<CssDeclaration> convert(IElementNode element, String value) {
            String cssEquivalent = value;
            if (!value.endsWith("%")) cssEquivalent += CssConstants.PX;
            return Arrays.asList(new CssDeclaration(CssConstants.WIDTH, cssEquivalent));
        }
    }

    private static class HeightAttributeConverter implements IAttributeConverter {

        @Override
        public boolean isSupportedForElement(String elementName) {
            return TagConstants.IMG.equals(elementName);
        }

        @Override
        public List<CssDeclaration> convert(IElementNode element, String value) {
            return Arrays.asList(new CssDeclaration(CssConstants.HEIGHT, value + CssConstants.PX));
        }
    }

    private static class AlignAttributeConverter implements IAttributeConverter {
        @Override
        public boolean isSupportedForElement(String elementName) {
            return TagConstants.HR.equals(elementName);
        }

        @Override
        public List<CssDeclaration> convert(IElementNode element, String value) {
            List<CssDeclaration> result = new ArrayList<CssDeclaration>(2);
            if ("right".equals(value)) {
                result.add(new CssDeclaration(CssConstants.MARGIN_RIGHT, "0"));
            } else if ("left".equals(value)) {
                result.add(new CssDeclaration(CssConstants.MARGIN_LEFT, "0"));
            } else if ("center".equals(value)) {
                result.add(new CssDeclaration(CssConstants.MARGIN_RIGHT, CssConstants.AUTO));
                result.add(new CssDeclaration(CssConstants.MARGIN_LEFT, CssConstants.AUTO));
            }
            return result;
        }
    }

    private static class NoShadeAttributeConverter implements IAttributeConverter {

        @Override
        public boolean isSupportedForElement(String elementName) {
            return TagConstants.HR.equals(elementName);
        }

        @Override
        public List<CssDeclaration> convert(IElementNode element, String value) {
            return Arrays.asList(
                    new CssDeclaration(CssConstants.HEIGHT, "2px"),
                    new CssDeclaration(CssConstants.BORDER_WIDTH, "0"),
                    new CssDeclaration(CssConstants.BACKGROUND_COLOR, "gray")
            );
        }
    }
}
