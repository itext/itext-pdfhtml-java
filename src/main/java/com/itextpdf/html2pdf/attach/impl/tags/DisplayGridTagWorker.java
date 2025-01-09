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
package com.itextpdf.html2pdf.attach.impl.tags;

import com.itextpdf.html2pdf.attach.ITagWorker;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.element.GridContainer;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.styledxmlparser.node.IElementNode;

/**
 * {@link ITagWorker} implementation for elements with {@code display: grid}.
 */
public class DisplayGridTagWorker extends DivTagWorker {

    /**
     * Creates a new {@link DisplayGridTagWorker} instance.
     *
     * @param element the element
     * @param context the context
     */
    public DisplayGridTagWorker(IElementNode element, ProcessorContext context) {
        super(element, context, new GridContainer());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean processTagChild(ITagWorker childTagWorker, ProcessorContext context) {
        final IPropertyContainer element = childTagWorker.getElementResult();
        if (childTagWorker instanceof BrTagWorker) {
            return super.processTagChild(childTagWorker, context);
        } else {
            return addBlockChild((IElement) element);
        }
    }
}
