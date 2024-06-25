/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2024 Apryse Group NV
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
package com.itextpdf.html2pdf.css.w3c.css_grid.abspos;

import com.itextpdf.html2pdf.css.w3c.css_grid.W3CCssGridTest;
import com.itextpdf.io.logs.IoLogMessageConstant;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;

//TODO DEVSIX-8358: Support shorthands
//TODO DEVSIX-5166 change after align-self is supported is supported
@LogMessages(messages = {
        @LogMessage(messageTemplate = IoLogMessageConstant.RECTANGLE_HAS_NEGATIVE_SIZE, count = 11)
})
public class GridAbsposStaticposAlignSelf001Test extends W3CCssGridTest {
    @Override
    protected String getHtmlFileName() {
        return "grid-abspos-staticpos-align-self-001.html";
    }
}
