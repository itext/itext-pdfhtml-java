/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2017 iText Group NV
    Authors: iText Software.

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
package com.itextpdf.html2pdf.resolver.form;

import com.itextpdf.layout.element.Paragraph;
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
