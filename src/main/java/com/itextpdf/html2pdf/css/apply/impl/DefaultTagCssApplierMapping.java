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

import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.css.apply.ICssApplier;
import com.itextpdf.html2pdf.css.resolve.func.counter.PageCountElementNode;
import com.itextpdf.html2pdf.html.TagConstants;
import com.itextpdf.html2pdf.util.TagProcessorMapping;
import com.itextpdf.styledxmlparser.css.page.PageMarginBoxContextNode;
import com.itextpdf.styledxmlparser.css.pseudo.CssPseudoElementUtil;

/**
 * Class that contains the default mapping between CSS keys and CSS appliers.
 */
class DefaultTagCssApplierMapping {

    /**
     * Creates a new {@link DefaultTagCssApplierMapping} instance.
     */
    DefaultTagCssApplierMapping() {
    }

    /** The default mapping. */
    private static TagProcessorMapping<ICssApplierCreator> mapping;
    static {
        mapping = new TagProcessorMapping<>();

        mapping.putMapping(TagConstants.A, () -> new SpanTagCssApplier());
        mapping.putMapping(TagConstants.ABBR, () -> new SpanTagCssApplier());
        mapping.putMapping(TagConstants.ADDRESS, () -> new BlockCssApplier());
        mapping.putMapping(TagConstants.ARTICLE, () -> new BlockCssApplier());
        mapping.putMapping(TagConstants.ASIDE, () -> new BlockCssApplier());
        mapping.putMapping(TagConstants.B, () -> new SpanTagCssApplier());
        mapping.putMapping(TagConstants.BDI, () -> new SpanTagCssApplier());
        mapping.putMapping(TagConstants.BDO, () -> new SpanTagCssApplier());
        mapping.putMapping(TagConstants.BLOCKQUOTE, () -> new BlockCssApplier());
        mapping.putMapping(TagConstants.BODY, () -> new BodyTagCssApplier());
        mapping.putMapping(TagConstants.BUTTON, () -> new BlockCssApplier());
        mapping.putMapping(TagConstants.CAPTION, () -> new CaptionCssApplier());
        mapping.putMapping(TagConstants.CENTER, () -> new BlockCssApplier());
        mapping.putMapping(TagConstants.CITE, () -> new SpanTagCssApplier());
        mapping.putMapping(TagConstants.CODE, () -> new SpanTagCssApplier());
        mapping.putMapping(TagConstants.COL, () -> new ColTagCssApplier());
        mapping.putMapping(TagConstants.COLGROUP, () -> new ColgroupTagCssApplier());
        mapping.putMapping(TagConstants.DD, () -> new BlockCssApplier());
        mapping.putMapping(TagConstants.DEL, () -> new SpanTagCssApplier());
        mapping.putMapping(TagConstants.DFN, () -> new SpanTagCssApplier());
        mapping.putMapping(TagConstants.DIV, () -> new BlockCssApplier());
        mapping.putMapping(TagConstants.DL, () -> new DlTagCssApplier());
        mapping.putMapping(TagConstants.DT, () -> new BlockCssApplier());
        mapping.putMapping(TagConstants.EM, () -> new SpanTagCssApplier());
        mapping.putMapping(TagConstants.FIELDSET, () -> new BlockCssApplier());
        mapping.putMapping(TagConstants.FIGCAPTION, () -> new BlockCssApplier());
        mapping.putMapping(TagConstants.FIGURE, () -> new BlockCssApplier());
        mapping.putMapping(TagConstants.FONT, () -> new SpanTagCssApplier());
        mapping.putMapping(TagConstants.FOOTER, () -> new BlockCssApplier());
        mapping.putMapping(TagConstants.FORM, () -> new BlockCssApplier());
        mapping.putMapping(TagConstants.H1, () -> new BlockCssApplier());
        mapping.putMapping(TagConstants.H2, () -> new BlockCssApplier());
        mapping.putMapping(TagConstants.H3, () -> new BlockCssApplier());
        mapping.putMapping(TagConstants.H4, () -> new BlockCssApplier());
        mapping.putMapping(TagConstants.H5, () -> new BlockCssApplier());
        mapping.putMapping(TagConstants.H6, () -> new BlockCssApplier());
        mapping.putMapping(TagConstants.HEADER, () -> new BlockCssApplier());
        mapping.putMapping(TagConstants.HR, () -> new HrTagCssApplier());
        mapping.putMapping(TagConstants.HTML, () -> new HtmlTagCssApplier());
        mapping.putMapping(TagConstants.I, () -> new SpanTagCssApplier());
        mapping.putMapping(TagConstants.IMG, () -> new BlockCssApplier());
        mapping.putMapping(TagConstants.INPUT, () -> new BlockCssApplier());
        mapping.putMapping(TagConstants.INS, () -> new SpanTagCssApplier());
        mapping.putMapping(TagConstants.KBD, () -> new SpanTagCssApplier());
        mapping.putMapping(TagConstants.LABEL, () -> new SpanTagCssApplier());
        mapping.putMapping(TagConstants.LEGEND, () -> new BlockCssApplier());
        mapping.putMapping(TagConstants.LI, () -> new LiTagCssApplier());
        mapping.putMapping(TagConstants.MAIN, () -> new BlockCssApplier());
        mapping.putMapping(TagConstants.MARK, () -> new SpanTagCssApplier());
        mapping.putMapping(TagConstants.NAV, () -> new BlockCssApplier());
        mapping.putMapping(TagConstants.OBJECT, () -> new BlockCssApplier());
        mapping.putMapping(TagConstants.OL, () -> new UlOlTagCssApplier());
        mapping.putMapping(TagConstants.OPTGROUP, () -> new BlockCssApplier());
        mapping.putMapping(TagConstants.OPTION, () -> new BlockCssApplier());
        mapping.putMapping(TagConstants.P, () -> new BlockCssApplier());
        mapping.putMapping(TagConstants.PRE, () -> new BlockCssApplier());
        mapping.putMapping(TagConstants.Q, () -> new SpanTagCssApplier());
        mapping.putMapping(TagConstants.S, () -> new SpanTagCssApplier());
        mapping.putMapping(TagConstants.SAMP, () -> new SpanTagCssApplier());
        mapping.putMapping(TagConstants.SECTION, () -> new BlockCssApplier());
        mapping.putMapping(TagConstants.SELECT, () -> new BlockCssApplier());
        mapping.putMapping(TagConstants.SMALL, () -> new SpanTagCssApplier());
        mapping.putMapping(TagConstants.SPAN, () -> new SpanTagCssApplier());
        mapping.putMapping(TagConstants.STRIKE, () -> new SpanTagCssApplier());
        mapping.putMapping(TagConstants.STRONG, () -> new SpanTagCssApplier());
        mapping.putMapping(TagConstants.SUB, () -> new SpanTagCssApplier());
        mapping.putMapping(TagConstants.SUP, () -> new SpanTagCssApplier());
        mapping.putMapping(TagConstants.SVG, () -> new BlockCssApplier());
        mapping.putMapping(TagConstants.TABLE, () -> new TableTagCssApplier());
        mapping.putMapping(TagConstants.TEXTAREA, () -> new BlockCssApplier());
        mapping.putMapping(TagConstants.TD, () -> new TdTagCssApplier());
        mapping.putMapping(TagConstants.TFOOT, () -> new BlockCssApplier());
        mapping.putMapping(TagConstants.TH, () -> new TdTagCssApplier());
        mapping.putMapping(TagConstants.THEAD, () -> new BlockCssApplier());
        mapping.putMapping(TagConstants.TIME, () -> new SpanTagCssApplier());
        mapping.putMapping(TagConstants.TR, () -> new TrTagCssApplier());
        mapping.putMapping(TagConstants.TT, () -> new SpanTagCssApplier());
        mapping.putMapping(TagConstants.U, () -> new SpanTagCssApplier());
        mapping.putMapping(TagConstants.UL, () -> new UlOlTagCssApplier());
        mapping.putMapping(TagConstants.VAR, () -> new SpanTagCssApplier());

        String placeholderPseudoElemName = CssPseudoElementUtil.createPseudoElementTagName(CssConstants.PLACEHOLDER);
        mapping.putMapping(placeholderPseudoElemName, () -> new PlaceholderCssApplier());

        mapping.putMapping(TagConstants.DIV, CssConstants.INLINE, () -> new SpanTagCssApplier());
        mapping.putMapping(TagConstants.UL, CssConstants.INLINE, () -> new SpanTagCssApplier());
        mapping.putMapping(TagConstants.LI, CssConstants.INLINE, () -> new SpanTagCssApplier());
        mapping.putMapping(TagConstants.LI, CssConstants.INLINE_BLOCK, () -> new BlockCssApplier());
        mapping.putMapping(TagConstants.LI, CssConstants.BLOCK, () -> new BlockCssApplier());
        mapping.putMapping(TagConstants.DD, CssConstants.INLINE, () -> new SpanTagCssApplier());
        mapping.putMapping(TagConstants.DT, CssConstants.INLINE, () -> new SpanTagCssApplier());

        mapping.putMapping(TagConstants.SPAN, CssConstants.BLOCK, () -> new BlockCssApplier());
        mapping.putMapping(TagConstants.SPAN, CssConstants.INLINE_BLOCK, () -> new BlockCssApplier());
        mapping.putMapping(TagConstants.A, CssConstants.INLINE_BLOCK, () -> new BlockCssApplier());
        mapping.putMapping(TagConstants.A, CssConstants.BLOCK, () -> new BlockCssApplier());
        mapping.putMapping(TagConstants.A, CssConstants.TABLE_CELL, () -> new BlockCssApplier());

        mapping.putMapping(TagConstants.LABEL, CssConstants.BLOCK, () -> new BlockCssApplier());
        mapping.putMapping(TagConstants.LABEL, CssConstants.INLINE_BLOCK, () -> new BlockCssApplier());

        mapping.putMapping(TagConstants.DIV, CssConstants.TABLE, () -> new TableTagCssApplier());
        mapping.putMapping(TagConstants.DIV, CssConstants.TABLE_CELL, () -> new TdTagCssApplier());
        mapping.putMapping(TagConstants.DIV, CssConstants.TABLE_ROW, () -> new DisplayTableRowTagCssApplier());
        mapping.putMapping(TagConstants.DIV, CssConstants.FLEX, () -> new DisplayFlexTagCssApplier());
        mapping.putMapping(TagConstants.SPAN, CssConstants.FLEX, () -> new DisplayFlexTagCssApplier());

        // pseudo elements mapping
        String beforePseudoElemName = CssPseudoElementUtil.createPseudoElementTagName(CssConstants.BEFORE);
        String afterPseudoElemName = CssPseudoElementUtil.createPseudoElementTagName(CssConstants.AFTER);
        mapping.putMapping(beforePseudoElemName, () -> new SpanTagCssApplier());
        mapping.putMapping(afterPseudoElemName, () -> new SpanTagCssApplier());
        mapping.putMapping(beforePseudoElemName, CssConstants.INLINE_BLOCK, () -> new BlockCssApplier());
        mapping.putMapping(afterPseudoElemName, CssConstants.INLINE_BLOCK, () -> new BlockCssApplier());
        mapping.putMapping(beforePseudoElemName, CssConstants.BLOCK, () -> new BlockCssApplier());
        mapping.putMapping(afterPseudoElemName, CssConstants.BLOCK, () -> new BlockCssApplier());
        // For now behaving like display:block in display:table case is sufficient
        mapping.putMapping(beforePseudoElemName, CssConstants.TABLE, () -> new BlockCssApplier());
        mapping.putMapping(afterPseudoElemName, CssConstants.TABLE, () -> new BlockCssApplier());
        mapping.putMapping(CssPseudoElementUtil.createPseudoElementTagName(TagConstants.IMG),
                () -> new BlockCssApplier());
        mapping.putMapping(CssPseudoElementUtil.createPseudoElementTagName(TagConstants.DIV),
                () -> new CssContentLinearGradientApplier());


        // custom elements mapping, implementation-specific
        mapping.putMapping(PageCountElementNode.PAGE_COUNTER_TAG, () -> new SpanTagCssApplier());
        mapping.putMapping(PageMarginBoxContextNode.PAGE_MARGIN_BOX_TAG, () -> new PageMarginBoxCssApplier());

    }

    /**
     * Gets the default CSS applier mapping.
     *
     * @return the default CSS applier mapping
     */
    TagProcessorMapping<ICssApplierCreator> getDefaultCssApplierMapping() {
        return mapping;
    }

    /**
     * Represents a function, which creates {@link ICssApplier} instance.
     */
    @FunctionalInterface
    public interface ICssApplierCreator {
        /**
         * Creates an {@link ICssApplier} instance.
         * @return {@link ICssApplier} instance.
         */
        ICssApplier create();
    }
}
