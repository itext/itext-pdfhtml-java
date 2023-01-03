/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2023 iText Group NV
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
package com.itextpdf.html2pdf.html;

import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.css.resolve.func.counter.CounterDigitsGlyphStyle;
import com.itextpdf.kernel.numbering.ArmenianNumbering;
import com.itextpdf.kernel.numbering.EnglishAlphabetNumbering;
import com.itextpdf.kernel.numbering.GeorgianNumbering;
import com.itextpdf.kernel.numbering.GreekAlphabetNumbering;
import com.itextpdf.kernel.numbering.RomanNumbering;

/**
 * Utilities class with HTML-related functionality.
 */
public final class HtmlUtils {

    /**
     * The Constant DISC_SYMBOL.
     */
    private static final String DISC_SYMBOL = "•";

    /**
     * The Constant CIRCLE_SYMBOL.
     */
    private static final String CIRCLE_SYMBOL = "◦";

    /**
     * The Constant SQUARE_SYMBOL.
     */
    private static final String SQUARE_SYMBOL = "■";

    /**
     * Symbols which are used to write numbers in Latin.
     */
    private static final String LATIN_NUMERALS = "abcdefghijklmnopqrstuvwxyz";

    /**
     * Symbols which are used to write numbers in Greek.
     */
    private static final String GREEK_NUMERALS = "αβγδεζηθικλμνξοπρστυφχψω";

    /**
     * Symbols which are used to write numbers in Roman.
     */
    private static final String ROMAN_NUMERALS = "ivxlcdm";

    /**
     * Symbols which are used to write numbers in Georgian.
     */
    private static final String GEORGIAN_NUMERALS = "აბგდევზჱთიკლმნჲოპჟრსტჳფქღყშჩცძწჭხჴჯჰჵ";

    /**
     * Symbols which are used to write numbers in Armenian.
     */
    private static final String ARMENIAN_NUMERALS = "ԱԲԳԴԵԶԷԸԹԺԻԼԽԾԿՀՁՂՃՄՅՆՇՈՉՊՋՌՍՎՏՐՑՒՓՔ";

    /**
     * Symbols which are used to write numbers by default.
     */
    private static final String DEFAULT_NUMERALS = "1234567890";

    /**
     * The Constant MAX_ROMAN_NUMBER.
     */
    private static final int MAX_ROMAN_NUMBER = 3999;

    /**
     * Creates a new {@link HtmlUtils} instance.
     */
    private HtmlUtils() {
    }

    /**
     * Converts number according to given glyph style.
     *
     * @param glyphStyle style of the glyphs
     * @param number number to be converted
     * @return converted number
     */
    public static String convertNumberAccordingToGlyphStyle(CounterDigitsGlyphStyle glyphStyle, int number) {
        if (glyphStyle == null) {
            return convertNumberDefault(number);
        }
        switch (glyphStyle) {
            case NONE:
                return "";
            case DISC:
                return DISC_SYMBOL;
            case SQUARE:
                return SQUARE_SYMBOL;
            case CIRCLE:
                return CIRCLE_SYMBOL;
            case UPPER_ALPHA_AND_LATIN:
                return number > 0 ? EnglishAlphabetNumbering.toLatinAlphabetNumberUpperCase(number)
                        : convertNumberDefault(number);
            case LOWER_ALPHA_AND_LATIN:
                return number > 0 ? EnglishAlphabetNumbering.toLatinAlphabetNumberLowerCase(number)
                        : convertNumberDefault(number);
            case LOWER_GREEK:
                return number > 0 ? GreekAlphabetNumbering.toGreekAlphabetNumberLowerCase(number)
                        : convertNumberDefault(number);
            case LOWER_ROMAN:
                return number <= MAX_ROMAN_NUMBER ? RomanNumbering.toRomanLowerCase(number)
                        : convertNumberDefault(number);
            case UPPER_ROMAN:
                return number <= MAX_ROMAN_NUMBER ? RomanNumbering.toRomanUpperCase(number)
                        : convertNumberDefault(number);
            case DECIMAL_LEADING_ZERO:
                return (number < 10 ? "0" : "") + convertNumberDefault(number);
            case GEORGIAN:
                return GeorgianNumbering.toGeorgian(number);
            case ARMENIAN:
                return ArmenianNumbering.toArmenian(number);
            case DEFAULT:
            default:
                return convertNumberDefault(number);
        }
    }

