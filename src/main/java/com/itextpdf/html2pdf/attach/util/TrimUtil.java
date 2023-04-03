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
package com.itextpdf.html2pdf.attach.util;

import com.itextpdf.html2pdf.attach.impl.layout.RunningElement;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.layout.LayoutPosition;
import com.itextpdf.layout.properties.FloatPropertyValue;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.styledxmlparser.util.WhiteSpaceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class to trim content.
 */
public final class TrimUtil {

    /**
     * Creates a new {@link TrimUtil} instance.
     */
    private TrimUtil() {
    }

    /**
     * Trim leaf elements, and sanitize.
     *
     * @param leafElements the leaf elements
     * @return the trimmed and sanitized list
     */
    static List<IElement> trimLeafElementsAndSanitize(List<IElement> leafElements) {
        ArrayList<IElement> waitingLeaves = new ArrayList<IElement>(leafElements);

        trimSubList(waitingLeaves, 0, waitingLeaves.size(), false);
        trimSubList(waitingLeaves, 0, waitingLeaves.size(), true);

        int pos = 0;
        while (pos < waitingLeaves.size() - 1) {
            if (waitingLeaves.get(pos) instanceof Text) {
                Text first = (Text) waitingLeaves.get(pos);
                if (isElementFloating(first)) {
                    trimTextElement(first, false);
                    trimTextElement(first, true);
                } else {
                    int firstEnd = getIndexAfterLastNonSpace(first);
                    if (firstEnd < first.getText().length()) {
                        trimSubList(waitingLeaves, pos + 1, waitingLeaves.size(), false);
                        first.setText(first.getText().substring(0, firstEnd + 1));
                    }
                }
            }
            pos++;
        }

        return waitingLeaves;
    }

    /**
     * Checks if a character is white space value that doesn't cause a newline.
     *
     * @param ch the character
     * @return true, if the character is a white space character, but no newline
     */
    static boolean isNonLineBreakSpace(char ch) {
        return WhiteSpaceUtil.isNonEmSpace(ch) && ch != '\n';
    }


    /**
     * Trims a sub list of leaf elements.
     *
     * @param list the list of leaf elements
     * @param begin the index where to begin
     * @param end the index where to end
     * @param last indicates where to start, if true, we start at the end
     */
    private static void trimSubList(ArrayList<IElement> list, int begin, int end, boolean last) {
        while (end > begin) {
            int pos = last ? end - 1 : begin;
            IElement leaf = list.get(pos);
            if (isElementFloating(leaf) || leaf instanceof RunningElement) {
                if (last) { --end; }
                else      { ++begin; }
                continue;
            }
            if (leaf instanceof Text) {
                Text text = (Text) leaf;
                trimTextElement(text, last);
                if (text.getText().length() == 0) {
                    if (hasZeroWidth(text)) {
                        list.remove(pos);
                    }
                    end--;
                    continue;
                }
            }
            break;
        }
    }

    /**
     * Trims a text element.
     *
     * @param text the text element
     * @param last indicates where to start, if true, we start at the end
     */
    private static void trimTextElement(Text text, boolean last) {
        int begin = last ? 0 : getIndexOfFirstNonSpace(text);
        int end = last ? getIndexAfterLastNonSpace(text) : text.getText().length();
        text.setText(text.getText().substring(begin, end));
    }

    /**
     * Gets the index of first character that isn't white space in some text.
     * Note: newline characters aren't counted as white space characters.
     *
     * @param text the text
     * @return the index of first character that isn't white space
     */
    private static int getIndexOfFirstNonSpace(Text text) {
        int pos = 0;
        while (pos < text.getText().length() && isNonLineBreakSpace(text.getText().charAt(pos))) {
            pos++;
        }
        return pos;
    }

    /**
     * Gets the index of last character following a character that isn't white space in some text.
     * Note: newline characters aren't counted as white space characters.
     *
     * @param text the text
     * @return the index following the last character that isn't white space
     */
    private static int getIndexAfterLastNonSpace(Text text) {
        int pos = text.getText().length();
        while (pos > 0 && isNonLineBreakSpace(text.getText().charAt(pos - 1))) {
            pos--;
        }
        return pos;
    }

    private static boolean isElementFloating(IElement leafElement) {
        FloatPropertyValue floatPropertyValue = leafElement.<FloatPropertyValue>getProperty(Property.FLOAT);
        Integer position = leafElement.<Integer>getProperty(Property.POSITION);
        return (position == null || position != LayoutPosition.ABSOLUTE) && floatPropertyValue != null && !floatPropertyValue.equals(FloatPropertyValue.NONE);
    }

    private static boolean hasZeroWidth(IElement leafElement) {
        return
                (null == leafElement.<Border>getProperty(Property.BORDER_RIGHT) || 0 == ((Border) leafElement.<Border>getProperty(Property.BORDER_RIGHT)).getWidth()) &&
                (null == leafElement.<Border>getProperty(Property.BORDER_LEFT) || 0 == ((Border) leafElement.<Border>getProperty(Property.BORDER_LEFT)).getWidth()) &&
                 // Note that iText parses padding and float values to points so the next unit values are always point values
                (null == leafElement.<UnitValue>getProperty(Property.PADDING_RIGHT) || 0 == leafElement.<UnitValue>getProperty(Property.PADDING_RIGHT).getValue()) &&
                (null == leafElement.<UnitValue>getProperty(Property.PADDING_LEFT) || 0 == leafElement.<UnitValue>getProperty(Property.PADDING_LEFT).getValue()) &&
                (null == leafElement.<UnitValue>getProperty(Property.MARGIN_RIGHT) || 0 == leafElement.<UnitValue>getProperty(Property.MARGIN_RIGHT).getValue()) &&
                (null == leafElement.<UnitValue>getProperty(Property.MARGIN_LEFT) || 0 == leafElement.<UnitValue>getProperty(Property.MARGIN_LEFT).getValue());
    }
}
