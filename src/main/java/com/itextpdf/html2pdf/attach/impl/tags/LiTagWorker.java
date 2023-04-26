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

import com.itextpdf.html2pdf.attach.ITagWorker;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.attach.util.AccessiblePropHelper;
import com.itextpdf.html2pdf.attach.util.WaitingInlineElementsHelper;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.css.apply.util.ListStyleApplierUtil;
import com.itextpdf.html2pdf.html.AttributeConstants;
import com.itextpdf.html2pdf.html.TagConstants;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.ILeafElement;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.List;
import com.itextpdf.layout.element.ListItem;
import com.itextpdf.layout.properties.ListSymbolPosition;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.styledxmlparser.css.util.CssDimensionParsingUtils;
import com.itextpdf.styledxmlparser.node.IElementNode;

/**
 * TagWorker class for the {@code li} element.
 */
public class LiTagWorker implements ITagWorker {

    /**
     * The list item.
     */
    protected ListItem listItem;

    /**
     * The list.
     */
    protected List list;

    /**
     * The inline helper.
     */
    private WaitingInlineElementsHelper inlineHelper;

    /**
     * Creates a new {@link LiTagWorker} instance.
     *
     * @param element the element
     * @param context the context
     */
    public LiTagWorker(IElementNode element, ProcessorContext context) {
        listItem = new ListItem();
        if (element.getAttribute(AttributeConstants.VALUE)!=null){
            Integer indexValue = (Integer) CssDimensionParsingUtils.parseInteger(element.getAttribute(AttributeConstants.VALUE));
            if (indexValue  != null) listItem.setListSymbolOrdinalValue(indexValue.intValue() );
        }
        if (!(context.getState().top() instanceof UlOlTagWorker)) {
            listItem.setProperty(Property.LIST_SYMBOL_POSITION, ListSymbolPosition.INSIDE);
            float em = CssDimensionParsingUtils.parseAbsoluteLength(element.getStyles().get(CssConstants.FONT_SIZE));
            if (TagConstants.LI.equals(element.name())) {
                ListStyleApplierUtil.setDiscStyle(listItem, em);
            } else {
                listItem.setProperty(Property.LIST_SYMBOL, null);
            }
            list = new List();
            list.add(listItem);
        }
        inlineHelper = new WaitingInlineElementsHelper(element.getStyles().get(CssConstants.WHITE_SPACE), element.getStyles().get(CssConstants.TEXT_TRANSFORM));

        AccessiblePropHelper.trySetLangAttribute(listItem, element);
    }

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.attach.ITagWorker#processEnd(com.itextpdf.html2pdf.html.node.IElementNode, com.itextpdf.html2pdf.attach.ProcessorContext)
     */
    @Override
    public void processEnd(IElementNode element, ProcessorContext context) {
        inlineHelper.flushHangingLeaves(listItem);
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
        IPropertyContainer element = childTagWorker.getElementResult();
        if (element instanceof ILeafElement) {
            inlineHelper.add((ILeafElement) element);
            return true;
        } else if (childTagWorker instanceof IDisplayAware &&
                (CssConstants.INLINE_BLOCK.equals(((IDisplayAware) childTagWorker).getDisplay()) ||
                        CssConstants.INLINE.equals(((IDisplayAware) childTagWorker).getDisplay()))
                && element instanceof IBlockElement) {
            inlineHelper.add((IBlockElement) element);
            return true;
        } else if (childTagWorker instanceof SpanTagWorker) {
            boolean allChildrenProcessed = true;
            for (IPropertyContainer propertyContainer : ((SpanTagWorker) childTagWorker).getAllElements()) {
                if (propertyContainer instanceof ILeafElement) {
                    inlineHelper.add((ILeafElement) propertyContainer);
                } else if (propertyContainer instanceof IBlockElement && CssConstants.INLINE_BLOCK.equals(((SpanTagWorker) childTagWorker).getElementDisplay(propertyContainer))) {
                    inlineHelper.add((IBlockElement) propertyContainer);
                } else {
                    allChildrenProcessed = processChild(propertyContainer) && allChildrenProcessed;
                }
            }
            return allChildrenProcessed;
        } else {
            return processChild(childTagWorker.getElementResult());
        }
    }

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.attach.ITagWorker#getElementResult()
     */
    @Override
    public IPropertyContainer getElementResult() {
        return list != null ? (IPropertyContainer) list : listItem;
    }

    /**
     * Processes a child.
     *
     * @param propertyContainer the property container
     * @return true, if successful
     */
    private boolean processChild(IPropertyContainer propertyContainer) {
        inlineHelper.flushHangingLeaves(listItem);
        if (propertyContainer instanceof Image) {
            listItem.add((Image) propertyContainer);
            return true;
        } else if (propertyContainer instanceof IBlockElement) {
            listItem.add((IBlockElement) propertyContainer);
            return true;
        }
        return false;
    }
}
