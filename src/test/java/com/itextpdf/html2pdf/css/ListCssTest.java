/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2023 iText Group NV
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
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.test.annotations.type.IntegrationTest;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.IOException;

@Category(IntegrationTest.class)
public class ListCssTest extends ExtendedHtmlConversionITextTest {
    private static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css/ListCSSTest/";
    private static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/css/ListCSSTest/";

    @BeforeClass
    public static void beforeClass() {
        createOrClearDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void listCSSStartTest01() throws IOException, InterruptedException {
        HtmlConverter.convertToPdf(new File(SOURCE_FOLDER + "orderedList.html"), new File(DESTINATION_FOLDER + "orderedList01.pdf"));
        Assert.assertNull(new CompareTool().compareByContent(DESTINATION_FOLDER + "orderedList01.pdf", SOURCE_FOLDER + "cmp_orderedList01.pdf", DESTINATION_FOLDER, "diff02_"));
    }

    @Test
    public void lowercaseATypeTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("aType", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void uppercaseATypeTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("aAType", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void lowercaseITypeTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("iType", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void uppercaseITypeTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("iIType", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void digitTypeTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("1Type", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    //TODO: DEVSIX-6128 NullPointerException when trying to convert html with non-existing ol type.
    @Test
    public void unsupportedType() {
        Assert.assertThrows(NullPointerException.class,
                () -> convertToPdfAndCompare("unsupportedType", SOURCE_FOLDER, DESTINATION_FOLDER));
    }

    @Test
    public void horizontalDescriptionListTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("horizontalDescriptionList", SOURCE_FOLDER, DESTINATION_FOLDER);
    }
}
