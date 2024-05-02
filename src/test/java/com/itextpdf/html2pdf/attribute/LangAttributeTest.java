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
package com.itextpdf.html2pdf.attribute;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.tagging.StandardRoles;
import com.itextpdf.kernel.pdf.tagutils.TagTreePointer;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

import java.io.FileInputStream;
import java.io.IOException;


@Tag("IntegrationTest")
public class LangAttributeTest extends ExtendedHtmlConversionITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/attribute/LangAttributeTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/attribute/LangAttributeTest/";

    @BeforeAll
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void langAttrInElementForTaggedPdfTest() throws IOException {
        String html = sourceFolder + "langAttrInElementForTaggedPdfTest.html";
        String outFile = destinationFolder + "langAttrInElementForTaggedPdfTest.pdf";
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outFile));
        pdfDocument.setTagged();

        HtmlConverter.convertToPdf(new FileInputStream(html), pdfDocument, null);
        printOutputPdfNameAndDir(outFile);

        PdfDocument document = new PdfDocument(new PdfReader(outFile));
        TagTreePointer tagPointer = new TagTreePointer(document);
        tagPointer.moveToKid(StandardRoles.P);
        Assertions.assertEquals("en", tagPointer.getProperties().getLanguage());
        Assertions.assertNull(document.getCatalog().getLang());

        document.close();
    }

    @Test
    public void langAttrInvalidTagsTest() throws IOException {
        String html = sourceFolder + "langAttrInvalidTagsTest.html";
        String outFile = destinationFolder + "langAttrInvalidTagsTest.pdf";
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outFile));
        pdfDocument.setTagged();

        HtmlConverter.convertToPdf(new FileInputStream(html), pdfDocument, null);
        printOutputPdfNameAndDir(outFile);

        PdfDocument document = new PdfDocument(new PdfReader(outFile));
        TagTreePointer tagPointer = new TagTreePointer(document);
        tagPointer.moveToKid(1, StandardRoles.P);
        Assertions.assertEquals("a-DE", tagPointer.getProperties().getLanguage());

        tagPointer.moveToRoot().moveToKid(2, StandardRoles.P);
        Assertions.assertEquals("DE", tagPointer.getProperties().getLanguage());

        tagPointer.moveToRoot().moveToKid(3, StandardRoles.P);
        Assertions.assertEquals("de-419-DE", tagPointer.getProperties().getLanguage());

        tagPointer.moveToRoot().moveToKid(4, StandardRoles.P);
        Assertions.assertEquals("en-gb", tagPointer.getProperties().getLanguage());

        tagPointer.moveToRoot().moveToKid(5, StandardRoles.P);
        Assertions.assertEquals("fr-brai", tagPointer.getProperties().getLanguage());

        tagPointer.moveToRoot().moveToKid(6, StandardRoles.P);
        Assertions.assertEquals("fr-BRAI", tagPointer.getProperties().getLanguage());

        Assertions.assertNull(document.getCatalog().getLang());
        document.close();
    }

    @Test
    public void langAttrEmptyTagTest() throws IOException {
        String html = sourceFolder + "langAttrEmptyTagTest.html";
        String outFile = destinationFolder + "langAttrEmptyTagTest.pdf";
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outFile));
        pdfDocument.setTagged();

        HtmlConverter.convertToPdf(new FileInputStream(html), pdfDocument, null);
        printOutputPdfNameAndDir(outFile);

        PdfDocument document = new PdfDocument(new PdfReader(outFile));
        TagTreePointer tagPointer = new TagTreePointer(document);

        tagPointer.moveToKid(0);
        Assertions.assertEquals("", tagPointer.getProperties().getLanguage());

        tagPointer
                .moveToRoot()
                .moveToKid(1)
                .moveToKid(0)
                .moveToKid(StandardRoles.P);
        Assertions.assertEquals("", tagPointer.getProperties().getLanguage());

        tagPointer
                .moveToRoot()
                .moveToKid(1)
                .moveToKid(2)
                .moveToKid(StandardRoles.P);
        Assertions.assertEquals("", tagPointer.getProperties().getLanguage());

        tagPointer
                .moveToRoot()
                .moveToKid(2)
                .moveToKid(0)
                .moveToKid(StandardRoles.TD);
        Assertions.assertEquals("", tagPointer.getProperties().getLanguage());

        tagPointer
                .moveToRoot()
                .moveToKid(2)
                .moveToKid(1)
                .moveToKid(StandardRoles.TD);
        Assertions.assertEquals("", tagPointer.getProperties().getLanguage());

        Assertions.assertNull(document.getCatalog().getLang());
        document.close();
    }

    @Test
    public void langAttrRegionSubtagTest() throws IOException {
        String html = sourceFolder + "langAttrRegionSubtagTest.html";
        String outFile = destinationFolder + "langAttrRegionSubtagTest.pdf";
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outFile));
        pdfDocument.setTagged();

        HtmlConverter.convertToPdf(new FileInputStream(html), pdfDocument, null);
        printOutputPdfNameAndDir(outFile);

        PdfDocument document = new PdfDocument(new PdfReader(outFile));
        TagTreePointer tagPointer = new TagTreePointer(document);
        tagPointer.moveToKid(1, StandardRoles.P);
        Assertions.assertEquals("de-DE", tagPointer.getProperties().getLanguage());

        tagPointer.moveToRoot().moveToKid(2, StandardRoles.P);
        Assertions.assertEquals("en-US", tagPointer.getProperties().getLanguage());

        tagPointer.moveToRoot().moveToKid(3, StandardRoles.P);
        Assertions.assertEquals("es-419", tagPointer.getProperties().getLanguage());

        Assertions.assertNull(document.getCatalog().getLang());

        document.close();
    }

    @Test
    public void langAttrScriptSubtagTest() throws IOException {
        String html = sourceFolder + "langAttrScriptSubtagTest.html";
        String outFile = destinationFolder + "langAttrScriptSubtagTest.pdf";
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outFile));
        pdfDocument.setTagged();

        HtmlConverter.convertToPdf(new FileInputStream(html), pdfDocument, null);
        printOutputPdfNameAndDir(outFile);

        PdfDocument document = new PdfDocument(new PdfReader(outFile));
        TagTreePointer tagPointer = new TagTreePointer(document);
        tagPointer.moveToKid(1, StandardRoles.P);
        Assertions.assertEquals("fr-Brai", tagPointer.getProperties().getLanguage());

        tagPointer.moveToRoot().moveToKid(2, StandardRoles.P);
        Assertions.assertEquals("ru-Latn", tagPointer.getProperties().getLanguage());

        tagPointer.moveToRoot().moveToKid(3, StandardRoles.P);
        Assertions.assertEquals("sr-Cyrl", tagPointer.getProperties().getLanguage());

        Assertions.assertNull(document.getCatalog().getLang());
        document.close();
    }

    @Test
    public void langAttrScriptRegionSubtagTest() throws IOException {
        String html = sourceFolder + "langAttrScriptRegionSubtagTest.html";
        String outFile = destinationFolder + "langAttrScriptRegionSubtagTest.pdf";
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outFile));
        pdfDocument.setTagged();

        HtmlConverter.convertToPdf(new FileInputStream(html), pdfDocument, null);
        printOutputPdfNameAndDir(outFile);

        PdfDocument document = new PdfDocument(new PdfReader(outFile));
        TagTreePointer tagPointer = new TagTreePointer(document);
        tagPointer.moveToKid(1, StandardRoles.P);
        Assertions.assertEquals("zh-Hans-CN", tagPointer.getProperties().getLanguage());

        tagPointer.moveToRoot().moveToKid(2, StandardRoles.P);
        Assertions.assertEquals("ru-Cyrl-BY", tagPointer.getProperties().getLanguage());
        
        Assertions.assertNull( document.getCatalog().getLang());
        document.close();
    }

    @Test
    public void langAttrInSvgForTaggedPdfTest() throws IOException {
        String html = sourceFolder + "langAttrInSvgForTaggedPdfTest.html";
        String outFile = destinationFolder + "langAttrInSvgForTaggedPdfTest.pdf";
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outFile));
        pdfDocument.setTagged();

        HtmlConverter.convertToPdf(new FileInputStream(html), pdfDocument,
                new ConverterProperties().setBaseUri(sourceFolder));
        printOutputPdfNameAndDir(outFile);

        PdfDocument document = new PdfDocument(new PdfReader(outFile));
        TagTreePointer tagPointer = new TagTreePointer(document);
        tagPointer.moveToKid(StandardRoles.FIGURE);

        Assertions.assertEquals("en", tagPointer.getProperties().getLanguage());

        tagPointer
                .moveToRoot()
                .moveToKid(2, StandardRoles.P)
                .moveToKid(StandardRoles.FIGURE);
        Assertions.assertEquals("fr", tagPointer.getProperties().getLanguage());

        tagPointer
                .moveToRoot()
                .moveToKid(3, StandardRoles.P)
                .moveToKid(StandardRoles.FIGURE);
        Assertions.assertEquals("ru", tagPointer.getProperties().getLanguage());

        Assertions.assertNull(document.getCatalog().getLang());
        document.close();
    }

    @Test
    public void langAttrInListsForTaggedPdfTest() throws IOException {
        String html = sourceFolder + "langAttrInListsForTaggedPdfTest.html";
        String outFile = destinationFolder + "langAttrInListsForTaggedPdfTest.pdf";
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outFile));
        pdfDocument.setTagged();

        HtmlConverter.convertToPdf(new FileInputStream(html), pdfDocument, null);
        printOutputPdfNameAndDir(outFile);

        PdfDocument document = new PdfDocument(new PdfReader(outFile));
        TagTreePointer tagPointer = new TagTreePointer(document);

        tagPointer.moveToKid(0, StandardRoles.L);
        Assertions.assertEquals("en", tagPointer.getProperties().getLanguage());
        tagPointer
                .moveToKid(2, StandardRoles.LI)
                .moveToKid(0, StandardRoles.LBODY);
        Assertions.assertEquals("", tagPointer.getProperties().getLanguage());
        tagPointer.moveToRoot();

        tagPointer.moveToKid(1, StandardRoles.L);
        tagPointer.getProperties().getLanguage();
        Assertions.assertEquals("de", tagPointer.getProperties().getLanguage());
        tagPointer.moveToRoot();

        tagPointer
                .moveToKid(2, StandardRoles.L)
                .moveToKid(0, StandardRoles.LI)
                .moveToKid(0, StandardRoles.LBODY);
        Assertions.assertEquals("en", tagPointer.getProperties().getLanguage());
        tagPointer.moveToRoot();

        tagPointer
                .moveToKid(2, StandardRoles.L)
                .moveToKid(1, StandardRoles.LI)
                .moveToKid(0, StandardRoles.LBODY);
        Assertions.assertEquals("de", tagPointer.getProperties().getLanguage());

        Assertions.assertNull(document.getCatalog().getLang());
        document.close();
    }

    @Test
    public void langAttrInListWithBeforeStyleForTaggedPdfTest() throws IOException {
        String html = sourceFolder + "langAttrInListWithBeforeStyleForTaggedPdfTest.html";
        String outFile = destinationFolder + "langAttrInListWithBeforeStyleForTaggedPdfTest.pdf";
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outFile));
        pdfDocument.setTagged();

        HtmlConverter.convertToPdf(new FileInputStream(html), pdfDocument, null);
        printOutputPdfNameAndDir(outFile);

        PdfDocument document = new PdfDocument(new PdfReader(outFile));
        TagTreePointer tagPointer = new TagTreePointer(document);

        tagPointer
                .moveToKid(0, StandardRoles.L)
                .moveToKid(1, StandardRoles.LI)
                .moveToKid(0, StandardRoles.LBODY);
        Assertions.assertEquals("de", tagPointer.getProperties().getLanguage());

        List<String> kidsRoles = tagPointer.moveToKid(StandardRoles.P).getKidsRoles();
        Assertions.assertTrue(StandardRoles.SPAN.equals(kidsRoles.get(0))
                && StandardRoles.SPAN.equals(kidsRoles.get(1)));

        Assertions.assertNull(document.getCatalog().getLang());
        document.close();
    }

    @Test
    public void langAttrInDivAndSpanForTagPdfTest() throws IOException, InterruptedException {
        PdfDocument doc = compareResultWithDocument("langAttrInDivAndSpanForTagPdfTest");
        Assertions.assertEquals("ru", doc.getCatalog().getLang().toUnicodeString());
        doc.close();
    }

    @Test
    public void langAttrInDivAndSpanForConvertToElementsMethodTest() throws IOException {
        String html = sourceFolder + "langAttrInDivAndSpanForTagPdfTest.html";

        List<IElement> elemList = HtmlConverter.convertToElements(new FileInputStream(html));

        Div div = (Div) elemList.get(0);
        Assertions.assertEquals("la", div.getAccessibilityProperties().getLanguage());
        Paragraph p = (Paragraph) div.getChildren().get(0);
        Assertions.assertNull(p.getAccessibilityProperties().getLanguage());
        div = (Div) div.getChildren().get(1);
        Assertions.assertEquals("en", div.getAccessibilityProperties().getLanguage());

        div = (Div) elemList.get(1);
        Assertions.assertEquals("ru", div.getAccessibilityProperties().getLanguage());
        p = (Paragraph) div.getChildren().get(0);
        Assertions.assertNull(p.getAccessibilityProperties().getLanguage());
        div = (Div) div.getChildren().get(1);
        Assertions.assertNull(div.getAccessibilityProperties().getLanguage());

        p = (Paragraph) elemList.get(2);
        Text text = (Text) p.getChildren().get(0);
        Assertions.assertNull(text.getAccessibilityProperties().getLanguage());
        text = (Text) p.getChildren().get(1);
        Assertions.assertEquals("ru", text.getAccessibilityProperties().getLanguage());
        text = (Text) p.getChildren().get(2);
        Assertions.assertEquals("en", text.getAccessibilityProperties().getLanguage());
        text = (Text) p.getChildren().get(3);
        Assertions.assertEquals("ru", text.getAccessibilityProperties().getLanguage());
        text = (Text) p.getChildren().get(4);
        Assertions.assertNull(text.getAccessibilityProperties().getLanguage());
    }

    @Test
    public void langAttrInFormFieldsetAndLegendForTaggedPdfTest() throws IOException, InterruptedException {
        PdfDocument doc = compareResultWithDocument("langAttrInFormFieldsetAndLegendForTaggedPdfTest");
        Assertions.assertNull(doc.getCatalog().getLang());
        doc.close();
    }

    @Test
    public void langAttrInInputAndTextareaForTaggedPdfTest() throws IOException, InterruptedException {
        PdfDocument doc = compareResultWithDocument("langAttrInInputAndTextareaForTaggedPdfTest");
        Assertions.assertEquals("da", doc.getCatalog().getLang().toUnicodeString());
        doc.close();
    }

    @Test
    public void langAttrInButtonForTaggedPdfTest() throws IOException, InterruptedException {
        PdfDocument doc = compareResultWithDocument("langAttrInButtonForTaggedPdfTest");
        Assertions.assertEquals("da", doc.getCatalog().getLang().toUnicodeString());
        doc.close();
    }

    @Test
    public void langAttrInInputAndTextareaForTaggedPdfWithActoformTest() throws IOException, InterruptedException {
        String html = sourceFolder + "langAttrInInputAndTextareaForTaggedPdfTest.html";
        String outFile = destinationFolder + "langAttrInInputAndTextareaForTaggedPdfWithActoformTest.pdf";
        String cmp = sourceFolder + "cmp_langAttrInInputAndTextareaForTaggedPdfWithActoformTest.pdf";
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outFile));
        pdfDocument.setTagged();

        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setCreateAcroForm(true);
        converterProperties.setBaseUri(sourceFolder);
        HtmlConverter.convertToPdf(new FileInputStream(html), pdfDocument, converterProperties);
        printOutputPdfNameAndDir(outFile);

        PdfDocument document = new PdfDocument(new PdfReader(outFile));
        //compareByContent is used here to check the complete logical structure tree to notice all the differences.
        Assertions.assertNull(new CompareTool().compareByContent(outFile, cmp, destinationFolder,
                "diff_forms"));
        Assertions.assertEquals("da", document.getCatalog().getLang().toUnicodeString());

        document.close();
    }

    @Test
    public void langAttrInButtonForTaggedPdfWithActoformTest() throws IOException, InterruptedException {
        String html = sourceFolder + "langAttrInButtonForTaggedPdfTest.html";
        String outFile = destinationFolder + "langAttrInButtonForTaggedPdfWithActoformTest.pdf";
        String cmp = sourceFolder + "cmp_langAttrInButtonForTaggedPdfWithActoformTest.pdf";
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outFile));
        pdfDocument.setTagged();

        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setCreateAcroForm(true);
        converterProperties.setBaseUri(sourceFolder);
        HtmlConverter.convertToPdf(new FileInputStream(html), pdfDocument, converterProperties);
        printOutputPdfNameAndDir(outFile);

        PdfDocument document = new PdfDocument(new PdfReader(outFile));
        //compareByContent is used here to check the complete logical structure tree to notice all the differences.
        Assertions.assertNull(new CompareTool().compareByContent(outFile, cmp, destinationFolder,
                "diff_forms"));
        Assertions.assertEquals("da", document.getCatalog().getLang().toUnicodeString());

        document.close();
    }

    @Test
    public void langAttrInTableForTaggedPdfTest() throws IOException, InterruptedException {
        PdfDocument doc = compareResultWithDocument("langAttrInTableForTaggedPdfTest");
        Assertions.assertEquals("da", doc.getCatalog().getLang().toUnicodeString());
        doc.close();
    }

    @Test
    public void langAttrInTableWithColgroupForTaggedPdfTest() throws IOException, InterruptedException {
        PdfDocument doc = compareResultWithDocument("langAttrInTableWithColgroupForTaggedPdfTest");
        Assertions.assertNull(doc.getCatalog().getLang());
        doc.close();
    }

    @Test
    public void langAttrInTableWithColgroupTheadAndTfootForTaggedPdfTest() throws IOException, InterruptedException {
        PdfDocument doc = compareResultWithDocument("langAttrInTableWithColgroupTheadAndTfootForTaggedPdfTest");
        Assertions.assertEquals("da", doc.getCatalog().getLang().toUnicodeString());
        doc.close();
    }

    @Test
    public void langAttrInTableWithTheadAndColgroupForTaggedPdfTest() throws IOException, InterruptedException {
        PdfDocument doc = compareResultWithDocument("langAttrInTableWithTheadAndColgroupForTaggedPdfTest");
        Assertions.assertEquals("da", doc.getCatalog().getLang().toUnicodeString());
        doc.close();
    }

    @Test
    public void langAttrInTableWithTheadTfootWithLangForTaggedPdfTest() throws IOException, InterruptedException {
        PdfDocument doc = compareResultWithDocument("langAttrInTableWithTheadTfootWithLangForTaggedPdfTest");
        Assertions.assertEquals("da", doc.getCatalog().getLang().toUnicodeString());
        doc.close();
    }

    @Test
    public void langAttrInTableWithTheadWithLangAndTfootForTaggedPdfTest() throws IOException, InterruptedException {
        PdfDocument doc = compareResultWithDocument("langAttrInTableWithTheadWithLangAndTfootForTaggedPdfTest");
        Assertions.assertEquals("da", doc.getCatalog().getLang().toUnicodeString());
        doc.close();
    }

    @Test
    public void langAttrInSelectsWithLangAndOneOptionForTaggedPdfTest() throws IOException, InterruptedException {
        PdfDocument doc = compareResultWithDocument("langAttrInSelectsWithLangAndOneOptionForTaggedPdfTest");
        Assertions.assertNull(doc.getCatalog().getLang());
        doc.close();
    }

    @Test
    public void langAttrInSelectsWithSeveralOptionForTaggedPdfTest() throws IOException, InterruptedException {
        PdfDocument doc = compareResultWithDocument("langAttrInSelectsWithSeveralOptionForTaggedPdfTest");
        Assertions.assertNull(doc.getCatalog().getLang());
        doc.close();
    }

    @Test
    public void langAttrInHtmlWithLangBodyWithoutLangTest() throws IOException, InterruptedException {
        PdfDocument doc = compareResultWithDocument("langAttrInHtmlWithLangBodyWithoutLangTest");
        Assertions.assertEquals("ru", doc.getCatalog().getLang().toUnicodeString());
        doc.close();
    }

    @Test
    public void langAttrInHtmlWithLangBodyWithoutLangForConvertToElementsMethodTest() throws IOException {
        String html = sourceFolder + "langAttrInHtmlWithLangBodyWithoutLangTest.html";

        List<IElement> elemList = HtmlConverter.convertToElements(new FileInputStream(html));

        Paragraph p = (Paragraph) elemList.get(0);
        Assertions.assertEquals("ru", p.getAccessibilityProperties().getLanguage());

        p = (Paragraph) elemList.get(1);
        Assertions.assertEquals("en", p.getAccessibilityProperties().getLanguage());

        Div div = (Div) elemList.get(2);
        Assertions.assertEquals("ru", div.getAccessibilityProperties().getLanguage());

        p = (Paragraph) elemList.get(3);
        Assertions.assertEquals("ru", p.getAccessibilityProperties().getLanguage());
    }

    @Test
    public void langAttrInHtmlWithoutLangBodyWithLangTest() throws IOException, InterruptedException {
        PdfDocument doc = compareResultWithDocument("langAttrInHtmlWithoutLangBodyWithLangTest");
        Assertions.assertEquals("ru", doc.getCatalog().getLang().toUnicodeString());
        doc.close();
    }

    @Test
    public void langAttrInHtmlWithLangBodyWithLangTest() throws IOException, InterruptedException {
        PdfDocument doc = compareResultWithDocument("langAttrInHtmlWithLangBodyWithLangTest");
        Assertions.assertEquals("by", doc.getCatalog().getLang().toUnicodeString());
        doc.close();
    }

    @Test
    public void langAttrInHtmlWithLangBodyWithLangForConvertToElementsMethodTest() throws IOException {
        String html = sourceFolder + "langAttrInHtmlWithLangBodyWithLangTest.html";

        List<IElement> elemList = HtmlConverter.convertToElements(new FileInputStream(html));

        Paragraph p = (Paragraph) elemList.get(0);
        Assertions.assertEquals("by", p.getAccessibilityProperties().getLanguage());

        p = (Paragraph) elemList.get(1);
        Assertions.assertEquals("en", p.getAccessibilityProperties().getLanguage());

        Div div = (Div) elemList.get(2);
        Assertions.assertEquals("by", div.getAccessibilityProperties().getLanguage());

        p = (Paragraph) elemList.get(3);
        Assertions.assertEquals("by", p.getAccessibilityProperties().getLanguage());
    }

    private PdfDocument compareResultWithDocument(String fileName) throws IOException, InterruptedException {
        String html = sourceFolder + fileName + ".html";
        String outFile = destinationFolder + fileName + ".pdf";
        String cmp = sourceFolder + "cmp_" + fileName + ".pdf";
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outFile));
        pdfDocument.setTagged();

        HtmlConverter.convertToPdf(new FileInputStream(html), pdfDocument,
                new ConverterProperties().setBaseUri(sourceFolder));
        printOutputPdfNameAndDir(outFile);

        PdfDocument document = new PdfDocument(new PdfReader(outFile));
        // compareByContent is used here to check the complete logical structure tree to notice all the differences.
        Assertions.assertNull(new CompareTool().compareByContent(outFile, cmp, destinationFolder,
                "diff_test"));

        return document;
    }
}
