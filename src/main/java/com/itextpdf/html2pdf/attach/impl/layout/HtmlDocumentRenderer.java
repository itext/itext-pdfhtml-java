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
package com.itextpdf.html2pdf.attach.impl.layout;

import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.css.page.PageContextConstants;
import com.itextpdf.html2pdf.css.resolve.ICssResolver;
import com.itextpdf.html2pdf.html.node.INode;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.layout.LayoutArea;
import com.itextpdf.layout.layout.LayoutPosition;
import com.itextpdf.layout.layout.LayoutResult;
import com.itextpdf.layout.property.FloatPropertyValue;
import com.itextpdf.layout.property.Property;
import com.itextpdf.layout.renderer.DocumentRenderer;
import com.itextpdf.layout.renderer.IRenderer;

import java.util.List;

/**
 * The DocumentRenderer class for HTML.
 */
public class HtmlDocumentRenderer extends DocumentRenderer {

    /**
     * The Constant TRIM_LAST_BLANK_PAGE.
     * In a future version, we might want to expose this value to the users,
     * or make it a public setting of the HTML renderer.
     */
    private static final boolean TRIM_LAST_BLANK_PAGE = true;

    /** The page context processor for the first page. */
    private PageContextProcessor firstPageProc;

    /** The page context processor for all left pages. */
    private PageContextProcessor leftPageProc;

    /** The page context processor for all right pages. */
    private PageContextProcessor rightPageProc;
    /**
     * Indicates if the current page is even.
     * Important: this number may differ from the result you get checking
     * if the number of pages in the document is even or not, because
     * the first page break-before might change right page to left (in ltr cases),
     * but a blank page will not be added.
     */
    private boolean currentPageEven = true;

    /**
     * The waiting element, an child element is kept waiting for the
     * next element to process the "keep with previous" property.
     */
    private IRenderer waitingElement;

    /**
     * Indicates if the first blank pages caused by a break-before-first
     * element should be trimmed.
     */
    private boolean shouldTrimFirstBlankPagesCausedByBreakBeforeFirstElement = true;

    /** Indicates if anything was added to the current area. */
    private boolean anythingAddedToCurrentArea = false;

    /** The estimated number of pages. */
    private int estimatedNumberOfPages;

    /**
     * Instantiates a new {@link HtmlDocumentRenderer} instance.
     *
     * @param document an iText {@link Document} instance
     * @param immediateFlush the immediate flush indicator
     */
    public HtmlDocumentRenderer(Document document, boolean immediateFlush) {
        super(document, immediateFlush);
    }

    /**
     * Processes the page rules.
     *
     * @param rootNode the root node
     * @param cssResolver the CSS resolver
     * @param context the processor context
     */
    public void processPageRules(INode rootNode, ICssResolver cssResolver, ProcessorContext context) {
        PageContextProperties firstPageProps = PageContextProperties.resolve(rootNode, cssResolver, context.getCssContext(),
                PageContextConstants.FIRST, PageContextConstants.RIGHT);
        // TODO in documents with set to rtl on root document, first page is considered as left
        PageContextProperties leftPageProps = PageContextProperties.resolve(rootNode, cssResolver, context.getCssContext(),
                PageContextConstants.LEFT);
        PageContextProperties rightPageProps = PageContextProperties.resolve(rootNode, cssResolver, context.getCssContext(),
                PageContextConstants.RIGHT);

        PageSize defaultPageSize = document.getPdfDocument().getDefaultPageSize();
        firstPageProc = new PageContextProcessor(firstPageProps, context, defaultPageSize);
        leftPageProc = new PageContextProcessor(leftPageProps, context, defaultPageSize);
        rightPageProc = new PageContextProcessor(rightPageProps, context, defaultPageSize);
    }

