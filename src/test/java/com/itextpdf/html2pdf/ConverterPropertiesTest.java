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

import com.itextpdf.commons.actions.NamespaceConstant;
import com.itextpdf.commons.actions.contexts.IMetaInfo;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.type.UnitTest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(UnitTest.class)
public class ConverterPropertiesTest extends ExtendedITextTest {

    @Test
    public void getDefaultMetaInfoTest() {
        ConverterProperties properties = new ConverterProperties();

        IMetaInfo metaInfo = properties.getEventMetaInfo();

        Assert.assertTrue(metaInfo.getClass().getName().startsWith(NamespaceConstant.PDF_HTML + "."));
    }

    @Test
    public void setEventMetaInfoAndGetTest() {
        ConverterProperties properties = new ConverterProperties();
        TestMetaInfo testMetaInfo = new TestMetaInfo();

        properties.setEventMetaInfo(testMetaInfo);
        IMetaInfo metaInfo = properties.getEventMetaInfo();

        Assert.assertSame(testMetaInfo, metaInfo);
    }

    @Test
    public void checkDefaultsTest() {
        ConverterProperties properties = new ConverterProperties();

        Assert.assertTrue(properties.isImmediateFlush());
        Assert.assertFalse(properties.isCreateAcroForm());
        Assert.assertEquals(10, properties.getLimitOfLayouts());

        properties.setImmediateFlush(false);
        properties.setCreateAcroForm(true);
        properties.setLimitOfLayouts(20);
        ConverterProperties propertiesCopied = new ConverterProperties(properties);

        Assert.assertFalse(propertiesCopied.isImmediateFlush());
        Assert.assertTrue(propertiesCopied.isCreateAcroForm());
        Assert.assertEquals(20, propertiesCopied.getLimitOfLayouts());
    }

    private static class TestMetaInfo implements IMetaInfo {
    }
}
