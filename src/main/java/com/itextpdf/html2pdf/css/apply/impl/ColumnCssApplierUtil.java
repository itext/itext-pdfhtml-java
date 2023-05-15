package com.itextpdf.html2pdf.css.apply.impl;

import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import com.itextpdf.styledxmlparser.css.util.CssDimensionParsingUtils;
import com.itextpdf.styledxmlparser.css.util.CssTypesValidationUtils;

import java.util.Map;

/**
 * Utility class to apply column-count values.
 */
public class ColumnCssApplierUtil {
    private ColumnCssApplierUtil() {
    }

    /**
     * Apply column-count to an element.
     *
     * @param cssProps the CSS properties
     * @param context  the Processor context
     * @param element the styles container
     */
    public static void applyColumnCount(Map<String, String> cssProps, ProcessorContext context, IPropertyContainer element) {
        if (context.isMulticolEnabled()) {
            Integer columnCount = CssDimensionParsingUtils.parseInteger(cssProps.get(CssConstants.COLUMN_COUNT));
            if (columnCount != null) {
                element.setProperty(Property.COLUMN_COUNT, columnCount);
            }
        }
    }
}
