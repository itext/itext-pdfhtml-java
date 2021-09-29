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
import com.itextpdf.commons.actions.AbstractProductProcessITextEvent;
import com.itextpdf.commons.actions.confirmations.EventConfirmationType;
import com.itextpdf.commons.actions.sequence.SequenceId;
import com.itextpdf.commons.actions.contexts.IMetaInfo;

/**
 * Class represents events registered in iText pdfHTML module.
 */
public final class PdfHtmlProductEvent extends AbstractProductProcessITextEvent {
    /**
     * Convert html event type.
     */
    public static final String CONVERT_HTML = "convert-html";

    private final String eventType;

    /**
     * Creates an event associated with a general identifier and additional meta data.
     *
     * @param sequenceId is an identifier associated with the event
     * @param metaInfo   is an additional meta info
     * @param eventType  is a string description of the event
     */
    private PdfHtmlProductEvent(SequenceId sequenceId, IMetaInfo metaInfo, String eventType) {
        super(sequenceId, PdfHtmlProductData.getInstance(), metaInfo, EventConfirmationType.ON_CLOSE);
        this.eventType = eventType;
    }

    /**
     * Creates a convert html event which associated with a general identifier and additional meta data.
     *
     * @param sequenceId is an identifier associated with the event
     * @param metaInfo is an additional meta info
     *
     * @return the convert html event
     */
    public static PdfHtmlProductEvent createConvertHtmlEvent(SequenceId sequenceId, IMetaInfo metaInfo) {
        return new PdfHtmlProductEvent(sequenceId, metaInfo, CONVERT_HTML);
    }

    @Override
    public String getEventType() {
        return eventType;
    }
}
