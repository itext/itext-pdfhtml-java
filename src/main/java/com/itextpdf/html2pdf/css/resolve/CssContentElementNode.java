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
package com.itextpdf.html2pdf.css.resolve;

import com.itextpdf.styledxmlparser.css.CssContextNode;
import com.itextpdf.styledxmlparser.css.pseudo.CssPseudoElementUtil;
import com.itextpdf.styledxmlparser.node.IAttribute;
import com.itextpdf.styledxmlparser.node.IAttributes;
import com.itextpdf.styledxmlparser.node.ICustomElementNode;
import com.itextpdf.styledxmlparser.node.IElementNode;
import com.itextpdf.styledxmlparser.node.INode;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * {@link IElementNode} implementation for content nodes.
 */
public class CssContentElementNode extends CssContextNode implements IElementNode, ICustomElementNode {
    
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
     * @see com.itextpdf.styledxmlparser.html.node.IElementNode#name()
     */
    @Override
    public String name() {
        return tagName;
    }

    /* (non-Javadoc)
     * @see com.itextpdf.styledxmlparser.html.node.IElementNode#getAttributes()
     */
    @Override
    public IAttributes getAttributes() {
        return attributes;
    }

    /* (non-Javadoc)
     * @see com.itextpdf.styledxmlparser.html.node.IElementNode#getAttribute(java.lang.String)
     */
    @Override
    public String getAttribute(String key) {
        return attributes.getAttribute(key);
    }

    /* (non-Javadoc)
     * @see com.itextpdf.styledxmlparser.html.node.IElementNode#getAdditionalHtmlStyles()
     */
    @Override
    public List<Map<String, String>> getAdditionalHtmlStyles() {
        return null;
    }

    /* (non-Javadoc)
     * @see com.itextpdf.styledxmlparser.html.node.IElementNode#addAdditionalHtmlStyles(java.util.Map)
     */
    @Override
    public void addAdditionalHtmlStyles(Map<String, String> styles) {
        throw new UnsupportedOperationException("addAdditionalHtmlStyles");
    }

    /* (non-Javadoc)
     * @see com.itextpdf.styledxmlparser.html.node.IElementNode#getLang()
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
         * @see com.itextpdf.styledxmlparser.html.node.IAttributes#getAttribute(java.lang.String)
         */
        @Override
        public String getAttribute(String key) {
            return attributes.get(key);
        }

        /* (non-Javadoc)
         * @see com.itextpdf.styledxmlparser.html.node.IAttributes#setAttribute(java.lang.String, java.lang.String)
         */
        @Override
        public void setAttribute(String key, String value) {
            throw new UnsupportedOperationException("setAttribute");
        }

        /* (non-Javadoc)
         * @see com.itextpdf.styledxmlparser.html.node.IAttributes#size()
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
         * @see com.itextpdf.styledxmlparser.html.node.IAttribute#getKey()
         */
        @Override
        public String getKey() {
            return entry.getKey();
        }

        /* (non-Javadoc)
         * @see com.itextpdf.styledxmlparser.html.node.IAttribute#getValue()
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
