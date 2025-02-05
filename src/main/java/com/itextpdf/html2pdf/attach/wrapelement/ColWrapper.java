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

import com.itextpdf.layout.properties.UnitValue;

import java.util.Map;

/**
 * Wrapper for the {@code col} element.
 */
public class ColWrapper implements IWrapElement {

    /** The span. */
    private int span;

    /** The lang attribute value. */
    private String lang;
    
    /** The width. */
    private UnitValue width;

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

    /**
     * Creates a new {@link ColWrapper} instance.
     *
     * @param span the span
     */
    public ColWrapper(int span) {
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
     * @return this {@link ColWrapper} instance
     */
    public ColWrapper setWidth(UnitValue width) {
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
     * @return this {@link ColWrapper} instance
     */
    public ColWrapper setCellCssProps(Map<String, String> cellCssProps) {
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
     * @return this {@link ColWrapper} instance
     */
    public ColWrapper setOwnCssProps(Map<String, String> ownCssProps) {
        this.ownCssProps = ownCssProps;
        return this;
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
