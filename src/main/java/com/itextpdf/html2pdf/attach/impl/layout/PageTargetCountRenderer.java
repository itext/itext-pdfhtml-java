package com.itextpdf.html2pdf.attach.impl.layout;

import com.itextpdf.html2pdf.LogMessageConstant;
import com.itextpdf.io.util.MessageFormatUtil;
import com.itextpdf.layout.layout.LayoutContext;
import com.itextpdf.layout.layout.LayoutResult;
import com.itextpdf.layout.property.Property;
import com.itextpdf.layout.renderer.DrawContext;
import com.itextpdf.layout.renderer.IRenderer;
import com.itextpdf.layout.renderer.TargetCounterHandler;
import com.itextpdf.layout.renderer.TextRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link TextRenderer} implementation for the page target-counter.
 */
public class PageTargetCountRenderer extends TextRenderer {

    private static final Logger LOGGER = LoggerFactory.getLogger(PageTargetCountRenderer.class);

    private static final String UNDEFINED_VALUE = "0";

    private final String target;

    /**
     * Instantiates a new {@link PageTargetCountRenderer}.
     *
     * @param textElement the text element
     */
    PageTargetCountRenderer(PageTargetCountElement textElement) {
        super(textElement);
        target = textElement.getTarget();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LayoutResult layout(LayoutContext layoutContext) {
        final String previousText = getText().toString();
        final Integer page = TargetCounterHandler.getPageByID(this, target);
        if (page == null) {
            setText(UNDEFINED_VALUE);
        } else {
            setText(String.valueOf(page));
        }
        final LayoutResult result = super.layout(layoutContext);
        setText(previousText);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(DrawContext drawContext) {
        if (!TargetCounterHandler.isValueDefinedForThisID(this, target)) {
            LOGGER.warn(MessageFormatUtil.format(LogMessageConstant.CANNOT_RESOLVE_TARGET_COUNTER_VALUE, target));
        }
        super.draw(drawContext);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IRenderer getNextRenderer() {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean resolveFonts(List<IRenderer> addTo) {
        final List<IRenderer> dummyList = new ArrayList<>();
        super.resolveFonts(dummyList);
        setProperty(Property.FONT, dummyList.get(0).<Object>getProperty(Property.FONT));
        addTo.add(this);
        return true;
    }
}
