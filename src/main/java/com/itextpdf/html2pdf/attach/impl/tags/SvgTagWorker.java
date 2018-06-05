package com.itextpdf.html2pdf.attach.impl.tags;

import com.itextpdf.html2pdf.LogMessageConstant;
import com.itextpdf.html2pdf.attach.ITagWorker;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.util.SvgProcessingUtil;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.element.Image;
import com.itextpdf.styledxmlparser.node.IElementNode;
import com.itextpdf.svg.exceptions.SvgProcessingException;
import com.itextpdf.svg.processors.ISvgProcessor;
import com.itextpdf.svg.processors.ISvgProcessorResult;
import com.itextpdf.svg.processors.impl.DefaultSvgProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TagWorker class for the {@code svg} element.
 */
public class SvgTagWorker implements ITagWorker{
    Image svgImage;
    ISvgProcessorResult processingResult;

    /**
     * The logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ObjectTagWorker.class);

    /**
     * Creates a new {@link SvgTagWorker} instance.
     *
     * @param element the element
     * @param context the context
     */
    public SvgTagWorker(IElementNode element, ProcessorContext context) {
        svgImage = null;
        try{
            ISvgProcessor proc = new DefaultSvgProcessor();
            //TODO(blocked by DEVSIX-1955, RND-982): uncomment and register in the mapping
            //processingResult = proc.process((INode) element);
            processingResult = null;
        }catch(SvgProcessingException pe){
            LOGGER.error(LogMessageConstant.UNABLE_TO_PROCESS_IMAGE_AS_SVG,pe);
        }
    }

    @Override
    public void processEnd(IElementNode element, ProcessorContext context) {
        if(context.getPdfDocument() != null && processingResult != null){
            SvgProcessingUtil util = new SvgProcessingUtil();
            svgImage = util.createImageFromProcessingResult(processingResult,context.getPdfDocument());
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
        return svgImage;
    }
}
