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
package com.itextpdf.html2pdf.css;

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

@Tag("IntegrationTest")
public class BlockFormattingContextTest extends ExtendedHtmlConversionITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/BlockFormattingContextTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/BlockFormattingContextTest/";

    @BeforeAll
    public static void beforeClass() {
        createOrClearDestinationFolder(destinationFolder);
    }


    @Test
    public void bfcOwnerFloat_floatsAndClear() throws IOException, InterruptedException {
        convertToPdfAndCompare("bfcOwnerFloat_floatsAndClear", sourceFolder, destinationFolder);
    }

    @Test
    public void bfcOwnerFloat_marginsCollapse() throws IOException, InterruptedException {
        convertToPdfAndCompare("bfcOwnerFloat_marginsCollapse", sourceFolder, destinationFolder);
    }

    @Test
    public void bfcOwnerAbsolute_floatsAndClear() throws IOException, InterruptedException {
        // TODO: DEVSIX-5470
        convertToPdfAndCompare("bfcOwnerAbsolute_floatsAndClear", sourceFolder, destinationFolder);
    }

    @Test
    public void bfcOwnerAbsolute_marginsCollapse() throws IOException, InterruptedException {
        // Margins don't collapse here which is correct,
        // however absolute positioning works a bit wrong from css point of view.
        convertToPdfAndCompare("bfcOwnerAbsolute_marginsCollapse", sourceFolder, destinationFolder);
    }

    @Test
    public void bfcOwnerInlineBlock_floatsAndClear() throws IOException, InterruptedException {
        convertToPdfAndCompare("bfcOwnerInlineBlock_floatsAndClear", sourceFolder, destinationFolder);
    }

    @Test
    public void bfcOwnerInlineBlock_marginsCollapse() throws IOException, InterruptedException {
        convertToPdfAndCompare("bfcOwnerInlineBlock_marginsCollapse", sourceFolder, destinationFolder);
    }

    @Test
    public void bfcOwnerOverflowHidden_floatsAndClear() throws IOException, InterruptedException {
        // TODO: DEVSIX-5471
        convertToPdfAndCompare("bfcOwnerOverflowHidden_floatsAndClear", sourceFolder, destinationFolder);
    }

    @Test
    public void bfcOwnerOverflowHidden_marginsCollapse() throws IOException, InterruptedException {
        convertToPdfAndCompare("bfcOwnerOverflowHidden_marginsCollapse", sourceFolder, destinationFolder);
    }
}
