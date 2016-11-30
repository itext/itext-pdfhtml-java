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

import com.itextpdf.html2pdf.attach.impl.tags.*;
import com.itextpdf.html2pdf.html.TagConstants;
import com.itextpdf.html2pdf.html.node.IElementNode;

// TODO add possibility to register operators-->See DefaultTagWorkerFactory
@Deprecated
public class TagWorkerFactory {
    @Deprecated
    public static ITagWorker getTagWorker(IElementNode tag, ProcessorContext context) {
        switch (tag.name()) {
            case TagConstants.A:
                return new ATagWorker(tag, context);
            case TagConstants.ARTICLE:
            case TagConstants.ASIDE:
                return new DivTagWorker(tag, context);
            case TagConstants.B:
                return new SpanTagWorker(tag, context);
            case TagConstants.BLOCKQUOTE:
                return new DivTagWorker(tag, context);
            case TagConstants.BODY:
                return new BodyTagWorker(tag, context);
            case TagConstants.BR:
                return new BrTagWorker(tag, context);
            case TagConstants.CITE:
            case TagConstants.CODE:
                return new SpanTagWorker(tag, context);
            case TagConstants.DIV:
                return new DivTagWorker(tag, context);
            case TagConstants.DD:
                return new DdTagWorker(tag, context);
            case TagConstants.DL:
                return new DlTagWorker(tag, context);
            case TagConstants.DT:
                return new DtTagWorker(tag, context);
            case TagConstants.EM:
                return new SpanTagWorker(tag, context);
            case TagConstants.FOOTER:
                return new DivTagWorker(tag, context);
            case TagConstants.HEADER:
                return new DivTagWorker(tag, context);
            case TagConstants.HR:
                return new HrTagWorker(tag, context);
            case TagConstants.HTML:
                return new HtmlTagWorker(tag, context);
            case TagConstants.I:
                return new SpanTagWorker(tag, context);
            case TagConstants.IMG:
                return new ImgTagWorker(tag, context);
            case TagConstants.LI:
                return new LiTagWorker(tag, context);
            case TagConstants.MAIN:
                return new DivTagWorker(tag, context);
            case TagConstants.META:
                return new MetaTagWorker(tag, context);
            case TagConstants.NAV:
                return new DivTagWorker(tag, context);
            case TagConstants.OL:
                return new UlOlTagWorker(tag, context);
            case TagConstants.H1:
            case TagConstants.H2:
            case TagConstants.H3:
            case TagConstants.H4:
            case TagConstants.H5:
            case TagConstants.H6:
            case TagConstants.P:
                return new PTagWorker(tag, context);
            case TagConstants.PRE:
                return new PTagWorker(tag, context);
            case TagConstants.Q:
                return new SpanTagWorker(tag, context);
            case TagConstants.SECTION:
                return new DivTagWorker(tag, context);
            case TagConstants.SMALL:
                return new SpanTagWorker(tag, context);
            case TagConstants.SPAN:
                return new SpanTagWorker(tag, context);
            case TagConstants.STRIKE:
                return new SpanTagWorker(tag, context);
            case TagConstants.STRONG:
                return new SpanTagWorker(tag, context);
            case TagConstants.SUB:
            case TagConstants.SUP:
                return new SpanTagWorker(tag, context);
            case TagConstants.TABLE:
                return new TableTagWorker(tag, context);
            case TagConstants.TFOOT:
                TableTagWorker footerTagWorker = new TableTagWorker(tag, context);
                footerTagWorker.setFooter();
                return footerTagWorker;
            case TagConstants.THEAD:
                TableTagWorker headerTagWorker = new TableTagWorker(tag, context);
                headerTagWorker.setHeader();
                return headerTagWorker;
            case TagConstants.TIME:
                return new SpanTagWorker(tag, context);
            case TagConstants.TITLE:
                return new TitleTagWorker(tag, context);
            case TagConstants.TD:
            case TagConstants.TH:
                return new TdTagWorker(tag, context);
            case TagConstants.TR:
                return new TrTagWorker(tag, context);
            case TagConstants.U:
                return new SpanTagWorker(tag, context);
            case TagConstants.UL:
                return new UlOlTagWorker(tag, context);
        }
        return null;
    }

}
