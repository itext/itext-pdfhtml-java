/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2020 iText Group NV
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
package com.itextpdf.html2pdf.css.resolve.func.counter;

import com.itextpdf.styledxmlparser.node.INode;
import com.itextpdf.styledxmlparser.node.impl.jsoup.node.JsoupElementNode;

/**
 * {@link JsoupElementNode} implementation for page target-counters.
 */
public class PageTargetCountElementNode extends PageCountElementNode {

    /**
     * The target from which page will be taken.
     */
    private final String target;

    /**
     * Creates a new {@link PageTargetCountElementNode} instance.
     *
     * @param parent the parent node
     * @param target the target from which page will be taken.
     */
    public PageTargetCountElementNode(INode parent, String target) {
        super(false, parent);
        this.target = target;
    }

    /**
     * Checks if the node represents the total page count.
     *
     * @return true, if the node represents the total page count
     */
    public String getTarget() {
        return target;
    }
}
