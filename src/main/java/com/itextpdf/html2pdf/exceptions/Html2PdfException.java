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

/**
 * Runtime exception that gets thrown if something goes wrong in the HTML to PDF conversion.
 */
public class Html2PdfException extends ITextException {

    /** Message in case one tries to write to a PDF document that isn't in writing mode. */
    public static final String PDF_DOCUMENT_SHOULD_BE_IN_WRITING_MODE = "PdfDocument should be created "
            + "in writing mode. Reading and stamping is not allowed";
    
    /** Message in case the font provider doesn't know about any fonts. */
    public static final String FONT_PROVIDER_CONTAINS_ZERO_FONTS = "Font Provider contains zero fonts. "
            + "At least one font shall be present";
    
    /** The Constant UnsupportedEncodingException. */
    public static final String UNSUPPORTED_ENCODING_EXCEPTION = "Unsupported encoding exception.";

    /**
     * Creates a new {@link Html2PdfException} instance.
     *
     * @param message the message
     */
    public Html2PdfException(String message) {
        super(message);
    }
}
