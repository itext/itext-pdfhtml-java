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
package com.itextpdf.html2pdf.attach.impl;

import com.itextpdf.html2pdf.html.AttributeConstants;
import com.itextpdf.html2pdf.html.TagConstants;
import com.itextpdf.kernel.pdf.annot.PdfLinkAnnotation;
import com.itextpdf.styledxmlparser.node.IElementNode;
import com.itextpdf.styledxmlparser.node.INode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 * This class keeps track of information regarding link (destinations) that occur in the document.
 * Doing so enables us to drastically trim the amount of PdfDestinations that will end up being included in the document.
 * For performance reasons it was decided to scan the DOM tree only once and store the result in a separate object
 * (this object) in the ProcessorContext.
 * <p>
 * This class is not reusable and a new instance shall be created for every new conversion process.
 */
public class LinkContext {

    /**
     * the ids currently in use as valid link destinations
     */
    private Set<String> linkDestinations = new HashSet<>();
    /**
     * Link annotations per destination id. Used to cache link annotations to be able to pass the same annotation
     * to different model elements.
     */
    private Map<String, PdfLinkAnnotation> linkAnnotations = new HashMap<>();

    /**
     * Construct an (empty) LinkContext
     */
    public LinkContext() {
    }

    /**
     * Scan the DOM tree for all (internal) link targets
     *
     * @param root the DOM tree root node
     * @return this LinkContext
     */
    public LinkContext scanForIds(INode root) {
        // clear previous
        linkDestinations.clear();

        // expensive scan operation
        while (root.parentNode() != null) {
            root = root.parentNode();
        }

        Stack<INode> stk = new Stack<>();
        stk.push(root);
        while (!stk.isEmpty()) {
            INode n = stk.pop();
            if (n instanceof IElementNode) {
                IElementNode elem = (IElementNode) n;
                if (TagConstants.A.equals(elem.name())) {
                    String href = elem.getAttribute(AttributeConstants.HREF);
                    if (href != null && href.startsWith("#")){
                        linkDestinations.add(href.substring(1));
                    }
                }
            }
            if (!n.childNodes().isEmpty()) {
                stk.addAll(n.childNodes());
            }
        }

        return this;
    }

    /**
     * Returns whether a given (internal) link destination is used by at least one href element in the document
     *
     * @param linkDestination link destination
     * @return whether a given (internal) link destination is used by at least one href element in the document
     */
    public boolean isUsedLinkDestination(String linkDestination) {
        return linkDestinations.contains(linkDestination);
    }

    /**
     * Add link annotation to the context.
     *
     * @param id link destination.
     * @param annot link annotation to store.
     */
    public void addLinkAnnotation(String id, PdfLinkAnnotation annot) {
        linkAnnotations.put(id, annot);
    }

    /**
     * Get link annotation.
     *
     * @param id link destination.
     * @return link annotation for the given link destination.
     */
    public PdfLinkAnnotation getLinkAnnotation(String id) {
        return linkAnnotations.get(id);
    }
}
