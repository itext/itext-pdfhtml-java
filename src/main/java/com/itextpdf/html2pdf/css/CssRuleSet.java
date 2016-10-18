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
package com.itextpdf.html2pdf.css;

import com.itextpdf.html2pdf.css.media.MediaDeviceDescription;
import com.itextpdf.html2pdf.css.selector.CssSelector;
import com.itextpdf.html2pdf.html.node.IElement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class CssRuleSet extends CssStatement {

    private static final Pattern importantMatcher = Pattern.compile(".*!\\s*important$");

    private CssSelector selector;
    private List<CssDeclaration> normalDeclarations;
    private List<CssDeclaration> importantDeclarations;

    public CssRuleSet(CssSelector selector, List<CssDeclaration> declarations) {
        this.selector = selector;
        this.normalDeclarations = new ArrayList<>();
        this.importantDeclarations = new ArrayList<>();
        splitDeclarationsIntoNormalAndImportant(declarations);
    }

    @Override
    public List<CssRuleSet> getCssRuleSets(IElement element, MediaDeviceDescription deviceDescription) {
        if (selector.matches(element)) {
            return Collections.singletonList(this);
        } else {
            return super.getCssRuleSets(element, deviceDescription);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(selector.toString());
        sb.append(" {\n");
        for (int i = 0; i < normalDeclarations.size(); i++) {
            if (i > 0) {
                sb.append(";").append("\n");
            }
            CssDeclaration declaration = normalDeclarations.get(i);
            sb.append("    ").append(declaration.toString());
        }
        for (int i = 0; i < importantDeclarations.size(); i++) {
            if (i > 0 || normalDeclarations.size() > 0) {
                sb.append(";").append("\n");
            }
            CssDeclaration declaration = importantDeclarations.get(i);
            sb.append("    ").append(declaration.toString()).append(" !important");
        }
        sb.append("\n}");
        return sb.toString();
    }

    public CssSelector getSelector() {
        return selector;
    }

    public List<CssDeclaration> getNormalDeclarations() {
        return normalDeclarations;
    }

    public List<CssDeclaration> getImportantDeclarations() {
        return importantDeclarations;
    }

    private void splitDeclarationsIntoNormalAndImportant(List<CssDeclaration> declarations) {
        for (CssDeclaration declaration : declarations) {
            int exclIndex = declaration.getExpression().indexOf('!');
            if (exclIndex > 0 && importantMatcher.matcher(declaration.getExpression()).matches()) {
                importantDeclarations.add(new CssDeclaration(declaration.getProperty(), declaration.getExpression().substring(0, exclIndex).trim()));
            } else {
                normalDeclarations.add(declaration);
            }
        }
    }

}
