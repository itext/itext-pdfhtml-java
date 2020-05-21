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
package com.itextpdf.html2pdf.resolver.resource;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.io.util.FileUtil;
import com.itextpdf.styledxmlparser.LogMessageConstant;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class ResourceReleaseResolverTest extends ExtendedITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/resolver/resource/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/resolver/resource/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(destinationFolder);
    }

    @Test
    public void testThatSvgIsReleasedAfterConversion() throws IOException {
        String dirName = "LocalImageResolverReleaseTest/";
        String htmlFileName = "testWithSvg.html";
        String svgFileName = "imageWithMultipleShapes.svg";
        String imageFileName = "image.png";
        String sourceHtmlFile = sourceFolder + dirName + htmlFileName;
        String sourceSvgFile = sourceFolder + dirName + svgFileName;
        String sourceImageFile = sourceFolder + dirName + imageFileName;

        String workDir = destinationFolder + dirName;
        createDestinationFolder(workDir);
        String targetPdfFile = workDir + "target.pdf";

        String workDirHtmlFile = workDir + htmlFileName;
        String workDirSvgFile = workDir + svgFileName;
        String workDirImageFile = workDir + imageFileName;
        Files.copy(Paths.get(sourceHtmlFile), Paths.get(workDirHtmlFile));
        Files.copy(Paths.get(sourceSvgFile), Paths.get(workDirSvgFile));
        Files.copy(Paths.get(sourceImageFile), Paths.get(workDirImageFile));
        for (int i = 0; i < 10; i++) {
            HtmlConverter.convertToPdf(new File(workDirHtmlFile), new File(targetPdfFile));
        }

        // The resource must be freed after the conversion
        File resourceToBeRemoved = new File(workDirSvgFile);
        resourceToBeRemoved.delete();
        Assert.assertFalse(resourceToBeRemoved.exists());

        resourceToBeRemoved = new File(workDirImageFile);
        resourceToBeRemoved.delete();
        Assert.assertFalse(resourceToBeRemoved.exists());
    }

    @Test
    public void testThatLocalFontIsReleasedAfterConversion() throws IOException {
        String dirName = "LocalFontIsReleased/";
        String htmlFileName = "localFontIsReleased.html";
        String fontFileName = "NotoSans-Regular.ttf";
        String sourceHtmlFile = sourceFolder + dirName + htmlFileName;
        String sourceFontFile = sourceFolder + dirName + fontFileName;

        String workDir = destinationFolder + dirName;
        createDestinationFolder(workDir);
        String targetPdfFile = workDir + "target.pdf";

        String workDirHtmlFile = workDir + htmlFileName;
        String workDirFontFile = workDir + fontFileName;

        Files.copy(Paths.get(sourceHtmlFile), Paths.get(workDirHtmlFile));
        Files.copy(Paths.get(sourceFontFile), Paths.get(workDirFontFile));

        // The issue cannot be reproduced consistently and automatically with just single conversion for some reason
        // Probably related to some optimizations done by JVM
        // It already reproduces with 2 conversions, but here we do more to get rid of possible JVM even trickier optimizations
        for (int i = 0; i < 5; i++) {
            HtmlConverter.convertToPdf(new File(workDirHtmlFile), new File(targetPdfFile));
        }

        // The resource must be freed after the conversion
        File resourceToBeRemoved = new File(workDirFontFile);
        resourceToBeRemoved.delete();
        Assert.assertFalse(resourceToBeRemoved.exists());
    }

    @Test
    @Ignore("Ignored because currently the font file is not removed and test is failed. Remove this ignore after DEVSIX-3199 is fixed")
    // TODO unignore after DEVSIX-3199 is fixed
    public void testThatAddedFontIsReleasedAfterConversion() throws IOException {
        String dirName = "AddedFontIsReleased/";
        String htmlFileName = "addedFontIsReleased.html";
        String fontFileName = "NotoSans-Regular.ttf";
        String sourceHtmlFile = sourceFolder + dirName + htmlFileName;
        String sourceFontFile = sourceFolder + dirName + fontFileName;

        String workDir = destinationFolder + dirName;
        createDestinationFolder(workDir);
        String targetPdfFile = workDir + "target.pdf";

        String workDirFontFile = workDir + fontFileName;
        Files.copy(Paths.get(sourceFontFile), Paths.get(workDirFontFile));

        DefaultFontProvider fontProvider = new DefaultFontProvider(true, false, false);
        fontProvider.addDirectory(workDir);

        ConverterProperties properties = new ConverterProperties()
                .setBaseUri(sourceFolder)
                .setFontProvider(fontProvider);
        HtmlConverter.convertToPdf(new File(sourceHtmlFile), new File(targetPdfFile), properties);

        File resourceToBeRemoved = new File(workDirFontFile);
        FileUtil.deleteFile(resourceToBeRemoved);

        Assert.assertFalse(resourceToBeRemoved.exists());
    }

    @Test
    public void testThatCssIsReleasedAfterConversion() throws IOException {
        String dirName = "CssIsReleased/";
        String htmlFileName = "cssIsReleased.html";
        String cssFileName = "cssIsReleased.css";
        String sourceHtmlFile = sourceFolder + dirName + htmlFileName;
        String sourceCssFile = sourceFolder + dirName + cssFileName;

        String workDir = destinationFolder + dirName;
        createDestinationFolder(workDir);
        String targetPdfFile = workDir + "target.pdf";

        String workDirHtmlFile = workDir + htmlFileName;
        String workDirCssFile = workDir + cssFileName;

        Files.copy(Paths.get(sourceHtmlFile), Paths.get(workDirHtmlFile));
        Files.copy(Paths.get(sourceCssFile), Paths.get(workDirCssFile));

        ConverterProperties properties = new ConverterProperties().setBaseUri(workDir);
        HtmlConverter.convertToPdf(new File(workDirHtmlFile), new File(targetPdfFile), properties);

        File resourceToBeRemoved = new File(workDirCssFile);
        FileUtil.deleteFile(resourceToBeRemoved);

        Assert.assertFalse(resourceToBeRemoved.exists());
    }
}
