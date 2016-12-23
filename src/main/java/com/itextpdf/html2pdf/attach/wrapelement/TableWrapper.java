/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2017 iText Group NV
    Authors: iText Software.

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

import com.itextpdf.html2pdf.attach.util.RowColHelper;
import com.itextpdf.html2pdf.attach.util.WaitingColgroupsHelper;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.UnitValue;

import java.util.ArrayList;
import java.util.List;

public class TableWrapper implements IWrapElement {

    private List<List<CellWrapper> > rows;
    private List<List<CellWrapper> > headerRows;
    private List<List<CellWrapper> > footerRows;

    private RowColHelper rowShift = new RowColHelper();
    private RowColHelper headerRowShift = new RowColHelper();
    private RowColHelper footerRowShift = new RowColHelper();
    
    public int getRowsSize() {
        return rows.size();
    }

    public void newRow() {
        if (rows == null) {
            rows = new ArrayList<>();
        }
        rowShift.newRow();
        rows.add(new ArrayList<CellWrapper>());
    }

    public void newHeaderRow() {
        if (headerRows == null) {
            headerRows = new ArrayList<>();
        }
        headerRowShift.newRow();
        headerRows.add(new ArrayList<CellWrapper>());
    }

    public void newFooterRow() {
        if (footerRows == null) {
            footerRows = new ArrayList<>();
        }
        footerRowShift.newRow();
        footerRows.add(new ArrayList<CellWrapper>());
    }

    public void addHeaderCell(Cell cell) {
        if (headerRows == null) {
            headerRows = new ArrayList<>();
        }
        if (headerRows.size() == 0) {
            newHeaderRow();
        }
        addCellToTable(cell, headerRows, headerRowShift);
    }

    public void addFooterCell(Cell cell) {
        if (footerRows == null) {
            footerRows = new ArrayList<>();
        }
        if (footerRows.size() == 0) {
            newFooterRow();
        }
        addCellToTable(cell, footerRows, footerRowShift);
    }

    public void addCell(Cell cell) {
        if (rows == null) {
            rows = new ArrayList<>();
        }
        if (rows.size() == 0) {
            newRow();
        }
        addCellToTable(cell, rows, rowShift);
    }

    private void addCellToTable(Cell cell, List<List<CellWrapper>> table, RowColHelper tableRowShift) {
        int col = tableRowShift.moveToNextEmptyCol();
        tableRowShift.updateCurrentPosition(cell.getColspan(), cell.getRowspan());
        table.get(table.size() - 1).add(new CellWrapper(col, cell));
    }

    public Table toTable(WaitingColgroupsHelper colgroupsHelper) {
        UnitValue[] widths = recalculateWidths(colgroupsHelper);
        Table table;
        if (widths.length > 0) {
            table = new Table(widths);
        } else {
            // if table is empty, create empty table with single column
            table = new Table(1);
        }
        if (headerRows != null) {
            for (List<CellWrapper> headerRow : headerRows) {
                for (CellWrapper headerCell : headerRow) {
                    table.addHeaderCell((Cell) headerCell.cell);
                }
            }
        }
        if (footerRows != null) {
            for (List<CellWrapper> footerRow : footerRows) {
                for (CellWrapper footerCell : footerRow) {
                    table.addFooterCell((Cell) footerCell.cell);
                }
            }
        }
        if (rows != null) {
            for (int i = 0; i < rows.size(); i++) {
                for (int j = 0; j < rows.get(i).size(); j++) {
                    table.addCell((Cell) (rows.get(i).get(j).cell));
                }
                if (i != rows.size() - 1) {
                    table.startNewRow();
                }
            }
        }

        return table;
    }

