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
package com.itextpdf.html2pdf.css;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.util.UrlUtil;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.test.ExtendedITextTest;

import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

@Tag("IntegrationTest")
public class CssCollapsingMarginsTest extends ExtendedITextTest {
    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/CssCollapsingMarginsTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/html2pdf/css/CssCollapsingMarginsTest/";

    @BeforeAll
    public static void beforeClass() {
        createOrClearDestinationFolder(destinationFolder);
    }

    @Test
    public void collapsingTest01() throws IOException, InterruptedException {
        test("collapsingTest01.html", "collapsingTest01.pdf", "diff_");
    }

    @Test
    public void collapsingTest02() throws IOException, InterruptedException {
        test("collapsingTest02.html", "collapsingTest02.pdf", "diff_");
    }

    @Test
    public void collapsingTest03() throws IOException, InterruptedException {
        test("collapsingTest03.html", "collapsingTest03.pdf", "diff_");
    }

    @Test
    public void collapsingTest04() throws IOException, InterruptedException {
        test("collapsingTest04.html", "collapsingTest04.pdf", "diff_");
    }

    @Test
    public void collapsingTest05() throws IOException, InterruptedException {
        // "min-height" property affects margins collapse differently in chrome and mozilla. While in chrome, this property
        // seems to not have any effect on collapsing margins at all (child margins collapse with parent margins even if
        // there is a considerable space between them due to the min-height property on parent), mozilla behaves better
        // and collapse happens only in case min-height of parent is less than actual height of the content and therefore
        // collapse really should happen. However even in mozilla, if parent has min-height which is a little bigger then
        // it's content actual height, margin collapse doesn't happen, however child margin doesn't have any effect either.

        test("collapsingTest05.html", "collapsingTest05.pdf", "diff_");
    }

    @Test
    public void collapsingTest06() throws IOException, InterruptedException {
        test("collapsingTest06.html", "collapsingTest06.pdf", "diff_");
    }

    @Test
    public void collapsingTest07() throws IOException, InterruptedException {
        test("collapsingTest07.html", "collapsingTest07.pdf", "diff_");
    }

    @Test
    public void collapsingTest07_malformed() throws IOException, InterruptedException {
        test("collapsingTest07_malformed.html", "collapsingTest07_malformed.pdf", "diff_");
    }

    @Test
    public void collapsingTest07_malformed2() throws IOException, InterruptedException {
        test("collapsingTest07_malformed2.html", "collapsingTest07_malformed2.pdf", "diff_");
    }

    @Test
    public void collapsingTest08() throws IOException, InterruptedException {
        test("collapsingTest08.html", "collapsingTest08.pdf", "diff_");
    }

    @Test
    public void collapsingTest09() throws IOException, InterruptedException {
        test("collapsingTest09.html", "collapsingTest09.pdf", "diff_");
    }

    @Test
    public void collapsingTest10() throws IOException, InterruptedException {
        test("collapsingTest10.html", "collapsingTest10.pdf", "diff_");
    }

    @Test
    public void collapsingTest11() throws IOException, InterruptedException {
        test("collapsingTest11.html", "collapsingTest11.pdf", "diff_");
    }

    @Test
    public void collapsingTest12() throws IOException, InterruptedException {
        test("collapsingTest12.html", "collapsingTest12.pdf", "diff_");
    }

    @Test
    public void elementTableTest() throws IOException, InterruptedException {
        // empty tables don't self-collapse in browsers
        test("elementTableTest.html", "elementTableTest.pdf", "diff_");
    }

    @Test
    public void elementUlOlLiTest() throws IOException, InterruptedException {
        test("elementUlOlLiTest.html", "elementUlOlLiTest.pdf", "diff_");
    }

    @Test
    public void emptyInlinesTest01() throws IOException, InterruptedException {
        test("emptyInlinesTest01.html", "emptyInlinesTest01.pdf", "diff_");
    }

    @Test
    public void emptyInlinesTest02() throws IOException, InterruptedException {
        test("emptyInlinesTest02.html", "emptyInlinesTest02.pdf", "diff_");
    }

    @Test
    public void negativeMarginsTest01() throws IOException, InterruptedException {
        test("negativeMarginsTest01.html", "negativeMarginsTest01.pdf", "diff_");
    }

