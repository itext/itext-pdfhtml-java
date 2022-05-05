/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2022 iText Group NV
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

import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.css.apply.util.enums.CssCounterManagerOperation;
import com.itextpdf.html2pdf.css.resolve.CssContext;
import com.itextpdf.html2pdf.css.resolve.func.counter.CssCounterManager;
import com.itextpdf.styledxmlparser.css.util.CssDimensionParsingUtils;
import com.itextpdf.styledxmlparser.node.IElementNode;

import java.util.Map;

/**
 * Utilities class to process counters.
 */
public class CounterProcessorUtil {

    /**
     * Processes counters.
     *
     * @param cssProps the CSS properties
     * @param context  the processor context
     */
    public static void processCounters(Map<String, String> cssProps, CssContext context) {
        String counterReset = cssProps.get(CssConstants.COUNTER_RESET);
        processCounter(counterReset, context, CssCounterManagerOperation.RESET);

        String counterIncrement = cssProps.get(CssConstants.COUNTER_INCREMENT);
        processCounter(counterIncrement, context, CssCounterManagerOperation.INCREMENT);
    }

    /**
     * Starts processing counters. Pushes current counter values to counters if necessary.
     * Usually it is expected that this method should be called before processing children of the element.
     *
     * @param context the processor context
     * @param element the element which counters shall be processed
     */
    public static void startProcessingCounters(CssContext context, IElementNode element) {
        final CssCounterManager counterManager = context.getCounterManager();
        counterManager.pushEveryCounterToCounters(element);
    }

    /**
     * Ends processing counters. Pops values of given counter list from counters if necessary.
     * Usually it is expected that this method should be called after processing cheldren of the element.
     *
     * @param context        the processor context
     * @param element the element which counters shall be processed
     */
    public static void endProcessingCounters(CssContext context, IElementNode element) {
        final CssCounterManager counterManager = context.getCounterManager();
        counterManager.popEveryCounterFromCounters(element);
    }

    private static void processCounter(String counter, CssContext context, CssCounterManagerOperation operation) {
        if (counter != null) {
            final CssCounterManager counterManager = context.getCounterManager();
            final String[] params = counter.split(" ");
            for (int i = 0; i < params.length; i++) {
                final String counterName = params[i];
                final Integer possibleCounterValue;
                if (i + 1 < params.length && (possibleCounterValue = CssDimensionParsingUtils.parseInteger(params[i + 1])) != null) {
                    processCounter(counterManager, counterName, operation, possibleCounterValue);
                    i++;
                } else {
                    processCounter(counterManager, counterName, operation);
                }
            }
        }
    }

    private static void processCounter(CssCounterManager counterManager, String counterName,
                                       CssCounterManagerOperation operation, Integer possibleCounterValue) {
        switch (operation) {
            case RESET:
                counterManager.resetCounter(counterName, possibleCounterValue);
                break;
            case INCREMENT:
                counterManager.incrementCounter(counterName, possibleCounterValue);
        }
    }

    private static void processCounter(CssCounterManager counterManager, String counterName,
                                       CssCounterManagerOperation operation) {
        processCounter(counterManager, counterName, operation, null);
    }

}
