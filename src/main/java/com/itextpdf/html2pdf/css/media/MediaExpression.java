/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2017 iText Group NV
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
package com.itextpdf.html2pdf.css.media;

import com.itextpdf.html2pdf.css.util.CssUtils;
import java.util.Objects;

public class MediaExpression {

    private boolean minPrefix;
    private boolean maxPrefix;
    private String feature;
    private String value;

    public MediaExpression(String feature, String value) {
        this.feature = feature.trim().toLowerCase();
        if (value != null) {
            this.value = value.trim().toLowerCase();
        }

        String minPref = MediaRuleConstants.MIN + "-";
        String maxPref = MediaRuleConstants.MAX + "-";
        minPrefix = feature.startsWith(minPref);
        if (minPrefix) {
            feature = feature.substring(minPref.length());
        }
        maxPrefix = feature.startsWith(maxPref);
        if (maxPrefix) {
            feature = feature.substring(maxPref.length());
        }
    }

    public boolean matches(MediaDeviceDescription deviceDescription) {
        switch (feature) {
            case MediaFeature.COLOR: {
                Integer val = CssUtils.parseInteger(value);
                if (minPrefix) {
                    return val != null && deviceDescription.getBitsPerComponent() >= val;
                } else if (maxPrefix) {
                    return val != null && deviceDescription.getBitsPerComponent() <= val;
                } else {
                    return val == null ? deviceDescription.getBitsPerComponent() != 0 : val == deviceDescription.getBitsPerComponent();
                }
            }
            case MediaFeature.COLOR_INDEX: {
                Integer val = CssUtils.parseInteger(value);
                if (minPrefix) {
                    return val != null && deviceDescription.getColorIndex() >= val;
                } else if (maxPrefix) {
                    return val != null && deviceDescription.getColorIndex() <= val;
                } else {
                    return val == null ? deviceDescription.getColorIndex() != 0 : val == deviceDescription.getColorIndex();
                }
            }
            case MediaFeature.ASPECT_RATIO: {
                int[] aspectRatio = CssUtils.parseAspectRatio(value);
                if (minPrefix) {
                    return aspectRatio != null && aspectRatio[0] * deviceDescription.getHeight() >= aspectRatio[1] * deviceDescription.getWidth();
                } else if (maxPrefix) {
                    return aspectRatio != null && aspectRatio[0] * deviceDescription.getHeight() <= aspectRatio[1] * deviceDescription.getWidth();
                } else {
                    return aspectRatio != null && aspectRatio[0] * deviceDescription.getHeight() == aspectRatio[1] * deviceDescription.getWidth();
                }
            }
            case MediaFeature.GRID: {
                Integer val = CssUtils.parseInteger(value);
                return val != null && val == 0 && !deviceDescription.isGrid() || deviceDescription.isGrid();
            }
            case MediaFeature.SCAN: {
                return Objects.equals(value, deviceDescription.getScan());
            }
            case MediaFeature.ORIENTATION: {
                return Objects.equals(value, deviceDescription.getOrientation());
            }
            case MediaFeature.MONOCHROME: {
                Integer val = CssUtils.parseInteger(value);
                if (minPrefix) {
                    return val != null && deviceDescription.getMonochrome() >= val;
                } else if (maxPrefix) {
                    return val != null && deviceDescription.getMonochrome() <= val;
                } else {
                    return val == null && deviceDescription.getMonochrome() > 0 || val == deviceDescription.getMonochrome();
                }
            }
            default:
                return false;
        }
    }

}
