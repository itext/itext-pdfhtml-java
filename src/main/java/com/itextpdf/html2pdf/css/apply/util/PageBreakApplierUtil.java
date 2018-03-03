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
package com.itextpdf.html2pdf.css.apply.util;

import com.itextpdf.html2pdf.attach.ITagWorker;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.attach.impl.layout.Html2PdfProperty;
import com.itextpdf.html2pdf.attach.impl.layout.HtmlPageBreak;
import com.itextpdf.html2pdf.attach.impl.layout.HtmlPageBreakType;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.html.node.IElementNode;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.property.Property;

import java.util.Map;

/**
 * Utilities class to apply page breaks.
 */
public class PageBreakApplierUtil {

    /**
     * Creates a new {@link PageBreakApplierUtil} instance.
     */
    private PageBreakApplierUtil() {
    }

    /**
     * Applies page break properties.
     *
     * @param cssProps the CSS properties
     * @param context the processor context
     * @param element the element
     */
    public static void applyPageBreakProperties(Map<String, String> cssProps, ProcessorContext context, IPropertyContainer element) {
        applyPageBreakInside(cssProps, context, element);
        applyKeepWithNext(cssProps, context, element);
    }

    /**
     * Processes a page break "before" property.
     *
     * @param context the processor context
     * @param parentTagWorker the parent tag worker
     * @param childElement the child element
     * @param childTagWorker the child tag worker
     */
    /* Handles left, right, always cases. Avoid is handled at different time along with other css property application */
    public static void addPageBreakElementBefore(ProcessorContext context, ITagWorker parentTagWorker, IElementNode childElement, ITagWorker childTagWorker) {
        // Applies to block-level elements
        if (CssConstants.BLOCK.equals(childElement.getStyles().get(CssConstants.DISPLAY)) ||
                childElement.getStyles().get(CssConstants.DISPLAY) == null && childTagWorker.getElementResult() instanceof IBlockElement) {
            String pageBreakBeforeVal = childElement.getStyles().get(CssConstants.PAGE_BREAK_BEFORE);
            HtmlPageBreak breakBefore = createHtmlPageBreak(pageBreakBeforeVal);
            if (breakBefore != null) {
                parentTagWorker.processTagChild(new HtmlPageBreakWorker(breakBefore), context);
            }
        }
    }

    /**
     * Processes a page break "after" property.
     *
     * @param context the processor context
     * @param parentTagWorker the parent tag worker
     * @param childElement the child element
     * @param childTagWorker the child tag worker
     */
    /* Handles left, right, always cases. Avoid is handled at different time along with other css property application */
    public static void addPageBreakElementAfter(ProcessorContext context, ITagWorker parentTagWorker, IElementNode childElement, ITagWorker childTagWorker) {
        // Applies to block-level elements
        if (CssConstants.BLOCK.equals(childElement.getStyles().get(CssConstants.DISPLAY)) ||
                childElement.getStyles().get(CssConstants.DISPLAY) == null && childTagWorker.getElementResult() instanceof IBlockElement) {
            String pageBreakAfterVal = childElement.getStyles().get(CssConstants.PAGE_BREAK_AFTER);
            HtmlPageBreak breakAfter = createHtmlPageBreak(pageBreakAfterVal);
            if (breakAfter != null) {
                parentTagWorker.processTagChild(new HtmlPageBreakWorker(breakAfter), context);
            }
        }
    }

    /**
     * Creates an {@link HtmlPageBreak} instance.
     *
     * @param pageBreakVal the page break value
     * @return the {@link HtmlPageBreak} instance
     */
    private static HtmlPageBreak createHtmlPageBreak(String pageBreakVal) {
        HtmlPageBreak pageBreak = null;
        if (CssConstants.ALWAYS.equals(pageBreakVal)) {
            pageBreak = new HtmlPageBreak(HtmlPageBreakType.ALWAYS);
        } else if (CssConstants.LEFT.equals(pageBreakVal)) {
            pageBreak = new HtmlPageBreak(HtmlPageBreakType.LEFT);
        } else if (CssConstants.RIGHT.equals(pageBreakVal)) {
            pageBreak = new HtmlPageBreak(HtmlPageBreakType.RIGHT);
        }
        return pageBreak;
    }

    /**
     * Applies a keep with next property to an element.
     *
     * @param cssProps the CSS properties
     * @param context the processor context
     * @param element the element
     */
    private static void applyKeepWithNext(Map<String, String> cssProps, ProcessorContext context, IPropertyContainer element) {
        String pageBreakBefore = cssProps.get(CssConstants.PAGE_BREAK_BEFORE);
        String pageBreakAfter = cssProps.get(CssConstants.PAGE_BREAK_AFTER);
        if (CssConstants.AVOID.equals(pageBreakAfter)) {
            element.setProperty(Property.KEEP_WITH_NEXT, true);
        }
        if (CssConstants.AVOID.equals(pageBreakBefore)) {
            element.setProperty(Html2PdfProperty.KEEP_WITH_PREVIOUS, true);
        }
    }

    /**
     * Applies a page break inside property.
     *
     * @param cssProps the CSS properties
     * @param context the processor context
     * @param element the element
     */
    private static void applyPageBreakInside(Map<String, String> cssProps, ProcessorContext context, IPropertyContainer element) {
        // TODO A potential page break location is typically under the influence of the parent element's 'page-break-inside' property,
        // the 'page-break-after' property of the preceding element, and the 'page-break-before' property of the following element.
        // When these properties have values other than 'auto', the values 'always', 'left', and 'right' take precedence over 'avoid'.
        String pageBreakInsideVal = cssProps.get(CssConstants.PAGE_BREAK_INSIDE);
        if (CssConstants.AVOID.equals(pageBreakInsideVal)) {
            element.setProperty(Property.KEEP_TOGETHER, true);
        }
    }

    /**
     * A {@code TagWorker} class for HTML page breaks.
     */
    private static class HtmlPageBreakWorker implements ITagWorker {

        /** The {@link HtmlPageBreak} instance. */
        private HtmlPageBreak pageBreak;

        /**
         * Creates a new {@link HtmlPageBreakWorker} instance.
         *
         * @param pageBreak the page break
         */
        HtmlPageBreakWorker(HtmlPageBreak pageBreak) {
            this.pageBreak = pageBreak;
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
            throw new IllegalStateException();
        }

        /* (non-Javadoc)
         * @see com.itextpdf.html2pdf.attach.ITagWorker#processTagChild(com.itextpdf.html2pdf.attach.ITagWorker, com.itextpdf.html2pdf.attach.ProcessorContext)
         */
        @Override
        public boolean processTagChild(ITagWorker childTagWorker, ProcessorContext context) {
            throw new IllegalStateException();
        }

        /* (non-Javadoc)
         * @see com.itextpdf.html2pdf.attach.ITagWorker#getElementResult()
         */
        @Override
        public IPropertyContainer getElementResult() {
            return pageBreak;
        }
    }

}
