/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2018 iText Group NV
    Authors: Bruno Lowagie, Paulo Soares, et al.

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License version 3
    as published by the Free Software Foundation with the addition of the
    following permission added to Section 15 as permitted in Section 7(a):
    FOR ANY PART OF THE COVERED WORK IN WHICH THE COPYRIGHT IS OWNED BY
    ITEXT GROUP. ITEXT GROUP DISCLAIMS THE WARRANTY OF NON INFRINGEMENT
    OF THIRD PARTY RIGHTS

    This program is distributed in the hope that it will be useful, but
    WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
    or FITNESS FOR A PARTICULAR PURPOSE.
    See the GNU Affero General Public License for more details.
    You should have received a copy of the GNU Affero General Public License
    along with this program; if not, see http://www.gnu.org/licenses or write to
    the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
    Boston, MA, 02110-1301 USA, or download the license from the following URL:
    http://itextpdf.com/terms-of-use/

    The interactive user interfaces in modified source and object code versions
    of this program must display Appropriate Legal Notices, as required under
    Section 5 of the GNU Affero General Public License.

    In accordance with Section 7(b) of the GNU Affero General Public License,
    a covered work must retain the producer line in every PDF that is created
    or manipulated using iText.

    You can be released from the requirements of the license by purchasing
    a commercial license. Buying such a license is mandatory as soon as you
    develop commercial activities involving the iText software without
    disclosing the source code of your own applications.
    These activities include: offering paid services to customers as an ASP,
    serving PDFs on the fly in a web application, shipping iText with a closed
    source product.

    For more information, please contact iText Software Corp. at this
    address: sales@itextpdf.com
 */
package com.itextpdf.html2pdf.attach.impl.layout.form.renderer;

import com.itextpdf.html2pdf.LogMessageConstant;
import com.itextpdf.html2pdf.attach.impl.layout.Html2PdfProperty;
import com.itextpdf.html2pdf.attach.impl.layout.form.element.IFormField;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.layout.layout.LayoutArea;
import com.itextpdf.layout.layout.LayoutContext;
import com.itextpdf.layout.layout.LayoutResult;
import com.itextpdf.layout.layout.MinMaxWidthLayoutResult;
import com.itextpdf.layout.minmaxwidth.MinMaxWidth;
import com.itextpdf.layout.property.FloatPropertyValue;
import com.itextpdf.layout.property.Property;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.renderer.*;
import org.slf4j.LoggerFactory;

/**
 * Abstract {@link BlockRenderer} for form fields.
 */
public abstract class AbstractFormFieldRenderer extends BlockRenderer implements ILeafElementRenderer {

    /**
     * The flat renderer.
     */
    protected IRenderer flatRenderer;

    /**
     * Creates a new {@link AbstractFormFieldRenderer} instance.
     *
     * @param modelElement the model element
     */
    AbstractFormFieldRenderer(IFormField modelElement) {
        super(modelElement);
    }

    /**
     * Checks if form fields need to be flattened.
     *
     * @return true, if fields need to be flattened
     */
    public boolean isFlatten() {
        Boolean flatten = getPropertyAsBoolean(Html2PdfProperty.FORM_FIELD_FLATTEN);
        return flatten != null ? (boolean) flatten : (boolean) modelElement.<Boolean>getDefaultProperty(Html2PdfProperty.FORM_FIELD_FLATTEN);
    }

    /**
     * Gets the default value of the form field.
     *
     * @return the default value of the form field
     */
    public String getDefaultValue() {
        String defaultValue = this.<String>getProperty(Html2PdfProperty.FORM_FIELD_VALUE);
        return defaultValue != null ? defaultValue : modelElement.<String>getDefaultProperty(Html2PdfProperty.FORM_FIELD_VALUE);
    }

    /* (non-Javadoc)
     * @see com.itextpdf.layout.renderer.BlockRenderer#layout(com.itextpdf.layout.layout.LayoutContext)
     */
    @Override
    public LayoutResult layout(LayoutContext layoutContext) {
        return layout(layoutContext, false);
    }

