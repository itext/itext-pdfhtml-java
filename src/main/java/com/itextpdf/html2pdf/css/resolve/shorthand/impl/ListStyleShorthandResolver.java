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
package com.itextpdf.html2pdf.css.resolve.shorthand.impl;

import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.css.CssDeclaration;
import com.itextpdf.html2pdf.css.resolve.shorthand.IShorthandResolver;

import java.util.*;

/**
 * {@link IShorthandResolver} implementation for list styles.
 */
public class ListStyleShorthandResolver implements IShorthandResolver {
    
    /** The list style types (disc, decimal,...). */
    private static final Set<String> LIST_STYLE_TYPE_VALUES = new HashSet<>(Arrays.asList(
            CssConstants.DISC, CssConstants.ARMENIAN, CssConstants.CIRCLE, CssConstants.CJK_IDEOGRAPHIC,
            CssConstants.DECIMAL, CssConstants.DECIMAL_LEADING_ZERO, CssConstants.GEORGIAN, CssConstants.HEBREW,
            CssConstants.HIRAGANA, CssConstants.HIRAGANA_IROHA, CssConstants.LOWER_ALPHA, CssConstants.LOWER_GREEK,
            CssConstants.LOWER_LATIN, CssConstants.LOWER_ROMAN, CssConstants.NONE, CssConstants.SQUARE,
            CssConstants.UPPER_ALPHA, CssConstants.UPPER_LATIN, CssConstants.UPPER_ROMAN
    ));
    
    /** The list stype positions (inside, outside). */
    private static final Set<String> LIST_STYLE_POSITION_VALUES = new HashSet<>(Arrays.asList(
            CssConstants.INSIDE, CssConstants.OUTSIDE
    ));

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.css.resolve.shorthand.IShorthandResolver#resolveShorthand(java.lang.String)
     */
    @Override
    public List<CssDeclaration> resolveShorthand(String shorthandExpression) {
        if (CssConstants.INITIAL.equals(shorthandExpression) || CssConstants.INHERIT.equals(shorthandExpression)) {
            return Arrays.asList(
                    new CssDeclaration(CssConstants.LIST_STYLE_TYPE, shorthandExpression),
                    new CssDeclaration(CssConstants.LIST_STYLE_POSITION, shorthandExpression),
                    new CssDeclaration(CssConstants.LIST_STYLE_IMAGE, shorthandExpression));
        }

        String[] props = shorthandExpression.split("\\s+");

        String listStyleTypeValue = null;
        String listStylePositionValue = null;
        String listStyleImageValue = null;

        for (String value : props) {
            if (value.contains("url(") || CssConstants.NONE.equals(value) && listStyleTypeValue != null) {
                listStyleImageValue = value;
            } else if (LIST_STYLE_TYPE_VALUES.contains(value)) {
                listStyleTypeValue = value;
            } else if (LIST_STYLE_POSITION_VALUES.contains(value)) {
                listStylePositionValue = value;
            }
        }

        List<CssDeclaration> resolvedDecl = new ArrayList<>();
        resolvedDecl.add(new CssDeclaration(CssConstants.LIST_STYLE_TYPE, listStyleTypeValue == null ? CssConstants.INITIAL : listStyleTypeValue));
        resolvedDecl.add(new CssDeclaration(CssConstants.LIST_STYLE_POSITION, listStylePositionValue == null ? CssConstants.INITIAL : listStylePositionValue));
        resolvedDecl.add(new CssDeclaration(CssConstants.LIST_STYLE_IMAGE, listStyleImageValue == null ? CssConstants.INITIAL : listStyleImageValue));
        return resolvedDecl;
    }
}
