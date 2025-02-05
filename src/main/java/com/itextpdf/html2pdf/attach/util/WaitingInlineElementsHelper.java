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
package com.itextpdf.html2pdf.attach.util;

import com.itextpdf.html2pdf.attach.impl.layout.Html2PdfProperty;
import com.itextpdf.html2pdf.attach.impl.layout.RunningElement;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.css.apply.util.OverflowApplierUtil;
import com.itextpdf.kernel.pdf.tagging.StandardRoles;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.element.ILeafElement;
import com.itextpdf.layout.element.ListItem;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.renderer.FlexContainerRenderer;
import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import com.itextpdf.styledxmlparser.util.WhiteSpaceUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for waiting inline elements.
 */
public class WaitingInlineElementsHelper {

    /**
     * A value that defines how to transform text.
     */
    private String textTransform;

    /**
     * Indicates whether line breaks need to be preserved.
     */
    private boolean keepLineBreaks;

    /**
     * Indicates whether white space characters need to be collapsed.
     */
    private boolean collapseSpaces;

    /**
     * List of waiting leaf elements.
     */
    private List<IElement> waitingLeaves = new ArrayList<>();

    /**
     * Creates a new {@link WaitingInlineElementsHelper} instance.
     *
     * @param whiteSpace    we'll check if this value equals "pre" or "pre-wrap"
     * @param textTransform will define the transformation that needs to be applied to the text
     */
    public WaitingInlineElementsHelper(String whiteSpace, String textTransform) {
        keepLineBreaks = CssConstants.PRE.equals(whiteSpace) || CssConstants.PRE_WRAP.equals(whiteSpace) || CssConstants.PRE_LINE.equals(whiteSpace);
        collapseSpaces = !(CssConstants.PRE.equals(whiteSpace) || CssConstants.PRE_WRAP.equals(whiteSpace));
        this.textTransform = textTransform;
    }

    /**
     * Adds text to the waiting leaves.
     *
     * @param text the text
     */
    public void add(String text) {
        text = WhiteSpaceUtil.processWhitespaces(text, keepLineBreaks, collapseSpaces);

        if (CssConstants.UPPERCASE.equals(textTransform)) {
            text = text.toUpperCase();
        } else if (CssConstants.LOWERCASE.equals(textTransform)) {
            text = text.toLowerCase();
        }

        waitingLeaves.add(new Text(text));
    }

    /**
     * Adds a leaf element to the waiting leaves.
     *
     * @param element the element
     */
    public void add(ILeafElement element) {
        waitingLeaves.add(element);
    }

    public void add(IBlockElement element) {
        waitingLeaves.add(element);
    }

    /**
     * Adds a collecton of leaf elements to the waiting leaves.
     *
     * @param collection the collection
     */
    public void addAll(Collection<ILeafElement> collection) {
        waitingLeaves.addAll(collection);
    }

    /**
     * Flush hanging leaves.
     *
     * @param container a container element
     */
    public void flushHangingLeaves(IPropertyContainer container) {
        Paragraph p = createLeavesContainer();
        if (p != null) {
            Map<String, String> map = new HashMap<>();
            map.put(CssConstants.OVERFLOW, CommonCssConstants.VISIBLE);
            OverflowApplierUtil.applyOverflow(map, p);
            if (container instanceof Document) {
                ((Document) container).add(p);
            } else if (container instanceof Paragraph) {
                for (IElement leafElement : waitingLeaves) {
                    if (leafElement instanceof ILeafElement) {
                        ((Paragraph) container).add((ILeafElement) leafElement);
                    } else if (leafElement instanceof IBlockElement) {
                        ((Paragraph) container).add((IBlockElement) leafElement);
                    }
                }
            } else if (((IElement) container).getRenderer() instanceof FlexContainerRenderer) {
                final Div div = new Div();
                OverflowApplierUtil.applyOverflow(map, div);
                div.add(p);
                ((Div) container).add(div);
            } else if (container instanceof Div) {
                ((Div) container).add(p);
            } else if (container instanceof Cell) {
                ((Cell) container).add(p);
            } else if (container instanceof com.itextpdf.layout.element.List) {
                ListItem li = new ListItem();
                li.add(p);
                ((com.itextpdf.layout.element.List) container).add(li);
            } else {
                throw new IllegalStateException("Unable to process hanging inline content");
            }
            waitingLeaves.clear();
        }
    }

