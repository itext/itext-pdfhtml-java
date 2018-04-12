package com.itextpdf.html2pdf.attach.impl.layout;

import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.property.Property;
import com.itextpdf.layout.property.UnitValue;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is used to store styles of {@code <html>} and {@code <body>} tags,
 * to simplify their application on the document as an {@link Html2PdfProperty}
 * and to simplify their processing on the layout level.
 * This class is primarily meant for internal usage.
 */
public class BodyHtmlStylesContainer implements IPropertyContainer {

    /**
     * {@inheritDoc}
     */
    protected Map<Integer, Object> properties = new HashMap<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public void setProperty(int property, Object value) {
        properties.put(property, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasProperty(int property) {
        return hasOwnProperty(property);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasOwnProperty(int property) {
        return properties.containsKey(property);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteOwnProperty(int property) {
        properties.remove(property);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T1> T1 getProperty(int property) {
        return (T1) this.<T1>getOwnProperty(property);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T1> T1 getOwnProperty(int property) {
        return (T1) properties.<T1>get(property);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T1> T1 getDefaultProperty(int property) {
        switch (property) {
            case Property.MARGIN_TOP:
            case Property.MARGIN_RIGHT:
            case Property.MARGIN_BOTTOM:
            case Property.MARGIN_LEFT:
            case Property.PADDING_TOP:
            case Property.PADDING_RIGHT:
            case Property.PADDING_BOTTOM:
            case Property.PADDING_LEFT:
                return (T1) (Object) UnitValue.createPointValue(0f);
            default:
                return (T1) (Object) null;
        }
    }

    /**
     * This method is needed to check if we need to draw a simulated {@link com.itextpdf.layout.element.Div} element,
     * i.e. to perform any drawing at all.
     *
     * @return true if there is at least one Border present in the container
     */
    public boolean hasBordersToDraw() {
        Border[] borders = getBodyHtmlBorders();
        for (int i = 0; i < 4; i++) {
            if (borders[i] != null && borders[i].getWidth() != 0)
                return true;
        }
        return false;
    }

    /**
     * This method is needed to check if there are styles applied on the current element.
     *
     * @return true if in the container there are present styles which impact the layout
     */
    public boolean hasStylesToApply() {
        float[] totalWidth = getTotalWidth();
        for (int i = 0; i < 4; i++)
            if (totalWidth[i] > 0)
                return true;
        return false;
    }

    /**
     * This method calculates the total widths of TOP-, RIGHT-, BOTTOM- and LEFT- side styles,
     * each combined of widths of applied margins, borders and paddings.
     *
     * @return a float array containing applied TOP-, RIGHT-, BOTTOM- and LEFT- side widths of styles respectively
     */
    public float[] getTotalWidth() {
        Border[] borders = getBodyHtmlBorders();
        float[] margins = getBodyHtmlMarginsOrPaddings(true);
        float[] paddings = getBodyHtmlMarginsOrPaddings(false);
        float[] width = new float[4];
        for (int i = 0; i < 4; i++) {
            width[i] += margins[i] + paddings[i];
            if (borders[i] != null)
                width[i] += borders[i].getWidth();
        }
        return width;
    }

    private float[] getBodyHtmlMarginsOrPaddings(boolean margin) {
        int[] property = new int[4];
        if (margin) {
            property[0] = Property.MARGIN_TOP;
            property[1] = Property.MARGIN_RIGHT;
            property[2] = Property.MARGIN_BOTTOM;
            property[3] = Property.MARGIN_LEFT;
        } else {
            property[0] = Property.PADDING_TOP;
            property[1] = Property.PADDING_RIGHT;
            property[2] = Property.PADDING_BOTTOM;
            property[3] = Property.PADDING_LEFT;
        }
        float widths[] = new float[4];
        UnitValue[] widthsProperties = new UnitValue[4];
        for (int i = 0; i < 4; i++) {
            widthsProperties[i] = this.<UnitValue>getOwnProperty(property[i]);
            if (widthsProperties[i] != null && widthsProperties[i].isPointValue())
                widths[i] = ((UnitValue) widthsProperties[i]).getValue();
        }
        return widths;
    }

    private Border[] getBodyHtmlBorders() {
        Border[] border = new Border[4];
        border[0] = this.<Border>getOwnProperty(Property.BORDER_TOP);
        border[1] = this.<Border>getOwnProperty(Property.BORDER_RIGHT);
        border[2] = this.<Border>getOwnProperty(Property.BORDER_BOTTOM);
        border[3] = this.<Border>getOwnProperty(Property.BORDER_LEFT);
        return border;
    }
}