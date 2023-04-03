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
package com.itextpdf.html2pdf.attach.wrapelement;

import com.itextpdf.layout.properties.UnitValue;

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

    /** The lang attribute value. */
    private String lang;

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
        } else {
            if (cellCssProps != null) {
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
            if (lang != null) {
                for (ColWrapper col : columns) {
                    if (col.getLang() == null) {
                        col.setLang(lang);
                    }
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

    /**
     * Sets the language attribute.
     *
     * @param lang the lang attribute
     */
    public void setLang(String lang) {
        this.lang = lang;
    }

    /**
     * Gets the language attribute.
     *
     * @return the lang attribute
     */
    public String getLang() {
        return lang;
    }
}
