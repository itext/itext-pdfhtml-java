/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2021 iText Group NV
    Authors: iText Software.

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
package com.itextpdf.html2pdf.attach.util;

import com.itextpdf.html2pdf.attach.impl.layout.Html2PdfProperty;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Tab;
import com.itextpdf.layout.element.Text;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.type.UnitTest;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(UnitTest.class)
public class WaitingInlineElementsHelperTest extends ExtendedITextTest {

    private final String capitalizeStyle = "capitalize";

    private WaitingInlineElementsHelper inlineHelper;


    @Test
    public void capitalizeLeafTest() {
        inlineHelper = new WaitingInlineElementsHelper(null, capitalizeStyle);
        inlineHelper.add("one");

        Div div = new Div();
        inlineHelper.flushHangingLeaves(div);
        String lineResult = getLine(div);

        Assert.assertEquals("One", lineResult);
    }

    @Test
    public void capitalizeLeafWithTruePropertyTest() {
        inlineHelper = new WaitingInlineElementsHelper(null, null);
        inlineHelper.add(createText("one", true));

        Div div = new Div();
        inlineHelper.flushHangingLeaves(div);
        String lineResult = getLine(div);

        Assert.assertEquals("One", lineResult);
    }

    @Test
    public void capitalizeWithEmptyTextLeafTest() {
        inlineHelper = new WaitingInlineElementsHelper(null, capitalizeStyle);
        inlineHelper.add(new Text("one"));
        inlineHelper.add("");
        inlineHelper.add(new Text("two"));

        Div div = new Div();
        inlineHelper.flushHangingLeaves(div);
        String lineResult = getLine(div);

        Assert.assertEquals("Onetwo", lineResult);
    }

    @Test
    public void capitalizeTwoLeavesWithSpaceBeforeSecondTest() {
        inlineHelper = new WaitingInlineElementsHelper(null, capitalizeStyle);
        inlineHelper.add(new Text("one"));
        inlineHelper.add(new Text(" two"));

        Div div = new Div();
        inlineHelper.flushHangingLeaves(div);
        String lineResult = getLine(div);

        Assert.assertEquals("One Two", lineResult);
    }

    @Test
    public void oneLeafWithFalsePropertyAndCapitalizeStyleTest() {
        inlineHelper = new WaitingInlineElementsHelper(null, capitalizeStyle);
        inlineHelper.add(createText("one", false));

        Div div = new Div();
        inlineHelper.flushHangingLeaves(div);
        String lineResult = getLine(div);

        Assert.assertEquals("one", lineResult);
    }

    @Test
    public void capitalizeWithTabBetweenLeavesTest() {
        inlineHelper = new WaitingInlineElementsHelper(null, null);
        inlineHelper.add(createText("one", true));
        inlineHelper.add(new Tab());
        inlineHelper.add(createText("two", true));

        Div div = new Div();
        inlineHelper.flushHangingLeaves(div);
        String lineResult = getLine(div);

        Assert.assertEquals("OneTwo", lineResult);
    }

    @Test
    public void capitalizeWithLineSeparatorBetweenLeavesTest() {
        inlineHelper = new WaitingInlineElementsHelper(null, null);
        inlineHelper.add(createText("one", true));
        inlineHelper.add(new LineSeparator(new SolidLine()));
        inlineHelper.add(createText("two", true));

        Div div = new Div();
        inlineHelper.flushHangingLeaves(div);
        String lineResult = getLine(div);

        Assert.assertEquals("OneTwo", lineResult);
    }

    @Test
    public void capitalizeWithDivBetweenLeavesTest() {
        inlineHelper = new WaitingInlineElementsHelper(null, null);
        inlineHelper.add(createText("one", true));
        inlineHelper.add(new Div());
        inlineHelper.add(createText("two", true));

        Div div = new Div();
        inlineHelper.flushHangingLeaves(div);
        String lineResult = getLine(div);

        Assert.assertEquals("OneTwo", lineResult);
    }

    @Test
    public void capitalizeTest01() {
        inlineHelper = new WaitingInlineElementsHelper(null, null);
        inlineHelper.add(createText("one", false));

        Div div = new Div();
        inlineHelper.flushHangingLeaves(div);
        String lineResult = getLine(div);

        Assert.assertEquals("one", lineResult);
    }

    @Test
    public void capitalizeTest02() {
        inlineHelper = new WaitingInlineElementsHelper(null, null);
        inlineHelper.add(createText("one", true));
        inlineHelper.add("\n");
        inlineHelper.add(createText("two", true));

        Div div = new Div();
        inlineHelper.flushHangingLeaves(div);
        String lineResult = getLine(div);

        Assert.assertEquals("One Two", lineResult);
    }

    @Test
    public void capitalizeTest03() {
        inlineHelper = new WaitingInlineElementsHelper(null, null);
        inlineHelper.add(createText("one ", true));
        inlineHelper.add(new Text(" two"));

        Div div = new Div();
        inlineHelper.flushHangingLeaves(div);
        String lineResult = getLine(div);

        Assert.assertEquals("One two", lineResult);
    }

