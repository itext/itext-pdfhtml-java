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
package com.itextpdf.html2pdf.attach.impl.tags;

import com.itextpdf.html2pdf.attach.ITagWorker;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.html.AttributeConstants;
import com.itextpdf.html2pdf.html.node.IElementNode;
import com.itextpdf.kernel.pdf.xobject.PdfImageXObject;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.element.Image;

/**
 * TagWorker class for the {@code img} element.
 */
public class ImgTagWorker implements ITagWorker {

    /** The image. */
    private HtmlImage image;

    /** The display value. */
    private String display;

    /**
     * Creates a new {@link ImgTagWorker} instance.
     *
     * @param element the element
     * @param context the context
     */
    public ImgTagWorker(IElementNode element, ProcessorContext context) {
        PdfImageXObject imageXObject = context.getResourceResolver().retrieveImage(element.getAttribute(AttributeConstants.SRC));
        if (imageXObject != null) {
            image = new HtmlImage(imageXObject);
            String altText = element.getAttribute(AttributeConstants.ALT);
            if (altText != null) {
                image.setAltText(altText);
            }
        }
        display = element.getStyles() != null ? element.getStyles().get(CssConstants.DISPLAY) : null;
        // TODO this is a workaround for now to that image is not added as inline
        if (element.getStyles() != null && CssConstants.ABSOLUTE.equals(element.getStyles().get(CssConstants.POSITION))) {
            display = CssConstants.BLOCK;
        }
    }

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.attach.ITagWorker#processEnd(com.itextpdf.html2pdf.html.node.IElementNode, com.itextpdf.html2pdf.attach.ProcessorContext)
     */
    @Override
    public void processEnd(IElementNode element, ProcessorContext context) {
    }

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.attach.ITagWorker#processContent(java.lang.String, com.itextpdf.html2pdf.attach.ProcessorContext)
     */
    @Override
    public boolean processContent(String content, ProcessorContext context) {
        return false;
    }

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.attach.ITagWorker#processTagChild(com.itextpdf.html2pdf.attach.ITagWorker, com.itextpdf.html2pdf.attach.ProcessorContext)
     */
    @Override
    public boolean processTagChild(ITagWorker childTagWorker, ProcessorContext context) {
        return false;
    }

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.attach.ITagWorker#getElementResult()
     */
    @Override
    public IPropertyContainer getElementResult() {
        return image;
    }

    /**
     * Gets the display value.
     *
     * @return the display value
     */
    String getDisplay() {
        return display;
    }

    /**
     * Implementation of the Image class when used in the context of HTML to PDF conversion.
     */
    private class HtmlImage extends Image {

        /**
         * In iText, we use user unit for the image sizes (and by default
         * one user unit = one point), whereas images are usually measured
         * in pixels.
         */
        private double pxToPt = 0.75;

        /**
     *   * Creates a new {@link HtmlImage} instance.
         *
         * @param xObject an Image XObject
         */
        public HtmlImage(PdfImageXObject xObject) {
            super(xObject);
        }

        /* (non-Javadoc)
         * @see com.itextpdf.layout.element.Image#getImageWidth()
         */
        @Override
        public float getImageWidth() {
            return (float) (xObject.getWidth() * pxToPt);
        }

        /* (non-Javadoc)
         * @see com.itextpdf.layout.element.Image#getImageHeight()
         */
        @Override
        public float getImageHeight() {
            return (float) (xObject.getHeight() * pxToPt);
        }

        /**
         * Sets the alt text for the image.
         *
         * @param altText the new alt text
         */
        void setAltText(String altText) {
            getAccessibilityProperties().setAlternateDescription(altText);
        }
    }
}
