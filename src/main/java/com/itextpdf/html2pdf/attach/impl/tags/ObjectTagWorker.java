package com.itextpdf.html2pdf.attach.impl.tags;

import com.itextpdf.html2pdf.LogMessageConstant;
import com.itextpdf.html2pdf.attach.ITagWorker;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.html.AttributeConstants;
import com.itextpdf.html2pdf.html.node.IElementNode;
import com.itextpdf.html2pdf.util.SvgProcessingUtil;
import com.itextpdf.io.util.MessageFormatUtil;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.element.Image;
import com.itextpdf.svg.converter.SvgConverter;
import com.itextpdf.svg.exceptions.SvgProcessingException;
import com.itextpdf.svg.processors.ISvgProcessorResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * TagWorker class for the {@code object} element.
 */
public class ObjectTagWorker implements ITagWorker {

    /**
     * The logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ObjectTagWorker.class);

    /**
     * The Svg as image.
     */
    private Image image;

    private ISvgProcessorResult res;

    private SvgProcessingUtil processUtil;

    /**
     * Creates a new {@link ImgTagWorker} instance.
     *
     * @param element the element
     * @param context the context
     */
    public ObjectTagWorker(IElementNode element, ProcessorContext context) {
        image = null;
        res = null;
        processUtil = new SvgProcessingUtil();
        //Retrieve object type
        String type = element.getAttribute(AttributeConstants.TYPE);
        if (isSvgImage(type)) {
            //Use resource resolver to retrieve the URL
            InputStream svgStream = context.getResourceResolver().retrieveResourceAsInputStream(element.getAttribute(AttributeConstants.DATA));
            if(svgStream != null) {
                try {
                    res = SvgConverter.parseAndProcess(svgStream);
                } catch (SvgProcessingException spe) {
                    LOGGER.error(spe.getMessage());
                } catch(IOException ie){
                    LOGGER.error(MessageFormatUtil.format(LogMessageConstant.UNABLE_TO_RETRIEVE_STREAM_WITH_GIVEN_BASE_URI,context.getBaseUri(),element.getAttribute(AttributeConstants.DATA)));
                }
            }
        }
    }
    @Override
    public void processEnd(IElementNode element, ProcessorContext context) {
        if (context.getPdfDocument() != null) {
            PdfDocument document = context.getPdfDocument();
            //Create Image object
            if (res != null) {
                image = processUtil.createImageFromProcessingResult(res,document);
            }

        } else {
            LOGGER.error(LogMessageConstant.PDF_DOCUMENT_NOT_PRESENT);
        }
    }

    @Override
    public boolean processContent(String content, ProcessorContext context) {
        return false;
    }

    @Override
    public boolean processTagChild(ITagWorker childTagWorker, ProcessorContext context) {
        return false;
    }

    @Override
    public IPropertyContainer getElementResult() {
        return image;
    }

    private boolean isSvgImage(String typeAttribute) {
        return typeAttribute.equals(AttributeConstants.ObjectTypes.SVGIMAGE);
    }
}
