/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2020 iText Group NV
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

import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.resolver.resource.HtmlResourceResolver;
import com.itextpdf.kernel.pdf.xobject.PdfXObject;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.svg.exceptions.SvgLogMessageConstant;
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

@Category(IntegrationTest.class)
public class ResourceResolverTest extends ExtendedITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/ResourceResolverTest/";

    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/ResourceResolverTest/";

    private final String bLogoCorruptedData = "data:image/png;base64,,,iVBORw0KGgoAAAANSUhEUgAAAVoAAAAxCAMAAACsy5FpAAAABGdBTUEAALGPC/xhBQAAAAFzUkdCAK7OHOkAAAAqUExURQAAAPicJAdJdQdJdQdJdficJjBUbPicJgdJdQdJdficJficJQdJdficJlrFe50AAAAMdFJOUwCBe8I/Phe+65/saIJg0K4AAAMOSURBVHja7ZvbmqsgDIU5Bo/v/7q7/WZXsQYNuGy1muuZFH7DIiSglFLU6pZUbGQQNvXpNcC4caoNRvNxOuDUdf80HXk3VYewKp516DHWxuOc/0ye/U00duAwU+/qkWzfh9F9hzIHJxuzNa+fsa4I7Ihx+H+qUFN/sKVhzP7lH+a+qwY1gJHtmwFDPBHK1wLLjLOGTb2jIWhHScAF7RgOGod2CAGTFB8J2JodJ3Dq5kNow95oH3BdtsjGHE6LVu+P9iG5UlVwNjXOndGeRWuZEBBJLtWcMMK11nFoDfDL4TOEMUu0K/leIpNNpUrYFVsrDi2Mbb1DXqv5PV4quWzKHikJKq99utTsoI1dsMjBkr2dctoAMO3XQS2ogrNrJ5vH1OvtU6/ddIPR0k1g9K++bcSKo6Htf8wbdxpK2rnRigJRqAU3WiEylzzVlubCF0TLb/pTyZXH9o1WoKLVoKK8yBbUHS6IdjksZYpxo82WXIzIXhptYtmDRPbQaDXiPBZaaQl26ZBI6pfQ+gZ00A3CxkH6COo2rIwjom12KM/IJRehBUdF2wLrtUWS+56P/Q7aPUrheYnYRpE9LtrwSbSp7cxuJnv1qCWzk9AeEy3t0MAp2ccq93NogWHry3QWowqHPDK0mPSr8aXZAWQzO+hB17ebb9P5ZbDCu2obJPeiNQQWbAUse10VbbKqSLm9yRutQGT/8wO0G6+LdvV2Aaq0eDW0kmI3SHKvhZZkESnoTd5o5SIr+gb0A2g9wGQi67KUw5wdLajNEHymyCqo5B4RLawWHp10XcEC528suBOjJVwDZ2iOca9lBNsSl4jZE6Ntd6jXmtKVzeiIOy/aDzwTydmPZpJrzov2A89EsrKod8mVoq1y0LbsE02Zf/sVQSAObXa5ZSq5UkGoZw9LlqwRNkai5ZT7rRXyHkJgQqioSBipgjhGHPdMYy3hbLx8UDbDPTatndyeeW1HpaXtodxYyUO+zmoDUWjeUnHRB7d5E/KQnazRs0VdbWjI/EluloPnb26+KXIGI+e+7CBt/wAetDeCKwxY6QAAAABJRU5ErkJggg==";

    private final String bLogo = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAVoAAAAxCAMAAACsy5FpAAAABGdBTUEAALGPC/xhBQAAAAFzUkdCAK7OHOkAAAAqUExURQAAAPicJAdJdQdJdQdJdficJjBUbPicJgdJdQdJdficJficJQdJdficJlrFe50AAAAMdFJOUwCBe8I/Phe+65/saIJg0K4AAAMOSURBVHja7ZvbmqsgDIU5Bo/v/7q7/WZXsQYNuGy1muuZFH7DIiSglFLU6pZUbGQQNvXpNcC4caoNRvNxOuDUdf80HXk3VYewKp516DHWxuOc/0ye/U00duAwU+/qkWzfh9F9hzIHJxuzNa+fsa4I7Ihx+H+qUFN/sKVhzP7lH+a+qwY1gJHtmwFDPBHK1wLLjLOGTb2jIWhHScAF7RgOGod2CAGTFB8J2JodJ3Dq5kNow95oH3BdtsjGHE6LVu+P9iG5UlVwNjXOndGeRWuZEBBJLtWcMMK11nFoDfDL4TOEMUu0K/leIpNNpUrYFVsrDi2Mbb1DXqv5PV4quWzKHikJKq99utTsoI1dsMjBkr2dctoAMO3XQS2ogrNrJ5vH1OvtU6/ddIPR0k1g9K++bcSKo6Htf8wbdxpK2rnRigJRqAU3WiEylzzVlubCF0TLb/pTyZXH9o1WoKLVoKK8yBbUHS6IdjksZYpxo82WXIzIXhptYtmDRPbQaDXiPBZaaQl26ZBI6pfQ+gZ00A3CxkH6COo2rIwjom12KM/IJRehBUdF2wLrtUWS+56P/Q7aPUrheYnYRpE9LtrwSbSp7cxuJnv1qCWzk9AeEy3t0MAp2ccq93NogWHry3QWowqHPDK0mPSr8aXZAWQzO+hB17ebb9P5ZbDCu2obJPeiNQQWbAUse10VbbKqSLm9yRutQGT/8wO0G6+LdvV2Aaq0eDW0kmI3SHKvhZZkESnoTd5o5SIr+gb0A2g9wGQi67KUw5wdLajNEHymyCqo5B4RLawWHp10XcEC528suBOjJVwDZ2iOca9lBNsSl4jZE6Ntd6jXmtKVzeiIOy/aDzwTydmPZpJrzov2A89EsrKod8mVoq1y0LbsE02Zf/sVQSAObXa5ZSq5UkGoZw9LlqwRNkai5ZT7rRXyHkJgQqioSBipgjhGHPdMYy3hbLx8UDbDPTatndyeeW1HpaXtodxYyUO+zmoDUWjeUnHRB7d5E/KQnazRs0VdbWjI/EluloPnb26+KXIGI+e+7CBt/wAetDeCKwxY6QAAAABJRU5ErkJggg==";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(destinationFolder);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = LogMessageConstant.UNABLE_TO_PROCESS_EXTERNAL_CSS_FILE, count = 1),
            @LogMessage(messageTemplate = LogMessageConstant.UNABLE_TO_RETRIEVE_IMAGE_WITH_GIVEN_BASE_URI, count = 1),
            @LogMessage(messageTemplate = LogMessageConstant.WORKER_UNABLE_TO_PROCESS_OTHER_WORKER, count = 1)})
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
    @LogMessages(messages = @LogMessage(messageTemplate = LogMessageConstant.NO_WORKER_FOUND_FOR_TAG, count = 1))
    public void resourceResolverTest07A() throws IOException, InterruptedException {
        String baseUri = sourceFolder + "%23r%e%2525s@o%25urces/";

        String outPdf = destinationFolder + "resourceResolverTest07A.pdf";
        String cmpPdf = sourceFolder + "cmp_resourceResolverTest07A.pdf";
        try (FileInputStream fileInputStream = new FileInputStream(sourceFolder + "resourceResolverTest07A.html");
             FileOutputStream fileOutputStream = new FileOutputStream(outPdf)) {
            HtmlConverter.convertToPdf(fileInputStream, fileOutputStream, new ConverterProperties().setBaseUri(baseUri));
        }
        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder, "diff07A_"));
    }

    @Test
    public void resourceResolverTest07B() throws IOException, InterruptedException {
        String outPdf = destinationFolder + "resourceResolverTest07B.pdf";
        String cmpPdf = sourceFolder + "cmp_resourceResolverTest07B.pdf";
        HtmlConverter.convertToPdf(new File(sourceFolder + "#r%e%25s@o%urces/resourceResolverTest07B.html"), new File(outPdf));
        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder, "diff07B_"));
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = LogMessageConstant.NO_WORKER_FOUND_FOR_TAG, count = 1))
    public void resourceResolverTest07C() throws IOException, InterruptedException {
        String outPdf = destinationFolder + "resourceResolverTest07C.pdf";
        String cmpPdf = sourceFolder + "cmp_resourceResolverTest07C.pdf";
        HtmlConverter.convertToPdf(new File(sourceFolder + "#r%e%25s@o%urces/resourceResolverTest07C.html"), new File(outPdf),
                new ConverterProperties().setBaseUri(sourceFolder + "#r%e%25s@o%urces/.."));
        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder, "diff07C_"));
    }

    @Test
    public void resourceResolverTest09() throws IOException, InterruptedException {
        String outPdf = destinationFolder + "resourceResolverTest09.pdf";
        String cmpPdf = sourceFolder + "cmp_resourceResolverTest09.pdf";
        HtmlConverter.convertToPdf(new File(sourceFolder + "resourceResolverTest09.html"), new File(outPdf));
        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder, "diff09_"));
    }

    @Test
    public void resourceResolverHtmlWithSvgTest01() throws IOException, InterruptedException {
        String outPdf = destinationFolder + "resourceResolverHtmlWithSvgTest01.pdf";
        String cmpPdf = sourceFolder + "cmp_resourceResolverHtmlWithSvgTest01.pdf";
        HtmlConverter.convertToPdf(new File(sourceFolder + "resourceResolverHtmlWithSvgTest01.html"), new File(outPdf));
        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder, "diff01_"));
    }

    @Test
    @LogMessages(messages = {
          @LogMessage(messageTemplate = LogMessageConstant.WORKER_UNABLE_TO_PROCESS_OTHER_WORKER, count = 2),
          @LogMessage(messageTemplate = LogMessageConstant.UNABLE_TO_RETRIEVE_IMAGE_WITH_GIVEN_BASE_URI, count = 2)
     })
    public void resourceResolverHtmlWithSvgTest02() throws IOException, InterruptedException {
        String baseUri = sourceFolder + "%23r%e%2525s@o%25urces/";

        String outPdf = destinationFolder + "resourceResolverHtmlWithSvgTest02.pdf";
        String cmpPdf = sourceFolder + "cmp_resourceResolverHtmlWithSvgTest02.pdf";
        try (FileInputStream fileInputStream = new FileInputStream(sourceFolder + "resourceResolverHtmlWithSvgTest02.html");
             FileOutputStream fileOutputStream = new FileOutputStream(outPdf)) {
            HtmlConverter.convertToPdf(fileInputStream, fileOutputStream, new ConverterProperties().setBaseUri(baseUri));
        }
        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder, "diff02_"));
    }

    @Test
    public void resourceResolverHtmlWithSvgTest03() throws IOException, InterruptedException {
        String baseUri = sourceFolder + "%23r%e%2525s@o%25urces/";

        String outPdf = destinationFolder + "resourceResolverHtmlWithSvgTest03.pdf";
        String cmpPdf = sourceFolder + "cmp_resourceResolverHtmlWithSvgTest03.pdf";
        try (FileInputStream fileInputStream = new FileInputStream(sourceFolder + "resourceResolverHtmlWithSvgTest03.html");
             FileOutputStream fileOutputStream = new FileOutputStream(outPdf)) {
            HtmlConverter.convertToPdf(fileInputStream, fileOutputStream, new ConverterProperties().setBaseUri(baseUri));
        }
        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder, "diff03_"));
    }

    @Test
    public void resourceResolverHtmlWithSvgTest04() throws IOException, InterruptedException {
        String baseUri = sourceFolder;

        String outPdf = destinationFolder + "resourceResolverHtmlWithSvgTest04.pdf";
        String cmpPdf = sourceFolder + "cmp_resourceResolverHtmlWithSvgTest04.pdf";
        try (FileInputStream fileInputStream = new FileInputStream(sourceFolder + "resourceResolverHtmlWithSvgTest04.html");
             FileOutputStream fileOutputStream = new FileOutputStream(outPdf)) {
            HtmlConverter.convertToPdf(fileInputStream, fileOutputStream, new ConverterProperties().setBaseUri(baseUri));
        }
        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder, "diff04_"));
    }

    @Test
    //TODO: update after DEVSIX-2239 fix
    public void resourceResolverCssWithSvg() throws IOException, InterruptedException {
        String outPdf = destinationFolder + "resourceResolverCssWithSvg.pdf";
        String cmpPdf = sourceFolder + "cmp_resourceResolverCssWithSvg.pdf";
        HtmlConverter.convertToPdf(new File(sourceFolder + "resourceResolverCssWithSvg.html"), new File(outPdf));
        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder, "diffCss_"));
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = com.itextpdf.styledxmlparser.LogMessageConstant.UNABLE_TO_RETRIEVE_IMAGE_WITH_GIVEN_BASE_URI),
            @LogMessage(messageTemplate = com.itextpdf.styledxmlparser.LogMessageConstant.UNABLE_TO_RETRIEVE_STREAM_WITH_GIVEN_BASE_URI),
            @LogMessage(messageTemplate = LogMessageConstant.WORKER_UNABLE_TO_PROCESS_OTHER_WORKER, count = 2)
    })
    public void resourceResolverHtmlWithSvgDifferentLevels() throws IOException, InterruptedException {
        String baseUri = sourceFolder;

        String outPdf = destinationFolder + "resourceResolverHtmlWithSvgDifferentLevels.pdf";
        String cmpPdf = sourceFolder + "cmp_resourceResolverHtmlWithSvgDifferentLevels.pdf";
        try (FileInputStream fileInputStream = new FileInputStream(sourceFolder + "resourceResolverHtmlWithSvgDifferentLevels.html");
             FileOutputStream fileOutputStream = new FileOutputStream(outPdf)) {
            HtmlConverter.convertToPdf(fileInputStream, fileOutputStream, new ConverterProperties().setBaseUri(baseUri));
        }
        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder, "diffsvgLevels_"));
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
    // TODO DEVSIX-1595
    @LogMessages(messages = @LogMessage(messageTemplate = LogMessageConstant.NO_WORKER_FOUND_FOR_TAG, count = 1))
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
    @LogMessages(messages = @LogMessage(messageTemplate = LogMessageConstant.NO_WORKER_FOUND_FOR_TAG, count = 1))
    public void resourceResolverTest12A() throws IOException, InterruptedException {
        String baseUri = sourceFolder + "path%with%spaces/";

        String outPdf = destinationFolder + "resourceResolverTest12A.pdf";
        String cmpPdf = sourceFolder + "cmp_resourceResolverTest12A.pdf";
        try (FileInputStream fileInputStream = new FileInputStream(sourceFolder + "resourceResolverTest12A.html");
             FileOutputStream fileOutputStream = new FileOutputStream(outPdf)) {
            HtmlConverter.convertToPdf(fileInputStream, fileOutputStream, new ConverterProperties().setBaseUri(baseUri));
        }
        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder, "diff12A_"));
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = LogMessageConstant.NO_WORKER_FOUND_FOR_TAG, count = 1))
    public void resourceResolverTest12B() throws IOException, InterruptedException {
        String baseUri = sourceFolder + "path%25with%25spaces/";

        String outPdf = destinationFolder + "resourceResolverTest12B.pdf";
        String cmpPdf = sourceFolder + "cmp_resourceResolverTest12B.pdf";
        try (FileInputStream fileInputStream = new FileInputStream(sourceFolder + "resourceResolverTest12B.html");
             FileOutputStream fileOutputStream = new FileOutputStream(outPdf)) {
            HtmlConverter.convertToPdf(fileInputStream, fileOutputStream, new ConverterProperties().setBaseUri(baseUri));
        }
        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder, "diff12B_"));
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = LogMessageConstant.NO_WORKER_FOUND_FOR_TAG, count = 1))
    public void resourceResolverTest12C() throws IOException, InterruptedException {
        String baseUri = sourceFolder + "path%2525with%2525spaces/";

        String outPdf = destinationFolder + "resourceResolverTest12C.pdf";
        String cmpPdf = sourceFolder + "cmp_resourceResolverTest12C.pdf";
        try (FileInputStream fileInputStream = new FileInputStream(sourceFolder + "resourceResolverTest12C.html");
             FileOutputStream fileOutputStream = new FileOutputStream(outPdf)) {
            HtmlConverter.convertToPdf(fileInputStream, fileOutputStream, new ConverterProperties().setBaseUri(baseUri));
        }
        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder, "diff12C_"));
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = LogMessageConstant.NO_WORKER_FOUND_FOR_TAG, count = 1))
    public void resourceResolverTest12D() throws IOException, InterruptedException {
        String baseUri = sourceFolder + "path with spaces/";

        String outPdf = destinationFolder + "resourceResolverTest12D.pdf";
        String cmpPdf = sourceFolder + "cmp_resourceResolverTest12D.pdf";
        try (FileInputStream fileInputStream = new FileInputStream(sourceFolder + "resourceResolverTest12D.html");
             FileOutputStream fileOutputStream = new FileOutputStream(outPdf)) {
            HtmlConverter.convertToPdf(fileInputStream, fileOutputStream, new ConverterProperties().setBaseUri(baseUri));
        }
        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder, "diff12D_"));
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = LogMessageConstant.NO_WORKER_FOUND_FOR_TAG, count = 1))
    public void resourceResolverTest12E() throws IOException, InterruptedException {
        String baseUri = sourceFolder + "path%20with%20spaces/";

        String outPdf = destinationFolder + "resourceResolverTest12E.pdf";
        String cmpPdf = sourceFolder + "cmp_resourceResolverTest12E.pdf";
        try (FileInputStream fileInputStream = new FileInputStream(sourceFolder + "resourceResolverTest12E.html");
             FileOutputStream fileOutputStream = new FileOutputStream(outPdf)) {
            HtmlConverter.convertToPdf(fileInputStream, fileOutputStream, new ConverterProperties().setBaseUri(baseUri));
        }
        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder, "diff12E_"));
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = LogMessageConstant.NO_WORKER_FOUND_FOR_TAG, count = 1))
    public void resourceResolverTest12F() throws IOException, InterruptedException {
        String baseUri = sourceFolder + "path%2520with%2520spaces/";

        String outPdf = destinationFolder + "resourceResolverTest12F.pdf";
        String cmpPdf = sourceFolder + "cmp_resourceResolverTest12F.pdf";
        try (FileInputStream fileInputStream = new FileInputStream(sourceFolder + "resourceResolverTest12F.html");
             FileOutputStream fileOutputStream = new FileOutputStream(outPdf)) {
            HtmlConverter.convertToPdf(fileInputStream, fileOutputStream, new ConverterProperties().setBaseUri(baseUri));
        }
        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder, "diff12F_"));
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

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = LogMessageConstant.NO_WORKER_FOUND_FOR_TAG, count = 1))
    public void resourceResolverTest16A() throws IOException, InterruptedException {
        String baseUri = sourceFolder + "path/with/spaces/";

        String outPdf = destinationFolder + "resourceResolverTest16A.pdf";
        String cmpPdf = sourceFolder + "cmp_resourceResolverTest16A.pdf";
        try (FileInputStream fileInputStream = new FileInputStream(sourceFolder + "resourceResolverTest16A.html");
             FileOutputStream fileOutputStream = new FileOutputStream(outPdf)) {
            HtmlConverter.convertToPdf(fileInputStream, fileOutputStream, new ConverterProperties().setBaseUri(baseUri));
        }
        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder, "diff16A_"));
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = LogMessageConstant.NO_WORKER_FOUND_FOR_TAG, count = 1))
    public void resourceResolverTest16B() throws IOException, InterruptedException {
        String baseUri = sourceFolder + "path%2Fwith%2Fspaces/";

        String outPdf = destinationFolder + "resourceResolverTest16B.pdf";
        String cmpPdf = sourceFolder + "cmp_resourceResolverTest16B.pdf";
        try (FileInputStream fileInputStream = new FileInputStream(sourceFolder + "resourceResolverTest16B.html");
             FileOutputStream fileOutputStream = new FileOutputStream(outPdf)) {
            HtmlConverter.convertToPdf(fileInputStream, fileOutputStream, new ConverterProperties().setBaseUri(baseUri));
        }
        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder, "diff16B_"));
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = LogMessageConstant.NO_WORKER_FOUND_FOR_TAG, count = 1))
    public void resourceResolverTest16C() throws IOException, InterruptedException {
        String baseUri = sourceFolder + "path%252Fwith%252Fspaces/";

        String outPdf = destinationFolder + "resourceResolverTest16C.pdf";
        String cmpPdf = sourceFolder + "cmp_resourceResolverTest16C.pdf";
        try (FileInputStream fileInputStream = new FileInputStream(sourceFolder + "resourceResolverTest16C.html");
             FileOutputStream fileOutputStream = new FileOutputStream(outPdf)) {
            HtmlConverter.convertToPdf(fileInputStream, fileOutputStream, new ConverterProperties().setBaseUri(baseUri));
        }
        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder, "diff16C_"));
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = LogMessageConstant.NO_WORKER_FOUND_FOR_TAG, count = 1))
    public void resourceResolverTest16D() throws IOException, InterruptedException {
        String baseUri = sourceFolder + "path%25252Fwith%25252Fspaces/";

        String outPdf = destinationFolder + "resourceResolverTest16D.pdf";
        String cmpPdf = sourceFolder + "cmp_resourceResolverTest16D.pdf";
        try (FileInputStream fileInputStream = new FileInputStream(sourceFolder + "resourceResolverTest16D.html");
             FileOutputStream fileOutputStream = new FileOutputStream(outPdf)) {
            HtmlConverter.convertToPdf(fileInputStream, fileOutputStream, new ConverterProperties().setBaseUri(baseUri));
        }
        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder, "diff16D_"));
    }

    @Test
    public void resourceResolverSvgWithImageInlineTest() throws IOException, InterruptedException {
        String baseUri = sourceFolder;
        String outPdf = destinationFolder + "resourceResolverSvgWithImageInline.pdf";
        String cmpPdf = sourceFolder + "cmp_resourceResolverSvgWithImageInline.pdf";
        try (FileInputStream fileInputStream = new FileInputStream(sourceFolder + "resourceResolverSvgWithImageInline.html");
             FileOutputStream fileOutputStream = new FileOutputStream(outPdf)) {
            HtmlConverter.convertToPdf(fileInputStream, fileOutputStream, new ConverterProperties().setBaseUri(baseUri));
        }
        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder, "diffInlineSvg_"));
    }

    @Test
    public void resourceResolverSvgWithImageBackgroundTest() throws IOException, InterruptedException {
        //Browsers do not render this
        String baseUri = sourceFolder;
        String outPdf = destinationFolder + "resourceResolverSvgWithImageBackground.pdf";
        String cmpPdf = sourceFolder + "cmp_resourceResolverSvgWithImageBackground.pdf";
        try (FileInputStream fileInputStream = new FileInputStream(
                sourceFolder + "resourceResolverSvgWithImageBackground.html");
             FileOutputStream fileOutputStream = new FileOutputStream(outPdf)) {
            HtmlConverter.convertToPdf(fileInputStream, fileOutputStream, new ConverterProperties().setBaseUri(baseUri));
        }
        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder, "diffSvgWithImg_"));
    }

    @Test
    public void resourceResolverSvgWithImageObjectTest() throws IOException, InterruptedException {
        String baseUri = sourceFolder;
        String outPdf = destinationFolder + "resourceResolverSvgWithImageObject.pdf";
        String cmpPdf = sourceFolder + "cmp_resourceResolverSvgWithImageObject.pdf";
        try (FileInputStream fileInputStream = new FileInputStream(sourceFolder + "resourceResolverSvgWithImageObject.html");
             FileOutputStream fileOutputStream = new FileOutputStream(outPdf)) {
            HtmlConverter.convertToPdf(fileInputStream, fileOutputStream, new ConverterProperties().setBaseUri(baseUri));
        }
        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder, "diff18_"));
    }

    private HtmlResourceResolver createResolver() {
        ConverterProperties cp = new ConverterProperties();
        cp.setBaseUri(sourceFolder);
        return new HtmlResourceResolver(sourceFolder, new ProcessorContext(cp));
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = com.itextpdf.styledxmlparser.LogMessageConstant.UNABLE_TO_RETRIEVE_IMAGE_WITH_GIVEN_BASE_URI, count = 1))
    public void retrieveImageExtendedNullTest() {
        HtmlResourceResolver resourceResolver = createResolver();
        PdfXObject image = resourceResolver.retrieveImageExtended(null);
        Assert.assertNull(image);
    }

    @Test
    public void retrieveImageExtendedBase64Test() {
        HtmlResourceResolver resourceResolver = createResolver();
        PdfXObject image = resourceResolver.retrieveImageExtended(bLogo);
        Assert.assertNotNull(image);
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = com.itextpdf.styledxmlparser.LogMessageConstant.UNABLE_TO_RETRIEVE_IMAGE_WITH_GIVEN_BASE_URI, count = 1))
    public void retrieveImageExtendedIncorrectBase64Test() {
        HtmlResourceResolver resourceResolver = createResolver();
        PdfXObject image = resourceResolver.retrieveImageExtended(bLogoCorruptedData);
        Assert.assertNull(image);
    }

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = SvgLogMessageConstant.NOROOT, count = 1),
            @LogMessage(messageTemplate = LogMessageConstant.WORKER_UNABLE_TO_PROCESS_OTHER_WORKER, count = 1)})
    public void resourceResolverIncorrectSyntaxTest() throws IOException, InterruptedException {
        String baseUri = sourceFolder;
        String outPdf = destinationFolder + "resourceResolverIncorrectSyntaxObject.pdf";
        String cmpPdf = sourceFolder + "cmp_resourceResolverIncorrectSyntaxObject.pdf";
        try (FileInputStream fileInputStream = new FileInputStream(sourceFolder + "resourceResolverIncorrectSyntaxObject.html");
             FileOutputStream fileOutputStream = new FileOutputStream(outPdf)) {
            HtmlConverter.convertToPdf(fileInputStream, fileOutputStream, new ConverterProperties().setBaseUri(baseUri));
        }
        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder, "diffIncorrectSyntax_"));
    }



    // TODO test with absolute http links for resources?
    // TODO test with http base URI?
}
