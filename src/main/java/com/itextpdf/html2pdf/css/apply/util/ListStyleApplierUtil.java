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
package com.itextpdf.html2pdf.css.apply.util;

import com.itextpdf.html2pdf.LogMessageConstant;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.css.util.CssUtils;
import com.itextpdf.html2pdf.html.TagConstants;
import com.itextpdf.html2pdf.html.node.IElementNode;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.List;
import com.itextpdf.layout.element.ListItem;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.ListNumberingType;
import com.itextpdf.layout.property.ListSymbolPosition;
import com.itextpdf.layout.property.Property;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Map;

public final class ListStyleApplierUtil {

    private ListStyleApplierUtil() {
    }

    public static void applyListStyleImageProperty(Map<String, String> cssProps, ProcessorContext context, IPropertyContainer element) {
        String listStyleImage = cssProps.get(CssConstants.LIST_STYLE_IMAGE);
        if (listStyleImage != null && !CssConstants.NONE.equals(listStyleImage)) {
            String url = CssUtils.extractUrl(listStyleImage);
            ImageData imageData = context.getResourceResolver().retrieveImage(url);
            if (imageData != null) {
                element.setProperty(Property.LIST_SYMBOL, new Image(imageData));
                element.setProperty(Property.LIST_SYMBOL_INDENT, 5);
            }
        }
    }

    //TODO problems with Pdf/A conversion. Avoid ZapfDingBats, Symbol font
    public static void applyListStyleTypeProperty(IElementNode node, Map<String, String> cssProps, ProcessorContext context, IPropertyContainer element) {
        float em = CssUtils.parseAbsoluteLength(cssProps.get(CssConstants.FONT_SIZE));

        String style = cssProps.get(CssConstants.LIST_STYLE_TYPE);
        if (CssConstants.DISC.equals(style)) {
            setDiscStyle(element, em);
        } else if (CssConstants.CIRCLE.equals(style)) {
            setCircleStyle(element, em);
        } else if (CssConstants.SQUARE.equals(style)) {
            setSquareStyle(element, em);
        } else if (CssConstants.DECIMAL.equals(style)) {
            setListSymbol(element, ListNumberingType.DECIMAL);
        } else if (CssConstants.DECIMAL_LEADING_ZERO.equals(style)) {
            setListSymbol(element, ListNumberingType.DECIMAL_LEADING_ZERO);
        } else if (CssConstants.UPPER_ALPHA.equals(style) || CssConstants.UPPER_LATIN.equals(style)) {
            setListSymbol(element, ListNumberingType.ENGLISH_UPPER);
        } else if (CssConstants.LOWER_ALPHA.equals(style) || CssConstants.LOWER_LATIN.equals(style)) {
            setListSymbol(element, ListNumberingType.ENGLISH_LOWER);
        } else if (CssConstants.UPPER_ROMAN.equals(style)) {
            setListSymbol(element, ListNumberingType.ROMAN_UPPER);
        } else if (CssConstants.LOWER_ROMAN.equals(style)) {
            setListSymbol(element, ListNumberingType.ROMAN_LOWER);
        } else if (CssConstants.LOWER_GREEK.equals(style)) {
            setListSymbol(element, ListNumberingType.GREEK_LOWER);
        } else if (CssConstants.NONE.equals(style)) {
            setListSymbol(element, new Text(""));
        } else {
            if (style != null) {
                Logger logger = LoggerFactory.getLogger(ListStyleApplierUtil.class);
                logger.error(MessageFormat.format(LogMessageConstant.NOT_SUPPORTED_LIST_STYLE_TYPE, style));
            }

            // Fallback style
            String elementName = node.name();
            if (TagConstants.UL.equals(elementName)) {
                setDiscStyle(element, em);
            } else if (TagConstants.OL.equals(elementName)) {
                setListSymbol(element, ListNumberingType.DECIMAL);
            } else if (TagConstants.LI.equals(elementName)) {
                setDiscStyle(element, em);
            }
        }
    }

    private static void setListSymbol(IPropertyContainer container, Text text) {
        if (container instanceof List) {
            ((List) container).setListSymbol(text);
        } else if (container instanceof ListItem) {
            ((ListItem) container).setListSymbol(text);
        }
    }

    private static void setListSymbol(IPropertyContainer container, ListNumberingType listNumberingType) {
        if (container instanceof List) {
            ((List) container).setListSymbol(listNumberingType);
        } else if (container instanceof ListItem) {
            ((ListItem) container).setListSymbol(listNumberingType);
        }
    }

    private static void setDiscStyle(IPropertyContainer element, float em) {
        Text symbol = new Text(String.valueOf(((char)108))).setFont(createZapfDingBatsSafe());
        symbol.setTextRise(1.5f);
        symbol.setFontSize(4.5f);
        element.setProperty(Property.LIST_SYMBOL, symbol);
        setListSymbolIndent(element, em);
    }

    private static void setSquareStyle(IPropertyContainer element, float em) {
        Text symbol = new Text(String.valueOf(((char)110))).setFont(createZapfDingBatsSafe());
        symbol.setTextRise(1.5f);
        symbol.setFontSize(4.5f);
        element.setProperty(Property.LIST_SYMBOL, symbol);
        setListSymbolIndent(element, em);
    }

    private static void setCircleStyle(IPropertyContainer element, float em) {
        Text symbol = new Text(String.valueOf(((char)109))).setFont(createZapfDingBatsSafe());
        symbol.setTextRise(1.5f);
        symbol.setFontSize(4.5f);
        element.setProperty(Property.LIST_SYMBOL, symbol);
        setListSymbolIndent(element, em);
    }

    private static void setListSymbolIndent(IPropertyContainer element, float em) {
        if  (ListSymbolPosition.INSIDE == element.<ListSymbolPosition>getProperty(Property.LIST_SYMBOL_POSITION)) {
            element.setProperty(Property.LIST_SYMBOL_INDENT, 1.5f * em);
        } else {
            element.setProperty(Property.LIST_SYMBOL_INDENT, 7.75f);
        }
    }

    private static PdfFont createZapfDingBatsSafe() {
        try {
            return PdfFontFactory.createFont(FontConstants.ZAPFDINGBATS);
        } catch (IOException exc) {
            Logger logger = LoggerFactory.getLogger(ListStyleApplierUtil.class);
            logger.error("Unable to create ZapfDingBats font", exc);
            return null;
        }
    }

}
