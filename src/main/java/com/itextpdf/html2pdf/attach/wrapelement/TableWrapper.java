/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2021 iText Group NV
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
package com.itextpdf.html2pdf.attach.wrapelement;

import com.itextpdf.html2pdf.attach.util.AccessiblePropHelper;
import com.itextpdf.html2pdf.attach.util.RowColHelper;
import com.itextpdf.html2pdf.attach.util.WaitingColgroupsHelper;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.UnitValue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Wrapper for the {@code table} element.
 */
public class TableWrapper implements IWrapElement {

    /** The body rows of the table. */
    private List<List<CellWrapper> > rows;
    
    /** The header rows. */
    private List<List<CellWrapper> > headerRows;
    
    /** The footer rows. */
    private List<List<CellWrapper> > footerRows;

    /** The current position in the body of the table (row / column). */
    private RowColHelper rowShift = new RowColHelper();

    /** The current position in the header of the table (row / column). */
    private RowColHelper headerRowShift = new RowColHelper();

    /** The current position in the footer of the table (row / column). */
    private RowColHelper footerRowShift = new RowColHelper();

    /** The number of columns. */
    private int numberOfColumns = 0;

    /** The direction value. */
    private boolean isRtl = false;

    /** The caption value. */
    private Div caption = null;

    /** The lang attribute value. */
    private String lang;

    /** The footer lang attribute value. */
    private String footerLang;

    /** The header lang attribute value. */
    private String headerLang;

    public TableWrapper() {
    }

    public TableWrapper(boolean isRtl) {
        this.isRtl = isRtl;
    }

    /**
     * Gets the number of rows.
     *
     * @return the number of rows
     */
    public int getRowsSize() {
        return rows.size();
    }

    /**
     * Adds a new body row.
     */
    public void newRow() {
        if (rows == null) {
            rows = new ArrayList<>();
        }
        rowShift.newRow();
        rows.add(new ArrayList<CellWrapper>());
    }

    /**
     * Adds a new header row.
     */
    public void newHeaderRow() {
        if (headerRows == null) {
            headerRows = new ArrayList<>();
        }
        headerRowShift.newRow();
        headerRows.add(new ArrayList<CellWrapper>());
    }

    /**
     * Adds a new footer row.
     */
    public void newFooterRow() {
        if (footerRows == null) {
            footerRows = new ArrayList<>();
        }
        footerRowShift.newRow();
        footerRows.add(new ArrayList<CellWrapper>());
    }

    /**
     * Adds a new cell to the header rows.
     *
     * @param cell the cell
     */
    public void addHeaderCell(Cell cell) {
        if (headerRows == null) {
            headerRows = new ArrayList<>();
        }
        if (headerRows.size() == 0) {
            newHeaderRow();
        }
        addCellToTable(cell, headerRows, headerRowShift);
    }

    /**
     * Adds a new cell to the footer rows.
     *
     * @param cell the cell
     */
    public void addFooterCell(Cell cell) {
        if (footerRows == null) {
            footerRows = new ArrayList<>();
        }
        if (footerRows.size() == 0) {
            newFooterRow();
        }
        addCellToTable(cell, footerRows, footerRowShift);
    }

