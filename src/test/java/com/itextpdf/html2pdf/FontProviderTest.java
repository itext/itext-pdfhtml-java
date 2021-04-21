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
package com.itextpdf.html2pdf;

import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.io.LogMessageConstant;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.layout.font.FontProvider;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import com.itextpdf.test.annotations.type.IntegrationTest;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.IOException;
import org.junit.rules.ExpectedException;

// Actually the results are invalid because there is no pdfCalligraph.
@Category(IntegrationTest.class)
public class FontProviderTest extends ExtendedITextTest {
    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/FontProviderTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/FontProviderTest/";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = LogMessageConstant.TYPOGRAPHY_NOT_FOUND, count = 4)
    })
    public void hebrewTest() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "hebrew.html"), new File(destinationFolder + "hebrew.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "hebrew.pdf", sourceFolder + "cmp_hebrew.pdf", destinationFolder, "diffHebrew_"));
    }

    @Test
    public void devanagariTest() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "devanagari.html"), new File(destinationFolder + "devanagari.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "devanagari.pdf", sourceFolder + "cmp_devanagari.pdf", destinationFolder, "diffDevanagari_"));
    }

    @Test
    //For more specific tests see FontSelectorTimesFontTest in html2pdf and FontSelectorHelveticaFontTest in html2pdf-private
    public void convertStandardFonts() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "convertStandardFonts.html"), new File(destinationFolder + "convertStandardFonts.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "convertStandardFonts.pdf", sourceFolder + "cmp_convertStandardFonts", destinationFolder, "difffontstand_"));
    }

    @Test
    public void notoSansMonoItalicTest() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "notoSansMonoItalic.html"), new File(destinationFolder + "notoSansMonoItalic.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "notoSansMonoItalic.pdf", sourceFolder + "cmp_notoSansMonoItalic.pdf", destinationFolder, "diffnotoSansMonoItalic_"));

    }

    @Test
    public void notoSansMonoBoldItalicTest() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(sourceFolder + "notoSansMonoBoldItalic.html"), new File(destinationFolder + "notoSansMonoBoldItalic.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "notoSansMonoBoldItalic.pdf", sourceFolder + "cmp_notoSansMonoBoldItalic.pdf", destinationFolder, "diffnotoSansMonoBoldItalic_"));

    }

    @Test
    // TODO: DEVSIX-4017 (Combination of default and pdfCalligraph fonts with italic style and
    // '"courier new", courier, monospace' family reproduces comparator exception.
    public void comparatorErrorTest() throws IOException, InterruptedException {
        ConverterProperties properties = new ConverterProperties();

        FontProvider pro = new DefaultFontProvider();
        pro.addFont(FontProgramFactory.createFont(sourceFolder + "NotoSansArabic-Regular.ttf"));
        pro.addFont(FontProgramFactory.createFont(sourceFolder + "NotoSansArabic-Bold.ttf"));
        pro.addFont(FontProgramFactory.createFont(sourceFolder + "NotoSansGurmukhi-Regular.ttf"));
        pro.addFont(FontProgramFactory.createFont(sourceFolder + "NotoSansGurmukhi-Bold.ttf"));
        pro.addFont(FontProgramFactory.createFont(sourceFolder + "NotoSansMyanmar-Regular.ttf"));
        pro.addFont(FontProgramFactory.createFont(sourceFolder + "NotoSansMyanmar-Bold.ttf"));
        pro.addFont(FontProgramFactory.createFont(sourceFolder + "NotoSansOriya-Regular.ttf"));
        pro.addFont(FontProgramFactory.createFont(sourceFolder + "NotoSansOriya-Bold.ttf"));
        pro.addFont(FontProgramFactory.createFont(sourceFolder + "NotoSerifBengali-Regular.ttf"));
        pro.addFont(FontProgramFactory.createFont(sourceFolder + "NotoSerifBengali-Bold.ttf"));
        pro.addFont(FontProgramFactory.createFont(sourceFolder + "NotoSerifDevanagari-Regular.ttf"));
        pro.addFont(FontProgramFactory.createFont(sourceFolder + "NotoSerifDevanagari-Bold.ttf"));
        pro.addFont(FontProgramFactory.createFont(sourceFolder + "NotoSerifGujarati-Regular.ttf"));
        pro.addFont(FontProgramFactory.createFont(sourceFolder + "NotoSerifGujarati-Bold.ttf"));
        pro.addFont(FontProgramFactory.createFont(sourceFolder + "NotoSerifHebrew-Regular.ttf"));
        pro.addFont(FontProgramFactory.createFont(sourceFolder + "NotoSerifHebrew-Bold.ttf"));
        pro.addFont(FontProgramFactory.createFont(sourceFolder + "NotoSerifKannada-Regular.ttf"));
        pro.addFont(FontProgramFactory.createFont(sourceFolder + "NotoSerifKannada-Bold.ttf"));
        pro.addFont(FontProgramFactory.createFont(sourceFolder + "NotoSerifKhmer-Regular.ttf"));
        pro.addFont(FontProgramFactory.createFont(sourceFolder + "NotoSerifKhmer-Bold.ttf"));
        pro.addFont(FontProgramFactory.createFont(sourceFolder + "NotoSerifMalayalam-Regular.ttf"));
        pro.addFont(FontProgramFactory.createFont(sourceFolder + "NotoSerifMalayalam-Bold.ttf"));
        pro.addFont(FontProgramFactory.createFont(sourceFolder + "NotoSerifMyanmar-Regular.ttf"));
        pro.addFont(FontProgramFactory.createFont(sourceFolder + "NotoSerifMyanmar-Bold.ttf"));
        pro.addFont(FontProgramFactory.createFont(sourceFolder + "NotoSerifTamil-Regular.ttf"));
        pro.addFont(FontProgramFactory.createFont(sourceFolder + "NotoSerifTamil-Bold.ttf"));
        pro.addFont(FontProgramFactory.createFont(sourceFolder + "NotoSerifTelugu-Regular.ttf"));
        pro.addFont(FontProgramFactory.createFont(sourceFolder + "NotoSerifTelugu-Bold.ttf"));
        pro.addFont(FontProgramFactory.createFont(sourceFolder + "NotoSerifThai-Regular.ttf"));
        pro.addFont(FontProgramFactory.createFont(sourceFolder + "NotoSerifThai-Bold.ttf"));

        properties.setFontProvider(pro);

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Comparison method violates its general contract!");
        HtmlConverter.convertToPdf(new File(sourceFolder + "comparatorError.html"),
                new File(destinationFolder + "comparatorError.pdf"), properties);

        Assert.assertNull(new CompareTool().compareByContent(destinationFolder + "comparatorError.pdf",
                sourceFolder + "cmp_comparatorError.pdf", destinationFolder));
    }
}
