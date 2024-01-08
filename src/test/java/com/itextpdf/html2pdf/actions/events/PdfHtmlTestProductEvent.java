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
package com.itextpdf.html2pdf.actions.events;

import com.itextpdf.html2pdf.actions.data.PdfHtmlProductData;
import com.itextpdf.commons.actions.AbstractProductProcessITextEvent;
import com.itextpdf.commons.actions.confirmations.EventConfirmationType;
import com.itextpdf.commons.actions.sequence.SequenceId;

public class PdfHtmlTestProductEvent  extends AbstractProductProcessITextEvent {

    private final String eventType;

    public PdfHtmlTestProductEvent(SequenceId sequenceId, String eventType, EventConfirmationType confirmationType) {
        super(sequenceId, PdfHtmlProductData.getInstance(), null, confirmationType);
        this.eventType = eventType;
    }

    public String getEventType() {
        return eventType;
    }
}
