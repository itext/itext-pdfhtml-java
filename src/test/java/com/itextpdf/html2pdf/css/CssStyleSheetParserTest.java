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
package com.itextpdf.html2pdf.css;

import com.itextpdf.io.util.ResourceUtil;
import com.itextpdf.io.util.StreamUtil;
import com.itextpdf.styledxmlparser.css.CssStyleSheet;
import com.itextpdf.styledxmlparser.css.parse.CssStyleSheetParser;
import com.itextpdf.test.ExtendedITextTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

import java.io.FileInputStream;
import java.io.IOException;

@Tag("UnitTest")
public class CssStyleSheetParserTest extends ExtendedITextTest {

    private static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/CssStyleSheetParserTest/";
    private static final String DEFAULT_CSS_PATH = "com/itextpdf/html2pdf/default.css";

    @Test
    public void testDefaultCss() throws IOException {
        String cmpFile = sourceFolder + "cmp_default.css";
        CssStyleSheet styleSheet = CssStyleSheetParser.parse(ResourceUtil.getResourceStream(DEFAULT_CSS_PATH));
        Assertions.assertEquals(getCssFileContents(cmpFile), styleSheet.toString());
    }

    private String getCssFileContents(String filePath) throws IOException {
        byte[] bytes = StreamUtil.inputStreamToArray(new FileInputStream(filePath));
        String content = new String(bytes);
        content = content.trim();
        content = content.replace("\r\n", "\n");
        return content;
    }

}
