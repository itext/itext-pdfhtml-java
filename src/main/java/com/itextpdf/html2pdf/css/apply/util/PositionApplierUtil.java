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
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.layout.LayoutPosition;
import com.itextpdf.layout.property.Property;
import com.itextpdf.layout.property.UnitValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.Map;

public final class PositionApplierUtil {

    private static final Logger logger = LoggerFactory.getLogger(PositionApplierUtil.class);

    private PositionApplierUtil() {
    }

    public static void applyPosition(Map<String, String> cssProps, ProcessorContext context, IPropertyContainer element) {
        String position = cssProps.get(CssConstants.POSITION);
        if (CssConstants.ABSOLUTE.equals(position)) {
            element.setProperty(Property.POSITION, LayoutPosition.ABSOLUTE);
            applyLeftRightTopBottom(cssProps, context, element);
        } else if (CssConstants.RELATIVE.equals(position)) {
            element.setProperty(Property.POSITION, LayoutPosition.RELATIVE);
            applyLeftRightTopBottom(cssProps, context, element);
        }
    }

    private static void applyLeftRightTopBottom(Map<String, String> cssProps, ProcessorContext context, IPropertyContainer element) {
        float em = CssUtils.parseAbsoluteLength(cssProps.get(CssConstants.FONT_SIZE));
        String left = cssProps.get(CssConstants.LEFT);
        String right = cssProps.get(CssConstants.RIGHT);
        String top = cssProps.get(CssConstants.TOP);
        String bottom = cssProps.get(CssConstants.BOTTOM);

        UnitValue leftVal = CssUtils.parseLengthValueToPt(left, em);
        UnitValue rightVal = CssUtils.parseLengthValueToPt(right, em);
        UnitValue topVal = CssUtils.parseLengthValueToPt(top, em);
        UnitValue bottomVal = CssUtils.parseLengthValueToPt(bottom, em);

        if (leftVal != null) {
            if (leftVal.isPointValue()) {
                element.setProperty(Property.LEFT, leftVal.getValue());
            } else {
                logger.error(MessageFormat.format(LogMessageConstant.CSS_PROPERTY_IN_PERCENTS_NOT_SUPPORTED, CssConstants.LEFT));
            }
        }

        if (rightVal != null) {
            if (rightVal.isPointValue()) {
                element.setProperty(Property.RIGHT, rightVal.getValue());
            } else {
                logger.error(MessageFormat.format(LogMessageConstant.CSS_PROPERTY_IN_PERCENTS_NOT_SUPPORTED, CssConstants.RIGHT));
            }
        }

        if (topVal != null) {
            if (topVal.isPointValue()) {
                element.setProperty(Property.TOP, topVal.getValue());
            } else {
                logger.error(MessageFormat.format(LogMessageConstant.CSS_PROPERTY_IN_PERCENTS_NOT_SUPPORTED, CssConstants.TOP));
            }
        }

        if (bottomVal != null) {
            if (bottomVal.isPointValue()) {
                element.setProperty(Property.BOTTOM, bottomVal.getValue());
            } else {
                logger.error(MessageFormat.format(LogMessageConstant.CSS_PROPERTY_IN_PERCENTS_NOT_SUPPORTED, CssConstants.BOTTOM));
            }
        }
    }

}
