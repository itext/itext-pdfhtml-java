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
package com.itextpdf.html2pdf.css.page;

import com.itextpdf.styledxmlparser.node.INode;

import java.util.List;

/**
 * Wrapper {@link INode} serving as a placeholder for running element.
 */
public class PageMarginRunningElementNode implements INode {

    private String runningElementName;
    private String runningElementOccurrence;

    public PageMarginRunningElementNode(String runningElementName, String runningElementOccurrence) {
        this.runningElementName = runningElementName;
        this.runningElementOccurrence = runningElementOccurrence;
    }

    @Override
    public List<INode> childNodes() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addChild(INode node) {
        throw new UnsupportedOperationException();
    }

    @Override
    public INode parentNode() {
        throw new UnsupportedOperationException();
    }

    public String getRunningElementName() {
        return runningElementName;
    }

    public String getRunningElementOccurrence() {
        return runningElementOccurrence;
    }
}
