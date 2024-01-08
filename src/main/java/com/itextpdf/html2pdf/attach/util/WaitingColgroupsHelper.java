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

import com.itextpdf.html2pdf.attach.wrapelement.ColWrapper;
import com.itextpdf.html2pdf.attach.wrapelement.ColgroupWrapper;
import com.itextpdf.html2pdf.html.TagConstants;
import com.itextpdf.html2pdf.html.AttributeConstants;
import com.itextpdf.styledxmlparser.css.util.CssDimensionParsingUtils;
import com.itextpdf.styledxmlparser.node.IElementNode;
import com.itextpdf.styledxmlparser.node.INode;

import java.util.ArrayList;

/**
 * Helper class for waiting column groups.
 */
public class WaitingColgroupsHelper {
    
    /** The table element. */
    private IElementNode tableElement;
    
    /** The column groups. */
    private ArrayList<ColgroupWrapper> colgroups = new ArrayList<>();

    /** The maximum value of the index. */
    private int maxIndex = -1;
    
    /** The index to column group mapping. */
    private int[] indexToColgroupMapping;
    
    /** The shift values for the columns. */
    private int[] shiftCol;

    /**
     * Creates a new {@link WaitingColgroupsHelper} instance.
     *
     * @param tableElement the table element
     */
    public WaitingColgroupsHelper(IElementNode tableElement) {
        this.tableElement = tableElement;
    }

    /**
     * Adds a column group.
     *
     * @param colgroup the column group
     */
    public void add(ColgroupWrapper colgroup) {
        colgroups.add(colgroup);
    }

    /**
     * Applies column styles.
     */
    public void applyColStyles() {
        if (colgroups.isEmpty() || maxIndex != -1) {
            return;
        }
        finalizeColgroups();
        RowColHelper tableRowColHelper = new RowColHelper();
        RowColHelper headerRowColHelper = new RowColHelper();
        RowColHelper footerRowColHelper = new RowColHelper();
        IElementNode element;
        for (INode child : tableElement.childNodes()) {
            if (child instanceof IElementNode) {
                element = (IElementNode) child;
                if (TagConstants.THEAD.equals(element.name())) {
                    applyColStyles(element, headerRowColHelper);
                } else if (TagConstants.TFOOT.equals(element.name())) {
                    applyColStyles(element, footerRowColHelper);
                } else {
                    applyColStyles(element, tableRowColHelper);
                }
            }
        }
    }

    /**
     * Gets a specific column.
     *
     * @param index the index of the column
     * @return the column
     */
    public ColWrapper getColWrapper(int index) {
        if (index > maxIndex) {
            return null;
        }
        return colgroups.get(indexToColgroupMapping[index]).getColumnByIndex(index - shiftCol[indexToColgroupMapping[index]]);
    }

    /**
     * Applies column styles.
     *
     * @param node the node
     * @param rowColHelper the helper class to keep track of the position inside the table
     */
    private void applyColStyles(INode node, RowColHelper rowColHelper) {
        int col;
        IElementNode element;
        for (INode child : node.childNodes()) {
            if (child instanceof IElementNode) {
                element = (IElementNode) child;
                if (TagConstants.TR.equals(element.name())) {
                    applyColStyles(element, rowColHelper);
                    rowColHelper.newRow();
                } else if (TagConstants.TH.equals(element.name()) || TagConstants.TD.equals(element.name())) {
                    Integer colspan = CssDimensionParsingUtils.parseInteger(element.getAttribute(AttributeConstants.COLSPAN));
                    Integer rowspan = CssDimensionParsingUtils.parseInteger(element.getAttribute(AttributeConstants.ROWSPAN));
                    colspan = colspan != null ? colspan : 1;
                    rowspan = rowspan != null ? rowspan : 1;
                    col = rowColHelper.moveToNextEmptyCol();
                    if (getColWrapper(col) != null) {
                        ColWrapper colWrapper = getColWrapper(col);
                        if (colWrapper.getCellCssProps() != null) {
                            element.addAdditionalHtmlStyles(colWrapper.getCellCssProps());
                        }
                        String elemLang = element.getAttribute(AttributeConstants.LANG);
                        String trLang = null;
                        if (node instanceof IElementNode) {
                            trLang = ((IElementNode) node).getAttribute(AttributeConstants.LANG);
                        }
                        if (trLang == null && colWrapper.getLang() != null && elemLang == null) {
                            element.getAttributes().setAttribute(AttributeConstants.LANG, colWrapper.getLang());
                        }
                    }
                    rowColHelper.updateCurrentPosition((int) colspan, (int) rowspan);
                } else {
                    applyColStyles(child, rowColHelper);
                }
            }
        }
    }

    /**
     * Finalizes the column groups.
     */
    private void finalizeColgroups() {
        int shift = 0;
        shiftCol = new int[colgroups.size()];
        for (int i = 0; i < colgroups.size(); ++i) {
            shiftCol[i] = shift;
            shift += colgroups.get(i).getSpan();
        }
        maxIndex = shift - 1;
        indexToColgroupMapping = new int[shift];
        for (int i = 0; i < colgroups.size(); ++i) {
            for (int j = 0; j < colgroups.get(i).getSpan(); ++j) {
                indexToColgroupMapping[j + shiftCol[i]] = i;
            }
        }
        colgroups.trimToSize();
    }
}
