package com.itextpdf.html2pdf.util;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.element.Image;
import com.itextpdf.styledxmlparser.AttributeConstants;
import com.itextpdf.styledxmlparser.IHtmlParser;
import com.itextpdf.styledxmlparser.css.util.CssUtils;
import com.itextpdf.styledxmlparser.node.INode;
import com.itextpdf.styledxmlparser.node.impl.jsoup.JsoupXmlParser;
import com.itextpdf.svg.exceptions.SvgLogMessageConstant;
import com.itextpdf.svg.exceptions.SvgProcessingException;
import com.itextpdf.svg.processors.ISvgProcessor;
import com.itextpdf.svg.processors.ISvgProcessorResult;
import com.itextpdf.svg.processors.impl.DefaultSvgProcessor;
import com.itextpdf.svg.renderers.ISvgNodeRenderer;
import com.itextpdf.svg.renderers.SvgDrawContext;
import com.itextpdf.svg.renderers.impl.PdfRootSvgNodeRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * Utility class for handling operations related to SVG
 */
public class SvgProcessingUtil {


    /**
     * The logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SvgProcessingUtil.class);

    /**
     * Create an {@code Image} layout object tied to the passed {@code PdfDocument} using the SVG processing result.
     * @param result Processing result containing the SVG information
     * @param pdfDocument pdf that shall contain the image
     * @return image layout object
     */
    public Image createImageFromProcessingResult(ISvgProcessorResult result, PdfDocument pdfDocument){
        ISvgNodeRenderer topSvgRenderer = result.getRootRenderer();
        float width = CssUtils.parseAbsoluteLength(topSvgRenderer.getAttribute(AttributeConstants.WIDTH));
        float height = CssUtils.parseAbsoluteLength(topSvgRenderer.getAttribute(AttributeConstants.HEIGHT));
        PdfFormXObject pdfForm = new PdfFormXObject(new Rectangle(0, 0, width, height));
        PdfCanvas canvas = new PdfCanvas(pdfForm, pdfDocument);

        SvgDrawContext context = new SvgDrawContext();
        context.addNamedObjects(result.getNamedObjects());
        context.pushCanvas(canvas);

        ISvgNodeRenderer root = new PdfRootSvgNodeRenderer(topSvgRenderer);

        root.draw(context);

        return new Image(pdfForm);
    }


}
