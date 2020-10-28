/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2020 iText Group NV
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
import com.itextpdf.html2pdf.attach.impl.layout.HtmlBodyStylesApplierHandler.LowestAndHighest;
import com.itextpdf.html2pdf.attach.impl.layout.HtmlBodyStylesApplierHandler.PageStylesProperties;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.layout.LayoutArea;
import com.itextpdf.layout.layout.LayoutPosition;
import com.itextpdf.layout.layout.LayoutResult;
import com.itextpdf.layout.property.FloatPropertyValue;
import com.itextpdf.layout.property.Property;
import com.itextpdf.layout.renderer.DocumentRenderer;
import com.itextpdf.layout.renderer.IRenderer;
import com.itextpdf.layout.renderer.ParagraphRenderer;
import com.itextpdf.layout.renderer.TargetCounterHandler;
import com.itextpdf.styledxmlparser.css.ICssResolver;
import com.itextpdf.styledxmlparser.css.page.PageContextConstants;
import com.itextpdf.styledxmlparser.node.INode;

import java.util.HashMap;
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

    private PageMarginBoxesDrawingHandler marginBoxesHandler;
    private HtmlBodyStylesApplierHandler htmlBodyHandler;

    private Map<Integer, PageStylesProperties> pageStylesPropertiesMap = new HashMap<>();

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
        htmlBodyHandler = new HtmlBodyStylesApplierHandler(this, pageStylesPropertiesMap);
        document.getPdfDocument().addEventHandler(PdfDocumentEvent.END_PAGE, htmlBodyHandler);
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
        // TODO DEVSIX-4118 in documents with set to rtl on root document, first page is considered as left
        PageContextProperties leftPageProps = PageContextProperties.resolve(rootNode, cssResolver, context.getCssContext(),
                PageContextConstants.LEFT);
        PageContextProperties rightPageProps = PageContextProperties.resolve(rootNode, cssResolver, context.getCssContext(),
                PageContextConstants.RIGHT);

        PageSize defaultPageSize = document.getPdfDocument().getDefaultPageSize();
        float[] defaultPageMargins = {document.getTopMargin(), document.getRightMargin(), document.getBottomMargin(), document.getRightMargin()};
        firstPageProc = new PageContextProcessor(firstPageProps, context, defaultPageSize, defaultPageMargins);
        leftPageProc = new PageContextProcessor(leftPageProps, context, defaultPageSize, defaultPageMargins);
        rightPageProc = new PageContextProcessor(rightPageProps, context, defaultPageSize, defaultPageMargins);

        marginBoxesHandler = new HtmlDocumentRenderer.PageMarginBoxesDrawingHandler().setHtmlDocumentRenderer(this);
        document.getPdfDocument().addEventHandler(PdfDocumentEvent.END_PAGE, marginBoxesHandler);
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
        processWaitingElement();
        super.close();
        trimLastPageIfNecessary();
        document.getPdfDocument().removeEventHandler(PdfDocumentEvent.END_PAGE, marginBoxesHandler);
        document.getPdfDocument().removeEventHandler(PdfDocumentEvent.END_PAGE, htmlBodyHandler);
        for (int i = 1; i <= document.getPdfDocument().getNumberOfPages(); ++i) {
            PdfPage page = document.getPdfDocument().getPage(i);
            if (!page.isFlushed()) {
                marginBoxesHandler.processPage(document.getPdfDocument(), i);
                htmlBodyHandler.processPage(page, i);
            }
        }
    }

    /**
     * Removes event handlers that were added to pdf document when this {@link HtmlDocumentRenderer} was created.
     */
    void removeEventHandlers() {
        document.getPdfDocument().removeEventHandler(PdfDocumentEvent.END_PAGE, htmlBodyHandler);
        // This handler is added in processPageRules method.
        document.getPdfDocument().removeEventHandler(PdfDocumentEvent.END_PAGE, marginBoxesHandler);
    }

    /* (non-Javadoc)
     * @see com.itextpdf.layout.renderer.DocumentRenderer#getNextRenderer()
     */
    @Override
    public IRenderer getNextRenderer() {
        // Process waiting element to get the correct number of pages
        processWaitingElement();
        HtmlDocumentRenderer relayoutRenderer = new HtmlDocumentRenderer(document, immediateFlush);
        PageSize defaultPageSize = document.getPdfDocument().getDefaultPageSize();
        float[] defaultPageMargins = {document.getTopMargin(), document.getRightMargin(), document.getBottomMargin(), document.getRightMargin()};
        relayoutRenderer.firstPageProc = firstPageProc.reset(defaultPageSize, defaultPageMargins);
        relayoutRenderer.leftPageProc = leftPageProc.reset(defaultPageSize, defaultPageMargins);
        relayoutRenderer.rightPageProc = rightPageProc.reset(defaultPageSize, defaultPageMargins);
        relayoutRenderer.estimatedNumberOfPages = currentPageNumber - simulateTrimLastPage();
        relayoutRenderer.marginBoxesHandler = marginBoxesHandler.setHtmlDocumentRenderer(relayoutRenderer);
        relayoutRenderer.targetCounterHandler = new TargetCounterHandler(targetCounterHandler);
        return relayoutRenderer;
    }

    @Override
    public void flush(){
        processWaitingElement();
        super.flush();
    }

    /**
     * Layouts waiting element.
     */
    public void processWaitingElement(){
        if (waitingElement != null) {
            IRenderer r = this.waitingElement;
            waitingElement = null;
            super.addChild(r);
        }
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

    @Override
    protected void flushSingleRenderer(IRenderer resultRenderer) {
        if (!isElementOnNonStaticLayout(resultRenderer)) {
            LayoutArea area = resultRenderer.getOccupiedArea();
            updateLowestAndHighestPoints(area.getBBox(), area.getPageNumber());
        }
        super.flushSingleRenderer(resultRenderer);
    }

    /* (non-Javadoc)
     * @see com.itextpdf.layout.renderer.DocumentRenderer#addNewPage(com.itextpdf.kernel.geom.PageSize)
     */
    @Override
    protected PageSize addNewPage(PageSize customPageSize) {
        PdfPage addedPage;

        int pageNumber = document.getPdfDocument().getNumberOfPages() + 1;
        PageContextProcessor nextProcessor = getPageProcessor(pageNumber);
        if (customPageSize != null) {
            addedPage = document.getPdfDocument().addNewPage(customPageSize);
        } else {
            addedPage = document.getPdfDocument().addNewPage(nextProcessor.getPageSize());
        }

        nextProcessor.processNewPage(addedPage);

        float[] margins = nextProcessor.computeLayoutMargins();
        BodyHtmlStylesContainer[] styles = new BodyHtmlStylesContainer[] {
                ((IPropertyContainer) document).<BodyHtmlStylesContainer>getProperty(Html2PdfProperty.HTML_STYLING),
                ((IPropertyContainer) document).<BodyHtmlStylesContainer>getProperty(Html2PdfProperty.BODY_STYLING)};
        pageStylesPropertiesMap.put(pageNumber, new PageStylesProperties(styles));
        updateDefaultMargins(styles, margins);

        setProperty(Property.MARGIN_TOP, margins[0]);
        setProperty(Property.MARGIN_RIGHT, margins[1]);
        setProperty(Property.MARGIN_BOTTOM, margins[2]);
        setProperty(Property.MARGIN_LEFT, margins[3]);

        return new PageSize(addedPage.getTrimBox());
    }

    boolean shouldAttemptTrimLastPage() {
        return TRIM_LAST_BLANK_PAGE && document.getPdfDocument().getNumberOfPages() > 1;
    }

    void trimLastPageIfNecessary() {
        if (shouldAttemptTrimLastPage()) {
            PdfDocument pdfDocument = document.getPdfDocument();
            PdfPage lastPage = pdfDocument.getLastPage();
            if (lastPage.getContentStreamCount() == 1 && lastPage.getContentStream(0).getOutputStream().getCurrentPos() <= 0) {
                // Remove last empty page
                pdfDocument.removePage(pdfDocument.getNumberOfPages());
            }
        }
    }

    /**
     * Returns the number of pages that will be trimmed on {@link #close()}
     * @return 0 if no pages will be trimmed, or positive number of trimmed pages in case any are trimmed
     */
    int simulateTrimLastPage() {
        if (shouldAttemptTrimLastPage()) {
            int lastPageNumber = document.getPdfDocument().getNumberOfPages();
            // At the moment we only check if some element was positioned on this page
            // However, there might theoretically be an inconsistency with the method that
            // actually does the trimming because that method checks the canvas output only.
            // We might want to simulate drawing on canvas here in the future, or possibly
            // consider invisible elements in the method that actually does the trimming
            boolean willAnyContentBeDrawnOnLastPage = false;
            for (IRenderer renderer : childRenderers) {
                if (renderer.getOccupiedArea().getPageNumber() == lastPageNumber) {
                    willAnyContentBeDrawnOnLastPage = true;
                }
            }
            for (IRenderer renderer : positionedRenderers) {
                if (renderer.getOccupiedArea().getPageNumber() == lastPageNumber) {
                    willAnyContentBeDrawnOnLastPage = true;
                }
            }
            return willAnyContentBeDrawnOnLastPage ? 0 : 1;
        } else {
            return 0;
        }
    }

    /**
     * Gets a page processor for the page.
     *
     * @param pageNum the number of the page for which the {@link PageContextProcessor} shall be obtained
     * @return a page processor
     */
    PageContextProcessor getPageProcessor(int pageNum) {
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
     * Gets the estimated number of pages.
     *
     * @return the estimated number of pages
     */
    int getEstimatedNumberOfPages() {
        return estimatedNumberOfPages;
    }

    private void updateDefaultMargins(BodyHtmlStylesContainer[] styles, float[] defaultMargins) {
        for (int i = 0; i < 2; i++) {
            if (styles[i] != null) {
                for (int j = 0; j < 4; j++) {
                    defaultMargins[j] += styles[i].getTotalWidth()[j];
                }
            }
        }
    }

    private boolean isElementOnNonStaticLayout(IRenderer resultRenderer) {
        boolean nonStaticLayout = false;
        if (resultRenderer.hasProperty(Property.POSITION)) {
            int positionProperty = (int) resultRenderer.<Integer>getProperty(Property.POSITION);
            nonStaticLayout = positionProperty == LayoutPosition.ABSOLUTE || positionProperty == LayoutPosition.FIXED;
        }
        if (!nonStaticLayout && resultRenderer.hasProperty(Property.FLOAT)) {
            FloatPropertyValue floatProperty = resultRenderer.<FloatPropertyValue>getProperty(Property.FLOAT);
            nonStaticLayout = floatProperty == FloatPropertyValue.LEFT || floatProperty == FloatPropertyValue.RIGHT;
        }
        return nonStaticLayout;
    }

    private void updateLowestAndHighestPoints(Rectangle rectangle, int page) {
        if (!pageStylesPropertiesMap.containsKey(page)) {
            return;
        }
        LowestAndHighest currentPagePoints = pageStylesPropertiesMap.get(page).lowestAndHighest;
        if (currentPagePoints == null) {
            pageStylesPropertiesMap.get(page).lowestAndHighest
                    = new LowestAndHighest(rectangle.getY(), rectangle.getY() + rectangle.getHeight());
        } else {
            float newLowestPoint = rectangle.getY();
            float newHighestPoint = rectangle.getY() + rectangle.getHeight();
            currentPagePoints.lowest = Math.min(newLowestPoint, currentPagePoints.lowest);
            currentPagePoints.highest = Math.max(newHighestPoint, currentPagePoints.highest);
        }
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

    /**
     * Checks if the current page is a left page.
     *
     * @return true, if is current page left
     */
    private boolean isPageLeft(int pageNum) {
        // TODO DEVSIX-4118 rtl
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
