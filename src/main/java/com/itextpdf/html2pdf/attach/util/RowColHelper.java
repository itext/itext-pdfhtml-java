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
package com.itextpdf.html2pdf.attach.util;


import java.util.ArrayList;

/**
 * Helper class to keep track of the current column / row position in a table.
 */
public class RowColHelper {
    
    /** The last empty row. */
    private ArrayList<Integer> lastEmptyRow = new ArrayList<>();
    
    /** The current row index. */
    private int currRow = -1;
    
    /** The current column index. */
    private int currCol = 0;

    /**
     * Move to a new row.
     */
    public void newRow() {
        ++currRow;
        currCol = 0;
    }

    /**
     * Update current position based on a colspan and a rowspan.
     *
     * @param colspan the colspan
     * @param rowspan the rowspan
     */
    public void updateCurrentPosition(int colspan, int rowspan) {
        ensureRowIsStarted();
        while (lastEmptyRow.size() < currCol) {
            lastEmptyRow.add(currRow);
        }
        int value = currRow + rowspan;
        int end = currCol + colspan;
        int middle = Math.min(lastEmptyRow.size(), end);
        for (int i = currCol; i < middle; ++i) {
            lastEmptyRow.set(i, Math.max(value, lastEmptyRow.get(i)));
        }
        while (lastEmptyRow.size() < end) {
            lastEmptyRow.add(value);
        }
        currCol = end;
    }

    /**
     * Move to next empty column.
     *
     * @return the current column position
     */
    public int moveToNextEmptyCol() {
        ensureRowIsStarted();
        while (!canPutCell(currCol)) {
            ++currCol;
        }
        return currCol;
    }

    /**
     * Checks if we can put a new cell in the column.
     *
     * @param col the column index
     * @return true, if successful
     */
    private boolean canPutCell(int col) {
        ensureRowIsStarted();
        if (col >= lastEmptyRow.size()) {
            return true;
        } else {
            return lastEmptyRow.get(col) <= currRow;
        }
    }

    /**
     * Ensure that a row is started.
     */
    private void ensureRowIsStarted() {
        if (currRow == -1) {
            newRow();
        }
    }
}
