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
package com.itextpdf.html2pdf.attach.impl.layout;

import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.styledxmlparser.css.CssContextNode;
import com.itextpdf.styledxmlparser.css.util.CssDimensionParsingUtils;

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
        float fontSize = CssDimensionParsingUtils.parseAbsoluteFontSize(node.getStyles().get(CssConstants.FONT_SIZE));
        UnitValue unitValue = CssDimensionParsingUtils.parseLengthValueToPt(content, fontSize, 0);
        if (unitValue == null) {
            return 0;
        }
        if (unitValue.isPointValue()) {
            return unitValue.getValue() + additionalWidthFix;
        }
        return maxAvailableDimension * unitValue.getValue() / 100f;
    }
}
