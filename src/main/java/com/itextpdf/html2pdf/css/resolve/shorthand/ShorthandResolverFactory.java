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
package com.itextpdf.html2pdf.css.resolve.shorthand;

import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.css.resolve.shorthand.impl.BackgroundShorthandResolver;
import com.itextpdf.html2pdf.css.resolve.shorthand.impl.BorderBottomShorthandResolver;
import com.itextpdf.html2pdf.css.resolve.shorthand.impl.BorderColorShorthandResolver;
import com.itextpdf.html2pdf.css.resolve.shorthand.impl.BorderLeftShorthandResolver;
import com.itextpdf.html2pdf.css.resolve.shorthand.impl.BorderRightShorthandResolver;
import com.itextpdf.html2pdf.css.resolve.shorthand.impl.BorderShorthandResolver;
import com.itextpdf.html2pdf.css.resolve.shorthand.impl.BorderStyleShorthandResolver;
import com.itextpdf.html2pdf.css.resolve.shorthand.impl.BorderTopShorthandResolver;
import com.itextpdf.html2pdf.css.resolve.shorthand.impl.BorderWidthShorthandResolver;
import com.itextpdf.html2pdf.css.resolve.shorthand.impl.FontShorthandResolver;
import com.itextpdf.html2pdf.css.resolve.shorthand.impl.ListStyleShorthandResolver;
import com.itextpdf.html2pdf.css.resolve.shorthand.impl.MarginShorthandResolver;
import com.itextpdf.html2pdf.css.resolve.shorthand.impl.OutlineShorthandResolver;
import com.itextpdf.html2pdf.css.resolve.shorthand.impl.PaddingShorthandResolver;

public class ShorthandResolverFactory {
    public static IShorthandResolver getShorthandResolver(String shorthandProperty) {
        // TODO text-decoration is a shorthand in CSS3, however it is not yet supported in any major browsers
        // TODO use static map
        switch (shorthandProperty) {
            case CssConstants.BACKGROUND:
                return new BackgroundShorthandResolver();
            case CssConstants.BORDER:
                return new BorderShorthandResolver();
            case CssConstants.BORDER_BOTTOM:
                return new BorderBottomShorthandResolver();
            case CssConstants.BORDER_COLOR:
                return new BorderColorShorthandResolver();
            case CssConstants.BORDER_LEFT:
                return new BorderLeftShorthandResolver();
            case CssConstants.BORDER_RIGHT:
                return new BorderRightShorthandResolver();
            case CssConstants.BORDER_STYLE:
                return new BorderStyleShorthandResolver();
            case CssConstants.BORDER_TOP:
                return new BorderTopShorthandResolver();
            case CssConstants.BORDER_WIDTH:
                return new BorderWidthShorthandResolver();
            case CssConstants.FONT:
                return new FontShorthandResolver();
            case CssConstants.LIST_STYLE:
                return new ListStyleShorthandResolver();
            case CssConstants.MARGIN:
                return new MarginShorthandResolver();
            case CssConstants.OUTLINE:
                return new OutlineShorthandResolver();
            case CssConstants.PADDING:
                return new PaddingShorthandResolver();
        }
        return null;
    }
}
