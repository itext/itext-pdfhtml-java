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
