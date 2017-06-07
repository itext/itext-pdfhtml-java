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
package com.itextpdf.html2pdf.css.util;

import com.itextpdf.html2pdf.LogMessageConstant;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;

class CssPropertyNormalizer {

    public static String normalize(String str) {
        StringBuilder buffer = new StringBuilder();
        int segmentStart = 0;
        for (int i = 0; i < str.length(); ++i) {
            if (str.charAt(i) == '\\') {
                ++i;
            } else if (str.charAt(i) == '\'' || str.charAt(i) == '"') {
                appendAndFormatSegment(buffer, str, segmentStart, i + 1);
                segmentStart = i = appendQuoteContent(buffer, str, i + 1, str.charAt(i));
            }
        }
        if (segmentStart < str.length()) {
            appendAndFormatSegment(buffer, str, segmentStart, str.length());
        }
        return buffer.toString();
    }

    private static void appendAndFormatSegment(StringBuilder buffer, String source, int start, int end) {
        String[] parts = source.substring(start, end).split("\\s");
        StringBuilder sb = new StringBuilder();
        for (String part : parts) {
            if (part.length() > 0) {
                if (sb.length() > 0 && !trimSpaceAfter(sb.charAt(sb.length() - 1)) && !trimSpaceBefore(part.charAt(0))) {
                    sb.append(" ");
                }
                // Do not make base64 data lowercase, function name only
                if (part.matches("^[uU][rR][lL]\\(.+\\)") && CssUtils.isBase64Data(part.substring(4, part.length() - 1))) {
                    sb.append(part.substring(0, 3).toLowerCase()).append(part.substring(3));
                } else {
                    sb.append(part.toLowerCase());
                }
            }
        }
        buffer.append(sb);
    }

    private static int appendQuoteContent(StringBuilder buffer, String source, int start, char endQuoteSymbol) {
        int end = findNextUnescapedChar(source, endQuoteSymbol, start);
        if (end == -1) {
            end = source.length();
            LoggerFactory.getLogger(CssPropertyNormalizer.class).warn(MessageFormat.format(LogMessageConstant.QUOTE_IS_NOT_CLOSED_IN_CSS_EXPRESSION, source));
        }
        buffer.append(source, start, end);
        return end;
    }

    private static int findNextUnescapedChar(String source, char ch, int startIndex) {
        int symbolPos = source.indexOf(ch, startIndex);
        if (symbolPos == -1) {
            return -1;
        }
        int afterNoneEscapePos = symbolPos;
        while (afterNoneEscapePos > 0 && source.charAt(afterNoneEscapePos - 1) == '\\') {
            --afterNoneEscapePos;
        }
        return (symbolPos - afterNoneEscapePos) % 2 == 0 ? symbolPos : findNextUnescapedChar(source, ch, symbolPos + 1);
    }

    private static boolean trimSpaceAfter(char ch) {
        return ch == ',' || ch == '(';
    }

    private static boolean trimSpaceBefore(char ch) {
        return ch == ',' || ch == ')';
    }
}