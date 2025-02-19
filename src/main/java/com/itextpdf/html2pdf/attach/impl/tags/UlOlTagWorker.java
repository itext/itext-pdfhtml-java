/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2025 Apryse Group NV
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
import com.itextpdf.html2pdf.html.AttributeConstants;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.element.AnonymousInlineBox;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.ILeafElement;
import com.itextpdf.layout.element.List;
import com.itextpdf.layout.element.ListItem;
import com.itextpdf.layout.element.MulticolContainer;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.styledxmlparser.css.util.CssDimensionParsingUtils;
import com.itextpdf.styledxmlparser.node.IElementNode;

/**
 * TagWorker class for the {@code ul} and {@code ol} elements.
 */
public class UlOlTagWorker implements ITagWorker {

    /**
     * The list object.
     */
    private List list;

    protected MulticolContainer multicolContainer;

    /**
     * Helper class for waiting inline elements.
     */
    private WaitingInlineElementsHelper inlineHelper;

    /**
     * Creates a new {@link UlOlTagWorker} instance.
     *
     * @param element the element
     * @param context the context
     */
    public UlOlTagWorker(IElementNode element, ProcessorContext context) {
        list = new List().setListSymbol("");

        if (element.getStyles().get(CssConstants.COLUMN_COUNT) != null
                || element.getStyles().containsKey(CssConstants.COLUMN_WIDTH)) {
            multicolContainer = new MulticolContainer();
            multicolContainer.add(list);
        }

        //In the case of an ordered list, see if the start attribute can be found
        if (element.getAttribute(AttributeConstants.START) != null) {
            Integer startValue = CssDimensionParsingUtils.parseInteger(element.getAttribute(AttributeConstants.START));
            if (startValue != null) list.setItemStartIndex((int) startValue);
        }
        inlineHelper = new WaitingInlineElementsHelper(element.getStyles().get(CssConstants.WHITE_SPACE), element.getStyles().get(CssConstants.TEXT_TRANSFORM));

        AccessiblePropHelper.trySetLangAttribute(list, element);
    }

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.attach.ITagWorker#processEnd(com.itextpdf.html2pdf.html.node.IElementNode, com.itextpdf.html2pdf.attach.ProcessorContext)
     */
    @Override
    public void processEnd(IElementNode element, ProcessorContext context) {
        processUnlabeledListItem();
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
        IPropertyContainer child = childTagWorker.getElementResult();
        if (child instanceof ILeafElement) {
            inlineHelper.add((ILeafElement) child);
            return true;
        } else if (childTagWorker instanceof SpanTagWorker) {
            boolean allChildrenProcessed = true;
            for (IPropertyContainer propertyContainer : ((SpanTagWorker) childTagWorker).getAllElements()) {
                if (propertyContainer instanceof ILeafElement) {
                    inlineHelper.add((ILeafElement) propertyContainer);
                } else if (propertyContainer instanceof IBlockElement && CssConstants.INLINE_BLOCK.equals(((SpanTagWorker) childTagWorker).getElementDisplay(propertyContainer))) {
                    inlineHelper.add((IBlockElement) propertyContainer);
                } else {
                    allChildrenProcessed = addBlockChild(propertyContainer) && allChildrenProcessed;
                }
            }
            return allChildrenProcessed;
        } else if (childTagWorker instanceof IDisplayAware && CssConstants.INLINE_BLOCK.equals(((IDisplayAware) childTagWorker).getDisplay()) && childTagWorker.getElementResult() instanceof IBlockElement) {
            inlineHelper.add((IBlockElement) childTagWorker.getElementResult());
            return true;
        } else {
            return addBlockChild(child);
        }
    }

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.attach.ITagWorker#getElementResult()
     */
    @Override
    public IPropertyContainer getElementResult() {
        return multicolContainer == null ? (IPropertyContainer) list : (IPropertyContainer) multicolContainer;
    }

    /**
     * Processes an unlabeled list item.
     */
    private void processUnlabeledListItem() {
        AnonymousInlineBox ab = new AnonymousInlineBox();
        inlineHelper.flushHangingLeaves(ab);
        if (ab.getChildren().isEmpty()) {
            return;
        }
        addUnlabeledListItem(ab);
    }

    /**
     * Adds an unlabeled list item.
     *
     * @param item the item
     */
    private void addUnlabeledListItem(IBlockElement item) {
        ListItem li = new ListItem();
        li.add(item);
        li.setProperty(Property.LIST_SYMBOL, null);
        list.add(li);
    }

    /**
     * Adds a child.
     *
     * @param child the child
     * @return true, if successful
     */
    private boolean addBlockChild(IPropertyContainer child) {
        processUnlabeledListItem();
        if (child instanceof ListItem) {
            list.add((ListItem) child);
            return true;
        } else if (child instanceof IBlockElement) {
            addUnlabeledListItem((IBlockElement) child);
            return true;
        }
        return false;
    }

}
