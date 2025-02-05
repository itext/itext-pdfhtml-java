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
package com.itextpdf.html2pdf.css.apply.util;

import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import com.itextpdf.styledxmlparser.node.IElementNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Utility class which processes text decoration properties.
 */
public final class TextDecorationApplierUtil {
    private static final String IGNORED = "__ignored__";
    private static final SupportedTextDecoration[] SUPPORTED_TEXT_DECORATION_PROPERTIES =
            new SupportedTextDecoration[] {
                    new SupportedTextDecoration(CommonCssConstants.TEXT_DECORATION_LINE, IGNORED),
                    new SupportedTextDecoration(CommonCssConstants.TEXT_DECORATION_STYLE, CommonCssConstants.SOLID),
                    new SupportedTextDecoration(CommonCssConstants.TEXT_DECORATION_COLOR,
                            CommonCssConstants.CURRENTCOLOR)
            };
    private static final int AMOUNT = SUPPORTED_TEXT_DECORATION_PROPERTIES.length;

    private TextDecorationApplierUtil() {
    }

    /**
     * Expand text decoration properties to the element if needed.
     * <p>
     * E.g. the following:
     * {@code text-decoration-line: overline underline; text-decoration-color: pink}
     * will be translated to:
     * {@code text-decoration-line: overline underline; text-decoration-color: pink pink}
     *
     * @param currentNode the element which decoration properties shall be expanded
     */
    public static void propagateTextDecorationProperties(IElementNode currentNode) {
        expandTextDecorationProperties(currentNode);
        if (!shouldPropagateTextDecorationProperties(currentNode)) {
            return;
        }
        mergeProperties(currentNode);
    }

    private static boolean shouldOnlyKeepParentProperties(IElementNode currentNode) {
        return currentNode.getStyles().get(CommonCssConstants.TEXT_DECORATION_LINE) != null
                && currentNode.getStyles().get(CommonCssConstants.TEXT_DECORATION_LINE)
                .contains(CommonCssConstants.NONE);
    }

    private static boolean shouldPropagateTextDecorationProperties(IElementNode currentNode) {
        final IElementNode parent = (IElementNode) currentNode.parentNode();
        if (parent == null || parent.getStyles() == null) {
            return false;
        }
        if (doesNotHaveTextDecorationKey(currentNode) && doesNotHaveTextDecorationKey(parent)) {
            return false;
        }
        if (CommonCssConstants.INLINE_BLOCK.equals(currentNode.getStyles().get(CommonCssConstants.DISPLAY))) {
            return false;
        }
        return true;
    }

    private static List<String> parseTokenIntoList(IElementNode node, String propertyName) {
        final String currentValue = node.getStyles().get(propertyName);
        return currentValue == null ? new ArrayList<>() : Arrays.asList(currentValue.split("\\s+"));
    }

