/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2023 Apryse Group NV
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
package com.itextpdf.html2pdf;

import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class HtmlConverterMultiThreadedTest extends ExtendedITextTest {

    public static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/HtmlConverterMultiThreadedTest/";

    @Test
    public void multiThreadedHtmlToPdfConversionTest() throws InterruptedException {
        int runcount = 75;
        List<Future<String>> futures = new ArrayList<>(runcount);
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        for (int i = 0; i < runcount; i++) {
            futures.add(executorService.submit(() ->
            {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                HtmlConverter.convertToPdf(SOURCE_FOLDER + "basicHtml.html", out);
                return "";
            }));
        }

        executorService.shutdown();
        Assert.assertTrue(executorService.awaitTermination(2, TimeUnit.MINUTES));
    }

}
