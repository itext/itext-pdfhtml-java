/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2017 iText Group NV
    Authors: iText Software.

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
package com.itextpdf.html2pdf.attach.impl;

import com.itextpdf.html2pdf.html.AttributeConstants;
import com.itextpdf.html2pdf.html.node.IElementNode;
import com.itextpdf.html2pdf.html.node.INode;

import java.util.HashSet;
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
                IElementNode n2 = (IElementNode) n;
                if (n2.name().equals(AttributeConstants.a) && n2.getAttribute(AttributeConstants.HREF) != null && n2.getAttribute(AttributeConstants.HREF).startsWith("#")) {
                    linkDestinations.add(n2.getAttribute(AttributeConstants.HREF).substring(1));
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
}
