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


import com.itextpdf.html2pdf.css.pseudo.CssPseudoElementNode;
import com.itextpdf.html2pdf.html.impl.jsoup.node.JsoupTextNode;
import com.itextpdf.html2pdf.html.node.IElementNode;
import com.itextpdf.html2pdf.html.node.INode;

import java.util.ArrayList;
import java.util.List;

public class CssPseudoClassSelectorItem implements ICssSelectorItem {

    private String pseudoClass;
    private String arguments;

    public CssPseudoClassSelectorItem(String pseudoClass) {
        int indexOfParentheses = pseudoClass.indexOf('(');
        if (indexOfParentheses == -1) {
            this.pseudoClass = pseudoClass;
            this.arguments = null;
        }
        else {
            this.pseudoClass = pseudoClass.substring(0, indexOfParentheses);
            this.arguments = pseudoClass.substring(indexOfParentheses + 1, pseudoClass.length() - 1).toLowerCase();
        }
    }

    @Override
    public int getSpecificity() {
        return CssSpecificityConstants.CLASS_SPECIFICITY;
    }

    @Override
    public boolean matches(INode node) {
        if (!(node instanceof IElementNode) || node instanceof CssPseudoElementNode) {
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
        return ":" + pseudoClass + (arguments != null ? new String("(" + arguments + ")") : "");
    }

    private List<INode> getAllChildren(INode child) {
        INode parentElement = child.parentNode();
        List<INode> childrenUnmodifiable = parentElement.childNodes();
        List<INode> children = new ArrayList<INode>(childrenUnmodifiable.size());
        for (INode iNode : childrenUnmodifiable) {
            if (iNode instanceof IElementNode)
                children.add(iNode);
        }
        return children;
    }

    private boolean resolveNthChild(INode node, List<INode> children) {
        if (arguments.matches("\\s*((-|\\+)?[0-9]*n(\\s*(-|\\+)\\s*[0-9]+)?|(-|\\+)?[0-9]+|odd|even)\\s*")) {
            int a, b;
            boolean bIsPositive = true;
            if (arguments.matches("\\s*(odd|even)\\s*")) {
                a = 2;
                b = arguments.matches("\\s*odd\\s*") ? 1 : 0;
            }
            else {
                int indexOfN = arguments.indexOf('n');
                if (indexOfN == -1) {
                    a = children.size();
                    b = Integer.valueOf(arguments);
                }
                else
                {
                    a = Integer.valueOf(arguments.substring(0, indexOfN).trim());
                    String[] bParticle = arguments.substring(indexOfN + 1).trim().split("\\s+");
                    bIsPositive = bParticle[0].equals("+") ? true : false;
                    b = Integer.valueOf(bParticle[1]);
                }
            }
            if (bIsPositive)
                return (children.indexOf(node) + 1) % a == b;
            else
                return (children.indexOf(node) + 1) % a == a - b;
        }
        else
            return false;
    }
}