    private static void mergeProperties(IElementNode node) {
        // this method expands the text decoration properties
        // example we have a  style like this:
        //      text-decoration-line: overline underline
        //      text-decoration-color: pink
        //      text-decoration-style: solid
        // will become underlying to avoid loss of data
        //      text-decoration-line: overline underline
        //      text-decoration-color: pink pink
        //      text-decoration-style: solid solid
        final boolean onlyKeepParentProperties = shouldOnlyKeepParentProperties(node);
        final List<List<String>> data = new ArrayList<>();
        for (final SupportedTextDecoration supportedTextDecoration : SUPPORTED_TEXT_DECORATION_PROPERTIES) {
            final List<String> currentValues = parseTokenIntoList(node, supportedTextDecoration.getPropertyName());
            final List<String> parentValues = parseTokenIntoList((IElementNode) node.parentNode(),
                    supportedTextDecoration.getPropertyName());
            final List<String> properties = new ArrayList<>(parentValues);
            data.add(properties);
            if (currentValues.isEmpty() || onlyKeepParentProperties) {
                continue;
            }
            properties.addAll(currentValues);
        }

        final int amount = data.get(0).size();
        final Set<String> uniqueMaker = new LinkedHashSet<>();
        //create a list for the keys so the location of each property is the same
        final List<Integer> indexListToKeep = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            final StringBuilder hashBuilder = new StringBuilder();
            for (int j = 0; j < AMOUNT; j++) {
                hashBuilder.append(data.get(j).get(i));
            }
            final String hash = hashBuilder.toString();
            // if it doesn't contain the hash this value is unique so the indexes should be stored
            if (!hash.contains(IGNORED) && !uniqueMaker.contains(hash)) {
                indexListToKeep.add((int) i);
                uniqueMaker.add(hashBuilder.toString());
            }
        }
        for (int i = 0; i < AMOUNT; i++) {
            final StringBuilder result = new StringBuilder();
            for (final Integer integer : indexListToKeep) {
                int index =(int) integer;
                result.append(data.get(i).get(index)).append(' ');
            }
            node.getStyles().put(SUPPORTED_TEXT_DECORATION_PROPERTIES[i].getPropertyName(), result.toString().trim());
        }

    }


    private static boolean doesNotHaveTextDecorationKey(IElementNode node) {
        for (int i = 0; i < AMOUNT; i++) {
            if (node.getStyles().containsKey(SUPPORTED_TEXT_DECORATION_PROPERTIES[i].getPropertyName())) {
                return false;
            }
        }
        return true;
    }

    private static void expandTextDecorationProperties(IElementNode node) {
        if (doesNotHaveTextDecorationKey(node)) {
            return;
        }
        final List<String[]> currentValuesParsed = new ArrayList<>(AMOUNT);
        for (final SupportedTextDecoration supportedTextDecorationProperty : SUPPORTED_TEXT_DECORATION_PROPERTIES) {
            final String unParsedValue = node.getStyles().get(supportedTextDecorationProperty.getPropertyName());
            String[] value = supportedTextDecorationProperty.getDefaultValue();
            if (unParsedValue != null) {
                value = unParsedValue.split("\\s+");
            }
            assert value != null;
            assert value.length > 0;
            currentValuesParsed.add(value);
        }
        int maxValue = currentValuesParsed.get(0).length;
        for (final String[] strings : currentValuesParsed) {
            if (strings.length > maxValue) {
                maxValue = strings.length;
            }
        }
        final List<StringBuilder> correctValues = new ArrayList<>(AMOUNT);
        for (int i = 0; i < AMOUNT; i++) {
            correctValues.add(new StringBuilder());
        }
        for (int i = 0; i < maxValue; i++) {
            for (int indexProperty = 0; indexProperty < AMOUNT; indexProperty++) {
                correctValues.get(indexProperty)
                        .append(getValueAtIndexOrLast(currentValuesParsed, i, indexProperty))
                        .append(' ');
            }
        }
        for (int i = 0; i < AMOUNT; i++) {
            node.getStyles()
                    .put(SUPPORTED_TEXT_DECORATION_PROPERTIES[i].getPropertyName(),
                            correctValues.get(i).toString().trim());
        }
    }

    private static String getValueAtIndexOrLast(List<String[]> currentValuesParsed, int i, int indexProperty) {
        return currentValuesParsed.get(indexProperty).length - 1 > i
                ? currentValuesParsed.get(indexProperty)[i]
                : currentValuesParsed.get(indexProperty)[currentValuesParsed.get(indexProperty).length - 1];
    }

    private static final class SupportedTextDecoration {
        private final String propertyName;
        private final String[] defaultValue;
        public SupportedTextDecoration(String propertyName, String defaultValue) {
            this.propertyName = propertyName;
            this.defaultValue = new String[] {defaultValue};
        }
        public String getPropertyName() {
            return propertyName;
        }

        public String[] getDefaultValue() {
            return (String[]) defaultValue.clone();
        }


    }
}


