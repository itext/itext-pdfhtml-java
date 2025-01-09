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
package com.itextpdf.html2pdf.css.resolve.func.counter;


import com.itextpdf.styledxmlparser.jsoup.nodes.Element;
import com.itextpdf.styledxmlparser.jsoup.parser.Tag;
import com.itextpdf.styledxmlparser.node.ICustomElementNode;
import com.itextpdf.styledxmlparser.node.INode;
import com.itextpdf.styledxmlparser.node.impl.jsoup.node.JsoupElementNode;
import java.util.Collections;
import java.util.List;

/**
 * {@link ICustomElementNode} implementation for a page count element node.
 */
public class PageCountElementNode extends JsoupElementNode implements ICustomElementNode {

    /** The Constant PAGE_COUNTER_TAG. */
    public static final String PAGE_COUNTER_TAG = "_e0d00a6_page-counter";

    /** The parent. */
    private final INode parent;

    private CounterDigitsGlyphStyle digitsGlyphStyle;

    /** Indicates if the node represents the total page count. */
    private boolean totalPageCount = false;

    public PageCountElementNode(boolean totalPageCount, INode parent) {
        super(new Element(Tag.valueOf(PAGE_COUNTER_TAG), ""));
        this.totalPageCount = totalPageCount;
        this.parent = parent;
    }

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.html.node.INode#childNodes()
     */
    @Override
    public List<INode> childNodes() {
        return Collections.<INode>emptyList();
    }

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.html.node.INode#addChild(com.itextpdf.html2pdf.html.node.INode)
     */
    @Override
    public void addChild(INode node) {
        throw new UnsupportedOperationException();
    }

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.html.node.INode#parentNode()
     */
    @Override
    public INode parentNode() {
        return parent;
    }

    /**
     * Checks if the node represents the total page count.
     *
     * @return true, if the node represents the total page count
     */
    public boolean isTotalPageCount() {
        return totalPageCount;
    }

    /**
     * Gets glyph style for digits.
     *
     * @return name of the glyph style
     */
    public CounterDigitsGlyphStyle getDigitsGlyphStyle() {
        return digitsGlyphStyle;
    }

    /**
     * Sets glyph style for digits.
     *
     * @param digitsGlyphStyle name of the glyph style
     * @return this {@link PageCountElementNode} instance
     */
    public PageCountElementNode setDigitsGlyphStyle(CounterDigitsGlyphStyle digitsGlyphStyle) {
        this.digitsGlyphStyle = digitsGlyphStyle;
        return this;
    }

}
