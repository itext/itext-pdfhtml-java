/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2026 Apryse Group NV
    Authors: Apryse Software.

    This program is offered under a commercial and under the AGPL license.
    For commercial licensing, contact us at https://itextpdf.com/sales.  For AGPL licensing, see below.

    AGPL licensing:
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.itextpdf.html2pdf.css.apply.impl;

import com.itextpdf.forms.form.element.IFormField;
import com.itextpdf.html2pdf.attach.ITagWorker;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.attach.impl.tags.SpanTagWorker;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.css.apply.ICssApplier;
import com.itextpdf.html2pdf.css.apply.util.BackgroundApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.BorderStyleApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.FloatApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.FontStyleApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.HyphenationApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.MarginApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.OpacityApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.OutlineApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.PaddingApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.PositionApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.VerticalAlignmentApplierUtil;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.FloatPropertyValue;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import com.itextpdf.styledxmlparser.node.IStylesContainer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * {@link ICssApplier} implementation for Span elements.
 */
public class SpanTagCssApplier implements ICssApplier {

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.css.apply.ICssApplier#apply(com.itextpdf.html2pdf.attach.ProcessorContext, com.itextpdf.html2pdf.html.node.IStylesContainer, com.itextpdf.html2pdf.attach.ITagWorker)
     */
    @Override
    public void apply(ProcessorContext context, IStylesContainer stylesContainer, ITagWorker tagWorker) {
        SpanTagWorker spanTagWorker = (SpanTagWorker) tagWorker;
        Map<String, String> cssStyles = stylesContainer.getStyles();
        for (IPropertyContainer child : spanTagWorker.getOwnLeafElements()) {
            // Workaround for form fields so that SpanTagCssApplier does not apply its font-size to the child.
            // Form fields have their own CSS applier
            if (!(child instanceof IFormField)) {
                applyChildElementStyles(child, cssStyles, context, stylesContainer);
            }
        }

        applyMarginsPaddingsBordersStyles(spanTagWorker.getAllElements(), cssStyles, context);

        VerticalAlignmentApplierUtil.applyVerticalAlignmentForInlines(cssStyles, context, stylesContainer,
                spanTagWorker.getAllElements());

        if (cssStyles.containsKey(CssConstants.OPACITY)) {
            for (IPropertyContainer elem : spanTagWorker.getAllElements()) {
                if (elem instanceof Text && !elem.hasProperty(Property.OPACITY)) {
                    OpacityApplierUtil.applyOpacity(cssStyles, context, elem);
                }
            }
        }

        String floatVal = cssStyles.get(CssConstants.FLOAT);
        if (floatVal != null && !CssConstants.NONE.equals(floatVal)) {
            for (IPropertyContainer elem : spanTagWorker.getAllElements()) {
                FloatPropertyValue kidFloatVal = elem.<FloatPropertyValue>getProperty(Property.FLOAT);
                if (kidFloatVal == null || FloatPropertyValue.NONE.equals(kidFloatVal)) {
                    FloatApplierUtil.applyFloating(cssStyles, context, elem);
                }
            }
        }

        if (spanTagWorker.getAllElements() != null) {
            for (IPropertyContainer child : spanTagWorker.getAllElements()) {
                FloatPropertyValue kidFloatVal = child.<FloatPropertyValue>getProperty(Property.FLOAT);
                if (child instanceof Text && !child.hasOwnProperty(Property.BACKGROUND)
                        && (kidFloatVal == null || FloatPropertyValue.NONE.equals(kidFloatVal))) {
                    BackgroundApplierUtil.applyBackground(cssStyles, context, child);
                }
            }
        }
    }

    /**
     * Applies styles to child elements.
     *
     * @param element the element
     * @param css the CSS mapping
     * @param context the processor context
     * @param stylesContainer the styles container
     */
    protected void applyChildElementStyles(IPropertyContainer element, Map<String, String> css, ProcessorContext context, IStylesContainer stylesContainer) {
        FontStyleApplierUtil.applyFontStyles(css, context, stylesContainer, element);
        BackgroundApplierUtil.applyBackground(css, context, element);
        OutlineApplierUtil.applyOutlines(css, context, element);
        HyphenationApplierUtil.applyHyphenation(css, context, stylesContainer, element);
        PositionApplierUtil.applyPosition(css, context, element);
        FloatApplierUtil.applyFloating(css, context, element);
    }

