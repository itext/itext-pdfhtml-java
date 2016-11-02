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
import com.itextpdf.html2pdf.css.util.CssUtils;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.property.Property;
import com.itextpdf.layout.property.UnitValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public final class WidthHeightApplierUtil {

    private static final Logger logger = LoggerFactory.getLogger(WidthHeightApplierUtil.class);
    private static final String HEIGHT_VALUE_IN_PERCENT_NOT_SUPPORTED = "Height value in percent not supported";

    private WidthHeightApplierUtil() {
    }

    public static void applyWidthHeight(Map<String, String> cssProps, ProcessorContext context, IPropertyContainer element) {
        float em = CssUtils.parseAbsoluteLength(cssProps.get(CssConstants.FONT_SIZE));
        String widthVal = cssProps.get(CssConstants.WIDTH);
        if (widthVal != null) {
            UnitValue width = CssUtils.parseLengthValueToPt(widthVal, em);
            element.setProperty(Property.WIDTH, width);
            if (element instanceof Image) {
                ((Image) element).setAutoScale(false);
            }
        }
        String heightVal = cssProps.get(CssConstants.HEIGHT);
        if (heightVal != null) {
            UnitValue height = CssUtils.parseLengthValueToPt(heightVal, em);
            if (height.isPointValue()) {
                element.setProperty(Property.HEIGHT, height);
                if (element instanceof Image) {
                    ((Image) element).setAutoScale(false);
                }
            } else {
                logger.error(HEIGHT_VALUE_IN_PERCENT_NOT_SUPPORTED);
            }
        }

    }

}
