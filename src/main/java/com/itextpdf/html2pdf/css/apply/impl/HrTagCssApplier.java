/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2023 Apryse Group NV
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
package com.itextpdf.html2pdf.css.apply.impl;

import com.itextpdf.html2pdf.attach.ITagWorker;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.css.apply.ICssApplier;
import com.itextpdf.styledxmlparser.node.IStylesContainer;

import java.util.HashMap;
import java.util.Map;

/**
 * {@link ICssApplier} implementation for hr elements.
 */
public class HrTagCssApplier extends BlockCssApplier {

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.css.apply.ICssApplier#apply(com.itextpdf.html2pdf.attach.ProcessorContext, com.itextpdf.html2pdf.html.node.IStylesContainer, com.itextpdf.html2pdf.attach.ITagWorker)
     */
    @Override
    public void apply(ProcessorContext context, IStylesContainer stylesContainer, ITagWorker tagWorker) {
        Map<String, String> originalStyles = stylesContainer.getStyles();
        Map<String, String> styles = new HashMap<>(originalStyles);
        // In general, if border color is not specified, it should take the value from color CSS property.
        // However, <hr> tag is an exception from this rule and color property does not affect the border color.
        // This is a workaround to solve the problem of color property being taken into account for <hr> tag.
        // Please note that this problem might as well be solved at BorderStyleApplierUtil.
        styles.remove(CssConstants.COLOR);
        stylesContainer.setStyles(styles);
        super.apply(context, stylesContainer, tagWorker);
        stylesContainer.setStyles(originalStyles);
    }
}
