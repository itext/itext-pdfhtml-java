/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2025 Apryse Group NV
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
package com.itextpdf.html2pdf.css.apply.util;

import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.commons.utils.MessageFormatUtil;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.properties.AlignContentPropertyValue;
import com.itextpdf.layout.properties.AlignmentPropertyValue;
import com.itextpdf.layout.properties.FlexDirectionPropertyValue;
import com.itextpdf.layout.properties.FlexWrapPropertyValue;
import com.itextpdf.layout.properties.JustifyContent;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import com.itextpdf.styledxmlparser.css.util.CssDimensionParsingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Utilities class to apply flex properties.
 */
final public class FlexApplierUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(FlexApplierUtil.class);

    private FlexApplierUtil() {
    }

    /**
     * Applies properties to a flex item.
     *
     * @param cssProps the map of the CSS properties
     * @param context  the context of the converter processor
     * @param element  the element to set the properties
     */
    public static void applyFlexItemProperties(Map<String, String> cssProps, ProcessorContext context,
            IPropertyContainer element) {
        element.setProperty(Property.COLLAPSING_MARGINS, null);

        logWarningIfThereAreNotSupportedPropertyValues(createSupportedFlexItemPropertiesAndValuesMap(), cssProps);

        final String flexGrow = cssProps.get(CommonCssConstants.FLEX_GROW);
        if (flexGrow != null) {
            final Float flexGrowValue = CssDimensionParsingUtils.parseFloat(flexGrow);
            element.setProperty(Property.FLEX_GROW, flexGrowValue);
        }

        final String flexShrink = cssProps.get(CommonCssConstants.FLEX_SHRINK);
        if (flexShrink != null) {
            final Float flexShrinkValue = CssDimensionParsingUtils.parseFloat(flexShrink);
            element.setProperty(Property.FLEX_SHRINK, flexShrinkValue);
        }

        final String flexBasis = cssProps.get(CommonCssConstants.FLEX_BASIS);
        if (flexBasis != null && !CommonCssConstants.AUTO.equals(flexBasis)) {
            if (!CommonCssConstants.CONTENT.equals(flexBasis)) {
                final float em = CssDimensionParsingUtils.parseAbsoluteLength(cssProps.get(CssConstants.FONT_SIZE));
                final float rem = context.getCssContext().getRootFontSize();
                final UnitValue flexBasisAbsoluteLength = CssDimensionParsingUtils
                        .parseLengthValueToPt(flexBasis, em, rem);
                element.setProperty(Property.FLEX_BASIS, flexBasisAbsoluteLength);
            } else {
                // The case when we don't set the flex-basis property should be identified
                // as flex-basis: content
                LOGGER.warn(MessageFormatUtil.format(Html2PdfLogMessageConstant.FLEX_PROPERTY_IS_NOT_SUPPORTED_YET,
                        CommonCssConstants.FLEX_BASIS, CommonCssConstants.CONTENT));
            }
        }
    }

    /**
     * Applies properties to a flex container.
     *
     * @param cssProps the CSS properties
     * @param element  the element
     */
    public static void applyFlexContainerProperties(Map<String, String> cssProps, IPropertyContainer element) {
        logWarningIfThereAreNotSupportedPropertyValues(createSupportedFlexContainerPropertiesAndValuesMap(), cssProps);
        applyAlignItems(cssProps, element);
        applyJustifyContent(cssProps, element);
        applyAlignContent(cssProps, element);
        applyWrap(cssProps, element);
        applyDirection(cssProps, element);
    }

    private static void applyWrap(Map<String, String> cssProps, IPropertyContainer element) {
        final String wrapString = cssProps.get(CommonCssConstants.FLEX_WRAP);
        if (wrapString != null) {
            FlexWrapPropertyValue wrap;
            switch (wrapString) {
                case CommonCssConstants.WRAP:
                    wrap = FlexWrapPropertyValue.WRAP;
                    break;
                case CommonCssConstants.WRAP_REVERSE:
                    wrap = FlexWrapPropertyValue.WRAP_REVERSE;
                    break;
                case CommonCssConstants.NOWRAP:
                    wrap = FlexWrapPropertyValue.NOWRAP;
                    break;
                default:
                    wrap = FlexWrapPropertyValue.NOWRAP;
                    break;
            }
            element.setProperty(Property.FLEX_WRAP, wrap);
        }
    }

    private static void applyDirection(Map<String, String> cssProps, IPropertyContainer element) {
        final String directionString = cssProps.get(CommonCssConstants.FLEX_DIRECTION);
        if (directionString != null) {
            FlexDirectionPropertyValue direction;
            switch (directionString) {
                case CommonCssConstants.ROW:
                    direction = FlexDirectionPropertyValue.ROW;
                    break;
                case CommonCssConstants.ROW_REVERSE:
                    direction = FlexDirectionPropertyValue.ROW_REVERSE;
                    break;
                case CommonCssConstants.COLUMN:
                    direction = FlexDirectionPropertyValue.COLUMN;
                    break;
                case CommonCssConstants.COLUMN_REVERSE:
                    direction = FlexDirectionPropertyValue.COLUMN_REVERSE;
                    break;
                default:
                    direction = FlexDirectionPropertyValue.ROW;
                    break;
            }
            element.setProperty(Property.FLEX_DIRECTION, direction);
        }
    }

    private static void applyAlignItems(Map<String, String> cssProps, IPropertyContainer element) {
        final String alignItemsString = cssProps.get(CommonCssConstants.ALIGN_ITEMS);
        if (alignItemsString != null) {
            AlignmentPropertyValue alignItems;
            switch (alignItemsString) {
                case CommonCssConstants.NORMAL:
                    alignItems = AlignmentPropertyValue.NORMAL;
                    break;
                case CommonCssConstants.START:
                    alignItems = AlignmentPropertyValue.START;
                    break;
                case CommonCssConstants.END:
                    alignItems = AlignmentPropertyValue.END;
                    break;
                case CommonCssConstants.FLEX_START:
                    alignItems = AlignmentPropertyValue.FLEX_START;
                    break;
                case CommonCssConstants.FLEX_END:
                    alignItems = AlignmentPropertyValue.FLEX_END;
                    break;
                case CommonCssConstants.CENTER:
                    alignItems = AlignmentPropertyValue.CENTER;
                    break;
                case CommonCssConstants.SELF_START:
                    alignItems = AlignmentPropertyValue.SELF_START;
                    break;
                case CommonCssConstants.SELF_END:
                    alignItems = AlignmentPropertyValue.SELF_END;
                    break;
                case CommonCssConstants.STRETCH:
                    alignItems = AlignmentPropertyValue.STRETCH;
                    break;
                default:
                    LOGGER.warn(MessageFormatUtil.format(Html2PdfLogMessageConstant.FLEX_PROPERTY_IS_NOT_SUPPORTED_YET,
                            CommonCssConstants.ALIGN_ITEMS, alignItemsString));
                    alignItems = AlignmentPropertyValue.STRETCH;
                    break;
            }
            element.setProperty(Property.ALIGN_ITEMS, alignItems);
        }
    }

    private static void applyJustifyContent(Map<String, String> cssProps, IPropertyContainer element) {
        final String justifyContentString = cssProps.get(CommonCssConstants.JUSTIFY_CONTENT);
        if (justifyContentString != null) {
            JustifyContent justifyContent;
            switch (justifyContentString) {
                case CommonCssConstants.NORMAL:
                    justifyContent = JustifyContent.NORMAL;
                    break;
                case CommonCssConstants.START:
                    justifyContent = JustifyContent.START;
                    break;
                case CommonCssConstants.END:
                    justifyContent = JustifyContent.END;
                    break;
                case CommonCssConstants.FLEX_END:
                    justifyContent = JustifyContent.FLEX_END;
                    break;
                case CommonCssConstants.SELF_START:
                    justifyContent = JustifyContent.SELF_START;
                    break;
                case CommonCssConstants.SELF_END:
                    justifyContent = JustifyContent.SELF_END;
                    break;
                case CommonCssConstants.LEFT:
                    justifyContent = JustifyContent.LEFT;
                    break;
                case CommonCssConstants.RIGHT:
                    justifyContent = JustifyContent.RIGHT;
                    break;
                case CommonCssConstants.CENTER:
                    justifyContent = JustifyContent.CENTER;
                    break;
                case CommonCssConstants.STRETCH:
                    justifyContent = JustifyContent.STRETCH;
                    break;
                case CommonCssConstants.FLEX_START:
                    justifyContent = JustifyContent.FLEX_START;
                    break;
                default:
                    LOGGER.warn(MessageFormatUtil.format(Html2PdfLogMessageConstant.FLEX_PROPERTY_IS_NOT_SUPPORTED_YET,
                        CommonCssConstants.JUSTIFY_CONTENT, justifyContentString));
                    justifyContent = JustifyContent.FLEX_START;
                    break;
            }
            element.setProperty(Property.JUSTIFY_CONTENT, justifyContent);
        }
    }

    private static void applyAlignContent(Map<String, String> cssProps, IPropertyContainer element) {
        final String alignContentString = cssProps.get(CommonCssConstants.ALIGN_CONTENT);
        if (alignContentString != null) {
            AlignContentPropertyValue alignContent;
            switch (alignContentString) {
                case CommonCssConstants.FLEX_START:
                    alignContent = AlignContentPropertyValue.FLEX_START;
                    break;
                case CommonCssConstants.FLEX_END:
                    alignContent = AlignContentPropertyValue.FLEX_END;
                    break;
                case CommonCssConstants.CENTER:
                    alignContent = AlignContentPropertyValue.CENTER;
                    break;
                case CommonCssConstants.SPACE_BETWEEN:
                    alignContent = AlignContentPropertyValue.SPACE_BETWEEN;
                    break;
                case CommonCssConstants.SPACE_AROUND:
                    alignContent = AlignContentPropertyValue.SPACE_AROUND;
                    break;
                case CommonCssConstants.SPACE_EVENLY:
                    alignContent = AlignContentPropertyValue.SPACE_EVENLY;
                    break;
                case CommonCssConstants.STRETCH:
                    alignContent = AlignContentPropertyValue.STRETCH;
                    break;
                default:
                    alignContent = AlignContentPropertyValue.NORMAL;
                    break;
            }
            element.setProperty(Property.ALIGN_CONTENT, alignContent);
        }
    }

    private static void logWarningIfThereAreNotSupportedPropertyValues(Map<String, Set<String>> supportedPairs,
                                                                       Map<String, String> cssProps) {
        for (Map.Entry<String, Set<String>> entry : supportedPairs.entrySet()) {
            String supportedPair = entry.getKey();
            Set<String> supportedValues = entry.getValue();
            String propertyValue = cssProps.get(supportedPair);
            if (propertyValue != null && !supportedValues.contains(propertyValue)) {
                LOGGER.warn(MessageFormatUtil.format(
                        Html2PdfLogMessageConstant.FLEX_PROPERTY_IS_NOT_SUPPORTED_YET, supportedPair, propertyValue));
            }
        }
    }

    private static Map<String, Set<String>> createSupportedFlexItemPropertiesAndValuesMap() {
        final Map<String, Set<String>> supportedPairs = new HashMap<>();

        final Set<String> supportedAlignSelfValues = new HashSet<>();
        supportedAlignSelfValues.add(CommonCssConstants.AUTO);

        supportedPairs.put(CommonCssConstants.ALIGN_SELF, supportedAlignSelfValues);

        final Set<String> supportedOrderValues = new HashSet<>();

        supportedPairs.put(CommonCssConstants.ORDER, supportedOrderValues);

        return supportedPairs;
    }

    private static Map<String, Set<String>> createSupportedFlexContainerPropertiesAndValuesMap() {
        final Map<String, Set<String>> supportedPairs = new HashMap<>();

        final Set<String> supportedFlexDirectionValues = new HashSet<>();
        supportedFlexDirectionValues.add(CommonCssConstants.ROW);
        supportedFlexDirectionValues.add(CommonCssConstants.ROW_REVERSE);

        supportedFlexDirectionValues.add(CommonCssConstants.COLUMN);
        supportedFlexDirectionValues.add(CommonCssConstants.COLUMN_REVERSE);

        supportedPairs.put(CommonCssConstants.FLEX_DIRECTION, supportedFlexDirectionValues);

        final Set<String> supportedAlignContentValues = new HashSet<>();
        supportedAlignContentValues.add(CommonCssConstants.STRETCH);
        supportedAlignContentValues.add(CommonCssConstants.NORMAL);
        supportedAlignContentValues.add(CommonCssConstants.FLEX_START);
        supportedAlignContentValues.add(CommonCssConstants.FLEX_END);
        supportedAlignContentValues.add(CommonCssConstants.CENTER);
        supportedAlignContentValues.add(CommonCssConstants.SPACE_AROUND);
        supportedAlignContentValues.add(CommonCssConstants.SPACE_BETWEEN);
        supportedAlignContentValues.add(CommonCssConstants.SPACE_EVENLY);

        supportedPairs.put(CommonCssConstants.ALIGN_CONTENT, supportedAlignContentValues);

        final Set<String> supportedRowGapValues = new HashSet<>();
        supportedRowGapValues.add(CommonCssConstants.NORMAL);

        supportedPairs.put(CommonCssConstants.ROW_GAP, supportedRowGapValues);

        final Set<String> supportedColumnGapValues = new HashSet<>();
        supportedColumnGapValues.add(CommonCssConstants.NORMAL);

        supportedPairs.put(CommonCssConstants.COLUMN_GAP, supportedColumnGapValues);

        return supportedPairs;
    }
}

