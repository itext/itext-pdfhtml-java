/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2017 iText Group NV
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
import com.itextpdf.html2pdf.attach.util.WaitingInlineElementsHelper;
import com.itextpdf.html2pdf.attach.wrapelement.TableRowWrapper;
import com.itextpdf.html2pdf.attach.wrapelement.TableWrapper;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.html.node.IElementNode;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.ILeafElement;

/**
 * TagWorker class for a table element.
 */
public class DisplayTableTagWorker implements ITagWorker {

    /**
     * The table.
     */
    private IPropertyContainer table;

    /**
     * The table wrapper.
     */
    private TableWrapper tableWrapper = new TableWrapper();

    /**
     * The helper class for waiting inline elements.
     */
    private WaitingInlineElementsHelper inlineHelper;

    /**
     * The cell waiting for flushing.
     */
    private Cell waitingCell = null;

    /**
     * The flag which indicates whether.
     */
    private boolean currentRowIsFinished = false;

    /**
     * Creates a new {@link DisplayTableTagWorker} instance.
     *
     * @param element the element
     * @param context the context
     */
    public DisplayTableTagWorker(IElementNode element, ProcessorContext context) {
        inlineHelper = new WaitingInlineElementsHelper(element.getStyles().get(CssConstants.WHITE_SPACE), element.getStyles().get(CssConstants.TEXT_TRANSFORM));
    }

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.attach.ITagWorker#processEnd(com.itextpdf.html2pdf.html.node.IElementNode, com.itextpdf.html2pdf.attach.ProcessorContext)
     */
    @Override
    public void processEnd(IElementNode element, ProcessorContext context) {
        flushWaitingCell();
        table = tableWrapper.toTable(null);
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
        boolean displayTableCell = childTagWorker instanceof IDisplayAware && CssConstants.TABLE_CELL.equals(((IDisplayAware) childTagWorker).getDisplay());
        if (currentRowIsFinished) {
            tableWrapper.newRow();
        }
        if (childTagWorker instanceof DisplayTableRowTagWorker) {
            flushWaitingCell();
            if (!currentRowIsFinished) {
                tableWrapper.newRow();
            }
            TableRowWrapper wrapper = ((DisplayTableRowTagWorker) childTagWorker).getTableRowWrapper();
            for (Cell cell : wrapper.getCells()) {
                tableWrapper.addCell(cell);
            }
            currentRowIsFinished = true;
            return true;
        } else if (childTagWorker.getElementResult() instanceof IBlockElement) {
            IBlockElement childResult = (IBlockElement) childTagWorker.getElementResult();
            Cell curCell = childResult instanceof Cell ? (Cell) childResult : createWrapperCell().add(childResult);
            processCell(curCell, displayTableCell);
            currentRowIsFinished = false;
            return true;
        } else if (childTagWorker.getElementResult() instanceof ILeafElement) {
            inlineHelper.add((ILeafElement) childTagWorker.getElementResult());
            currentRowIsFinished = false;
            return true;
        } else if (childTagWorker instanceof SpanTagWorker) {
            // the previous one
            if (displayTableCell) {
                flushWaitingCell();
            }
            boolean allChildrenProcessed = true;
            for (IPropertyContainer propertyContainer : ((SpanTagWorker) childTagWorker).getAllElements()) {
                if (propertyContainer instanceof ILeafElement) {
                    inlineHelper.add((ILeafElement) propertyContainer);
                } else {
                    allChildrenProcessed = false;
                }
            }
            // the current one
            if (displayTableCell) {
                flushWaitingCell();
            }
            currentRowIsFinished = false;
            return allChildrenProcessed;
        }
        return false;
    }

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.attach.ITagWorker#getElementResult()
     */
    @Override
    public IPropertyContainer getElementResult() {
        return table;
    }

    /**
     * Processes a cell.
     *
     * @param cell the cell
     */
    private void processCell(Cell cell, boolean displayTableCell) {
        if (displayTableCell) {
            if (waitingCell != cell) {
                flushWaitingCell();
                tableWrapper.addCell(cell);
            } else if (!cell.isEmpty()) {
                tableWrapper.addCell(cell);
                waitingCell = null;
            }
        } else {
            flushInlineElementsToWaitingCell();
            waitingCell.add(cell);
        }
    }

    /**
     * Flushes inline elements to the waiting cell.
     */
    private void flushInlineElementsToWaitingCell() {
        if (null == waitingCell) {
            waitingCell = createWrapperCell();
        }
        inlineHelper.flushHangingLeaves(waitingCell);
    }


    /**
     * Flushes the waiting cell.
     */
    private void flushWaitingCell() {
        flushInlineElementsToWaitingCell();
        if (null != waitingCell) {
            processCell(waitingCell, true);
        }
    }

    /**
     * Creates a wrapper cell.
     *
     * @return the cell
     */
    private Cell createWrapperCell() {
        return new Cell().setBorder(Border.NO_BORDER).setPadding(0);
    }
}