    protected void applyMarginsPaddingsBordersStyles(List<IPropertyContainer> allChildren, Map<String, String> css,
                                                     ProcessorContext context) {
        if (allChildren != null && !allChildren.isEmpty()) {
            IPropertyContainer firstChild = allChildren.get(0);
            IPropertyContainer lastChild = allChildren.get(allChildren.size() - 1);

            // Apply left margin to the first child, apply right margin to the last child.
            applyPropertiesCertainPropertyValue(firstChild, css, Property.MARGIN_LEFT, true,
                    newStyles -> MarginApplierUtil.applyMargins(newStyles, context, firstChild),
                    CommonCssConstants.MARGIN_LEFT);
            applyPropertiesCertainPropertyValue(lastChild, css, Property.MARGIN_RIGHT, true,
                    newStyles -> MarginApplierUtil.applyMargins(newStyles, context, lastChild),
                    CommonCssConstants.MARGIN_RIGHT);

            // Apply left padding to the first child, apply right padding to the last child.
            applyPropertiesCertainPropertyValue(firstChild, css, Property.PADDING_LEFT, true,
                    newStyles -> PaddingApplierUtil.applyPaddings(newStyles, context, firstChild),
                    CommonCssConstants.PADDING_LEFT);
            applyPropertiesCertainPropertyValue(lastChild, css, Property.PADDING_RIGHT, true,
                    newStyles -> PaddingApplierUtil.applyPaddings(newStyles, context, lastChild),
                    CommonCssConstants.PADDING_RIGHT);

            // Apply top and bottom paddings to all the children.
            Map<String, String> topAndBottomPaddingStyles = new HashMap<>();
            topAndBottomPaddingStyles.put(CommonCssConstants.PADDING_TOP, css.get(CommonCssConstants.PADDING_TOP));
            topAndBottomPaddingStyles.put(CommonCssConstants.PADDING_BOTTOM,
                    css.get(CommonCssConstants.PADDING_BOTTOM));
            topAndBottomPaddingStyles.put(CommonCssConstants.FONT_SIZE, css.get(CommonCssConstants.FONT_SIZE));
            for (IPropertyContainer child : allChildren) {
                PaddingApplierUtil.applyPaddings(topAndBottomPaddingStyles, context, child);
            }

            // Apply border-left, as well as left radii to the first child.
            applyPropertiesCertainPropertyValue(firstChild, css, Property.BORDER_LEFT, false,
                    newStyles -> BorderStyleApplierUtil.applyBorders(newStyles, context, firstChild),
                    CommonCssConstants.BORDER_LEFT_STYLE, CommonCssConstants.BORDER_LEFT_COLOR,
                    CommonCssConstants.BORDER_LEFT_WIDTH);
            applyPropertiesCertainPropertyValue(firstChild, css, Property.BORDER_TOP_LEFT_RADIUS, false,
                    newStyles -> BorderStyleApplierUtil.applyBorders(newStyles, context, firstChild),
                    CommonCssConstants.BORDER_TOP_LEFT_RADIUS);
            applyPropertiesCertainPropertyValue(firstChild, css, Property.BORDER_BOTTOM_LEFT_RADIUS, false,
                    newStyles -> BorderStyleApplierUtil.applyBorders(newStyles, context, firstChild),
                    CommonCssConstants.BORDER_BOTTOM_LEFT_RADIUS);

            // Apply border-right, as well as right radii to the last child.
            applyPropertiesCertainPropertyValue(lastChild, css, Property.BORDER_RIGHT, false,
                    newStyles -> BorderStyleApplierUtil.applyBorders(newStyles, context, lastChild),
                    CommonCssConstants.BORDER_RIGHT_STYLE, CommonCssConstants.BORDER_RIGHT_COLOR,
                    CommonCssConstants.BORDER_RIGHT_WIDTH);
            applyPropertiesCertainPropertyValue(lastChild, css, Property.BORDER_TOP_RIGHT_RADIUS, false,
                    newStyles -> BorderStyleApplierUtil.applyBorders(newStyles, context, lastChild),
                    CommonCssConstants.BORDER_TOP_RIGHT_RADIUS);
            applyPropertiesCertainPropertyValue(lastChild, css, Property.BORDER_BOTTOM_RIGHT_RADIUS, false,
                    newStyles -> BorderStyleApplierUtil.applyBorders(newStyles, context, lastChild),
                    CommonCssConstants.BORDER_BOTTOM_RIGHT_RADIUS);

            // Apply top and bottom borders to all the children.
            Map<String, String> topAndBottomBorderStyles = new HashMap<>(css);
            topAndBottomBorderStyles.remove(CommonCssConstants.BORDER_LEFT_STYLE);
            topAndBottomBorderStyles.remove(CommonCssConstants.BORDER_LEFT_COLOR);
            topAndBottomBorderStyles.remove(CommonCssConstants.BORDER_LEFT_WIDTH);
            topAndBottomBorderStyles.remove(CommonCssConstants.BORDER_RIGHT_STYLE);
            topAndBottomBorderStyles.remove(CommonCssConstants.BORDER_RIGHT_COLOR);
            topAndBottomBorderStyles.remove(CommonCssConstants.BORDER_RIGHT_WIDTH);
            topAndBottomBorderStyles.remove(CommonCssConstants.BORDER_TOP_LEFT_RADIUS);
            topAndBottomBorderStyles.remove(CommonCssConstants.BORDER_TOP_RIGHT_RADIUS);
            topAndBottomBorderStyles.remove(CommonCssConstants.BORDER_BOTTOM_LEFT_RADIUS);
            topAndBottomBorderStyles.remove(CommonCssConstants.BORDER_BOTTOM_RIGHT_RADIUS);

            Map<String, String> topBorderStyles = new HashMap<>(topAndBottomBorderStyles);
            topBorderStyles.remove(CommonCssConstants.BORDER_BOTTOM_STYLE);
            topBorderStyles.remove(CommonCssConstants.BORDER_BOTTOM_WIDTH);
            topBorderStyles.remove(CommonCssConstants.BORDER_BOTTOM_COLOR);

            Map<String, String> bottomBorderStyles = new HashMap<>(topAndBottomBorderStyles);
            topBorderStyles.remove(CommonCssConstants.BORDER_TOP_STYLE);
            topBorderStyles.remove(CommonCssConstants.BORDER_TOP_WIDTH);
            topBorderStyles.remove(CommonCssConstants.BORDER_TOP_COLOR);

            for (IPropertyContainer child : allChildren) {
                if (!child.hasProperty(Property.BORDER_TOP)) {
                    BorderStyleApplierUtil.applyBorders(topBorderStyles, context, child);
                }
                if (!child.hasProperty(Property.BORDER_BOTTOM)) {
                    BorderStyleApplierUtil.applyBorders(bottomBorderStyles, context, child);
                }
            }
        }
    }

