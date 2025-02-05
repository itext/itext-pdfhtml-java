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
package com.itextpdf.html2pdf.css.apply.impl;

import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.css.apply.ICssApplier;
import com.itextpdf.html2pdf.css.apply.ICssApplierFactory;
import com.itextpdf.html2pdf.css.apply.impl.DefaultTagCssApplierMapping.ICssApplierCreator;
import com.itextpdf.html2pdf.util.TagProcessorMapping;
import com.itextpdf.styledxmlparser.node.IElementNode;

/**
 * A factory for creating {@link ICssApplier} objects.
 */
public class DefaultCssApplierFactory implements ICssApplierFactory {

    private static final ICssApplierFactory INSTANCE = new DefaultCssApplierFactory();

    /** The default mapping of CSS keywords and CSS appliers. */
    private final TagProcessorMapping<ICssApplierCreator> defaultMapping;

    /**
     * Creates a new {@link DefaultCssApplierFactory} instance.
     */
    public DefaultCssApplierFactory() {
        defaultMapping = new DefaultTagCssApplierMapping().getDefaultCssApplierMapping();
    }

    /**
     * Gets {@link DefaultCssApplierFactory} instance.
     * @return default instance that is used if custom css appliers are not configured
     */
    public static ICssApplierFactory getInstance() {
        return INSTANCE;
    }

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.css.apply.ICssApplierFactory#getCssApplier(com.itextpdf.html2pdf.html.node.IElementNode)
     */
    @Override
    public final ICssApplier getCssApplier(IElementNode tag) {
        ICssApplier cssApplier = getCustomCssApplier(tag);

        if (cssApplier == null) {
            final ICssApplierCreator cssApplierCreator = getCssApplierCreator(defaultMapping, tag);

            if (cssApplierCreator == null) {
                return null;
            }

            return cssApplierCreator.create();
        }

        return cssApplier;
    }

    /**
     * Gets a custom CSS applier.
     * This method needs to be overridden because the default CSS applier
     * factory will always return {@code null}.
     *
     * @param tag the key
     * @return the custom CSS applier
     */
    public ICssApplier getCustomCssApplier(IElementNode tag) {
        return null;
    }

    TagProcessorMapping<ICssApplierCreator> getDefaultMapping() {
        return defaultMapping;
    }

    /**
     * Gets the css applier class.
     *
     * @param mapping the mapping
     * @param tag the tag
     * @return the css applier class creator
     */
    private static ICssApplierCreator getCssApplierCreator(TagProcessorMapping<ICssApplierCreator> mapping,
                                                           IElementNode tag) {
        ICssApplierCreator cssApplierCreator = null;
        String display = tag.getStyles() != null ? tag.getStyles().get(CssConstants.DISPLAY) : null;
        if (display != null) {
            cssApplierCreator = (ICssApplierCreator) mapping.getMapping(tag.name(), display);
        }
        if (cssApplierCreator == null) {
            cssApplierCreator = (ICssApplierCreator) mapping.getMapping(tag.name());
        }
        return cssApplierCreator;
    }

}
