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
package com.itextpdf.html2pdf.attach.util;

import com.itextpdf.commons.datastructures.Tuple2;
import com.itextpdf.commons.utils.MessageFormatUtil;
import com.itextpdf.html2pdf.attach.ITagWorker;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.attach.impl.tags.SpanTagWorker;
import com.itextpdf.html2pdf.html.AttributeConstants;
import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.action.PdfAction;
import com.itextpdf.kernel.pdf.annot.PdfAnnotation;
import com.itextpdf.kernel.pdf.annot.PdfLinkAnnotation;
import com.itextpdf.kernel.pdf.tagging.StandardRoles;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.element.ILeafElement;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.layout.tagging.IAccessibleElement;
import com.itextpdf.styledxmlparser.node.IElementNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Helper class for links.
 */
public class LinkHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(LinkHelper.class);

    /**
     * Creates a new {@link LinkHelper} class.
     */
    private LinkHelper() {
    }

    /**
     * Applies a link annotation.
     *
     * @param container the containing object
     * @param url       the destination
     * @param context   the processor context
     * @param element   the element node
     */
    public static void applyLinkAnnotation(IPropertyContainer container, String url, ProcessorContext context,
                                           IElementNode element) {
        if (container != null) {
            PdfLinkAnnotation linkAnnotation;
            if (url.startsWith("#")) {
                String id = url.substring(1);
                linkAnnotation = context.getLinkContext().getLinkAnnotation(id);
                if (linkAnnotation == null) {
                    linkAnnotation = (PdfLinkAnnotation)
                            new PdfLinkAnnotation(new Rectangle(0, 0, 0, 0))
                                    .setAction(PdfAction.createGoTo(id))
                                    .setFlags(PdfAnnotation.PRINT);
                    context.getLinkContext().addLinkAnnotation(id, linkAnnotation);
                }
            } else {
                linkAnnotation = (PdfLinkAnnotation)
                        new PdfLinkAnnotation(new Rectangle(0, 0, 0, 0))
                                .setAction(PdfAction.createURI(url))
                                .setFlags(PdfAnnotation.PRINT);
            }
            if (container instanceof IAccessibleElement) {
                context.getDIContainer()
                        .getInstance(AlternateDescriptionResolver.class)
                        .resolve((IAccessibleElement) container, element);
                String altDescription = ((IAccessibleElement) container)
                        .getAccessibilityProperties()
                        .getAlternateDescription();
                if (altDescription != null) {
                    linkAnnotation.setContents(altDescription);
                }

            }


            linkAnnotation.setBorder(new PdfArray(new float[]{0, 0, 0}));
            container.setProperty(Property.LINK_ANNOTATION, linkAnnotation);
            if (container instanceof ILeafElement && container instanceof IAccessibleElement) {
                ((IAccessibleElement) container).getAccessibilityProperties().setRole(StandardRoles.LINK);
            }
        }
    }

    /**
     * Creates a destination
     *
     * @param tagWorker the tagworker that is building the (iText) element
     * @param element   the (HTML) element being converted
     * @param context   the Processor context
     */
    public static void createDestination(ITagWorker tagWorker, IElementNode element, ProcessorContext context) {
        String id = element.getAttribute(AttributeConstants.ID);
        if (id == null)
            return;

        final IPropertyContainer propertyContainer = getPropertyContainer(tagWorker);

        if (context.getLinkContext().isUsedLinkDestination(id)) {
            if (propertyContainer == null) {
                String tagWorkerClassName = tagWorker != null ? tagWorker.getClass().getName() : "null";
                LOGGER.warn(MessageFormatUtil.format(Html2PdfLogMessageConstant.ANCHOR_LINK_NOT_HANDLED,
                        element.name(), id, tagWorkerClassName));
                return;
            }

            PdfLinkAnnotation linkAnnotation = context.getLinkContext().getLinkAnnotation(id);
            if (linkAnnotation == null) {
                linkAnnotation =
                        (PdfLinkAnnotation) new PdfLinkAnnotation(new Rectangle(0, 0, 0, 0)).setAction(PdfAction.createGoTo(id)).setFlags(PdfAnnotation.PRINT);
                context.getLinkContext().addLinkAnnotation(id, linkAnnotation);
            }

            propertyContainer.setProperty(Property.DESTINATION, new Tuple2<String, PdfDictionary>(id,
                    linkAnnotation.getAction()));
        }
        if (propertyContainer != null) {
            propertyContainer.setProperty(Property.ID, id);
        }
    }

    private static IPropertyContainer getPropertyContainer(ITagWorker tagWorker) {
        if (tagWorker != null) {
            if (tagWorker instanceof SpanTagWorker) {
                List<IPropertyContainer> spanElements = ((SpanTagWorker) tagWorker).getAllElements();
                if (!spanElements.isEmpty()) {
                    return spanElements.get(0);
                }
            } else {
                return tagWorker.getElementResult();
            }
        }
        return null;
    }
}
