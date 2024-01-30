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

import com.itextpdf.html2pdf.attach.ITagWorker;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.attach.util.AccessiblePropHelper;
import com.itextpdf.html2pdf.attach.util.WaitingInlineElementsHelper;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.css.apply.impl.MultiColumnCssApplierUtil;
import com.itextpdf.html2pdf.html.AttributeConstants;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.ILeafElement;
import com.itextpdf.layout.element.MulticolContainer;
import com.itextpdf.styledxmlparser.css.util.CssDimensionParsingUtils;
import com.itextpdf.styledxmlparser.node.IElementNode;

import java.util.Map;

/**
 * TagWorker class for the {@code td} element.
 */
public class TdTagWorker implements ITagWorker, IDisplayAware {

    /**
     * The cell.
     */
    private final Cell cell;

    /**
     * Container for cell children in case of multicol layouting
     */
    private Div childOfMulticolContainer;

    /**
     * Container for children in case of multicol layouting
     */
    protected MulticolContainer multicolContainer;

    /**
     * The inline helper.
     */
    private final WaitingInlineElementsHelper inlineHelper;

    /**
     * The display.
     */
    private final String display;

    /**
     * Creates a new {@link TdTagWorker} instance.
     *
     * @param element the element
     * @param context the context
     */
    public TdTagWorker(IElementNode element, ProcessorContext context) {
        Integer colspan = CssDimensionParsingUtils.parseInteger(element.getAttribute(AttributeConstants.COLSPAN));
        Integer rowspan = CssDimensionParsingUtils.parseInteger(element.getAttribute(AttributeConstants.ROWSPAN));
        colspan = colspan != null ? colspan : 1;
        rowspan = rowspan != null ? rowspan : 1;

        cell = new Cell((int)rowspan, (int)colspan);
        cell.setPadding(0);

        Map<String, String> styles = element.getStyles();
        if (styles.containsKey(CssConstants.COLUMN_COUNT) || styles.containsKey(CssConstants.COLUMN_WIDTH)) {
            multicolContainer = new MulticolContainer();
            childOfMulticolContainer = new Div();
            multicolContainer.add(childOfMulticolContainer);
            MultiColumnCssApplierUtil.applyMultiCol(styles, context, multicolContainer);
            cell.add(multicolContainer);
        }

        inlineHelper = new WaitingInlineElementsHelper(styles.get(CssConstants.WHITE_SPACE), styles.get(CssConstants.TEXT_TRANSFORM));
        display = styles.get(CssConstants.DISPLAY);

        AccessiblePropHelper.trySetLangAttribute(cell, element);
    }

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.attach.ITagWorker#processEnd(com.itextpdf.html2pdf.html.node.IElementNode, com.itextpdf.html2pdf.attach.ProcessorContext)
     */
    @Override
    public void processEnd(IElementNode element, ProcessorContext context) {
        inlineHelper.flushHangingLeaves(getCellContainer());
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
        if (childTagWorker instanceof IDisplayAware && CssConstants.INLINE_BLOCK.equals(((IDisplayAware) childTagWorker).getDisplay()) && childTagWorker.getElementResult() instanceof IBlockElement) {
            inlineHelper.add((IBlockElement) childTagWorker.getElementResult());
            processed = true;
        } else if (childTagWorker instanceof SpanTagWorker) {
            boolean allChildrenProcesssed = true;
            for (IPropertyContainer propertyContainer : ((SpanTagWorker) childTagWorker).getAllElements()) {
                if (propertyContainer instanceof ILeafElement) {
                    inlineHelper.add((ILeafElement) propertyContainer);
                } else if (propertyContainer instanceof IBlockElement && CssConstants.INLINE_BLOCK.equals(((SpanTagWorker) childTagWorker).getElementDisplay(propertyContainer))) {
                    inlineHelper.add((IBlockElement) propertyContainer);
                } else {
                    allChildrenProcesssed = processChild(propertyContainer) && allChildrenProcesssed;
                }
            }
            processed = allChildrenProcesssed;
        } else if (childTagWorker.getElementResult() instanceof ILeafElement) {
            inlineHelper.add((ILeafElement)childTagWorker.getElementResult());
            processed = true;
        } else {
            processed = processChild(childTagWorker.getElementResult());
        }
        return processed;
    }

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.attach.ITagWorker#getElementResult()
     */
    @Override
    public IPropertyContainer getElementResult() {
        return (IPropertyContainer) cell;
    }

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.attach.impl.tags.IDisplayAware#getDisplay()
     */
    @Override
    public String getDisplay() {
        return display;
    }

    /**
     * Processes a child element.
     *
     * @param propertyContainer the property container
     * @return true, if successful
     */
    private boolean processChild(IPropertyContainer propertyContainer) {
        boolean processed = false;
        inlineHelper.flushHangingLeaves(getCellContainer());
        if (propertyContainer instanceof IBlockElement) {
            if (childOfMulticolContainer == null) {
                cell.add((IBlockElement) propertyContainer);
            } else {
                childOfMulticolContainer.add((IBlockElement) propertyContainer);
            }
            processed = true;
        }
        return processed;
    }

    private IPropertyContainer getCellContainer() {
        return childOfMulticolContainer == null ? (IPropertyContainer) cell : (IPropertyContainer) childOfMulticolContainer;
    }
}
