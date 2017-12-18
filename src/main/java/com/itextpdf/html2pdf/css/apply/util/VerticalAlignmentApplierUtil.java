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

import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.css.util.CssUtils;
import com.itextpdf.html2pdf.html.node.IElementNode;
import com.itextpdf.html2pdf.html.node.INode;
import com.itextpdf.html2pdf.html.node.IStylesContainer;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.Property;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;

import java.util.List;
import java.util.Map;

/**
 * Utilities class to apply vertical alignment values.
 */
public class VerticalAlignmentApplierUtil {

    /** The Constant ASCENDER_COEFFICIENT. */
    private static final double ASCENDER_COEFFICIENT = 0.8;

    /** The Constant DESCENDER_COEFFICIENT. */
    private static final double DESCENDER_COEFFICIENT = 0.2;

    /**
     * Creates a new {@link VerticalAlignmentApplierUtil}.
     */
    private VerticalAlignmentApplierUtil() {
    }

    /**
     * Applies vertical alignment to cells.
     *
     * @param cssProps the CSS properties
     * @param context the processor context
     * @param element the element
     */
    public static void applyVerticalAlignmentForCells(Map<String, String> cssProps, ProcessorContext context, IPropertyContainer element) {
        String vAlignVal = cssProps.get(CssConstants.VERTICAL_ALIGN);
        if (vAlignVal != null) {
            // In layout, 'top' is the default behaviour for cells;
            // 'baseline' is not supported at the moment on layout level, so it defaults to value 'top';
            // all other possible values except 'middle' and 'bottom' do not apply to cells; 'baseline' is applied instead.

            if (CssConstants.MIDDLE.equals(vAlignVal)) {
                element.setProperty(Property.VERTICAL_ALIGNMENT, VerticalAlignment.MIDDLE);
            } else if (CssConstants.BOTTOM.equals(vAlignVal)) {
                element.setProperty(Property.VERTICAL_ALIGNMENT, VerticalAlignment.BOTTOM);
            }
        }
    }

    /**
     * Apply vertical alignment to inline elements.
     *
     * @param cssProps the CSS properties
     * @param context the processor context
     * @param stylesContainer the styles container
     * @param childElements the child elements
     */
    public static void applyVerticalAlignmentForInlines(Map<String, String> cssProps, ProcessorContext context, IStylesContainer stylesContainer, List<IPropertyContainer> childElements) {
        String vAlignVal = cssProps.get(CssConstants.VERTICAL_ALIGN);
        if (vAlignVal != null) {

            // TODO for inline images and tables (inline-blocks) v-align is not supported

            float textRise = 0;

            // TODO 'top' and 'bottom' values are not supported;
            // 'top' and 'bottom' require information of actual line height, therefore should be applied at layout level;
            // 'sub', 'super' calculations are based on the behaviour of the common browsers (+33% and -20% shift accordingly from the parent's font size);
            // 'middle', 'text-top', 'text-bottom' calculations are based on the approximate assumptions that x-height is 0.5 of the font size
            // and descender and ascender heights are 0.2 and 0.8 of the font size accordingly.

            if (CssConstants.SUB.equals(vAlignVal) || CssConstants.SUPER.equals(vAlignVal)) {
                textRise = calcTextRiseForSupSub(stylesContainer, vAlignVal);

            } else if (CssConstants.MIDDLE.equals(vAlignVal)) {
                textRise = calcTextRiseForMiddle(stylesContainer);

            } else if (CssConstants.TEXT_TOP.equals(vAlignVal)) {
                textRise = calcTextRiseForTextTop(stylesContainer, context.getCssContext().getRootFontSize());

            } else if (CssConstants.TEXT_BOTTOM.equals(vAlignVal)) {
                textRise = calcTextRiseForTextBottom(stylesContainer, context.getCssContext().getRootFontSize());

            } else if (CssUtils.isMetricValue(vAlignVal)) {
                textRise = CssUtils.parseAbsoluteLength(vAlignVal);

            } else if (vAlignVal.endsWith(CssConstants.PERCENTAGE)) {
                textRise = calcTextRiseForPercentageValue(stylesContainer, context.getCssContext().getRootFontSize(), vAlignVal);
            }
            if (textRise != 0) {
                for (IPropertyContainer element : childElements) {
                    if (element instanceof Text) {
                        Float effectiveTr = element.<Float>getProperty(Property.TEXT_RISE);
                        if (effectiveTr != null) {
                            effectiveTr += textRise;
                        } else {
                            effectiveTr = textRise;
                        }
                        element.setProperty(Property.TEXT_RISE, effectiveTr);
                    } else if (element instanceof IBlockElement) {
                        break;
                    }
                }
            }
        }
    }

    /**
     * Calculates the text rise value for &lt;sup&gt; and &lt;sub&gt; tags.
     *
     * @param stylesContainer the styles container
     * @param vAlignVal the vertical alignment value
     * @return the calculated text rise
     */
    private static float calcTextRiseForSupSub(IStylesContainer stylesContainer, String vAlignVal) {
        float parentFontSize = getParentFontSize(stylesContainer);
        String superscriptPosition = "33%";
        String subscriptPosition = "-20%";
        String relativeValue = CssConstants.SUPER.equals(vAlignVal) ? superscriptPosition : subscriptPosition;
        return CssUtils.parseRelativeValue(relativeValue, parentFontSize);
    }

