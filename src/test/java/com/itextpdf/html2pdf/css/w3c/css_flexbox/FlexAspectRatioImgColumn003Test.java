/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2021 iText Group NV
    Authors: iText Software.

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
package com.itextpdf.html2pdf.css.w3c.css_flexbox;

import com.itextpdf.html2pdf.LogMessageConstant;
import com.itextpdf.html2pdf.css.w3c.W3CCssTest;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;

//TODO DEVSIX-5096 support flex-direction: column
//TODO DEVSIX-5004 improve support of flex-items with intrinsic aspect ratio
//TODO DEVSIX-5166 flex: Support align-self property
@LogMessages(messages = @LogMessage(messageTemplate = LogMessageConstant.FLEX_PROPERTY_IS_NOT_SUPPORTED_YET, count = 2))
public class FlexAspectRatioImgColumn003Test extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "flex-aspect-ratio-img-column-003.html";
    }
}
