package com.itextpdf.html2pdf.css.apply.impl;

import com.itextpdf.html2pdf.attach.ITagWorker;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.css.apply.ICssApplier;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import com.itextpdf.styledxmlparser.css.util.CssDimensionParsingUtils;
import com.itextpdf.styledxmlparser.css.util.CssUtils;
import com.itextpdf.styledxmlparser.node.IStylesContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * {@link ICssApplier} implementation for elements with display grid.
 */
public class DisplayGridTagCssApplier extends BlockCssApplier {

    /**
     * {@inheritDoc}
     */
    @Override
    public void apply(ProcessorContext context, IStylesContainer stylesContainer, ITagWorker tagWorker) {
        super.apply(context, stylesContainer, tagWorker);
        final IPropertyContainer container = tagWorker.getElementResult();
        if (container != null) {
            Map<String, String> cssProps = stylesContainer.getStyles();

            final float emValue = CssDimensionParsingUtils.parseAbsoluteFontSize(cssProps.get(CssConstants.FONT_SIZE));
            final float remValue = context.getCssContext().getRootFontSize();

            final String templateColumnsStr = cssProps.get(CssConstants.GRID_TEMPLATE_COLUMNS);
            parseAndSetTemplate(templateColumnsStr, container, Property.GRID_TEMPLATE_COLUMNS, emValue, remValue);

            final String templateRowsStr = cssProps.get(CssConstants.GRID_TEMPLATE_ROWS);
            parseAndSetTemplate(templateRowsStr, container, Property.GRID_TEMPLATE_ROWS, emValue, remValue);

            final String autoRows = cssProps.get(CssConstants.GRID_AUTO_ROWS);
            final UnitValue autoRowsUnit = CssDimensionParsingUtils.parseLengthValueToPt(autoRows, emValue, remValue);
            if (autoRowsUnit != null) {
                container.setProperty(Property.GRID_AUTO_ROWS, autoRowsUnit);
            }

            final String autoColumns = cssProps.get(CssConstants.GRID_AUTO_COLUMNS);
            final UnitValue autoColumnsUnit = CssDimensionParsingUtils.parseLengthValueToPt(autoColumns, emValue, remValue);
            if (autoColumnsUnit != null) {
                container.setProperty(Property.GRID_AUTO_COLUMNS, autoColumnsUnit);
            }
        }
        MultiColumnCssApplierUtil.applyMultiCol(stylesContainer.getStyles(), context, container);
    }

    private static void parseAndSetTemplate(String templateStr, IPropertyContainer container, int property, float emValue, float remValue) {
        if (templateStr != null) {
            final List<String> templateStrArray = CssUtils.extractShorthandProperties(templateStr).get(0);
            final List<UnitValue> templateResult = new ArrayList<>();
            for (String s : templateStrArray) {
                final UnitValue trackUnit = CssDimensionParsingUtils.parseLengthValueToPt(s, emValue, remValue);
                if (trackUnit != null) {
                    templateResult.add(trackUnit);
                }
            }
            if (!templateResult.isEmpty()) {
                container.setProperty(property, templateResult);
            }
        }
    }
}