    /* (non-Javadoc)
     * @see com.itextpdf.layout.renderer.BlockRenderer#draw(com.itextpdf.layout.renderer.DrawContext)
     */
    @Override
    public void draw(DrawContext drawContext) {
        if (flatRenderer != null) {
            super.draw(drawContext);
        }
    }

    /* (non-Javadoc)
     * @see com.itextpdf.layout.renderer.AbstractRenderer#drawChildren(com.itextpdf.layout.renderer.DrawContext)
     */
    @Override
    public void drawChildren(DrawContext drawContext) {
        drawContext.getCanvas().saveState();
        boolean flatten = isFlatten();
        if (flatten) {
            drawContext.getCanvas().rectangle(occupiedArea.getBBox()).clip().newPath();
            flatRenderer.draw(drawContext);
        } else {
            applyAcroField(drawContext);
        }
        drawContext.getCanvas().restoreState();
    }

    /* (non-Javadoc)
     * @see com.itextpdf.layout.renderer.BlockRenderer#getMinMaxWidth(float)
     */
    @Override
    protected MinMaxWidth getMinMaxWidth(float availableWidth) {
        MinMaxWidthLayoutResult result = (MinMaxWidthLayoutResult) layout(new LayoutContext(new LayoutArea(1, new Rectangle(availableWidth, AbstractRenderer.INF))), true);
        return result.getNotNullMinMaxWidth(availableWidth);
    }

    /**
     * Adjusts the field layout.
     */
    protected abstract void adjustFieldLayout();

    /**
     * Creates the flat renderer instance.
     *
     * @return the renderer instance
     */
    protected abstract IRenderer createFlatRenderer();

    /**
     * Applies the AcroField widget.
     *
     * @param drawContext the draw context
     */
    protected abstract void applyAcroField(DrawContext drawContext);

    /**
     * Gets the model id.
     *
     * @return the model id
     */
    protected String getModelId() {
        return ((IFormField) getModelElement()).getId();
    }

    /**
     * Checks if the renderer fits a certain width and height.
     *
     * @param availableWidth  the available width
     * @param availableHeight the available height
     * @return true, if the renderer fits
     */
    protected boolean isRendererFit(float availableWidth, float availableHeight) {
        if (occupiedArea == null) {
            return false;
        }
        return availableHeight >= occupiedArea.getBBox().getHeight() && availableWidth >= occupiedArea.getBBox().getWidth();
    }

    /**
     * Gets the content width.
     *
     * @return the content width
     */
    protected Float getContentWidth() {
        UnitValue width = this.<UnitValue>getProperty(Property.WIDTH);
        if (width != null) {
            if (width.isPointValue()) {
                return retrieveWidth(0);
            } else {
                LoggerFactory.getLogger(getClass()).warn(LogMessageConstant.INPUT_SUPPORTS_ONLY_POINT_WIDTH);
            }
        }
        return null;
    }

