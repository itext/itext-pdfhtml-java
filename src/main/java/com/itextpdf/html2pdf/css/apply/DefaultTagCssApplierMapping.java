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

import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.css.apply.impl.BodyTagCssApplier;
import com.itextpdf.html2pdf.css.apply.impl.ColTagCssApplier;
import com.itextpdf.html2pdf.css.apply.impl.ColgroupTagCssApplier;
import com.itextpdf.html2pdf.css.apply.impl.DlTagCssApplier;
import com.itextpdf.html2pdf.css.apply.impl.HtmlTagCssApplier;
import com.itextpdf.html2pdf.css.apply.impl.LiTagCssApplier;
import com.itextpdf.html2pdf.css.apply.impl.SpanTagCssApplier;
import com.itextpdf.html2pdf.css.apply.impl.TdTagCssApplier;
import com.itextpdf.html2pdf.css.apply.impl.TrTagCssApplier;
import com.itextpdf.html2pdf.css.apply.impl.UlOlTagCssApplier;
import com.itextpdf.html2pdf.html.TagConstants;
import com.itextpdf.html2pdf.util.TagProcessorMapping;


class DefaultTagCssApplierMapping {

    private DefaultTagCssApplierMapping() {
    }

    private static TagProcessorMapping mapping;

    static {
        mapping = new TagProcessorMapping();
        mapping.putMapping(TagConstants.A, SpanTagCssApplier.class);
        mapping.putMapping(TagConstants.ABBR, SpanTagCssApplier.class);
        mapping.putMapping(TagConstants.ADDRESS, BlockCssApplier.class);
        mapping.putMapping(TagConstants.ARTICLE, BlockCssApplier.class);
        mapping.putMapping(TagConstants.ASIDE, BlockCssApplier.class);
        mapping.putMapping(TagConstants.B, SpanTagCssApplier.class);
        mapping.putMapping(TagConstants.BDI, SpanTagCssApplier.class);
        mapping.putMapping(TagConstants.BDO, SpanTagCssApplier.class);
        mapping.putMapping(TagConstants.BLOCKQUOTE, BlockCssApplier.class);
        mapping.putMapping(TagConstants.BODY, BodyTagCssApplier.class);
        mapping.putMapping(TagConstants.CAPTION, SpanTagCssApplier.class);
        mapping.putMapping(TagConstants.CENTER, BlockCssApplier.class);
        mapping.putMapping(TagConstants.CITE, SpanTagCssApplier.class);
        mapping.putMapping(TagConstants.CODE, SpanTagCssApplier.class);
        mapping.putMapping(TagConstants.COL, ColTagCssApplier.class);
        mapping.putMapping(TagConstants.COLGROUP, ColgroupTagCssApplier.class);
        mapping.putMapping(TagConstants.EM, SpanTagCssApplier.class);
        mapping.putMapping(TagConstants.DEL, SpanTagCssApplier.class);
        mapping.putMapping(TagConstants.DFN, SpanTagCssApplier.class);
        mapping.putMapping(TagConstants.DT, BlockCssApplier.class);
        mapping.putMapping(TagConstants.DD, BlockCssApplier.class);
        mapping.putMapping(TagConstants.DIV, BlockCssApplier.class);
        mapping.putMapping(TagConstants.FONT, SpanTagCssApplier.class);
        mapping.putMapping(TagConstants.FOOTER, BlockCssApplier.class);
        mapping.putMapping(TagConstants.FIGCAPTION, BlockCssApplier.class);
        mapping.putMapping(TagConstants.FIGURE, BlockCssApplier.class);
        mapping.putMapping(TagConstants.H1, BlockCssApplier.class);
        mapping.putMapping(TagConstants.H2, BlockCssApplier.class);
        mapping.putMapping(TagConstants.H3, BlockCssApplier.class);
        mapping.putMapping(TagConstants.H4, BlockCssApplier.class);
        mapping.putMapping(TagConstants.H5, BlockCssApplier.class);
        mapping.putMapping(TagConstants.H6, BlockCssApplier.class);
        mapping.putMapping(TagConstants.HEADER, BlockCssApplier.class);
        mapping.putMapping(TagConstants.HR, BlockCssApplier.class);
        mapping.putMapping(TagConstants.IMG, BlockCssApplier.class);
        mapping.putMapping(TagConstants.INPUT, SpanTagCssApplier.class);
        mapping.putMapping(TagConstants.MAIN, BlockCssApplier.class);
        mapping.putMapping(TagConstants.NAV, BlockCssApplier.class);
        mapping.putMapping(TagConstants.P, BlockCssApplier.class);
        mapping.putMapping(TagConstants.SECTION, BlockCssApplier.class);
        mapping.putMapping(TagConstants.TABLE, BlockCssApplier.class);
        mapping.putMapping(TagConstants.TFOOT, BlockCssApplier.class);
        mapping.putMapping(TagConstants.THEAD, BlockCssApplier.class);
        mapping.putMapping(TagConstants.DL, DlTagCssApplier.class);
        mapping.putMapping(TagConstants.HTML, HtmlTagCssApplier.class);
        mapping.putMapping(TagConstants.I, SpanTagCssApplier.class);
        mapping.putMapping(TagConstants.INS, SpanTagCssApplier.class);
        mapping.putMapping(TagConstants.KBD, SpanTagCssApplier.class);
        mapping.putMapping(TagConstants.LABEL, SpanTagCssApplier.class);
        mapping.putMapping(TagConstants.LI, LiTagCssApplier.class);
        mapping.putMapping(TagConstants.MARK, SpanTagCssApplier.class);
        mapping.putMapping(TagConstants.OL, UlOlTagCssApplier.class);
        mapping.putMapping(TagConstants.PRE, BlockCssApplier.class);
        mapping.putMapping(TagConstants.Q, SpanTagCssApplier.class);
        mapping.putMapping(TagConstants.S, SpanTagCssApplier.class);
        mapping.putMapping(TagConstants.SAMP, SpanTagCssApplier.class);
        mapping.putMapping(TagConstants.SMALL, SpanTagCssApplier.class);
        mapping.putMapping(TagConstants.SPAN, SpanTagCssApplier.class);
        mapping.putMapping(TagConstants.STRIKE, SpanTagCssApplier.class);
        mapping.putMapping(TagConstants.STRONG, SpanTagCssApplier.class);
        mapping.putMapping(TagConstants.SUB, SpanTagCssApplier.class);
        mapping.putMapping(TagConstants.SUP, SpanTagCssApplier.class);
        mapping.putMapping(TagConstants.TD, TdTagCssApplier.class);
        mapping.putMapping(TagConstants.TH, TdTagCssApplier.class);
        mapping.putMapping(TagConstants.TIME, SpanTagCssApplier.class);
        mapping.putMapping(TagConstants.TR, TrTagCssApplier.class);
        mapping.putMapping(TagConstants.U, SpanTagCssApplier.class);
        mapping.putMapping(TagConstants.UL, UlOlTagCssApplier.class);
        mapping.putMapping(TagConstants.VAR, SpanTagCssApplier.class);

        mapping.putMapping(TagConstants.LI, CssConstants.INLINE, SpanTagCssApplier.class);
        mapping.putMapping(TagConstants.DD, CssConstants.INLINE, SpanTagCssApplier.class);
        mapping.putMapping(TagConstants.DT, CssConstants.INLINE, SpanTagCssApplier.class);
    }

    static TagProcessorMapping getDefaultCssApplierMapping() {
        return mapping;
    }


}
