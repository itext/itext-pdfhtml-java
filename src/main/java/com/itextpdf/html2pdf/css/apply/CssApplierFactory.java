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

import com.itextpdf.html2pdf.css.apply.impl.*;
import com.itextpdf.html2pdf.html.TagConstants;

@Deprecated
public class CssApplierFactory {

    @Deprecated
    public static ICssApplier getCssApplier(String tag) {
        switch (tag) {
            case TagConstants.A:
                return new SpanTagCssApplier();
            case TagConstants.ARTICLE:
                return new BlockCssApplier();
            case TagConstants.B:
                return new SpanTagCssApplier();
            case TagConstants.ASIDE:
                return new BlockCssApplier();
            case TagConstants.BLOCKQUOTE:
                return new BlockCssApplier();
            case TagConstants.BODY:
                return new BodyTagCssApplier();
            case TagConstants.CITE:
            case TagConstants.CODE:
            case TagConstants.EM:
                return new SpanTagCssApplier();
            case TagConstants.DT:
            case TagConstants.DD:
            case TagConstants.DIV:
            case TagConstants.FOOTER:
            case TagConstants.H1:
            case TagConstants.H2:
            case TagConstants.H3:
            case TagConstants.H4:
            case TagConstants.H5:
            case TagConstants.H6:
            case TagConstants.HEADER:
            case TagConstants.HR:
            case TagConstants.IMG:
            case TagConstants.MAIN:
            case TagConstants.NAV:
            case TagConstants.P:
            case TagConstants.SECTION:
            case TagConstants.TABLE:
            case TagConstants.TFOOT:
            case TagConstants.THEAD:
                return new BlockCssApplier();
            case TagConstants.DL:
                return new DlTagCssApplier();
            case TagConstants.HTML:
                return new HtmlTagCssApplier();
            case TagConstants.I:
                return new SpanTagCssApplier();
            case TagConstants.LI:
                return new LiTagCssApplier();
            case TagConstants.OL:
                return new UlOlTagCssApplier();
            case TagConstants.PRE:
                return new BlockCssApplier();
            case TagConstants.Q:
                return new SpanTagCssApplier();
            case TagConstants.SMALL:
                return new SpanTagCssApplier();
			case TagConstants.SPAN:
                return new SpanTagCssApplier();
            case TagConstants.STRIKE:
                return new SpanTagCssApplier();
            case TagConstants.STRONG:
                return new SpanTagCssApplier();
            case TagConstants.SUB:
            case TagConstants.SUP:
                return new SpanTagCssApplier();
            case TagConstants.TD:
            case TagConstants.TH:
                return new TdTagCssApplier();
            case TagConstants.TIME:
                return new SpanTagCssApplier();
            case TagConstants.U:
                return new SpanTagCssApplier();
            case TagConstants.UL:
                return new UlOlTagCssApplier();
            default:
                return null;
        }
    }

}
