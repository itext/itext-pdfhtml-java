/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2019 iText Group NV
    Authors: Bruno Lowagie, Paulo Soares, et al.

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License version 3
    as published by the Free Software Foundation with the addition of the
    following permission added to Section 15 as permitted in Section 7(a):
    FOR ANY PART OF THE COVERED WORK IN WHICH THE COPYRIGHT IS OWNED BY
    ITEXT GROUP. ITEXT GROUP DISCLAIMS THE WARRANTY OF NON INFRINGEMENT
    OF THIRD PARTY RIGHTS

    This program is distributed in the hope that it will be useful, but
    WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
    or FITNESS FOR A PARTICULAR PURPOSE.
    See the GNU Affero General Public License for more details.
    You should have received a copy of the GNU Affero General Public License
    along with this program; if not, see http://www.gnu.org/licenses or write to
    the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
    Boston, MA, 02110-1301 USA, or download the license from the following URL:
    http://itextpdf.com/terms-of-use/

    The interactive user interfaces in modified source and object code versions
    of this program must display Appropriate Legal Notices, as required under
    Section 5 of the GNU Affero General Public License.

    In accordance with Section 7(b) of the GNU Affero General Public License,
    a covered work must retain the producer line in every PDF that is created
    or manipulated using iText.

    You can be released from the requirements of the license by purchasing
    a commercial license. Buying such a license is mandatory as soon as you
    develop commercial activities involving the iText software without
    disclosing the source code of your own applications.
    These activities include: offering paid services to customers as an ASP,
    serving PDFs on the fly in a web application, shipping iText with a closed
    source product.

    For more information, please contact iText Software Corp. at this
    address: sales@itextpdf.com
 */
package com.itextpdf.html2pdf.resolver.font;

import com.itextpdf.html2pdf.LogMessageConstant;
import com.itextpdf.io.util.ResourceUtil;
import com.itextpdf.io.util.StreamUtil;
import com.itextpdf.layout.font.Range;
import com.itextpdf.layout.font.RangeBuilder;
import com.itextpdf.styledxmlparser.resolver.font.BasicFontProvider;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * The default {@link BasicFontProvider} for pdfHTML, that, as opposed to
 * the font provider in iText 7's styled-xml-parser, also includes a
 * series of fonts that are shipped with the add-on.
 */
public class DefaultFontProvider extends BasicFontProvider {

    /**
     * The logger.
     */
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(DefaultFontProvider.class);

    /** The path to the shipped fonts. */
    private static final String SHIPPED_FONT_RESOURCE_PATH = "com/itextpdf/html2pdf/font/";

    /** The file names of the shipped fonts. */
    private static final String[] SHIPPED_FONT_NAMES = new String[] {
            "FreeMono.ttf",
            "FreeMonoBold.ttf",
            "FreeMonoBoldOblique.ttf",
            "FreeMonoOblique.ttf",
            "FreeSans.ttf",
            "FreeSansBold.ttf",
            "FreeSansBoldOblique.ttf",
            "FreeSansOblique.ttf",
            "FreeSerif.ttf",
            "FreeSerifBold.ttf",
            "FreeSerifBoldItalic.ttf",
            "FreeSerifItalic.ttf",
    };

    // This range exclude Hebrew, Arabic, Syriac, Arabic Supplement, Thaana, NKo, Samaritan,
    // Mandaic, Syriac Supplement, Arabic Extended-A, Devanagari, Bengali, Gurmukhi, Gujarati,
    // Oriya, Tamil, Telugu, Kannada, Malayalam, Sinhala, Thai unicode blocks.
    // Those blocks either require pdfCalligraph or do not supported by GNU Free Fonts.
    private static final Range FREE_FONT_RANGE = new RangeBuilder()
            .addRange(0, 0x058F).addRange(0x0E80, Integer.MAX_VALUE).create();

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
     * @param registerShippedFreeFonts use true if you want to register the shipped fonts (can be embedded)
     * @param registerSystemFonts use true if you want to register the system fonts (can require quite some resources)
     */
    public DefaultFontProvider(boolean registerStandardPdfFonts, boolean registerShippedFreeFonts, boolean registerSystemFonts) {
        super(registerStandardPdfFonts, registerSystemFonts);
        if (registerShippedFreeFonts) {
            if(checkCalligraphFonts() != null) {
                addShippedFreeFonts(FREE_FONT_RANGE);
                addCalligraphFonts();
            } else {
                addShippedFreeFonts(null);
            }
        }
    }

    /**
     * Adds the shipped free fonts.
     */
    private void addShippedFreeFonts(Range rangeToLoad) {
        for (String fontName : SHIPPED_FONT_NAMES) {
            try (InputStream stream = ResourceUtil.getResourceStream(SHIPPED_FONT_RESOURCE_PATH + fontName)) {
                byte[] fontProgramBytes = StreamUtil.inputStreamToArray(stream);
                addFont(fontProgramBytes, null, rangeToLoad);
            } catch (Exception e) {
                LOGGER.error(LogMessageConstant.ERROR_LOADING_FONT);
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
     *
     * @return a unicode {@link Range} that excludes the loaded from pdfCalligraph fonts,
     * i.e. the unicode range that is to be rendered with any other font contained in this FontProvider
     */
    protected Range addCalligraphFonts() {
        String methodName = "loadShippedFonts";
        Class<?> klass = checkCalligraphFonts();
        if (klass != null) {
            try {
                Method m = klass.getMethod(methodName);
                ArrayList<byte[]> fontStreams = (ArrayList<byte[]>) m.invoke(null, null);
                for (byte[] font : fontStreams)
                    addFont(font);
                // here we return a unicode range that excludes the loaded from the calligraph module fonts
                // i.e. the unicode range that is to be rendered with standard or shipped free fonts
                return FREE_FONT_RANGE;
            } catch (Exception e) {
                LOGGER.error(LogMessageConstant.ERROR_LOADING_FONT);
            }
        }
        return null;
    }

    private Class<?> checkCalligraphFonts() {
        Class<?> klass = null;
        try {
            klass = getTypographyUtilsClass();
        } catch (ClassNotFoundException ignored) { }
        return klass;
    }

    private static Class<?> getTypographyUtilsClass() throws ClassNotFoundException {
        String typographyClassFullName = "com.itextpdf.typography.util.TypographyShippedFontsUtil";
        return Class.forName(typographyClassFullName);
    }
}
