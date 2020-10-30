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
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.html2pdf.attach.ITagWorker;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.attach.impl.DefaultTagWorkerFactory;
import com.itextpdf.html2pdf.attach.impl.OutlineHandler;
import com.itextpdf.html2pdf.attach.impl.tags.HtmlTagWorker;
import com.itextpdf.html2pdf.attach.impl.tags.PageMarginBoxWorker;
import com.itextpdf.html2pdf.css.apply.ICssApplier;
import com.itextpdf.html2pdf.css.apply.impl.DefaultCssApplierFactory;
import com.itextpdf.html2pdf.css.apply.impl.PageMarginBoxCssApplier;
import com.itextpdf.html2pdf.html.TagConstants;
import com.itextpdf.io.util.UrlUtil;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
import com.itextpdf.kernel.pdf.tagging.StandardRoles;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.tagging.IAccessibleElement;
import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import com.itextpdf.styledxmlparser.css.media.MediaDeviceDescription;
import com.itextpdf.styledxmlparser.css.page.PageMarginBoxContextNode;
import com.itextpdf.styledxmlparser.node.IElementNode;
import com.itextpdf.styledxmlparser.node.IStylesContainer;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.LogLevelConstants;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

@Category(IntegrationTest.class)
public class PageRuleTest extends ExtendedITextTest {
    public static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css/PageRuleTest/";
    public static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/css/PageRuleTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(DESTINATION_FOLDER);
    }

    @Rule
    public ExpectedException junitExpectedException = ExpectedException.none();

    @Test
    public void marksCropCrossPageRuleTest() {
        runTest("marksCropCrossPageRuleTest");
    }

    @Test
    public void marksCropPageRuleTest() {
        runTest("marksCropPageRuleTest");
    }

    @Test
    public void marksCrossPageRuleTest() {
        runTest("marksCrossPageRuleTest");
    }

    @Test
    public void marksInvalidPageRuleTest() {
        runTest("marksInvalidPageRuleTest");
    }

    @Test
    public void marksNonePageRuleTest() {
        runTest("marksNonePageRuleTest");
    }

    @Test
    public void paddingPageRuleTest() {
        runTest("paddingPageRuleTest");
    }

    @Test
    public void compoundSizePageRuleTest() {
        runTest("compoundSizePageRuleTest");
    }

    @Test
    public void bleedPageRuleTest() {
        runTest("bleedPageRuleTest");
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = Html2PdfLogMessageConstant.PAGE_SIZE_VALUE_IS_INVALID, count = 3))
    public void invalidCompoundSizePageRuleTest() {
        runTest("invalidCompoundSizePageRuleTest");
    }

    @Test
    public void notAllMarginsPageRuleTest() {
        runTest("notAllMarginsPageRuleTest");
    }

    @Test
    public void firstLeftRightPageRuleTest() {
        runTest("firstLeftRightPageRuleTest");
    }

    @Test
    public void marksBleedPageRuleTest() {
        runTest("marksBleedPageRuleTest");
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = Html2PdfLogMessageConstant.CONTENT_PROPERTY_INVALID, count = 3))
    public void marginBoxTest01() {
        runTest("marginBoxTest01");
    }

    @Test
    public void marginBoxTest02() {
        runTest("marginBoxTest02");
    }

    @Test
    public void marginBoxTest03() {
        runTest("marginBoxTest03");
    }

    @Test
    public void marginBoxTest04() {
        runTest("marginBoxTest04");
    }

    @Test
    public void marginBoxTestImg01() {
        runTest("marginBoxTestImg01");
    }

    @Test
    public void marginBoxTestImg02() {
        runTest("marginBoxTestImg02");
    }

    @Test
    public void bigImageOnPageMarginTest01() {
        runTest("bigImageOnPageMarginTest01");
    }

    @Test
    public void bigImageOnPageMarginTest02() {
        runTest("bigImageOnPageMarginTest02");
    }

    @Test
    public void bigImageOnPageMarginTest03() {
        runTest("bigImageOnPageMarginTest03", new ConverterProperties().setTagWorkerFactory(new PageMarginBoxImagesTagWorkerFactory()));
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = com.itextpdf.io.LogMessageConstant.CLIP_ELEMENT, count= 4, logLevel = LogLevelConstants.WARN)
    })
    public void linearGradientOnPageMarginWithAutoWidthAndHeightTest() {
        runTest("linearGradientOnPageMarginWithAutoWidthAndHeightTest", new ConverterProperties().setTagWorkerFactory(new PageMarginBoxImagesTagWorkerFactory()));
    }


    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = com.itextpdf.io.LogMessageConstant.CLIP_ELEMENT, count= 2, logLevel = LogLevelConstants.WARN)})
    public void linearGradientOnPageMarginWithPercentWidthAndHeightTest() {
        runTest("linearGradientOnPageMarginWithPercentWidthAndHeightTest", new ConverterProperties().setTagWorkerFactory(new PageMarginBoxImagesTagWorkerFactory()));
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = com.itextpdf.io.LogMessageConstant.CLIP_ELEMENT, count= 2, logLevel = LogLevelConstants.WARN)
    })
    public void linearGradientOnPageMarginWithWidthAndHeightTest() {
        runTest("linearGradientOnPageMarginWithWidthAndHeightTest", new ConverterProperties().setTagWorkerFactory(new PageMarginBoxImagesTagWorkerFactory()));
    }

    private static class PageMarginBoxImagesTagWorkerFactory extends DefaultTagWorkerFactory {
        @Override
        public ITagWorker getCustomTagWorker(IElementNode tag, ProcessorContext context) {
            if (tag.name().equals(PageMarginBoxContextNode.PAGE_MARGIN_BOX_TAG)) {
                return new PageMarginBoxImagesWorker(tag, context);
            }
            return super.getCustomTagWorker(tag, context);
        }
    }

    private static class PageMarginBoxImagesWorker extends PageMarginBoxWorker {
        public PageMarginBoxImagesWorker(IElementNode element, ProcessorContext context) {
            super(element, context);
        }

        @Override
        public boolean processTagChild(ITagWorker childTagWorker, ProcessorContext context) {
            if (childTagWorker.getElementResult() instanceof Image) {
                // TODO Since iText 7.2 release it is ("it will be" for now, see PageMarginBoxDummyElement class) possible
                // to get current page margin box name and dimensions from the "element" IElementNode passed to the constructor of this tag worker.
                ((Image) childTagWorker.getElementResult()).setAutoScale(true);
            }
            return super.processTagChild(childTagWorker, context);
        }
    }

    @Test
    public void bigTextOnPageMarginTest01() {
        runTest("bigTextOnPageMarginTest01");
    }

    @Test
    public void bigTextOnPageMarginTest02() {
        runTest("bigTextOnPageMarginTest02");
    }

    @Test
    public void marginBoxOverflowPropertyTest01() {
        runTest("marginBoxOverflowPropertyTest01");
    }

    @Test
    public void marginBoxOverflowPropertyTest02() {
        runTest("marginBoxOverflowPropertyTest02", new ConverterProperties().setCssApplierFactory(new PageMarginsOverflowCssApplierFactory()));
    }

    private static class PageMarginsOverflowCssApplierFactory extends DefaultCssApplierFactory {
        @Override
        public ICssApplier getCustomCssApplier(IElementNode tag) {
            if (PageMarginBoxContextNode.PAGE_MARGIN_BOX_TAG.equals(tag.name())) {
                return new CustomOverflowPageMarginBoxCssApplier();
            }
            return super.getCustomCssApplier(tag);
        }
    }

    private static class CustomOverflowPageMarginBoxCssApplier extends PageMarginBoxCssApplier {
        @Override
        public void apply(ProcessorContext context, IStylesContainer stylesContainer, ITagWorker tagWorker) {
            Map<String, String> styles = stylesContainer.getStyles();
            if (styles.get(CssConstants.OVERFLOW) == null) {
                styles.put(CssConstants.OVERFLOW, CommonCssConstants.VISIBLE);
            }
            super.apply(context, stylesContainer, tagWorker);
        }
    }

    @Test
    public void marginBoxOutlinePropertyTest01() {
        // TODO Outlines are currently not supported for page margin boxes, because of the outlines handling specificity (they are handled on renderer's parent level).
        //      See com.itextpdf.html2pdf.attach.impl.layout.PageContextProcessor.
        runTest("marginBoxOutlinePropertyTest01");
    }

    @Test
    public void marginBoxRunningTest01() {
        runTest("marginBoxRunningTest01");
    }

    @Test
    public void marginBoxRunningTest02() {
        runTest("marginBoxRunningTest02");
    }

    @Test
    public void marginBoxRunningTest03() {
        runTest("marginBoxRunningTest03");
    }

    @Test
    public void marginBoxRunningTest04() {
        // TODO This tests shows wrong result, because running element name is custom-ident which shall be case sensitive, while iText treats it as case-insensitive.
        runTest("marginBoxRunningTest04");
    }

    @Test
    public void marginBoxRunningTest05() {
        runTest("marginBoxRunningTest05");
    }

    @Test
    public void marginBoxRunningTest06() {
        runTest("marginBoxRunningTest06");
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = Html2PdfLogMessageConstant.CONTENT_PROPERTY_INVALID))
    public void marginBoxRunningTest07() {
        runTest("marginBoxRunningTest07");
    }

    @Test
    public void marginBoxRunningTest08() {
        runTest("marginBoxRunningTest08");
    }

    @Test
    public void marginBoxRunningOverrideTest01() {
        runTest("marginBoxRunningOverrideTest01");
    }

    @Test
    public void marginBoxRunningOverrideTest02() {
        runTest("marginBoxRunningOverrideTest02");
    }

    @Test
    public void marginBoxRunningOverrideTest03() {
        runTest("marginBoxRunningOverrideTest03");
    }

    @Test
    public void marginBoxRunningOverrideTest04() {
        runTest("marginBoxRunningOverrideTest04");
    }

    @Test
    public void marginBoxRunningOverrideTest05() {
        runTest("marginBoxRunningOverrideTest05");
    }

    @Test
    public void marginBoxRunningOverrideTest06() {
        runTest("marginBoxRunningOverrideTest06");
    }

    @Test
    public void marginBoxRunningOverrideTest07() {
        runTest("marginBoxRunningOverrideTest07");
    }

    @Test
    public void marginBoxRunningOverrideTest08() {
        runTest("marginBoxRunningOverrideTest08");
    }

    @Test
    public void marginBoxRunningImg01() {
        runTest("marginBoxRunningImg01");
    }

    @Test
    public void marginBoxRunningImg02() {
        runTest("marginBoxRunningImg02");
    }

    @Test
    public void marginBoxRunningTable01() {
        runTest("marginBoxRunningTable01");
    }

    @Test
    public void marginBoxRunningLink01() {
        runTest("marginBoxRunningLink01");
    }

    @Test
    public void marginBoxRunningLink02() {
        runTest("marginBoxRunningLink02");
    }

    @Test
    public void marginBoxRunningLink03() {
        runTest("marginBoxRunningLink03");
    }

    @Test
    public void marginBoxRunningElements01() {
        runTest("marginBoxRunningElements01");
    }

    @Test
    public void marginBoxRunningElements02() {
        runTest("marginBoxRunningElements02");
    }

    @Test
    public void marginBoxRunningElements03() {
        runTest("marginBoxRunningElements03");
    }

    @Test
    public void marginBoxRunningElements04() {
        runTest("marginBoxRunningElements04");
    }

    @Test
    public void marginBoxRunningParent01() {
        runTest("marginBoxRunningParent01");
    }

    @Test
    public void marginBoxRunningParent02() {
        runTest("marginBoxRunningParent02");
    }

    @Test
    public void marginBoxRunningParent03() {
        runTest("marginBoxRunningParent03");
    }

    @Test
    public void marginBoxRunningParent04() {
        runTest("marginBoxRunningParent04");
    }

    @Test
    public void marginBoxRunningParent05() {
        runTest("marginBoxRunningParent05");
    }

    @Test
    public void marginBoxRunningParent06() {
        runTest("marginBoxRunningParent06");
    }

    @Test
    public void marginBoxRunningParent07() {
        runTest("marginBoxRunningParent07");
    }

    @Test
    public void marginBoxRunningParent08() {
        runTest("marginBoxRunningParent08");
    }

    @Test
    public void marginBoxRunningPageBreak01() {
        runTest("marginBoxRunningPageBreak01");
    }

    @Test
    public void marginBoxRunningPageBreak02() {
        runTest("marginBoxRunningPageBreak02");
    }

    @Test
    public void marginBoxRunningPageBreak03() {
        runTest("marginBoxRunningPageBreak03");
    }

    @Test
    public void marginBoxRunningPageBreakAvoid01() {
        runTest("marginBoxRunningPageBreakAvoid01");
    }

    @Test
    public void marginBoxRunningOutlines01() {
        runTest("marginBoxRunningOutlines01", new ConverterProperties().setOutlineHandler(OutlineHandler.createStandardHandler()));
    }

    @Test
    public void marginBoxRunningOutlines02() {
        runTest("marginBoxRunningOutlines02", new ConverterProperties().setOutlineHandler(OutlineHandler.createStandardHandler()));
    }

    @Test
    public void marginBoxRunningQuotes01() {
        runTest("marginBoxRunningQuotes01");
    }

    @Test
    public void marginBoxRunningQuotes02() {
        runTest("marginBoxRunningQuotes02");
    }

    @Test
    public void marginBoxRunningQuotes03() {
        runTest("marginBoxRunningQuotes03");
    }

    @Test
    public void marginBoxRunningQuotes04() {
        runTest("marginBoxRunningQuotes04");
    }

    @Test
    public void checkMarginBoxFixDimension() {
        runTest("checkMarginBoxFixDimension");
    }

    @Test
    public void checkMarginBoxMaxMinDimension() {
        runTest("checkMarginBoxMaxMinDimension");
    }

    @Test
    public void checkMarginBoxMarginPaddings() {
        runTest("checkMarginBoxMarginPaddings");
    }

    @Test
    public void mediaAppliedToRunningElementsProperties() {
        MediaDeviceDescription printMediaDevice = new MediaDeviceDescription("print");
        ConverterProperties converterProperties = new ConverterProperties().setMediaDeviceDescription(printMediaDevice);
        runTest("mediaAppliedToRunningElementsProperties", converterProperties);
    }

    @Test
    public void mediaNotAppliedToRunningElementsProperties() {
        runTest("mediaNotAppliedToRunningElementsProperties");
    }

    @Test
    public void marginBoxTaggedTest01() throws IOException, InterruptedException, ParserConfigurationException, SAXException {
        runTest("marginBoxTaggedTest01", null, true);
    }

    @Test
    public void marginBoxTaggedTest02() throws IOException, InterruptedException, ParserConfigurationException, SAXException {
        runTest("marginBoxTaggedTest02", null, true);
    }

    @Test
    public void marginBoxTaggedTest03() throws IOException, InterruptedException, ParserConfigurationException, SAXException {
        runTest("marginBoxTaggedTest03", new ConverterProperties().setTagWorkerFactory(new TaggedPageMarginBoxTagWorkerFactory()), true);
    }

    @Test
    public void marginBoxTaggedTest04() throws IOException, InterruptedException, ParserConfigurationException, SAXException {
        runTest("marginBoxTaggedTest04", new ConverterProperties().setTagWorkerFactory(new TaggedPageMarginBoxTagWorkerFactory()), true);
    }

    private static class TaggedPageMarginBoxTagWorkerFactory extends DefaultTagWorkerFactory {
        @Override
        public ITagWorker getCustomTagWorker(IElementNode tag, ProcessorContext context) {
            if (tag.name().equals(PageMarginBoxContextNode.PAGE_MARGIN_BOX_TAG)) {
                return new TaggedPageMarginBoxWorker(tag, context);
            }
            return super.getCustomTagWorker(tag, context);
        }
    }

    private static class TaggedPageMarginBoxWorker extends PageMarginBoxWorker {
        public TaggedPageMarginBoxWorker(IElementNode element, ProcessorContext context) {
            super(element, context);
        }

        @Override
        public void processEnd(IElementNode element, ProcessorContext context) {
            super.processEnd(element, context);

            if (getElementResult() instanceof IAccessibleElement) {
                ((IAccessibleElement)getElementResult()).getAccessibilityProperties().setRole(StandardRoles.DIV);
            }
        }
    }
    @Test
    public void marginBoxRunningNoImmediateFlush01() throws IOException, InterruptedException {
        String name = "marginBoxRunningNoImmediateFlush01";
        String htmlPath = SOURCE_FOLDER + name + ".html";
        String pdfPath = DESTINATION_FOLDER + name + ".pdf";

        ConverterProperties converterProperties = new ConverterProperties().setImmediateFlush(false);
        Document doc = HtmlConverter.convertToDocument(new FileInputStream(htmlPath), new PdfWriter(pdfPath), converterProperties);
        doc.close();
        compareResult(name);
    }

    @Test
    public void marginBoxRunningNoImmediateFlush02() throws IOException, InterruptedException {
        String name = "marginBoxRunningNoImmediateFlush02";
        String htmlPath = SOURCE_FOLDER + name + ".html";
        String pdfPath = DESTINATION_FOLDER + name + ".pdf";

        ConverterProperties converterProperties = new ConverterProperties().setImmediateFlush(false);
        Document doc = HtmlConverter.convertToDocument(new FileInputStream(htmlPath), new PdfWriter(pdfPath), converterProperties);
        doc.setMargins(120f, 120f, 120f, 120f);
        doc.relayout();
        doc.relayout(); // relayouting second time in order to fix total number of pages
        doc.close();
        compareResult(name);
    }

    @Test
    public void marginBoxRunningNoImmediateFlush03() throws IOException, InterruptedException {
        String name = "marginBoxRunningNoImmediateFlush03";
        String htmlPath = SOURCE_FOLDER + name + ".html";
        String pdfPath = DESTINATION_FOLDER + name + ".pdf";

        ConverterProperties converterProperties = new ConverterProperties().setImmediateFlush(false);
        Document doc = HtmlConverter.convertToDocument(new FileInputStream(htmlPath), new PdfWriter(pdfPath), converterProperties);

        // TODO This is kinda a workaround, because calling document.close() would close the whole document,
        // which would forbid any further operations with it, however in html2pdf some things are waiting for document to be closed and finished:
        // - adding last waiting element (connected with keep_with_previous functionality);
        // - drawing margin boxes for the last page.
        doc.getRenderer().close();

        int pagesNum = doc.getPdfDocument().getNumberOfPages();
        Assert.assertTrue(pagesNum > 1);
        int k = 1;
        for (int i = pagesNum; i > 0 ; --i) {
            doc.getPdfDocument().movePage(pagesNum, k++);
        }
        doc.getPdfDocument().close(); // closing PdfDocument directly, in order to avoid second call for document renderer closing
        compareResult(name);
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = com.itextpdf.io.LogMessageConstant.REMOVING_PAGE_HAS_ALREADY_BEEN_FLUSHED, count = 6))
    public void marginBoxRunningNoImmediateFlush04() throws IOException, InterruptedException {
        String name = "marginBoxRunningNoImmediateFlush04";
        String htmlPath = SOURCE_FOLDER + name + ".html";
        String pdfPath = DESTINATION_FOLDER + name + ".pdf";

        ConverterProperties converterProperties = new ConverterProperties().setImmediateFlush(false);
        Document doc = HtmlConverter.convertToDocument(new FileInputStream(htmlPath), new PdfWriter(pdfPath), converterProperties);
        doc.flush();
        for (int i = 1; i <= doc.getPdfDocument().getNumberOfPages(); ++i) {
            doc.getPdfDocument().getPage(i).flush();
        }
        doc.relayout();
        doc.close();
        compareResult(name);
    }


    @Test
    public void marginBoxRunningNoImmediateFlush05() throws IOException, InterruptedException {
        String name = "marginBoxRunningNoImmediateFlush05";
        String htmlPath = SOURCE_FOLDER + name + ".html";
        String pdfPath = DESTINATION_FOLDER + name + ".pdf";

        ConverterProperties converterProperties = new ConverterProperties().setImmediateFlush(false);
        converterProperties.setTagWorkerFactory(new CustomFlushingTagWorkerFactory());
        HtmlConverter.convertToPdf(new FileInputStream(htmlPath), new PdfWriter(pdfPath), converterProperties);
        compareResult(name);
    }

    @Test
    public void marginBoxMultilineTest01() {
        runTest("marginBoxMultilineTest01");
    }

    @Test
    public void marginBoxMultilineTest02() {
        runTest("marginBoxMultilineTest02");
    }

    @Test
    public void marginBoxMultilineTest03() {
        runTest("marginBoxMultilineTest03");
    }

    @Test
    public void marginBoxMultilineTest04() {
        runTest("marginBoxMultilineTest04");
    }

    @Test
    public void wrongPageRuleCssStructureTest() {
        junitExpectedException.expect(RuntimeException.class);
        runTest("wrongPageRuleCssStructureTest");
    }

    @Test
    //TODO: DEVSIX-1570, SUP-2322. Remove junitExpectedException after the fix.
    public void pageCountTestTableAlignLeft() throws IOException {
        junitExpectedException.expect(AssertionError.class);

        String expectedText = "Page 1 of 3";
        String nameAlignLeft = "htmlWithTableAlignLeft.html";
        String pdfOutputName = DESTINATION_FOLDER + nameAlignLeft + ".pdf";

        File pdfOutputAlignLeft = new File(pdfOutputName);
        HtmlConverter.convertToPdf(new File(SOURCE_FOLDER + nameAlignLeft), pdfOutputAlignLeft);

        PdfDocument pdfDocument = new PdfDocument(new PdfReader(pdfOutputAlignLeft));
        String textFromPage = PdfTextExtractor.getTextFromPage(pdfDocument.getPage(1));

        // Print the output file. No comparison will be made on the following line
        ExtendedITextTest.printOutputPdfNameAndDir(pdfOutputName);

        Assert.assertTrue("Page doesn't contain text " + expectedText, textFromPage.contains(expectedText));
    }

    @Test
    //TODO: DEVSIX-1570, SUP-2322. Remove junitExpectedException after the fix.
    public void pageCountTestTableFloatLeft() throws IOException {
        junitExpectedException.expect(AssertionError.class);

        String expectedText = "Page 3 of 3";
        String nameFloatLeft = "htmlWithTableFloatLeft.html";
        String pdfOutputName = DESTINATION_FOLDER + nameFloatLeft + ".pdf";

        File pdfOutputFloatLeft = new File(pdfOutputName);
        HtmlConverter.convertToPdf(new File(SOURCE_FOLDER + nameFloatLeft), pdfOutputFloatLeft);

        PdfDocument pdfDocument = new PdfDocument(new PdfReader(pdfOutputFloatLeft));
        String textFromPage = PdfTextExtractor.getTextFromPage(pdfDocument.getPage(3));

        // Print the output file. No comparison will be made on the following line
        ExtendedITextTest.printOutputPdfNameAndDir(pdfOutputName);
        
        Assert.assertTrue("Page doesn't contain text " + expectedText, textFromPage.contains(expectedText));
    }

    private static class CustomFlushingTagWorkerFactory extends DefaultTagWorkerFactory {
        @Override
        public ITagWorker getCustomTagWorker(IElementNode tag, ProcessorContext context) {
            if (tag.name().equals(TagConstants.HTML)) {
                return new CustomFlushingHtmlTagWorker(tag, context);
            }
            return super.getCustomTagWorker(tag, context);
        }
    }

    private static class CustomFlushingHtmlTagWorker extends HtmlTagWorker {
        public CustomFlushingHtmlTagWorker(IElementNode tag, ProcessorContext context) {
            super(tag, context);
        }

        @Override
        public boolean processTagChild(ITagWorker childTagWorker, ProcessorContext context) {
            Document document = (Document) getElementResult();
            if (document.getPdfDocument().getNumberOfPages() % 3 == 0) {
                document.flush();
                for (int i = 1; i < document.getPdfDocument().getNumberOfPages(); ++i) {
                    document.getPdfDocument().getPage(i).flush();
                }
            }
            return super.processTagChild(childTagWorker, context);
        }
    }


    private void runTest(String name) {
        runTest(name, null);
    }

    private void runTest(String name, ConverterProperties converterProperties) {
        try {
            runTest(name, converterProperties, false);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private void runTest(String name, ConverterProperties converterProperties, boolean isTagged) throws IOException, InterruptedException, ParserConfigurationException, SAXException {
        String htmlPath = SOURCE_FOLDER + name + ".html";
        String pdfPath = DESTINATION_FOLDER + name + ".pdf";
        String cmpPdfPath = SOURCE_FOLDER + "cmp_" + name + ".pdf";
        String diffPrefix = "diff_" + name + "_";

        File outFile = new File(pdfPath);

        System.out.println("html: " + UrlUtil.getNormalizedFileUriString(htmlPath) + "\n");

        if (converterProperties == null) {
            converterProperties = new ConverterProperties();
        }
        if (converterProperties.getBaseUri() == null) {
            converterProperties.setBaseUri(UrlUtil.getFileUriString(htmlPath));
        }

        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outFile));

        if (isTagged) {
            pdfDocument.setTagged();
        }

        HtmlConverter.convertToPdf(new FileInputStream(htmlPath), pdfDocument, converterProperties);
        CompareTool compareTool = new CompareTool();
        if (isTagged) {
            compareTool.compareTagStructures(pdfPath, cmpPdfPath);
        }
        Assert.assertNull(compareTool.compareByContent(pdfPath, cmpPdfPath, DESTINATION_FOLDER, diffPrefix));
    }

    private void compareResult(String name) throws InterruptedException, IOException {
        String pdfPath = DESTINATION_FOLDER + name + ".pdf";
        String cmpPdfPath = SOURCE_FOLDER + "cmp_" + name + ".pdf";
        String diffPrefix = "diff_" + name + "_";
        Assert.assertNull(new CompareTool().compareByContent(pdfPath, cmpPdfPath, DESTINATION_FOLDER, diffPrefix));
    }
}
