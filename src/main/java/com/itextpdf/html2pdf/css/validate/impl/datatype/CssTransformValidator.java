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
package com.itextpdf.html2pdf.css.validate.impl.datatype;

import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.css.validate.ICssDataTypeValidator;

/**
 * {@link ICssDataTypeValidator} implementation for .
 */
public class CssTransformValidator implements ICssDataTypeValidator {

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.css.validate.ICssDataTypeValidator#isValid(java.lang.String)
     */
    @Override
    public boolean isValid(String objectString) {
        String function, args;
        if (!CssConstants.NONE.equals(objectString)) {
            function = objectString.substring(0, objectString.indexOf('('));
            args = objectString.substring(objectString.indexOf('(') + 1, objectString.length() - 1);
        } else {
            return true;
        }
        if (CssConstants.MATRIX.equals(function) || CssConstants.SCALE.equals(function) ||
                CssConstants.SCALE_X.equals(function) || CssConstants.SCALE_Y.equals(function)) {
            String[] arg = args.split(",");
            if (arg.length == 6 && CssConstants.MATRIX.equals(function) ||
                    (arg.length == 1 || arg.length == 2) && CssConstants.SCALE.equals(function) ||
                    arg.length == 1 && (CssConstants.SCALE_X.equals(function) || CssConstants.SCALE_Y.equals(function))) {
                int i = 0;
                for (; i < arg.length; i++) {
                    try {
                        Float.parseFloat(arg[i].trim());
                    } catch (NumberFormatException exc) {
                        return false;
                    }
                }
                if (i == arg.length)
                    return true;
            }
            return false;
        } else if (CssConstants.TRANSLATE.equals(function)
                || CssConstants.TRANSLATE_X.equals(function) || CssConstants.TRANSLATE_Y.equals(function)) {
            String[] arg = args.split(",");
            if ((arg.length == 1 || arg.length == 2 && CssConstants.TRANSLATE.equals(function))
                    || (arg.length == 1 && (CssConstants.TRANSLATE_X.equals(function) || CssConstants.TRANSLATE_Y.equals(function)))) {
                for (String a : arg)
                    if (!isValidForTranslate(a))
                        return false;
                return true;
            }
            return false;
        } else if (CssConstants.ROTATE.equals(function)) {
            int i = args.indexOf('d');
            if (i > 0 && args.substring(i).equals("deg")) {
                try {
                    Double.parseDouble(args.substring(0, i));
                } catch (NumberFormatException exc) {
                    return false;
                }
                return true;
            }
            return false;
        } else if (CssConstants.SKEW.equals(function)
                || CssConstants.SKEW_X.equals(function) || CssConstants.SKEW_Y.equals(function)) {
            String[] arg = args.split(",");
            if ((arg.length == 1 || arg.length == 2 && CssConstants.SKEW.equals(function))
                    || (arg.length == 1 && (CssConstants.SKEW_X.equals(function) || CssConstants.SKEW_Y.equals(function)))) {
                for (int k = 0; k < arg.length; k++) {
                    int i = arg[k].indexOf('d');
                    if (i < 0 || !arg[k].substring(i).equals("deg"))
                        return false;
                    try {
                        Float.parseFloat(arg[k].trim().substring(0, i));
                    } catch (NumberFormatException exc) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    private static boolean isValidForTranslate(String string) {
        if (string == null)
            return false;
        int pos = 0;
        while (pos < string.length()) {
            if (string.charAt(pos) == '+' ||
                    string.charAt(pos) == '-' ||
                    string.charAt(pos) == '.' ||
                    string.charAt(pos) >= '0' && string.charAt(pos) <= '9') {
                pos++;
            } else {
                break;
            }
        }
        if (pos > 0) {
            try {
                Float.parseFloat(string.substring(0, pos));
            } catch (NumberFormatException exc) {
                return false;
            }
            return (string.substring(pos).equals(CssConstants.PT) || string.substring(pos).equals(CssConstants.IN) ||
                    string.substring(pos).equals(CssConstants.CM) || string.substring(pos).equals(CssConstants.Q) ||
                    string.substring(pos).equals(CssConstants.MM) || string.substring(pos).equals(CssConstants.PC) ||
                    string.substring(pos).equals(CssConstants.PX));
        }
        return false;
    }
}
