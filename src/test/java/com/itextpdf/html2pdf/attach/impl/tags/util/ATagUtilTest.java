/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2025 Apryse Group NV
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
package com.itextpdf.html2pdf.attach.impl.tags.util;

import com.itextpdf.test.ExtendedITextTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("UnitTest")
public class ATagUtilTest extends ExtendedITextTest {

    @Test
    public void baseUrlIsNullTest() {
        String anchorLink = "Anchor link";
        String baseUrl = null;
        String modifiedLink = ATagUtil.resolveAnchorLink(anchorLink, baseUrl);
        Assertions.assertEquals(anchorLink, modifiedLink);
    }

    @Test
    public void linkIsAnAnchorTest() {
        String anchorLink = "#Anchor link";
        String baseUrl = "https://not_existing_url.com";
        String modifiedLink = ATagUtil.resolveAnchorLink(anchorLink, baseUrl);
        Assertions.assertEquals(anchorLink, modifiedLink);
    }

    @Test
    public void baseLinkTest() {
        String anchorLink = "Anchor link";
        String baseUrl = "Base url";
        String modifiedLink = ATagUtil.resolveAnchorLink(anchorLink, baseUrl);
        Assertions.assertEquals(anchorLink, modifiedLink);
    }

    @Test
    public void resolvedURlEndsWIthSlashTest() {
        String anchorLink = "";
        String baseUrl = "/random_path/";
        String modifiedLink = ATagUtil.resolveAnchorLink(anchorLink, baseUrl);
        Assertions.assertEquals(anchorLink, modifiedLink);
    }

    @Test
    public void resolveURlForExternalLinkTest() {
        String anchorLink = "Anchor";
        String baseUrl = "https://not_existing_url.com";

        String expectedUrl = "https://not_existing_url.com/Anchor";

        String modifiedLink = ATagUtil.resolveAnchorLink(anchorLink, baseUrl);
        Assertions.assertEquals(expectedUrl, modifiedLink);
    }

    @Test
    public void resolveURlThrowsMalformedExceptionTest() {
        String anchorLink = "htt://not_existing_url.com";
        String baseUrl = "https://not_existing_url.com";

        String expectedUrl = "htt://not_existing_url.com";

        String modifiedLink = ATagUtil.resolveAnchorLink(anchorLink, baseUrl);
        Assertions.assertEquals(expectedUrl, modifiedLink);
    }
}
