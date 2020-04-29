package com.itextpdf.html2pdf.css.util;

import com.itextpdf.styledxmlparser.css.CssStyleSheet;
import com.itextpdf.styledxmlparser.css.parse.CssStyleSheetParser;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.type.UnitTest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(UnitTest.class)
public class CssStyleSheetAnalyzerTest extends ExtendedITextTest {

    @Test
    public void simpleNegativeTest() {
        CssStyleSheet styleSheet = CssStyleSheetParser.parse("* { color: red; }");
        Assert.assertFalse(CssStyleSheetAnalyzer.checkPagesCounterPresence(styleSheet));
    }

    @Test
    public void pagesInCounterSimpleTest() {
        CssStyleSheet styleSheet = CssStyleSheetParser.parse(".x::before { content: counter(pages) }");
        Assert.assertTrue(CssStyleSheetAnalyzer.checkPagesCounterPresence(styleSheet));
    }

    @Test
    public void pagesInCountersSimpleTest() {
        CssStyleSheet styleSheet = CssStyleSheetParser.parse(".x::before { content: counters(pages,'.') }");
        Assert.assertTrue(CssStyleSheetAnalyzer.checkPagesCounterPresence(styleSheet));
    }

    @Test
    public void pagesInCounterSpacesPresenceTest() {
        CssStyleSheet styleSheet = CssStyleSheetParser.parse(".x::before { content: counter( pages ) }");
        Assert.assertTrue(CssStyleSheetAnalyzer.checkPagesCounterPresence(styleSheet));
    }

    @Test
    public void pagesInCountersSpacesPresenceTest() {
        CssStyleSheet styleSheet = CssStyleSheetParser.parse(".x::before { content: counters( pages,'.') }");
        Assert.assertTrue(CssStyleSheetAnalyzer.checkPagesCounterPresence(styleSheet));
    }

    @Test
    public void counterWithoutPagesTest() {
        CssStyleSheet styleSheet = CssStyleSheetParser.parse(".x::before { content: counter(othercounter) }");
        Assert.assertFalse(CssStyleSheetAnalyzer.checkPagesCounterPresence(styleSheet));
    }

}
