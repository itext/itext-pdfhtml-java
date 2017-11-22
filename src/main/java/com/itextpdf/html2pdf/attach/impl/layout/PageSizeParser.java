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
package com.itextpdf.html2pdf.attach.impl.layout;

import com.itextpdf.html2pdf.LogMessageConstant;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.css.util.CssUtils;
import com.itextpdf.io.util.MessageFormatUtil;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.layout.property.UnitValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * The Class PageSizeParser.
 */
class PageSizeParser {

    /** A Map mapping page size names to page size values. */
    private static final Map<String, PageSize> pageSizeConstants = new HashMap<>();
    static {
        pageSizeConstants.put("a5", PageSize.A5);
        pageSizeConstants.put("a4", PageSize.A4);
        pageSizeConstants.put("a3", PageSize.A3);
        pageSizeConstants.put("b5", PageSize.B5);
        pageSizeConstants.put("b4", PageSize.B4);
        pageSizeConstants.put("jis-b5", new PageSize(516, 729));
        pageSizeConstants.put("jis-b4", new PageSize(729, 1032));
        pageSizeConstants.put("letter", PageSize.LETTER);
        pageSizeConstants.put("legal", PageSize.LEGAL);
        pageSizeConstants.put("ledger", PageSize.LEDGER); // TODO may be use here TABLOID? based on w3c tests, ledger in html is interpreted as portrait-oriented page
    }

    /**
     * Fetch the page size.
     *
     * @param pageSizeStr the name of the page size ("a4", "letter",...)
     * @param em the em value
     * @param rem the root em value
     * @param defaultPageSize the default page size
     * @return the page size
     */
    static PageSize fetchPageSize(String pageSizeStr, float em, float rem, PageSize defaultPageSize) {
        PageSize pageSize = (PageSize) defaultPageSize.clone();
        if (pageSizeStr == null || CssConstants.AUTO.equals(pageSizeStr)) {
            return pageSize;
        }

        String[] pageSizeChunks = pageSizeStr.split(" ");
        String firstChunk = pageSizeChunks[0];

        if (isLengthValue(firstChunk)) {
            PageSize pageSizeBasedOnLength = parsePageLengthValue(pageSizeChunks, em, rem);
            if (pageSizeBasedOnLength != null) {
                pageSize = pageSizeBasedOnLength;
            } else {
                Logger logger = LoggerFactory.getLogger(PageSizeParser.class);
                logger.error(MessageFormatUtil.format(LogMessageConstant.PAGE_SIZE_VALUE_IS_INVALID, pageSizeStr));
            }
        } else {
            Boolean landscape = null;
            PageSize namedPageSize = null;

            if (isLandscapePortraitValue(firstChunk)) {
                landscape = CssConstants.LANDSCAPE.equals(firstChunk);
            } else {
                namedPageSize = pageSizeConstants.get(firstChunk);
            }

            if (pageSizeChunks.length > 1) {
                String secondChunk = pageSizeChunks[1];
                if (isLandscapePortraitValue(secondChunk)) {
                    landscape = CssConstants.LANDSCAPE.equals(secondChunk);
                } else {
                    namedPageSize = pageSizeConstants.get(secondChunk);
                }
            }

            boolean b1 = pageSizeChunks.length == 1 && (namedPageSize != null || landscape != null);
            boolean b2 = namedPageSize != null && landscape != null;
            if (b1 || b2) {
                if (namedPageSize != null) {
                    pageSize = namedPageSize;
                }
                if (Boolean.TRUE.equals(landscape)) {
                    pageSize = pageSize.rotate();
                }
            } else {
                Logger logger = LoggerFactory.getLogger(PageSizeParser.class);
                logger.error(MessageFormatUtil.format(LogMessageConstant.PAGE_SIZE_VALUE_IS_INVALID, pageSizeStr));
            }
        }

        return pageSize;
    }

    /**
     * Parses a page length value into a page size.
     *
     * @param pageSizeChunks array of string values that represent the page size
     * @param em the em value
     * @param rem the root em value
     * @return the page size
     */
    private static PageSize parsePageLengthValue(String[] pageSizeChunks, float em, float rem) {
        Float width, height;
        width = tryParsePageLengthValue(pageSizeChunks[0], em, rem);
        if (width == null) {
            return null;
        }
        if (pageSizeChunks.length > 1) {
            height = tryParsePageLengthValue(pageSizeChunks[1], em, rem);
            if (height == null) {
                return null;
            }
        } else {
            height = width;
        }
        return new PageSize((float) width, (float) height);
    }

    /**
     * Try to parse a page length value.
     *
     * @param valueChunk a string containing a value
     * @param em the em value
     * @param rem the root em value
     * @return the value as a float
     */
    private static Float tryParsePageLengthValue(String valueChunk, float em, float rem) {
        UnitValue unitValue = CssUtils.parseLengthValueToPt(valueChunk, em, rem);
        if (unitValue == null || unitValue.isPercentValue()) {
            return null;
        }
        return unitValue.getValue();
    }

    /**
     * Checks if a string represents length value.
     *
     * @param pageSizeChunk the string that possibly represents a length value
     * @return true, if the string represents a length value
     */
    private static boolean isLengthValue(String pageSizeChunk) {
        return CssUtils.isMetricValue(pageSizeChunk) || CssUtils.isRelativeValue(pageSizeChunk);
    }

    /**
     * Checks if a string represents the CSS value for landscape or portrait orientation.
     *
     * @param pageSizeChunk the string that possibly represents a landscape or portrait value
     * @return true, if the string represents a landscape or portrait value
     */
    private static boolean isLandscapePortraitValue(String pageSizeChunk) {
        return CssConstants.LANDSCAPE.equals(pageSizeChunk) || CssConstants.PORTRAIT.equals(pageSizeChunk);
    }
}
