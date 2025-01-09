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
package com.itextpdf.html2pdf.attach;

import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.styledxmlparser.node.IElementNode;

/**
 * Interface for all the tag worker implementations.
 */
public interface ITagWorker {

    /**
     * Placeholder for what needs to be done after the content of a tag has been processed.
     *
     * @param element the element node
     * @param context the processor context
     */
    void processEnd(IElementNode element, ProcessorContext context);

    /**
     * Placeholder for what needs to be done while the content of a tag is being processed.
     *
     * @param content the content of a node
     * @param context the processor context
     * @return true, if content was successfully processed, otherwise false.
     */
    boolean processContent(String content, ProcessorContext context);

    /**
     * Placeholder for what needs to be done when a child node is being processed.
     *
     * @param childTagWorker the tag worker of the child node
     * @param context the processor context
     * @return true, if child was successfully processed, otherwise false.
     */
    boolean processTagChild(ITagWorker childTagWorker, ProcessorContext context);

    /**
     * Gets a processed object if it can be expressed as an {@link IPropertyContainer} instance.
     *
     * @return the same object on every call.
     *         Might return null either if result is not yet produced or if this particular
     *         tag worker doesn't produce result in a form of {@link IPropertyContainer}.
     */
    IPropertyContainer getElementResult();
}
