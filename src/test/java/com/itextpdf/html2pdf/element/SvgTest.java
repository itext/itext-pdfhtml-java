/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2018 iText Group NV
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
package com.itextpdf.html2pdf.element;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.styledxmlparser.LogMessageConstant;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.svg.exceptions.SvgLogMessageConstant;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import com.itextpdf.test.annotations.type.IntegrationTest;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.io.IOException;

import static com.itextpdf.html2pdf.LogMessageConstant.UNABLE_TO_RETRIEVE_FONT;
import static com.itextpdf.html2pdf.LogMessageConstant.UNABLE_TO_RETRIEVE_STREAM_WITH_GIVEN_BASE_URI;

@Category(IntegrationTest.class)
public class SvgTest extends ExtendedITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/element/SvgTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/element/SvgTest/";

    @Rule
    public ExpectedException junitExpectedException = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void InlineSvgTest() throws IOException, InterruptedException {
        String name = "inline_svg";
        HtmlConverter.convertToPdf(new File(sourceFolder + name + ".html"), new File(destinationFolder + name + ".pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + name + ".pdf", sourceFolder + "cmp_" + name + ".pdf", destinationFolder, "diff_" + name + "_"));
    }

    @Test
    public void InlineNestedSvgTest() throws IOException, InterruptedException {
        String name = "inline_nested_svg";
        HtmlConverter.convertToPdf(new File(sourceFolder + name + ".html"), new File(destinationFolder + name + ".pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + name + ".pdf", sourceFolder + "cmp_" + name + ".pdf", destinationFolder, "diff_" + name + "_"));

    }

    @Test
    public void InlineSvgExternalFontRelativeTest() throws IOException, InterruptedException {
        String name = "inline_svg_external_font_relative";
        HtmlConverter.convertToPdf(new File(sourceFolder + name + ".html"), new File(destinationFolder + name + ".pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + name + ".pdf", sourceFolder + "cmp_" + name + ".pdf", destinationFolder, "diff_" + name + "_"));
    }

    @Test
    public void InlineSvgExternalFontUrlTest() throws IOException, InterruptedException {
        // TODO RND-1042 external font loading in SVG via @import
        String name = "inline_svg_external_font_url";
        HtmlConverter.convertToPdf(new File(sourceFolder + name + ".html"), new File(destinationFolder + name + ".pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + name + ".pdf", sourceFolder + "cmp_" + name + ".pdf", destinationFolder, "diff_" + name + "_"));
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = com.itextpdf.styledxmlparser.LogMessageConstant.ERROR_PARSING_COULD_NOT_MAP_NODE),
            @LogMessage(messageTemplate = LogMessageConstant.ERROR_RESOLVING_PARENT_STYLES, count = 4),
    })
    public void externalImageSuccessTest() throws IOException, InterruptedException {
        String name = "external_img";
        HtmlConverter.convertToPdf(new File(sourceFolder + name + ".html"), new File(destinationFolder + name + ".pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + name + ".pdf", sourceFolder + "cmp_" + name + ".pdf", destinationFolder, "diff_" + name + "_"));

    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = LogMessageConstant.UNABLE_TO_RETRIEVE_STREAM_WITH_GIVEN_BASE_URI),
            @LogMessage(messageTemplate = com.itextpdf.html2pdf.LogMessageConstant.WORKER_UNABLE_TO_PROCESS_OTHER_WORKER),
    })
    public void externalImageNonExistentRefTest() throws IOException, InterruptedException {
        String name = "external_img_nonExistentRef";
        HtmlConverter.convertToPdf(new File(sourceFolder + name + ".html"), new File(destinationFolder + name + ".pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + name + ".pdf", sourceFolder + "cmp_" + name + ".pdf", destinationFolder, "diff_" + name + "_"));

    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = com.itextpdf.styledxmlparser.LogMessageConstant.ERROR_PARSING_COULD_NOT_MAP_NODE),
            @LogMessage(messageTemplate = LogMessageConstant.ERROR_RESOLVING_PARENT_STYLES, count = 4),
    })
    public void externalObjectSuccessTest() throws IOException, InterruptedException {
        String name = "external_object";
        HtmlConverter.convertToPdf(new File(sourceFolder + name + ".html"), new File(destinationFolder + name + ".pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + name + ".pdf", sourceFolder + "cmp_" + name + ".pdf", destinationFolder, "diff_" + name + "_"));
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = LogMessageConstant.UNABLE_TO_RETRIEVE_STREAM_WITH_GIVEN_BASE_URI),
            @LogMessage(messageTemplate = com.itextpdf.html2pdf.LogMessageConstant.WORKER_UNABLE_TO_PROCESS_OTHER_WORKER),
    })
    public void externalObjectNonExistentRefTest() throws IOException, InterruptedException {
        String name = "external_objectNonExistentRef";
        HtmlConverter.convertToPdf(new File(sourceFolder + name + ".html"), new File(destinationFolder + name + ".pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + name + ".pdf", sourceFolder + "cmp_" + name + ".pdf", destinationFolder, "diff_" + name + "_"));
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = SvgLogMessageConstant.MISSING_WIDTH),
            @LogMessage(messageTemplate = SvgLogMessageConstant.MISSING_HEIGHT),
    })
    public void SvgWithoutDimensionsTest() throws IOException, InterruptedException {
        String name = "svg_without_dimensions";
        HtmlConverter.convertToPdf(new File(sourceFolder + name + ".html"), new File(destinationFolder + name + ".pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + name + ".pdf", sourceFolder + "cmp_" + name + ".pdf", destinationFolder, "diff_" + name + "_"));
    }
    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = SvgLogMessageConstant.MISSING_WIDTH),
            @LogMessage(messageTemplate = SvgLogMessageConstant.MISSING_HEIGHT),
    })
    public void SvgWithoutDimensionsWithViewboxTest() throws IOException, InterruptedException {
        String name = "svg_without_dimensions_with_viewbox";
        HtmlConverter.convertToPdf(new File(sourceFolder + name + ".html"), new File(destinationFolder + name + ".pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + name + ".pdf", sourceFolder + "cmp_" + name + ".pdf", destinationFolder, "diff_" + name + "_"));
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = SvgLogMessageConstant.MISSING_WIDTH, count = 2),
            @LogMessage(messageTemplate = SvgLogMessageConstant.MISSING_HEIGHT, count = 2),
            @LogMessage(messageTemplate = LogMessageConstant.ERROR_RESOLVING_PARENT_STYLES, count=6)
    })
    public void SvgWithoutDimensionsImageAndObjectRef() throws IOException, InterruptedException {
        String name = "SvgWithoutDimensionsImageAndObjectRef";
        HtmlConverter.convertToPdf(new File(sourceFolder + name + ".html"), new File(destinationFolder + name + ".pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + name + ".pdf", sourceFolder + "cmp_" + name + ".pdf", destinationFolder, "diff_" + name + "_"));
    }

}
