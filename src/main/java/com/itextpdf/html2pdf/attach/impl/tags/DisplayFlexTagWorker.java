/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2021 iText Group NV
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
package com.itextpdf.html2pdf.attach.impl.tags;

import com.itextpdf.html2pdf.attach.ITagWorker;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.attach.util.AccessiblePropHelper;
import com.itextpdf.html2pdf.attach.util.WaitingInlineElementsHelper;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.FlexContainer;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.element.ILeafElement;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Text;
import com.itextpdf.styledxmlparser.node.IElementNode;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * {@link ITagWorker} implementation for elements with {@code display: flex}.
 */
public class DisplayFlexTagWorker implements ITagWorker, IDisplayAware {

    private static final Pattern ANY_SYMBOL_PATTERN;

    private final FlexContainer flexContainer;

    private final WaitingInlineElementsHelper inlineHelper;

    static {
        ANY_SYMBOL_PATTERN = Pattern.compile("\\S+");
    }

    /**
     * Creates instance of {@link DisplayFlexTagWorker}.
     *
     * @param element the element with defined styles
     * @param context the context of the converter processor
     */
    public DisplayFlexTagWorker(IElementNode element, ProcessorContext context) {
        flexContainer = new FlexContainer();
        final Map<String, String> styles = element.getStyles();
        inlineHelper = new WaitingInlineElementsHelper(styles == null ? null : styles.get(CssConstants.WHITE_SPACE),
                styles == null ? null : styles.get(CssConstants.TEXT_TRANSFORM));
        AccessiblePropHelper.trySetLangAttribute(flexContainer, element);
    }

    @Override
    public void processEnd(IElementNode element, ProcessorContext context) {
        if (inlineHelperContainsText()) {
            addInlineWaitingLeavesToFlexContainer();
        }
    }

    @Override
    public boolean processContent(String content, ProcessorContext context) {
        if (ANY_SYMBOL_PATTERN.matcher(content).find()) {
            inlineHelper.add(content);
        }
        return true;
    }

    @Override
    public IPropertyContainer getElementResult() {
        return flexContainer;
    }

    @Override
    public String getDisplay() {
        return CssConstants.FLEX;
    }

    @Override
    public boolean processTagChild(ITagWorker childTagWorker, ProcessorContext context) {
        final IPropertyContainer element = childTagWorker.getElementResult();
        if (childTagWorker instanceof BrTagWorker) {
            inlineHelper.add((ILeafElement) element);
        } else {
            if (inlineHelperContainsText()) {
                addInlineWaitingLeavesToFlexContainer();
            }
            if (element instanceof IBlockElement) {
                flexContainer.add((IBlockElement) element);
            } else if (element instanceof Image) {
                flexContainer.add((Image) element);
            } else if (element instanceof AreaBreak) {
                flexContainer.add((AreaBreak) element);
            } else {
                return false;
            }
        }
        return true;
    }

    private void addInlineWaitingLeavesToFlexContainer() {
        inlineHelper.flushHangingLeaves(flexContainer);
        inlineHelper.clearWaitingLeaves();
    }

    private boolean inlineHelperContainsText() {
        boolean containsText = false;
        for (final IElement element : inlineHelper.getWaitingLeaves()) {
            if (element instanceof Text && ANY_SYMBOL_PATTERN.matcher(((Text) element).getText()).find()) {
                containsText = true;
            }
        }
        return containsText;
    }
}
