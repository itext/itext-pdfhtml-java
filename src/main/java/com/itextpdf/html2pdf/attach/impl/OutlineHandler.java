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
package com.itextpdf.html2pdf.attach.impl;

import com.itextpdf.commons.datastructures.Tuple2;
import com.itextpdf.html2pdf.attach.IOutlineMarkExtractor;
import com.itextpdf.html2pdf.html.TagConstants;
import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.html2pdf.attach.ITagWorker;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.commons.utils.MessageFormatUtil;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfOutline;
import com.itextpdf.kernel.pdf.action.PdfAction;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.styledxmlparser.node.IElementNode;
import com.itextpdf.styledxmlparser.node.impl.jsoup.node.JsoupElementNode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * A {@link OutlineHandler} handles creating outlines for marks.
 * Marks are extracted via interface {@link IOutlineMarkExtractor}.
 * <p>
 * This class is not reusable and a new instance shall be created for every new conversion process.
 */
public class OutlineHandler {

    /**
     * The Constant DEFAULT_DESTINATION_PREFIX.
     */
    private static final String DEFAULT_DESTINATION_NAME_PREFIX = "pdfHTML-iText-outline-";

    /**
     * The destination counter.
     *
     * Counts the number of created the destinations with the same prefix in name,
     * to achieve the uniqueness of the destination names.
     *
     */
    private Map<String, Integer> destCounter = new HashMap<String, Integer>();

    /**
     * The current outline.
     */
    protected PdfOutline currentOutline;

    /**
     * The destinations in process.
     */
    protected Deque<Tuple2<String, PdfDictionary>> destinationsInProcess = new LinkedList<Tuple2<String, PdfDictionary>>();

    /**
     * The levels in process.
     */
    protected Deque<Integer> levelsInProcess = new LinkedList<Integer>();

    /**
     * The mark priorities mapping.
     */
    private Map<String, Integer> markPrioritiesMapping = new HashMap<String, Integer>();

    /**
     * The destination prefix.
     */
    private String destinationNamePrefix = DEFAULT_DESTINATION_NAME_PREFIX;

    /**
     *The mark extractor defines what part of element will be used to create outline
     */
    protected IOutlineMarkExtractor markExtractor;

    /**
     * Creates an OutlineHandler with standard {@link TagOutlineMarkExtractor}.
     */
    public OutlineHandler(){
        markExtractor = new TagOutlineMarkExtractor();
    }
    /**
     * Creates an OutlineHandler with standard {@link TagOutlineMarkExtractor} and predefined mappings.
     *
     * @return the outline handler
     */
    public static OutlineHandler createStandardHandler() {
        OutlineHandler handler = new OutlineHandler();
        handler.putMarkPriorityMapping(TagConstants.H1, 1);
        handler.putMarkPriorityMapping(TagConstants.H2, 2);
        handler.putMarkPriorityMapping(TagConstants.H3, 3);
        handler.putMarkPriorityMapping(TagConstants.H4, 4);
        handler.putMarkPriorityMapping(TagConstants.H5, 5);
        handler.putMarkPriorityMapping(TagConstants.H6, 6);
        return handler;
    }

    /**
     * Creates an OutlineHandler with custom {@link IOutlineMarkExtractor}
     *
     * @param extractor the mark extractor
     *
     * @return the outline handler
     */
    public static OutlineHandler createHandler(IOutlineMarkExtractor extractor) {
        OutlineHandler handler = new OutlineHandler();
        handler.markExtractor = extractor;
        return handler;
    }

    /**
     * Get mark extractor.
     *
     * @return the mark extractor
     */
    public IOutlineMarkExtractor getMarkExtractor(){
        return markExtractor;
    }

    /**
     * Set mark extractor.
     *
     * @param extractor the mark extractor
     *
     * @return the outline handler
     */
    public OutlineHandler setMarkExtractor(IOutlineMarkExtractor extractor){
        markExtractor = extractor;
        return this;
    }

    /**
     * Put mark into priority mapping.
     *
     * @param markName the mark name
     * @param priority the priority
     *
     * @return the outline handler
     */
    public OutlineHandler putMarkPriorityMapping(String markName, Integer priority) {
        markPrioritiesMapping.put(markName, priority);
        return this;
    }

    /**
     * Put all marks into priority mappings.
     *
     * @param mappings the mappings
     *
     * @return the outline handler
     */
    public OutlineHandler putAllMarksPriorityMappings(Map<String, Integer> mappings) {
        markPrioritiesMapping.putAll(mappings);
        return this;
    }

    /**
     * Gets the mark from priority mapping.
     *
     * @param markName the mark name
     *
     * @return the tag priority mapping
     */
    public Integer getMarkPriorityMapping(String markName) {
        return markPrioritiesMapping.get(markName);
    }

    /**
     * Checks for tag in priority mapping.
     *
     * @param markName the mark name
     *
     * @return true, if the tag name is listed in the tag priorities mapping
     */
    public boolean hasMarkPriorityMapping(String markName) {
        if (markName == null){
            return false;
        } else {
            return markPrioritiesMapping.containsKey(markName);
        }
    }

