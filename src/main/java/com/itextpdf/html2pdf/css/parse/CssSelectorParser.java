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
package com.itextpdf.html2pdf.css.parse;

import com.itextpdf.html2pdf.css.selector.item.CssAttributeSelectorItem;
import com.itextpdf.html2pdf.css.selector.item.CssClassSelectorItem;
import com.itextpdf.html2pdf.css.selector.item.CssIdSelectorItem;
import com.itextpdf.html2pdf.css.selector.item.CssPseudoClassSelectorItem;
import com.itextpdf.html2pdf.css.selector.item.CssPseudoElementSelectorItem;
import com.itextpdf.html2pdf.css.selector.item.CssSeparatorSelectorItem;
import com.itextpdf.html2pdf.css.selector.item.CssTagSelectorItem;
import com.itextpdf.html2pdf.css.selector.item.ICssSelectorItem;
import com.itextpdf.io.util.MessageFormatUtil;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utilities class to parse a CSS selector.
 */
public final class CssSelectorParser {

    /** Set of legacy pseudo elements (first-line, first-letter, before, after). */
    private static final Set<String> legacyPseudoElements = new HashSet<>();
    static {
        legacyPseudoElements.add("first-line");
        legacyPseudoElements.add("first-letter");
        legacyPseudoElements.add("before");
        legacyPseudoElements.add("after");
    }

    /** The pattern string for selectors. */
    private static final String SELECTOR_PATTERN_STR =
            "(\\*)|([_a-zA-Z][\\w-]*)|(\\.[_a-zA-Z][\\w-]*)|(#[_a-z][\\w-]*)|(\\[[_a-zA-Z][\\w-]*(([~^$*|])?=((\"[^\"]+\")|([^\"]+)|('[^\"]+')))?\\])|(::?[a-zA-Z-]*(\\([ \t\\+\\.#\\w-]*\\))?)|( )|(\\+)|(>)|(~)";

    /** The pattern for selectors. */
    private static final Pattern selectorPattern = Pattern.compile(SELECTOR_PATTERN_STR);

    /**
     * Creates a new <code>CssSelectorParser</code> instance.
     */
    private CssSelectorParser() {
    }

    /**
     * Parses the selector items.
     *
     * @param selector the selectors in the form of a <code>String</code>
     * @return the resulting list of {@link ICssSelectorItem}
     */
    public static List<ICssSelectorItem> parseSelectorItems(String selector) {
        List<ICssSelectorItem> selectorItems = new ArrayList<>();
        Matcher itemMatcher = selectorPattern.matcher(selector);
        boolean tagSelectorDescription = false;
        while (itemMatcher.find()) {
            String selectorItem = itemMatcher.group(0);
            char firstChar = selectorItem.charAt(0);
            switch (firstChar) {
                case '#':
                    selectorItems.add(new CssIdSelectorItem(selectorItem.substring(1)));
                    break;
                case '.':
                    selectorItems.add(new CssClassSelectorItem(selectorItem.substring(1)));
                    break;
                case '[':
                    selectorItems.add(new CssAttributeSelectorItem(selectorItem));
                    break;
                case ':':
                    selectorItems.add(resolvePseudoSelector(selectorItem));
                    break;
                case ' ':
                case '+':
                case '>':
                case '~':
                    if (selectorItems.size() == 0) {
                        throw new IllegalArgumentException(MessageFormatUtil.format("Invalid token detected in the start of the selector string: {0}", firstChar));
                    }
                    ICssSelectorItem lastItem = selectorItems.get(selectorItems.size() - 1);
                    CssSeparatorSelectorItem curItem = new CssSeparatorSelectorItem(firstChar);
                    if (lastItem instanceof CssSeparatorSelectorItem) {
                        if (curItem.getSeparator() == ' ') {
                            break;
                        } else if (((CssSeparatorSelectorItem) lastItem).getSeparator() == ' ') {
                            selectorItems.set(selectorItems.size() - 1, curItem);
                        } else {
                            throw new IllegalArgumentException(MessageFormatUtil.format("Invalid selector description. Two consequent characters occurred: {0}, {1}", ((CssSeparatorSelectorItem) lastItem).getSeparator(), curItem.getSeparator()));
                        }
                    } else {
                        selectorItems.add(curItem);
                        tagSelectorDescription = false;
                    }
                    break;
                default: //and case '*':
                    if (tagSelectorDescription) {
                        throw new IllegalStateException("Invalid selector string");
                    }
                    tagSelectorDescription = true;
                    selectorItems.add(new CssTagSelectorItem(selectorItem));
                    break;
            }
        }

        if (selectorItems.size() == 0) {
            throw new IllegalArgumentException("Selector declaration is invalid");
        }

        return selectorItems;
    }

    /**
     * Resolves a pseudo selector.
     *
     * @param pseudoSelector the pseudo selector
     * @return the {@link ICssSelectorItem} item
     */
    private static ICssSelectorItem resolvePseudoSelector(String pseudoSelector) {
        pseudoSelector = pseudoSelector.toLowerCase();
        /*
            This :: notation is introduced by the current document in order to establish a discrimination between
            pseudo-classes and pseudo-elements.
            For compatibility with existing style sheets, user agents must also accept the previous one-colon
            notation for pseudo-elements introduced in CSS levels 1 and 2 (namely, :first-line, :first-letter, :before and :after).
            This compatibility is not allowed for the new pseudo-elements introduced in this specification.
         */
        if (pseudoSelector.startsWith("::")) {
            return new CssPseudoElementSelectorItem(pseudoSelector.substring(2));
        } else if (pseudoSelector.startsWith(":") && legacyPseudoElements.contains(pseudoSelector.substring(1))) {
            return new CssPseudoElementSelectorItem(pseudoSelector.substring(1));
        } else {
            return new CssPseudoClassSelectorItem(pseudoSelector.substring(1));
        }
    }

}