    @Test
    public void capitalizeTest04() {
        inlineHelper = new WaitingInlineElementsHelper(null, capitalizeStyle);
        inlineHelper.add(new Text("one "));
        inlineHelper.add(createText(" two ", false));
        inlineHelper.add(new Text(" three"));

        Div div = new Div();
        inlineHelper.flushHangingLeaves(div);
        String lineResult = getLine(div);

        Assert.assertEquals("One two Three", lineResult);
    }

    @Test
    public void capitalizeTest05() {
        inlineHelper = new WaitingInlineElementsHelper(null, capitalizeStyle);
        inlineHelper.add(new Text("one two"));
        inlineHelper.add(new Text("three four"));

        Div div = new Div();
        inlineHelper.flushHangingLeaves(div);
        String lineResult = getLine(div);

        Assert.assertEquals("One Twothree Four", lineResult);
    }

    @Test
    public void capitalizeTest06() {
        inlineHelper = new WaitingInlineElementsHelper(null, null);
        inlineHelper.add(new Text("one"));
        inlineHelper.add(createText("two three", true));

        Div div = new Div();
        inlineHelper.flushHangingLeaves(div);
        String lineResult = getLine(div);

        Assert.assertEquals("onetwo Three", lineResult);
    }

    @Test
    public void capitalizeTest07() {
        inlineHelper = new WaitingInlineElementsHelper(null, null);
        inlineHelper.add(createText("one two", true));
        inlineHelper.add(createText("three", false));
        inlineHelper.add(createText("four five", true));

        Div div = new Div();
        inlineHelper.flushHangingLeaves(div);
        String lineResult = getLine(div);

        Assert.assertEquals("One Twothreefour Five", lineResult);
    }

    @Test
    public void capitalizeTest08() {
        inlineHelper = new WaitingInlineElementsHelper(null, null);
        inlineHelper.add(createText("one ", true));
        inlineHelper.add(createText("two", false));
        inlineHelper.add(createText("three", true));

        Div div = new Div();
        inlineHelper.flushHangingLeaves(div);
        String lineResult = getLine(div);

        Assert.assertEquals("One twothree", lineResult);
    }

    @Test
    public void capitalizeTest09() {
        inlineHelper = new WaitingInlineElementsHelper(null, null);
        inlineHelper.add(createText("one ", false));
        inlineHelper.add(createText("two", true));

        Div div = new Div();
        inlineHelper.flushHangingLeaves(div);
        String lineResult = getLine(div);

        Assert.assertEquals("one Two", lineResult);
    }

    @Test
    //TODO: replace assertNotEquals with assertEquals DEVSIX-4414
    public void capitalizeAfterUnderScoreTest() {
        inlineHelper = new WaitingInlineElementsHelper(null, null);
        inlineHelper.add(createText("( one_", true));
        inlineHelper.add(createText("two) ", true));
        inlineHelper.add(createText("( _one", true));
        inlineHelper.add(createText("_two)", true));

        Div div = new Div();
        inlineHelper.flushHangingLeaves(div);
        String lineResult = getLine(div);

        Assert.assertNotEquals("( One_two) ( _one_two)", lineResult);
    }

    @Test
    //TODO: replace assertNotEquals with assertEquals DEVSIX-4414
    public void capitalizeAfterDigitsTest() {
        inlineHelper = new WaitingInlineElementsHelper(null, null);
        inlineHelper.add(createText("( one2", true));
        inlineHelper.add(createText("two) ", true));
        inlineHelper.add(createText("( one ", true));
        inlineHelper.add(createText("2two) ", true));
        inlineHelper.add(createText("( one-", true));
        inlineHelper.add(createText("2two) ", true));
        inlineHelper.add(createText("one_", true));
        inlineHelper.add(createText("2two", true));

        Div div = new Div();
        inlineHelper.flushHangingLeaves(div);
        String lineResult = getLine(div);

        Assert.assertNotEquals("( One2two) ( One 2two) ( One-2two) ( One_2two)", lineResult);
    }

    @Test
    //TODO: replace assertNotEquals with assertEquals DEVSIX-4414
    public void capitalizeAfterColonTest() {
        inlineHelper = new WaitingInlineElementsHelper(null, null);
        inlineHelper.add(createText("one:", true));
        inlineHelper.add(createText("two", true));

        Div div = new Div();
        inlineHelper.flushHangingLeaves(div);
        String lineResult = getLine(div);

        Assert.assertNotEquals("One:two", lineResult);
    }

    @Test
    public void capitalizeTest10() {
        inlineHelper = new WaitingInlineElementsHelper(null, null);
        inlineHelper.add(createText("(one/", true));
        inlineHelper.add(createText("two) ", true));
        inlineHelper.add(createText("(one-", true));
        inlineHelper.add(createText("two) ", true));
        inlineHelper.add(createText("(one&", true));
        inlineHelper.add(createText("two)", true));

        Div div = new Div();
        inlineHelper.flushHangingLeaves(div);
        String lineResult = getLine(div);

        Assert.assertEquals("(One/Two) (One-Two) (One&Two)", lineResult);
    }

