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
import com.itextpdf.kernel.actions.ProductNameConstant;
import com.itextpdf.kernel.actions.events.AbstractITextProductEvent;
import com.itextpdf.kernel.actions.sequence.SequenceId;
import com.itextpdf.kernel.counter.event.IMetaInfo;

/**
 * Class represents events registered in iText pdfHTML module.
 */
public class PdfHtmlProductEvent extends AbstractITextProductEvent {
    /**
     * Convert elements event type.
     */
    public static final String CONVERT_ELEMENTS = "convert-elements-event";

    private final String eventType;

    /**
     * Creates an event associated with a general identifier and additional meta data.
     *
     * @param sequenceId is an identifier associated with the event
     * @param metaInfo   is an additional meta info
     * @param eventType  is a string description of the event
     */
    public PdfHtmlProductEvent(SequenceId sequenceId, IMetaInfo metaInfo, String eventType) {
        super(sequenceId, PdfHtmlProductData.getInstance(), metaInfo);
        this.eventType = eventType;
    }

    @Override
    public String getProductName() {
        return ProductNameConstant.PDF_HTML;
    }

    @Override
    public String getEventType() {
        return eventType;
    }
}