    /**
     * Adds a new cell to the body rows.
     *
     * @param cell the cell
     */
    public void addCell(Cell cell) {
        if (rows == null) {
            rows = new ArrayList<>();
        }
        if (rows.size() == 0) {
            newRow();
        }
        addCellToTable(cell, rows, rowShift);
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public void setFooterLang(String footerLang) {
        this.footerLang = footerLang;
    }

    public void setHeaderLang(String headerLang) {
        this.headerLang = headerLang;
    }

    /**
     * Adds a cell to a table.
     *
     * @param cell the cell
     * @param table the table
     * @param tableRowShift the applicable table row shift (current col / row position).
     */
    private void addCellToTable(Cell cell, List<List<CellWrapper>> table, RowColHelper tableRowShift) {
        int col = tableRowShift.moveToNextEmptyCol();
        tableRowShift.updateCurrentPosition(cell.getColspan(), cell.getRowspan());
        List<CellWrapper> currentRow = table.get(table.size() - 1);
        currentRow.add(new CellWrapper(col, cell));
        numberOfColumns = Math.max(numberOfColumns, col + cell.getColspan());
    }

    /**
     * Sets the table's caption.
     *
     * @param caption the caption to be set
     */
    public void setCaption(Div caption) {
        this.caption = caption;
    }

    /**
     * Renders all the rows to a {@link Table} object.
     *
     * @param colgroupsHelper the colgroups helper class
     * @return the table
     */
    public Table toTable(WaitingColgroupsHelper colgroupsHelper) {
        Table table;
        if (numberOfColumns > 0) {
            table = new Table(getColWidths(colgroupsHelper));
        } else {
            // if table is empty, create empty table with single column
            table = new Table(1);
        }
        AccessiblePropHelper.trySetLangAttribute(table, lang);

        if (headerRows != null) {
            for (int i = 0; i < headerRows.size(); i++) {
                if (isRtl) {
                    Collections.reverse(headerRows.get(i));
                }
                for (int j = 0; j < headerRows.get(i).size(); j++) {
                    Cell cell = headerRows.get(i).get(j).cell;
                    ColWrapper colWrapper = colgroupsHelper.getColWrapper(j);
                    if (colWrapper != null) {
                        if (headerLang == null && cell.getAccessibilityProperties().getLanguage() == null) {
                            if (colWrapper.getLang() != null) {
                                cell.getAccessibilityProperties().setLanguage(colWrapper.getLang());
                            }
                        }
                    }
                    table.addHeaderCell(cell);
                }
                if (i != headerRows.size() - 1) {
                    table.getHeader().startNewRow();
                }
            }
            AccessiblePropHelper.trySetLangAttribute(table.getHeader(), headerLang);
        }
        if (footerRows != null) {
            for (int i = 0; i < footerRows.size(); i++) {
                if (isRtl) {
                    Collections.reverse(footerRows.get(i));
                }
                for (int j = 0; j < footerRows.get(i).size(); j++) {
                    Cell cell = footerRows.get(i).get(j).cell;
                    ColWrapper colWrapper = colgroupsHelper.getColWrapper(j);
                    if (colWrapper != null) {
                        if (footerLang == null && cell.getAccessibilityProperties().getLanguage() == null) {
                            if (colWrapper.getLang() != null) {
                                cell.getAccessibilityProperties().setLanguage(colWrapper.getLang());
                            }
                        }
                    }
                    table.addFooterCell(cell);
                }
                if (i != footerRows.size() - 1) {
                    table.getFooter().startNewRow();
                }
            }
            AccessiblePropHelper.trySetLangAttribute(table.getFooter(), footerLang);
        }
        if (rows != null) {
            for (int i = 0; i < rows.size(); i++) {
                if (isRtl) {
                    Collections.reverse(rows.get(i));
                }
                for (int j = 0; j < rows.get(i).size(); j++) {
                    table.addCell(rows.get(i).get(j).cell);
                }
                if (i != rows.size() - 1) {
                    table.startNewRow();
                }
            }
        }
        if (caption != null) {
            table.setCaption(caption);
        }

        return table;
    }

    /**
     * Gets the column widths.
     *
     * @param colgroups the colgroups helper class
     * @return the column widths
     */
    private UnitValue[] getColWidths(WaitingColgroupsHelper colgroups) {
        UnitValue[] colWidths = new UnitValue[numberOfColumns];
        if (colgroups == null) {
            for (int i = 0; i < numberOfColumns; i++) {
                colWidths[i] = null;
            }
        } else {
            for (int i = 0; i < numberOfColumns; i++) {
                colWidths[i] = colgroups.getColWrapper(i) != null
                        ? colgroups.getColWrapper(i).getWidth()
                        : null;
            }
        }
        return colWidths;
    }

    /**
     * Wrapper for the {@code td}/{@code th} element.
     */
    private static class CellWrapper {

        /** The column index. */
        int col;
        
        /** The cell. */
        Cell cell;

        /**
         * Creates a new {@link CellWrapper} instance.
         *
         * @param col the column index
         * @param cell the cell
         */
        CellWrapper(int col, Cell cell) {
            this.col = col;
            this.cell = cell;
        }
    }
}
