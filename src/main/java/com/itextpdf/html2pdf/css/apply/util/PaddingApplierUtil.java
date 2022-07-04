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
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.styledxmlparser.css.util.CssDimensionParsingUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Utilities class to apply a padding.
 */
public final class PaddingApplierUtil {

    /** The logger. */
    private static final Logger logger = LoggerFactory.getLogger(PaddingApplierUtil.class);

    /**
     * Creates a new {@link PaddingApplierUtil} instance.
     */
    private PaddingApplierUtil() {
    }

    /**
     * Applies paddings to an element.
     *
     * @param cssProps the CSS properties
     * @param context the processor context
     * @param element the element
     */
    public static void applyPaddings(Map<String, String> cssProps, ProcessorContext context, IPropertyContainer element) {
        applyPaddings(cssProps, context, element, 0.0f, 0.0f);
    }

    /**
     * Applies paddings to an element.
     *
     * @param cssProps the CSS properties
     * @param context the processor context
     * @param element the element
     * @param baseValueHorizontal value used by default for horizontal dimension
     * @param baseValueVertical value used by default for vertical dimension
     */
    public static void applyPaddings(Map<String, String> cssProps, ProcessorContext context, IPropertyContainer element, float baseValueVertical, float baseValueHorizontal) {

        float em = CssDimensionParsingUtils.parseAbsoluteLength(cssProps.get(CssConstants.FONT_SIZE));
        float rem = context.getCssContext().getRootFontSize();
        applyPaddings(cssProps, CssConstants.PADDING_TOP, em, rem, Property.PADDING_TOP, element, baseValueVertical);
        applyPaddings(cssProps, CssConstants.PADDING_BOTTOM, em, rem, Property.PADDING_BOTTOM, element, baseValueVertical);
        applyPaddings(cssProps, CssConstants.PADDING_LEFT, em, rem, Property.PADDING_LEFT, element, baseValueHorizontal);
        applyPaddings(cssProps, CssConstants.PADDING_RIGHT, em, rem, Property.PADDING_RIGHT, element, baseValueHorizontal);
    }

    private static void applyPaddings(Map<String, String> cssProps, String cssConstants,
                                     float em, float rem, int paddingProperty,
                                     IPropertyContainer element, float baseValue) {
        String paddingPropertyValue = cssProps.get(cssConstants);
        UnitValue paddingVal = CssDimensionParsingUtils.parseLengthValueToPt(paddingPropertyValue, em, rem);
        if (paddingVal != null) {
            if (paddingVal.isPointValue()) {
                element.setProperty(paddingProperty, paddingVal);
            } else {
                if (baseValue != 0.0f)
                    element.setProperty(paddingProperty, new UnitValue(UnitValue.POINT, baseValue * paddingVal.getValue() * 0.01f));
                else
                    logger.error(Html2PdfLogMessageConstant.PADDING_VALUE_IN_PERCENT_NOT_SUPPORTED);
            }
        }
    }

}
