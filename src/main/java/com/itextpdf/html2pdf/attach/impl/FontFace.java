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
package com.itextpdf.html2pdf.attach.impl;

import com.itextpdf.html2pdf.css.CssDeclaration;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class FontFace {

    private final String alias;
    private final List<FontFaceSrc> sources;

    public static FontFace create(List<CssDeclaration> properties) {
        String fontFamily = null;
        String srcs = null;
        for(CssDeclaration descriptor: properties) {
            if ("font-family".equals(descriptor.getProperty())) {
                fontFamily = descriptor.getExpression();
            } else if ("src".equals(descriptor.getProperty())) {
                srcs = descriptor.getExpression();
            }
        }
        if (fontFamily == null || srcs == null) {
            // 'font-family' and 'src' is required according to spec:
            // https://www.w3.org/TR/2013/CR-css-fonts-3-20131003/#descdef-font-family\
            // https://www.w3.org/TR/2013/CR-css-fonts-3-20131003/#descdef-src
            return null;
        }

        List<FontFaceSrc> sources = new ArrayList<>();
        // ttc collection are supported via url(Arial.ttc#1), url(Arial.ttc#2), etc.
        for (String src : srcs.split(",")) {
            //local|url("ideal-sans-serif.woff")( format("woff"))?
            FontFaceSrc source = FontFaceSrc.create(src.trim());
            if (source != null) {
                sources.add(source);
            }
        }

        if (sources.size() > 0) {
            return new FontFace(fontFamily, sources);
        } else {
            return null;
        }
    }

    /**
     * Actually font-family is an alias.
     */
    public String getFontFamily() {
        return alias;
    }

    public List<FontFaceSrc> getSources() {
        return sources;
    }

    private FontFace(String alias, List<FontFaceSrc> sources) {
        this.alias = alias;
        this.sources = sources;
    }

    //region Nested types

    static class FontFaceSrc {
        static final Pattern UrlPattern = Pattern.compile("^((local)|(url))\\(((\'[^\']*\')|(\"[^\"]*\")|([^\'\"\\)]*))\\)( format\\(((\'[^\']*\')|(\"[^\"]*\")|([^\'\"\\)]*))\\))?$");
        static final int TypeGroup = 1;
        static final int UrlGroup = 4;
        static final int FormatGroup = 9;

        final FontFormat format;
        final String src;
        final boolean isLocal;

        @Override
        public String toString() {
            return MessageFormat.format("{0}({1}){2}", isLocal ? "local" : "url", src, format != FontFormat.None ? MessageFormat.format(" format({0})", format) : "");
        }

        static FontFaceSrc create(String src) {
            Matcher m = UrlPattern.matcher(src);
            if (!m.matches()) {
                return null;
            }
            return new FontFaceSrc(unquote(m.group(UrlGroup)),
                    "local".equals(m.group(TypeGroup)),
                    parseFormat(m.group(FormatGroup)));
        }

        static FontFormat parseFormat(String formatStr) {
            if (formatStr != null && formatStr.length() > 0) {
                switch (unquote(formatStr).toLowerCase()) {
                    case "truetype":
                        return FontFormat.TrueType;
                    case "opentype":
                        return FontFormat.OpenType;
                    case "woff":
                        return FontFormat.WOFF;
                    case "woff2":
                        return FontFormat.WOFF2;
                    case "embedded-opentype":
                        return FontFormat.EOT;
                    case "svg":
                        return FontFormat.SVG;
                }
            }
            return FontFormat.None;
        }

        static String unquote(String quotedString) {
            if (quotedString.charAt(0) == '\'' || quotedString.charAt(0) == '\"') {
                return quotedString.substring(1, quotedString.length() - 1);
            }
            return quotedString;
        }

        private FontFaceSrc(String src, boolean isLocal, FontFormat format) {
            this.format = format;
            this.src = src;
            this.isLocal = isLocal;
        }
    }

    enum FontFormat {
        None,
        TrueType, // "truetype"
        OpenType, // "opentype"
        WOFF, // "woff"
        WOFF2, // "woff2"
        EOT, // "embedded-opentype"
        SVG // "svg"
    }

    //endregion
}
