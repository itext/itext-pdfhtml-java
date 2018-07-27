/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2018 iText Group NV
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
package com.itextpdf.html2pdf.resolver.resource;

import com.itextpdf.html2pdf.exception.Html2PdfException;

import java.io.UnsupportedEncodingException;

/**
 * Utilities class to decode HTML strings to a strings in a specific encoding.
 *
 * @deprecated Will be removed in iText 7.1
 */
@Deprecated
public class DecodeUtil {

    /**
     * The default encoding ("UTF-8").
     */
    private static String dfltEncName = "UTF-8";

    /**
     * The default uri scheme ("file").
     */
    private static String dfltUriScheme = "file";

    /**
     * Decode a {@link String} to a {@link String} using the default encoding and the default uri scheme.
     *
     * @param s the string to decode
     * @return the decoded string
     */
    public static String decode(String s) {
        return decode(s, dfltUriScheme);
    }

    /**
     * Decodes a {@link String} to a {@link String} using a specific uri scheme
     * and default encoding.
     *
     * @param s      the string to decode
     * @param scheme the uri scheme
     * @return the decoded string
     */
    public static String decode(String s, String scheme) {
        return decode(s, scheme, dfltEncName);
    }

    /**
     * Decodes a {@link String} to a {@link String} using a specific encoding
     * and uri string.
     *
     * @param s      the string to decode
     * @param scheme the uri scheme
     * @param enc    the encoding
     * @return the decoded string
     */
    public static String decode(String s, String scheme, String enc) {
        boolean needToChange = false;
        int numChars = s.length();
        StringBuffer sb = new StringBuffer(numChars > 500 ? numChars / 2 : numChars);
        int i = 0;

        if (null == enc || enc.length() == 0) {
            throw new Html2PdfException(Html2PdfException.UnsupportedEncodingException);
        }

        char c;
        byte[] bytes = null;
        while (i < numChars) {
            c = s.charAt(i);
            if ('%' == c) {
                // (numChars-i)/3 is an upper bound for the number
                // of remaining bytes
                if (bytes == null)
                    bytes = new byte[(numChars - i) / 3];
                int pos = 0;
                boolean incrementLater = !(i + 2 < numChars);

                while (((i + 2) < numChars) &&
                        (c == '%')) {
                    int v;
                    try {
                        v = Integer.parseInt(s.substring(i + 1, i + 3), 16);
                    } catch (NumberFormatException e) {
                        v = -1;
                    }
                    if (v < 0) {
                        i++;
                        break;
                    } else {
                        bytes[pos++] = (byte) v;
                        i += 3;
                        if (i < numChars) {
                            c = s.charAt(i);
                        }
                    }
                }

                if ((i < numChars) && (c == '%')) {
                    bytes[pos++] = (byte) c;
                }
                if (incrementLater)
                    i++;
                try {
                    sb.append(new String(bytes, 0, pos, enc));
                } catch (UnsupportedEncodingException e) {
                    throw new Html2PdfException(Html2PdfException.UnsupportedEncodingException);
                }
                needToChange = true;
            } else {
                sb.append(c);
                i++;
                if ("http".equals(scheme) || "https".equals(scheme)) {
                    if ('?' == c) {
                        break;
                    } else if ('#' == c) {
                        sb.append(c);
                        needToChange = true;
                    }
                }
            }
        }

        if (needToChange && i + 1 < numChars) {
            sb.append(s.substring(i + 1));
        }

        return (needToChange ? sb.toString() : s);
    }
}
