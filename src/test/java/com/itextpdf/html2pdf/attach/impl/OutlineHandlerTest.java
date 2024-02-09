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
package com.itextpdf.html2pdf.attach.impl;

import com.itextpdf.commons.datastructures.Tuple2;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.attach.ITagWorker;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.attach.impl.tags.PTagWorker;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.html.TagConstants;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.kernel.pdf.action.PdfAction;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.styledxmlparser.jsoup.nodes.Element;
import com.itextpdf.styledxmlparser.jsoup.parser.Tag;
import com.itextpdf.styledxmlparser.node.IElementNode;
import com.itextpdf.styledxmlparser.node.impl.jsoup.node.JsoupElementNode;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class OutlineHandlerTest extends ExtendedHtmlConversionITextTest {

    private static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/attach/impl/OutlineHandlerTest/";
    private static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/attach/impl/OutlineHandlerTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void defaultDestinationPrefixTest() {
        Map<String, Integer> priorityMappings = new HashMap<>();
        priorityMappings.put("p", 1);
        OutlineHandler outlineHandler = new OutlineHandler().putAllMarksPriorityMappings(priorityMappings);

        ProcessorContext context = new ProcessorContext(new ConverterProperties().setOutlineHandler(outlineHandler));
        context.reset(new PdfDocument(new PdfWriter(new ByteArrayOutputStream())));

        IElementNode elementNode = new JsoupElementNode(new Element(Tag.valueOf(TagConstants.P), TagConstants.P));
        Map<String, String> styles = new HashMap<>();
        styles.put(CssConstants.WHITE_SPACE, CssConstants.NORMAL);
        styles.put(CssConstants.TEXT_TRANSFORM, CssConstants.LOWERCASE);
        // Styles are required in the constructor of the PTagWorker class
        elementNode.setStyles(styles);

        outlineHandler.addOutlineAndDestToDocument(new PTagWorker(elementNode, context), elementNode, context);

        PdfOutline pdfOutline = context.getPdfDocument().getOutlines(false).getAllChildren().get(0);
        Assert.assertEquals("p1", pdfOutline.getTitle());
        PdfString pdfStringDest = (PdfString) pdfOutline.getDestination().getPdfObject();
        Assert.assertEquals("pdfHTML-iText-outline-1", pdfStringDest.toUnicodeString());
    }

    @Test
    public void customDestinationPrefixTest() {
        Map<String, Integer> priorityMappings = new HashMap<>();
        priorityMappings.put("p", 1);
        OutlineHandler outlineHandler = new OutlineHandler().putAllMarksPriorityMappings(priorityMappings);

        outlineHandler.setDestinationNamePrefix("prefix-");
        Assert.assertEquals("prefix-", outlineHandler.getDestinationNamePrefix());

        ProcessorContext context = new ProcessorContext(new ConverterProperties().setOutlineHandler(outlineHandler));
        context.reset(new PdfDocument(new PdfWriter(new ByteArrayOutputStream())));

        IElementNode elementNode = new JsoupElementNode(new Element(Tag.valueOf(TagConstants.P), TagConstants.P));
        Map<String, String> styles = new HashMap<>();
        styles.put(CssConstants.WHITE_SPACE, CssConstants.NORMAL);
        styles.put(CssConstants.TEXT_TRANSFORM, CssConstants.LOWERCASE);
        // Styles are required in the constructor of the PTagWorker class
        elementNode.setStyles(styles);

        outlineHandler.addOutlineAndDestToDocument(new PTagWorker(elementNode, context), elementNode, context);

        PdfOutline pdfOutline = context.getPdfDocument().getOutlines(false).getAllChildren().get(0);
        Assert.assertEquals("p1", pdfOutline.getTitle());
        PdfString pdfStringDest = (PdfString) pdfOutline.getDestination().getPdfObject();
        Assert.assertEquals("prefix-1", pdfStringDest.toUnicodeString());
    }

    @Test
    // TODO DEVSIX-5195 fix cmp after fix is introduced
    public void defaultOutlineHandlerWithHTagHavingIdTest() throws IOException, InterruptedException {
        String inFile = SOURCE_FOLDER + "defaultOutlineHandlerWithHTagHavingIdTest.html";
        String outFile = DESTINATION_FOLDER + "defaultOutlineHandlerWithHTagHavingIdTest.pdf";
        String cmpFile = SOURCE_FOLDER + "cmp_defaultOutlineHandlerWithHTagHavingIdTest.pdf";

        OutlineHandler outlineHandler = OutlineHandler.createStandardHandler();
        HtmlConverter.convertToPdf(new File(inFile), new File(outFile),
                new ConverterProperties().setOutlineHandler(outlineHandler));
        Assert.assertNull(new CompareTool().compareByContent(outFile, cmpFile, DESTINATION_FOLDER, "diff_defaultOutlineHandlerWithHTagHavingIdTest"));
    }

    @Test
    public void resetOutlineHandlerTest() throws IOException, InterruptedException {
        OutlineHandler outlineHandler = OutlineHandler.createStandardHandler();

        for (int i = 1; i <= 3; i++) {
            String srcHtml = SOURCE_FOLDER + "outlines0" + i + ".html";
            String outPdf = DESTINATION_FOLDER + "outlines0" + i + ".pdf";
            String cmpPdf = SOURCE_FOLDER + "cmp_outlines0" + i + ".pdf";

            HtmlConverter.convertToPdf(new File(srcHtml), new File(outPdf),
                    new ConverterProperties().setOutlineHandler(outlineHandler));
            Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, DESTINATION_FOLDER, "diff_" + i));
        }
    }

    @Test
    public void capitalHeadingLevelTest() throws IOException, InterruptedException {
        String inFile = SOURCE_FOLDER + "capitalHeadingLevel.html";
        String outFile = DESTINATION_FOLDER + "capitalHeadingLevel.pdf";
        String cmpFile = SOURCE_FOLDER + "cmp_capitalHeadingLevel.pdf";

        OutlineHandler outlineHandler = OutlineHandler.createStandardHandler();
        HtmlConverter.convertToPdf(new File(inFile), new File(outFile),
                new ConverterProperties().setOutlineHandler(outlineHandler));
        Assert.assertNull(new CompareTool().compareByContent(outFile, cmpFile, DESTINATION_FOLDER,
                "diff_capitalHeadingLevelOne"));
    }

    @Test
    public void classBasedOutlineTest() throws IOException, InterruptedException {
        String inFile = SOURCE_FOLDER + "htmlForClassBasedOutline.html";
        String outFile = DESTINATION_FOLDER + "pdfWithClassBasedOutline.pdf";
        String cmpFile = SOURCE_FOLDER + "cmp_pdfWithClassBasedOutline.pdf";
        Map<String, Integer> priorityMappings = new HashMap<>();
        priorityMappings.put("heading1", 1);
        priorityMappings.put("heading2", 2);
        OutlineHandler handler = OutlineHandler.createHandler(new ClassOutlineMarkExtractor())
                .putAllMarksPriorityMappings(priorityMappings);
        HtmlConverter.convertToPdf(new File(inFile), new File(outFile),
                new ConverterProperties().setOutlineHandler(handler));
        Assert.assertNull(new CompareTool().compareByContent(outFile, cmpFile, DESTINATION_FOLDER,
                "diff_ClassBasedOutline"));
    }

    @Test
    public void overrideOutlineHandlerTest() throws IOException, InterruptedException {
        String inFile = SOURCE_FOLDER + "htmlForChangedOutlineHandler.html";
        String outFile = DESTINATION_FOLDER + "changedOutlineHandlerDoc.pdf";
        String cmpFile = SOURCE_FOLDER + "cmp_changedOutlineHandlerDoc.pdf";
        OutlineHandler handler = new ChangedOutlineHandler();
        HtmlConverter.convertToPdf(new File(inFile), new File(outFile),
                new ConverterProperties().setOutlineHandler(handler));
        Assert.assertNull(new CompareTool().compareByContent(outFile, cmpFile, DESTINATION_FOLDER,
                "diff_ChangedOutlineHandler"));
    }

    public static class ChangedOutlineHandler extends OutlineHandler {
        @Override
        protected OutlineHandler addOutlineAndDestToDocument(ITagWorker tagWorker, IElementNode element, ProcessorContext context) {
            String markName = markExtractor.getMark(element);
            if (null != tagWorker && hasMarkPriorityMapping(markName) && context.getPdfDocument() != null
                    && "customMark".equals(element.getAttribute("class"))) {
                int level = (int) getMarkPriorityMapping(markName);
                if (null == currentOutline) {
                    currentOutline = context.getPdfDocument().getOutlines(false);
                }
                PdfOutline parent = currentOutline;
                while (!levelsInProcess.isEmpty() && level <= levelsInProcess.getFirst()) {
                    parent = parent.getParent();
                    levelsInProcess.pop();
                }
                PdfOutline outline = parent.addOutline(generateOutlineName(element));
                String destination = generateUniqueDestinationName(element);
                PdfAction action = PdfAction.createGoTo(destination);
                outline.addAction(action);
                destinationsInProcess.push(new Tuple2<String, PdfDictionary>(destination, action.getPdfObject()));

                levelsInProcess.push(level);
                currentOutline = outline;
            }
            return this;
        }
        public ChangedOutlineHandler(){
            markExtractor = new TagOutlineMarkExtractor();
            putMarkPriorityMapping(TagConstants.H1, 1);
            putMarkPriorityMapping(TagConstants.H2, 2);
            putMarkPriorityMapping(TagConstants.H3, 3);
            putMarkPriorityMapping(TagConstants.H4, 4);
            putMarkPriorityMapping(TagConstants.H5, 5);
            putMarkPriorityMapping(TagConstants.H6, 6);
        }
    }
}
