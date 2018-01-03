package com.itextpdf.html2pdf.attach.impl.layout;

import com.itextpdf.kernel.pdf.tagging.StandardRoles;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.layout.LayoutContext;
import com.itextpdf.layout.layout.LayoutResult;
import com.itextpdf.layout.renderer.DivRenderer;
import com.itextpdf.layout.renderer.DrawContext;
import com.itextpdf.layout.renderer.IRenderer;

/**
 * An {@link com.itextpdf.layout.element.IElement} that serves as a placeholder for removed running element
 * from the normal flow. This element is designed to register where particular running element would have been placed.
 */
public class RunningElement extends Div {
    private RunningElementContainer runningElementContainer;

    /**
     * Creates a new instance of {@link RunningElement}.
     * @param runningElementContainer a container for the actual running element removed from the normal flow.
     */
    public RunningElement(RunningElementContainer runningElementContainer) {
        this.runningElementContainer = runningElementContainer;
        getAccessibilityProperties().setRole(StandardRoles.ARTIFACT);
    }

    @Override
    protected IRenderer makeNewRenderer() {
        return new RunningElementRenderer(this, runningElementContainer);
    }

    /**
     * It's an empty div so it's not expected to be ever split between areas.
     */
    static class RunningElementRenderer extends DivRenderer {
        private RunningElementContainer runningElementContainer;
        private boolean isFirstOnRootArea;

        public RunningElementRenderer(Div modelElement, RunningElementContainer runningElementContainer) {
            super(modelElement);
            this.runningElementContainer = runningElementContainer;
        }

        @Override
        public LayoutResult layout(LayoutContext layoutContext) {
            this.isFirstOnRootArea = isFirstOnRootArea();
            return super.layout(layoutContext);
        }

        @Override
        public void draw(DrawContext drawContext) {
            runningElementContainer.setOccurrencePage(getOccupiedArea().getPageNumber(), isFirstOnRootArea);
            super.draw(drawContext);
        }
    }
}
