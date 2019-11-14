/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2019 iText Group NV
    Authors: iText Software.

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
import com.itextpdf.test.annotations.type.IntegrationTest;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.FileInputStream;
import java.io.IOException;


@Category(IntegrationTest.class)
public class LangAttributeTest extends ExtendedHtmlConversionITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/attribute/LangAttributeTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/attribute/LangAttributeTest/";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    //TODO: DEVSIX-3243 Assert exact language
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
        Assert.assertNull(tagPointer.getProperties().getLanguage());
        Assert.assertNull(document.getCatalog().getLang());

        document.close();
    }

    @Test
    //TODO: DEVSIX-3243 Assert exact language
    public void langAttrInHtmlTagForTaggedPdfTest() throws IOException {
        String html = sourceFolder + "langAttrInHtmlTagForTaggedPdfTest.html";
        String outFile = destinationFolder + "langAttrInHtmlTagForTaggedPdfTest.pdf";
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outFile));
        pdfDocument.setTagged();

        HtmlConverter.convertToPdf(new FileInputStream(html), pdfDocument, null);
        printOutputPdfNameAndDir(outFile);

        PdfDocument document = new PdfDocument(new PdfReader(outFile));
        TagTreePointer tagPointer = new TagTreePointer(document);
        Assert.assertNull(tagPointer.getProperties().getLanguage());
        Assert.assertNull(document.getCatalog().getLang());
        
        document.close();
    }

    @Test
    //TODO: DEVSIX-3243 Assert exact language
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
        Assert.assertNull(tagPointer.getProperties().getLanguage());

        tagPointer.moveToRoot().moveToKid(2, StandardRoles.P);
        Assert.assertNull(tagPointer.getProperties().getLanguage());

        tagPointer.moveToRoot().moveToKid(3, StandardRoles.P);
        Assert.assertNull(tagPointer.getProperties().getLanguage());

        tagPointer.moveToRoot().moveToKid(4, StandardRoles.P);
        Assert.assertNull(tagPointer.getProperties().getLanguage());

        tagPointer.moveToRoot().moveToKid(5, StandardRoles.P);
        Assert.assertNull(tagPointer.getProperties().getLanguage());

        tagPointer.moveToRoot().moveToKid(6, StandardRoles.P);
        Assert.assertNull(tagPointer.getProperties().getLanguage());

        Assert.assertNull(document.getCatalog().getLang());
        document.close();
    }

    @Test
    //TODO: DEVSIX-3243 Assert exact language
    public void langAttrEmptyTagTest() throws IOException {
        String html = sourceFolder + "langAttrEmptyTagTest.html";
        String outFile = destinationFolder + "langAttrEmptyTagTest.pdf";
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outFile));
        pdfDocument.setTagged();

        HtmlConverter.convertToPdf(new FileInputStream(html), pdfDocument, null);
        printOutputPdfNameAndDir(outFile);

        PdfDocument document = new PdfDocument(new PdfReader(outFile));
        TagTreePointer tagPointer = new TagTreePointer(document);
        tagPointer.moveToKid(StandardRoles.P);

        Assert.assertNull(tagPointer.getProperties().getLanguage());
        Assert.assertNull(document.getCatalog().getLang());
        document.close();
    }

    @Test
    //TODO: DEVSIX-3243 Assert exact language
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
        Assert.assertNull(tagPointer.getProperties().getLanguage());

        tagPointer.moveToRoot().moveToKid(2, StandardRoles.P);
        Assert.assertNull(tagPointer.getProperties().getLanguage());

        tagPointer.moveToRoot().moveToKid(3, StandardRoles.P);
        Assert.assertNull(tagPointer.getProperties().getLanguage());

        Assert.assertNull(document.getCatalog().getLang());

        document.close();
    }

    @Test
    //TODO: DEVSIX-3243 Assert exact language
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
        Assert.assertNull(tagPointer.getProperties().getLanguage());

        tagPointer.moveToRoot().moveToKid(2, StandardRoles.P);
        Assert.assertNull(tagPointer.getProperties().getLanguage());

        tagPointer.moveToRoot().moveToKid(3, StandardRoles.P);
        Assert.assertNull(tagPointer.getProperties().getLanguage());

        Assert.assertNull(document.getCatalog().getLang());
        document.close();
    }

    @Test
    //TODO: DEVSIX-3243 Assert exact language
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
        Assert.assertNull(tagPointer.getProperties().getLanguage());

        tagPointer.moveToRoot().moveToKid(2, StandardRoles.P);
        Assert.assertNull(tagPointer.getProperties().getLanguage());
        
        Assert.assertNull( document.getCatalog().getLang());
        document.close();
    }

    @Test
    //TODO: after DEVSIX-3243 fixed need to assert exact language and check assertEquals
    public void langAttrInTableForTaggedPdfTest() throws IOException, InterruptedException {
        String html = sourceFolder + "langAttrInTableForTaggedPdfTest.html";
        String outFile = destinationFolder + "langAttrInTableForTaggedPdfTest.pdf";
        String cmp = sourceFolder + "cmp_langAttrInTableForTaggedPdfTest.pdf";
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outFile));
        pdfDocument.setTagged();

        HtmlConverter.convertToPdf(new FileInputStream(html), pdfDocument, null);
        printOutputPdfNameAndDir(outFile);

        PdfDocument document = new PdfDocument(new PdfReader(outFile));
        //compareByContent is used here to check the complete logical structure tree to notice all the differences.
        Assert.assertNull(new CompareTool().compareByContent(outFile, cmp, destinationFolder,
                "diff_Table"));
        Assert.assertNull(document.getCatalog().getLang());

        pdfDocument.close();

    }

    @Test
    //TODO: after DEVSIX-3243 fixed need to assert exact language and check assertEquals
    public void langAttrInSvgForTaggedPdfTest() throws IOException {
        String html = sourceFolder + "langAttrInSvgForTaggedPdfTest.html";
        String outFile = destinationFolder + "langAttrInSvgForTaggedPdfTest.pdf";
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outFile));
        pdfDocument.setTagged();

        HtmlConverter.convertToPdf(new FileInputStream(html), pdfDocument, new ConverterProperties().setBaseUri(sourceFolder));
        printOutputPdfNameAndDir(outFile);

        PdfDocument document = new PdfDocument(new PdfReader(outFile));
        TagTreePointer tagPointer = new TagTreePointer(document);
        tagPointer.moveToKid(StandardRoles.FIGURE);

        Assert.assertNull(tagPointer.getProperties().getLanguage());

        tagPointer
                .moveToParent()
                .moveToKid(2, StandardRoles.P)
                .moveToKid(StandardRoles.FIGURE);

        Assert.assertNull(tagPointer.getProperties().getLanguage());
        Assert.assertNull(document.getCatalog().getLang());
        document.close();
    }

    @Test
    //TODO: after DEVSIX-3243 fixed need to assert exact language and check assertEquals
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

        Assert.assertNull(tagPointer.getProperties().getLanguage());
        tagPointer.moveToParent();

        tagPointer.moveToKid(1, StandardRoles.L);
        tagPointer.getProperties().getLanguage();
        Assert.assertNull(tagPointer.getProperties().getLanguage());
        tagPointer.moveToParent();

        tagPointer
                .moveToKid(2, StandardRoles.L)
                .moveToKid(0, StandardRoles.LI);

        Assert.assertNull(tagPointer.getProperties().getLanguage());
        tagPointer.moveToRoot();

        tagPointer
                .moveToKid(2, StandardRoles.L)
                .moveToKid(1, StandardRoles.LI);

        Assert.assertNull(tagPointer.getProperties().getLanguage());
        Assert.assertNull(document.getCatalog().getLang());
        document.close();
    }

    @Test
    //TODO: after DEVSIX-3243 fixed need to assert exact language and check assertEquals
    public void langAttrInDivAndSpanForTagPdfTest() throws IOException, ParserConfigurationException, SAXException, InterruptedException {
        String html = sourceFolder + "langAttrInDivAndSpanForTagPdfTest.html";
        String outFile = destinationFolder + "langAttrInDivAndSpanForTagPdfTest.pdf";
        String cmp = sourceFolder + "cmp_langAttrInDivAndSpanForTagPdfTest.pdf";
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outFile));
        pdfDocument.setTagged();

        HtmlConverter.convertToPdf(new FileInputStream(html), pdfDocument, null);
        printOutputPdfNameAndDir(outFile);

        PdfDocument document = new PdfDocument(new PdfReader(outFile));
        //compareByContent is used here to check the complete logical structure tree to notice all the differences.
        Assert.assertNull(new CompareTool().compareByContent(outFile, cmp, destinationFolder, "diff_Table"));
        Assert.assertNull(document.getCatalog().getLang());
        pdfDocument.close();
    }

    @Test
    //TODO: after DEVSIX-3243 fixed need to assert exact language and check assertEquals
    public void langAttrInFormForTaggedPdfTest() throws IOException, InterruptedException {
        String html = sourceFolder + "langAttrInFormForTaggedPdfTest.html";
        String outFile = destinationFolder + "langAttrInFormForTaggedPdfTest.pdf";
        String cmp = sourceFolder + "cmp_langAttrInFormForTaggedPdfTest.pdf";
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outFile));
        pdfDocument.setTagged();

        HtmlConverter.convertToPdf(new FileInputStream(html), pdfDocument,
                new ConverterProperties().setBaseUri(sourceFolder));
        printOutputPdfNameAndDir(outFile);

        PdfDocument document = new PdfDocument(new PdfReader(outFile));
        //compareByContent is used here to check the complete logical structure tree to notice all the differences.
        Assert.assertNull(new CompareTool().compareByContent(outFile, cmp, destinationFolder,
                "diff_forms"));
        Assert.assertNull(document.getCatalog().getLang());

        document.close();
    }
}