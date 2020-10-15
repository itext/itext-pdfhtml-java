package com.itextpdf.html2pdf.css.resolve.func.counter;

import com.itextpdf.styledxmlparser.node.INode;
import com.itextpdf.styledxmlparser.node.impl.jsoup.node.JsoupElementNode;

/**
 * {@link JsoupElementNode} implementation for page target-counters.
 */
public class PageTargetCountElementNode extends PageCountElementNode {

    /**
     * The target from which page will be taken.
     */
    private final String target;

    /**
     * Creates a new {@link PageTargetCountElementNode} instance.
     *
     * @param parent the parent node
     * @param target the target from which page will be taken.
     */
    public PageTargetCountElementNode(INode parent, String target) {
        super(false, parent);
        this.target = target;
    }

    /**
     * Checks if the node represents the total page count.
     *
     * @return true, if the node represents the total page count
     */
    public String getTarget() {
        return target;
    }
}
