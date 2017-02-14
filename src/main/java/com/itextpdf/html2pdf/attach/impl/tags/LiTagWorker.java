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
import com.itextpdf.html2pdf.attach.util.WaitingInlineElementsHelper;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.css.util.CssUtils;
import com.itextpdf.html2pdf.html.node.IElementNode;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.ListSymbolPosition;
import com.itextpdf.layout.property.Property;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class LiTagWorker implements ITagWorker {

    protected ListItem listItem;
    protected List list;
    private WaitingInlineElementsHelper inlineHelper;

    public LiTagWorker(IElementNode element, ProcessorContext context) {
        listItem = new ListItem();
        if (!(context.getState().top() instanceof UlOlTagWorker)) {
            listItem.setProperty(Property.LIST_SYMBOL_POSITION, ListSymbolPosition.INSIDE);
            Text symbol = new Text(String.valueOf(((char)108))).setFont(createZapfDingBatsSafe());
            symbol.setTextRise(1.5f);
            symbol.setFontSize(4.5f);
            float em = CssUtils.parseAbsoluteLength(element.getStyles().get(CssConstants.FONT_SIZE));
            listItem.setProperty(Property.LIST_SYMBOL_INDENT, 1.5f * em);
            listItem.setProperty(Property.LIST_SYMBOL, symbol);

            list = new List();
            list.add(listItem);
        }
        inlineHelper = new WaitingInlineElementsHelper(element.getStyles().get(CssConstants.WHITE_SPACE), element.getStyles().get(CssConstants.TEXT_TRANSFORM));
    }

    @Override
    public void processEnd(IElementNode element, ProcessorContext context) {
        inlineHelper.flushHangingLeaves(listItem);
    }

    @Override
    public boolean processContent(String content, ProcessorContext context) {
        inlineHelper.add(content);
        return true;
    }

    @Override
    public boolean processTagChild(ITagWorker childTagWorker, ProcessorContext context) {
        if (childTagWorker instanceof SpanTagWorker) {
            boolean allChildrenProcessed = true;
            for (IPropertyContainer propertyContainer : ((SpanTagWorker) childTagWorker).getAllElements()) {
                if (propertyContainer instanceof ILeafElement) {
                    inlineHelper.add((ILeafElement) propertyContainer);
                } else {
                    allChildrenProcessed = processChild(propertyContainer) && allChildrenProcessed;
                }
            }
            return allChildrenProcessed;
        } else {
            return processChild(childTagWorker.getElementResult());
        }
    }

    @Override
    public IPropertyContainer getElementResult() {
        return list != null ? (IPropertyContainer) list : listItem;
    }

    private boolean processChild(IPropertyContainer propertyContainer) {
        inlineHelper.flushHangingLeaves(listItem);
        if (propertyContainer instanceof Image) {
            listItem.add((Image) propertyContainer);
            return true;
        } else if (propertyContainer instanceof IBlockElement) {
            listItem.add((IBlockElement) propertyContainer);
            return true;
        }
        return false;
    }

    private static PdfFont createZapfDingBatsSafe() {
        try {
            return PdfFontFactory.createFont(FontConstants.ZAPFDINGBATS);
        } catch (IOException exc) {
            Logger logger = LoggerFactory.getLogger(LiTagWorker.class);
            logger.error("Unable to create ZapfDingBats font", exc);
            return null;
        }
    }
}
