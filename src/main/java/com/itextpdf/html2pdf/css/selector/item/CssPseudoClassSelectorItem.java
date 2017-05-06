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
package com.itextpdf.html2pdf.css.selector.item;


import com.itextpdf.html2pdf.html.node.ICustomElementNode;
import com.itextpdf.html2pdf.html.node.IElementNode;
import com.itextpdf.html2pdf.html.node.INode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CssPseudoClassSelectorItem implements ICssSelectorItem {

    private String pseudoClass;
    private String arguments;
    private int nthChildA;
    private int nthChildB;

    public CssPseudoClassSelectorItem(String pseudoClass) {
        int indexOfParentheses = pseudoClass.indexOf('(');
        if (indexOfParentheses == -1) {
            this.pseudoClass = pseudoClass;
            this.arguments = "";
        } else {
            this.pseudoClass = pseudoClass.substring(0, indexOfParentheses);
            this.arguments = pseudoClass.substring(indexOfParentheses + 1, pseudoClass.length() - 1).trim();
            getNthChildArguments();
        }
    }

    @Override
    public int getSpecificity() {
        return CssSpecificityConstants.CLASS_SPECIFICITY;
    }

    @Override
    public boolean matches(INode node) {
        if (!(node instanceof IElementNode) || node instanceof ICustomElementNode) {
            return false;
        }
        List<INode> children = getAllChildren(node);
        switch (pseudoClass) {
            case "first-child":
                return children.isEmpty() ? false : node.equals(children.get(0));
            case "last-child":
                return children.isEmpty() ? false : node.equals(children.get(children.size() - 1));
            case "nth-child":
                return children.isEmpty() ? false : resolveNthChild(node, children);
            default:
                return false;
        }
    }

    @Override
    public String toString() {
        return ":" + pseudoClass + (!arguments.isEmpty() ? "(" + arguments + ")" : "");
    }

    private List<INode> getAllChildren(INode child) {
        INode parentElement = child.parentNode();
        if (parentElement != null) {
            List<INode> childrenUnmodifiable = parentElement.childNodes();
            List<INode> children = new ArrayList<INode>(childrenUnmodifiable.size());
            for (INode iNode : childrenUnmodifiable) {
                if (iNode instanceof IElementNode)
                    children.add(iNode);
            }
            return children;
        }
        return Collections.<INode>emptyList();
    }

    private void getNthChildArguments() {
        if (arguments.matches("((-|\\+)?[0-9]*n(\\s*(-|\\+)\\s*[0-9]+)?|(-|\\+)?[0-9]+|odd|even)")) {
            if (arguments.equals("odd")) {
                this.nthChildA = 2;
                this.nthChildB = 1;
            } else if (arguments.equals("even")) {
                this.nthChildA = 2;
                this.nthChildB = 0;
            } else {
                int indexOfN = arguments.indexOf('n');
                if (indexOfN == -1) {
                    this.nthChildA = 0;
                    this.nthChildB = Integer.valueOf(arguments);
                } else {
                    String aParticle = arguments.substring(0, indexOfN).trim();
                    if (aParticle.isEmpty())
                        this.nthChildA = 0;
                    else if (aParticle.length() == 1 && !Character.isDigit(aParticle.charAt(0)))
                        this.nthChildA = aParticle.equals("+") ? 1 : -1;
                    else
                        this.nthChildA = Integer.valueOf(aParticle);
                    String bParticle = arguments.substring(indexOfN + 1).trim();
                    if (!bParticle.isEmpty())
                        this.nthChildB = Integer.valueOf(bParticle.charAt(0) + bParticle.substring(1).trim());
                    else
                        this.nthChildB = 0;
                }
            }
        } else {
            this.nthChildA = 0;
            this.nthChildB = 0;
        }
    }

    private boolean resolveNthChild(INode node, List<INode> children) {
        if (!children.contains(node))
            return false;
        if (this.nthChildA > 0) {
            int temp = children.indexOf(node) + 1 - this.nthChildB;
            return temp >= 0 ? temp % this.nthChildA == 0 : false;
        } else if (this.nthChildA < 0) {
            int temp = children.indexOf(node) + 1 - this.nthChildB;
            return temp <= 0 ? temp % this.nthChildA == 0 : false;
        } else
            return (children.indexOf(node) + 1) - this.nthChildB == 0;
    }
}