    private UnitValue[] recalculateWidths(WaitingColgroupsHelper colgroupsHelper) {
        List<UnitValue> maxAbsoluteWidths = new ArrayList<>();
        List<UnitValue> maxPercentageWidths = new ArrayList<>();
        if (rows != null) {
            calculateMaxWidths(rows, maxAbsoluteWidths, maxPercentageWidths, colgroupsHelper);
        }
        if (headerRows != null) {
            calculateMaxWidths(headerRows, maxAbsoluteWidths, maxPercentageWidths, colgroupsHelper);
        }
        if (footerRows != null) {
            calculateMaxWidths(footerRows, maxAbsoluteWidths, maxPercentageWidths, colgroupsHelper);
        }

        UnitValue[] tableWidths = new UnitValue[maxAbsoluteWidths.size()];
        float totalAbsoluteSum = 0;
        float totalPercentSum = 0;
        float maxTotalWidth = 0;
        int nullWidth = 0;

        UnitValue curAbsWidth;
        UnitValue curPerWidth;
        for (int i = 0; i < tableWidths.length; ++i) {
            if (maxPercentageWidths.get(i) != null) {
                curPerWidth = maxPercentageWidths.get(i);
                totalPercentSum += curPerWidth.getValue();
                tableWidths[i] = maxPercentageWidths.get(i);
                if (maxAbsoluteWidths.get(i) != null) {
                    curAbsWidth = maxAbsoluteWidths.get(i);
                    maxTotalWidth = Math.max(maxTotalWidth, 100 / curPerWidth.getValue() * curAbsWidth.getValue());
                }
            } else if (maxAbsoluteWidths.get(i) != null) {
                curAbsWidth = maxAbsoluteWidths.get(i);
                totalAbsoluteSum += curAbsWidth.getValue();
                tableWidths[i] = curAbsWidth;
            } else {
                ++nullWidth;
            }
        }
        if (totalPercentSum < 100) {
            maxTotalWidth = Math.max(maxTotalWidth, 100 / (100 - totalPercentSum) * totalAbsoluteSum);
            // TODO: Layout based maxWidth calculations needed here. Currently unsupported.
            for (int i = 0; i < tableWidths.length; i++) {
                UnitValue width = tableWidths[i];
                if (width == null && nullWidth > 0) {
                    tableWidths[i] = UnitValue.createPercentValue((100 - totalPercentSum) / nullWidth);
                }
            }
        }
        else if (nullWidth != 0 && nullWidth < tableWidths.length){
            // TODO: In this case, the columns without percent width should be assigned to min-width. This is currently unsupported.
            // So we fall back to just division of the available place uniformly.
            for (int i = 0; i < tableWidths.length; i++) {
                tableWidths[i] = UnitValue.createPercentValue(100 / tableWidths.length);
            }
        }
        return tableWidths;
    }

    private void calculateMaxWidths(List<List<CellWrapper>> rows, List<UnitValue> absoluteMaxWidths, List<UnitValue> percentageMaxWidths, WaitingColgroupsHelper colgroupsHelper) {
        for (List<CellWrapper> row : rows) {
            for (CellWrapper cellWrapper : row) {
                int colspan = cellWrapper.cell.getColspan();
                UnitValue cellWidth = cellWrapper.cell.getWidth();
                UnitValue collWidth = null;
                if (colspan > 1 && cellWidth != null) {
                    cellWidth = new UnitValue(cellWidth.getUnitType(), cellWidth.getValue() / colspan);
                }
                if (colgroupsHelper != null && colgroupsHelper.getColWraper(cellWrapper.col) != null) {
                    collWidth = colgroupsHelper.getColWraper(cellWrapper.col).getWidth();
                }
                UnitValue absoluteWidth = getMaxValue(UnitValue.POINT, cellWidth, collWidth);
                UnitValue percentageWidth = getMaxValue(UnitValue.PERCENT, cellWidth, collWidth);
                applyNewWidth(absoluteMaxWidths, cellWrapper.col, cellWrapper.col + colspan, absoluteWidth);
                applyNewWidth(percentageMaxWidths, cellWrapper.col, cellWrapper.col + colspan, percentageWidth);
            }
        }
    }

    private void applyNewWidth(List<UnitValue> maxWidths, int start, int end, UnitValue value) {
        UnitValue old;
        while (maxWidths.size() < start) {
            maxWidths.add(null);
        }
        int middle = Math.min(maxWidths.size(), end);
        for (int i = start; i < middle; ++i) {
            maxWidths.set(i, getMaxValue(maxWidths.get(i), value));
        }
        while (maxWidths.size() < end) {
            maxWidths.add(value);
        }
    }

    private UnitValue getMaxValue(int unitType, UnitValue first, UnitValue second) {
        if (first != null && first.getUnitType() != unitType) {
            first = null;
        }
        if (second != null && second.getUnitType() != unitType) {
            second = null;
        }
        return getMaxValue(first, second);
    }

    private UnitValue getMaxValue(UnitValue first, UnitValue second) {
        if (first == null) {
            return second;
        }
        if (second == null) {
            return first;
        }
        return first.getValue() < second.getValue() ? second : first;
    }
    
    private class CellWrapper {
        int col;
        Cell cell;

        public CellWrapper(int col, Cell cell) {
            this.col = col;
            this.cell = cell;
        }
    }


}
