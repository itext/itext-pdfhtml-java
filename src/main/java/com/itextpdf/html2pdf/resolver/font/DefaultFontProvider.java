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
package com.itextpdf.html2pdf.resolver.font;

import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.io.util.ResourceUtil;
import com.itextpdf.io.util.StreamUtil;
import com.itextpdf.layout.font.Range;
import com.itextpdf.layout.font.RangeBuilder;
import com.itextpdf.layout.renderer.TypographyUtils;
import com.itextpdf.styledxmlparser.resolver.font.BasicFontProvider;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The default {@link BasicFontProvider} for pdfHTML, that, as opposed to
 * the font provider in iText's styled-xml-parser, also includes a
 * series of fonts that are shipped with the add-on.
 */
public class DefaultFontProvider extends BasicFontProvider {

    /** The path to the shipped fonts. */
    static final String SHIPPED_FONT_RESOURCE_PATH = "com/itextpdf/html2pdf/font/";

    /** The file names of the shipped fonts. */
    static final String[] SHIPPED_FONT_NAMES = new String[] {
            "NotoSansMono-Regular.ttf",
            "NotoSansMono-Bold.ttf",
            "NotoSans-Regular.ttf",
            "NotoSans-Bold.ttf",
            "NotoSans-BoldItalic.ttf",
            "NotoSans-Italic.ttf",
            "NotoSerif-Regular.ttf",
            "NotoSerif-Bold.ttf",
            "NotoSerif-BoldItalic.ttf",
            "NotoSerif-Italic.ttf",
    };

    /**
     * The logger.
     */
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(DefaultFontProvider.class);

    private static final String DEFAULT_FONT_FAMILY = "Times";

    // This range exclude Hebrew, Arabic, Syriac, Arabic Supplement, Thaana, NKo, Samaritan,
    // Mandaic, Syriac Supplement, Arabic Extended-A, Devanagari, Bengali, Gurmukhi, Gujarati,
    // Oriya, Tamil, Telugu, Kannada, Malayalam, Sinhala, Thai unicode blocks.
    // Those blocks either require pdfCalligraph or do not supported by GNU Free Fonts.
    private static final Range FREE_FONT_RANGE = new RangeBuilder()
            .addRange(0, 0x058F).addRange(0x0E80, Integer.MAX_VALUE).create();

    //we want to add free fonts to font provider before calligraph fonts. However, the existing public API states
    // that addCalligraphFonts() should be used first to load calligraph fonts and to define the range for loading free fonts.
    // In order to maintain backward compatibility, this temporary field is used to stash calligraph fonts before free fonts are loaded.
    private List<byte[]> calligraphyFontsTempList = new ArrayList<>();

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
        this(registerStandardPdfFonts, registerShippedFonts, registerSystemFonts, DEFAULT_FONT_FAMILY);
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
        super(registerStandardPdfFonts, registerSystemFonts, defaultFontFamily);
        if (registerShippedFonts) {
            addAllAvailableFonts(addCalligraphFonts());
        }
    }

    private void addAllAvailableFonts(Range rangeToLoad) {
        addShippedFonts(rangeToLoad);
        for(byte[] fontData : calligraphyFontsTempList) {
            addFont(fontData, null);
        }
        calligraphyFontsTempList = null;
    }

    /**
     * Adds the shipped fonts.
     *
     * @param rangeToLoad a unicode {@link Range} to load characters
     */
    private void addShippedFonts(Range rangeToLoad) {
        for (String fontName : SHIPPED_FONT_NAMES) {
            try (InputStream stream = ResourceUtil.getResourceStream(SHIPPED_FONT_RESOURCE_PATH + fontName)) {
                byte[] fontProgramBytes = StreamUtil.inputStreamToArray(stream);
                addFont(fontProgramBytes, null, rangeToLoad);
            } catch (Exception e) {
                LOGGER.error(Html2PdfLogMessageConstant.ERROR_LOADING_FONT);
            }
        }
    }

    /**
     * This method loads a list of noto fonts from pdfCalligraph (if present in the classpath!) into FontProvider.
     * The list is the following (each font is represented in regular and bold types): NotoSansArabic, NotoSansGurmukhi,
     * NotoSansOriya, NotoSerifBengali, NotoSerifDevanagari, NotoSerifGujarati, NotoSerifHebrew, NotoSerifKannada,
     * NotoSerifKhmer, NotoSerifMalayalam, NotoSerifTamil, NotoSerifTelugu, NotoSerifThai.
     * If it's needed to have a DefaultFontProvider without typography fonts loaded,
     * create an extension of DefaultFontProvider and override this method so it does nothing and only returns null.
     *
     * @return a unicode {@link Range} that excludes the loaded from pdfCalligraph fonts,
     * i.e. the unicode range that is to be rendered with any other font contained in this FontProvider
     */
    protected Range addCalligraphFonts() {
        if (TypographyUtils.isPdfCalligraphAvailable()) {
            try {
                Map<String, byte[]> fontStreams = TypographyUtils.loadShippedFonts();
                this.calligraphyFontsTempList.addAll(fontStreams.values());
                // here we return a unicode range that excludes the loaded from the calligraph module fonts
                // i.e. the unicode range that is to be rendered with standard or shipped free fonts
                return FREE_FONT_RANGE;
            } catch (Exception e) {
                LOGGER.error(Html2PdfLogMessageConstant.ERROR_LOADING_FONT);
            }
        }
        return null;
    }
}
