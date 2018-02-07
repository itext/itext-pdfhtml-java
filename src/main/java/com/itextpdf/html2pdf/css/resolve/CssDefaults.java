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

import com.itextpdf.html2pdf.LogMessageConstant;
import com.itextpdf.html2pdf.css.CssConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.io.util.MessageFormatUtil;
import java.util.HashMap;
import java.util.Map;

/**
 * Helper class that allows you to get the default values of CSS properties.
 */
public class CssDefaults {

    /** A map with properties and their default values. */
    private static final Map<String, String> defaultValues = new HashMap<>();

    static {
        defaultValues.put(CssConstants.COLOR, "black"); // not specified, varies from browser to browser
        defaultValues.put(CssConstants.OPACITY, "1");

        defaultValues.put(CssConstants.BACKGROUND_ATTACHMENT, CssConstants.SCROLL);
        defaultValues.put(CssConstants.BACKGROUND_BLEND_MODE, CssConstants.NORMAL);
        defaultValues.put(CssConstants.BACKGROUND_COLOR, CssConstants.TRANSPARENT);
        defaultValues.put(CssConstants.BACKGROUND_IMAGE, CssConstants.NONE);
        defaultValues.put(CssConstants.BACKGROUND_POSITION, "0% 0%");
        defaultValues.put(CssConstants.BACKGROUND_REPEAT, CssConstants.REPEAT);
        defaultValues.put(CssConstants.BACKGROUND_CLIP, CssConstants.BORDER_BOX);
        defaultValues.put(CssConstants.BACKGROUND_ORIGIN, CssConstants.PADDING_BOX);
        defaultValues.put(CssConstants.BACKGROUND_SIZE, CssConstants.AUTO);

        defaultValues.put(CssConstants.BORDER_BOTTOM_COLOR, CssConstants.CURRENTCOLOR);
        defaultValues.put(CssConstants.BORDER_LEFT_COLOR, CssConstants.CURRENTCOLOR);
        defaultValues.put(CssConstants.BORDER_RIGHT_COLOR, CssConstants.CURRENTCOLOR);
        defaultValues.put(CssConstants.BORDER_TOP_COLOR, CssConstants.CURRENTCOLOR);
        defaultValues.put(CssConstants.BORDER_BOTTOM_STYLE, CssConstants.NONE);
        defaultValues.put(CssConstants.BORDER_LEFT_STYLE, CssConstants.NONE);
        defaultValues.put(CssConstants.BORDER_RIGHT_STYLE, CssConstants.NONE);
        defaultValues.put(CssConstants.BORDER_TOP_STYLE, CssConstants.NONE);
        defaultValues.put(CssConstants.BORDER_BOTTOM_WIDTH, CssConstants.MEDIUM);
        defaultValues.put(CssConstants.BORDER_LEFT_WIDTH, CssConstants.MEDIUM);
        defaultValues.put(CssConstants.BORDER_RIGHT_WIDTH, CssConstants.MEDIUM);
        defaultValues.put(CssConstants.BORDER_TOP_WIDTH, CssConstants.MEDIUM);
        defaultValues.put(CssConstants.BORDER_WIDTH, CssConstants.MEDIUM);
        defaultValues.put(CssConstants.BORDER_IMAGE, CssConstants.NONE);

        defaultValues.put(CssConstants.BORDER_RADIUS, "0");
        defaultValues.put(CssConstants.BORDER_BOTTOM_LEFT_RADIUS, "0");
        defaultValues.put(CssConstants.BORDER_BOTTOM_RIGHT_RADIUS, "0");
        defaultValues.put(CssConstants.BORDER_TOP_LEFT_RADIUS, "0");
        defaultValues.put(CssConstants.BORDER_TOP_RIGHT_RADIUS, "0");

        defaultValues.put(CssConstants.BOX_SHADOW, CssConstants.NONE);

        defaultValues.put(CssConstants.FLOAT, CssConstants.NONE);
        defaultValues.put(CssConstants.FONT_WEIGHT, CssConstants.NORMAL);
        defaultValues.put(CssConstants.FONT_SIZE, CssConstants.MEDIUM);
        defaultValues.put(CssConstants.FONT_STYLE, CssConstants.NORMAL);
        defaultValues.put(CssConstants.FONT_VARIANT, CssConstants.NORMAL);

        defaultValues.put(CssConstants.HYPHENS, CssConstants.MANUAL);

        defaultValues.put(CssConstants.LINE_HEIGHT, CssConstants.NORMAL);
        defaultValues.put(CssConstants.LIST_STYLE_TYPE, CssConstants.DISC);
        defaultValues.put(CssConstants.LIST_STYLE_IMAGE, CssConstants.NONE);
        defaultValues.put(CssConstants.LIST_STYLE_POSITION, CssConstants.OUTSIDE);

        defaultValues.put(CssConstants.MARGIN_BOTTOM, "0");
        defaultValues.put(CssConstants.MARGIN_LEFT, "0");
        defaultValues.put(CssConstants.MARGIN_RIGHT, "0");
        defaultValues.put(CssConstants.MARGIN_TOP, "0");

        defaultValues.put(CssConstants.MIN_HEIGHT, "0");

        defaultValues.put(CssConstants.OUTLINE_COLOR, CssConstants.CURRENTCOLOR);
        defaultValues.put(CssConstants.OUTLINE_STYLE, CssConstants.NONE);
        defaultValues.put(CssConstants.OUTLINE_WIDTH, CssConstants.MEDIUM);

        defaultValues.put(CssConstants.PADDING_BOTTOM, "0");
        defaultValues.put(CssConstants.PADDING_LEFT, "0");
        defaultValues.put(CssConstants.PADDING_RIGHT, "0");
        defaultValues.put(CssConstants.PADDING_TOP, "0");

        defaultValues.put(CssConstants.PAGE_BREAK_AFTER, CssConstants.AUTO);
        defaultValues.put(CssConstants.PAGE_BREAK_BEFORE, CssConstants.AUTO);
        defaultValues.put(CssConstants.PAGE_BREAK_INSIDE, CssConstants.AUTO);

        defaultValues.put(CssConstants.POSITION, CssConstants.STATIC);

        defaultValues.put(CssConstants.QUOTES, "\"\\00ab\" \"\\00bb\"");

        defaultValues.put(CssConstants.TEXT_ALIGN, CssConstants.START);
        defaultValues.put(CssConstants.TEXT_DECORATION, CssConstants.NONE);
        defaultValues.put(CssConstants.TEXT_TRANSFORM, CssConstants.NONE);
        defaultValues.put(CssConstants.TEXT_DECORATION, CssConstants.NONE);

        defaultValues.put(CssConstants.WHITE_SPACE, CssConstants.NORMAL);
        defaultValues.put(CssConstants.WIDTH, CssConstants.AUTO);

        // TODO not complete
    }

    /**
     * Gets the default value of a property.
     *
     * @param property the property
     * @return the default value
     */
    public static String getDefaultValue(String property) {
        String defaultVal = defaultValues.get(property);
        if (defaultVal == null) {
            Logger logger = LoggerFactory.getLogger(CssDefaults.class);
            logger.error(MessageFormatUtil.format(LogMessageConstant.DEFAULT_VALUE_OF_CSS_PROPERTY_UNKNOWN, property));
        }
        return defaultVal;
    }
}
