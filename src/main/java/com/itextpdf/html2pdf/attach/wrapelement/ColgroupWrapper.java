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
package com.itextpdf.html2pdf.attach.wrapelement;

import com.itextpdf.layout.property.UnitValue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Wrapper for the {@code colgroup} element.
 */
public class ColgroupWrapper implements IWrapElement {

    /** The span. */
    private int span;
    
    /** The width. */
    private UnitValue width;
    
    /** The index to column mapping. */
    private int[] indexToColMapping;
    
    /**
     * The cell CSS properties.
     * These properties should be inherited from &lt;colgroup&gt; to &lt;col&gt;
     * and are eventually applied to &lt;td&gt; or &lt;th&gt;.
     */
    private Map<String, String> cellCssProps;
    
    /**
     * The own CSS properties.
     * These properties shouldn't be applied to &lt;td&gt; or &lt;th&gt;.
     */
    private Map<String, String> ownCssProps;
    
    /** A list of column wrappers. */
    private List<ColWrapper> columns = new ArrayList<>();

    /**
     * Creates a new {@link ColgroupWrapper} instance.
     *
     * @param span the span
     */
    public ColgroupWrapper(int span) {
        this.span = span;
    }

    /**
     * Gets the span.
     *
     * @return the span
     */
    public int getSpan() {
        return span;
    }

    /**
     * Gets the width.
     *
     * @return the width
     */
    public UnitValue getWidth() {
        return width;
    }

    /**
     * Sets the width.
     *
     * @param width the width
     * @return this {@link ColgroupWrapper} instance
     */
    public ColgroupWrapper setWidth(UnitValue width) {
        this.width = width;
        return this;
    }

    /**
     * Gets the cell CSS properties.
     *
     * @return the cell CSS properties
     */
    public Map<String, String> getCellCssProps() {
        return cellCssProps;
    }

    /**
     * Sets the cell CSS properties.
     *
     * @param cellCssProps the cell CSS properties
     * @return this {@link ColgroupWrapper} instance
     */
    public ColgroupWrapper setCellCssProps(Map<String, String> cellCssProps) {
        this.cellCssProps = cellCssProps;
        return this;
    }

    /**
     * Gets the own CSS properties.
     *
     * @return the own CSS properties
     */
    public Map<String, String> getOwnCssProps() {
        return ownCssProps;
    }

    /**
     * Sets the own CSS properties.
     *
     * @param ownCssProps the own CSS properties
     * @return this {@link ColgroupWrapper} instance
     */
    public ColgroupWrapper setOwnCssProps(Map<String, String> ownCssProps) {
        this.ownCssProps = ownCssProps;
        return this;
    }

    /**
     * Gets the columns.
     *
     * @return the columns
     */
    public List<ColWrapper> getColumns() {
        return columns;
    }

    /**
     * Finalize the columns.
     *
     * @return this {@link ColgroupWrapper} instance
     */
    public ColgroupWrapper finalizeCols() {
        if (indexToColMapping != null) {
            return this;
        }
        if (columns.isEmpty()) {
            columns.add(new ColWrapper(span).setCellCssProps(cellCssProps).setWidth(width));
        } else if (cellCssProps != null) {
            for (ColWrapper col : columns) {
                Map<String, String> colStyles = new HashMap<>(cellCssProps);
                if (col.getCellCssProps() != null) {
                    colStyles.putAll(col.getCellCssProps());
                }
                if (colStyles.size() > 0) {
                    col.setCellCssProps(colStyles);
                }
                if (col.getWidth() == null) {
                    col.setWidth(width);
                }
            }
        }
        columns = Collections.unmodifiableList(columns);
        int ncol = 0;
        for(ColWrapper col : columns) {
            ncol += col.getSpan();
        }
        indexToColMapping = new int[ncol];
        span = 0;
        for(int i = 0; i < columns.size(); ++i) {
            int colSpan = columns.get(i).getSpan();
            for (int j = 0; j < colSpan; ++j) {
                indexToColMapping[span + j] = i;
            }
            span += colSpan;
        }
        return this;
    }

    /**
     * Gets the column by index.
     *
     * @param index the index
     * @return the column corresponding with the index
     */
    public ColWrapper getColumnByIndex(int index) {
        return columns.get(indexToColMapping[index]);
    }
}
