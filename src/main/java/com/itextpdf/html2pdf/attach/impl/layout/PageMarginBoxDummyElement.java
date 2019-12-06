/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2019 iText Group NV
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
package com.itextpdf.html2pdf.attach.impl.layout;

import com.itextpdf.styledxmlparser.css.page.PageMarginBoxContextNode;
import com.itextpdf.styledxmlparser.node.IAttributes;
import com.itextpdf.styledxmlparser.node.ICustomElementNode;
import com.itextpdf.styledxmlparser.node.IElementNode;
import com.itextpdf.styledxmlparser.node.INode;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @deprecated Remove this class in 7.2 and use {@link PageMarginBoxContextNode} instead
 *             (by making it implement {@link IElementNode}).
 */
@Deprecated
class PageMarginBoxDummyElement implements IElementNode, ICustomElementNode {

    /** The resolved styles. */
    private Map<String, String> elementResolvedStyles;

    @Override
    public String name() {
        return PageMarginBoxContextNode.PAGE_MARGIN_BOX_TAG;
    }

    @Override
    public IAttributes getAttributes() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getAttribute(String key) {
        return null;
    }

    @Override
    public List<Map<String, String>> getAdditionalHtmlStyles() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addAdditionalHtmlStyles(Map<String, String> styles) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getLang() {
        throw new UnsupportedOperationException();
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

    @Override
    public void setStyles(Map<String, String> stringStringMap) {
        elementResolvedStyles = stringStringMap;
    }

    @Override
    public Map<String, String> getStyles() {
        return elementResolvedStyles == null ? Collections.<String,String>emptyMap() : elementResolvedStyles;
    }
}
