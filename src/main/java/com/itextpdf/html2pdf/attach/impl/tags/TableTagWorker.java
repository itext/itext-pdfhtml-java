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
import com.itextpdf.html2pdf.attach.util.WaitingColgroupsHelper;
import com.itextpdf.html2pdf.attach.wrapelement.TableRowWrapper;
import com.itextpdf.html2pdf.attach.wrapelement.TableWrapper;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.html.AttributeConstants;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.Table;
import com.itextpdf.styledxmlparser.node.IElementNode;

/**
 * TagWorker class for the {@code table} element.
 */
public class TableTagWorker implements ITagWorker, IDisplayAware {

    /**
     * The table wrapper.
     */
    private TableWrapper tableWrapper;

    /**
     * The table.
     */
    private Table table;

    /**
     * The footer.
     */
    private boolean footer;

    /**
     * The header.
     */
    private boolean header;

    /**
     * The parent tag worker.
     */
    private ITagWorker parentTagWorker;

    /**
     * The colgroups helper.
     */
    private WaitingColgroupsHelper colgroupsHelper;

    /**
     * The display value.
     */
    private String display;

    /**
     * Creates a new {@link TableTagWorker} instance.
     *
     * @param element the element
     * @param context the context
     */
    public TableTagWorker(IElementNode element, ProcessorContext context) {
        String str = element.getStyles().get(CssConstants.DIRECTION);
        boolean isRtl = "rtl".equals(str);
        tableWrapper = new TableWrapper(isRtl);
        parentTagWorker = context.getState().empty() ? null : context.getState().top();
        if (parentTagWorker instanceof TableTagWorker) {
            ((TableTagWorker) parentTagWorker).applyColStyles();
        } else {
            colgroupsHelper = new WaitingColgroupsHelper(element);
        }
        display = element.getStyles() != null ? element.getStyles().get(CssConstants.DISPLAY) : null;

        String lang = element.getAttribute(AttributeConstants.LANG);
        if (lang != null) {
            tableWrapper.setLang(lang);
        }
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
            TableRowWrapper wrapper = ((TrTagWorker) childTagWorker).getTableRowWrapper();
            tableWrapper.newRow();
            for (Cell cell : wrapper.getCells()) {
                tableWrapper.addCell(cell);
            }
            return true;
        } else if (childTagWorker instanceof TableTagWorker) {
            if (((TableTagWorker) childTagWorker).header) {
                Table header = ((TableTagWorker) childTagWorker).tableWrapper.toTable(colgroupsHelper);
                String headerLang = header.getAccessibilityProperties().getLanguage();
                tableWrapper.setHeaderLang(headerLang);
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
                String footerLang = footer.getAccessibilityProperties().getLanguage();
                tableWrapper.setFooterLang(footerLang);
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
        } else if (childTagWorker instanceof ColgroupTagWorker) {
            if (colgroupsHelper != null) {
                colgroupsHelper.add(((ColgroupTagWorker) childTagWorker).getColgroup().finalizeCols());
                return true;
            }
        } else if (childTagWorker instanceof CaptionTagWorker) {
            tableWrapper.setCaption((Div) childTagWorker.getElementResult());
            return true;
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