    /* (non-Javadoc)
     * @see com.itextpdf.layout.renderer.RootRenderer#addChild(com.itextpdf.layout.renderer.IRenderer)
     */
    @Override
    public void addChild(IRenderer renderer) {
        if (waitingElement != null) {
            if (Boolean.TRUE.equals(renderer.<Boolean>getProperty(Html2PdfProperty.KEEP_WITH_PREVIOUS))) {
                waitingElement.setProperty(Property.KEEP_WITH_NEXT, true);
            }
            super.addChild(waitingElement);
            // After we have added any child, we should not trim first pages because of break before element, even if the added child had zero height
            shouldTrimFirstBlankPagesCausedByBreakBeforeFirstElement = false;
            waitingElement = null;
        }
        waitingElement = renderer;

        FloatPropertyValue floatPropertyValue = renderer.<FloatPropertyValue>getProperty(Property.FLOAT);
        Integer position = renderer.<Integer>getProperty(Property.POSITION);
        if ((position != null && position == LayoutPosition.ABSOLUTE) || (floatPropertyValue != null && !floatPropertyValue.equals(FloatPropertyValue.NONE))) {
            waitingElement = null;
            super.addChild(renderer);
        }
    }

    /* (non-Javadoc)
     * @see com.itextpdf.layout.renderer.DocumentRenderer#close()
     */
    @Override
    public void close() {
        if (waitingElement != null) {
            IRenderer r = this.waitingElement;
            waitingElement = null;
            super.addChild(r);
        }
        super.close();
        if (TRIM_LAST_BLANK_PAGE) {
            PdfDocument pdfDocument = document.getPdfDocument();
            if (pdfDocument.getNumberOfPages() > 1) {
                PdfPage lastPage = pdfDocument.getLastPage();
                if (lastPage.getContentStreamCount() == 1 && lastPage.getContentStream(0).getOutputStream().getCurrentPos() <= 0) {
                    // Remove last empty page
                    pdfDocument.removePage(pdfDocument.getNumberOfPages());
                }
            }
        }
    }

    /* (non-Javadoc)
     * @see com.itextpdf.layout.renderer.DocumentRenderer#getNextRenderer()
     */
    @Override
    public IRenderer getNextRenderer() {
        // Process waiting element to get the correct number of pages
        if (waitingElement != null) {
            super.addChild(waitingElement);
            waitingElement = null;
        }
        HtmlDocumentRenderer relayoutRenderer = new HtmlDocumentRenderer(document, immediateFlush);
        relayoutRenderer.firstPageProc = firstPageProc;
        relayoutRenderer.leftPageProc = leftPageProc;
        relayoutRenderer.rightPageProc = rightPageProc;
        relayoutRenderer.estimatedNumberOfPages = currentPageNumber;
        return relayoutRenderer;
    }

    /* (non-Javadoc)
     * @see com.itextpdf.layout.renderer.DocumentRenderer#updateCurrentArea(com.itextpdf.layout.layout.LayoutResult)
     */
    @Override
    protected LayoutArea updateCurrentArea(LayoutResult overflowResult) {
        AreaBreak areaBreak = overflowResult != null ? overflowResult.getAreaBreak() : null;
        if (areaBreak instanceof HtmlPageBreak) {
            HtmlPageBreakType htmlPageBreakType = ((HtmlPageBreak) areaBreak).getBreakType();
            if (shouldTrimFirstBlankPagesCausedByBreakBeforeFirstElement && currentArea != null &&
                    overflowResult.getStatus() == LayoutResult.NOTHING && currentArea.isEmptyArea() && currentArea.getPageNumber() == 1) {
                // Remove blank page that was added just to have area for elements to layout on.
                // Now we will add a page with dimensions and all the stuff that is requested by page-break-before
                document.getPdfDocument().removePage(1);
                currentPageNumber = 0;
                overflowResult = null;
                currentArea = null;
                shouldTrimFirstBlankPagesCausedByBreakBeforeFirstElement = false;

                if (HtmlPageBreakType.LEFT.equals(htmlPageBreakType) && isCurrentPageLeft() || HtmlPageBreakType.RIGHT.equals(htmlPageBreakType) && isCurrentPageRight()){
                    currentPageEven = !currentPageEven; // hack to change the "evenness" of the first page without adding an unnecessary blank page
                }
            }

            anythingAddedToCurrentArea = anythingAddedToCurrentArea || overflowResult != null && overflowResult.getStatus() == LayoutResult.PARTIAL;

            if (HtmlPageBreakType.ALWAYS.equals(htmlPageBreakType)) {
                LayoutArea nextArea = currentArea;
                if (anythingAddedToCurrentArea || currentArea == null) {
                    nextArea = super.updateCurrentArea(overflowResult);
                }
                anythingAddedToCurrentArea = false;
                return nextArea;
            } else if (HtmlPageBreakType.LEFT.equals(htmlPageBreakType)) {
                LayoutArea nextArea = currentArea;
                if (anythingAddedToCurrentArea || !isCurrentPageLeft() || currentArea == null) {
                    do {
                        nextArea = super.updateCurrentArea(overflowResult);
                    } while (!isCurrentPageLeft());
                }
                anythingAddedToCurrentArea = false;
                return nextArea;
            } else if (HtmlPageBreakType.RIGHT.equals(htmlPageBreakType)) {
                LayoutArea nextArea = currentArea;
                if (anythingAddedToCurrentArea || !isCurrentPageRight() || currentArea == null) {
                    do {
                        nextArea = super.updateCurrentArea(overflowResult);
                    } while (!isCurrentPageRight());
                }
                anythingAddedToCurrentArea = false;
                return nextArea;
            }
        }
        anythingAddedToCurrentArea = false;
        return super.updateCurrentArea(overflowResult);
    }

