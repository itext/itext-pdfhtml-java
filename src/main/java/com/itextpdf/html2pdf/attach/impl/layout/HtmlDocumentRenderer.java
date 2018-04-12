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
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.tagging.StandardRoles;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.layout.LayoutArea;
import com.itextpdf.layout.layout.LayoutPosition;
import com.itextpdf.layout.layout.LayoutResult;
import com.itextpdf.layout.property.FloatPropertyValue;
import com.itextpdf.layout.property.Property;
import com.itextpdf.layout.renderer.DocumentRenderer;
import com.itextpdf.layout.renderer.IRenderer;
import com.itextpdf.layout.renderer.ParagraphRenderer;
import java.util.List;
import java.util.Map;

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
     * Indicates if even pages are considered as left or right.
     * Important: this value might differ depending on page progression direction,
     * as well as because the first page break-before might change right page to left (in ltr cases),
     * but a blank page will not be added.
     */
    private boolean evenPagesAreLeft = true;

    private PageMarginBoxesDrawingHandler handler;

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
     * @param document       an iText {@link Document} instance
     * @param immediateFlush the immediate flush indicator
     */
    public HtmlDocumentRenderer(Document document, boolean immediateFlush) {
        super(document, immediateFlush);
    }

    /**
     * Processes the page rules.
     *
     * @param rootNode    the root node
     * @param cssResolver the CSS resolver
     * @param context     the processor context
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
        float[] defaultPageMargins = {document.getTopMargin(), document.getRightMargin(), document.getBottomMargin(), document.getRightMargin()};
        firstPageProc = new PageContextProcessor(firstPageProps, context, defaultPageSize, defaultPageMargins);
        leftPageProc = new PageContextProcessor(leftPageProps, context, defaultPageSize, defaultPageMargins);
        rightPageProc = new PageContextProcessor(rightPageProps, context, defaultPageSize, defaultPageMargins);

        handler = new HtmlDocumentRenderer.PageMarginBoxesDrawingHandler().setHtmlDocumentRenderer(this);
        document.getPdfDocument().addEventHandler(PdfDocumentEvent.END_PAGE, handler);
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
            IRenderer element = waitingElement;
            waitingElement = null;
            super.addChild(element);
            if (!isRunningElementsOnly(element)) {
                // After we have added any child, we should not trim first pages because of break before element, even if the added child had zero height
                shouldTrimFirstBlankPagesCausedByBreakBeforeFirstElement = false;
            }
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
        document.getPdfDocument().removeEventHandler(PdfDocumentEvent.END_PAGE, handler);
        for (int i = 1; i <= document.getPdfDocument().getNumberOfPages(); ++i) {
            PdfPage page = document.getPdfDocument().getPage(i);
            if (!page.isFlushed()) {
                handler.processPage(document.getPdfDocument(), i);
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
        PageSize defaultPageSize = document.getPdfDocument().getDefaultPageSize();
        float[] defaultPageMargins = {document.getTopMargin(), document.getRightMargin(), document.getBottomMargin(), document.getRightMargin()};
        relayoutRenderer.firstPageProc = firstPageProc.reset(defaultPageSize, defaultPageMargins);
        relayoutRenderer.leftPageProc = leftPageProc.reset(defaultPageSize, defaultPageMargins);
        relayoutRenderer.rightPageProc = rightPageProc.reset(defaultPageSize, defaultPageMargins);
        relayoutRenderer.estimatedNumberOfPages = currentPageNumber;
        relayoutRenderer.handler = handler.setHtmlDocumentRenderer(relayoutRenderer);
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

                if (HtmlPageBreakType.LEFT.equals(htmlPageBreakType) && !isPageLeft(1) || HtmlPageBreakType.RIGHT.equals(htmlPageBreakType) && !isPageRight(1)) {
                    evenPagesAreLeft = !evenPagesAreLeft; // hack to change the "evenness" of the first page without adding an unnecessary blank page
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
                if (anythingAddedToCurrentArea || currentArea == null || !isPageLeft(currentPageNumber)) {
                    do {
                        nextArea = super.updateCurrentArea(overflowResult);
                    } while (!isPageLeft(currentPageNumber));
                }
                anythingAddedToCurrentArea = false;
                return nextArea;
            } else if (HtmlPageBreakType.RIGHT.equals(htmlPageBreakType)) {
                LayoutArea nextArea = currentArea;
                if (anythingAddedToCurrentArea || currentArea == null || !isPageRight(currentPageNumber)) {
                    do {
                        nextArea = super.updateCurrentArea(overflowResult);
                    } while (!isPageRight(currentPageNumber));
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
        PageContextProcessor nextProcessor = getPageProcessor(numberOfPages + 1);
        if (customPageSize != null) {
            addedPage = document.getPdfDocument().addNewPage(customPageSize);
        } else {
            addedPage = document.getPdfDocument().addNewPage(nextProcessor.getPageSize());
        }

        nextProcessor.processNewPage(addedPage);
        float[] margins = nextProcessor.computeLayoutMargins();
        float[] htmlStylesWidth = applyHtmlBodyMarginsBorders(addedPage, margins, false);
        float[] simulatedMarginForBody = new float[4];
        for (int i = 0; i < 4; i++)
            simulatedMarginForBody[i] = margins[i] + htmlStylesWidth[i];
        float[] bodyStylesWidth = applyHtmlBodyMarginsBorders(addedPage, simulatedMarginForBody, true);

        setProperty(Property.MARGIN_TOP, margins[0] + htmlStylesWidth[0] + bodyStylesWidth[0]);
        setProperty(Property.MARGIN_RIGHT, margins[1] + htmlStylesWidth[1] + bodyStylesWidth[1]);
        setProperty(Property.MARGIN_BOTTOM, margins[2] + htmlStylesWidth[2] + bodyStylesWidth[2]);
        setProperty(Property.MARGIN_LEFT, margins[3] + htmlStylesWidth[3] + bodyStylesWidth[3]);

        return new PageSize(addedPage.getTrimBox());
    }

    private float[] applyHtmlBodyMarginsBorders(PdfPage page, float[] defaultMargins, boolean body) {
        int htmlOrBodyStylingProperty = body ? Html2PdfProperty.BODY_STYLING : Html2PdfProperty.HTML_STYLING;
        BodyHtmlStylesContainer styles = ((IPropertyContainer) document).<BodyHtmlStylesContainer>getProperty(htmlOrBodyStylingProperty);
        if (styles == null)
            return new float[4];

        if (styles.hasBordersToDraw()) {
            Div pageBordersSimulation;
            pageBordersSimulation = new Div().setFillAvailableArea(true);

            for (Map.Entry<Integer, Object> entry : styles.properties.entrySet()) {
                pageBordersSimulation.setProperty(entry.getKey(), entry.getValue());
            }

            pageBordersSimulation.getAccessibilityProperties().setRole(StandardRoles.ARTIFACT);

            Canvas canvas = new Canvas(new PdfCanvas(page), page.getDocument(), page.getTrimBox()
                    .applyMargins(defaultMargins[0], defaultMargins[1], defaultMargins[2], defaultMargins[3], false));
            canvas.enableAutoTagging(page);
            canvas.add(pageBordersSimulation);
            canvas.close();
        }
        return styles.getTotalWidth();
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
     * Gets a page processor for the page.
     *
     * @param pageNum the number of the page for which the {@link PageContextProcessor} shall be obtained
     * @return a page processor
     */
    private PageContextProcessor getPageProcessor(int pageNum) {
        // If first page, but break-before: left for ltr is present, we should use left page instead of first
        if (pageNum == 1 && evenPagesAreLeft) {
            return firstPageProc;
        } else if (isPageLeft(pageNum)) {
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
    private boolean isPageLeft(int pageNum) {
        // TODO rtl
        boolean pageIsEven = pageNum % 2 == 0;
        return evenPagesAreLeft == pageIsEven;
    }

    /**
     * Checks if the current page is a right page.
     *
     * @return true, if is current page right
     */
    private boolean isPageRight(int pageNum) {
        return !isPageLeft(pageNum);
    }

    private static boolean isRunningElementsOnly(IRenderer waitingElement) {
        boolean res;
        if (res = waitingElement instanceof ParagraphRenderer && !waitingElement.getChildRenderers().isEmpty()) {
            List<IRenderer> childRenderers = waitingElement.getChildRenderers();
            int i = 0;
            while (res && i < childRenderers.size()) {
                res = childRenderers.get(i++) instanceof RunningElement.RunningElementRenderer;
            }
        }
        return res;
    }

    private static class PageMarginBoxesDrawingHandler implements IEventHandler {
        private HtmlDocumentRenderer htmlDocumentRenderer;

        PageMarginBoxesDrawingHandler setHtmlDocumentRenderer(HtmlDocumentRenderer htmlDocumentRenderer) {
            this.htmlDocumentRenderer = htmlDocumentRenderer;
            return this;
        }

        @Override
        public void handleEvent(Event event) {
            if (event instanceof PdfDocumentEvent) {
                PdfPage page = ((PdfDocumentEvent) event).getPage();
                PdfDocument pdfDoc = ((PdfDocumentEvent) event).getDocument();
                int pageNumber = pdfDoc.getPageNumber(page);
                processPage(pdfDoc, pageNumber);
            }
        }

        void processPage(PdfDocument pdfDoc, int pageNumber) {
            PageContextProcessor pageProcessor = htmlDocumentRenderer.getPageProcessor(pageNumber);
            pageProcessor.processPageEnd(pageNumber, pdfDoc, htmlDocumentRenderer);
        }
    }
}
