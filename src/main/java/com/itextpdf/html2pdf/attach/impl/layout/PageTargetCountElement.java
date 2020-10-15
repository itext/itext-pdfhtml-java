package com.itextpdf.html2pdf.attach.impl.layout;

import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.renderer.IRenderer;

/**
 * {@link Text} implementation to be used for the page target-counter.
 */
public class PageTargetCountElement extends Text {

    private final String target;

    /**
     * Instantiates a new {@link PageTargetCountElement}.
     *
     * @param target name of the corresponding target
     */
    public PageTargetCountElement(String target) {
        super("1234567890");
        this.target = target.replace("'", "").replace("#", "");
    }

    /**
     * Gets element's target.
     *
     * @return target which was specified for this element.
     */
    public String getTarget() {
        return target;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IRenderer makeNewRenderer() {
        return new PageTargetCountRenderer(this);
    }
}