    @Test
    public void negativeMarginsTest02() throws IOException, InterruptedException {
        test("negativeMarginsTest02.html", "negativeMarginsTest02.pdf", "diff_");
    }

    @Test
    public void negativeMarginsTest03() throws IOException, InterruptedException {
        test("negativeMarginsTest03.html", "negativeMarginsTest03.pdf", "diff_");
    }

    @Test
    public void notSanitizedTest01() throws IOException, InterruptedException {
        test("notSanitizedTest01.html", "notSanitizedTest01.pdf", "diff_");
    }

    @Test
    public void notSanitizedTest02() throws IOException, InterruptedException {
        test("notSanitizedTest02.html", "notSanitizedTest02.pdf", "diff_");
    }

    @Test
    public void selfCollapsingEmptyInlinesTest01() throws IOException, InterruptedException {
        test("selfCollapsingEmptyInlinesTest01.html", "selfCollapsingEmptyInlinesTest01.pdf", "diff_");
    }

    @Test
    public void selfCollapsingEmptyInlinesTest02() throws IOException, InterruptedException {
        test("selfCollapsingEmptyInlinesTest02.html", "selfCollapsingEmptyInlinesTest02.pdf", "diff_");
    }

    @Test
    public void selfCollapsingEmptyInlinesTest03() throws IOException, InterruptedException {
        test("selfCollapsingEmptyInlinesTest03.html", "selfCollapsingEmptyInlinesTest03.pdf", "diff_");
    }

    @Test
    public void selfCollapsingTest01() throws IOException, InterruptedException {
        test("selfCollapsingTest01.html", "selfCollapsingTest01.pdf", "diff_");
    }

    @Test
    public void selfCollapsingTest02() throws IOException, InterruptedException {
        test("selfCollapsingTest02.html", "selfCollapsingTest02.pdf", "diff_");
    }

    @Test
    public void selfCollapsingTest03() throws IOException, InterruptedException {
        test("selfCollapsingTest03.html", "selfCollapsingTest03.pdf", "diff_");
    }

    @Test
    public void selfCollapsingTest04() throws IOException, InterruptedException {
        test("selfCollapsingTest04.html", "selfCollapsingTest04.pdf", "diff_");
    }

    @Test
    public void selfCollapsingTest05() throws IOException, InterruptedException {
        test("selfCollapsingTest05.html", "selfCollapsingTest05.pdf", "diff_");
    }

    @Test
    public void selfCollapsingTest06() throws IOException, InterruptedException {
        test("selfCollapsingTest06.html", "selfCollapsingTest06.pdf", "diff_");
    }

    @Test
    public void selfCollapsingTest07() throws IOException, InterruptedException {
        test("selfCollapsingTest07.html", "selfCollapsingTest07.pdf", "diff_");
    }

    @Test
    public void selfCollapsingTest07_zero() throws IOException, InterruptedException {
        test("selfCollapsingTest07_zero.html", "selfCollapsingTest07_zero.pdf", "diff_");
    }

    @Test
    public void selfCollapsingTest08() throws IOException, InterruptedException {
        test("selfCollapsingTest08.html", "selfCollapsingTest08.pdf", "diff_");
    }

    @Test
    public void selfCollapsingTest08_zero() throws IOException, InterruptedException {
        test("selfCollapsingTest08_zero.html", "selfCollapsingTest08_zero.pdf", "diff_");
    }

    @Test
    public void selfCollapsingTest09() throws IOException, InterruptedException {
        test("selfCollapsingTest09.html", "selfCollapsingTest09.pdf", "diff_");
    }

    @Test
    public void selfCollapsingTest10() throws IOException, InterruptedException {
        test("selfCollapsingTest10.html", "selfCollapsingTest10.pdf", "diff_");
    }

    @Test
    public void selfCollapsingTest11() throws IOException, InterruptedException {
        test("selfCollapsingTest11.html", "selfCollapsingTest11.pdf", "diff_");
    }

    @Test
    public void selfCollapsingTest12() throws IOException, InterruptedException {
        test("selfCollapsingTest12.html", "selfCollapsingTest12.pdf", "diff_");
    }

    @Test
    public void selfCollapsingTest13() throws IOException, InterruptedException {
        test("selfCollapsingTest13.html", "selfCollapsingTest13.pdf", "diff_");
    }

