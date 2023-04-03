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
package com.itextpdf.html2pdf.exceptions;

import com.itextpdf.commons.exceptions.ITextException;
import com.itextpdf.commons.utils.MessageFormatUtil;

/**
 * Runtime exception that gets thrown if a tag worker can't be initialized.
 */
public class TagWorkerInitializationException extends ITextException {

    /** Template for the error message in case a tag worker couldn't be instantiated. */
    public static final String REFLECTION_IN_TAG_WORKER_FACTORY_IMPLEMENTATION_FAILED = "Could not "
            + "instantiate TagWorker-class {0} for tag {1}.";

    /**
     * Creates a {@link TagWorkerInitializationException} instance.
     *
     * @param message    the message
     * @param classNames the class names
     * @param tag        the tag
     */
    public TagWorkerInitializationException(String message, String classNames, String tag) {
        super(MessageFormatUtil.format(message,classNames,tag));
    }

    /**
     *  Creates a {@link TagWorkerInitializationException} instance.
     *
     * @param message the message
     * @param classNames the class names
     * @param tag the tag
     * @param cause the cause
     */
    public TagWorkerInitializationException(String message, String classNames, String tag, Throwable cause) {
        super(MessageFormatUtil.format(message,classNames,tag), cause);
    }
}
