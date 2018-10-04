package com.itextpdf.html2pdf.resolver.resource;

import com.itextpdf.html2pdf.HtmlConverter;
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
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class LocalImageResolverReleaseTest extends ExtendedITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/resolver/resource/LocalImageResolverReleaseTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/resolver/resource/LocalImageResolverReleaseTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(destinationFolder);
    }

    @Test
    @LogMessages(messages = {@LogMessage(messageTemplate = LogMessageConstant.ERROR_RESOLVING_PARENT_STYLES, count = 60)})
    public void testThatSvgIsReleasedAfterConversion() throws IOException {
        String htmlFileName = "testWithSvg.html";
        String svgFileName = "imageWithMultipleShapes.svg";
        String imageFileName = "image.png";
        String sourceHtmlFile = sourceFolder + htmlFileName;
        String sourceSvgFile = sourceFolder + svgFileName;
        String sourceImageFile = sourceFolder + imageFileName;

        String workDir = destinationFolder + "work/";
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

}
