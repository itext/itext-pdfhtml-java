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
