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
package com.itextpdf.html2pdf.css.w3c.css_backgrounds.bg_size.vector;

import com.itextpdf.html2pdf.css.w3c.W3CCssTest;
import com.itextpdf.svg.logs.SvgLogMessageConstant;
import com.itextpdf.test.LogLevelConstants;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;

@LogMessages(messages = {
        @LogMessage(messageTemplate = SvgLogMessageConstant.VIEWBOX_WIDTH_OR_HEIGHT_IS_ZERO, logLevel = LogLevelConstants.INFO)
})
public class ZeroHRatioA5PxTest extends W3CCssTest {
    @Override
    protected String getHtmlFileName() {
        return "zero-height-ratio-auto-5px.html";
    }
}
