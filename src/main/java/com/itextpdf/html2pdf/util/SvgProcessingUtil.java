/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2021 iText Group NV
    Authors: iText Software.

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
package com.itextpdf.html2pdf.util;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.element.Image;
import com.itextpdf.styledxmlparser.resolver.resource.ResourceResolver;
import com.itextpdf.svg.converter.SvgConverter;
import com.itextpdf.svg.processors.ISvgProcessorResult;
import com.itextpdf.svg.processors.impl.SvgProcessorResult;
import com.itextpdf.svg.renderers.ISvgNodeRenderer;
import com.itextpdf.svg.renderers.SvgDrawContext;
import com.itextpdf.svg.renderers.impl.PdfRootSvgNodeRenderer;
import com.itextpdf.svg.utils.SvgCssUtils;

/**
 * Utility class for handling operations related to SVG
 */
public class SvgProcessingUtil {
    private ResourceResolver resourceResolver;

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
     * Create an {@code Image} layout object tied to the passed {@code PdfDocument} using the SVG processing result.
     * @param result Processing result containing the SVG information
     * @param pdfDocument pdf that shall contain the image
     * @return image layout object
     */
    public Image createImageFromProcessingResult(ISvgProcessorResult result, PdfDocument pdfDocument) {
        PdfFormXObject xObject = createXObjectFromProcessingResult(result, pdfDocument);
        return new Image(xObject);
    }

    /**
     * Create an {@link PdfFormXObject} tied to the passed {@code PdfDocument} using the SVG processing result.
     * @param result Processing result containing the SVG information
     * @param pdfDocument pdf that shall contain the image
     * @return PdfFormXObject instance
     */
    public PdfFormXObject createXObjectFromProcessingResult(ISvgProcessorResult result, PdfDocument pdfDocument){
        ISvgNodeRenderer topSvgRenderer = result.getRootRenderer();
        float width, height;
        float[] wh = SvgConverter.extractWidthAndHeight(topSvgRenderer);
        width = wh[0];
        height = wh[1];
        PdfFormXObject pdfForm = new PdfFormXObject(new Rectangle(0, 0, width, height));
        PdfCanvas canvas = new PdfCanvas(pdfForm, pdfDocument);

        ResourceResolver tempResolver = new ResourceResolver(null, resourceResolver.getRetriever());
        // TODO DEVSIX-4107 pass the resourceResolver variable (not tempResolver variable) to the
        //  SvgDrawContext constructor so that the SVG inside the SVG is processed.
        SvgDrawContext context = new SvgDrawContext(tempResolver, result.getFontProvider());
        if (result instanceof SvgProcessorResult) {
            context.setCssContext(((SvgProcessorResult) result).getContext().getCssContext());
        }
        context.addNamedObjects(result.getNamedObjects());
        context.pushCanvas(canvas);

        ISvgNodeRenderer root = new PdfRootSvgNodeRenderer(topSvgRenderer);

        root.draw(context);

        return pdfForm;
    }
}
