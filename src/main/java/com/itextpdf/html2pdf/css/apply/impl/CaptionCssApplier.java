package com.itextpdf.html2pdf.css.apply.impl;

import com.itextpdf.html2pdf.attach.ITagWorker;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.css.apply.ICssApplier;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.property.CaptionSide;
import com.itextpdf.layout.property.Property;
import com.itextpdf.styledxmlparser.node.IStylesContainer;

import java.util.Map;

/**
 * {@link ICssApplier} implementation for a <code>caption</code>element.
 */
public class CaptionCssApplier extends BlockCssApplier {

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.css.apply.impl.BlockCssApplier#apply(com.itextpdf.html2pdf.attach.ProcessorContext, com.itextpdf.html2pdf.html.node.IStylesContainer, com.itextpdf.html2pdf.attach.ITagWorker)
     */
    @Override
    public void apply(ProcessorContext context, IStylesContainer stylesContainer, ITagWorker tagWorker) {
        Map<String, String> cssProps = stylesContainer.getStyles();
        super.apply(context, stylesContainer, tagWorker);
        if (CssConstants.BOTTOM.equals(cssProps.get(CssConstants.CAPTION_SIDE))) {
            IPropertyContainer container = tagWorker.getElementResult();
            container.setProperty(Property.CAPTION_SIDE, CaptionSide.BOTTOM);
        }

    }
}
