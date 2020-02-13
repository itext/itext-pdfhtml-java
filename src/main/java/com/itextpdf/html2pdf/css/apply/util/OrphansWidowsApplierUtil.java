package com.itextpdf.html2pdf.css.apply.util;

import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.property.ParagraphOrphansControl;
import com.itextpdf.layout.property.ParagraphWidowsControl;
import com.itextpdf.layout.property.Property;
import com.itextpdf.styledxmlparser.css.util.CssUtils;

import java.util.Map;

/**
 * Utilities class to apply orphans and widows properties.
 */
public class OrphansWidowsApplierUtil {

    public static final int MAX_LINES_TO_MOVE = 2;
    public static final boolean OVERFLOW_PARAGRAPH_ON_VIOLATION = false;

    /**
     * Creates a {@link OrphansWidowsApplierUtil} instance.
     */
    private OrphansWidowsApplierUtil() {

    }

    /**
     * Applies orphans and widows properties to an element.
     *
     * @param cssProps the CSS properties
     * @param element  to which the property will be applied.
     */
    public static void applyOrphansAndWidows(Map<String, String> cssProps, IPropertyContainer element) {
        if (cssProps != null) {
            if (cssProps.containsKey(CssConstants.WIDOWS)) {
                Integer minWidows = CssUtils.parseInteger(cssProps.get(CssConstants.WIDOWS));
                if (minWidows != null && minWidows > 0) {
                    element.setProperty(Property.WIDOWS_CONTROL,
                            new ParagraphWidowsControl(minWidows.intValue(), MAX_LINES_TO_MOVE, OVERFLOW_PARAGRAPH_ON_VIOLATION));
                }
            }

            if (cssProps.containsKey(CssConstants.ORPHANS)) {
                Integer minOrphans = CssUtils.parseInteger(cssProps.get(CssConstants.ORPHANS));
                if (minOrphans != null && minOrphans > 0) {
                    element.setProperty(Property.ORPHANS_CONTROL,
                            new ParagraphOrphansControl(minOrphans.intValue()));
                }
            }
        }
    }
}
