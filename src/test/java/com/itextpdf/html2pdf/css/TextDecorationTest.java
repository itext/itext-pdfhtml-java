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
package com.itextpdf.html2pdf.css;

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.html2pdf.LogMessageConstant;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import com.itextpdf.test.annotations.type.IntegrationTest;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.IOException;

@Category(IntegrationTest.class)
public class TextDecorationTest extends ExtendedHtmlConversionITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/TextDecorationTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/TextDecorationTest/";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void textDecoration01Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("textDecorationTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void textDecoration02Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("textDecorationTest02", sourceFolder, destinationFolder);
    }

    @Test
    public void textDecoration03Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("textDecorationTest03", sourceFolder, destinationFolder);
    }

    //Text decoration property is in defaults.css for a[href], should be replaced by css.
    @Test
    public void textDecoration04Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("textDecorationTest04", sourceFolder, destinationFolder);
    }

    @Test
    // TODO DEVSIX-2532
    public void textDecoration05Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("textDecorationTest05", sourceFolder, destinationFolder);
    }

    @Test
    // TODO DEVSIX-3933
    public void textDecorationShorthandAllValuesTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("textDecorationShorthandAllValues", sourceFolder, destinationFolder);
    }

    @Test
    // TODO DEVSIX-3933
    public void textDecorationShorthandOneValueTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("textDecorationShorthandOneValue", sourceFolder, destinationFolder);
    }

    @Test
    // TODO DEVSIX-3933
    public void textDecorationShorthandTwoValuesTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("textDecorationShorthandTwoValues", sourceFolder, destinationFolder);
    }

    @Test
    public void textDecorationWithChildElementTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("textDecorationWithChildElement", sourceFolder, destinationFolder);
    }

    @Test
    //TODO: DEVSIX-3933
    @LogMessages(messages = {
            @LogMessage(messageTemplate = LogMessageConstant.HSL_COLOR_NOT_SUPPORTED)})
    public void textDecorationColorTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("textDecorationColor", sourceFolder, destinationFolder);
    }

    @Test
    public void textDecorationColorWithTransparencyTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("textDecorationColorWithTransparency", sourceFolder, destinationFolder);
    }

    @Test
    //TODO: DEVSIX-3933
    public void textDecorationLineTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("textDecorationLine", sourceFolder, destinationFolder);
    }

    @Test
    public void textDecorationLineNoneAndUnderlineTogetherTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("textDecorationLineNoneAndUnderlineTogether", sourceFolder, destinationFolder);
    }

    @Test
    //TODO: DEVSIX-3933
    public void textDecorationStyleTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("textDecorationStyle", sourceFolder, destinationFolder);
    }

    @Test
    //TODO: DEVSIX-3933
    public void shorthandAndSpecificTextDecorPropsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("shorthandAndSpecificTextDecorProps", sourceFolder, destinationFolder);
    }

    @Test
    //TODO: DEVSIX-3933
    public void combinationOfLinesInTextDecorationTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("combinationOfLinesInTextDecoration", sourceFolder, destinationFolder);
    }
}
