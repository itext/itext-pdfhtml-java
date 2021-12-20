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
package com.itextpdf.html2pdf.attribute;

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.io.logs.IoLogMessageConstant;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class DirAttributeTest extends ExtendedHtmlConversionITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/attribute/DirAttributeTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/attribute/DirAttributeTest/";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    // TODO DEVSIX-5034 Direction of the contents of description list items with dir = "rtl" is wrong
    @LogMessages(messages = {
            @LogMessage(messageTemplate = IoLogMessageConstant.TYPOGRAPHY_NOT_FOUND, count = 8),
    })
    public void differentDirsOfDlsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("differentDirsOfDls", sourceFolder, destinationFolder, false);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = IoLogMessageConstant.TYPOGRAPHY_NOT_FOUND, count = 18),
    })
    public void differentDirsOfOrderedListsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("differentDirsOfOrderedLists", sourceFolder, destinationFolder);
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = IoLogMessageConstant.TYPOGRAPHY_NOT_FOUND, count = 18),
    })
    public void differentDirsOfUnorderedListsTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("differentDirsOfUnorderedLists", sourceFolder, destinationFolder);
    }

    @Test
    //TODO: DEVSIX-2438 html2Pdf: float + rtl works incorrectly for element placement
    @LogMessages(messages = {
            @LogMessage(messageTemplate = IoLogMessageConstant.TYPOGRAPHY_NOT_FOUND, count = 16),
    })
    public void floatedTableInRtlDocumentTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("floatedTableInRtlDocument", sourceFolder, destinationFolder, false);
    }

    @Test
    //TODO: DEVSIX-2435 Process several elements which do not respect the specified direction
    @LogMessages(messages = {
            @LogMessage(messageTemplate = IoLogMessageConstant.TYPOGRAPHY_NOT_FOUND, count = 4),
    })
    public void paragraphsOfDifferentDirsWithImageTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("paragraphsOfDifferentDirsWithImage", sourceFolder, destinationFolder, false);
    }

    @Test
    //TODO: DEVSIX-2435 Process several elements which do not respect the specified direction
    @LogMessages(messages = {
            @LogMessage(messageTemplate = IoLogMessageConstant.TYPOGRAPHY_NOT_FOUND, count = 4),
    })
    public void rtlDirectionOfLinkTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("rtlDirectionOfLink", sourceFolder, destinationFolder);
    }

    @Test
    //TODO: DEVSIX-2435 Process several elements which do not respect the specified direction
    @LogMessages(messages = {
            @LogMessage(messageTemplate = IoLogMessageConstant.TYPOGRAPHY_NOT_FOUND, count = 26),
    })
    public void rtlDirectionOfListInsideListTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("rtlDirectionOfListInsideList", sourceFolder, destinationFolder, false);
    }

    @Test
    //TODO: DEVSIX-2435 Process several elements which do not respect the specified direction
    @LogMessages(messages = {
            @LogMessage(messageTemplate = IoLogMessageConstant.TYPOGRAPHY_NOT_FOUND, count = 4),
    })
    public void rtlDirectionOfSpanTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("rtlDirectionOfSpan", sourceFolder, destinationFolder);
    }

    @Test
    public void rtlDir01Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("rtlDirTest01", sourceFolder, destinationFolder);
    }

    @Test
    public void rtlDir02Test() throws IOException, InterruptedException {
        convertToPdfAndCompare("rtlDirTest02", sourceFolder, destinationFolder);
    }

    @Test
    //TODO DEVSIX-2437 dir ltr is ignored in rtl documents
    public void spansOfDifferentDirsInsideParagraphTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("spansOfDifferentDirsInsideParagraph", sourceFolder, destinationFolder, false);
    }

    @Test
    //TODO DEVSIX-3069 pdfHTML: RTL tables are not aligned correctly if there is no enough space
    @LogMessages(messages = {
            @LogMessage(messageTemplate = IoLogMessageConstant.TYPOGRAPHY_NOT_FOUND, count = 32),
            @LogMessage(messageTemplate = IoLogMessageConstant.TABLE_WIDTH_IS_MORE_THAN_EXPECTED_DUE_TO_MIN_WIDTH)})
    public void tableAlignedToWrongSideInCaseOfNotEnoughSpaceTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("tableAlignedToWrongSideInCaseOfNotEnoughSpace", sourceFolder, destinationFolder);
    }
}
