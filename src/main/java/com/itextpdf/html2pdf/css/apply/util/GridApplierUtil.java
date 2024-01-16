package com.itextpdf.html2pdf.css.apply.util;

import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.styledxmlparser.css.util.CssDimensionParsingUtils;

import java.util.Map;

/**
 * Utilities class to apply css grid properties and styles.
 */
public final class GridApplierUtil {
    private GridApplierUtil() {
        // empty constructor
    }

    /**
     * Applies grid properties to an element.
     *
     * @param cssProps the CSS properties
     * @param element  the element
     */
    public static void applyGridItemProperties(Map<String, String> cssProps, IPropertyContainer element) {

        Integer columnStart = CssDimensionParsingUtils.parseInteger(cssProps.get(CssConstants.GRID_COLUMN_START));
        if (columnStart != null) {
            element.setProperty(Property.GRID_COLUMN_START, columnStart);
        }

        Integer columnEnd = CssDimensionParsingUtils.parseInteger(cssProps.get(CssConstants.GRID_COLUMN_END));
        if (columnEnd != null) {
            element.setProperty(Property.GRID_COLUMN_END, columnEnd);
        }

        Integer rowStart = CssDimensionParsingUtils.parseInteger(cssProps.get(CssConstants.GRID_ROW_START));
        if (rowStart != null) {
            element.setProperty(Property.GRID_ROW_START, rowStart);
        }

        Integer rowEnd = CssDimensionParsingUtils.parseInteger(cssProps.get(CssConstants.GRID_ROW_END));
        if (rowEnd != null) {
            element.setProperty(Property.GRID_ROW_END, rowEnd);
        }
    }
}
