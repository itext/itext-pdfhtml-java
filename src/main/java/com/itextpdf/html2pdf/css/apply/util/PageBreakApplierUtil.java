/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2023 Apryse Group NV
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
package com.itextpdf.html2pdf.css.apply.util;

import com.itextpdf.html2pdf.attach.ITagWorker;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.attach.impl.layout.Html2PdfProperty;
import com.itextpdf.html2pdf.attach.impl.layout.HtmlPageBreak;
import com.itextpdf.html2pdf.attach.impl.layout.HtmlPageBreakType;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.styledxmlparser.node.IElementNode;

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
        if (isEligibleForBreakBeforeAfter(parentTagWorker, childElement, childTagWorker)) {
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
        if (isEligibleForBreakBeforeAfter(parentTagWorker, childElement, childTagWorker)) {
            String pageBreakAfterVal = childElement.getStyles().get(CssConstants.PAGE_BREAK_AFTER);
            HtmlPageBreak breakAfter = createHtmlPageBreak(pageBreakAfterVal);
            if (breakAfter != null) {
                parentTagWorker.processTagChild(new HtmlPageBreakWorker(breakAfter), context);
            }
        }
    }

    private static boolean isEligibleForBreakBeforeAfter(ITagWorker parentTagWorker, IElementNode childElement, ITagWorker childTagWorker) {
        // Applies to block-level elements as per spec
        String childElementDisplay = childElement.getStyles().get(CssConstants.DISPLAY);
        return CssConstants.BLOCK.equals(childElementDisplay) ||
                CssConstants.TABLE.equals(childElementDisplay) ||
                childElementDisplay == null && childTagWorker.getElementResult() instanceof IBlockElement;
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
        // TODO DEVSIX-4521 A page break location is under the influence of the parent 'page-break-inside' property,
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
