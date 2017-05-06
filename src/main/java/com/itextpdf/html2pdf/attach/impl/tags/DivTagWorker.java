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
package com.itextpdf.html2pdf.attach.impl.tags;

import com.itextpdf.html2pdf.attach.ITagWorker;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.attach.impl.layout.form.element.IFormField;
import com.itextpdf.html2pdf.attach.util.WaitingInlineElementsHelper;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.html.node.IElementNode;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.FloatPropertyValue;
import com.itextpdf.layout.property.Property;

import java.util.Map;

public class DivTagWorker implements ITagWorker, IDisplayAware {

    private Div div;
    private WaitingInlineElementsHelper inlineHelper;
    private String display;

    public DivTagWorker(IElementNode element, ProcessorContext context) {
        div = new Div();
        Map<String, String> styles = element.getStyles();
        inlineHelper = new WaitingInlineElementsHelper(styles == null ? null : styles.get(CssConstants.WHITE_SPACE),
                styles == null ? null : styles.get(CssConstants.TEXT_TRANSFORM));
        display = element.getStyles() != null ? element.getStyles().get(CssConstants.DISPLAY) : null;
    }

    @Override
    public void processEnd(IElementNode element, ProcessorContext context) {
        inlineHelper.flushHangingLeaves(div);
    }

    @Override
    public boolean processContent(String content, ProcessorContext context) {
        inlineHelper.add(content);
        return true;
    }

    @Override
    public boolean processTagChild(ITagWorker childTagWorker, ProcessorContext context) {
        boolean processed = false;
        IPropertyContainer element = childTagWorker.getElementResult();
        if (childTagWorker instanceof BrTagWorker) {
            if (inlineHelper.getSanitizedWaitingLeaves().size() == 1 && inlineHelper.getSanitizedWaitingLeaves().get(0) instanceof Image) {
                // TODO This is a workaround for case of single image to set leading to 1
                postProcessInlineGroup();
            }
            inlineHelper.add((ILeafElement) childTagWorker.getElementResult());
            return true;
        } else if (childTagWorker instanceof SpanTagWorker) {
            boolean allChildrenProcessed = true;
            for (IPropertyContainer childElement : ((SpanTagWorker) childTagWorker).getAllElements()) {
                if (childElement instanceof ILeafElement) {
                    inlineHelper.add((ILeafElement) childElement);
                } else if (childElement instanceof IElement) {
                    allChildrenProcessed = addBlockChild((IElement) childElement) && allChildrenProcessed;
                }
            }
            processed = allChildrenProcessed;
        } else if (element instanceof IFormField) {
            if (childTagWorker instanceof IDisplayAware && CssConstants.BLOCK.equals(((IDisplayAware) childTagWorker).getDisplay())) {
                // TODO make IFormField implement IBlockElement and add it directly to div without inline helper.
                // TODO Requires refactoring of AbstractOneLineTextFieldRenderer (calculate baselines properly)
                postProcessInlineGroup();
                inlineHelper.add((ILeafElement) element);
                postProcessInlineGroup();
            } else {
                inlineHelper.add((IFormField) element);
            }
            processed = true;
        } else if (element instanceof AreaBreak) {
            postProcessInlineGroup();
            div.add((AreaBreak) element);
            processed = true;
        } else if (childTagWorker instanceof ImgTagWorker) {
            if (CssConstants.BLOCK.equals(((ImgTagWorker) childTagWorker).getDisplay())) {
                processed = addBlockChild((com.itextpdf.layout.element.IElement) element);
            } else if (childTagWorker.getElementResult() instanceof Image) {
                inlineHelper.add((ILeafElement) childTagWorker.getElementResult());
                processed = true;
            }
        } else if (element instanceof com.itextpdf.layout.element.IElement) {
            processed = addBlockChild((com.itextpdf.layout.element.IElement) element);
        }
        return processed;
    }

    @Override
    public IPropertyContainer getElementResult() {
        return div;
    }

    @Override
    public String getDisplay() {
        return display;
    }

    private boolean addBlockChild(com.itextpdf.layout.element.IElement element) {
        boolean waitingLeavesContainsFloat = false;
        for (ILeafElement waitingLeaf : inlineHelper.getWaitingLeaves()) {
            if (elementIsFloated(waitingLeaf)) {
                waitingLeavesContainsFloat = true;
                break;
            }
        }

        if (elementIsFloated(element)) {
            if (waitingLeavesContainsFloat) {
                postProcessInlineGroup();
            }
        } else {
            postProcessInlineGroup();
        }

        boolean processed = false;
        if (element instanceof IBlockElement) {
            div.add(((IBlockElement) element));
            processed = true;
        } else if (element instanceof Image) {
            div.add((Image) element);
            processed = true;
        }
        return processed;
    }

    private boolean elementIsFloated(IElement element) {
        FloatPropertyValue floatPropertyValue = element.<FloatPropertyValue>getProperty(Property.FLOAT);
        return floatPropertyValue != null && !floatPropertyValue.equals(FloatPropertyValue.NONE);
    }

    private void postProcessInlineGroup() {
        inlineHelper.flushHangingLeaves(div);
    }
}
