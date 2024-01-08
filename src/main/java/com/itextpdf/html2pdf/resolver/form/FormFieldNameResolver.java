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

import com.itextpdf.styledxmlparser.css.util.CssDimensionParsingUtils;
import java.util.HashMap;
import java.util.Map;

/**
 * Utilities class that resolves form field names.
 */
public class FormFieldNameResolver {
    
    /** The default base name of a field: "Field". */
    private static final String DEFAULT_NAME = "Field";
    
    /** The separator between a field name and an index. */
    private static final String NAME_COUNT_SEPARATOR = "_";
    
    /** A map containing all the base field names, mapped to the current index. */
    private final Map<String, Integer> names = new HashMap<>();

    /**
     * Creates a new {@link FormFieldNameResolver} instance.
     */
    public FormFieldNameResolver() {
    }

    /**
     * Resolves a proposed field name to a valid field name.
     *
     * @param name the proposed name
     * @return the valid name
     */
    public String resolveFormName(String name) {
        name = normalizeString(name);
        if (name.isEmpty()) {
            return resolveNormalizedFormName(DEFAULT_NAME);
        } else {
            return resolveNormalizedFormName(name);
        }

    }

    /**
     * Resets the map containing all the field names.
     */
    public void reset() {
        names.clear();
    }

    /**
     * Normalizes a field name.
     *
     * @param s the proposed field name
     * @return the normalized name
     */
    private String normalizeString(String s) {
        return s != null ? s.trim().replace(".", "") : "";
    }

    /**
     * Resolves a normalized form name.
     *
     * @param name the proposed name
     * @return the resolved name
     */
    private String resolveNormalizedFormName(String name) {
        int separatorIndex = name.lastIndexOf(NAME_COUNT_SEPARATOR);
        Integer nameIndex = null;
        if (separatorIndex != -1 && separatorIndex < name.length()) {
            String numberString = name.substring(separatorIndex + 1);
            nameIndex = CssDimensionParsingUtils.parseInteger(numberString);
            //Treat number as index only in case it is positive
            if (nameIndex != null && nameIndex > 0) {
                name = name.substring(0, separatorIndex);
            }
        }
        Integer savedIndex = names.get(name);
        int indexToSave = savedIndex != null ? savedIndex.intValue() + 1 : 0;
        if (nameIndex != null && indexToSave < nameIndex.intValue()) {
            indexToSave = nameIndex.intValue();
        }
        names.put(name, indexToSave);
        return indexToSave == 0 ? name : name + NAME_COUNT_SEPARATOR + String.valueOf(indexToSave);
    }
}