    private static void applyPropertiesCertainPropertyValue(IPropertyContainer child, Map<String, String> styles,
                                                     int property, boolean combineValues,
                                                     Consumer<Map<String, String>> operation, String... propertyNames) {
        Map<String, String> stylesWithRequiredValue = new HashMap<>(propertyNames.length);
        for (String propertyName : propertyNames) {
            stylesWithRequiredValue.put(propertyName, styles.get(propertyName));
        }
        // Commonly useful properties.
        stylesWithRequiredValue.put(CommonCssConstants.FONT_SIZE, styles.get(CommonCssConstants.FONT_SIZE));
        stylesWithRequiredValue.put(CommonCssConstants.COLOR, styles.get(CommonCssConstants.COLOR));

        if (combineValues) {
            UnitValue previousPropertyValue = child.<UnitValue>getProperty(property);
            child.deleteOwnProperty(property);
            operation.accept(stylesWithRequiredValue);
            UnitValue currentPropertyValue = child.<UnitValue>getProperty(property);
            if (previousPropertyValue != null) {
                if (currentPropertyValue == null || previousPropertyValue.isPercentValue()
                        || currentPropertyValue.isPercentValue()) {
                    child.setProperty(property, previousPropertyValue);
                } else {
                    child.setProperty(property, UnitValue.createPointValue(
                            previousPropertyValue.getValue() + currentPropertyValue.getValue()));
                }
            }
        } else {
            operation.accept(stylesWithRequiredValue);
        }
    }
}
