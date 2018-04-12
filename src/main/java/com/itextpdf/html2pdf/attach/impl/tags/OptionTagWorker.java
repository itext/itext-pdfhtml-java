package com.itextpdf.html2pdf.attach.impl.tags;

import com.itextpdf.html2pdf.attach.ITagWorker;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.attach.impl.layout.Html2PdfProperty;
import com.itextpdf.html2pdf.html.AttributeConstants;
import com.itextpdf.html2pdf.html.impl.jsoup.node.JsoupTextNode;
import com.itextpdf.html2pdf.html.node.IElementNode;
import com.itextpdf.html2pdf.jsoup.nodes.TextNode;

/**
 * TagWorker class for the {@code option} element.
 */
public class OptionTagWorker extends DivTagWorker {
    // extends DivTagWorker for the sake of pseudo elements

    private StringBuilder actualOptionTextContent;

    private String labelAttrVal;
    private boolean labelReplacedContent;
    private boolean fakedContent;

    /**
     * Creates a new {@link OptionTagWorker} instance.
     *
     * @param element the element
     * @param context the context
     */
    public OptionTagWorker(IElementNode element, ProcessorContext context) {
        super(element, context);
        boolean selectedAttr = element.getAttribute(AttributeConstants.SELECTED) != null;
        getElementResult().setProperty(Html2PdfProperty.FORM_FIELD_SELECTED, selectedAttr);
        actualOptionTextContent = new StringBuilder();

        labelAttrVal = element.getAttribute(AttributeConstants.LABEL);
        if (labelAttrVal != null) {
            if (element.childNodes().isEmpty()) {
                // workaround to ensure that processContent method is called
                element.addChild(new JsoupTextNode(new TextNode("", "")));
                fakedContent = true;
            }
        }
    }

    @Override
    public void processEnd(IElementNode element, ProcessorContext context) {
        super.processEnd(element, context);

        String valueAttr = element.getAttribute(AttributeConstants.VALUE);
        String labelAttr = element.getAttribute(AttributeConstants.LABEL);
        String content = actualOptionTextContent.toString();

        if (labelAttr == null) {
            labelAttr = content;
        }
        if (valueAttr == null) {
            valueAttr = content;
        }

        getElementResult().setProperty(Html2PdfProperty.FORM_FIELD_VALUE, valueAttr);
        getElementResult().setProperty(Html2PdfProperty.FORM_FIELD_LABEL, labelAttr);
    }

    @Override
    public boolean processContent(String content, ProcessorContext context) {
        content = content.trim(); // white-space:pre is overridden in chrome in user agent css for list-boxes, we don't do this for now
        if (labelAttrVal != null) {
            if (!labelReplacedContent) {
                labelReplacedContent = true;
                super.processContent(labelAttrVal, context);
            }
        } else {
            super.processContent(content, context);
        }
        if (!fakedContent) {
            actualOptionTextContent.append(content); // TODO DEVSIX-1901: spaces are not collapsed according to white-space property in here
        }
        return true;
    }

    @Override
    public boolean processTagChild(ITagWorker childTagWorker, ProcessorContext context) {
        // Currently, it seems that jsoup handles all the inner content of options exactly the same way as browsers do,
        // removing all the inner tags and leaving only the text content.

        // This method is meant to handle only pseudo elements (:before and :after).

        return super.processTagChild(childTagWorker, context);
    }
}