    /**
     * Resets the current state so that this {@link OutlineHandler} is ready to process new document
     */
    public void reset() {
        currentOutline = null;
        destinationsInProcess.clear();
        levelsInProcess.clear();
        destCounter.clear();
    }

    /**
     * Sets the destination name prefix.
     *
     * The destination name prefix serves as the prefix for the destination names created in the
     * {@link #generateUniqueDestinationName} method.
     *
     * @param destinationNamePrefix the destination name prefix
     */
    public void setDestinationNamePrefix(String destinationNamePrefix) {
        this.destinationNamePrefix = destinationNamePrefix;
    }

    /**
     * Gets the destination name prefix.
     *
     * The destination name prefix serves as the prefix for the destination names created in the
     * {@link #generateUniqueDestinationName} method.
     *
     * @return the destination name prefix
     */
    public String getDestinationNamePrefix() {
        return destinationNamePrefix;
    }

    /**
     * Generate the unique destination name.
     *
     * The destination name is a unique identifier for the outline so it is generated for the outline
     * in the {@link #addOutlineAndDestToDocument} method. You can override this method to set
     * your own way to generate the destination names, to avoid the destination name conflicts when
     * merging several PDF files created by html2pdf.
     *
     * @param element the element
     *
     * @return the unique destination name
     */
    protected String generateUniqueDestinationName(IElementNode element) {
        return getUniqueID(destinationNamePrefix);
    }

    /**
     * Generate the outline name.
     *
     * This method is used in the {@link #addOutlineAndDestToDocument} method.
     * You can override this method to set your own way to generate the outline names.
     *
     * @param element the element
     *
     * @return the unique destination name
     */
    protected String generateOutlineName(IElementNode element) {
        String markName = markExtractor.getMark(element);
        String content = ((JsoupElementNode) element).text();
        if (content.isEmpty()) {
            content = getUniqueID(markName);
        }
        return content;
    }

    /**
     * Adds the outline and the destination.
     *
     * Adds the outline and its corresponding the destination to the PDF document
     * if the priority mapping is set for the element.
     *
     * @param tagWorker the tag worker
     * @param element   the element
     * @param context   the processor context
     *
     * @return the outline handler
     */
    protected OutlineHandler addOutlineAndDestToDocument(ITagWorker tagWorker, IElementNode element,
                                                         ProcessorContext context) {
        String markName = markExtractor.getMark(element);
        if (null != tagWorker && hasMarkPriorityMapping(markName) && context.getPdfDocument() != null) {
            int level = (int) getMarkPriorityMapping(markName);
            if (null == currentOutline) {
                currentOutline = context.getPdfDocument().getOutlines(false);
            }
            PdfOutline parent = currentOutline;
            while (!levelsInProcess.isEmpty() && level <= levelsInProcess.getFirst()) {
                parent = parent.getParent();
                levelsInProcess.pop();
            }
            PdfOutline outline = parent.addOutline(generateOutlineName(element));
            String destination = generateUniqueDestinationName(element);
            PdfAction action = PdfAction.createGoTo(destination);
            outline.addAction(action);
            destinationsInProcess.push(new Tuple2<String, PdfDictionary>(destination, action.getPdfObject()));

            levelsInProcess.push(level);
            currentOutline = outline;
        }
        return this;
    }

    /**
     * Sets the destination to element.
     *
     * Sets the destination previously created in the {@link #addOutlineAndDestToDocument} method
     * to the tag worker element.
     *
     * @param tagWorker the tag worker
     * @param element   the element
     *
     * @return the outline handler
     */
    protected OutlineHandler setDestinationToElement(ITagWorker tagWorker, IElementNode element) {
        String markName = markExtractor.getMark(element);
        if (null != tagWorker && hasMarkPriorityMapping(markName) && destinationsInProcess.size() > 0) {
            Tuple2<String, PdfDictionary> content = destinationsInProcess.pop();
            if (tagWorker.getElementResult() instanceof IElement) {
                tagWorker.getElementResult().setProperty(Property.DESTINATION, content);
            } else {
                Logger logger = LoggerFactory.getLogger(OutlineHandler.class);
                logger.warn(MessageFormatUtil.format(
                        Html2PdfLogMessageConstant.NO_IPROPERTYCONTAINER_RESULT_FOR_THE_TAG, markName));
            }
        }
        return this;
    }

    /**
     * Gets the unique ID.
     *
     * This method is used in the {@link #generateUniqueDestinationName} method to generate the unique
     * destination names and in the {@link #generateOutlineName} method to generate the unique
     * outline names. The {@link #destCounter} map serves to achieve the uniqueness of an ID.
     *
     * @param key the key
     *
     * @return the unique ID
     */
    private String getUniqueID(String key) {
        if (!destCounter.containsKey(key)) {
            destCounter.put(key, 1);
        }
        int id = (int) destCounter.get(key);
        destCounter.put(key, id + 1);
        return key + id;
    }
}
