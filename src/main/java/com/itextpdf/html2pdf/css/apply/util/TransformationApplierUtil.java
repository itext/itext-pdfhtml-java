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
import com.itextpdf.layout.property.Transform;
import com.itextpdf.layout.property.UnitValue;

import java.util.Map;

import static java.lang.Math.toRadians;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.tan;

public class TransformationApplierUtil {

    /**
     * Creates a new {@link TransformationApplierUtil} instance.
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
        String transformationFunction;
        if (cssProps.get(CssConstants.TRANSFORM) != null)
            transformationFunction = cssProps.get(CssConstants.TRANSFORM).toLowerCase();
        else
            return;
        String[] components = transformationFunction.split("\\)");
        Transform multipleFunction = new Transform(components.length);
        for (String component : components) {
            multipleFunction.addSingleTransform(parseSingleFunction(component));
        }
        element.setProperty(Property.TRANSFORM, multipleFunction);
    }

    private static Transform.SingleTransform parseSingleFunction(String transformationFunction) {
        String function, args;
        if (!CssConstants.NONE.equals(transformationFunction)) {
            function = transformationFunction.substring(0, transformationFunction.indexOf('(')).trim();
            args = transformationFunction.substring(transformationFunction.indexOf('(') + 1);
        } else {
            return getSingleTransform(1, 0, 0, 1, 0, 0);
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
                return getSingleTransform(matrix);
            }
        }
        if (CssConstants.TRANSLATE.equals(function)) {
            String[] arg = args.split(",");
            boolean xPoint, yPoint = true;
            float x, y = 0;
            xPoint = arg[0].indexOf('%') < 0;
            x = xPoint ? CssUtils.parseAbsoluteLength(arg[0].trim()) : Float.parseFloat(arg[0].trim().substring(0, arg[0].indexOf('%')));
            if (arg.length == 2) {
                yPoint = arg[1].indexOf('%') < 0;
                y = -1 * (yPoint ? CssUtils.parseAbsoluteLength(arg[1].trim()) : Float.parseFloat(arg[1].trim().substring(0, arg[1].indexOf('%'))));
            }
            return getSingleTransformTranslate(1, 0, 0, 1, x, y, xPoint, yPoint);
        }
        if (CssConstants.TRANSLATE_X.equals(function)) {
            boolean xPoint = args.indexOf('%') < 0;
            float x = xPoint ? CssUtils.parseAbsoluteLength(args.trim()) : Float.parseFloat(args.trim().substring(0, args.indexOf('%')));
            ;
            return getSingleTransformTranslate(1, 0, 0, 1, x, 0, xPoint, true);
        }
        if (CssConstants.TRANSLATE_Y.equals(function)) {
            boolean yPoint = args.indexOf('%') < 0;
            float y = -1 * (yPoint ? CssUtils.parseAbsoluteLength(args.trim()) : Float.parseFloat(args.trim().substring(0, args.indexOf('%'))));
            return getSingleTransformTranslate(1, 0, 0, 1, 0, y, true, yPoint);
        }
        if (CssConstants.ROTATE.equals(function)) {
            double angleInRad = parseAngleToRadians(args);
            float cos = (float) cos(angleInRad);
            float sin = (float) sin(angleInRad);
            return getSingleTransform(cos, sin, -1 * sin, cos, 0, 0);
        }
        if (CssConstants.SKEW.equals(function)) {
            String[] arg = args.split(",");
            double xAngleInRad = parseAngleToRadians(arg[0]),
                    yAngleInRad = arg.length == 2 ? parseAngleToRadians(arg[1]) : 0.0;
            float tanX = (float) tan(xAngleInRad);
            float tanY = (float) tan(yAngleInRad);
            return getSingleTransform(1, tanY, tanX, 1, 0, 0);
        }
        if (CssConstants.SKEW_X.equals(function)) {
            float tanX = (float) tan(parseAngleToRadians(args));
            return getSingleTransform(1, 0, tanX, 1, 0, 0);
        }
        if (CssConstants.SKEW_Y.equals(function)) {
            float tanY = (float) tan(parseAngleToRadians(args));
            return getSingleTransform(1, tanY, 0, 1, 0, 0);
        }
        if (CssConstants.SCALE.equals(function)) {
            String[] arg = args.split(",");
            float x, y;
            if (arg.length == 2) {
                x = Float.parseFloat(arg[0].trim());
                y = Float.parseFloat(arg[1].trim());
            } else {
                x = Float.parseFloat(arg[0].trim());
                y = x;
            }
            return getSingleTransform(x, 0, 0, y, 0, 0);
        }
        if (CssConstants.SCALE_X.equals(function)) {
            float x = Float.parseFloat(args.trim());
            return getSingleTransform(x, 0, 0, 1, 0, 0);
        }
        if (CssConstants.SCALE_Y.equals(function)) {
            float y = Float.parseFloat(args.trim());
            return getSingleTransform(1, 0, 0, y, 0, 0);
        }
        return new Transform.SingleTransform();
    }

    /**
     * Convert an angle (presented as radians or degrees) to radians
     *
     * @param value the angle (as a CSS string)
     */
    private static double parseAngleToRadians(String value) {
        if (value.indexOf('d') < 0)
            return 0.0;
        if (value.indexOf('r') > 0)
            return -1 * Double.parseDouble(value.trim().substring(0, value.indexOf('r')));
        return toRadians(-1 * Double.parseDouble(value.trim().substring(0, value.indexOf('d'))));
    }

    /**
     * Apply a linear transformation, using a transformation matrix
     *
     * @param a      element [0,0] of the transformation matrix
     * @param b      element [0,1] of the transformation matrix
     * @param c      element [1,0] of the transformation matrix
     * @param d      element [1,1] of the transformation matrix
     * @param tx     translation on x-axis
     * @param ty     translation on y-axis
     */
    private static Transform.SingleTransform getSingleTransformTranslate(float a, float b, float c, float d, float tx, float ty, boolean xPoint, boolean yPoint) {
        return new Transform.SingleTransform(a, b, c, d,
                new UnitValue(xPoint ? UnitValue.POINT : UnitValue.PERCENT, tx), new UnitValue(yPoint ? UnitValue.POINT : UnitValue.PERCENT, ty));
    }

    /**
     * Apply a linear transformation using a transformation matrix
     *
     * @param a  element [0,0] of the transformation matrix
     * @param b  element [0,1] of the transformation matrix
     * @param c  element [1,0] of the transformation matrix
     * @param d  element [1,1] of the transformation matrix
     * @param tx translation on x-axis
     * @param ty translation on y-axis
     */
    private static Transform.SingleTransform getSingleTransform(float a, float b, float c, float d, float tx, float ty) {
        return new Transform.SingleTransform(a, b, c, d,
                new UnitValue(UnitValue.POINT, tx), new UnitValue(UnitValue.POINT, ty));
    }

    /**
     * Apply a linear transformation using a transformation matrix
     *
     * @param floats the transformation matrix (flattened) as array
     */
    private static Transform.SingleTransform getSingleTransform(float floats[]) {
        return new Transform.SingleTransform(floats[0], floats[1], floats[2], floats[3],
                new UnitValue(UnitValue.POINT, floats[4]), new UnitValue(UnitValue.POINT, floats[5]));
    }
}
