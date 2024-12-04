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
package com.itextpdf.html2pdf.resolver.font;

import com.itextpdf.layout.font.Range;
import com.itextpdf.styledxmlparser.resolver.font.BasicFontProvider;

/**
 * The default {@link BasicFontProvider} for pdfHTML, that, as opposed to
 * the font provider in iText's styled-xml-parser, also includes a
 * series of fonts that are shipped with the add-on.
 * <p>
 * Deprecated in favour of {@link BasicFontProvider} since it has the same functionality
 * This class will be removed and {@link BasicFontProvider} will be renamed to {@code DefaultFontProvider}
 */
@Deprecated
public class DefaultFontProvider extends BasicFontProvider {

    /**
     * Creates a new {@link DefaultFontProvider} instance.
     */
    public DefaultFontProvider() {
        this(true, true, false);
    }

    /**
     * Creates a new {@link DefaultFontProvider} instance.
     *
     * @param registerStandardPdfFonts use true if you want to register the standard Type 1 fonts (can't be embedded)
     * @param registerShippedFonts use true if you want to register the shipped fonts (can be embedded)
     * @param registerSystemFonts use true if you want to register the system fonts (can require quite some resources)
     */
    public DefaultFontProvider(boolean registerStandardPdfFonts, boolean registerShippedFonts,
            boolean registerSystemFonts) {
        super(registerStandardPdfFonts, registerShippedFonts, registerSystemFonts);
    }

    /**
     * Creates a new {@link DefaultFontProvider} instance.
     *
     * @param registerStandardPdfFonts use true if you want to register the standard Type 1 fonts (can't be embedded)
     * @param registerShippedFonts use true if you want to register the shipped fonts (can be embedded)
     * @param registerSystemFonts use true if you want to register the system fonts (can require quite some resources)
     * @param defaultFontFamily default font family
     */
    public DefaultFontProvider(boolean registerStandardPdfFonts, boolean registerShippedFonts,
            boolean registerSystemFonts, String defaultFontFamily) {
        super(registerStandardPdfFonts, registerShippedFonts, registerSystemFonts, defaultFontFamily);
    }

    /**
     * Adds the shipped fonts.
     * <p>
     * Deprecated since similar method was added to parent class.
     *
     * @param rangeToLoad a unicode {@link Range} to load characters
     */
    @Deprecated
    @Override
    protected void addShippedFonts(Range rangeToLoad) {
        super.addShippedFonts(rangeToLoad);
    }

    /**
     * This method loads a list of noto fonts from pdfCalligraph (if present in the classpath!) into FontProvider.
     * The list is the following (each font is represented in regular and bold types): NotoSansArabic, NotoSansGurmukhi,
     * NotoSansOriya, NotoSerifBengali, NotoSerifDevanagari, NotoSerifGujarati, NotoSerifHebrew, NotoSerifKannada,
     * NotoSerifKhmer, NotoSerifMalayalam, NotoSerifTamil, NotoSerifTelugu, NotoSerifThai.
     * If it's needed to have a DefaultFontProvider without typography fonts loaded,
     * create an extension of DefaultFontProvider and override this method so it does nothing and only returns null.
     * <p>
     * Deprecated since similar method was added to parent class.
     *
     * @return a unicode {@link Range} that excludes the loaded from pdfCalligraph fonts,
     * i.e. the unicode range that is to be rendered with any other font contained in this FontProvider
     */
    @Deprecated
    @Override
    protected Range addCalligraphFonts() {
        return super.addCalligraphFonts();
    }
}
