/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2025 Apryse Group NV
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
package com.itextpdf.html2pdf.util;

import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import com.itextpdf.styledxmlparser.css.util.CssDimensionParsingUtils;
import com.itextpdf.styledxmlparser.resolver.resource.ResourceResolver;
import com.itextpdf.svg.converter.SvgConverter;
import com.itextpdf.svg.element.SvgImage;
import com.itextpdf.svg.processors.ISvgProcessorResult;
import com.itextpdf.svg.renderers.ISvgNodeRenderer;
import com.itextpdf.svg.renderers.SvgDrawContext;
import com.itextpdf.svg.utils.SvgCssUtils;
import com.itextpdf.svg.xobject.SvgImageXObject;

/**
 * Utility class for handling operations related to SVG
 */
public class SvgProcessingUtil {
    private final ResourceResolver resourceResolver;

    /**
     * Creates a new {@link SvgProcessingUtil} instance based on {@link ResourceResolver}
     * which is used to resolve a variety of resources.
     *
     * @param resourceResolver the resource resolver
     */
    public SvgProcessingUtil(ResourceResolver resourceResolver) {
        this.resourceResolver = resourceResolver;
    }

    /**
     * Create {@code SvgImage} layout object tied to the passed {@code PdfDocument} using the SVG processing result.
     *
     * @param result      processing result containing the SVG information
     * @param pdfDocument pdf that shall contain the image
     *
     * @return SVG image layout object
     */
    @Deprecated
    public Image createImageFromProcessingResult(ISvgProcessorResult result, PdfDocument pdfDocument) {
        SvgImageXObject xObject = (SvgImageXObject) createXObjectFromProcessingResult(result, pdfDocument);
        return new SvgImage(xObject);
    }

    /**
     * Create {@code SvgImage} layout object using the SVG processing result.
     *
     * @param result processing result containing the SVG information
     *
     * @return SVG image layout object
     */
    @Deprecated
    public Image createSvgImageFromProcessingResult(ISvgProcessorResult result) {
        return createImageFromProcessingResult(result, null);
    }

    /**
     * Create an {@link PdfFormXObject} tied to the passed {@code PdfDocument} using the SVG processing result.
     *
     * @param result      processing result containing the SVG information
     * @param pdfDocument pdf that shall contain the SVG image
     *
     * @return {@link SvgImageXObject} instance
     */
    @Deprecated
    public PdfFormXObject createXObjectFromProcessingResult(ISvgProcessorResult result, PdfDocument pdfDocument) {
        ISvgNodeRenderer topSvgRenderer = result.getRootRenderer();
        float width, height;
        float[] wh = SvgConverter.extractWidthAndHeight(topSvgRenderer);
        width = wh[0];
        height = wh[1];
        SvgImageXObject svgImageXObject =
                new SvgImageXObject(new Rectangle(0, 0, width, height), result, resourceResolver);
        if (pdfDocument != null) {
            svgImageXObject.generate(pdfDocument);
        }
        return svgImageXObject;
    }


    /**
     * Create an {@link PdfFormXObject} tied to the passed {@link ProcessorContext} using the SVG processing result.
     *
     * @param result  processing result containing the SVG information
     * @param context html2pdf processor context
     * @param generateAbsolutelySizedSvg if true and context has pdf document and svg is not relative sized, it will be immediately
     *                 generated, otherwise no generation will be performed
     *
     * @return new {@link SvgImageXObject} instance
     */
    public SvgImageXObject createXObjectFromProcessingResult(ISvgProcessorResult result, ProcessorContext context,
                                                             boolean generateAbsolutelySizedSvg) {
        float em = context.getCssContext().getCurrentFontSize();
        SvgDrawContext svgContext = new SvgDrawContext(resourceResolver, result.getFontProvider());
        svgContext.getCssContext().setRootFontSize(context.getCssContext().getRootFontSize());

        if (isSvgRelativeSized(result.getRootRenderer(), context)) {
            return new SvgImageXObject(result, svgContext, em, context.getPdfDocument());
        } else {
            Rectangle bbox = SvgCssUtils.extractWidthAndHeight(result.getRootRenderer(), em, svgContext);
            SvgImageXObject svgImageXObject = new SvgImageXObject(bbox, result, resourceResolver);
            if (context.getPdfDocument() != null && generateAbsolutelySizedSvg) {
                svgImageXObject.generate(context.getPdfDocument());
            }
            return svgImageXObject;
        }
    }

    private static boolean isSvgRelativeSized(ISvgNodeRenderer rootRenderer, ProcessorContext context) {
        float em = context.getCssContext().getCurrentFontSize();
        float rem = context.getCssContext().getRootFontSize();
        String widthStr = rootRenderer.getAttribute(CommonCssConstants.WIDTH);
        UnitValue width = CssDimensionParsingUtils.parseLengthValueToPt(widthStr, em, rem);
        String heightStr = rootRenderer.getAttribute(CommonCssConstants.HEIGHT);
        UnitValue height = CssDimensionParsingUtils.parseLengthValueToPt(heightStr, em, rem);
        return width == null || width.isPercentValue() || height == null || height.isPercentValue();
    }
}
