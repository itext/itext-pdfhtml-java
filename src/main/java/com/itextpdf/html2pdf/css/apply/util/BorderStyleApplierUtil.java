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

import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.css.resolve.CssDefaults;
import com.itextpdf.html2pdf.css.util.CssUtils;
import com.itextpdf.kernel.color.WebColors;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.border.*;
import com.itextpdf.layout.property.Property;
import com.itextpdf.layout.property.UnitValue;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BorderStyleApplierUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(BorderStyleApplierUtil.class);

    private BorderStyleApplierUtil(){
    }

    public static void applyBorders(Map<String, String> cssProps, ProcessorContext context, IPropertyContainer element) {
        float em =  CssUtils.parseAbsoluteLength(cssProps.get(CssConstants.FONT_SIZE));
        Border topBorder = getCertainBorder(cssProps.get(CssConstants.BORDER_TOP_WIDTH),
                cssProps.get(CssConstants.BORDER_TOP_STYLE), cssProps.get(CssConstants.BORDER_TOP_COLOR), em);
        if (topBorder != null) {
            element.setProperty(Property.BORDER_TOP, topBorder);
        }
        Border bottomBorder = getCertainBorder(cssProps.get(CssConstants.BORDER_BOTTOM_WIDTH),
                cssProps.get(CssConstants.BORDER_BOTTOM_STYLE), cssProps.get(CssConstants.BORDER_BOTTOM_COLOR), em);
        if (bottomBorder != null) {
            element.setProperty(Property.BORDER_BOTTOM, bottomBorder);
        }
        Border leftBorder = getCertainBorder(cssProps.get(CssConstants.BORDER_LEFT_WIDTH),
                cssProps.get(CssConstants.BORDER_LEFT_STYLE), cssProps.get(CssConstants.BORDER_LEFT_COLOR), em);
        if (leftBorder != null) {
            element.setProperty(Property.BORDER_LEFT, leftBorder);
        }
        Border rightBorder = getCertainBorder(cssProps.get(CssConstants.BORDER_RIGHT_WIDTH),
                cssProps.get(CssConstants.BORDER_RIGHT_STYLE), cssProps.get(CssConstants.BORDER_RIGHT_COLOR), em);
        if (rightBorder != null) {
            element.setProperty(Property.BORDER_RIGHT, rightBorder);
        }
    }

    public static Border getCertainBorder(String borderWidth, String borderStyle, String borderColor, float em) {
        if (borderStyle == null || CssConstants.NONE.equals(borderStyle)) {
            return null;
        }

        Border border = null;
        if (borderWidth == null) {
            borderWidth = CssDefaults.getDefaultValue(CssConstants.BORDER_WIDTH);
        }

        float borderWidthValue;
        if (CssConstants.BORDER_WIDTH_VALUES.contains(borderWidth)) {
            if (CssConstants.THIN.equals(borderWidth)) {
                borderWidth = "1px";
            } else if (CssConstants.MEDIUM.equals(borderWidth)) {
                borderWidth = "2px";
            } else if (CssConstants.THICK.equals(borderWidth)) {
                borderWidth = "3px";
            }
        }

        UnitValue unitValue = CssUtils.parseLengthValueToPt(borderWidth, em);
        if (unitValue.isPercentValue()) {
            LOGGER.error("border-width in percents is not supported");
            return null;
        }

        borderWidthValue = unitValue.getValue();
        if (borderWidthValue > 0) {
            switch (borderStyle.toLowerCase()) {
                case CssConstants.SOLID:
                    border = new SolidBorder(borderWidthValue);
                    break;
                case CssConstants.DASHED:
                    border = new DashedBorder(borderWidthValue);
                    break;
                case CssConstants.DOTTED:
                    border = new DottedBorder(borderWidthValue);
                    break;
                case CssConstants.DOUBLE:
                    border = new DoubleBorder(borderWidthValue);
                    break;
                case CssConstants.GROOVE:
                    border = new GrooveBorder(borderWidthValue);
                    break;
                case CssConstants.RIDGE:
                    border = new RidgeBorder(borderWidthValue);
                    break;
                case CssConstants.INSET:
                    border = new InsetBorder(borderWidthValue);
                    break;
                case CssConstants.OUTSET:
                    border = new OutsetBorder(borderWidthValue);
                    break;
                default:
                    border = null;
                    break;
            }
            if (border != null && borderColor != null) {
                border.setColor(WebColors.getRGBColor(borderColor));
            }
        }
        return border;
    }
}
