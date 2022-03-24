/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2022 iText Group NV
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
package com.itextpdf.html2pdf.attach.impl.tags;

import com.itextpdf.html2pdf.attach.ITagWorker;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.attach.util.AccessiblePropHelper;
import com.itextpdf.html2pdf.attach.util.WaitingInlineElementsHelper;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.element.ILeafElement;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.styledxmlparser.node.IElementNode;

/**
 * TagWorker class for the {@code p} element.
 * <p>
 * This is how this worker processes the &lt;p&gt; tag:
 * <ul>
 * <li> if the worker meets a text or an inline element, it processes them with a help of
 * the {@link com.itextpdf.html2pdf.attach.util.WaitingInlineElementsHelper} instance
 *
 * <li> if the worker meets a block element without inline displaying or
 * an inline element with the {@code display: block} style, it wraps all the content which hasn't been handled yet
 * into a {@code com.itextpdf.layout.element.Paragraph} object and adds this paragraph to the resultant {@code com.itextpdf.layout.element.Div} object
 * </ul>
 */
public class PTagWorker implements ITagWorker, IDisplayAware {

    /** The latest paragraph object inside tag. */
    private Paragraph lastParagraph;

    /** The container which handles the elements that are present in the &lt;p&gt; tag. */
    private Div elementsContainer;

    /** Helper class for waiting inline elements. */
    private WaitingInlineElementsHelper inlineHelper;

    /** The display value. */
    private String display;

    /**
     * Creates a new {@link PTagWorker} instance.
     *
     * @param element the element
     * @param context the context
     */
    public PTagWorker(IElementNode element, ProcessorContext context) {
        lastParagraph = new Paragraph();
        inlineHelper = new WaitingInlineElementsHelper(element.getStyles().get(CssConstants.WHITE_SPACE),
                element.getStyles().get(CssConstants.TEXT_TRANSFORM));
        display = element.getStyles() != null ? element.getStyles().get(CssConstants.DISPLAY) : null;
    }

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.attach.ITagWorker#processEnd(com.itextpdf.html2pdf.html.node.IElementNode, com.itextpdf.html2pdf.attach.ProcessorContext)
     */
    @Override
    public void processEnd(IElementNode element, ProcessorContext context) {
        inlineHelper.flushHangingLeaves(lastParagraph);

        if (elementsContainer != null) {
            AccessiblePropHelper.trySetLangAttribute(elementsContainer, element);
        } else {
            AccessiblePropHelper.trySetLangAttribute(lastParagraph, element);
        }
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
        // Child might be inline, however still have display:block; it behaves like a block,
        // however p includes it in own occupied area
        IPropertyContainer element = childTagWorker.getElementResult();
        if (childTagWorker instanceof ImgTagWorker && CssConstants.BLOCK.equals(((ImgTagWorker) childTagWorker).getDisplay())) {
            IPropertyContainer propertyContainer = childTagWorker.getElementResult();
            processBlockElement((Image) propertyContainer);
            return true;
        } else if (element instanceof ILeafElement) {
            inlineHelper.add((ILeafElement) element);
            return true;
        } else if (childTagWorker instanceof IDisplayAware &&
                (CssConstants.INLINE_BLOCK.equals(((IDisplayAware) childTagWorker).getDisplay()) ||
                        CssConstants.INLINE.equals(((IDisplayAware) childTagWorker).getDisplay()))
                && element instanceof IBlockElement) {
            inlineHelper.add((IBlockElement) element);
            return true;
        } else if (isBlockWithDisplay(childTagWorker, element, CssConstants.INLINE_BLOCK, false)) {
            inlineHelper.add((IBlockElement) element);
            return true;
        } else if (isBlockWithDisplay(childTagWorker, element,CssConstants.BLOCK, false)) {
            IPropertyContainer propertyContainer = childTagWorker.getElementResult();
            processBlockElement((IBlockElement) propertyContainer);
            return true;
        } else if (childTagWorker instanceof SpanTagWorker) {
            boolean allChildrenProcessed = true;

            for (IPropertyContainer propertyContainer : ((SpanTagWorker) childTagWorker).getAllElements()) {
                if (propertyContainer instanceof ILeafElement) {
                    inlineHelper.add((ILeafElement) propertyContainer);
                } else if (isBlockWithDisplay(childTagWorker, propertyContainer, CssConstants.INLINE_BLOCK, true)) {
                    inlineHelper.add((IBlockElement) propertyContainer);
                } else if (isBlockWithDisplay(childTagWorker, propertyContainer, CssConstants.BLOCK, true)) {
                    processBlockElement((IBlockElement) propertyContainer);
                } else {
                    allChildrenProcessed = false;
                }
            }
            return allChildrenProcessed;
        }
        return false;
    }

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.attach.ITagWorker#getElementResult()
     */
    @Override
    public IPropertyContainer getElementResult() {
        return null == elementsContainer ? (IPropertyContainer) lastParagraph : (IPropertyContainer) elementsContainer;
    }

    @Override
    public String getDisplay() {
        return display;
    }

    private void processBlockElement(IElement propertyContainer) {
        if (elementsContainer == null) {
            elementsContainer = new Div();
            elementsContainer.add(lastParagraph);
        }
        inlineHelper.flushHangingLeaves(lastParagraph);

        if (propertyContainer instanceof Image) {
            elementsContainer.add((Image) propertyContainer);
        } else {
            elementsContainer.add((IBlockElement) propertyContainer);
        }

        lastParagraph = new Paragraph();
        elementsContainer.add(lastParagraph);
    }

    private boolean isBlockWithDisplay(ITagWorker childTagWorker, IPropertyContainer element,
                                       String displayMode, boolean isChild) {
        if (isChild) {
            return element instanceof IBlockElement &&
                    displayMode.equals(((SpanTagWorker) childTagWorker).getElementDisplay(element));
        } else {
            return element instanceof IBlockElement &&
                    childTagWorker instanceof IDisplayAware &&
                    displayMode.equals(((IDisplayAware) childTagWorker).getDisplay());
        }
    }
}
