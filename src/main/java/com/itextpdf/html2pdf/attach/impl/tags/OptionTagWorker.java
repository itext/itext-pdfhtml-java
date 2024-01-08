/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2024 Apryse Group NV
    Authors: Apryse Software.

    This program is offered under a commercial and under the AGPL license.
    For commercial licensing, contact us at https://itextpdf.com/sales.  For AGPL licensing, see below.

    AGPL licensing:
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.itextpdf.html2pdf.attach.impl.tags;

import com.itextpdf.forms.form.FormProperty;
import com.itextpdf.html2pdf.attach.ITagWorker;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.html.AttributeConstants;
import com.itextpdf.styledxmlparser.jsoup.nodes.TextNode;
import com.itextpdf.styledxmlparser.node.IElementNode;
import com.itextpdf.styledxmlparser.node.impl.jsoup.node.JsoupTextNode;

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
        getElementResult().setProperty(FormProperty.FORM_FIELD_SELECTED, selectedAttr);
        actualOptionTextContent = new StringBuilder();

        labelAttrVal = element.getAttribute(AttributeConstants.LABEL);
        if (labelAttrVal != null) {
            if (element.childNodes().isEmpty()) {
                // workaround to ensure that processContent method is called
                element.addChild(new JsoupTextNode(new TextNode("")));
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

        getElementResult().setProperty(FormProperty.FORM_FIELD_VALUE, valueAttr);
        getElementResult().setProperty(FormProperty.FORM_FIELD_LABEL, labelAttr);
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
            // TODO DEVSIX-2443: spaces are not collapsed according to white-space property in here
            actualOptionTextContent.append(content);
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
