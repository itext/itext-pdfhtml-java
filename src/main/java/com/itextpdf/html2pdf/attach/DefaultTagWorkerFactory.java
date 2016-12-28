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
package com.itextpdf.html2pdf.attach;

import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.exception.TagWorkerInitializationException;
import com.itextpdf.html2pdf.html.node.IElementNode;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

public class DefaultTagWorkerFactory implements ITagWorkerFactory {

    /**
     * Internal map to keep track of tags and associated tag workers
     */
    private Map<String, Class<?>> tagMap;

    //Internal mappings of tag workers and display ccs property
    private Map<String, Class<?>> displayMap;
    // Tags that will consider display property while creating tagWorker.
    private Set<String> displayPropertySupportedTags;

    public DefaultTagWorkerFactory() {
        this.tagMap = new ConcurrentHashMap<String, Class<?>>();
        this.displayMap = new ConcurrentHashMap<String, Class<?>>();
        this.displayPropertySupportedTags = new HashSet<>();
        registerDefaultHtmlTagWorkers();
    }

    @Override
    public ITagWorker getTagWorkerInstance(IElementNode tag, ProcessorContext context) throws TagWorkerInitializationException {
        // Get Tag Worker class name
        Class<?> tagWorkerClass = tagMap.get(tag.name());
        // No tag worker found for tag
        if (tagWorkerClass == null) {
            return null;
        }
        if (tag.getStyles() != null) {
            String displayCssProp = tag.getStyles().get(CssConstants.DISPLAY);
            if (displayCssProp != null) {
                Class<?> displayWorkerClass = displayMap.get(displayCssProp);
                if (displayPropertySupportedTags.contains(tag.name()) && displayWorkerClass != null) {
                    tagWorkerClass = displayWorkerClass;
                }
            }
        }
        // Use reflection to create an instance
        try {
            Constructor ctor = tagWorkerClass.getDeclaredConstructor(new Class<?>[]{IElementNode.class, ProcessorContext.class});
            ITagWorker res = (ITagWorker) ctor.newInstance(new Object[]{tag, context});
            return res;
        } catch (Exception e) {
            throw new TagWorkerInitializationException(TagWorkerInitializationException.REFLECTION_IN_TAG_WORKER_FACTORY_IMPLEMENTATION_FAILED, tagWorkerClass.getName(), tag.name());
        }
    }

    @Override
    public void registerTagWorker(String tag, Class<?> tagWorkerClass) {
        tagMap.put(tag, tagWorkerClass);
    }

    @Override
    public void removeTagWorker(String tag) {
        tagMap.remove(tag);
    }

    private void registerDefaultHtmlTagWorkers() {
        Map<String, Class<?>> defaultMapping = DefaultTagWorkerMapping.getDefaultTagWorkerMapping();
        for (Map.Entry<String, Class<?>> ent : defaultMapping.entrySet()) {
            tagMap.put(ent.getKey(), ent.getValue());
        }
        displayMap.putAll(DefaultDisplayWorkerMapping.getDefaultDisplayWorkerMapping());
        displayPropertySupportedTags.addAll(DefaultDisplayWorkerMapping.getSupportedTags());
    }
}
