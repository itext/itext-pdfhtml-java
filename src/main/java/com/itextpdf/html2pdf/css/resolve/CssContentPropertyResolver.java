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
package com.itextpdf.html2pdf.css.resolve;

import com.itextpdf.html2pdf.LogMessageConstant;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.html.node.INode;
import com.itextpdf.html2pdf.html.node.ITextNode;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class CssContentPropertyResolver {

    static INode resolveContent(String contentStr, INode contentContainer, CssContext context) {
        if (contentStr == null || CssConstants.NONE.equals(contentStr) || CssConstants.NORMAL.equals(contentStr)) {
            return null;
        }

        StringBuilder content = new StringBuilder();
        StringBuilder nonDirectContent = new StringBuilder();
        boolean insideQuotes = false;
        boolean insideDoubleQuotes = false;
        for (int i = 0; i < contentStr.length(); ++i) {
            if (contentStr.charAt(i) == '"' && (!insideQuotes || insideDoubleQuotes) 
                    || contentStr.charAt(i) == '\'' && (!insideQuotes || !insideDoubleQuotes)) {
                if (!insideQuotes) {
                    // TODO in future, try to resolve if counter() or smth like that encountered
                    if (!nonDirectContent.toString().trim().isEmpty()) {
                        break;
                    }
                    nonDirectContent.setLength(0);
                    insideDoubleQuotes = contentStr.charAt(i) == '"'; 
                }
                insideQuotes = !insideQuotes;
            } else if (insideQuotes) {
                content.append(contentStr.charAt(i));
            } else {
                nonDirectContent.append(contentStr.charAt(i));
            }
        }
        if (!nonDirectContent.toString().trim().isEmpty()) {
            Logger logger = LoggerFactory.getLogger(CssContentPropertyResolver.class);
            logger.error(MessageFormat.format(LogMessageConstant.CONTENT_PROPERTY_INVALID, contentStr));
            return null;
        }
        // TODO resolve unicode sequences. see PseudoElementsTest#collapsingMarginsBeforeAfterPseudo03
        String resolvedContent = content.toString();
        
        // TODO in future, when img content values will be supported, some specific IElementNode might be returned with 
        // correct src attribute, however this element shall not get img styles from css style sheet 
        // and also the one that implements it should be aware of possible infinite loop (see PseudoElementsTest#imgPseudoTest02)
        return new ContentTextNode(contentContainer, resolvedContent);
    }

    private static class ContentTextNode implements ITextNode {
        private final INode parent;
        private String content;

        public ContentTextNode(INode parent, String content) {
            this.parent = parent;
            this.content = content;
        }

        @Override
        public List<INode> childNodes() {
            return Collections.<INode>emptyList();
        }

        @Override
        public void addChild(INode node) {
            throw new UnsupportedOperationException();
        }

        @Override
        public INode parentNode() {
            return parent;
        }

        @Override
        public String wholeText() {
            return content;
        }

    }
}
