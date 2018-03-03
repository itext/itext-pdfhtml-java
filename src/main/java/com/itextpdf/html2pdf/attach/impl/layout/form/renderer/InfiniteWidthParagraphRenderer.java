package com.itextpdf.html2pdf.attach.impl.layout.form.renderer;

import com.itextpdf.io.font.otf.GlyphLine;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.layout.LayoutArea;
import com.itextpdf.layout.layout.LayoutContext;
import com.itextpdf.layout.layout.LayoutResult;
import com.itextpdf.layout.layout.MinMaxWidthLayoutResult;
import com.itextpdf.layout.property.OverflowPropertyValue;
import com.itextpdf.layout.property.Property;
import com.itextpdf.layout.renderer.IRenderer;
import com.itextpdf.layout.renderer.LineRenderer;
import com.itextpdf.layout.renderer.ParagraphRenderer;
import com.itextpdf.layout.splitting.ISplitCharacters;

/**
 * TODO
 * Is required for layouting all the options text in one line.
 *
 * Will be redundant when no-wrap value for white-space property will be supported
 */
class InfiniteWidthParagraphRenderer extends ParagraphRenderer { // TODO make a block with paragraph as child

    public InfiniteWidthParagraphRenderer(ParagraphRenderer renderer) {
        super((Paragraph) renderer.getModelElement());
        childRenderers.addAll(renderer.getChildRenderers());
        setParent(renderer.getParent());

        setProperty(Property.OVERFLOW_X, OverflowPropertyValue.VISIBLE);
        setProperty(Property.OVERFLOW_Y, OverflowPropertyValue.VISIBLE);

//        setProperty(Property.SPLIT_CHARACTERS, new ISplitCharacters() {
//            @Override
//            public boolean isSplitCharacter(GlyphLine text, int glyphPos) {
//                return false;
//            }
//        });
    }

    private InfiniteWidthParagraphRenderer(Paragraph p) {
        super(p);
    }

    @Override
    public LayoutResult layout(LayoutContext layoutContext) {
        LayoutArea area = layoutContext.getArea().clone();
        area.getBBox().setWidth(INF);
        LayoutResult layoutResult = super.layout(new LayoutContext(area, layoutContext.getMarginsCollapseInfo(),
                layoutContext.getFloatRendererAreas(), layoutContext.isClippedHeight()));

        // TODO result not FULL

        LayoutArea resultOccArea = layoutResult.getOccupiedArea().clone();

        float maxLineWidth = Float.MIN_VALUE;
        for (LineRenderer line : getLines()) { // TODO no lines? null lines?
            maxLineWidth = Math.max(line.getOccupiedArea().getBBox().getHeight(), maxLineWidth);
        }
        resultOccArea.getBBox().setWidth(maxLineWidth);

        LayoutResult adjustedResult;
        if (layoutResult instanceof MinMaxWidthLayoutResult) { // TODO probably not required if won't extend ParagraphRenderer
            adjustedResult = new MinMaxWidthLayoutResult(layoutResult.getStatus(), resultOccArea,
                    layoutResult.getSplitRenderer(), layoutResult.getOverflowRenderer(), layoutResult.getCauseOfNothing());
            ((MinMaxWidthLayoutResult)adjustedResult).setMinMaxWidth(((MinMaxWidthLayoutResult) layoutResult).getMinMaxWidth());
        } else {
            adjustedResult = new LayoutResult(layoutResult.getStatus(), resultOccArea,
                    layoutResult.getSplitRenderer(), layoutResult.getOverflowRenderer(), layoutResult.getCauseOfNothing());
        }

        return adjustedResult;
    }

    @Override
    public IRenderer getNextRenderer() {
        return new InfiniteWidthParagraphRenderer((Paragraph) modelElement);
    }
}
