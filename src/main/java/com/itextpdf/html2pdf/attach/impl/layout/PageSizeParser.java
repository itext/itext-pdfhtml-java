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
package com.itextpdf.html2pdf.attach.impl.layout;

import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.commons.utils.MessageFormatUtil;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.styledxmlparser.css.util.CssTypesValidationUtils;
import com.itextpdf.styledxmlparser.css.util.CssDimensionParsingUtils;

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
        /* according to CSS Paged Media Module Level 3 Editor’s Draft:
        "ledger - Equivalent to the size of North American ledger: 11 inches wide by 17 inches high"
        See https://www.w3.org/TR/css-page-3/
        That makes this <page-size> portrait-oriented, i.e. rotated PageSize.LEDGER.
        */
        pageSizeConstants.put("ledger", PageSize.LEDGER.rotate());
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
                logger.error(MessageFormatUtil.format(
                        Html2PdfLogMessageConstant.PAGE_SIZE_VALUE_IS_INVALID, pageSizeStr));
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

            boolean isValidSingleWordDeclaration = pageSizeChunks.length == 1 && (namedPageSize != null || landscape != null);
            boolean isValidTwoWordDeclaration = namedPageSize != null && landscape != null;
            if (isValidSingleWordDeclaration || isValidTwoWordDeclaration) {
                if (namedPageSize != null) {
                    pageSize = namedPageSize;
                }
                boolean landscapeRequestedAndNeedRotation = Boolean.TRUE.equals(landscape) && pageSize.getWidth() < pageSize.getHeight();
                boolean portraitRequestedAndNeedRotation = Boolean.FALSE.equals(landscape) && pageSize.getHeight() < pageSize.getWidth();
                if (landscapeRequestedAndNeedRotation || portraitRequestedAndNeedRotation) {
                    pageSize = pageSize.rotate();
                }
            } else {
                Logger logger = LoggerFactory.getLogger(PageSizeParser.class);
                logger.error(MessageFormatUtil.format(
                        Html2PdfLogMessageConstant.PAGE_SIZE_VALUE_IS_INVALID, pageSizeStr));
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
        UnitValue unitValue = CssDimensionParsingUtils.parseLengthValueToPt(valueChunk, em, rem);
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
        return CssTypesValidationUtils.isMetricValue(pageSizeChunk) || CssTypesValidationUtils.isRelativeValue(pageSizeChunk);
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
