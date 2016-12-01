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
package com.itextpdf.html2pdf;

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

import com.itextpdf.html2pdf.css.apply.BlockCssApplier;
import com.itextpdf.html2pdf.css.apply.impl.BodyTagCssApplier;
import com.itextpdf.html2pdf.css.apply.impl.DlTagCssApplier;
import com.itextpdf.html2pdf.css.apply.impl.HtmlTagCssApplier;
import com.itextpdf.html2pdf.css.apply.impl.LiTagCssApplier;
import com.itextpdf.html2pdf.css.apply.impl.SpanTagCssApplier;
import com.itextpdf.html2pdf.css.apply.impl.TdTagCssApplier;
import com.itextpdf.html2pdf.css.apply.impl.UlOlTagCssApplier;

import com.itextpdf.html2pdf.html.TagConstants;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by SamuelHuylebroeck on 11/30/2016.
 */
public class DefaultTagMapping {

    public static Map<String,String> getDefaultTagWorkerMapping(){
        Map<String, String>  mapping = new ConcurrentHashMap<>();
        mapping.put(TagConstants.A, ATagWorker.class.getName());
        mapping.put(TagConstants.ARTICLE, DivTagWorker.class.getName());
        mapping.put(TagConstants.ASIDE,DivTagWorker.class.getName());
        mapping.put(TagConstants.B, SpanTagWorker.class.getName());
        mapping.put(TagConstants.BLOCKQUOTE,DivTagWorker.class.getName());
        mapping.put(TagConstants.BODY, BodyTagWorker.class.getName());
        mapping.put(TagConstants.BR, BrTagWorker.class.getName());
        mapping.put(TagConstants.CITE,SpanTagWorker.class.getName());
        mapping.put(TagConstants.CODE,SpanTagWorker.class.getName());
        mapping.put(TagConstants.DIV,DivTagWorker.class.getName());
        mapping.put(TagConstants.DD, DdTagWorker.class.getName());
        mapping.put(TagConstants.DL, DlTagWorker.class.getName());
        mapping.put(TagConstants.DT, DtTagWorker.class.getName());
        mapping.put(TagConstants.EM, SpanTagWorker.class.getName());
        mapping.put(TagConstants.FOOTER, DivTagWorker.class.getName());
        mapping.put(TagConstants.HEADER, DivTagWorker.class.getName());
        mapping.put(TagConstants.HR, HrTagWorker.class.getName());
        mapping.put(TagConstants.HTML, HtmlTagWorker.class.getName());
        mapping.put(TagConstants.I, SpanTagWorker.class.getName());
        mapping.put(TagConstants.IMG, ImgTagWorker.class.getName());
        mapping.put(TagConstants.LI, LiTagWorker.class.getName());
        mapping.put(TagConstants.MAIN, DivTagWorker.class.getName());
        mapping.put(TagConstants.META, MetaTagWorker.class.getName());
        mapping.put(TagConstants.NAV, DivTagWorker.class.getName());
        mapping.put(TagConstants.OL, UlOlTagWorker.class.getName());
        mapping.put(TagConstants.H1, PTagWorker.class.getName());
        mapping.put(TagConstants.H2, PTagWorker.class.getName());
        mapping.put(TagConstants.H3, PTagWorker.class.getName());
        mapping.put(TagConstants.H4, PTagWorker.class.getName());
        mapping.put(TagConstants.H5, PTagWorker.class.getName());
        mapping.put(TagConstants.H6, PTagWorker.class.getName());
        mapping.put(TagConstants.P, PTagWorker.class.getName());
        mapping.put(TagConstants.PRE, PTagWorker.class.getName());
        mapping.put(TagConstants.Q, SpanTagWorker.class.getName());
        mapping.put(TagConstants.SECTION, DivTagWorker.class.getName());
        mapping.put(TagConstants.SMALL,SpanTagWorker.class.getName());
        mapping.put(TagConstants.SPAN, SpanTagWorker.class.getName());
        mapping.put(TagConstants.STRIKE, SpanTagWorker.class.getName());
        mapping.put(TagConstants.STRONG,SpanTagWorker.class.getName());
        mapping.put(TagConstants.SUB, SpanTagWorker.class.getName());
        mapping.put(TagConstants.SUP,SpanTagWorker.class.getName());
        mapping.put(TagConstants.TABLE, TableTagWorker.class.getName());
        mapping.put(TagConstants.TFOOT, TableFooterTagWorker.class.getName());
        mapping.put(TagConstants.THEAD, TableHeaderTagWorker.class.getName());
        mapping.put(TagConstants.TIME,SpanTagWorker.class.getName());
        mapping.put(TagConstants.TITLE, TitleTagWorker.class.getName());
        mapping.put(TagConstants.TD, TdTagWorker.class.getName());
        mapping.put(TagConstants.TH,TdTagWorker.class.getName());
        mapping.put(TagConstants.TR, TrTagWorker.class.getName());
        mapping.put(TagConstants.U, SpanTagWorker.class.getName());
        mapping.put(TagConstants.UL, UlOlTagWorker.class.getName());
        return mapping;
    }

