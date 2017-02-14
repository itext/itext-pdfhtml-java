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
package com.itextpdf.html2pdf.css.parse;

import com.itextpdf.html2pdf.LogMessageConstant;
import com.itextpdf.html2pdf.css.CssDeclaration;
import com.itextpdf.html2pdf.css.CssRuleSet;
import com.itextpdf.html2pdf.css.selector.CssSelector;
import com.itextpdf.html2pdf.css.util.CssUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public final class CssRuleSetParser {

    private static final Logger logger = LoggerFactory.getLogger(CssRuleSetParser.class);

    private CssRuleSetParser() {
    }

    public static List<CssDeclaration> parsePropertyDeclarations(String propertiesStr) {
        List<CssDeclaration> declarations = new ArrayList<>();
        int pos = getSemicolonPosition(propertiesStr);
        while (pos != -1) {
            String[] propertySplit = splitCssProperty(propertiesStr.substring(0, pos));
            if (propertySplit != null) {
                declarations.add(new CssDeclaration(propertySplit[0], propertySplit[1]));
            }
            propertiesStr = propertiesStr.substring(pos + 1);
            pos = getSemicolonPosition(propertiesStr);
        }
        if (!propertiesStr.replaceAll("[\\n\\r\\t ]", "").isEmpty()) {
            String[] propertySplit = splitCssProperty(propertiesStr);
            if (propertySplit != null) {
                declarations.add(new CssDeclaration(propertySplit[0], propertySplit[1]));
            }
            return declarations;
        }
        return declarations;
    }

    // Returns List because selector can be compound, like "p, div, #navbar".
    public static List<CssRuleSet> parseRuleSet(String selectorStr, String propertiesStr) {
        List<CssDeclaration> declarations = parsePropertyDeclarations(propertiesStr);
        List<CssRuleSet> ruleSets = new ArrayList<>();

        //check for rules like p, {…}
        String[] selectors = selectorStr.split(",");
        for (int i = 0; i < selectors.length; i++) {
            selectors[i] = CssUtils.removeDoubleSpacesAndTrim(selectors[i]);
            if (selectors[i].length() == 0)
                return ruleSets;
        }
        for (String currentSelectorStr : selectors) {
            try {
                CssSelector selector = new CssSelector(currentSelectorStr);
                ruleSets.add(new CssRuleSet(selector, declarations));
            } catch (Exception exc) {
                logger.error(LogMessageConstant.ERROR_PARSING_CSS_SELECTOR, exc);
                //if any separated selector has errors, all others become invalid.
                //in this case we just clear map, it is the easies way to support this.
                declarations.clear();
                return ruleSets;
            }
        }

        return ruleSets;
    }

    private static String[] splitCssProperty(String property) {
        String[] result = new String[2];
        int position = property.indexOf(":");
        if (position < 0) {
            logger.error(MessageFormat.format(LogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION, property.trim()));
            return null;
        }
        result[0] = property.substring(0, position);
        result[1] = property.substring(position + 1);

        return result;
    }

    private static int getSemicolonPosition(String propertiesStr) {
        int semiColonPos = propertiesStr.indexOf(";");
        int openedBracketPos = propertiesStr.indexOf("(");
        int closedBracketPos = propertiesStr.indexOf(")");
        if (semiColonPos != -1 && semiColonPos > openedBracketPos && semiColonPos < closedBracketPos) {
            int pos = getSemicolonPosition(propertiesStr.substring(semiColonPos + 1)) + 1;
            if (pos > 0) {
                semiColonPos += getSemicolonPosition(propertiesStr.substring(semiColonPos + 1)) + 1;
            } else {
                semiColonPos = -1;
            }
        }
        return semiColonPos;
    }
}
