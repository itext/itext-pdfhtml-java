package com.itextpdf.html2pdf.attach.impl.layout;

import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.css.apply.util.FontStyleApplierUtil;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.styledxmlparser.css.CssContextNode;
import com.itextpdf.styledxmlparser.css.util.CssUtils;

/**
 * Container class for grouping necessary values used in dimension calculation
 */
abstract class DimensionContainer {
    float dimension, minDimension, maxDimension;
    float minContentDimension, maxContentDimension;

    DimensionContainer() {
        dimension = -1;
        minDimension = 0;
        minContentDimension = 0;
        maxDimension = Float.MAX_VALUE;
        maxContentDimension = Float.MAX_VALUE;
    }

    /**
     * Check if this dimension is auto
     *
     * @return True if the dimension is to be automatically calculated, false if it was set via a property
     */
    boolean isAutoDimension() {
        return dimension == -1;
    }

    float parseDimension(CssContextNode node, String content, float maxAvailableDimension, float additionalWidthFix) {
        float fontSize = FontStyleApplierUtil.parseAbsoluteFontSize(node.getStyles().get(CssConstants.FONT_SIZE));
        UnitValue unitValue = CssUtils.parseLengthValueToPt(content, fontSize, 0);
        if (unitValue == null) {
            return 0;
        }
        if (unitValue.isPointValue()) {
            return unitValue.getValue() + additionalWidthFix;
        }
        return maxAvailableDimension * unitValue.getValue() / 100f;
    }
}
