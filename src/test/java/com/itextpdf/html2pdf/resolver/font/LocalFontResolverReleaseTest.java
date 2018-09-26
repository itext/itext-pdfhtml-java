package com.itextpdf.html2pdf.resolver.font;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.type.IntegrationTest;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Category(IntegrationTest.class)
public class LocalFontResolverReleaseTest extends ExtendedITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/resolver/font/LocalFontResolverReleaseTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/resolver/font/LocalFontResolverReleaseTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(destinationFolder);
    }

    @Test
    public void testThatLocalFontIsReleasedAfterConversion() throws IOException {
        String htmlFileName = "test.html";
        String fontFileName = "NotoSans-Regular.ttf";
        String sourceHtmlFile = sourceFolder + htmlFileName;
        String sourceFontFile = sourceFolder + fontFileName;

        String workDir = destinationFolder + "work/";
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
        File resourceToBeremoved = new File(workDirFontFile);
        resourceToBeremoved.delete();
        Assert.assertFalse(resourceToBeremoved.exists());
    }

}
