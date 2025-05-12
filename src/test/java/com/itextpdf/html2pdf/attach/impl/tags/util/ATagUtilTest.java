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
