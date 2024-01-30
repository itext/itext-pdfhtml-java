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

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.io.logs.IoLogMessageConstant;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class AbsolutePositionTest extends ExtendedHtmlConversionITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/AbsolutePositionTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/AbsolutePositionTest/";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void absolutePosition01Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("absolutePositionTest01", sourceFolder, destinationFolder);
    }

    @Test
    @Ignore("DEVSIX-1616: Absolute position for elements that break across pages is not supported")
    public void absolutePosition02Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("absolutePositionTest02", sourceFolder, destinationFolder);
    }

    @Test
    public void absolutePosition03Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("absolutePositionTest03", sourceFolder, destinationFolder);
    }

    @Test
    public void absolutePosition04Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("absolutePositionTest04", sourceFolder, destinationFolder);
    }

    @Test
    public void absolutePosition05Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("absolutePositionTest05", sourceFolder, destinationFolder);
    }

    @Test
    public void absolutePosition06Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("absolutePositionTest06", sourceFolder, destinationFolder);
    }

    @Test
    public void absolutePosition07Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("absolutePositionTest07", sourceFolder, destinationFolder);
    }

    @Test
    public void absolutePosition08Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("absolutePositionTest08", sourceFolder, destinationFolder);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = IoLogMessageConstant.OCCUPIED_AREA_HAS_NOT_BEEN_INITIALIZED, count = 1),
    })
    public void absolutePosition09Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("absolutePositionTest09", sourceFolder, destinationFolder);
    }

    @Test
    public void absolutePosition10Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("absolutePositionTest10", sourceFolder, destinationFolder);
    }

    @Test
    public void absolutePosition11Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("absolutePositionTest11", sourceFolder, destinationFolder);
    }

    @Test
    public void absolutePosition12Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("absolutePositionTest12", sourceFolder, destinationFolder);
    }

    @Test
    public void absolutePosition13Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("absolutePositionTest13", sourceFolder, destinationFolder);
    }

    @Test
    public void absolutePosition14Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("absolutePositionTest14", sourceFolder, destinationFolder);
    }

    @Test
    public void absolutePosition15Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("absolutePositionTest15", sourceFolder, destinationFolder);
    }

    @Test
    public void absolutePositionTest16() throws IOException, InterruptedException {
        convertToPdfAndCompare("absolutePositionTest16", sourceFolder, destinationFolder);
    }

    @Test
    public void absolutePositionTest17() throws IOException, InterruptedException {
        convertToPdfAndCompare("absolutePositionTest17", sourceFolder, destinationFolder);
    }

    @Ignore("DEVSIX-1818")
    @Test
    public void absolutePositionTest18() throws IOException, InterruptedException {
        convertToPdfAndCompare("absolutePositionTest18", sourceFolder, destinationFolder);
    }

    @Test
    public void absPosNoTopBottomTest01() throws IOException, InterruptedException {
        // TODO DEVSIX-1950
        convertToPdfAndCompare("absPosNoTopBottomTest01", sourceFolder, destinationFolder);
    }
}