    /* (non-Javadoc)
     * @see com.itextpdf.layout.renderer.RootRenderer#shrinkCurrentAreaAndProcessRenderer(com.itextpdf.layout.renderer.IRenderer, java.util.List, com.itextpdf.layout.layout.LayoutResult)
     */
    @Override
    protected void shrinkCurrentAreaAndProcessRenderer(IRenderer renderer, List<IRenderer> resultRenderers, LayoutResult result) {
        if (renderer != null) {
            anythingAddedToCurrentArea = true;
        }
        super.shrinkCurrentAreaAndProcessRenderer(renderer, resultRenderers, result);
    }

    /* (non-Javadoc)
     * @see com.itextpdf.layout.renderer.DocumentRenderer#addNewPage(com.itextpdf.kernel.geom.PageSize)
     */
    @Override
    protected PageSize addNewPage(PageSize customPageSize) {
        PdfPage addedPage;

        int numberOfPages = document.getPdfDocument().getNumberOfPages();
        PageContextProcessor nextProcessor = getNextPageProcessor(numberOfPages == 0);
        if (customPageSize != null) {
            addedPage = document.getPdfDocument().addNewPage(customPageSize);
        } else {
            addedPage = document.getPdfDocument().addNewPage(nextProcessor.getPageSize());
        }

        currentPageEven = !currentPageEven;

        nextProcessor.processNewPage(addedPage, this);

        float[] margins = nextProcessor.computeLayoutMargins();
        document.setMargins(margins[0], margins[1], margins[2], margins[3]);
        return new PageSize(addedPage.getTrimBox());
    }

    /**
     * Gets the estimated number of pages.
     *
     * @return the estimated number of pages
     */
    int getEstimatedNumberOfPages() {
        return estimatedNumberOfPages;
	}

    /**
     * Gets the next page processor.
     *
     * @param firstPage the first page
     * @return the next page processor
     */
    private PageContextProcessor getNextPageProcessor(boolean firstPage) {
        // If first page, but break-before: left for ltr is present, we should use left page instead of first
        if (firstPage && currentPageEven) {
            return firstPageProc;
        } else if (isCurrentPageRight()) {
            return leftPageProc;
        } else {
            return rightPageProc;
        }
    }

    /**
     * Checks if the current page is a left page.
     *
     * @return true, if is current page left
     */
    private boolean isCurrentPageLeft() {
        // TODO rtl
        return currentPageEven;
    }

    /**
     * Checks if the current page is a right page.
     *
     * @return true, if is current page right
     */
    private boolean isCurrentPageRight() {
        return !isCurrentPageLeft();
    }

}
