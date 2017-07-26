/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2017 iText Group NV
    Authors: Bruno Lowagie, Paulo Soares, et al.

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
import com.itextpdf.html2pdf.html.node.IStylesContainer;
import com.itextpdf.io.util.MessageFormatUtil;
import com.itextpdf.kernel.numbering.AlphabetNumbering;
import com.itextpdf.kernel.pdf.xobject.PdfImageXObject;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.List;
import com.itextpdf.layout.element.ListItem;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.IListSymbolFactory;
import com.itextpdf.layout.property.ListNumberingType;
import com.itextpdf.layout.property.ListSymbolPosition;
import com.itextpdf.layout.property.Property;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Utilities class to apply list styles to an element.
 */
public final class ListStyleApplierUtil {

    //private static final String HTML_SYMBOL_FONT = "Sans-serif";

    /** The Constant GREEK_ALPHABET_LENGTH. */
    private static final int GREEK_ALPHABET_LENGTH = 24;

    /** The Constant GREEK_LOWERCASE. */
    private static final char[] GREEK_LOWERCASE = new char[GREEK_ALPHABET_LENGTH];

    /** The Constant DISC_SYMBOL. */
    private static final String DISC_SYMBOL = "\u2022";

    /** The Constant CIRCLE_SYMBOL. */
    private static final String CIRCLE_SYMBOL = "\u25cb";

    /** The Constant SQUARE_SYMBOL. */
    private static final String SQUARE_SYMBOL = "\u25a0";

    static {
        for (int i = 0; i < GREEK_ALPHABET_LENGTH; i++) {
            GREEK_LOWERCASE[i] = (char) (945 + i + (i > 16 ? 1 : 0));
        }
    }

    /**
     * Creates a new {@link ListStyleApplierUtil} instance.
     */
    private ListStyleApplierUtil() {
    }

    /**
     * Applies an image list style to an element.
     *
     * @param cssProps the CSS properties
     * @param context the processor context
     * @param element the element
     */
    public static void applyListStyleImageProperty(Map<String, String> cssProps, ProcessorContext context, IPropertyContainer element) {
        String listStyleImage = cssProps.get(CssConstants.LIST_STYLE_IMAGE);
        if (listStyleImage != null && !CssConstants.NONE.equals(listStyleImage)) {
            String url = CssUtils.extractUrl(listStyleImage);
            PdfImageXObject imageXObject = context.getResourceResolver().retrieveImage(url);
            if (imageXObject != null) {
                element.setProperty(Property.LIST_SYMBOL, new Image(imageXObject));
                element.setProperty(Property.LIST_SYMBOL_INDENT, 5);
            }
        }
    }

