package com.itextpdf.html2pdf.attach.util;

import com.itextpdf.html2pdf.html.AttributeConstants;
import com.itextpdf.kernel.pdf.tagutils.AccessibilityProperties;
import com.itextpdf.layout.tagging.IAccessibleElement;
import com.itextpdf.styledxmlparser.node.IElementNode;

public class AccessiblePropHelper {
    public static void trySetLangAttribute(IAccessibleElement accessibleElement, IElementNode element) {
        String lang = element.getAttribute(AttributeConstants.LANG);
        trySetLangAttribute(accessibleElement, lang);
    }

    public static void trySetLangAttribute(IAccessibleElement accessibleElement, String lang) {
        if (lang != null) {
            AccessibilityProperties properties = accessibleElement.getAccessibilityProperties();
            if (properties.getLanguage() == null) {
                properties.setLanguage(lang);
            }
        }
    }
}
