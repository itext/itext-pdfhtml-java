/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2019 iText Group NV
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
package com.itextpdf.html2pdf.attach.impl.tags;

import com.itextpdf.html2pdf.LogMessageConstant;
import com.itextpdf.html2pdf.attach.ITagWorker;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.html.AttributeConstants;
import com.itextpdf.html2pdf.util.SvgProcessingUtil;
import com.itextpdf.io.util.MessageFormatUtil;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.element.Image;
import com.itextpdf.styledxmlparser.node.IElementNode;
import com.itextpdf.svg.converter.SvgConverter;
import com.itextpdf.svg.exceptions.SvgProcessingException;
import com.itextpdf.svg.processors.ISvgProcessorResult;
import com.itextpdf.svg.processors.impl.SvgConverterProperties;
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
            try (InputStream svgStream = context.getResourceResolver().retrieveResourceAsInputStream(element.getAttribute(AttributeConstants.DATA))) {
                if (svgStream != null) {
                    try {
                        SvgConverterProperties svgConverterProperties = new SvgConverterProperties();
                        svgConverterProperties.setBaseUri(context.getBaseUri())
                                .setFontProvider(context.getFontProvider())
                                .setMediaDeviceDescription(context.getDeviceDescription());
                        res = SvgConverter.parseAndProcess(svgStream, svgConverterProperties);
                    } catch (SvgProcessingException spe) {
                        LOGGER.error(spe.getMessage());
                    }
                }
            } catch (IOException ie) {
                LOGGER.error(MessageFormatUtil.format(LogMessageConstant.UNABLE_TO_RETRIEVE_STREAM_WITH_GIVEN_BASE_URI, context.getBaseUri(), element.getAttribute(AttributeConstants.DATA)));
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
