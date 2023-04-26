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
package com.itextpdf.html2pdf.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Class that allows to map keys (html tags, css attributes) to the
 * corresponding tag processors (a tag worker or a CSS applier).
 */
public class TagProcessorMapping<T> {

    /** The default display key. */
    private static String DEFAULT_DISPLAY_KEY = "defaultKey";

    /** The actual mapping. */
    private Map<String, Map<String, T>> mapping;

    /**
     * Creates a new {@link TagProcessorMapping} instance.
     */
    public TagProcessorMapping() {
        mapping = new HashMap<>();
    }

    /**
     * Add a new tag to the map.
     *
     * @param tag the key
     * @param mapping the class instance that maps to the tag
     */
    public void putMapping(String tag, T mapping) {
        ensureMappingExists(tag).put(DEFAULT_DISPLAY_KEY, mapping);
    }

    /**
     * Add a new tag to the map.
     *
     * @param tag the key
     * @param display the display value
     * @param mapping the class instance that maps to the tag
     */
    public void putMapping(String tag, String display, T mapping) {
        ensureMappingExists(tag).put(display, mapping);
    }

    /**
     * Gets the class that maps to a specific tag.
     *
     * @param tag the key
     * @return the class that maps to the tag
     */
    public Object getMapping(String tag) {
        return getMapping(tag, DEFAULT_DISPLAY_KEY);
    }

    /**
     * Gets the class that maps to a specific tag.
     *
     * @param tag the key
     * @param display the display value
     * @return the class that maps to the tag
     */
    public Object getMapping(String tag, String display) {
        Map<String, T> tagMapping = mapping.get(tag);
        if (tagMapping == null) {
            return null;
        } else {
            return tagMapping.get(display);
        }
    }

    /**
     * Ensure that a mapping for a specific key exists.
     *
     * @param tag the key
     * @return the map
     */
    private Map<String, T> ensureMappingExists(String tag) {
        if (mapping.containsKey(tag)) {
            return mapping.get(tag);
        } else {
            Map<String, T> tagMapping = new HashMap<>();
            mapping.put(tag, tagMapping);
            return tagMapping;
        }
    }

}
