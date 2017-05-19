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
package com.itextpdf.html2pdf.attach.impl;

import com.itextpdf.html2pdf.LogMessageConstant;
import com.itextpdf.html2pdf.attach.ITagWorker;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.html.impl.jsoup.node.JsoupElementNode;
import com.itextpdf.html2pdf.html.node.IElementNode;
import com.itextpdf.kernel.pdf.PdfOutline;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.navigation.PdfDestination;
import com.itextpdf.layout.ElementPropertyContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.*;

/**
 * A {@link OutlineHandler} handles creating outlines for tags.
 *
 * This class is not reusable and a new instance shall be created for every new conversion process.
 */
public class OutlineHandler {
    private static final String DESTINATION_PREFIX = "pdfHTML-iText-outline-";

    private PdfOutline currentOutline;

    private Deque<String> destinationsInProcess = new LinkedList<String>();
    private Deque<Integer> levelsInProcess = new LinkedList<Integer>();

    private Map<String, Integer> tagPrioritiesMapping = new HashMap<String, Integer>();
    private Map<String, Integer> uniqueIDs = new HashMap<String, Integer>();


    /**
     * Creates an OutlineHandler with standard predefined mappings.
     */
    public static OutlineHandler createStandardHandler() {
        OutlineHandler handler = new OutlineHandler();
        handler.putTagPriorityMapping("h1", 1);
        handler.putTagPriorityMapping("h2", 2);
        handler.putTagPriorityMapping("h3", 3);
        handler.putTagPriorityMapping("h4", 4);
        handler.putTagPriorityMapping("h5", 5);
        handler.putTagPriorityMapping("h6", 6);
        return handler;
    }

    public OutlineHandler putTagPriorityMapping(String tagName, Integer priority) {
        tagPrioritiesMapping.put(tagName, priority);
        return this;
    }

    public OutlineHandler putAllTagPriorityMappings(Map<String, Integer> mappings) {
        tagPrioritiesMapping.putAll(mappings);
        return this;
    }

    public Integer getTagPriorityMapping(String tagName) {
        return tagPrioritiesMapping.get(tagName);
    }

    public boolean hasTagPriorityMapping(String tagName) {
        return tagPrioritiesMapping.containsKey(tagName);
    }

    OutlineHandler addOutline(ITagWorker tagWorker, IElementNode element, ProcessorContext context) {
        String tagName = element.name();
        if (null != tagWorker && hasTagPriorityMapping(tagName)) {
            int level = getTagPriorityMapping(tagName);
            if (null == currentOutline) {
                currentOutline = context.getPdfDocument().getOutlines(false);
            }
            PdfOutline parent = currentOutline;
            while (!levelsInProcess.isEmpty() && level <= levelsInProcess.peek()) {
                parent = parent.getParent();
                levelsInProcess.pop();
            }
            String content = ((JsoupElementNode) element).text();
            if (content.isEmpty()) {
                content = getUniqueID(tagName);
            }
            PdfOutline outline = parent.addOutline(content);
            String destination = DESTINATION_PREFIX + getUniqueID(DESTINATION_PREFIX);
            outline.addDestination(PdfDestination.makeDestination(new PdfString(destination)));

            destinationsInProcess.push(destination);

            levelsInProcess.push(level);
            currentOutline = outline;
        }
        return this;
    }

    OutlineHandler addDestination(ITagWorker tagWorker, IElementNode element) {
        String tagName = element.name();
        if (null != tagWorker && hasTagPriorityMapping(tagName)) {
            String content = destinationsInProcess.pop();
            if (tagWorker.getElementResult() instanceof ElementPropertyContainer) {
                ((ElementPropertyContainer) tagWorker.getElementResult()).setDestination(content);
            } else {
                Logger logger = LoggerFactory.getLogger(OutlineHandler.class);
                logger.warn(MessageFormat.format(LogMessageConstant.NO_IPROPERTYCONTAINER_RESULT_FOR_THE_TAG, tagName));
            }
        }
        return this;
    }

    private String getUniqueID(String key) {
        if (!uniqueIDs.containsKey(key)) {
            uniqueIDs.put(key, 1);
        }
        int id =  uniqueIDs.get(key);
        uniqueIDs.put(key, id+1);
        return key + id;
    }
}
