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

import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.attach.impl.layout.RunningElement;
import com.itextpdf.html2pdf.attach.impl.tags.util.ATagUtil;
import com.itextpdf.html2pdf.attach.util.LinkHelper;
import com.itextpdf.html2pdf.html.AttributeConstants;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.properties.FloatPropertyValue;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.layout.properties.Transform;
import com.itextpdf.styledxmlparser.node.IElementNode;


/**
 * TagWorker class for the {@code a} element.
 */
public class ATagWorker extends SpanTagWorker {

    /**
     * Creates a new {@link ATagWorker} instance.
     *
     * @param element the element
     * @param context the context
     */
    public ATagWorker(IElementNode element, ProcessorContext context) {
        super(element, context);
    }

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.attach.impl.tags.SpanTagWorker#processEnd(com.itextpdf.html2pdf.html.node.IElementNode, com.itextpdf.html2pdf.attach.ProcessorContext)
     */
    @Override
    public void processEnd(IElementNode element, ProcessorContext context) {
        super.processEnd(element, context);

        String url = element.getAttribute(AttributeConstants.HREF);
        if (url != null) {
            String anchorLink = element.getAttribute(AttributeConstants.HREF);
            String baseUri = context.getBaseUri();
            String modifiedUrl = ATagUtil.resolveAnchorLink(anchorLink, baseUri);
            for (int i = 0; i < getAllElements().size(); i++) {
                if (getAllElements().get(i) instanceof RunningElement) {
                    continue;
                }
                if (getAllElements().get(i) instanceof IBlockElement) {
                    Div simulatedDiv = new Div();
                    Transform cssTransform = getAllElements().get(i).<Transform>getProperty(Property.TRANSFORM);
                    if (cssTransform != null) {
                        getAllElements().get(i).deleteOwnProperty(Property.TRANSFORM);
                        simulatedDiv.setProperty(Property.TRANSFORM, cssTransform);
                    }
                    FloatPropertyValue floatPropVal = getAllElements().get(i).<FloatPropertyValue>getProperty(Property.FLOAT);
                    if (floatPropVal != null) {
                        getAllElements().get(i).deleteOwnProperty(Property.FLOAT);
                        simulatedDiv.setProperty(Property.FLOAT, floatPropVal);
                    }
                    simulatedDiv.add((IBlockElement) getAllElements().get(i));
                    String display = childrenDisplayMap.remove(getAllElements().get(i));
                    if (display != null) {
                        childrenDisplayMap.put(simulatedDiv, display);
                    }
                    getAllElements().set(i, simulatedDiv);
                }
                LinkHelper.applyLinkAnnotation(getAllElements().get(i), modifiedUrl, context, element);
            }
        }

        if (!getAllElements().isEmpty()) {
            String name = element.getAttribute(AttributeConstants.NAME);
            IPropertyContainer firstElement = getAllElements().get(0);
            firstElement.setProperty(Property.DESTINATION, name);
            firstElement.setProperty(Property.ID, name);
        }
    }
}