    private static String convertNumberDefault(int number) {
        return String.valueOf(number);
    }

    /**
     * Gets a string which contains all glyphs which can be used in number according to given glyph style.
     *
     * @param glyphStyle style of the glyphs
     * @return string of all number glyphs
     */
    public static String getAllNumberGlyphsForStyle(CounterDigitsGlyphStyle glyphStyle) {
        if (glyphStyle == null) {
            return DEFAULT_NUMERALS;
        }
        switch (glyphStyle) {
            case NONE:
                return "";
            case DISC:
                return DISC_SYMBOL;
            case SQUARE:
                return SQUARE_SYMBOL;
            case CIRCLE:
                return CIRCLE_SYMBOL;
            case UPPER_ALPHA_AND_LATIN:
                return LATIN_NUMERALS.toUpperCase();
            case LOWER_ALPHA_AND_LATIN:
                return LATIN_NUMERALS;
            case LOWER_GREEK:
                return GREEK_NUMERALS;
            case LOWER_ROMAN:
                return ROMAN_NUMERALS;
            case UPPER_ROMAN:
                return ROMAN_NUMERALS.toUpperCase();
            case GEORGIAN:
                return GEORGIAN_NUMERALS;
            case ARMENIAN:
                return ARMENIAN_NUMERALS;
            default:
                return DEFAULT_NUMERALS;
        }
    }

    /**
     * Gets enum representation of given digits glyph style.
     *
     * @param glyphStyle style of the glyphs
     * @return {@link CounterDigitsGlyphStyle} equivalent of given glyph style
     */
    public static CounterDigitsGlyphStyle convertStringCounterGlyphStyleToEnum(String glyphStyle) {
        if (glyphStyle == null) {
            return CounterDigitsGlyphStyle.DEFAULT;
        }
        switch (glyphStyle) {
            case CssConstants.NONE:
                return CounterDigitsGlyphStyle.NONE;
            case CssConstants.DISC:
                return CounterDigitsGlyphStyle.DISC;
            case CssConstants.SQUARE:
                return CounterDigitsGlyphStyle.SQUARE;
            case CssConstants.CIRCLE:
                return CounterDigitsGlyphStyle.CIRCLE;
            case CssConstants.UPPER_ALPHA:
            case CssConstants.UPPER_LATIN:
                return CounterDigitsGlyphStyle.UPPER_ALPHA_AND_LATIN;
            case CssConstants.LOWER_ALPHA:
            case CssConstants.LOWER_LATIN:
                return CounterDigitsGlyphStyle.LOWER_ALPHA_AND_LATIN;
            case CssConstants.LOWER_GREEK:
                return CounterDigitsGlyphStyle.LOWER_GREEK;
            case CssConstants.LOWER_ROMAN:
                return CounterDigitsGlyphStyle.LOWER_ROMAN;
            case CssConstants.UPPER_ROMAN:
                return CounterDigitsGlyphStyle.UPPER_ROMAN;
            case CssConstants.GEORGIAN:
                return CounterDigitsGlyphStyle.GEORGIAN;
            case CssConstants.ARMENIAN:
                return CounterDigitsGlyphStyle.ARMENIAN;
            case CssConstants.DECIMAL_LEADING_ZERO:
                return CounterDigitsGlyphStyle.DECIMAL_LEADING_ZERO;
            default:
                return CounterDigitsGlyphStyle.DEFAULT;
        }
    }
}
