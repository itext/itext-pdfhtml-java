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
import java.util.List;
import java.util.ArrayList;

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
        String transformationFunction;
        if (cssProps.get(CssConstants.TRANSFORM) != null)
            transformationFunction = cssProps.get(CssConstants.TRANSFORM).toLowerCase();
        else
            return;
        String[] components = transformationFunction.split("\\)");
        List<String[]> multipleFunction = new ArrayList<>(components.length);
        for (String component : components) {
            multipleFunction.add(parseSingleFunction(component));
        }
        element.setProperty(Property.TRANSFORM, multipleFunction);
    }

    private static String[] parseSingleFunction(String transformationFunction) {
        String function, args;
        if (!CssConstants.NONE.equals(transformationFunction)) {
            function = transformationFunction.substring(0, transformationFunction.indexOf('(')).trim();
            args = transformationFunction.substring(transformationFunction.indexOf('(') + 1);
        } else {
            return floatArrayToStringArray(new float[]{1, 0, 0, 1, 0, 0});
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
                return floatArrayToStringArray(matrix);
            }
        } else if (CssConstants.TRANSLATE.equals(function)) {
            String[] arg = args.split(",");
            String xStr = null, yStr = null;
            float x = 0, y = 0;
            if (arg[0].indexOf('%') > 0)
                xStr = arg[0];
            else
                x = CssUtils.parseAbsoluteLength(arg[0].trim());
            if (arg.length == 2) {
                if (arg[1].indexOf('%') > 0)
                    yStr = Float.toString(-1 * Float.parseFloat(arg[1].substring(0, arg[1].indexOf('%')))) + '%';
                else
                    y = -1 * CssUtils.parseAbsoluteLength(arg[1].trim());
            }
            String[] transform = floatArrayToStringArray(new float[]{1, 0, 0, 1, x, y});
            if (xStr != null)
                transform[4] = xStr;
            if (yStr != null)
                transform[5] = yStr;
            return transform;
        } else if (CssConstants.TRANSLATE_X.equals(function)) {
            String xStr = null;
            float x = 0;
            if (args.indexOf('%') > 0)
                xStr = args;
            else
                x = CssUtils.parseAbsoluteLength(args.trim());
            String[] transform = floatArrayToStringArray(new float[]{1, 0, 0, 1, x, 0});
            if (xStr != null)
                transform[4] = xStr;
            return transform;
        } else if (CssConstants.TRANSLATE_Y.equals(function)) {
            String yStr = null;
            float y = 0;
            if (args.indexOf('%') > 0)
                yStr = Float.toString(-1 * Float.parseFloat(args.substring(0, args.indexOf('%')))) + '%';
            else
                y = -1 * CssUtils.parseAbsoluteLength(args.trim());
            String[] transform = floatArrayToStringArray(new float[]{1, 0, 0, 1, 0, y});
            if (yStr != null)
                transform[4] = yStr;
            return transform;
        } else if (CssConstants.ROTATE.equals(function)) {
            double angleInRad = parseAngleToRadians(args);
            float cos = (float) cos(angleInRad);
            float sin = (float) sin(angleInRad);
            return floatArrayToStringArray(new float[]{cos, sin, -1 * sin, cos, 0, 0});
        } else if (CssConstants.SKEW.equals(function)) {
            String[] arg = args.split(",");
            double xAngleInRad = parseAngleToRadians(arg[0]),
                    yAngleInRad = arg.length == 2 ? parseAngleToRadians(arg[1]) : 0.0;
            float tanX = (float) tan(xAngleInRad);
            float tanY = (float) tan(yAngleInRad);
            return floatArrayToStringArray(new float[]{1, tanY, tanX, 1, 0, 0});
        } else if (CssConstants.SKEW_X.equals(function)) {
            float tanX = (float) tan(parseAngleToRadians(args));
            return floatArrayToStringArray(new float[]{1, 0, tanX, 1, 0, 0});
        } else if (CssConstants.SKEW_Y.equals(function)) {
            float tanY = (float) tan(parseAngleToRadians(args));
            return floatArrayToStringArray(new float[]{1, tanY, 0, 1, 0, 0});
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
            return floatArrayToStringArray(new float[]{x, 0, 0, y, 0, 0});
        } else if (CssConstants.SCALE_X.equals(function)) {
            float x = Float.parseFloat(args.trim());
            return floatArrayToStringArray(new float[]{x, 0, 0, 1, 0, 0});
        } else if (CssConstants.SCALE_Y.equals(function)) {
            float y = Float.parseFloat(args.trim());
            return floatArrayToStringArray(new float[]{1, 0, 0, y, 0, 0});
        }
        return new String[6];
    }

    private static double parseAngleToRadians(String value){
        if (value.indexOf('d') < 0)
            return 0.0;
        if (value.indexOf('r') > 0)
           return -1 * Double.parseDouble(value.trim().substring(0, value.indexOf('r')));
        return toRadians(-1 * Double.parseDouble(value.trim().substring(0, value.indexOf('d'))));
    }

    private static String[] floatArrayToStringArray(float[] floats) {
        String[] strings = new String[floats.length];
        for (int i = 0; i < floats.length; i++)
            strings[i] = Float.toString(floats[i]);
        return strings;
    }
}
