/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2017 iText Group NV
    Authors: Bruno Lowagie, Paulo Soares, et al.

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
package com.itextpdf.html2pdf.attach.util;

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
        if (!keepLineBreaks && collapseSpaces) {
            text = collapseConsecutiveSpaces(text);
        } else if (keepLineBreaks && collapseSpaces) {
            StringBuilder sb = new StringBuilder(text.length());
            for (int i = 0; i < text.length(); i++) {
                if (TrimUtil.isNonLineBreakSpace(text.charAt(i))) {
                    if (sb.length() == 0 || sb.charAt(sb.length() - 1) != ' ') {
                        sb.append(" ");
                    }
                } else {
                    sb.append(text.charAt(i));
                }
            }
            text = sb.toString();
        } else { // false == collapseSpaces
            // prohibit trimming first and last spaces
            StringBuilder sb = new StringBuilder(text.length());
            sb.append('\u200d');
            for (int i = 0; i < text.length(); i++) {
                sb.append(text.charAt(i));
                if ('\n' == text.charAt(i) ||
                        ('\r' == text.charAt(i) && i + 1 < text.length() && '\n' != text.charAt(i + 1))) {
                    sb.append('\u200d');
                }
            }
            if ('\u200d' == sb.charAt(sb.length() - 1)) {
                sb.delete(sb.length() - 1, sb.length());
            }
            text = sb.toString();
        }

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
            map.put(CssConstants.OVERFLOW, CssConstants.VISIBLE);
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
        if (CssConstants.CAPITALIZE.equals(textTransform)) {
            capitalize(waitingLeaves);
        }

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
                // TODO this might be avoided in future if we will come up with removing of completely empty
                // (both in terms of content and possible properties like background and borders) tags from
                // logical structure of resultant PDF documents
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
    private static void capitalize(List<IElement> leaves) {
        boolean previousLetter = false;
        for (IElement element : leaves) {
            if (element instanceof Text) {
                String text = ((Text) element).getText();
                StringBuilder sb = new StringBuilder();
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
                ((Text) element).setText(sb.toString());
            } else {
                previousLetter = false;
            }
        }
    }

    /**
     * Collapses consecutive spaces.
     *
     * @param s a string
     * @return the string with the consecutive spaces collapsed
     */
    private static String collapseConsecutiveSpaces(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (TrimUtil.isNonEmSpace(s.charAt(i))) {
                if (sb.length() == 0 || !TrimUtil.isNonEmSpace(sb.charAt(sb.length() - 1))) {
                    sb.append(" ");
                }
            } else {
                sb.append(s.charAt(i));
            }
        }
        return sb.toString();
    }

}
