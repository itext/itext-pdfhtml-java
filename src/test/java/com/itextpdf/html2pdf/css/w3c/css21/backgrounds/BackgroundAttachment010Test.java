/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2020 iText Group NV
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
package com.itextpdf.html2pdf.css.w3c.css21.backgrounds;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.css.w3c.W3CCssTest;
import com.itextpdf.styledxmlparser.css.media.MediaDeviceDescription;
import com.itextpdf.styledxmlparser.css.media.MediaType;

// TODO DEVSIX-2027. There are NO multiple pages and there is NO a blue box on all pages.
public class BackgroundAttachment010Test extends W3CCssTest {
    private static final String SRC_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css/w3c/css21/backgrounds/";

    @Override
    protected String getHtmlFileName() {
        return "background-attachment-010.xht";
    }

    // iText sets "enable printing background images" by default, but "paged media view" enables below in the method
    @Override
    protected ConverterProperties getConverterProperties() {
        return new ConverterProperties().setBaseUri(SRC_FOLDER + "background-attachment-010.xht")
                .setMediaDeviceDescription(new MediaDeviceDescription(MediaType.PRINT));
    }
}