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
package com.itextpdf.html2pdf.attach.impl.layout;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.layout.LayoutContext;
import com.itextpdf.layout.layout.LayoutResult;
import com.itextpdf.layout.property.Property;
import com.itextpdf.layout.renderer.AbstractRenderer;
import com.itextpdf.layout.renderer.DocumentRenderer;
import com.itextpdf.layout.renderer.IRenderer;
import com.itextpdf.layout.renderer.TextRenderer;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link TextRenderer} implementation for the page count.
 */
class PageCountRenderer extends TextRenderer {

    /**
     * Instantiates a new page count renderer.
     *
     * @param textElement the text element
     */
    PageCountRenderer(PageCountElement textElement) {
        super(textElement);
    }

    /* (non-Javadoc)
     * @see com.itextpdf.layout.renderer.TextRenderer#layout(com.itextpdf.layout.layout.LayoutContext)
     */
    @Override
    public LayoutResult layout(LayoutContext layoutContext) {
        PageCountType pageCountType = (PageCountType)this.<PageCountType>getProperty(Html2PdfProperty.PAGE_COUNT_TYPE);
        if (pageCountType == PageCountType.CURRENT_PAGE_NUMBER) {
            setText(String.valueOf(layoutContext.getArea().getPageNumber()));
        } else if (pageCountType == PageCountType.TOTAL_PAGE_COUNT) {
            IRenderer rootRenderer = this;
            while (rootRenderer instanceof AbstractRenderer && ((AbstractRenderer) rootRenderer).getParent() != null) {
                rootRenderer = ((AbstractRenderer) rootRenderer).getParent();
            }
            if (rootRenderer instanceof HtmlDocumentRenderer && ((HtmlDocumentRenderer) rootRenderer).getEstimatedNumberOfPages() > 0) {
                setText(String.valueOf(((HtmlDocumentRenderer) rootRenderer).getEstimatedNumberOfPages()));
            } else if (rootRenderer instanceof DocumentRenderer && rootRenderer.getModelElement() instanceof Document) {
                setText(String.valueOf(((Document) rootRenderer.getModelElement()).getPdfDocument().getNumberOfPages()));
            }
        }
        return super.layout(layoutContext);
    }

    /* (non-Javadoc)
     * @see com.itextpdf.layout.renderer.TextRenderer#resolveFonts(java.util.List)
     */
    @Override
    protected boolean resolveFonts(List<IRenderer> addTo) {
        List<IRenderer> dummyList = new ArrayList<>();
        super.resolveFonts(dummyList);
        setProperty(Property.FONT, dummyList.get(0).<Object>getProperty(Property.FONT));
        addTo.add(this);
        return true;
    }

}
