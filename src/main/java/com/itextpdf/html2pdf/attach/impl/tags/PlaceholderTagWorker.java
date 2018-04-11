package com.itextpdf.html2pdf.attach.impl.tags;

import com.itextpdf.html2pdf.attach.ITagWorker;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.html.node.IElementNode;
import com.itextpdf.layout.IPropertyContainer;

/**
 * TagWorker class for the {@code input}'s placeholder.
 */
public class PlaceholderTagWorker implements ITagWorker {

    public PlaceholderTagWorker(IElementNode element, ProcessorContext context) {
        // Do nothing here
    }

    @Override
    public void processEnd(IElementNode element, ProcessorContext context) {
        // Do nothing here
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
        return null;
    }
}
