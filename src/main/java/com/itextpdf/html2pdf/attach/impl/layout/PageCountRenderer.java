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
package com.itextpdf.html2pdf.attach.impl.layout;

import com.itextpdf.html2pdf.css.resolve.func.counter.CounterDigitsGlyphStyle;
import com.itextpdf.html2pdf.html.HtmlUtils;
import com.itextpdf.io.logs.IoLogMessageConstant;
import com.itextpdf.io.font.otf.GlyphLine;
import com.itextpdf.commons.utils.MessageFormatUtil;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.layout.LayoutContext;
import com.itextpdf.layout.layout.LayoutResult;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.layout.renderer.AbstractRenderer;
import com.itextpdf.layout.renderer.DocumentRenderer;
import com.itextpdf.layout.renderer.IRenderer;
import com.itextpdf.layout.renderer.TextRenderer;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link TextRenderer} implementation for the page count.
 */
class PageCountRenderer extends TextRenderer {

    private final CounterDigitsGlyphStyle digitsGlyphStyle;

    /**
     * Instantiates a new page count renderer.
     *
     * @param textElement the text element
     */
    PageCountRenderer(PageCountElement textElement) {
        super(textElement);
        this.digitsGlyphStyle = textElement.getDigitsGlyphStyle();
    }

    protected PageCountRenderer(TextRenderer other) {
        super(other);
        this.digitsGlyphStyle = ((PageCountRenderer)other).digitsGlyphStyle;
    }

    /* (non-Javadoc)
     * @see com.itextpdf.layout.renderer.TextRenderer#layout(com.itextpdf.layout.layout.LayoutContext)
     */
    @Override
    public LayoutResult layout(LayoutContext layoutContext) {
        PageCountType pageCountType = (PageCountType)this.<PageCountType>getProperty(Html2PdfProperty.PAGE_COUNT_TYPE);
        String previousText = getText().toString();
        // If typography is enabled and the page counter element has a non-default direction,
        // iText processes its content (see LineRenderer#updateBidiLevels) before layouting it.
        // This might result in an ArrayIndexOutOfBounds exception, because currently iText updates the page counter's content on layout.
        // To solve this, this workaround has been implemented: the renderer's strToBeConverted shouldn't be updated by layout.
        boolean textHasBeenReplaced = false;
        if (pageCountType == PageCountType.CURRENT_PAGE_NUMBER) {
            setText(HtmlUtils.convertNumberAccordingToGlyphStyle(digitsGlyphStyle,
                    layoutContext.getArea().getPageNumber()));
            textHasBeenReplaced = true;
        } else if (pageCountType == PageCountType.TOTAL_PAGE_COUNT) {
            IRenderer rootRenderer = this;
            while (rootRenderer instanceof AbstractRenderer && ((AbstractRenderer) rootRenderer).getParent() != null) {
                rootRenderer = ((AbstractRenderer) rootRenderer).getParent();
            }
            if (rootRenderer instanceof HtmlDocumentRenderer && ((HtmlDocumentRenderer) rootRenderer).getEstimatedNumberOfPages() > 0) {
                setText(HtmlUtils.convertNumberAccordingToGlyphStyle(digitsGlyphStyle,
                        ((HtmlDocumentRenderer) rootRenderer).getEstimatedNumberOfPages()));
                textHasBeenReplaced = true;
            } else if (rootRenderer instanceof DocumentRenderer && rootRenderer.getModelElement() instanceof Document) {
                setText(HtmlUtils.convertNumberAccordingToGlyphStyle(digitsGlyphStyle,
                        ((Document) rootRenderer.getModelElement()).getPdfDocument().getNumberOfPages()));
                textHasBeenReplaced = true;
            }
        }
        LayoutResult result = super.layout(layoutContext);
        if (textHasBeenReplaced) {
            setText(previousText);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IRenderer getNextRenderer() {
        if (PageCountRenderer.class != this.getClass()) {
            Logger logger = LoggerFactory.getLogger(PageCountRenderer.class);
            logger.error(MessageFormatUtil.format(
                    IoLogMessageConstant.GET_NEXT_RENDERER_SHOULD_BE_OVERRIDDEN));
        }
        return new PageCountRenderer((PageCountElement) modelElement);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected TextRenderer createCopy(GlyphLine gl, PdfFont font) {
        if (PageCountRenderer.class != this.getClass()) {
            Logger logger = LoggerFactory.getLogger(PageCountRenderer.class);
            logger.error(MessageFormatUtil.format(IoLogMessageConstant.CREATE_COPY_SHOULD_BE_OVERRIDDEN));
        }
        PageCountRenderer copy = new PageCountRenderer(this);
        copy.setProcessedGlyphLineAndFont(gl, font);
        return copy;
    }

    /* (non-Javadoc)
     * @see com.itextpdf.layout.renderer.TextRenderer#resolveFonts(java.util.List)
     */
    @Override
    protected boolean resolveFonts(List<IRenderer> addTo) {
        List<IRenderer> dummyList = new ArrayList<>();
        super.resolveFonts(dummyList);
        setProperty(Property.FONT, dummyList.get(0).<Object>getProperty(Property.FONT));
        addTo.add(this);
        return true;
    }

}
