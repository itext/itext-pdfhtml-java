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
package com.itextpdf.html2pdf.resolver.form;

import com.itextpdf.forms.form.FormProperty;
import com.itextpdf.forms.form.element.Radio;

import java.util.HashMap;
import java.util.Map;

/**
 * Utilities class that resolves radio's checked property value.
 */
public class RadioCheckResolver {

    /**
     * A map containing all the radio group names, mapped to the corresponded checked radio field.
     */
    private final Map<String, Radio> checked = new HashMap<>();

    /**
     * Creates a new {@link RadioCheckResolver} instance.
     */
    public RadioCheckResolver() {
    }

    /**
     * Checks the radio field.
     *
     * @param radioGroupName the name of the radio group the radio field belongs to
     * @param checkedField the radio field to be checked
     */
    public void checkField(String radioGroupName, Radio checkedField) {
        Radio previouslyChecked = checked.get(radioGroupName);
        if (null != previouslyChecked) {
            previouslyChecked.deleteOwnProperty(FormProperty.FORM_FIELD_CHECKED);
        }
        checked.put(radioGroupName, checkedField);
    }
}