    /**
     * Applies a list style to an element.
     *
     * @param stylesContainer the styles container
     * @param cssProps the CSS properties
     * @param context the processor context
     * @param element the element
     */
    public static void applyListStyleTypeProperty(IStylesContainer stylesContainer, Map<String, String> cssProps, ProcessorContext context, IPropertyContainer element) {
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
            element.setProperty(Property.LIST_SYMBOL, new HtmlAlphabetSymbolFactory(GREEK_LOWERCASE));
        } else if (CssConstants.NONE.equals(style)) {
            setListSymbol(element, new Text(""));
        } else {
            if (style != null) {
                Logger logger = LoggerFactory.getLogger(ListStyleApplierUtil.class);
                logger.error(MessageFormatUtil.format(LogMessageConstant.NOT_SUPPORTED_LIST_STYLE_TYPE, style));
            }

            // Fallback style
            if (stylesContainer instanceof IElementNode) {
                String elementName = ((IElementNode)stylesContainer).name();
                if (TagConstants.UL.equals(elementName)) {
                    setDiscStyle(element, em);
                } else if (TagConstants.OL.equals(elementName)) {
                    setListSymbol(element, ListNumberingType.DECIMAL);
                }
            }
        }
    }

    /**
     * Applies the "disc" list style to an element.
     *
     * @param element the element
     * @param em the em value
     */
    public static void setDiscStyle(IPropertyContainer element, float em) {
        Text symbol = new Text(DISC_SYMBOL);
        element.setProperty(Property.LIST_SYMBOL, symbol);
        setListSymbolIndent(element, em);
    }

    /**
     * Sets the list symbol for a {@link List} or {@link ListItem} element.
     *
     * @param container the container element ({@link List} or {@link ListItem})
     * @param text the list symbol
     */
    private static void setListSymbol(IPropertyContainer container, Text text) {
        if (container instanceof List) {
            ((List) container).setListSymbol(text);
        } else if (container instanceof ListItem) {
            ((ListItem) container).setListSymbol(text);
        }
    }

    /**
     * Sets the list symbol for a {@link List} or {@link ListItem} element.
     *
     * @param container the container element ({@link List} or {@link ListItem})
     * @param listNumberingType the list numbering type
     */
    private static void setListSymbol(IPropertyContainer container, ListNumberingType listNumberingType) {
        if (container instanceof List) {
            ((List) container).setListSymbol(listNumberingType);
        } else if (container instanceof ListItem) {
            ((ListItem) container).setListSymbol(listNumberingType);
        }
    }

    /**
     * Applies the "square" list style to an element.
     *
     * @param element the element
     * @param em the em value
     */
    private static void setSquareStyle(IPropertyContainer element, float em) {
        Text symbol = new Text(SQUARE_SYMBOL);
        symbol.setTextRise(1.5f * em / 12);
        symbol.setFontSize(4.5f * em / 12);
        element.setProperty(Property.LIST_SYMBOL, symbol);
        setListSymbolIndent(element, em);
    }

    /**
     * Applies the "circle" list style to an element.
     *
     * @param element the element
     * @param em the em value
     */
    private static void setCircleStyle(IPropertyContainer element, float em) {
        Text symbol = new Text(CIRCLE_SYMBOL);
        symbol.setTextRise(1.5f * em / 12);
        symbol.setFontSize(4.5f * em / 12);
        element.setProperty(Property.LIST_SYMBOL, symbol);
        setListSymbolIndent(element, em);
    }

    /**
     * Sets the list symbol indentation.
     *
     * @param element the element
     * @param em the em value
     */
    private static void setListSymbolIndent(IPropertyContainer element, float em) {
        if  (ListSymbolPosition.INSIDE == element.<ListSymbolPosition>getProperty(Property.LIST_SYMBOL_POSITION)) {
            element.setProperty(Property.LIST_SYMBOL_INDENT, 1.5f * em);
        } else {
            element.setProperty(Property.LIST_SYMBOL_INDENT, 7.75f);
        }
    }

    /**
     * A factory for creating {@code HtmlAlphabetSymbol} objects.
     */
    private static class HtmlAlphabetSymbolFactory implements IListSymbolFactory {

        /** The alphabet. */
        private final char[] alphabet;

        /**
         * Creates a new {@link HtmlAlphabetSymbolFactory} instance.
         *
         * @param alphabet the alphabet
         */
        public HtmlAlphabetSymbolFactory(char[] alphabet) {
            this.alphabet = alphabet;
        }

        /* (non-Javadoc)
         * @see com.itextpdf.layout.property.IListSymbolFactory#createSymbol(int, com.itextpdf.layout.IPropertyContainer, com.itextpdf.layout.IPropertyContainer)
         */
        @Override
        public IElement createSymbol(int index, IPropertyContainer list, IPropertyContainer listItem) {
            Object preValue = getListItemOrListProperty(listItem, list, Property.LIST_SYMBOL_PRE_TEXT);
            Object postValue = getListItemOrListProperty(listItem, list, Property.LIST_SYMBOL_POST_TEXT);
            Text result = new Text(preValue + AlphabetNumbering.toAlphabetNumber(index, alphabet) + postValue);
            return result;
        }

        /**
         * Gets the a property from a {@link ListItem}, or from the {@link List}
         * (if the property) isn't declared for the list item.
         *
         * @param listItem the list item
         * @param list the list
         * @param propertyId the property id
         * @return the property value
         */
        private static Object getListItemOrListProperty(IPropertyContainer listItem, IPropertyContainer list, int propertyId) {
            return listItem.hasProperty(propertyId) ? listItem.<Object>getProperty(propertyId) : list.<Object>getProperty(propertyId);
        }
    }
}
