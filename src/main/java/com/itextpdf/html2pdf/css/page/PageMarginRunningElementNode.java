package com.itextpdf.html2pdf.css.page;

import com.itextpdf.html2pdf.html.node.INode;
import java.util.List;

/**
 * Wrapper {@link INode} serving as a placeholder for running element.
 */
public class PageMarginRunningElementNode implements INode {

    private String runningElementName;
    private String runningElementOccurrence;

    public PageMarginRunningElementNode(String runningElementName, String runningElementOccurrence) {
        this.runningElementName = runningElementName;
        this.runningElementOccurrence = runningElementOccurrence;
    }

    @Override
    public List<INode> childNodes() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addChild(INode node) {
        throw new UnsupportedOperationException();
    }

    @Override
    public INode parentNode() {
        throw new UnsupportedOperationException();
    }

    public String getRunningElementName() {
        return runningElementName;
    }

    public String getRunningElementOccurrence() {
        return runningElementOccurrence;
    }
}
