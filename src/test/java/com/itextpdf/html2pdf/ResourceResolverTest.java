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
package com.itextpdf.html2pdf;

import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import com.itextpdf.test.annotations.type.IntegrationTest;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;

@Category(IntegrationTest.class)
public class ResourceResolverTest extends ExtendedITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/ResourceResolverTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/ResourceResolverTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(destinationFolder);
    }

    @Test
    public void resourceResolverTest03() throws IOException, InterruptedException {
        String baseUri = sourceFolder + "res";
        String outPdf = destinationFolder + "resourceResolverTest03.pdf";
        String cmpPdf = sourceFolder + "cmp_resourceResolverTest03.pdf";
        try (FileInputStream fileInputStream = new FileInputStream(sourceFolder + "resourceResolverTest03.html");
             FileOutputStream fileOutputStream = new FileOutputStream(outPdf)) {
            HtmlConverter.convertToPdf(fileInputStream, fileOutputStream, new ConverterProperties().setBaseUri(baseUri));
        }
        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder, "diff03_"));
    }
    @Test
    public void resourceResolverTest07() throws IOException, InterruptedException {
        String outPdf = destinationFolder + "resourceResolverTest07.pdf";
        String cmpPdf = sourceFolder + "cmp_resourceResolverTest07.pdf";
        HtmlConverter.convertToPdf(new File(sourceFolder + "resourceResolverTest07.html"), new File(outPdf));
        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder, "diff07_"));
    }

    @Test
    public void resourceResolverTest09() throws IOException, InterruptedException {
        String outPdf = destinationFolder + "resourceResolverTest09.pdf";
        String cmpPdf = sourceFolder + "cmp_resourceResolverTest09.pdf";
        HtmlConverter.convertToPdf(new File(sourceFolder + "resourceResolverTest09.html"), new File(outPdf));
        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder, "diff09_"));
    }

    @Test
    public void resourceResolverTest10() throws IOException, InterruptedException {
        String outPdf = destinationFolder + "resourceResolverTest10.pdf";
        String cmpPdf = sourceFolder + "cmp_resourceResolverTest10.pdf";
        try (FileInputStream fileInputStream = new FileInputStream(sourceFolder + "resourceResolverTest10.html");
             FileOutputStream fileOutputStream = new FileOutputStream(outPdf)) {
            HtmlConverter.convertToPdf(fileInputStream, fileOutputStream, new ConverterProperties().setBaseUri("%homepath%"));
        }
        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder, "diff10_"));
    }

    @Test
    public void resourceResolverTest11() throws IOException, InterruptedException {
        String outPdf = destinationFolder + "resourceResolverTest11.pdf";
        String cmpPdf = sourceFolder + "cmp_resourceResolverTest11.pdf";
        try (FileInputStream fileInputStream = new FileInputStream(sourceFolder + "resourceResolverTest11.html");
             FileOutputStream fileOutputStream = new FileOutputStream(outPdf)) {
            HtmlConverter.convertToPdf(fileInputStream, fileOutputStream, new ConverterProperties().setBaseUri("https://en.wikipedia.org/wiki/Welsh_Corgi"));
        }
        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder, "diff11_"));
    }

    @Test
    @Ignore("DEVSIX-1668")
    public void resourceResolverTest12() throws IOException, InterruptedException {
        String baseUri = sourceFolder + "path with spaces";

        String outPdf = destinationFolder + "resourceResolverTest12.pdf";
        String cmpPdf = sourceFolder + "cmp_resourceResolverTest12.pdf";
        try (FileInputStream fileInputStream = new FileInputStream(sourceFolder + "resourceResolverTest12.html");
             FileOutputStream fileOutputStream = new FileOutputStream(outPdf)) {
            HtmlConverter.convertToPdf(fileInputStream, fileOutputStream, new ConverterProperties().setBaseUri(baseUri));
        }
        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder, "diff12_"));
    }

    @Test
    public void resourceResolverTest13() throws IOException, InterruptedException {
        String baseUri = sourceFolder;

        String outPdf = destinationFolder + "resourceResolverTest13.pdf";
        String cmpPdf = sourceFolder + "cmp_resourceResolverTest13.pdf";
        try (FileInputStream fileInputStream = new FileInputStream(sourceFolder + "resourceResolverTest13.html");
             FileOutputStream fileOutputStream = new FileOutputStream(outPdf)) {
            HtmlConverter.convertToPdf(fileInputStream, fileOutputStream, new ConverterProperties().setBaseUri(baseUri));
        }
        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder, "diff13_"));
    }

    @Test
    @Ignore("DEVSIX-1668")
    public void resourceResolverTest14() throws IOException, InterruptedException {
        String baseUri = sourceFolder + "path%20with%20spaces";

        String outPdf = destinationFolder + "resourceResolverTest14.pdf";
        String cmpPdf = sourceFolder + "cmp_resourceResolverTest14.pdf";
        try (FileInputStream fileInputStream = new FileInputStream(sourceFolder + "resourceResolverTest12.html");
             FileOutputStream fileOutputStream = new FileOutputStream(outPdf)) {
            HtmlConverter.convertToPdf(fileInputStream, fileOutputStream, new ConverterProperties().setBaseUri(baseUri));
        }
        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder, "diff14_"));
    }

    @Test
    public void resourceResolverTest15() throws IOException, InterruptedException {
        String baseUri = sourceFolder;

        String outPdf = destinationFolder + "resourceResolverTest15.pdf";
        String cmpPdf = sourceFolder + "cmp_resourceResolverTest15.pdf";
        try (FileInputStream fileInputStream = new FileInputStream(sourceFolder + "resourceResolverTest15.html");
             FileOutputStream fileOutputStream = new FileOutputStream(outPdf)) {
            HtmlConverter.convertToPdf(fileInputStream, fileOutputStream, new ConverterProperties().setBaseUri(baseUri));
        }
        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder, "diff15_"));
    }

    // TODO test with absolute http links for resources?
    // TODO test with http base URI?
}
