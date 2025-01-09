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
package com.itextpdf.html2pdf.attach.impl.layout;

import com.itextpdf.test.ExtendedITextTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

import java.lang.reflect.Field;
import com.itextpdf.commons.utils.MessageFormatUtil;
import java.util.HashSet;
import java.util.Set;

@Tag("UnitTest")
public class Html2PdfPropertyTest extends ExtendedITextTest {

    @Test
    public void propertyUniquenessTest() throws IllegalAccessException {
        Set<Integer> fieldValues = new HashSet<>();
        int maxFieldValue = Integer.MIN_VALUE;
        int minFieldValue = Integer.MAX_VALUE;
        for (Field field : Html2PdfProperty.class.getFields()) {
            if (field.getType() == int.class) {
                int value = (int) field.get(null);
                maxFieldValue = Math.max(maxFieldValue, value);
                minFieldValue = Math.min(minFieldValue, value);
                if (fieldValues.contains(value)) {
                    Assertions.fail(MessageFormatUtil.format("Multiple fields with same value: {0}", value));
                }
                fieldValues.add(value);
            }
        }

        for (int i = minFieldValue; i <= maxFieldValue; i++) {
            if (!fieldValues.contains(i)) {
                Assertions.fail(MessageFormatUtil.format("Missing value: {0}", i));
            }
        }

        System.out.println(MessageFormatUtil.format("Max field value: {0}", maxFieldValue));
    }

}