    /**
     * Creates the leaves container.
     *
     * @return a paragraph
     */
    private Paragraph createLeavesContainer() {
        if (collapseSpaces) {
            waitingLeaves = TrimUtil.trimLeafElementsAndSanitize(waitingLeaves);
        }
        capitalize(waitingLeaves);

        if (waitingLeaves.size() > 0) {
            Paragraph p = createParagraphContainer();
            boolean runningElementsOnly = true;
            for (IElement leaf : waitingLeaves) {
                if (leaf instanceof ILeafElement) {
                    runningElementsOnly = false;
                    p.add((ILeafElement) leaf);
                } else if (leaf instanceof IBlockElement) {
                    runningElementsOnly = runningElementsOnly && leaf instanceof RunningElement;
                    p.add((IBlockElement) leaf);
                }
            }
            if (runningElementsOnly) {
                // TODO DEVSIX-7008 Remove completely empty tags from logical structure of resultant PDF documents
                p.getAccessibilityProperties().setRole(StandardRoles.ARTIFACT);
            }
            return p;
        } else {
            return null;
        }
    }

    /**
     * Gets the waiting leaves.
     *
     * @return the waiting leaves
     */
    public Collection<IElement> getWaitingLeaves() {
        return waitingLeaves;
    }

    /**
     * Gets the sanitized waiting leaves.
     *
     * @return the sanitized waiting leaves
     */
    public List<IElement> getSanitizedWaitingLeaves() {
        if (collapseSpaces) {
            return TrimUtil.trimLeafElementsAndSanitize(waitingLeaves);
        } else {
            return waitingLeaves;
        }
    }

    /**
     * Clears the waiting leaves.
     */
    public void clearWaitingLeaves() {
        waitingLeaves.clear();
    }

    /**
     * Creates a paragraph container.
     *
     * @return the paragraph container
     */
    public Paragraph createParagraphContainer() {
        return new Paragraph().setMargin(0);
    }

    /**
     * Capitalizes a series of leaf elements.
     *
     * @param leaves a list of leaf elements
     */
    private void capitalize(List<IElement> leaves) {
        boolean previousLetter = false;
        boolean previousProcessed = false;
        for (int i = 0; i < leaves.size(); i++) {
            IElement element = leaves.get(i);
            boolean hasCapitalizeProperty = element.hasOwnProperty(Html2PdfProperty.CAPITALIZE_ELEMENT);
            boolean needCapitalize = hasCapitalizeProperty
                    && ((boolean) element.<Boolean>getOwnProperty(Html2PdfProperty.CAPITALIZE_ELEMENT));
            if (hasCapitalizeProperty && !needCapitalize) {
                previousProcessed = false;
                continue;
            }
            if (element instanceof Text && (CssConstants.CAPITALIZE.equals(textTransform) || needCapitalize)) {
                String text = ((Text) element).getText();
                if (!previousProcessed && i > 0) {
                    previousLetter = isLastCharAlphabetic(leaves.get(i - 1));
                }
                previousLetter = capitalizeAndReturnIsLastAlphabetic((Text) element, text, previousLetter);
                previousProcessed = true;
            } else {
                previousProcessed = false;
                previousLetter = false;
            }
        }
    }

    private boolean isLastCharAlphabetic(IElement element) {
        if (!(element instanceof Text)) {
            return false;
        }
        String text = ((Text) element).getText();
        return text.length() > 0 && Character.isAlphabetic(text.charAt(text.length() - 1));
    }

    private boolean capitalizeAndReturnIsLastAlphabetic(Text element, String text, boolean previousAlphabetic) {
        StringBuilder sb = new StringBuilder();
        boolean previousLetter = previousAlphabetic;
        for (int i = 0; i < text.length(); i++) {
            if (Character.isLowerCase(text.charAt(i)) && !previousLetter) {
                sb.append(Character.toUpperCase(text.charAt(i)));
                previousLetter = true;
            } else if (Character.isAlphabetic(text.charAt(i))) {
                sb.append(text.charAt(i));
                previousLetter = true;
            } else {
                sb.append(text.charAt(i));
                previousLetter = false;
            }
        }
        element.setText(sb.toString());
        return previousLetter;
    }
}
