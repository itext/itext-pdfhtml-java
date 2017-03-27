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
package com.itextpdf.html2pdf.css.resolve;

class CssContentTokenizer {
    private String src;
    private int index = -1;
    private char stringQuote;
    private boolean inString;
    private int functionDepth = 0;

    public CssContentTokenizer(String src) {
        this.src = src;
    }

    public ContentToken getNextValidToken() {
        ContentToken token = getNextToken();
        while (token != null && !token.isString() && token.getValue().trim().isEmpty()) {
            token = getNextToken();
        }
        if (token != null && functionDepth > 0) {
            StringBuilder functionBuffer = new StringBuilder();
            while (token != null && functionDepth > 0) {
                processFunctionToken(token, functionBuffer);
                token = getNextToken();
            }
            functionDepth = 0;
            if (functionBuffer.length() != 0) {
                if (token != null) {
                    processFunctionToken(token, functionBuffer);
                }
                return new ContentToken(functionBuffer.toString(), false);
            }
        }
        return token;
    }

    private ContentToken getNextToken() {
        StringBuilder buff = new StringBuilder();
        char curChar;
        if (index >= src.length() - 1) {
            return null;
        }
        if (inString) {
            boolean isEscaped = false;
            StringBuilder pendingUnicodeSequence = new StringBuilder();
            while (++index < src.length()) {
                curChar = src.charAt(index);
                if (isEscaped) {
                    if (isHexDigit(curChar) && pendingUnicodeSequence.length() < 6) {
                        pendingUnicodeSequence.append(curChar);
                    } else if (pendingUnicodeSequence.length() != 0) {
                        int codePoint = Integer.parseInt(pendingUnicodeSequence.toString(), 16);
                        if (Character.isValidCodePoint(codePoint)) {
                            buff.appendCodePoint(codePoint);
                        } else {
                            buff.append("\uFFFD");
                        }
                        pendingUnicodeSequence.setLength(0);
                        if (curChar == stringQuote) {
                            inString = false;
                            return new ContentToken(buff.toString(), true);
                        } else if (!Character.isWhitespace(curChar)) {
                            buff.append(curChar);
                        }
                        isEscaped = false;
                    } else {
                        buff.append(curChar);
                        isEscaped = false;
                    }
                } else if (curChar == stringQuote){
                    inString = false;
                    return new ContentToken(buff.toString(), true);
                } else if (curChar == '\\') {
                    isEscaped = true;
                } else {
                    buff.append(curChar);
                }
            }
        } else {
            while (++index < src.length()) {
                curChar = src.charAt(index);
                if (curChar == '(') {
                    ++functionDepth;
                    buff.append(curChar);
                } else if (curChar == ')') {
                    --functionDepth;
                    buff.append(curChar);
                } else if (curChar == '"' || curChar == '\'') {
                    stringQuote = curChar;
                    inString = true;
                    return new ContentToken(buff.toString(), false);
                } else if (Character.isWhitespace(curChar)) {
                    if (functionDepth > 0) {
                        buff.append(curChar);
                    }
                    return new ContentToken(buff.toString(), false);
                } else {
                    buff.append(curChar);
                }
            }
        }
        return new ContentToken(buff.toString(), false);
    }

    private boolean isHexDigit(char c) {
        return (47 < c && c < 58) || (64 < c && c < 71) || (96 < c && c < 103);
    }

    private void processFunctionToken(ContentToken token, StringBuilder functionBuffer) {
        if (token.isString()) {
            functionBuffer.append(stringQuote);
            functionBuffer.append(token.getValue());
            functionBuffer.append(stringQuote);
        } else {
            functionBuffer.append(token.getValue());
        }
    }

    static class ContentToken {
        private String value;
        private boolean isString;

        public ContentToken(String value, boolean isString) {
            this.value = value;
            this.isString = isString;
        }

        public String getValue() {
            return value;
        }

        public boolean isString() {
            return isString;
        }

        @Override
        public String toString() {
            return value;
        }
    }
}
