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
package com.itextpdf.html2pdf.attach.impl;

import com.itextpdf.html2pdf.attach.ITagWorker;
import com.itextpdf.html2pdf.attach.ITagWorkerFactory;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.attach.impl.DefaultTagWorkerMapping.ITagWorkerCreator;
import com.itextpdf.html2pdf.util.TagProcessorMapping;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.styledxmlparser.node.IElementNode;

/**
 * The default implementation of a tag worker factory, mapping tags to
 * tag worker implementations.
 */
public class DefaultTagWorkerFactory implements ITagWorkerFactory {

    private static final ITagWorkerFactory INSTANCE = new DefaultTagWorkerFactory();

    /** The default mapping. */
    private final TagProcessorMapping<ITagWorkerCreator> defaultMapping;

    /**
     * Instantiates a new default tag worker factory.
     */
    public DefaultTagWorkerFactory() {
        this.defaultMapping = new DefaultTagWorkerMapping().getDefaultTagWorkerMapping();
    }

    /**
     * Gets {@link ITagWorkerFactory} instance.
     * @return default instance that is used if custom tag workers are not configured
     */
    public static ITagWorkerFactory getInstance() {
        return INSTANCE;
    }

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.attach.ITagWorkerFactory#getTagWorker(com.itextpdf.html2pdf.html.node.IElementNode, com.itextpdf.html2pdf.attach.ProcessorContext)
     */
    @Override
    public final ITagWorker getTagWorker(IElementNode tag, ProcessorContext context) {
        ITagWorker tagWorker = getCustomTagWorker(tag, context);

        if (tagWorker == null ) {
            final ITagWorkerCreator tagWorkerCreator = getTagWorkerCreator(this.defaultMapping, tag);

            if (tagWorkerCreator == null) {
                return null;
            }

            return tagWorkerCreator.create(tag, context);
        }

        return tagWorker;
    }

    TagProcessorMapping<ITagWorkerCreator> getDefaultMapping() {
        return defaultMapping;
    }

    /**
     * Gets the tag worker creator for a specific element node.
     *
     * @param mapping the mapping
     * @param tag the element node
     * @return the tag worker class creator
     */
    private static ITagWorkerCreator getTagWorkerCreator(TagProcessorMapping<ITagWorkerCreator> mapping,
                                                         IElementNode tag) {
        ITagWorkerCreator tagWorkerCreator = null;
        String display = tag.getStyles() != null ? tag.getStyles().get(CssConstants.DISPLAY) : null;
        if (display != null) {
            tagWorkerCreator = (ITagWorkerCreator) mapping.getMapping(tag.name(), display);
        }
        if (tagWorkerCreator == null) {
            tagWorkerCreator = (ITagWorkerCreator) mapping.getMapping(tag.name());
        }
        return tagWorkerCreator;
    }

    /**
     * This is a hook method. Users wanting to provide a custom mapping
     * or introduce their own ITagWorkers should implement this method.
     *
     * @param tag the tag
     * @param context the context
     * @return the custom tag worker
     */
    public ITagWorker getCustomTagWorker(IElementNode tag, ProcessorContext context) {
        return null;
    }

}