    public static Map<String,String> getDefaultCssApplierMapping(){
        Map<String,String> mapping = new ConcurrentHashMap<String, String>();
        mapping.put(TagConstants.A, SpanTagCssApplier.class.getName());
        mapping.put(TagConstants.ARTICLE, BlockCssApplier.class.getName());
        mapping.put(TagConstants.B,SpanTagCssApplier.class.getName());
        mapping.put(TagConstants.ASIDE,BlockCssApplier.class.getName());
        mapping.put(TagConstants.BLOCKQUOTE,BlockCssApplier.class.getName());
        mapping.put(TagConstants.BODY, BodyTagCssApplier.class.getName());
        mapping.put(TagConstants.CITE, SpanTagCssApplier.class.getName());
        mapping.put(TagConstants.CODE,SpanTagCssApplier.class.getName());
        mapping.put(TagConstants.EM, SpanTagCssApplier.class.getName());
        mapping.put(TagConstants.DT,BlockCssApplier.class.getName());
        mapping.put(TagConstants.DD,BlockCssApplier.class.getName());
        mapping.put(TagConstants.DIV,BlockCssApplier.class.getName());
        mapping.put(TagConstants.FOOTER,BlockCssApplier.class.getName());
        mapping.put(TagConstants.H1,BlockCssApplier.class.getName());
        mapping.put(TagConstants.H2,BlockCssApplier.class.getName());
        mapping.put(TagConstants.H3,BlockCssApplier.class.getName());
        mapping.put(TagConstants.H4,BlockCssApplier.class.getName());
        mapping.put(TagConstants.H5,BlockCssApplier.class.getName());
        mapping.put(TagConstants.H6,BlockCssApplier.class.getName());
        mapping.put(TagConstants.HEADER,BlockCssApplier.class.getName());
        mapping.put(TagConstants.HR,BlockCssApplier.class.getName());
        mapping.put(TagConstants.IMG,BlockCssApplier.class.getName());
        mapping.put(TagConstants.MAIN,BlockCssApplier.class.getName());
        mapping.put(TagConstants.NAV,BlockCssApplier.class.getName());
        mapping.put(TagConstants.P,BlockCssApplier.class.getName());
        mapping.put(TagConstants.SECTION,BlockCssApplier.class.getName());
        mapping.put(TagConstants.TABLE,BlockCssApplier.class.getName());
        mapping.put(TagConstants.TFOOT,BlockCssApplier.class.getName());
        mapping.put(TagConstants.THEAD,BlockCssApplier.class.getName());
        mapping.put(TagConstants.DL, DlTagCssApplier.class.getName());
        mapping.put(TagConstants.HTML, HtmlTagCssApplier.class.getName());
        mapping.put(TagConstants.I,SpanTagCssApplier.class.getName());
        mapping.put(TagConstants.LI, LiTagCssApplier.class.getName());
        mapping.put(TagConstants.OL, UlOlTagCssApplier.class.getName());
        mapping.put(TagConstants.PRE, BlockCssApplier.class.getName());
        mapping.put(TagConstants.Q, SpanTagCssApplier.class.getName());
        mapping.put(TagConstants.SMALL,SpanTagCssApplier.class.getName());
        mapping.put(TagConstants.SPAN,SpanTagCssApplier.class.getName());
        mapping.put(TagConstants.STRIKE,SpanTagCssApplier.class.getName());
        mapping.put(TagConstants.STRONG,SpanTagCssApplier.class.getName());
        mapping.put(TagConstants.SUB,SpanTagCssApplier.class.getName());
        mapping.put(TagConstants.SUP,SpanTagCssApplier.class.getName());
        mapping.put(TagConstants.TD, TdTagCssApplier.class.getName());
        mapping.put(TagConstants.TH, TdTagCssApplier.class.getName());
        mapping.put(TagConstants.TIME,SpanTagCssApplier.class.getName());
        mapping.put(TagConstants.U,SpanTagCssApplier.class.getName());
        mapping.put(TagConstants.UL,UlOlTagCssApplier.class.getName());
        return mapping;
    }


}
