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

import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.element.ILeafElement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Wrapper for the {@code span} element.
 */
public class SpanWrapper implements IWrapElement {

    /** The children of the span element. */
    private List<Object> children = new ArrayList<>();

    /**
     * Adds a child span.
     *
     * @param span the span element to add
     */
    public void add(SpanWrapper span) {
        children.add(span);
    }

    /**
     * Adds a child image.
     *
     * @param img the img element to add
     */
    public void add(ILeafElement img) {
        children.add(img);
    }

    /**
     * Adds a child block element.
     *
     * @param block the block element to add
     */
    public void add(IBlockElement block) {
        children.add(block);
    }

    /**
     * Adds a collection of lead elements as children.
     *
     * @param collection the collection to add
     */
    public void addAll(Collection<IElement> collection) {
        children.addAll(collection);
    }

    /**
     * Gets a list of all the child elements.
     *
     * @return the child elements
     */
    public List<IPropertyContainer> getElements() {
        List<IPropertyContainer> leafs = new ArrayList<>();
        for (Object child : children) {
            if (child instanceof IPropertyContainer) {
                leafs.add((IPropertyContainer) child);
            } else if (child instanceof SpanWrapper) {
                leafs.addAll(((SpanWrapper) child).getElements());
            }
        }
        return leafs;
    }

}
