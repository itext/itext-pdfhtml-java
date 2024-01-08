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
package com.itextpdf.html2pdf.css.apply.impl;

import com.itextpdf.html2pdf.attach.ITagWorker;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.attach.impl.tags.ImgTagWorker;
import com.itextpdf.html2pdf.attach.impl.tags.SpanTagWorker;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.css.apply.ICssApplier;
import com.itextpdf.html2pdf.css.apply.util.BackgroundApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.BorderStyleApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.FlexApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.FloatApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.FontStyleApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.HyphenationApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.MarginApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.OpacityApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.OrphansWidowsApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.OutlineApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.OverflowApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.PaddingApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.PageBreakApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.PositionApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.TransformationApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.VerticalAlignmentApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.WidthHeightApplierUtil;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.styledxmlparser.node.IStylesContainer;
import com.itextpdf.styledxmlparser.node.impl.jsoup.node.JsoupElementNode;

import java.util.Map;

/**
 * {@link ICssApplier} implementation for Block elements.
 */
public class BlockCssApplier implements ICssApplier {

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.css.apply.ICssApplier#apply(com.itextpdf.html2pdf.attach.ProcessorContext, com.itextpdf.html2pdf.html.node.IStylesContainer, com.itextpdf.html2pdf.attach.ITagWorker)
     */
    @Override
    public void apply(ProcessorContext context, IStylesContainer stylesContainer, ITagWorker tagWorker) {
        Map<String, String> cssProps = stylesContainer.getStyles();

        IPropertyContainer container = tagWorker.getElementResult();
        if (container != null) {
            WidthHeightApplierUtil.applyWidthHeight(cssProps, context, container);
            BackgroundApplierUtil.applyBackground(cssProps, context, container);
            MarginApplierUtil.applyMargins(cssProps, context, container);
            PaddingApplierUtil.applyPaddings(cssProps, context, container);
            FontStyleApplierUtil.applyFontStyles(cssProps, context, stylesContainer, container);
            BorderStyleApplierUtil.applyBorders(cssProps, context, container);
            HyphenationApplierUtil.applyHyphenation(cssProps, context, stylesContainer, container);
            PositionApplierUtil.applyPosition(cssProps, context, container);
            OpacityApplierUtil.applyOpacity(cssProps, context, container);
            PageBreakApplierUtil.applyPageBreakProperties(cssProps, context, container);
            OverflowApplierUtil.applyOverflow(cssProps, container);
            TransformationApplierUtil.applyTransformation(cssProps, context, container);
            OutlineApplierUtil.applyOutlines(cssProps, context, container);
            OrphansWidowsApplierUtil.applyOrphansAndWidows(cssProps, container);
            VerticalAlignmentApplierUtil.applyVerticalAlignmentForBlocks(cssProps, container, isInlineItem(tagWorker));
            MultiColumnCssApplierUtil.applyMultiCol(cssProps, context, container);
            if (isFlexItem(stylesContainer)) {
                FlexApplierUtil.applyFlexItemProperties(cssProps, context, container);
            } else {
                // Floating doesn't work for flex items.
                // See CSS Flexible Box Layout Module Level 1 W3C Candidate Recommendation, 19 November 2018,
                // 3. Flex Containers: the flex and inline-flex display values
                FloatApplierUtil.applyFloating(cssProps, context, container);
            }
        }
    }

    private static boolean isInlineItem(ITagWorker tagWorker) {
        return tagWorker instanceof SpanTagWorker ||
                tagWorker instanceof ImgTagWorker;
    }

    private static boolean isFlexItem(IStylesContainer stylesContainer) {
        if (stylesContainer instanceof JsoupElementNode
                && ((JsoupElementNode) stylesContainer).parentNode() instanceof JsoupElementNode) {
            final Map<String, String> parentStyles = ((JsoupElementNode) ((JsoupElementNode) stylesContainer)
                    .parentNode()).getStyles();
            return CssConstants.FLEX.equals(parentStyles.get(CssConstants.DISPLAY));
        }
        return false;
    }
}
