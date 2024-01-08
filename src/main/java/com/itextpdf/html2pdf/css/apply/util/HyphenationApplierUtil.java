/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2024 Apryse Group NV
    Authors: Apryse Software.

    This program is offered under a commercial and under the AGPL license.
    For commercial licensing, contact us at https://itextpdf.com/sales.  For AGPL licensing, see below.

    AGPL licensing:
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.itextpdf.html2pdf.css.apply.util;

import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.hyphenation.HyphenationConfig;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.styledxmlparser.css.resolve.CssDefaults;
import com.itextpdf.styledxmlparser.node.IElementNode;
import com.itextpdf.styledxmlparser.node.IStylesContainer;

import java.util.Map;

/**
 * The Class HyphenationApplierUtil.
 */
public final class HyphenationApplierUtil {

    // These are css properties actually, but it is not supported by the browsers currently

    /** The Constant HYPHENATE_BEFORE. */
    private static final int HYPHENATE_BEFORE = 2;
    
    /** The Constant HYPHENATE_AFTER. */
    private static final int HYPHENATE_AFTER = 3;

    /**
     * Creates a new {@link HyphenationApplierUtil} instance.
     */
    private HyphenationApplierUtil() {
    }

    /**
     * Applies hyphenation to an element.
     *
     * @param cssProps the CSS props
     * @param context the processor context
     * @param stylesContainer the styles container
     * @param element the element
     */
    public static void applyHyphenation(Map<String, String> cssProps, ProcessorContext context, IStylesContainer stylesContainer, IPropertyContainer element) {
        String value = cssProps.get(CssConstants.HYPHENS);
        if (value == null) {
            value = CssDefaults.getDefaultValue(CssConstants.HYPHENS);
        }

        if (CssConstants.NONE.equals(value)) {
            element.setProperty(Property.HYPHENATION, null);
        } else if (CssConstants.MANUAL.equals(value)) {
            element.setProperty(Property.HYPHENATION, new HyphenationConfig(HYPHENATE_BEFORE, HYPHENATE_AFTER));
        } else if (CssConstants.AUTO.equals(value) && stylesContainer instanceof IElementNode) {
            String lang = ((IElementNode)stylesContainer).getLang();
            if (lang != null && lang.length() > 0) {
                element.setProperty(Property.HYPHENATION, new HyphenationConfig(lang.substring(0, 2), "", HYPHENATE_BEFORE, HYPHENATE_AFTER));
            }
        }
    }

}
