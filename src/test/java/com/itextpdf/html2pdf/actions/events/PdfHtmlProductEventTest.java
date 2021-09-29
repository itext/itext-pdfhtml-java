/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2021 iText Group NV
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
package com.itextpdf.html2pdf.actions.events;

import com.itextpdf.html2pdf.actions.data.PdfHtmlProductData;
import com.itextpdf.commons.actions.ProductNameConstant;
import com.itextpdf.commons.actions.confirmations.EventConfirmationType;
import com.itextpdf.commons.actions.sequence.SequenceId;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.type.UnitTest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(UnitTest.class)
public class PdfHtmlProductEventTest extends ExtendedITextTest {
    @Test
    public void convertElementsEventTest() {
        SequenceId sequenceId = new SequenceId();
        PdfHtmlProductEvent event = PdfHtmlProductEvent.createConvertHtmlEvent(sequenceId, new PdfHtmlTestMetaInfo("meta data"));

        Assert.assertEquals(PdfHtmlProductEvent.CONVERT_HTML, event.getEventType());
        Assert.assertEquals(ProductNameConstant.PDF_HTML, event.getProductName());
        Assert.assertEquals(EventConfirmationType.ON_CLOSE, event.getConfirmationType());
        Assert.assertEquals(sequenceId, event.getSequenceId());

        Assert.assertEquals(PdfHtmlProductData.getInstance().getPublicProductName(), event.getProductData().getPublicProductName());
        Assert.assertEquals(PdfHtmlProductData.getInstance().getProductName(), event.getProductData().getProductName());
        Assert.assertEquals(PdfHtmlProductData.getInstance().getVersion(), event.getProductData().getVersion());
        Assert.assertEquals(PdfHtmlProductData.getInstance().getSinceCopyrightYear(), event.getProductData().getSinceCopyrightYear());
        Assert.assertEquals(PdfHtmlProductData.getInstance().getToCopyrightYear(), event.getProductData().getToCopyrightYear());
    }
}
