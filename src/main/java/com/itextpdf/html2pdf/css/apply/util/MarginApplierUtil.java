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
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.Property;
import com.itextpdf.layout.property.UnitValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Utilities class to apply margins.
 */
public final class MarginApplierUtil {

    /** The logger. */
    private static final Logger logger = LoggerFactory.getLogger(MarginApplierUtil.class);

    /**
     * Creates a {@link MarginApplierUtil} instance.
     */
    private MarginApplierUtil() {
    }

    /**
     * Applies margins to an element.
     *
     * @param cssProps the CSS properties
     * @param context the processor context
     * @param element the element
     */
    public static void applyMargins(Map<String, String> cssProps, ProcessorContext context, IPropertyContainer element) {
        String marginTop = cssProps.get(CssConstants.MARGIN_TOP);
        String marginBottom = cssProps.get(CssConstants.MARGIN_BOTTOM);
        String marginLeft = cssProps.get(CssConstants.MARGIN_LEFT);
        String marginRight = cssProps.get(CssConstants.MARGIN_RIGHT);

        // The check for display is useful at least for images
        boolean isBlock = element instanceof IBlockElement || CssConstants.BLOCK.equals(cssProps.get(CssConstants.DISPLAY));
        boolean isImage = element instanceof Image;
        
        float em = CssUtils.parseAbsoluteLength(cssProps.get(CssConstants.FONT_SIZE));
        float rem = context.getCssContext().getRootFontSize();

        if (isBlock || isImage) {
            trySetMarginIfNotAuto(Property.MARGIN_TOP, marginTop, element, em, rem);
            trySetMarginIfNotAuto(Property.MARGIN_BOTTOM, marginBottom, element, em, rem);
        }

        boolean isLeftAuto = !trySetMarginIfNotAuto(Property.MARGIN_LEFT, marginLeft, element, em, rem);
        boolean isRightAuto = !trySetMarginIfNotAuto(Property.MARGIN_RIGHT, marginRight, element, em, rem);

        if (isBlock) {
            if (isLeftAuto && isRightAuto) {
                element.setProperty(Property.HORIZONTAL_ALIGNMENT, HorizontalAlignment.CENTER);
            } else if (isLeftAuto) {
                element.setProperty(Property.HORIZONTAL_ALIGNMENT, HorizontalAlignment.RIGHT);
            } else if (isRightAuto) {
                element.setProperty(Property.HORIZONTAL_ALIGNMENT, HorizontalAlignment.LEFT);
            }
        }

    }

    /**
     * Tries set margin if the value isn't "auto".
     *
     * @param marginProperty the margin property
     * @param marginValue the margin value
     * @param element the element
     * @param em the em value
     * @param rem the root em value
     * @return false if the margin value was "auto"
     */
    private static boolean trySetMarginIfNotAuto(int marginProperty, String marginValue, IPropertyContainer element, float em, float rem) {
        boolean isAuto = CssConstants.AUTO.equals(marginValue);
        if (isAuto) {
            return false;
        }
        
        Float marginVal = parseMarginValue(marginValue, em, rem);
        if (marginVal != null) {
            element.setProperty(marginProperty, UnitValue.createPointValue((float) marginVal));
        }
        return true;
    }

    /**
     * Parses the margin value.
     *
     * @param marginValString the margin value as a {@link String}
     * @param em the em value
     * @param rem the root em value
     * @return the margin value as a {@link Float}
     */
    private static Float parseMarginValue(String marginValString, float em, float rem) {
        UnitValue marginUnitVal = CssUtils.parseLengthValueToPt(marginValString, em, rem);
        if (marginUnitVal != null) {
            if (!marginUnitVal.isPointValue()) {
                logger.error(LogMessageConstant.MARGIN_VALUE_IN_PERCENT_NOT_SUPPORTED);
                return null;
            }

            return marginUnitVal.getValue();
        } else {
            return null;
        }
    }

}
