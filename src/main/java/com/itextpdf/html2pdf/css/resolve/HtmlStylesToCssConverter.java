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
import com.itextpdf.html2pdf.css.resolve.shorthand.impl.BorderShorthandResolver;
import com.itextpdf.html2pdf.css.util.CssUtils;
import com.itextpdf.html2pdf.html.AttributeConstants;
import com.itextpdf.html2pdf.html.TagConstants;
import com.itextpdf.html2pdf.html.node.IAttribute;
import com.itextpdf.html2pdf.html.node.IElementNode;
import com.itextpdf.html2pdf.html.node.INode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

class HtmlStylesToCssConverter {

    private static final Map<String, IAttributeConverter> htmlAttributeConverters;

    static {
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
        htmlAttributeConverters.put(AttributeConstants.VALIGN, new VAlignAttributeConverter());
    }

    public static List<CssDeclaration> convert(IElementNode element) {
        ArrayList<CssDeclaration> convertedHtmlStyles = new ArrayList<>();
        if (element.getAdditionalHtmlStyles() != null) {
            HashMap<String, String> additionalStyles = new HashMap<>();
            for(Map<String, String> styles : element.getAdditionalHtmlStyles()) {
                additionalStyles.putAll(styles);
            }
            convertedHtmlStyles.ensureCapacity(convertedHtmlStyles.size() + additionalStyles.size());
            for (Map.Entry<String, String> entry : additionalStyles.entrySet()) {
                convertedHtmlStyles.add(new CssDeclaration(entry.getKey(), entry.getValue()));
            }
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


    private static class BorderAttributeConverter implements IAttributeConverter {

        private static void applyBordersToTableCells(INode node, Map<String, String> borderStyles) {
            List<INode> nodes = node.childNodes();
            for (INode childNode : nodes) {
                if (childNode instanceof IElementNode) {
                    IElementNode elementNode = (IElementNode) childNode;
                    if (TagConstants.TD.equals(elementNode.name()) || TagConstants.TH.equals(elementNode.name())) {
                        elementNode.addAdditionalHtmlStyles(borderStyles);
                    } else {
                        applyBordersToTableCells(childNode, borderStyles);
                    }
                }
            }
        }

        @Override
        public boolean isSupportedForElement(String elementName) {
            return TagConstants.IMG.equals(elementName) || TagConstants.TABLE.equals(elementName);
        }

        @Override
        public List<CssDeclaration> convert(IElementNode element, String value) {
            Float width = CssUtils.parseFloat(value);
            if (width != null) {
                if (TagConstants.TABLE.equals(element.name()) && width != 0) {
                    List<CssDeclaration> declarations = new BorderShorthandResolver().resolveShorthand("1px solid");
                    Map<String, String> styles = new HashMap<>(declarations.size());
                    for (CssDeclaration declaration : declarations) {
                        styles.put(declaration.getProperty(), declaration.getExpression());
                    }
                    applyBordersToTableCells(element, styles);
                }
                if (width >= 0) {
                    return Arrays.asList(new CssDeclaration(CssConstants.BORDER, value + "px solid"));
                }
            }
            return Collections.<CssDeclaration>emptyList();
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
                try {
                    boolean signedValue = value.contains("-") || value.contains("+");
                    int htmlFontSize = Integer.parseInt(value);
                    if (signedValue) {
                        htmlFontSize = 3 + htmlFontSize;
                    }
                    if (htmlFontSize < 2) {
                        cssValueEquivalent = CssConstants.X_SMALL;
                    } else if (htmlFontSize > 6) {
                        cssValueEquivalent = "48px";
                    } else if (htmlFontSize == 2) {
                        cssValueEquivalent = CssConstants.SMALL;
                    } else if (htmlFontSize == 3) {
                        cssValueEquivalent = CssConstants.MEDIUM;
                    } else if (htmlFontSize == 4) {
                        cssValueEquivalent = CssConstants.LARGE;
                    } else if (htmlFontSize == 5) {
                        cssValueEquivalent = CssConstants.X_LARGE;
                    } else if (htmlFontSize == 6) {
                        cssValueEquivalent = CssConstants.XX_LARGE;
                    }
                } catch (NumberFormatException ex) {
                    cssValueEquivalent = CssConstants.MEDIUM;
                }
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
                case AttributeConstants._1:
                    cssEquivalent = CssConstants.DECIMAL;
                    break;
                case AttributeConstants.A:
                    cssEquivalent = CssConstants.UPPER_ALPHA;
                    break;
                case AttributeConstants.a:
                    cssEquivalent = CssConstants.LOWER_ALPHA;
                    break;
                case AttributeConstants.I:
                    cssEquivalent = CssConstants.UPPER_ROMAN;
                    break;
                case AttributeConstants.i:
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
            return TagConstants.HR.equals(elementName) || TagConstants.IMG.equals(elementName)
                    || TagConstants.TABLE.equals(elementName) || TagConstants.TD.equals(elementName)
                    || TagConstants.TH.equals(elementName) || TagConstants.COLGROUP.equals(elementName)
                    || TagConstants.COL.equals(elementName);
        }

        @Override
        public List<CssDeclaration> convert(IElementNode element, String value) {
            String cssEquivalent = value;
            if (!value.endsWith(CssConstants.PERCENTAGE)) {
                cssEquivalent += CssConstants.PX;
            }
            return Arrays.asList(new CssDeclaration(CssConstants.WIDTH, cssEquivalent));
        }
    }

    private static class HeightAttributeConverter implements IAttributeConverter {

        @Override
        public boolean isSupportedForElement(String elementName) {
            return TagConstants.IMG.equals(elementName) || TagConstants.TD.equals(elementName);
        }

        @Override
        public List<CssDeclaration> convert(IElementNode element, String value) {
            String cssEquivalent = value;
            if (!value.endsWith(CssConstants.PERCENTAGE)) {
                cssEquivalent += CssConstants.PX;
            }
            return Arrays.asList(new CssDeclaration(CssConstants.HEIGHT, cssEquivalent));
        }
    }

    private static class AlignAttributeConverter implements IAttributeConverter {
        @Override
        public boolean isSupportedForElement(String elementName) {
            return TagConstants.HR.equals(elementName) || TagConstants.TABLE.equals(elementName) || TagConstants.IMG.equals(elementName)
                    || TagConstants.TD.equals(elementName) || TagConstants.DIV.equals(elementName) || TagConstants.P.equals(elementName);
        }

        @Override
        public List<CssDeclaration> convert(IElementNode element, String value) {
            List<CssDeclaration> result = new ArrayList<CssDeclaration>(2);
            if (TagConstants.HR.equals(element.name())
                    // html align-center attribute doesn't apply text wrapping
                    || (TagConstants.TABLE.equals(element.name()) && AttributeConstants.CENTER.equals(value))) {
                String leftMargin = null;
                String rightMargin = null;
                if (AttributeConstants.RIGHT.equals(value)) {
                    leftMargin = CssConstants.AUTO;
                    rightMargin = "0";
                } else if (AttributeConstants.LEFT.equals(value)) {
                    leftMargin = "0";
                    rightMargin = CssConstants.AUTO;
                } else if (AttributeConstants.CENTER.equals(value)) {
                    leftMargin = CssConstants.AUTO;
                    rightMargin = CssConstants.AUTO;
                }

                if (leftMargin != null) {
                    result.add(new CssDeclaration(CssConstants.MARGIN_LEFT, leftMargin));
                    result.add(new CssDeclaration(CssConstants.MARGIN_RIGHT, rightMargin));
                }
            } else if (TagConstants.TABLE.equals(element.name()) || TagConstants.IMG.equals(element.name())) {
                if (TagConstants.IMG.equals(element.name()) && AttributeConstants.TOP.equals(value)
                        && AttributeConstants.MIDDLE.equals(value) && AttributeConstants.BOTTOM.equals(value)) {
                    result.add(new CssDeclaration(CssConstants.VERTICAL_ALIGN, value));

                } else if (AttributeConstants.LEFT.equals(value) || AttributeConstants.RIGHT.equals(value)) {
                    result.add(new CssDeclaration(CssConstants.FLOAT, value));
                }
            } else {
                // TODO in fact, align attribute also affects horizontal alignment of all child blocks (not only direct children),
                // however this effect conflicts in queer manner with 'text-align' property if it set on the same blocks explicitly via CSS
                // (see HorizontalAlignmentTest#alignAttribute01)
                result.add(new CssDeclaration(CssConstants.TEXT_ALIGN, value));
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

    private static class VAlignAttributeConverter implements IAttributeConverter {
        @Override
        public boolean isSupportedForElement(String elementName) {
            return TagConstants.TD.equals(elementName) || TagConstants.TH.equals(elementName) 
                    || TagConstants.TR.equals(elementName);
        }

        @Override
        public List<CssDeclaration> convert(IElementNode element, String value) {
            return Arrays.asList(new CssDeclaration(CssConstants.VERTICAL_ALIGN, value));
        }
    }
}
