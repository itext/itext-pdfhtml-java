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
package com.itextpdf.html2pdf.attach;

import com.itextpdf.html2pdf.attach.impl.tags.ATagWorker;
import com.itextpdf.html2pdf.attach.impl.tags.BodyTagWorker;
import com.itextpdf.html2pdf.attach.impl.tags.BrTagWorker;
import com.itextpdf.html2pdf.attach.impl.tags.DdTagWorker;
import com.itextpdf.html2pdf.attach.impl.tags.DivTagWorker;
import com.itextpdf.html2pdf.attach.impl.tags.DlTagWorker;
import com.itextpdf.html2pdf.attach.impl.tags.DtTagWorker;
import com.itextpdf.html2pdf.attach.impl.tags.HrTagWorker;
import com.itextpdf.html2pdf.attach.impl.tags.HtmlTagWorker;
import com.itextpdf.html2pdf.attach.impl.tags.ImgTagWorker;
import com.itextpdf.html2pdf.attach.impl.tags.InputTagWorker;
import com.itextpdf.html2pdf.attach.impl.tags.LiTagWorker;
import com.itextpdf.html2pdf.attach.impl.tags.MetaTagWorker;
import com.itextpdf.html2pdf.attach.impl.tags.PTagWorker;
import com.itextpdf.html2pdf.attach.impl.tags.SpanTagWorker;
import com.itextpdf.html2pdf.attach.impl.tags.TableFooterTagWorker;
import com.itextpdf.html2pdf.attach.impl.tags.TableHeaderTagWorker;
import com.itextpdf.html2pdf.attach.impl.tags.TableTagWorker;
import com.itextpdf.html2pdf.attach.impl.tags.TdTagWorker;
import com.itextpdf.html2pdf.attach.impl.tags.TitleTagWorker;
import com.itextpdf.html2pdf.attach.impl.tags.TrTagWorker;
import com.itextpdf.html2pdf.attach.impl.tags.UlOlTagWorker;
import com.itextpdf.html2pdf.html.TagConstants;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DefaultTagWorkerMapping {

    private static Map<String, Class<?>> mapping;

    public static Map<String, Class<?>> getDefaultTagWorkerMapping() {
        if (mapping == null) {
            Map<String, Class<?>> buildMap = new HashMap<String, Class<?>>();
            buildMap.put(TagConstants.A, ATagWorker.class);
            buildMap.put(TagConstants.ABBR, ATagWorker.class);
            buildMap.put(TagConstants.ADDRESS, DivTagWorker.class);
            buildMap.put(TagConstants.ARTICLE, DivTagWorker.class);
            buildMap.put(TagConstants.ASIDE, DivTagWorker.class);
            buildMap.put(TagConstants.B, SpanTagWorker.class);
            buildMap.put(TagConstants.BDI, SpanTagWorker.class);
            buildMap.put(TagConstants.BDO, SpanTagWorker.class);
            buildMap.put(TagConstants.BLOCKQUOTE, DivTagWorker.class);
            buildMap.put(TagConstants.BODY, BodyTagWorker.class);
            buildMap.put(TagConstants.BR, BrTagWorker.class);
            //buildMap.put(TagConstants.CAPTION, SpanTagWorker.class);
            buildMap.put(TagConstants.CENTER, PTagWorker.class);
            buildMap.put(TagConstants.CITE, SpanTagWorker.class);
            buildMap.put(TagConstants.CODE, SpanTagWorker.class);
            buildMap.put(TagConstants.DEL, SpanTagWorker.class);
            buildMap.put(TagConstants.DFN, SpanTagWorker.class);
            buildMap.put(TagConstants.DIV, DivTagWorker.class);
            buildMap.put(TagConstants.DD, DdTagWorker.class);
            buildMap.put(TagConstants.DL, DlTagWorker.class);
            buildMap.put(TagConstants.DT, DtTagWorker.class);
            buildMap.put(TagConstants.EM, SpanTagWorker.class);
            buildMap.put(TagConstants.FONT, SpanTagWorker.class);
            buildMap.put(TagConstants.FIGCAPTION, PTagWorker.class);
            buildMap.put(TagConstants.FIGURE, DivTagWorker.class);
            buildMap.put(TagConstants.FOOTER, DivTagWorker.class);
            buildMap.put(TagConstants.HEADER, DivTagWorker.class);
            buildMap.put(TagConstants.HR, HrTagWorker.class);
            buildMap.put(TagConstants.HTML, HtmlTagWorker.class);
            buildMap.put(TagConstants.I, SpanTagWorker.class);
            buildMap.put(TagConstants.IMG, ImgTagWorker.class);
            buildMap.put(TagConstants.INPUT, InputTagWorker.class);
            buildMap.put(TagConstants.INS, SpanTagWorker.class);
            buildMap.put(TagConstants.KBD, SpanTagWorker.class);
            buildMap.put(TagConstants.LI, LiTagWorker.class);
            buildMap.put(TagConstants.MAIN, DivTagWorker.class);
            buildMap.put(TagConstants.MARK, SpanTagWorker.class);
            buildMap.put(TagConstants.META, MetaTagWorker.class);
            buildMap.put(TagConstants.NAV, DivTagWorker.class);
            buildMap.put(TagConstants.OL, UlOlTagWorker.class);
            buildMap.put(TagConstants.H1, PTagWorker.class);
            buildMap.put(TagConstants.H2, PTagWorker.class);
            buildMap.put(TagConstants.H3, PTagWorker.class);
            buildMap.put(TagConstants.H4, PTagWorker.class);
            buildMap.put(TagConstants.H5, PTagWorker.class);
            buildMap.put(TagConstants.H6, PTagWorker.class);
            buildMap.put(TagConstants.P, PTagWorker.class);
            buildMap.put(TagConstants.PRE, DivTagWorker.class);
            buildMap.put(TagConstants.Q, SpanTagWorker.class);
            buildMap.put(TagConstants.S, SpanTagWorker.class);
            buildMap.put(TagConstants.SAMP, SpanTagWorker.class);
            buildMap.put(TagConstants.SECTION, DivTagWorker.class);
            buildMap.put(TagConstants.SMALL, SpanTagWorker.class);
            buildMap.put(TagConstants.SPAN, SpanTagWorker.class);
            buildMap.put(TagConstants.STRIKE, SpanTagWorker.class);
            buildMap.put(TagConstants.STRONG, SpanTagWorker.class);
            buildMap.put(TagConstants.SUB, SpanTagWorker.class);
            buildMap.put(TagConstants.SUP, SpanTagWorker.class);
            buildMap.put(TagConstants.TABLE, TableTagWorker.class);
            buildMap.put(TagConstants.TFOOT, TableFooterTagWorker.class);
            buildMap.put(TagConstants.THEAD, TableHeaderTagWorker.class);
            buildMap.put(TagConstants.TIME, SpanTagWorker.class);
            buildMap.put(TagConstants.TITLE, TitleTagWorker.class);
            buildMap.put(TagConstants.TD, TdTagWorker.class);
            buildMap.put(TagConstants.TH, TdTagWorker.class);
            buildMap.put(TagConstants.TR, TrTagWorker.class);
            buildMap.put(TagConstants.U, SpanTagWorker.class);
            buildMap.put(TagConstants.UL, UlOlTagWorker.class);
            buildMap.put(TagConstants.VAR, SpanTagWorker.class);

            mapping = Collections.unmodifiableMap(buildMap);
        }

        return mapping;
    }


}