    /**
     * Calculates the text rise for middle alignment.
     *
     * @param stylesContainer the styles container
     * @return the calculated text rise
     */
    private static float calcTextRiseForMiddle(IStylesContainer stylesContainer) {
        String ownFontSizeStr = stylesContainer.getStyles().get(CssConstants.FONT_SIZE);
        float fontSize = CssUtils.parseAbsoluteLength(ownFontSizeStr);
        float parentFontSize = getParentFontSize(stylesContainer);

        double fontMiddleCoefficient = 0.3;
        float elementMidPoint = (float) (fontSize * fontMiddleCoefficient); // shift to element mid point from the baseline
        float xHeight = parentFontSize / 4;

        return xHeight - elementMidPoint;
    }

    /**
     * Calculates the text rise for top alignment.
     *
     * @param stylesContainer the styles container
     * @param rootFontSize the root font size
     * @return the calculated text rise
     */
    private static float calcTextRiseForTextTop(IStylesContainer stylesContainer, float rootFontSize) {
        String ownFontSizeStr = stylesContainer.getStyles().get(CssConstants.FONT_SIZE);
        float fontSize = CssUtils.parseAbsoluteLength(ownFontSizeStr);
        String lineHeightStr = stylesContainer.getStyles().get(CssConstants.LINE_HEIGHT);
        float lineHeightActualValue = getLineHeightActualValue(fontSize, rootFontSize, lineHeightStr);
        float parentFontSize = getParentFontSize(stylesContainer);

        float elementTopEdge = (float) (fontSize * ASCENDER_COEFFICIENT + (lineHeightActualValue - fontSize) / 2);
        float parentTextTop = (float) (parentFontSize * ASCENDER_COEFFICIENT);

        return parentTextTop - elementTopEdge;
    }

    /**
     * Calculates the text rise for bottom alignment.
     *
     * @param stylesContainer the styles container
     * @param rootFontSize the root font size
     * @return the calculated text rise
     */
    private static float calcTextRiseForTextBottom(IStylesContainer stylesContainer, float rootFontSize) {
        String ownFontSizeStr = stylesContainer.getStyles().get(CssConstants.FONT_SIZE);
        float fontSize = CssUtils.parseAbsoluteLength(ownFontSizeStr);
        String lineHeightStr = stylesContainer.getStyles().get(CssConstants.LINE_HEIGHT);
        float lineHeightActualValue = getLineHeightActualValue(fontSize, rootFontSize, lineHeightStr);
        float parentFontSize = getParentFontSize(stylesContainer);

        float elementBottomEdge = (float) (fontSize * DESCENDER_COEFFICIENT + (lineHeightActualValue - fontSize) / 2);
        float parentTextBottom = (float) (parentFontSize * DESCENDER_COEFFICIENT);

        return elementBottomEdge - parentTextBottom;
    }

    /**
     * Calculates text rise for percentage value text rise.
     *
     * @param stylesContainer the styles container
     * @param rootFontSize the root font size
     * @param vAlignVal the vertical alignment value
     * @return the calculated text rise
     */
    private static float calcTextRiseForPercentageValue(IStylesContainer stylesContainer, float rootFontSize, String vAlignVal) {
        String ownFontSizeStr = stylesContainer.getStyles().get(CssConstants.FONT_SIZE);
        float fontSize = CssUtils.parseAbsoluteLength(ownFontSizeStr);
        String lineHeightStr = stylesContainer.getStyles().get(CssConstants.LINE_HEIGHT);
        float lineHeightActualValue = getLineHeightActualValue(fontSize, rootFontSize, lineHeightStr);

        return CssUtils.parseRelativeValue(vAlignVal, lineHeightActualValue);
    }


    /**
     * Gets the actual value of the line height.
     *
     * @param fontSize the font size
     * @param rootFontSize the root font size
     * @param lineHeightStr the line height as a {@link String}
     * @return the actual line height as a {@code float}
     */
    private static float getLineHeightActualValue(float fontSize, float rootFontSize, String lineHeightStr) {
        float lineHeightActualValue;
        if (lineHeightStr != null) {
            if (CssConstants.NORMAL.equals(lineHeightStr) || CssConstants.AUTO.equals(lineHeightStr)) {
                lineHeightActualValue = (float) (fontSize * 1.2);
            } else {
                UnitValue lineHeightValue = CssUtils.parseLengthValueToPt(lineHeightStr, fontSize, rootFontSize);
                if (CssUtils.isNumericValue(lineHeightStr)) {
                    lineHeightActualValue = fontSize * lineHeightValue.getValue();
                } else if (lineHeightValue.isPointValue()) {
                    lineHeightActualValue = lineHeightValue.getValue();
                } else {
                    lineHeightActualValue = fontSize * lineHeightValue.getValue() / 100;
                }
            }
        } else {
            lineHeightActualValue = (float) (fontSize * 1.2);
        }
        return lineHeightActualValue;
    }

    /**
     * Gets the parent font size.
     *
     * @param stylesContainer the styles container
     * @return the parent font size
     */
    private static float getParentFontSize(IStylesContainer stylesContainer) {
        float parentFontSize;
        if (stylesContainer instanceof INode && ((IElementNode)stylesContainer).parentNode() instanceof IStylesContainer) {
            INode parent = ((IElementNode) stylesContainer).parentNode();
            String parentFontSizeStr = ((IStylesContainer) parent).getStyles().get(CssConstants.FONT_SIZE);
            parentFontSize = CssUtils.parseAbsoluteLength(parentFontSizeStr);
        } else {
            // let's take own font size for this unlikely case
            String ownFontSizeStr = stylesContainer.getStyles().get(CssConstants.FONT_SIZE);
            parentFontSize = CssUtils.parseAbsoluteLength(ownFontSizeStr);
        }
        return parentFontSize;
    }
}
