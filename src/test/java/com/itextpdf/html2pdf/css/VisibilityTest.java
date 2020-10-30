/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2021 iText Group NV
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
package com.itextpdf.html2pdf.css;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import com.itextpdf.test.annotations.type.IntegrationTest;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.IOException;

@Category(IntegrationTest.class)
public class VisibilityTest extends ExtendedHtmlConversionITextTest {
    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/VisibilityTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/VisibilityTest/";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    //TODO update cmp-file after DEVSIX-2090 done
    public void visiblePropertyLastPageTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("visiblePropertyLastPageTest", sourceFolder, destinationFolder);
    }

    @Test
    //TODO update cmp-file after DEVSIX-2090 done
    public void visiblePropertyTableTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("visiblePropertyTableTest", sourceFolder, destinationFolder);
    }

    @Test
    //TODO update cmp-file after DEVSIX-2090 done
    public void visiblePropertySvgTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("visiblePropertySvgTest", sourceFolder, destinationFolder);
    }

    @Test
    //TODO update cmp-file after DEVSIX-2090 done
    public void visiblePropertyLinkTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("visiblePropertyLinkTest", sourceFolder, destinationFolder);
    }

    @Test
    //TODO update cmp-file after DEVSIX-2090 done
    public void visiblePropertyImagesTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("visiblePropertyImagesTest", sourceFolder, destinationFolder);
    }

    @Test
    //TODO update cmp-file after DEVSIX-2090 done
    public void visiblePropertyInFormsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("visiblePropertyInFormsTest", sourceFolder, destinationFolder);
    }

    @Test
    //TODO update cmp-file after DEVSIX-2090 done
    public void visiblePropertyInFormFieldTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("visiblePropertyInFormFieldTest", sourceFolder, destinationFolder);
    }

    @Test
    //TODO update cmp-file after DEVSIX-2090 done
    public void visiblePropertyInFormRadioButtonTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("visiblePropertyInFormRadioButtonTest", sourceFolder, destinationFolder);
    }

    @Test
    @LogMessages(messages = {@LogMessage
            (messageTemplate = Html2PdfLogMessageConstant.ACROFORM_NOT_SUPPORTED_FOR_SELECT)})
    //TODO update cmp-file after DEVSIX-2090 and DEVSIX-1901 done
    public void visiblePropertyInFormDropdownListTest() throws IOException, InterruptedException {
        String htmlFile = sourceFolder + "visiblePropertyInFormDropdownListTest.html";
        String outAcroPdf = destinationFolder + "visiblePropertyInFormDropdownListTest.pdf";

        ConverterProperties properties = new ConverterProperties();
        properties.setCreateAcroForm(true);
        HtmlConverter.convertToPdf(new File(htmlFile), new File(outAcroPdf), properties);
        Assert.assertNull(new CompareTool().compareByContent(outAcroPdf, sourceFolder +
                "cmp_visiblePropertyInFormDropdownListTest.pdf", destinationFolder, "diff_dropdown"));
    }

    @Test
    //TODO update cmp-file after DEVSIX-2090 done
    public void visiblePropertyInFormCheckBoxTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("visiblePropertyInFormCheckBoxTest", sourceFolder, destinationFolder);
    }

    @Test
    //TODO update cmp-file after DEVSIX-2090 done
    public void visiblePropertyDivTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("visiblePropertyDivTest", sourceFolder, destinationFolder);
    }
}