    private LayoutResult layout(LayoutContext layoutContext, boolean minMaxWidth) {
        childRenderers.clear();
        flatRenderer = null;

        float parentWidth = layoutContext.getArea().getBBox().getWidth();
        float parentHeight = layoutContext.getArea().getBBox().getHeight();

        UnitValue maxHeight = this.<UnitValue>getProperty(Property.MAX_HEIGHT);
        UnitValue height = this.<UnitValue>getProperty(Property.HEIGHT);
        boolean restoreMaxHeight = hasOwnProperty(Property.MAX_HEIGHT);
        boolean restoreHeight = hasOwnProperty(Property.HEIGHT);
        setProperty(Property.MAX_HEIGHT, null);
        setProperty(Property.HEIGHT, null);

        IRenderer renderer = createFlatRenderer();
        Float width = getContentWidth();
        if (width != null) {
            renderer.setProperty(Property.WIDTH, new UnitValue(UnitValue.POINT, (float) width));
        }
        addChild(renderer);

        Rectangle bBox = layoutContext.getArea().getBBox().clone().moveDown(INF - parentHeight).setHeight(INF);
        layoutContext.getArea().setBBox(bBox);
        LayoutResult result = super.layout(layoutContext);

        if (restoreMaxHeight) {
            setProperty(Property.MAX_HEIGHT, maxHeight);
        } else {
            deleteOwnProperty(Property.MAX_HEIGHT);
        }
        if (restoreHeight) {
            setProperty(Property.HEIGHT, height);
        } else {
            deleteOwnProperty(Property.HEIGHT);
        }

        if (!Boolean.TRUE.equals(getPropertyAsBoolean(Property.FORCED_PLACEMENT)) && (result.getStatus() != LayoutResult.FULL)) {
            //@TODO investigate this tricky code a little more.
            FloatPropertyValue floatPropertyValue = this.<FloatPropertyValue>getProperty(Property.FLOAT);
            if (floatPropertyValue == null || floatPropertyValue == FloatPropertyValue.NONE) {
                setProperty(Property.FORCED_PLACEMENT, true);
            } else {
                flatRenderer = childRenderers.get(0);
                childRenderers.clear();
                LayoutArea flatRendererOccupiedArea = flatRenderer.getOccupiedArea();
                applyPaddings(flatRendererOccupiedArea.getBBox(), true);
                applyBorderBox(flatRendererOccupiedArea.getBBox(), true);
                applyMargins(flatRendererOccupiedArea.getBBox(), true);
                childRenderers.add(flatRenderer);
                adjustFieldLayout();
                occupiedArea.setBBox(flatRenderer.getOccupiedArea().getBBox().clone());
            }

            return new MinMaxWidthLayoutResult(LayoutResult.NOTHING, occupiedArea, null, this, this).setMinMaxWidth(new MinMaxWidth(0, parentWidth));
        }
        if (!childRenderers.isEmpty()) {
            flatRenderer = childRenderers.get(0);
            childRenderers.clear();
            childRenderers.add(flatRenderer);
            adjustFieldLayout();
            Rectangle fBox = flatRenderer.getOccupiedArea().getBBox();
            occupiedArea.getBBox().setX(fBox.getX()).setY(fBox.getY()).setWidth(fBox.getWidth()).setHeight(fBox.getHeight());
            applyPaddings(occupiedArea.getBBox(), true);
            applyBorderBox(occupiedArea.getBBox(), true);
            applyMargins(occupiedArea.getBBox(), true);
        } else {
            LoggerFactory.getLogger(getClass()).error(LogMessageConstant.ERROR_WHILE_LAYOUT_OF_FORM_FIELD);
            occupiedArea.getBBox().setWidth(0).setHeight(0);
        }
        if (!Boolean.TRUE.equals(getPropertyAsBoolean(Property.FORCED_PLACEMENT)) && !isRendererFit(parentWidth, parentHeight)) {
            if (!minMaxWidth) {
                setProperty(Property.FORCED_PLACEMENT, true);
                occupiedArea.getBBox().setWidth(0).setHeight(0);
                return new MinMaxWidthLayoutResult(LayoutResult.NOTHING, occupiedArea, null, this, this).setMinMaxWidth(new MinMaxWidth(0, parentWidth));
            } else {
                return new MinMaxWidthLayoutResult(LayoutResult.NOTHING, occupiedArea, null, this, this).setMinMaxWidth(new MinMaxWidth(occupiedArea.getBBox().getWidth(), occupiedArea.getBBox().getWidth()));
            }
        }
        if (result.getStatus() != LayoutResult.FULL || !isRendererFit(parentWidth, parentHeight)) {
            LoggerFactory.getLogger(getClass()).warn(LogMessageConstant.INPUT_FIELD_DOES_NOT_FIT);
        }
        return new MinMaxWidthLayoutResult(LayoutResult.FULL, occupiedArea, this, null)
                .setMinMaxWidth(new MinMaxWidth(0, parentWidth, occupiedArea.getBBox().getWidth(), occupiedArea.getBBox().getWidth()));
    }
}
