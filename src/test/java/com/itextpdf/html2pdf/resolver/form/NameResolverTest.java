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
package com.itextpdf.html2pdf.resolver.form;

import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.type.UnitTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(UnitTest.class)
public class NameResolverTest extends ExtendedITextTest {

    @Test
    public void normalizationTest() {
        runTest("Dots.Should.Be.Removed", "DotsShouldBeRemoved");
    }

    @Test
    public void noNameTest() {
        runTest(new String[]{"", null, "  "},
                new String[]{"Field", "Field_1", "Field_2"});
    }

    @Test
    public void equalNamesTest() {
        runTest(new String[]{"name", "name", "name_4", "name", "name_3", "name_-2", "name_-2"},
                new String[]{"name", "name_1", "name_4", "name_5", "name_6", "name_-2", "name_-2_1"});
    }

    @Test
    public void separatorTest() {
        runTest(new String[]{"field_name", "field_name_2", "field_name"},
                new String[]{"field_name", "field_name_2", "field_name_3"});
    }

    private void runTest(String input, String expectedOutput) {
        FormFieldNameResolver nameResolver = new FormFieldNameResolver();
        Assert.assertEquals(expectedOutput, nameResolver.resolveFormName(input));
    }

    private void runTest(String[] input, String[] expectedOutput) {
        Assert.assertTrue("Input and output should be the same length", input.length == expectedOutput.length);
        FormFieldNameResolver nameResolver = new FormFieldNameResolver();
        String[] output = new String[input.length];
        for (int i = 0; i < input.length; ++i) {
            output[i] = nameResolver.resolveFormName(input[i]);
        }
        Assert.assertArrayEquals(expectedOutput, output);
    }
}
