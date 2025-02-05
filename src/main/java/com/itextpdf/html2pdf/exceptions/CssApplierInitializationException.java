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
package com.itextpdf.html2pdf.exceptions;

import com.itextpdf.commons.exceptions.ITextException;
import com.itextpdf.commons.utils.MessageFormatUtil;

/**
 * Runtime exception in case a CSS applier can't be initialized.
 */
public class CssApplierInitializationException extends ITextException {

    /** The message template in case reflection failed. */
    public static final String REFLECTION_FAILED = "Could not instantiate CssApplier-class {0} for tag {1}.";

    /**
     * Creates a new {@link CssApplierInitializationException} instance.
     *
     * @param message   the message
     * @param className the class name of the CSS applier
     * @param tag       the key
     */
    public CssApplierInitializationException(String message, String className, String tag) {
        super(MessageFormatUtil.format(message, className, tag));
    }
}
