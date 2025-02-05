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
package com.itextpdf.html2pdf.attach.wrapelement;

import com.itextpdf.html2pdf.attach.util.AccessiblePropHelper;
import com.itextpdf.html2pdf.attach.util.RowColHelper;
import com.itextpdf.html2pdf.attach.util.WaitingColgroupsHelper;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;

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
                table.startNewRow();

                if (isRtl) {
                    Collections.reverse(rows.get(i));
                }

                for (int j = 0; j < rows.get(i).size(); j++) {
                    table.addCell(rows.get(i).get(j).cell);
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
