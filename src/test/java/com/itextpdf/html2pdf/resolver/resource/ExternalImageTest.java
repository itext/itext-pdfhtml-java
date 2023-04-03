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
package com.itextpdf.html2pdf.resolver.resource;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.xobject.PdfXObject;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.layout.element.Image;
import com.itextpdf.styledxmlparser.resolver.resource.ResourceResolver;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.type.IntegrationTest;

import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class ExternalImageTest extends ExtendedITextTest {
    public static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/resolver/resource/ExternalImageTest/";

    @Test
    // Android-Conversion-Ignore-Test (TODO DEVSIX-6459 fix the SecurityException(Permission denied) from UrlUtil method)
    public void test() throws IOException {
        ResourceResolver resourceResolver = new ResourceResolver("");
        PdfXObject externalImage = resourceResolver.retrieveImage(
                "https://raw.githubusercontent.com/itext/itext7/develop/layout/src/test/resources/com/itextpdf/layout/ImageTest/itis.jpg");

        Assert.assertNotNull(externalImage);

        ImageData imageData = ImageDataFactory.create(sourceFolder + "itis.jpg");
        Image localImage = new Image(imageData);

        Assert.assertTrue(new CompareTool().compareStreams(externalImage.getPdfObject(),
                localImage.getXObject().getPdfObject()));
    }
}
