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
package com.itextpdf.html2pdf.css.resolve;


import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.css.apply.util.FontStyleApplierUtil;
import com.itextpdf.html2pdf.css.page.CssRunningManager;
import com.itextpdf.html2pdf.css.resolve.func.counter.CssCounterManager;

/**
 * Class that bundles all the CSS context properties.
 */
public class CssContext {

    /** The root font size value in pt. */
    private float rootFontSize = FontStyleApplierUtil.parseAbsoluteFontSize(CssDefaults.getDefaultValue(CssConstants.FONT_SIZE));

    /** The counter manager. */
    private CssCounterManager counterManager = new CssCounterManager();

    /** Indicates if a page counter is present. */
    private boolean pagesCounterPresent = false;

    /** The quotes depth. */
    private int quotesDepth = 0;

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
     * Sets the root font size.
     *
     * @param fontSizeStr the new root font size
     */
    public void setRootFontSize(String fontSizeStr) {
        this.rootFontSize = FontStyleApplierUtil.parseAbsoluteFontSize(fontSizeStr);
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
     * Sets the presence of a page counter.
     *
     * @param pagesCounterPresent the new pages counter present
     */
    public void setPagesCounterPresent(boolean pagesCounterPresent) {
        this.pagesCounterPresent = pagesCounterPresent;
    }

    /**
     * Checks if a pages counter is present.
     *
     * @return true, if is pages counter present
     */
    public boolean isPagesCounterPresent() {
        return pagesCounterPresent;
    }

    /**
     * Gets the quotes depth.
     *
     * @return the quotes depth
     */
    public int getQuotesDepth() {
        return quotesDepth;
    }

    /**
     * Sets the quotes depth.
     *
     * @param quotesDepth the new quotes depth
     */
    public void setQuotesDepth(int quotesDepth) {
        this.quotesDepth = quotesDepth;
    }

    public CssRunningManager getRunningManager() {
        return runningManager;
    }
}
