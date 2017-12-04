/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2017 iText Group NV
    Authors: Bruno Lowagie, Paulo Soares, et al.

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
package com.itextpdf.html2pdf.attach.util;

import com.itextpdf.html2pdf.attach.ITagWorker;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.html.AttributeConstants;
import com.itextpdf.html2pdf.html.node.IElementNode;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.action.PdfAction;
import com.itextpdf.kernel.pdf.annot.PdfAnnotation;
import com.itextpdf.kernel.pdf.annot.PdfLinkAnnotation;
import com.itextpdf.kernel.pdf.tagging.StandardRoles;
import com.itextpdf.layout.tagging.IAccessibleElement;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.element.ILeafElement;
import com.itextpdf.layout.property.Property;

/**
 * Helper class for links.
 */
public class LinkHelper {

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
     */
    public static void applyLinkAnnotation(IPropertyContainer container, String url) {
        if (container != null) {
            PdfLinkAnnotation linkAnnotation;
            if (url.startsWith("#")) {
                String name = url.substring(1);
                linkAnnotation = (PdfLinkAnnotation) new PdfLinkAnnotation(new Rectangle(0, 0, 0, 0)).setAction(PdfAction.createGoTo(name)).setFlags(PdfAnnotation.PRINT);
            } else {
                linkAnnotation = (PdfLinkAnnotation) new PdfLinkAnnotation(new Rectangle(0, 0, 0, 0)).setAction(PdfAction.createURI(url)).setFlags(PdfAnnotation.PRINT);
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
        if (element.getAttribute(AttributeConstants.ID) == null)
            return;

        if (tagWorker == null)
            return;

        IPropertyContainer propertyContainer = tagWorker.getElementResult();
        if (propertyContainer == null)
            return;

        // get id
        String id = element.getAttribute(AttributeConstants.ID);

        // set property
        if (context.getLinkContext().isUsedLinkDestination(id)) {
            propertyContainer.setProperty(Property.DESTINATION, id);
        }
    }
}
