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
package com.itextpdf.html2pdf.attach.impl.tags;

import com.itextpdf.html2pdf.attach.ITagWorker;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.attach.util.AccessiblePropHelper;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.element.Text;
import com.itextpdf.styledxmlparser.util.FontFamilySplitterUtil;
import com.itextpdf.styledxmlparser.node.IElementNode;
import java.util.List;

/**
 * TagWorker class for the {@code br} element.
 */
public class BrTagWorker implements ITagWorker {

    /** A new line Text element. */
    private Text newLine = new Text("\n");

    /**
     * Creates a new {@link BrTagWorker} instance.
     *
     * @param element the element
     * @param context the context
     */
    public BrTagWorker(IElementNode element, ProcessorContext context) {
        //There is no mappings for BR element in DefaultTagCssApplierMapping,
        // because only font-family should be applied to <br /> element.
        String fontFamily = element.getStyles().get(CssConstants.FONT_FAMILY);
        // TODO DEVSIX-2534
        List<String> splitFontFamily = FontFamilySplitterUtil.splitFontFamily(fontFamily);
        newLine.setFontFamily(splitFontFamily.toArray(new String[splitFontFamily.size()]));

        AccessiblePropHelper.trySetLangAttribute(newLine, element);
    }

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.attach.ITagWorker#processEnd(com.itextpdf.html2pdf.html.node.IElementNode, com.itextpdf.html2pdf.attach.ProcessorContext)
     */
    @Override
    public void processEnd(IElementNode element, ProcessorContext context) {
    }

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.attach.ITagWorker#processContent(java.lang.String, com.itextpdf.html2pdf.attach.ProcessorContext)
     */
    @Override
    public boolean processContent(String content, ProcessorContext context) {
        return false;
    }

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.attach.ITagWorker#processTagChild(com.itextpdf.html2pdf.attach.ITagWorker, com.itextpdf.html2pdf.attach.ProcessorContext)
     */
    @Override
    public boolean processTagChild(ITagWorker childTagWorker, ProcessorContext context) {
        return false;
    }

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.attach.ITagWorker#getElementResult()
     */
    @Override
    public IPropertyContainer getElementResult() {
        return newLine;
    }
}
