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

import com.itextpdf.layout.property.Property;

import java.util.Map;

import static java.lang.Math.toRadians;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.tan;

public class TransformationApplierUtil {

    /**
     * Creates a new <code>TransformationApplierUtil</code> instance.
     */
    private TransformationApplierUtil() {
    }

    /**
     * Applies a transformation to an element.
     *
     * @param cssProps the CSS properties
     * @param context  the properties context
     * @param element  the element
     */
    public static void applyTransformation(Map<String, String> cssProps, ProcessorContext context, IPropertyContainer element) {
        String tranformationFunction, function, args;
        if (cssProps.get(CssConstants.TRANSFORM) != null)
            tranformationFunction = cssProps.get(CssConstants.TRANSFORM).toLowerCase();
        else
            tranformationFunction = "none";
        if (!CssConstants.NONE.equals(tranformationFunction)) {
            function = tranformationFunction.substring(0, tranformationFunction.indexOf('('));
            args = tranformationFunction.substring(tranformationFunction.indexOf('(') + 1, tranformationFunction.length() - 1);
        } else {
            function = tranformationFunction;
            args = "0";
        }
        if (CssConstants.MATRIX.equals(function)) {
            String[] arg = args.split(",");
            if (arg.length == 6) {
                float[] matrix = new float[6];
                int i = 0;
                for (; i < 6; i++) {
                    if (i == 4 || i == 5)
                        matrix[i] = CssUtils.parseAbsoluteLength(arg[i].trim());
                    else
                        matrix[i] = Float.parseFloat(arg[i].trim());
                    if (i == 1 || i == 2 || i == 5)
                        matrix[i] *= -1;
                }
                element.setProperty(Property.TRANSFORM, matrix);
            }
        } else if (CssConstants.TRANSLATE.equals(function)) {
            String[] arg = args.split(",");
            float x = 0, y = 0;
            if (arg.length == 2) {
                x = CssUtils.parseAbsoluteLength(arg[0].trim());
                y = -1 * CssUtils.parseAbsoluteLength(arg[1].trim());
            } else if (arg.length == 1) {
                x = CssUtils.parseAbsoluteLength(arg[0].trim());
            }
            element.setProperty(Property.TRANSFORM, new float[]{1, 0, 0, 1, x, y});
        } else if (CssConstants.TRANSLATE_X.equals(function)) {
            float x = CssUtils.parseAbsoluteLength(args.trim());
            element.setProperty(Property.TRANSFORM, new float[]{1, 0, 0, 1, x, 0});
        } else if (CssConstants.TRANSLATE_Y.equals(function)) {
            float y = -1 * CssUtils.parseAbsoluteLength(args.trim());
            element.setProperty(Property.TRANSFORM, new float[]{1, 0, 0, 1, 0, y});
        } else if (CssConstants.ROTATE.equals(function)) {
            float cos = (float) cos(toRadians(-1 * Double.parseDouble(args.substring(0, args.indexOf('d')))));
            float sin = (float) sin(toRadians(-1 * Double.parseDouble(args.substring(0, args.indexOf('d')))));
            element.setProperty(Property.TRANSFORM, new float[]{cos, sin, -1 * sin, cos, 0, 0});
        } else if (CssConstants.SKEW.equals(function)) {
            String[] arg = args.split(",");
            float x = 0, y = 0;
            int i1 = arg[0].indexOf('d');
            if (arg.length == 2) {
                int i2 = arg[1].indexOf('d');
                x = -1 * Float.parseFloat(arg[0].trim().substring(0, i1));
                y = -1 * Float.parseFloat(arg[1].trim().substring(0, i2));
            } else
                x = -1 * Float.parseFloat(arg[0].trim().substring(0, i1));
            float tanX = (float) tan(toRadians(x));
            float tanY = (float) tan(toRadians(y));
            element.setProperty(Property.TRANSFORM, new float[]{1, tanY, tanX, 1, 0, 0});
        } else if (CssConstants.SKEW_X.equals(function)) {
            float x = -1 * Float.parseFloat(args.trim().substring(0, args.indexOf('d')));
            float tanX = (float) tan(toRadians(x));
            element.setProperty(Property.TRANSFORM, new float[]{1, 0, tanX, 1, 0, 0});
        } else if (CssConstants.SKEW_Y.equals(function)) {
            float y = -1 * Float.parseFloat(args.trim().substring(0, args.indexOf('d')));
            float tanY = (float) tan(toRadians(y));
            element.setProperty(Property.TRANSFORM, new float[]{1, tanY, 0, 1, 0, 0});
        } else if (CssConstants.SCALE.equals(function)) {
            String[] arg = args.split(",");
            float x, y;
            if (arg.length == 2) {
                x = Float.parseFloat(arg[0].trim());
                y = Float.parseFloat(arg[1].trim());
            } else {
                x = Float.parseFloat(arg[0].trim());
                y = x;
            }
            element.setProperty(Property.TRANSFORM, new float[]{x, 0, 0, y, 0, 0});
        } else if (CssConstants.SCALE_X.equals(function)) {
            float x = Float.parseFloat(args.trim());
            element.setProperty(Property.TRANSFORM, new float[]{x, 0, 0, 1, 0, 0});
        } else if (CssConstants.SCALE_Y.equals(function)) {
            float y = Float.parseFloat(args.trim());
            element.setProperty(Property.TRANSFORM, new float[]{1, 0, 0, y, 0, 0});
        }
    }
}
