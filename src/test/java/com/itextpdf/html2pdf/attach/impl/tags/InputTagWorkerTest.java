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
package com.itextpdf.html2pdf.attach.impl.tags;

import com.itextpdf.html2pdf.html.AttributeConstants;
import com.itextpdf.test.ExtendedITextTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

@Tag("UnitTest")
public class InputTagWorkerTest extends ExtendedITextTest {

    @Test
    public void testNumberInputPreprocessing() {
        Assertions.assertEquals("", InputTagWorker.preprocessInputValue("bear", AttributeConstants.NUMBER));
        Assertions.assertEquals("4", InputTagWorker.preprocessInputValue("4", AttributeConstants.NUMBER));
        Assertions.assertEquals("04", InputTagWorker.preprocessInputValue("04", AttributeConstants.NUMBER));
        Assertions.assertEquals("", InputTagWorker.preprocessInputValue("4.", AttributeConstants.NUMBER));
        Assertions.assertEquals("", InputTagWorker.preprocessInputValue(".", AttributeConstants.NUMBER));
        Assertions.assertEquals(".9", InputTagWorker.preprocessInputValue(".9", AttributeConstants.NUMBER));
        Assertions.assertEquals("4.4", InputTagWorker.preprocessInputValue("4.4", AttributeConstants.NUMBER));
        Assertions.assertEquals("-4", InputTagWorker.preprocessInputValue("-4", AttributeConstants.NUMBER));
        Assertions.assertEquals("", InputTagWorker.preprocessInputValue("", AttributeConstants.NUMBER));
        Assertions.assertEquals("", InputTagWorker.preprocessInputValue("-", AttributeConstants.NUMBER));
        Assertions.assertEquals("-.9", InputTagWorker.preprocessInputValue("-.9", AttributeConstants.NUMBER));
        Assertions.assertEquals("", InputTagWorker.preprocessInputValue("0-.9", AttributeConstants.NUMBER));
    }
}
