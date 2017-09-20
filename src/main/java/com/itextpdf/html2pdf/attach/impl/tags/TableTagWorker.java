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
import com.itextpdf.html2pdf.attach.util.WaitingColgroupsHelper;
import com.itextpdf.html2pdf.attach.wrapelement.TableRowWrapper;
import com.itextpdf.html2pdf.attach.wrapelement.TableWrapper;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.html.node.IElementNode;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;

/**
 * TagWorker class for the {@code table} element.
 */
public class TableTagWorker implements ITagWorker, IDisplayAware {

    /** The table wrapper. */
    private TableWrapper tableWrapper;

    /** The table. */
    private Table table;

    /** The footer. */
    private boolean footer;

    /** The header. */
    private boolean header;

    /** The parent tag worker. */
    private ITagWorker parentTagWorker;

    /** The colgroups helper. */
    private WaitingColgroupsHelper colgroupsHelper;

    /** The display value. */
    private String display;

    /**
     * Creates a new {@link TableTagWorker} instance.
     *
     * @param element the element
     * @param context the context
     */
    public TableTagWorker(IElementNode element, ProcessorContext context) {
        tableWrapper = new TableWrapper();
        parentTagWorker = context.getState().empty() ? null : context.getState().top();
        if (parentTagWorker instanceof TableTagWorker) {
            ((TableTagWorker) parentTagWorker).applyColStyles();
        } else {
            colgroupsHelper = new WaitingColgroupsHelper(element);
        }
        display = element.getStyles() != null ? element.getStyles().get(CssConstants.DISPLAY) : null;
    }

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.attach.ITagWorker#processEnd(com.itextpdf.html2pdf.html.node.IElementNode, com.itextpdf.html2pdf.attach.ProcessorContext)
     */
    @Override
    public void processEnd(IElementNode element, ProcessorContext context) {
        table = tableWrapper.toTable(colgroupsHelper);
    }

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.attach.ITagWorker#processContent(java.lang.String, com.itextpdf.html2pdf.attach.ProcessorContext)
     */
    @Override
    public boolean processContent(String content, ProcessorContext context) {
        return parentTagWorker != null && parentTagWorker.processContent(content, context);
    }

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.attach.ITagWorker#processTagChild(com.itextpdf.html2pdf.attach.ITagWorker, com.itextpdf.html2pdf.attach.ProcessorContext)
     */
    @Override
    public boolean processTagChild(ITagWorker childTagWorker, ProcessorContext context) {
        if (childTagWorker instanceof TrTagWorker) {
            TableRowWrapper wrapper = ((TrTagWorker)childTagWorker).getTableRowWrapper();
            tableWrapper.newRow();
            for (Cell cell : wrapper.getCells()) {
                tableWrapper.addCell(cell);
            }
            return true;
        }
        else if (childTagWorker instanceof TableTagWorker) {
            if (((TableTagWorker) childTagWorker).header){
                Table header = ((TableTagWorker) childTagWorker).tableWrapper.toTable(colgroupsHelper);
                for (int i = 0; i < header.getNumberOfRows(); i++) {
                    tableWrapper.newHeaderRow();
                    for (int j = 0; j < header.getNumberOfColumns(); j++) {
                        Cell headerCell = header.getCell(i, j);
                        if (headerCell != null) {
                            tableWrapper.addHeaderCell(headerCell);
                        }
                    }
                }
                return true;
            } else if (((TableTagWorker) childTagWorker).footer) {
                Table footer = ((TableTagWorker) childTagWorker).tableWrapper.toTable(colgroupsHelper);
                for (int i = 0; i < footer.getNumberOfRows(); i++) {
                    tableWrapper.newFooterRow();
                    for (int j = 0; j < footer.getNumberOfColumns(); j++) {
                        Cell footerCell = footer.getCell(i, j);
                        if (footerCell != null) {
                            tableWrapper.addFooterCell(footerCell);
                        }
                    }
                }
                return true;
            }
        }
        else if (childTagWorker instanceof ColgroupTagWorker) {
            if (colgroupsHelper != null) {
                colgroupsHelper.add(((ColgroupTagWorker) childTagWorker).getColgroup().finalizeCols());
                return true;
            }
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

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.attach.impl.tags.IDisplayAware#getDisplay()
     */
    @Override
    public String getDisplay() {
        return display;
    }

    /**
     * Method to indicate that this is actually a {@link TableFooterTagWorker} instance.
     */
    public void setFooter() {
        footer = true;
    }

    /**
     * Method to indicate that this is actually a {@link TableHeaderTagWorker} instance.
     */
    public void setHeader() {
        header = true;
    }

    /**
     * Applies the column styles.
     */
    public void applyColStyles() {
        if (colgroupsHelper != null) {
            colgroupsHelper.applyColStyles();
        }
    }
}
