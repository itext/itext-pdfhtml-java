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
package com.itextpdf.html2pdf.attach.impl.layout;

import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.css.page.PageContextConstants;
import com.itextpdf.html2pdf.css.resolve.ICssResolver;
import com.itextpdf.html2pdf.html.node.INode;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.renderer.DocumentRenderer;

public class HtmlDocumentRenderer extends DocumentRenderer {
    private PageContextProcessor firstPageProc;
    private PageContextProcessor leftPageProc;
    private PageContextProcessor rightPageProc;

    public HtmlDocumentRenderer(Document document, boolean immediateFlush) {
        super(document, immediateFlush);
    }

    public void processPageRules(INode rootNode, ICssResolver cssResolver, ProcessorContext context) {
        PageContextProperties firstPageProps = PageContextProperties.resolve(rootNode, cssResolver, context.getCssContext(),
                PageContextConstants.FIRST, PageContextConstants.RIGHT); // TODO in documents with set to rtl on root document, first page is considered as left 
        PageContextProperties leftPageProps = PageContextProperties.resolve(rootNode, cssResolver, context.getCssContext(),
                PageContextConstants.LEFT);
        PageContextProperties rightPageProps = PageContextProperties.resolve(rootNode, cssResolver, context.getCssContext(),
                PageContextConstants.RIGHT);

        PageSize defaultPageSize = document.getPdfDocument().getDefaultPageSize();
        firstPageProc = new PageContextProcessor(firstPageProps, context, defaultPageSize);
        leftPageProc = new PageContextProcessor(leftPageProps, context, defaultPageSize);
        rightPageProc = new PageContextProcessor(rightPageProps, context, defaultPageSize);
    }

    @Override
    protected PageSize addNewPage(PageSize customPageSize) {
        PdfPage addedPage;

        int numberOfPages = document.getPdfDocument().getNumberOfPages();
        PageContextProcessor nextProcessor = getNextPageProcessor(numberOfPages);
        if (customPageSize != null) {
            addedPage = document.getPdfDocument().addNewPage(customPageSize);
        } else {
            addedPage = document.getPdfDocument().addNewPage(nextProcessor.getPageSize());
        }

        nextProcessor.processNewPage(addedPage);

        float[] margins = nextProcessor.computeLayoutMargins();
        document.setMargins(margins[0], margins[1], margins[2], margins[3]);
        return new PageSize(addedPage.getTrimBox());
    }

    private PageContextProcessor getNextPageProcessor(int numberOfPages) {
        if (numberOfPages == 0) {
            return firstPageProc;
        } else if (numberOfPages % 2 == 1) {
            return leftPageProc;
        } else {
            return rightPageProc;
        }
    }


}
