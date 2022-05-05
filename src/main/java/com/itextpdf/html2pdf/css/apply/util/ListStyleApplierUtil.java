/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2022 iText Group NV
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

import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.html.TagConstants;
import com.itextpdf.commons.utils.MessageFormatUtil;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.gradients.StrategyBasedLinearGradientBuilder;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.numbering.AlphabetNumbering;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.kernel.pdf.xobject.PdfImageXObject;
import com.itextpdf.kernel.pdf.xobject.PdfXObject;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.List;
import com.itextpdf.layout.element.ListItem;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.IListSymbolFactory;
import com.itextpdf.layout.properties.ListNumberingType;
import com.itextpdf.layout.properties.ListSymbolPosition;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.styledxmlparser.css.util.CssGradientUtil;
import com.itextpdf.styledxmlparser.css.util.CssDimensionParsingUtils;
import com.itextpdf.styledxmlparser.css.util.CssUtils;
import com.itextpdf.styledxmlparser.exceptions.StyledXMLParserException;
import com.itextpdf.styledxmlparser.node.IElementNode;
import com.itextpdf.styledxmlparser.node.IStylesContainer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Utilities class to apply list styles to an element.
 */
public final class ListStyleApplierUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ListStyleApplierUtil.class);

    /**
     * The Constant LIST_ITEM_MARKER_SIZE_COEFFICIENT.
     *
     * The coefficient value of 2/5 is chosen in such a way that the result
     * of the converting is as similar as possible to the browsers displaying.
     */
    private static final float LIST_ITEM_MARKER_SIZE_COEFFICIENT = 2 / 5f;

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
        String listStyleImageStr = cssProps.get(CssConstants.LIST_STYLE_IMAGE);

        PdfXObject imageXObject = null;
        if (listStyleImageStr != null && !CssConstants.NONE.equals(listStyleImageStr)) {
            if (CssGradientUtil.isCssLinearGradientValue(listStyleImageStr)) {
                float em = CssDimensionParsingUtils.parseAbsoluteLength(cssProps.get(CssConstants.FONT_SIZE));
                float rem = context.getCssContext().getRootFontSize();
                try {
                    StrategyBasedLinearGradientBuilder gradientBuilder =
                            CssGradientUtil.parseCssLinearGradient(listStyleImageStr, em, rem);
                    if (gradientBuilder != null) {
                        Rectangle formBBox = new Rectangle(0, 0, em * LIST_ITEM_MARKER_SIZE_COEFFICIENT,
                                em * LIST_ITEM_MARKER_SIZE_COEFFICIENT);

                        PdfDocument pdfDocument = context.getPdfDocument();
                        Color gradientColor = gradientBuilder.buildColor(formBBox, null, pdfDocument);
                        if (gradientColor != null) {
                            imageXObject = new PdfFormXObject(formBBox);
                            new PdfCanvas((PdfFormXObject) imageXObject, context.getPdfDocument())
                                    .setColor(gradientColor, true)
                                    .rectangle(formBBox)
                                    .fill();
                        }
                    }
                } catch (StyledXMLParserException e) {
                    LOGGER.warn(MessageFormatUtil.format(
                            Html2PdfLogMessageConstant.INVALID_GRADIENT_DECLARATION, listStyleImageStr));
                }
            } else {
                imageXObject = context.getResourceResolver().retrieveImage(CssUtils.extractUrl(listStyleImageStr));
            }

            if (imageXObject != null) {
                Image image = null;
                if (imageXObject instanceof PdfImageXObject) {
                    image = new Image((PdfImageXObject) imageXObject);
                } else if (imageXObject instanceof PdfFormXObject) {
                    image = new Image((PdfFormXObject) imageXObject);
                } else {
                    throw new IllegalStateException();
                }
                element.setProperty(Property.LIST_SYMBOL, image);
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
        float em = CssDimensionParsingUtils.parseAbsoluteLength(cssProps.get(CssConstants.FONT_SIZE));

        String style = cssProps.get(CssConstants.LIST_STYLE_TYPE);
        switch (style) {
            case CssConstants.DISC:
                setDiscStyle(element, em);
                break;
            case CssConstants.CIRCLE:
                setCircleStyle(element, em);
                break;
            case CssConstants.SQUARE:
                setSquareStyle(element, em);
                break;
            case CssConstants.DECIMAL:
                setListSymbol(element, ListNumberingType.DECIMAL);
                break;
            case CssConstants.DECIMAL_LEADING_ZERO:
                setListSymbol(element, ListNumberingType.DECIMAL_LEADING_ZERO);
                break;
            case CssConstants.UPPER_ALPHA:
            case CssConstants.UPPER_LATIN:
                setListSymbol(element, ListNumberingType.ENGLISH_UPPER);
                break;
            case CssConstants.LOWER_ALPHA:
            case CssConstants.LOWER_LATIN:
                setListSymbol(element, ListNumberingType.ENGLISH_LOWER);
                break;
            case CssConstants.LOWER_ROMAN:
                setListSymbol(element, ListNumberingType.ROMAN_LOWER);
                break;
            case CssConstants.UPPER_ROMAN:
                setListSymbol(element, ListNumberingType.ROMAN_UPPER);
                break;
            case CssConstants.LOWER_GREEK:
                element.setProperty(Property.LIST_SYMBOL, new HtmlAlphabetSymbolFactory(GREEK_LOWERCASE));
                break;
            case CssConstants.NONE:
                setListSymbol(element, new Text(""));
                break;
            default:
                Logger logger = LoggerFactory.getLogger(ListStyleApplierUtil.class);
                logger.error(MessageFormatUtil.format(Html2PdfLogMessageConstant.NOT_SUPPORTED_LIST_STYLE_TYPE, style));

                // Fallback style
                if (stylesContainer instanceof IElementNode) {
                    String elementName = ((IElementNode)stylesContainer).name();
                    if (TagConstants.UL.equals(elementName)) {
                        setDiscStyle(element, em);
                    } else if (TagConstants.OL.equals(elementName)) {
                        setListSymbol(element, ListNumberingType.DECIMAL);
                    }
                }
                break;
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
            return new Text(preValue + AlphabetNumbering.toAlphabetNumber(index, alphabet) + postValue);
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
