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
package com.itextpdf.html2pdf.attach.util;

import com.itextpdf.html2pdf.attach.wrapelement.ColWrapper;
import com.itextpdf.html2pdf.attach.wrapelement.ColgroupWrapper;
import com.itextpdf.html2pdf.css.util.CssUtils;
import com.itextpdf.html2pdf.html.AttributeConstants;
import com.itextpdf.html2pdf.html.TagConstants;
import com.itextpdf.html2pdf.html.node.IElementNode;
import com.itextpdf.html2pdf.html.node.INode;

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
                if (element.name().equals(TagConstants.THEAD)) {
                    applyColStyles(element, headerRowColHelper);
                } else if (element.name().equals(TagConstants.TFOOT)) {
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
                if (element.name().equals(TagConstants.TR)) {
                    applyColStyles(element, rowColHelper);
                    rowColHelper.newRow();
                } else if (element.name().equals(TagConstants.TH) || element.name().equals(TagConstants.TD)) {
                    Integer colspan = CssUtils.parseInteger(element.getAttribute(AttributeConstants.COLSPAN));
                    Integer rowspan = CssUtils.parseInteger(element.getAttribute(AttributeConstants.ROWSPAN));
                    colspan = colspan != null ? colspan : 1;
                    rowspan = rowspan != null ? rowspan : 1;
                    col = rowColHelper.moveToNextEmptyCol();
                    if (getColWrapper(col) != null && getColWrapper(col).getCellCssProps() != null) {
                        element.addAdditionalHtmlStyles(getColWrapper(col).getCellCssProps());
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
