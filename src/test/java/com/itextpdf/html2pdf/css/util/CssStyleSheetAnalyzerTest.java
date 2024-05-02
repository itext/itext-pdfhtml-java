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
package com.itextpdf.html2pdf.css.util;

import com.itextpdf.styledxmlparser.css.CssStyleSheet;
import com.itextpdf.styledxmlparser.css.parse.CssStyleSheetParser;
import com.itextpdf.test.ExtendedITextTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

@Tag("UnitTest")
public class CssStyleSheetAnalyzerTest extends ExtendedITextTest {

    @Test
    public void simpleNegativeTest() {
        CssStyleSheet styleSheet = CssStyleSheetParser.parse("* { color: red; }");
        Assertions.assertFalse(CssStyleSheetAnalyzer.checkPagesCounterPresence(styleSheet));
    }

    @Test
    public void pagesInCounterSimpleTest() {
        CssStyleSheet styleSheet = CssStyleSheetParser.parse(".x::before { content: counter(pages) }");
        Assertions.assertTrue(CssStyleSheetAnalyzer.checkPagesCounterPresence(styleSheet));
    }

    @Test
    public void pagesInCountersSimpleTest() {
        CssStyleSheet styleSheet = CssStyleSheetParser.parse(".x::before { content: counters(pages,'.') }");
        Assertions.assertTrue(CssStyleSheetAnalyzer.checkPagesCounterPresence(styleSheet));
    }

    @Test
    public void pagesInCounterSpacesPresenceTest() {
        CssStyleSheet styleSheet = CssStyleSheetParser.parse(".x::before { content: counter( pages ) }");
        Assertions.assertTrue(CssStyleSheetAnalyzer.checkPagesCounterPresence(styleSheet));
    }

    @Test
    public void pagesInCountersSpacesPresenceTest() {
        CssStyleSheet styleSheet = CssStyleSheetParser.parse(".x::before { content: counters( pages,'.') }");
        Assertions.assertTrue(CssStyleSheetAnalyzer.checkPagesCounterPresence(styleSheet));
    }

    @Test
    public void counterWithoutPagesTest() {
        CssStyleSheet styleSheet = CssStyleSheetParser.parse(".x::before { content: counter(othercounter) }");
        Assertions.assertFalse(CssStyleSheetAnalyzer.checkPagesCounterPresence(styleSheet));
    }

}
