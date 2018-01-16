package com.itextpdf.html2pdf.attach.impl.tags;

import com.itextpdf.html2pdf.attach.ITagWorker;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.attach.impl.layout.RunningElementContainer;
import com.itextpdf.html2pdf.attach.impl.layout.RunningElement;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.html.node.IElementNode;
import com.itextpdf.layout.IPropertyContainer;

/**
 * TagWorker class for the running elements taken out of the normal flow.
 */
public class RunningElementTagWorker implements ITagWorker, IDisplayAware {
    private RunningElement runningElement;

    public RunningElementTagWorker(RunningElementContainer runningElementContainer) {
        runningElement = new RunningElement(runningElementContainer);
    }

    @Override
    public void processEnd(IElementNode element, ProcessorContext context) {

    }

    @Override
    public boolean processContent(String content, ProcessorContext context) {
        return false;
    }

    @Override
    public boolean processTagChild(ITagWorker childTagWorker, ProcessorContext context) {
        return false;
    }

    @Override
    public IPropertyContainer getElementResult() {
        return runningElement;
    }

    @Override
    public String getDisplay() {
        return CssConstants.INLINE_BLOCK;
    }
}
