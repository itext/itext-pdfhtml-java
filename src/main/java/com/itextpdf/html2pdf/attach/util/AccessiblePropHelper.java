/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2025 Apryse Group NV
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
package com.itextpdf.html2pdf.attach.util;

import com.itextpdf.html2pdf.html.AttributeConstants;
import com.itextpdf.kernel.pdf.tagutils.AccessibilityProperties;
import com.itextpdf.layout.tagging.IAccessibleElement;
import com.itextpdf.styledxmlparser.node.IElementNode;

/**
 * Utility class to set lang attribute.
 */
public class AccessiblePropHelper {

    /**
     * Set language attribute in elements accessibility properties if it is not set, does nothing otherwise.
     *
     * @param accessibleElement pdf element to set language property on
     * @param element html element from which lang property will be extracted
     */
    public static void trySetLangAttribute(IAccessibleElement accessibleElement, IElementNode element) {
        String lang = element.getAttribute(AttributeConstants.LANG);
        trySetLangAttribute(accessibleElement, lang);
    }

    /**
     * Set language attribute in elements accessibility properties if it is not set, does nothing otherwise.
     *
     * @param accessibleElement pdf element to set language property on
     * @param lang language to set
     */
    public static void trySetLangAttribute(IAccessibleElement accessibleElement, String lang) {
        if (lang != null) {
            AccessibilityProperties properties = accessibleElement.getAccessibilityProperties();
            if (properties.getLanguage() == null) {
                properties.setLanguage(lang);
            }
        }
    }
}