    @Test
    public void selfCollapsingTest14() throws IOException, InterruptedException {
        test("selfCollapsingTest14.html", "selfCollapsingTest14.pdf", "diff_");
    }

    @Test
    public void selfCollapsingTest15() throws IOException, InterruptedException {
        test("selfCollapsingTest15.html", "selfCollapsingTest15.pdf", "diff_");
    }

    @Test
    public void selfCollapsingTest16() throws IOException, InterruptedException {
        test("selfCollapsingTest16.html", "selfCollapsingTest16.pdf", "diff_");
    }

    @Test
    public void selfCollapsingTest17() throws IOException, InterruptedException {
        test("selfCollapsingTest17.html", "selfCollapsingTest17.pdf", "diff_");
    }

    @Test
    public void selfCollapsingTest19() throws IOException, InterruptedException {
        test("selfCollapsingTest19.html", "selfCollapsingTest19.pdf", "diff_");
    }

    @Test
    public void collapsingMarginsFloatTest01() throws IOException, InterruptedException {
        test("collapsingMarginsFloatTest01.html", "collapsingMarginsFloatTest01.pdf", "diff_");
    }

    @Test
    public void collapsingMarginsFloatTest02() throws IOException, InterruptedException {
        test("collapsingMarginsFloatTest02.html", "collapsingMarginsFloatTest02.pdf", "diff_");
    }

    @Test
    public void collapsingMarginsFloatTest03() throws IOException, InterruptedException {
        test("collapsingMarginsFloatTest03.html", "collapsingMarginsFloatTest03.pdf", "diff_");
    }

    @Test
    public void collapsingMarginsFloatTest04() throws IOException, InterruptedException {
        test("collapsingMarginsFloatTest04.html", "collapsingMarginsFloatTest04.pdf", "diff_");
    }

    @Test
    public void collapsingMarginsFloatTest05() throws IOException, InterruptedException {
        test("collapsingMarginsFloatTest05.html", "collapsingMarginsFloatTest05.pdf", "diff_");
    }

    @Test
    public void collapsingMarginsFloatTest06() throws IOException, InterruptedException {
        test("collapsingMarginsFloatTest06.html", "collapsingMarginsFloatTest06.pdf", "diff_");
    }

    @Test
    public void collapsingMarginsFloatTest07() throws IOException, InterruptedException {
        test("collapsingMarginsFloatTest07.html", "collapsingMarginsFloatTest07.pdf", "diff_");
    }

    @Test
    public void collapsingMarginsFloatTest08() throws IOException, InterruptedException {
        // TODO DEVSIX-1820: on floats positioning collapsing margins of parent and first child is not taken into account
        test("collapsingMarginsFloatTest08.html", "collapsingMarginsFloatTest08.pdf", "diff_");
    }

    @Test
    public void collapsingMarginsFloatTest09() throws IOException, InterruptedException {
        // TODO DEVSIX-1820: on floats positioning collapsing margins of parent and first child is not taken into account
        test("collapsingMarginsFloatTest09.html", "collapsingMarginsFloatTest09.pdf", "diff_");
    }

    @Test
    public void collapsingMarginsImageTest() throws IOException, InterruptedException {
        test("collapsingMarginsImage.html", "collapsingMarginsImage.pdf", "diff_");
    }

    @Test
    public void collapsingMarginsImgInNestedDivsTest() throws IOException, InterruptedException {
        test("collapsingMarginsImgInNestedDivs.html", "collapsingMarginsImgInNestedDivs.pdf", "diff_");
    }

    @Test
    public void selfCollapsingWithImageTest() throws IOException, InterruptedException {
        test("selfCollapsingWithImage.html", "selfCollapsingWithImage.pdf", "diff_");
    }

    private void test(String in, String out, String diff) throws IOException, InterruptedException {
        String outPdf = destinationFolder + out;
        String cmpPdf = sourceFolder + "cmp_" + out;

        File srcFile = new File(sourceFolder + in);
        File destFile = new File(outPdf);
        HtmlConverter.convertToPdf(srcFile, destFile);

        System.out.println("html: " + UrlUtil.getNormalizedFileUriString(srcFile.getAbsolutePath()) + "\n");
        Assertions.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder, diff));
    }

}
