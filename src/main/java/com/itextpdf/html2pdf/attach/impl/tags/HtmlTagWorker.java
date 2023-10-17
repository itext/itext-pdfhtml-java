/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2023 Apryse Group NV
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

import com.itextpdf.forms.form.element.IFormField;
import com.itextpdf.html2pdf.attach.ITagWorker;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.attach.impl.DefaultHtmlProcessor;
import com.itextpdf.html2pdf.attach.impl.layout.HtmlDocument;
import com.itextpdf.html2pdf.attach.impl.layout.HtmlDocumentRenderer;
import com.itextpdf.html2pdf.attach.util.WaitingInlineElementsHelper;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.html.AttributeConstants;
import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.element.ILeafElement;
import com.itextpdf.layout.element.Image;
import com.itextpdf.styledxmlparser.css.ICssResolver;
import com.itextpdf.styledxmlparser.node.IElementNode;
import com.itextpdf.styledxmlparser.node.INode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TagWorker class for the {@code html} element.
 */
public class HtmlTagWorker implements ITagWorker {

    private static final Logger LOGGER = LoggerFactory.getLogger(HtmlTagWorker.class);

    /**
     * The iText document instance.
     */
    private Document document;

    /**
     * Helper class for waiting inline elements.
     */
    private WaitingInlineElementsHelper inlineHelper;

    /**
     * Creates a new {@link HtmlTagWorker} instance.
     *
     * @param element the element
     * @param context the context
     */
    public HtmlTagWorker(IElementNode element, ProcessorContext context) {
        // TODO DEVSIX-4261 more precise check if a counter was actually added to the document
        boolean immediateFlush =
                context.isImmediateFlush() && !context.getCssContext().isPagesCounterPresent()
                        && !context.isCreateAcroForm();
        if (context.isImmediateFlush() && context.isCreateAcroForm()) {
            LOGGER.info(Html2PdfLogMessageConstant.IMMEDIATE_FLUSH_DISABLED);
        }
        PdfDocument pdfDocument = context.getPdfDocument();
        document = new HtmlDocument(pdfDocument, pdfDocument.getDefaultPageSize(), immediateFlush);
        document.setRenderer(new HtmlDocumentRenderer(document, immediateFlush));

        DefaultHtmlProcessor.setConvertedRootElementProperties(element.getStyles(), context, document);

        inlineHelper = new WaitingInlineElementsHelper(element.getStyles().get(CssConstants.WHITE_SPACE),
                element.getStyles().get(CssConstants.TEXT_TRANSFORM));

        String lang = element.getAttribute(AttributeConstants.LANG);
        if (lang != null) {
            pdfDocument.getCatalog().setLang(new PdfString(lang, PdfEncodings.UNICODE_BIG));
        }
    }

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.attach.ITagWorker#processEnd(com.itextpdf.html2pdf.html.node.IElementNode, com.itextpdf.html2pdf.attach.ProcessorContext)
     */
    @Override
    public void processEnd(IElementNode element, ProcessorContext context) {
        inlineHelper.flushHangingLeaves(document);
    }

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.attach.ITagWorker#processContent(java.lang.String, com.itextpdf.html2pdf.attach.ProcessorContext)
     */
    @Override
    public boolean processContent(String content, ProcessorContext context) {
        inlineHelper.add(content);
        return true;
    }

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.attach.ITagWorker#processTagChild(com.itextpdf.html2pdf.attach.ITagWorker, com.itextpdf.html2pdf.attach.ProcessorContext)
     */
    @Override
    public boolean processTagChild(ITagWorker childTagWorker, ProcessorContext context) {
        boolean processed = false;
        if (childTagWorker instanceof SpanTagWorker) {
            boolean allChildrenProcessed = true;
            for (IPropertyContainer propertyContainer : ((SpanTagWorker) childTagWorker).getAllElements()) {
                if (propertyContainer instanceof ILeafElement) {
                    inlineHelper.add((ILeafElement) propertyContainer);
                } else if (propertyContainer instanceof IBlockElement && CssConstants.INLINE_BLOCK.equals(((SpanTagWorker) childTagWorker).getElementDisplay(propertyContainer))) {
                    inlineHelper.add((IBlockElement) propertyContainer);
                } else {
                    allChildrenProcessed = processBlockChild(propertyContainer) && allChildrenProcessed;
                }
            }
            processed = allChildrenProcessed;
        } else if (childTagWorker.getElementResult() instanceof IFormField && !(childTagWorker instanceof IDisplayAware && CssConstants.BLOCK.equals(((IDisplayAware) childTagWorker).getDisplay()))) {
            inlineHelper.add((IBlockElement) childTagWorker.getElementResult());
            processed = true;
        } else if (childTagWorker.getElementResult() instanceof AreaBreak) {
            postProcessInlineGroup();
            document.add((AreaBreak) childTagWorker.getElementResult());
            processed = true;
        } else if (childTagWorker instanceof IDisplayAware && CssConstants.INLINE_BLOCK.equals(((IDisplayAware) childTagWorker).getDisplay()) && childTagWorker.getElementResult() instanceof IBlockElement) {
            inlineHelper.add((IBlockElement) childTagWorker.getElementResult());
            processed = true;
        } else if (childTagWorker instanceof BrTagWorker) {
            inlineHelper.add((ILeafElement) childTagWorker.getElementResult());
            processed = true;
        } else if (childTagWorker instanceof ImgTagWorker && childTagWorker.getElementResult() instanceof IElement && !CssConstants.BLOCK.equals(((ImgTagWorker) childTagWorker).getDisplay())) {
            inlineHelper.add((ILeafElement) childTagWorker.getElementResult());
            processed = true;
        } else if (childTagWorker.getElementResult() != null) {
            processed = processBlockChild(childTagWorker.getElementResult());
        }

        return processed;
    }

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.attach.ITagWorker#getElementResult()
     */
    @Override
    public IPropertyContainer getElementResult() {
        return document;
    }

    /**
     * Processes the page rules.
     *
     * @param rootNode the root node
     * @param cssResolver the css resolver
     * @param context the context
     */
    public void processPageRules(INode rootNode, ICssResolver cssResolver, ProcessorContext context) {
        ((HtmlDocumentRenderer)document.getRenderer()).processPageRules(rootNode, cssResolver, context);
    }

    /**
     * Processes a block child.
     *
     * @param element the element
     * @return true, if successful
     */
    private boolean processBlockChild(IPropertyContainer element) {
        postProcessInlineGroup();
        if (element instanceof IBlockElement) {
            document.add((IBlockElement) element);
            return true;
        }
        if (element instanceof Image) {
            document.add((Image) element);
            return true;
        }
        return false;
    }

    /**
     * Post-processes the hanging leaves of the waiting inline elements.
     */
    private void postProcessInlineGroup() {
        inlineHelper.flushHangingLeaves(document);
    }

}
