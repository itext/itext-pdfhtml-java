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
import com.itextpdf.html2pdf.attach.util.AccessiblePropHelper;
import com.itextpdf.html2pdf.attach.util.WaitingInlineElementsHelper;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.element.ILeafElement;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.MulticolContainer;
import com.itextpdf.styledxmlparser.node.IElementNode;

import java.util.Map;

/**
 * TagWorker class for the {@code div} element.
 */
public class DivTagWorker implements ITagWorker, IDisplayAware {

    /**
     * Column container element.
     */
    protected MulticolContainer multicolContainer;

    /** The div element. */
    private Div div;

    /** Helper class for waiting inline elements. */
    private WaitingInlineElementsHelper inlineHelper;

    /** The display value. */
    private String display;

    /**
     * Creates a new {@link DivTagWorker} instance.
     *
     * @param element the element
     * @param context the context
     */
    public DivTagWorker(IElementNode element, ProcessorContext context) {
        div = new Div();
        Map<String, String> styles = element.getStyles();
        if (styles != null && (styles.containsKey(CssConstants.COLUMN_COUNT) || styles.containsKey(CssConstants.COLUMN_WIDTH))) {
            multicolContainer = new MulticolContainer();
            multicolContainer.add(div);
        }
        inlineHelper = new WaitingInlineElementsHelper(styles == null ? null : styles.get(CssConstants.WHITE_SPACE),
                styles == null ? null : styles.get(CssConstants.TEXT_TRANSFORM));
        display = element.getStyles() != null ? element.getStyles().get(CssConstants.DISPLAY) : null;

        AccessiblePropHelper.trySetLangAttribute(div, element);
    }

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.attach.ITagWorker#processEnd(com.itextpdf.html2pdf.html.node.IElementNode, com.itextpdf.html2pdf.attach.ProcessorContext)
     */
    @Override
    public void processEnd(IElementNode element, ProcessorContext context) {
        inlineHelper.flushHangingLeaves(div);
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
        IPropertyContainer element = childTagWorker.getElementResult();
        if (childTagWorker instanceof BrTagWorker) {
            inlineHelper.add((ILeafElement) childTagWorker.getElementResult());
            return true;
        } else if (childTagWorker instanceof IDisplayAware && CssConstants.INLINE_BLOCK.equals(((IDisplayAware) childTagWorker).getDisplay()) && childTagWorker.getElementResult() instanceof IBlockElement) {
            inlineHelper.add((IBlockElement) childTagWorker.getElementResult());
            return true;
        } else if (childTagWorker instanceof SpanTagWorker) {
            boolean allChildrenProcessed = true;
            for (IPropertyContainer childElement : ((SpanTagWorker) childTagWorker).getAllElements()) {
                if (childElement instanceof ILeafElement) {
                    inlineHelper.add((ILeafElement) childElement);
                } else if (childElement instanceof IBlockElement && CssConstants.INLINE_BLOCK.equals(((SpanTagWorker) childTagWorker).getElementDisplay(childElement))) {
                    inlineHelper.add((IBlockElement) childElement);
                } else if (childElement instanceof IElement) {
                    allChildrenProcessed = addBlockChild((IElement) childElement) && allChildrenProcessed;
                }
            }
            processed = allChildrenProcessed;
        } else if (element instanceof IFormField && !(childTagWorker instanceof IDisplayAware && CssConstants.BLOCK.equals(((IDisplayAware) childTagWorker).getDisplay()))) {
            inlineHelper.add((IBlockElement) element);
            processed = true;
        } else if (element instanceof AreaBreak) {
            postProcessInlineGroup();
            div.add((AreaBreak) element);
            processed = true;
        } else if (childTagWorker instanceof ImgTagWorker && element instanceof IElement && !CssConstants.BLOCK.equals(((ImgTagWorker) childTagWorker).getDisplay())) {
            inlineHelper.add((ILeafElement) childTagWorker.getElementResult());
            processed = true;
       } else if (element instanceof com.itextpdf.layout.element.IElement) {
            processed = addBlockChild((com.itextpdf.layout.element.IElement) element);
        }
        return processed;
    }

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.attach.ITagWorker#getElementResult()
     */
    @Override
    public IPropertyContainer getElementResult() {
        return multicolContainer == null ? div : multicolContainer;
    }

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.attach.impl.tags.IDisplayAware#getDisplay()
     */
    @Override
    public String getDisplay() {
        return display;
    }

    /**
     * Adds a child element to the div block.
     *
     * @param element the element
     * @return true, if successful
     */
    protected boolean addBlockChild(com.itextpdf.layout.element.IElement element) {
        postProcessInlineGroup();

        boolean processed = false;
        if (element instanceof IBlockElement) {
            div.add(((IBlockElement) element));
            processed = true;
        } else if (element instanceof Image) {
            div.add((Image) element);
            processed = true;
        }
        return processed;
    }

    /**
     * Post-processes the hanging leaves of the waiting inline elements.
     */
    protected void postProcessInlineGroup() {
        inlineHelper.flushHangingLeaves(div);
    }
}
