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
package com.itextpdf.html2pdf.css.apply;

import com.itextpdf.html2pdf.css.apply.impl.BodyTagCssApplier;
import com.itextpdf.html2pdf.css.apply.impl.DlTagCssApplier;
import com.itextpdf.html2pdf.css.apply.impl.HtmlTagCssApplier;
import com.itextpdf.html2pdf.css.apply.impl.LiTagCssApplier;
import com.itextpdf.html2pdf.css.apply.impl.SpanTagCssApplier;
import com.itextpdf.html2pdf.css.apply.impl.TdTagCssApplier;
import com.itextpdf.html2pdf.css.apply.impl.UlOlTagCssApplier;
import com.itextpdf.html2pdf.html.TagConstants;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by SamuelHuylebroeck on 11/30/2016.
 */
public class DefaultTagCssApplierMapping {
    
    private static Map<String, Class<?>> mapping;

    public static Map<String,Class<?>> getDefaultCssApplierMapping(){
        if(mapping == null) {
            Map<String, Class<?>> buildMap = new HashMap<String, Class<?>>();
            buildMap.put(TagConstants.A, SpanTagCssApplier.class);
            buildMap.put(TagConstants.ABBR, SpanTagCssApplier.class);
            buildMap.put(TagConstants.ADDRESS, BlockCssApplier.class);
            buildMap.put(TagConstants.ARTICLE, BlockCssApplier.class);
            buildMap.put(TagConstants.ASIDE, BlockCssApplier.class);
            buildMap.put(TagConstants.B, SpanTagCssApplier.class);
            buildMap.put(TagConstants.BDI, SpanTagCssApplier.class);
            buildMap.put(TagConstants.BDO, SpanTagCssApplier.class);
            buildMap.put(TagConstants.BLOCKQUOTE, BlockCssApplier.class);
            buildMap.put(TagConstants.BODY, BodyTagCssApplier.class);
            //buildMap.put(TagConstants.CAPTION,SpanTagCssApplier.class);
            buildMap.put(TagConstants.CENTER,BlockCssApplier.class);
            buildMap.put(TagConstants.CITE, SpanTagCssApplier.class);
            buildMap.put(TagConstants.CODE, SpanTagCssApplier.class);
            buildMap.put(TagConstants.EM, SpanTagCssApplier.class);
            buildMap.put(TagConstants.DEL, SpanTagCssApplier.class);
            buildMap.put(TagConstants.DFN,SpanTagCssApplier.class);
            buildMap.put(TagConstants.DT, BlockCssApplier.class);
            buildMap.put(TagConstants.DD, BlockCssApplier.class);
            buildMap.put(TagConstants.DIV, BlockCssApplier.class);
            buildMap.put(TagConstants.FOOTER, BlockCssApplier.class);
            buildMap.put(TagConstants.FIGCAPTION, BlockCssApplier.class);
            buildMap.put(TagConstants.FIGURE, BlockCssApplier.class);
            buildMap.put(TagConstants.H1, BlockCssApplier.class);
            buildMap.put(TagConstants.H2, BlockCssApplier.class);
            buildMap.put(TagConstants.H3, BlockCssApplier.class);
            buildMap.put(TagConstants.H4, BlockCssApplier.class);
            buildMap.put(TagConstants.H5, BlockCssApplier.class);
            buildMap.put(TagConstants.H6, BlockCssApplier.class);
            buildMap.put(TagConstants.HEADER, BlockCssApplier.class);
            buildMap.put(TagConstants.HR, BlockCssApplier.class);
            buildMap.put(TagConstants.IMG, BlockCssApplier.class);
            buildMap.put(TagConstants.MAIN, BlockCssApplier.class);
            buildMap.put(TagConstants.NAV, BlockCssApplier.class);
            buildMap.put(TagConstants.P, BlockCssApplier.class);
            buildMap.put(TagConstants.SECTION, BlockCssApplier.class);
            buildMap.put(TagConstants.TABLE, BlockCssApplier.class);
            buildMap.put(TagConstants.TFOOT, BlockCssApplier.class);
            buildMap.put(TagConstants.THEAD, BlockCssApplier.class);
            buildMap.put(TagConstants.DL, DlTagCssApplier.class);
            buildMap.put(TagConstants.HTML, HtmlTagCssApplier.class);
            buildMap.put(TagConstants.I, SpanTagCssApplier.class);
            buildMap.put(TagConstants.INS, SpanTagCssApplier.class);
            buildMap.put(TagConstants.KBD, SpanTagCssApplier.class);
            buildMap.put(TagConstants.LI, LiTagCssApplier.class);
            buildMap.put(TagConstants.MARK, SpanTagCssApplier.class);
            buildMap.put(TagConstants.OL, UlOlTagCssApplier.class);
            buildMap.put(TagConstants.PRE, BlockCssApplier.class);
            buildMap.put(TagConstants.Q, SpanTagCssApplier.class);
            buildMap.put(TagConstants.S, SpanTagCssApplier.class);
            buildMap.put(TagConstants.SAMP,SpanTagCssApplier.class);
            buildMap.put(TagConstants.SMALL, SpanTagCssApplier.class);
            buildMap.put(TagConstants.SPAN, SpanTagCssApplier.class);
            buildMap.put(TagConstants.STRIKE, SpanTagCssApplier.class);
            buildMap.put(TagConstants.STRONG, SpanTagCssApplier.class);
            buildMap.put(TagConstants.SUB, SpanTagCssApplier.class);
            buildMap.put(TagConstants.SUP, SpanTagCssApplier.class);
            buildMap.put(TagConstants.TD, TdTagCssApplier.class);
            buildMap.put(TagConstants.TH, TdTagCssApplier.class);
            buildMap.put(TagConstants.TIME, SpanTagCssApplier.class);
            buildMap.put(TagConstants.U, SpanTagCssApplier.class);
            buildMap.put(TagConstants.UL, UlOlTagCssApplier.class);
            buildMap.put(TagConstants.VAR,SpanTagCssApplier.class);

            mapping = Collections.unmodifiableMap(buildMap);
        }
        return mapping;
    }


}
