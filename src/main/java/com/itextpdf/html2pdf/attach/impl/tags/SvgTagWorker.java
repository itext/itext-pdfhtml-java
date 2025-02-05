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
package com.itextpdf.html2pdf.attach.impl.tags;

import com.itextpdf.html2pdf.attach.ITagWorker;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.attach.util.AccessiblePropHelper;
import com.itextpdf.html2pdf.attach.util.ContextMappingHelper;
import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.html2pdf.util.SvgProcessingUtil;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.element.Image;
import com.itextpdf.styledxmlparser.node.IElementNode;
import com.itextpdf.styledxmlparser.node.INode;
import com.itextpdf.svg.element.SvgImage;
import com.itextpdf.svg.exceptions.SvgProcessingException;
import com.itextpdf.svg.processors.ISvgProcessorResult;
import com.itextpdf.svg.processors.impl.DefaultSvgProcessor;
import com.itextpdf.svg.processors.impl.SvgConverterProperties;
import com.itextpdf.svg.xobject.SvgImageXObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TagWorker class for the {@code svg} element.
 */
public class SvgTagWorker implements ITagWorker {
    private static final Logger LOGGER = LoggerFactory.getLogger(SvgTagWorker.class);

    private Image svgImage;
    private ISvgProcessorResult processingResult;

    /**
     * Creates a new {@link SvgTagWorker} instance.
     *
     * @param element the element
     * @param context the context
     */
    public SvgTagWorker(IElementNode element, ProcessorContext context) {
        svgImage = null;
        SvgConverterProperties props = ContextMappingHelper.mapToSvgConverterProperties(context);
        try {
            processingResult = new DefaultSvgProcessor().process((INode) element, props);
        } catch (SvgProcessingException spe) {
            LOGGER.error(Html2PdfLogMessageConstant.UNABLE_TO_PROCESS_SVG_ELEMENT, spe);
        }
        context.startProcessingInlineSvg();
    }

    @Override
    public void processEnd(IElementNode element, ProcessorContext context) {
        if (processingResult != null) {
            SvgImageXObject svgImageXObject = new SvgProcessingUtil(context.getResourceResolver())
                    .createXObjectFromProcessingResult(processingResult, context, true);
            svgImage = new SvgImage(svgImageXObject);

            AccessiblePropHelper.trySetLangAttribute(svgImage, element);
            context.endProcessingInlineSvg();
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
