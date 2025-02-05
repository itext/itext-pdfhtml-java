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
package com.itextpdf.html2pdf.css.resolve;

import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.css.page.CssRunningManager;
import com.itextpdf.html2pdf.css.resolve.func.counter.CssCounterManager;
import com.itextpdf.styledxmlparser.css.resolve.AbstractCssContext;
import com.itextpdf.styledxmlparser.css.resolve.CssDefaults;
import com.itextpdf.styledxmlparser.css.util.CssDimensionParsingUtils;

/**
 * Class that bundles all the CSS context properties.
 */
public class CssContext extends AbstractCssContext {

    /** The root font size value in pt. */
    private float rootFontSize = CssDimensionParsingUtils.parseAbsoluteFontSize(CssDefaults.getDefaultValue(CssConstants.FONT_SIZE));

    /** Current element font size in pt. */
    private float currentFontSize = -1.0F;

    /** The counter manager. */
    private CssCounterManager counterManager = new CssCounterManager();

    /** Indicates if a pages counter or page(s) target-counter is present. */
    private boolean pagesCounterOrTargetCounterPresent = false;

    /** Indicates if a non-page(s) target-counter(s) is present. */
    private boolean nonPagesTargetCounterPresent = false;

    /** The running elements manager. */
    private CssRunningManager runningManager = new CssRunningManager();

    /**
     * Gets the root font size.
     *
     * @return the root font size in pt
     */
    public float getRootFontSize() {
        return rootFontSize;
    }

    /**
     * Sets the root font size.
     *
     * @param fontSize the new root font size
     */
    public void setRootFontSize(float fontSize) {
        this.rootFontSize = fontSize;
    }

    /**
     * Gets the current element font size.
     *
     * @return the current element font size in pt
     */
    public float getCurrentFontSize() {
        return currentFontSize;
    }

    /**
     * Sets the current font size.
     *
     * @param fontSize the new current element font size
     */
    public void setCurrentFontSize(float fontSize) {
        this.currentFontSize = fontSize;
    }

    /**
     * Sets the root font size.
     *
     * @param fontSizeStr the new root font size
     */
    public void setRootFontSize(String fontSizeStr) {
        this.rootFontSize = CssDimensionParsingUtils.parseAbsoluteFontSize(fontSizeStr);
    }

    /**
     * Gets the counter manager.
     *
     * @return the counter manager
     */
    public CssCounterManager getCounterManager() {
        return counterManager;
    }

    /**
     * Sets the presence of a pages counter or page(s) target counter.
     *
     * @param pagesCounterOrTargetCounterPresent the new pages counter or page(s) target-counter present
     */
    public void setPagesCounterPresent(boolean pagesCounterOrTargetCounterPresent) {
        this.pagesCounterOrTargetCounterPresent = pagesCounterOrTargetCounterPresent;
    }

    /**
     * Checks if a pages counter or page(s) target-counter is present.
     *
     * @return true, if pages counter or page(s) target-counter present
     */
    public boolean isPagesCounterPresent() {
        return pagesCounterOrTargetCounterPresent;
    }

    /**
     * Sets the presence of a non-page(s) target-counter(s).
     *
     * @param nonPagesTargetCounterPresent the new non-page(s) target-counter(s) present
     */
    public void setNonPagesTargetCounterPresent(boolean nonPagesTargetCounterPresent) {
        this.nonPagesTargetCounterPresent = nonPagesTargetCounterPresent;
    }

    /**
     * Checks if a non-page(s) target-counter(s) is present.
     *
     * @return true, if non-page(s) target-counter(s) present
     */
    public boolean isNonPagesTargetCounterPresent() {
        return nonPagesTargetCounterPresent;
    }

    public CssRunningManager getRunningManager() {
        return runningManager;
    }
}
