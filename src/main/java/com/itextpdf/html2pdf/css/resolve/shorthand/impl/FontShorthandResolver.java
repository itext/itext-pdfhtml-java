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
package com.itextpdf.html2pdf.css.resolve.shorthand.impl;

import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.css.CssDeclaration;
import com.itextpdf.html2pdf.css.resolve.shorthand.IShorthandResolver;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FontShorthandResolver implements IShorthandResolver {
    private static final Set<String> UNSUPPORTED_VALUES_OF_FONT_SHORTHAND = new HashSet<>(Arrays.asList(
            CssConstants.CAPTION, CssConstants.ICON, CssConstants.MENU, CssConstants.MESSAGE_BOX,
            CssConstants.SMALL_CAPTION, CssConstants.STATUS_BAR
    ));

    private static final Set<String> FONT_WEIGHT_NOT_DEFAULT_VALUES = new HashSet<>(Arrays.asList(
            CssConstants.BOLD, CssConstants.BOLDER, CssConstants.LIGHTER,
            "100", "200", "300", "400", "500", "600", "700", "800", "900"
    ));
    private static final Set<String> FONT_SIZE_VALUES = new HashSet<>(Arrays.asList(
            CssConstants.MEDIUM, CssConstants.XX_SMALL, CssConstants.X_SMALL, CssConstants.SMALL, CssConstants.LARGE,
            CssConstants.X_LARGE, CssConstants.XX_LARGE, CssConstants.SMALLER, CssConstants.LARGER
    ));

    @Override
    public List<CssDeclaration> resolveShorthand(String shorthandExpression) {
        if (UNSUPPORTED_VALUES_OF_FONT_SHORTHAND.contains(shorthandExpression)) {
            Logger logger = LoggerFactory.getLogger(FontShorthandResolver.class);
            logger.error(MessageFormat.format("The \"{0}\" value of CSS shorthand property \"font\" is not supported", shorthandExpression));
        }

        String fontStyleValue = null;
        String fontVariantValue = null;
        String fontWeightValue = null;
        String fontSizeValue = null;
        String lineHeightValue = null;
        String fontFamilyValue = null;

        String[] props = shorthandExpression.replaceAll(",\\s*", ",").split(" ");
        for (String value : props) {
            int slashSymbolIndex = value.indexOf('/');
            if (CssConstants.ITALIC.equals(value) || CssConstants.OBLIQUE.equals(value)) {
                fontStyleValue = value;
            } else if (CssConstants.SMALL_CAPS.equals(value)) {
                fontVariantValue = value;
            } else if (FONT_WEIGHT_NOT_DEFAULT_VALUES.contains(value)) {
                fontWeightValue = value;
            } else if (slashSymbolIndex > 0) {
                fontSizeValue = value.substring(0, slashSymbolIndex);
                lineHeightValue = value.substring(slashSymbolIndex + 1, value.length());
            } else if (FONT_SIZE_VALUES.contains(value)) {
                fontSizeValue = value;
            } else {
                fontFamilyValue = value;
            }
        }

        List<CssDeclaration> cssDeclarations = Arrays.asList(
                new CssDeclaration(CssConstants.FONT_STYLE, fontStyleValue == null ? CssConstants.INITIAL : fontStyleValue),
                new CssDeclaration(CssConstants.FONT_VARIANT, fontVariantValue == null ? CssConstants.INITIAL : fontVariantValue),
                new CssDeclaration(CssConstants.FONT_WEIGHT, fontWeightValue == null ? CssConstants.INITIAL : fontWeightValue),
                new CssDeclaration(CssConstants.FONT_SIZE, fontSizeValue == null ? CssConstants.INITIAL : fontSizeValue),
                new CssDeclaration(CssConstants.LINE_HEIGHT, lineHeightValue == null ? CssConstants.INITIAL : lineHeightValue),
                new CssDeclaration(CssConstants.FONT_FAMILY, fontFamilyValue == null ? CssConstants.INITIAL : fontFamilyValue)
        );

        return cssDeclarations;
    }
}
