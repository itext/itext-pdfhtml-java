/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2024 Apryse Group NV
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

import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.commons.utils.MessageFormatUtil;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.tagging.PdfStructureAttributes;
import com.itextpdf.kernel.pdf.tagging.StandardRoles;
import com.itextpdf.kernel.pdf.tagutils.AccessibilityProperties;
import com.itextpdf.layout.tagging.IAccessibleElement;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.html2pdf.html.AttributeConstants;
import com.itextpdf.styledxmlparser.node.IElementNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThTagWorker extends TdTagWorker {
    /**
     * Creates a new {@link ThTagWorker} instance.
     *
     * @param element the element
     * @param context the context
     */
    public ThTagWorker(IElementNode element, ProcessorContext context) {
        super(element, context);
    }

    @Override
    public void processEnd(IElementNode element, ProcessorContext context) {
        super.processEnd(element, context);
        IPropertyContainer elementResult = super.getElementResult();
        if (elementResult instanceof IAccessibleElement) {
            ((IAccessibleElement) elementResult).getAccessibilityProperties().setRole(StandardRoles.TH);
            if (context.getPdfDocument() == null || context.getPdfDocument().isTagged()) {
                String scope = element.getAttribute(AttributeConstants.SCOPE);
                AccessibilityProperties properties = ((IAccessibleElement) elementResult).getAccessibilityProperties();
                PdfDictionary attributes = new PdfDictionary();
                attributes.put(PdfName.O, PdfName.Table);
                if (scope != null && (AttributeConstants.ROW.equalsIgnoreCase(scope) || AttributeConstants.ROWGROUP.equalsIgnoreCase(scope))) {
                    attributes.put(PdfName.Scope, PdfName.Row);
                    properties.addAttributes(new PdfStructureAttributes(attributes));
                } else if (scope != null && (AttributeConstants.COL.equalsIgnoreCase(scope) || AttributeConstants.COLGROUP.equalsIgnoreCase(scope))) {
                    attributes.put(PdfName.Scope, PdfName.Column);
                    properties.addAttributes(new PdfStructureAttributes(attributes));
                } else {
                    Logger logger = LoggerFactory.getLogger(ThTagWorker.class);
                    logger.warn(MessageFormatUtil.format(
                            Html2PdfLogMessageConstant.NOT_SUPPORTED_TH_SCOPE_TYPE, scope));
                }

            }

        }
    }

}
