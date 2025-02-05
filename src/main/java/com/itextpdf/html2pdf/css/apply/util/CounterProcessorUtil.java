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
package com.itextpdf.html2pdf.css.apply.util;

import com.itextpdf.html2pdf.css.CssConstants;
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
        processReset(counterReset, context);

        String counterIncrement = cssProps.get(CssConstants.COUNTER_INCREMENT);
        processIncrement(counterIncrement, context);
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

    private static void processReset(String counterReset, CssContext context) {
        if (counterReset != null) {
            final CssCounterManager counterManager = context.getCounterManager();
            final String[] params = counterReset.split(" ");
            for (int i = 0; i < params.length; i++) {
                final String counterName = params[i];
                final Integer possibleCounterValue;
                if (i + 1 < params.length && (possibleCounterValue = CssDimensionParsingUtils.parseInteger(params[i + 1])) != null) {
                    counterManager.resetCounter(counterName, (int) possibleCounterValue);
                    i++;
                } else {
                    counterManager.resetCounter(counterName);
                }
            }
        }
    }

    private static void processIncrement(String counterIncrement, CssContext context) {
        if (counterIncrement != null) {
            final CssCounterManager counterManager = context.getCounterManager();
            final String[] params = counterIncrement.split(" ");
            for (int i = 0; i < params.length; i++) {
                final String counterName = params[i];
                final Integer possibleIncrementValue;
                if (i + 1 < params.length && (possibleIncrementValue = CssDimensionParsingUtils.parseInteger(params[i + 1])) != null) {
                    counterManager.incrementCounter(counterName, (int) possibleIncrementValue);
                    i++;
                } else {
                    counterManager.incrementCounter(counterName);
                }
            }
        }
    }

}
