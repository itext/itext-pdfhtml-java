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
package com.itextpdf.html2pdf.css.w3c.css_multicol;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.css.w3c.W3CCssTest;
import com.itextpdf.io.util.UrlUtil;
import com.itextpdf.kernel.utils.CompareTool;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class BalanceGridContainerRefTest extends W3CCssTest {
    private static final String baseDestinationFolder = "./target/test/com/itextpdf/html2pdf/css/w3c/";
    private static final String baseSourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/w3c/";
    //after  completing DEVSIX-8427, remove everything from this class except for getHtmlFileName() method
    @Override
    protected String getHtmlFileName() {
        return "balance-grid-container-ref.html";
    }

    @Override
    @Disabled("DEVSIX-8427")
    @Test
    public void test() throws IOException, InterruptedException {
        String sourceFolder = getSourceFolder();
        String destinationFolder = getDestinationFolder();
        String htmlFilePath = sourceFolder + getHtmlFileName();
        String outFilePath = destinationFolder + getOutPdfFileName();
        String cmpFilePath = sourceFolder + getOutPdfFileName();
        System.out.println("html: " + UrlUtil.getNormalizedFileUriString(htmlFilePath) + "\n");
        HtmlConverter.convertToPdf(new File(htmlFilePath), new File(outFilePath), getConverterProperties());
        Assertions.assertNull(new CompareTool().compareByContent(outFilePath, cmpFilePath, destinationFolder, "diff_"));
    }

    private String getOutPdfFileName() {
        String htmlFileName = getHtmlFileName();
        return htmlFileName.replaceAll("\\.[a-zA-Z]+?$", ".pdf");
    }

    private String getDestinationFolder() {
        String localPackage = getLocalPackage();
        return baseDestinationFolder + localPackage + File.separatorChar + getTestClassName() + File.separatorChar;
    }

    private String getSourceFolder() {
        String localPackage = getLocalPackage();
        return baseSourceFolder + localPackage + File.separatorChar;
    }

    private String getLocalPackage() {
        String packageName = getClass().getPackage().getName();
        String basePackageName = W3CCssTest.class.getPackage().getName();
        return packageName.substring(basePackageName.length()).replace('.', File.separatorChar);
    }

    private String getTestClassName() {
        return getClass().getSimpleName();
    }
}
