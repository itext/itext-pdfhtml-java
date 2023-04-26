/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2023 Apryse Group NV
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
package com.itextpdf.html2pdf.utils;

import com.itextpdf.kernel.geom.AffineTransform;
import com.itextpdf.kernel.geom.Matrix;
import com.itextpdf.kernel.geom.NoninvertibleTransformException;
import com.itextpdf.kernel.geom.Point;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.canvas.parser.EventType;
import com.itextpdf.kernel.pdf.canvas.parser.data.IEventData;
import com.itextpdf.kernel.pdf.canvas.parser.data.ImageRenderInfo;
import com.itextpdf.kernel.pdf.canvas.parser.listener.IEventListener;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class ImageSizeMeasuringListener implements IEventListener {

    Rectangle cropbox;
    public Rectangle bbox;
    int page;

    public ImageSizeMeasuringListener(int page) {

        if (cropbox == null) {
            cropbox = new Rectangle(Float.MIN_VALUE, Float.MIN_VALUE, Float.MAX_VALUE, Float.MAX_VALUE);
        }
        this.page = page;
    }

    public void eventOccurred(IEventData data, EventType type) {
        switch (type) {
            case RENDER_IMAGE:
                ImageRenderInfo renderInfo = (ImageRenderInfo) data;
                final Matrix imageCtm = renderInfo.getImageCtm();
                bbox = calcImageRect(imageCtm);
            default:
                break;
        }
    }

    public Set<EventType> getSupportedEvents() {
        return null;
    }

    private double getWidth(Matrix m) {
        return Math.sqrt(Math.pow(m.get(Matrix.I11), 2) + Math.pow(m.get(Matrix.I21), 2));
    }

    private double getHeight(Matrix m) {
        return Math.sqrt(Math.pow(m.get(Matrix.I12), 2) + Math.pow(m.get(Matrix.I22), 2));
    }

    private Rectangle calcImageRect(Matrix ctm) {
        if (ctm == null) {
            return null;
        }

        Point[] points = transformPoints(ctm, false,
                new Point(0, 0), new Point(0, 1),
                new Point(1, 0), new Point(1, 1));

        return getAsRectangle(points[0], points[1], points[2], points[3]);
    }

    private Rectangle getAsRectangle(Point p1, Point p2, Point p3, Point p4) {
        List<Double> xs = Arrays.asList(p1.getX(), p2.getX(), p3.getX(), p4.getX());
        List<Double> ys = Arrays.asList(p1.getY(), p2.getY(), p3.getY(), p4.getY());

        double left = Collections.min(xs);
        double bottom = Collections.min(ys);
        double right = Collections.max(xs);
        double top = Collections.max(ys);

        return new Rectangle((float) left, (float) bottom, (float) right, (float) top);
    }

    private Point[] transformPoints(Matrix transformationMatrix, boolean inverse, Point... points) {
        AffineTransform t = new AffineTransform(transformationMatrix.get(Matrix.I11),
                transformationMatrix.get(Matrix.I12),
                transformationMatrix.get(Matrix.I21), transformationMatrix.get(Matrix.I22),
                transformationMatrix.get(Matrix.I31), transformationMatrix.get(Matrix.I32));
        Point[] transformed = new Point[points.length];

        if (inverse) {
            try {
                t = t.createInverse();
            } catch (NoninvertibleTransformException e) {
                throw new RuntimeException("Exception during effective image rectangle calculation", e);
            }
        }

        t.transform(points, 0, transformed, 0, points.length);

        return transformed;
    }
}