    @Test
    public void capitalizeTest11() {
        inlineHelper = new WaitingInlineElementsHelper(null, null);
        inlineHelper.add(createText("(one: ", true));
        inlineHelper.add(createText("two) ", true));
        inlineHelper.add(createText("(one;", true));
        inlineHelper.add(createText("two) ", true));
        inlineHelper.add(createText("(one?", true));
        inlineHelper.add(createText("two)", true));

        Div div = new Div();
        inlineHelper.flushHangingLeaves(div);
        String lineResult = getLine(div);

        Assert.assertEquals("(One: Two) (One;Two) (One?Two)", lineResult);
    }

    @Test
    //TODO: replace assertNotEquals with assertEquals DEVSIX-4414
    public void capitalizeTest12() {
        inlineHelper = new WaitingInlineElementsHelper(null, null);
        inlineHelper.add(createText("one@", true));
        inlineHelper.add(createText("2two", true));

        Div div = new Div();
        inlineHelper.flushHangingLeaves(div);
        String lineResult = getLine(div);

        Assert.assertNotEquals("One@2two", lineResult);
    }

    @Test
    //TODO: replace assertNotEquals with assertEquals DEVSIX-4414
    public void capitalizeTest13() {
        inlineHelper = new WaitingInlineElementsHelper(null, null);
        inlineHelper.add(createText("_one", true));
        inlineHelper.add(createText("_@two", true));

        Div div = new Div();
        inlineHelper.flushHangingLeaves(div);
        String lineResult = getLine(div);

        Assert.assertNotEquals("_one_@Two", lineResult);
    }

    @Test
    //TODO: replace assertNotEquals with assertEquals DEVSIX-4414
    public void capitalizeTest14() {
        inlineHelper = new WaitingInlineElementsHelper(null, null);
        inlineHelper.add(createText("one'", true));
        inlineHelper.add(createText("two'", true));

        Div div = new Div();
        inlineHelper.flushHangingLeaves(div);
        String lineResult = getLine(div);

        Assert.assertNotEquals("One'two'", lineResult);
    }

    @Test
    public void capitalizeTest15() {
        inlineHelper = new WaitingInlineElementsHelper(null, null);
        inlineHelper.add(createText("( 4'", true));
        inlineHelper.add(createText("two') ", true));
        inlineHelper.add(createText("( one2(", true));
        inlineHelper.add(createText("two))", true));

        Div div = new Div();
        inlineHelper.flushHangingLeaves(div);
        String lineResult = getLine(div);

        Assert.assertEquals("( 4'Two') ( One2(Two))", lineResult);
    }

    @Test
    public void capitalizeTest16() {
        inlineHelper = new WaitingInlineElementsHelper(null, null);
        inlineHelper.add(createText("(!one", true));
        inlineHelper.add(createText("!two) ", true));
        inlineHelper.add(createText("( one:", true));
        inlineHelper.add(createText(":two) ", true));
        inlineHelper.add(createText("( one:", true));
        inlineHelper.add(createText("-two)", true));

        Div div = new Div();
        inlineHelper.flushHangingLeaves(div);
        String lineResult = getLine(div);

        Assert.assertEquals("(!One!Two) ( One::Two) ( One:-Two)", lineResult);
    }

    @Test
    public void capitalizeTest17() {
        inlineHelper = new WaitingInlineElementsHelper(null, null);
        inlineHelper.add(createText("( one:'", true));
        inlineHelper.add(createText("two') ", true));
        inlineHelper.add(createText("( one(", true));
        inlineHelper.add(createText("two))", true));

        Div div = new Div();
        inlineHelper.flushHangingLeaves(div);
        String lineResult = getLine(div);

        Assert.assertEquals("( One:'Two') ( One(Two))", lineResult);
    }

    @Test
    public void capitalizeTest18() {
        inlineHelper = new WaitingInlineElementsHelper(null, null);
        inlineHelper.add(createText("( one,", true));
        inlineHelper.add(createText("two) ", true));
        inlineHelper.add(createText("( one", true));
        inlineHelper.add(createText("~two)", true));

        Div div = new Div();
        inlineHelper.flushHangingLeaves(div);
        String lineResult = getLine(div);

        Assert.assertEquals("( One,Two) ( One~Two)", lineResult);
    }

    private Text createText(String text, boolean capitalizeProperty) {
        Text element = new Text(text);
        element.setProperty(Html2PdfProperty.CAPITALIZE_ELEMENT, capitalizeProperty);
        return element;
    }

    private String getLine(Div div) {
        List<IElement> elementList = div.getChildren();
        Paragraph paragraph = (Paragraph) elementList.get(0);
        List<IElement> paragraphChildren = paragraph.getChildren();
        StringBuilder line = new StringBuilder();
        for (IElement paragraphChild : paragraphChildren) {
            if (paragraphChild instanceof Text) {
                line.append(((Text) paragraphChild).getText());
            }
        }
        return line.toString();
    }
}
