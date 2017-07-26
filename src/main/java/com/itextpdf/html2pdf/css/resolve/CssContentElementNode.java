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
package com.itextpdf.html2pdf.css.resolve;

import com.itextpdf.html2pdf.css.CssContextNode;
import com.itextpdf.html2pdf.css.pseudo.CssPseudoElementUtil;
import com.itextpdf.html2pdf.html.node.IAttribute;
import com.itextpdf.html2pdf.html.node.IAttributes;
import com.itextpdf.html2pdf.html.node.ICustomElementNode;
import com.itextpdf.html2pdf.html.node.IElementNode;
import com.itextpdf.html2pdf.html.node.INode;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * {@link IElementNode} implementation for content nodes.
 */
class CssContentElementNode extends CssContextNode implements IElementNode, ICustomElementNode {
    
    /** The attributes. */
    private Attributes attributes;
    
    /** The tag name. */
    private String tagName;

    /**
     * Creates a new {@link CssContentElementNode} instance.
     *
     * @param parentNode the parent node
     * @param pseudoElementName the pseudo element name
     * @param attributes the attributes
     */
    public CssContentElementNode(INode parentNode, String pseudoElementName, Map<String, String> attributes) {
        super(parentNode);
        this.tagName = CssPseudoElementUtil.createPseudoElementTagName(pseudoElementName);
        this.attributes = new Attributes(attributes);
    }

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.html.node.IElementNode#name()
     */
    @Override
    public String name() {
        return tagName;
    }

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.html.node.IElementNode#getAttributes()
     */
    @Override
    public IAttributes getAttributes() {
        return attributes;
    }

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.html.node.IElementNode#getAttribute(java.lang.String)
     */
    @Override
    public String getAttribute(String key) {
        return attributes.getAttribute(key);
    }

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.html.node.IElementNode#getAdditionalHtmlStyles()
     */
    @Override
    public List<Map<String, String>> getAdditionalHtmlStyles() {
        return null;
    }

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.html.node.IElementNode#addAdditionalHtmlStyles(java.util.Map)
     */
    @Override
    public void addAdditionalHtmlStyles(Map<String, String> styles) {
        throw new UnsupportedOperationException("addAdditionalHtmlStyles");
    }

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.html.node.IElementNode#getLang()
     */
    @Override
    public String getLang() {
        return null;
    }

    /**
     * Simple {@link IAttributes} implementation.
     */
    private static class Attributes implements IAttributes {
        
        /** The attributes. */
        private Map<String, String> attributes;

        /**
         * Creates a new {@link Attributes} instance.
         *
         * @param attributes the attributes
         */
        public Attributes(Map<String, String> attributes) {
            this.attributes = attributes;
        }

        /* (non-Javadoc)
         * @see com.itextpdf.html2pdf.html.node.IAttributes#getAttribute(java.lang.String)
         */
        @Override
        public String getAttribute(String key) {
            return attributes.get(key);
        }

        /* (non-Javadoc)
         * @see com.itextpdf.html2pdf.html.node.IAttributes#setAttribute(java.lang.String, java.lang.String)
         */
        @Override
        public void setAttribute(String key, String value) {
            throw new UnsupportedOperationException("setAttribute");
        }

        /* (non-Javadoc)
         * @see com.itextpdf.html2pdf.html.node.IAttributes#size()
         */
        @Override
        public int size() {
            return attributes.size();
        }

        /* (non-Javadoc)
         * @see java.lang.Iterable#iterator()
         */
        @Override
        public Iterator<IAttribute> iterator() {
            return new AttributeIterator(attributes.entrySet().iterator());
        }
    }

    /**
     * Simple {@link IAttribute} implementation.
     */
    private static class Attribute implements IAttribute {
        
        /** The entry. */
        private Map.Entry<String, String> entry;

        /**
         * Creates a new {@link Attribute} instance.
         *
         * @param entry the entry
         */
        public Attribute(Map.Entry<String, String> entry) {
            this.entry = entry;
        }

        /* (non-Javadoc)
         * @see com.itextpdf.html2pdf.html.node.IAttribute#getKey()
         */
        @Override
        public String getKey() {
            return entry.getKey();
        }

        /* (non-Javadoc)
         * @see com.itextpdf.html2pdf.html.node.IAttribute#getValue()
         */
        @Override
        public String getValue() {
            return entry.getValue();
        }
    }

    /**
     * {@link IAttribute} iterator.
     */
    private static class AttributeIterator implements Iterator<IAttribute> {
        
        /** The iterator. */
        private Iterator<Map.Entry<String, String>> iterator;

        /**
         * Creates a new {@link AttributeIterator} instance.
         *
         * @param iterator the iterator
         */
        public AttributeIterator(Iterator<Map.Entry<String, String>> iterator) {
            this.iterator = iterator;
        }

        /* (non-Javadoc)
         * @see java.util.Iterator#hasNext()
         */
        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        /* (non-Javadoc)
         * @see java.util.Iterator#next()
         */
        @Override
        public IAttribute next() {
            return new Attribute(iterator.next());
        }

        /* (non-Javadoc)
         * @see java.util.Iterator#remove()
         */
        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove");
        }
    }
}
