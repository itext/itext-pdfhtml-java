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

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.LogMessageConstant;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.File;
import java.io.IOException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class BackgroundTest extends ExtendedHtmlConversionITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/BackgroundTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/BackgroundTest/";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void backgroundSizeTest01() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "backgroundsize01.html"),
                new File(destinationFolder + "backgroundsize01.pdf"));
        Assert.assertNull(new CompareTool()
                .compareByContent(destinationFolder + "backgroundsize01.pdf", sourceFolder + "cmp_backgroundsize01.pdf",
                        destinationFolder));
    }

    @Test
    public void backgroundAttachmentMarginRoot1Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundAttachmentMarginRoot1", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundAttachmentMarginRoot2Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundAttachmentMarginRoot2", sourceFolder, destinationFolder);
    }

    @Test
    // TODO DEVSIX-4445 support display: contents
    public void backgroundColorBodyDisplayContentsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundColorBodyDisplayContents", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundMarginHtmlTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundMarginHtml", sourceFolder, destinationFolder);
    }

    @Test
    // TODO DEVSIX-4426 support rotateZ() - remove log message after fixing
    @LogMessages(messages = {
            @LogMessage(messageTemplate = LogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION)})
    public void backgroundTransformedRootTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundTransformedRoot", sourceFolder, destinationFolder);
    }

    @Test
    // TODO DEVSIX-4448 support will-change CSS property
    public void backgroundWillChangeRootTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundWillChangeRoot", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundSoloImageTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("background_solo_image", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundImageWithoutFixedWidthTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundImageWithoutFixedSize", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundImageCoverSizeTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundImageCoverSize", sourceFolder, destinationFolder);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = com.itextpdf.styledxmlparser.LogMessageConstant.ONLY_THE_LAST_BACKGROUND_CAN_INCLUDE_BACKGROUND_COLOR)
    })
    public void backgroundImageAndColorNotLastTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("background_image_and_color_not_last", sourceFolder, destinationFolder);
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = LogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION))
    public void backgroundImageAndColorsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("background_image_and_colors", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundSoloImageWithNoRepeatTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("background_solo_image_with_no_repeat", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundSoloImageWithNoRepeatAndColorTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("background_solo_image_with_no_repeat_and_color", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundMultiImageTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("background_multi_image", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundTransparentAndNotTransparentImagesTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundTransparentAndNotTransparentImages", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundTwoTransparentImagesAndColorTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundTwoTransparentImagesAndColor", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundPositionTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundPosition", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundGradientPositionTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundGradientPosition", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundGradientWithPercentagePositionTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundGradientWithPercentagePosition", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundPositionWithoutYTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundPositionWithoutY", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundPositionXTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundPositionX", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundPositionYTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundPositionY", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundPositionXYTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundPositionXY", sourceFolder, destinationFolder);
    }

    @Test
    // html works inappropriate in chrome.
    public void backgroundComplicatedPositionXYTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundComplicatedPositionXY", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundPositionXAndPositionTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundPositionXAndPosition", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundPositionAndPositionXTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundPositionAndPositionX", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundPositionInheritAndInitialTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundPositionInheritAndInitial", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundShorthandOnlyImageTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundShorthandOnlyImage", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundShorthandImageRepeatAndColorTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundShorthandImageRepeatAndColor", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundShorthandTwoImageWithRepeatAndColorTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundShorthandTwoImageWithRepeatAndColor", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundShorthandThreeImagesWithOneRepeatTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundShorthandThreeImagesWithOneRepeat", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundShorthandAndPropertyTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundShorthandAndProperty", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundPropertyAndShorthandTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("backgroundPropertyAndShorthand", sourceFolder, destinationFolder);
    }

    @Test
    public void backgroundSvgTest() throws IOException, InterruptedException {
        String testName = "backgroundSvgTest";
        HtmlConverter.convertToPdf(new File(sourceFolder + testName + ".xht"),
                new File(destinationFolder + testName + ".pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + testName + ".pdf",
                sourceFolder + "cmp_" + testName + ".pdf", destinationFolder));
    }

    @Test
    public void multipleBackgroundRepeatMissedTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("multipleBackgroundRepeatMissed", sourceFolder, destinationFolder);
    }

    @Test
    public void clipOriginTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("clipOrigin", sourceFolder, destinationFolder);
    }

    @Test
    public void clipOriginGradientTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("clipOriginGradient", sourceFolder, destinationFolder);
    }

    @Test
    public void clipOriginShorthandTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("clipOriginShorthand", sourceFolder, destinationFolder);
    }

    @Test
    public void clipOriginLatentImageTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("clipOriginLatentImage", sourceFolder, destinationFolder);
    }

    @Test
    public void clipOriginRepeatXTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("clipOriginRepeatX", sourceFolder, destinationFolder);
    }

    @Test
    public void clipOriginLatentImageRepeatXTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("clipOriginLatentImageRepeatX", sourceFolder, destinationFolder);
    }

    @Test
    public void clipOriginPositionOnePercentageValueTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("clipOriginPositionOnePercentageValue", sourceFolder, destinationFolder);
    }

    @Test
    public void clipOriginSizeCoverTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("clipOriginSizeCover", sourceFolder, destinationFolder);
    }

    @Test
    public void clipOriginRepeatSpaceTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("clipOriginRepeatSpace", sourceFolder, destinationFolder);
    }
    @Test
    public void clipOriginRepeatRoundTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("clipOriginRepeatRound", sourceFolder, destinationFolder);
    }

}
