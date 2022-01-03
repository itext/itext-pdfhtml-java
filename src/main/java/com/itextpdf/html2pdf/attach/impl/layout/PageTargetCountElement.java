/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2022 iText Group NV
    Authors: iText Software.

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
package com.itextpdf.html2pdf.attach.impl.layout;

import com.itextpdf.html2pdf.css.resolve.func.counter.CounterDigitsGlyphStyle;
import com.itextpdf.html2pdf.html.HtmlUtils;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.renderer.IRenderer;

/**
 * {@link Text} implementation to be used for the page target-counter.
 */
public class PageTargetCountElement extends Text {

    private final String target;
    private final CounterDigitsGlyphStyle digitsGlyphStyle;

    /**
     * Instantiates a new {@link PageTargetCountElement}.
     *
     * @param target name of the corresponding target
     */
    public PageTargetCountElement(String target) {
        super("1234567890");
        this.target = target.replace("'", "").replace("#", "");
        this.digitsGlyphStyle = CounterDigitsGlyphStyle.DEFAULT;
    }

    /**
     * Instantiates a new {@link PageTargetCountElement}.
     *
     * @param target name of the corresponding target
     * @param digitsGlyphStyle digits glyph style
     */
    public PageTargetCountElement(String target, CounterDigitsGlyphStyle digitsGlyphStyle) {
        super(HtmlUtils.getAllNumberGlyphsForStyle(digitsGlyphStyle));
        this.target = target.replace("'", "").replace("#", "");
        this.digitsGlyphStyle = digitsGlyphStyle;
    }

    /**
     * Gets element's target.
     *
     * @return target which was specified for this element.
     */
    public String getTarget() {
        return target;
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
     * {@inheritDoc}
     */
    @Override
    public IRenderer makeNewRenderer() {
        return new PageTargetCountRenderer(this);
    }
}
