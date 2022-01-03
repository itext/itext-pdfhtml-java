/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2022 iText Group NV
    Authors: iText Software.

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
package com.itextpdf.html2pdf.css.page;

import com.itextpdf.html2pdf.attach.impl.layout.RunningElementContainer;
import com.itextpdf.html2pdf.css.CssConstants;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

/**
 * Class that manages running elements.
 */
public class CssRunningManager {
    private Map<String, LinkedHashSet<RunningElementContainer>> runningElements = new HashMap<>();

    /**
     * Registers new running element from HTML document.
     *
     * @param runningElemName the name of the new running element.
     * @param container       a wrapper for the running elements taken out of the normal flow.
     */
    public void addRunningElement(String runningElemName, RunningElementContainer container) {
        LinkedHashSet<RunningElementContainer> runningElems = runningElements.get(runningElemName);
        if (runningElems == null) {
            runningElems = new LinkedHashSet<>();
            runningElements.put(runningElemName, runningElems);
        }
        runningElems.add(container);
    }

    /**
     * Finds the running element that has particular name and should appear on specific page with given occurrence
     * options. This would work only if page content was already layouted and flushed (drawn).
     *
     * @param runningElemName  the running element name.
     * @param occurrenceOption an option defining which running element should be chosen in case there are multiple
     *                         running elements with the same name on the given page.
     * @param pageNum          the 1-based index of the page for which running element is requested.
     * @return {@link RunningElementContainer} with corresponding running element, or {@code null} if no running
     * element should be displayed for the given page with the given name or occurrence option.
     */
    public RunningElementContainer getRunningElement(String runningElemName, String occurrenceOption, int pageNum) {
        LinkedHashSet<RunningElementContainer> runningElementContainers = runningElements.get(runningElemName);
        if (runningElementContainers == null || runningElementContainers.isEmpty()) {
            return null;
        }

        boolean isLast = CssConstants.LAST.equals(occurrenceOption);
        boolean isFirstExcept = CssConstants.FIRST_EXCEPT.equals(occurrenceOption);
        boolean isStart = CssConstants.START.equals(occurrenceOption);

        RunningElementContainer runningElementContainer = null;
        for (RunningElementContainer container : runningElementContainers) {
            if (container.getOccurrencePage() == 0 || container.getOccurrencePage() > pageNum) {
                // Imagine that floating element is before some normal element, but is drawn on the next page,
                // while this normal element is drawn on previous page.
                // This example covers both the cases when zero and bigger page nums are required to be skipped,
                // rather than breaking here.
                continue;
            }

            if (container.getOccurrencePage() < pageNum) {
                runningElementContainer = container;
            }
            if (container.getOccurrencePage() == pageNum) {
                if (isFirstExcept) {
                    return null;
                }
                if (!isStart || container.isFirstOnPage()) {
                    runningElementContainer = container;
                }
                if (!isLast) {
                    break;
                }
            }
        }

        return runningElementContainer;
    }
}
