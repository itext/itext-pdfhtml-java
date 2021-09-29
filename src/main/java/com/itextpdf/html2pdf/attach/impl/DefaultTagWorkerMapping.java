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
package com.itextpdf.html2pdf.attach.impl;

import com.itextpdf.html2pdf.attach.ITagWorker;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.attach.impl.tags.ABlockTagWorker;
import com.itextpdf.html2pdf.attach.impl.tags.ATagWorker;
import com.itextpdf.html2pdf.attach.impl.tags.AbbrTagWorker;
import com.itextpdf.html2pdf.attach.impl.tags.BodyTagWorker;
import com.itextpdf.html2pdf.attach.impl.tags.BrTagWorker;
import com.itextpdf.html2pdf.attach.impl.tags.ButtonTagWorker;
import com.itextpdf.html2pdf.attach.impl.tags.CaptionTagWorker;
import com.itextpdf.html2pdf.attach.impl.tags.ColTagWorker;
import com.itextpdf.html2pdf.attach.impl.tags.ColgroupTagWorker;
import com.itextpdf.html2pdf.attach.impl.tags.DisplayFlexTagWorker;
import com.itextpdf.html2pdf.attach.impl.tags.DisplayTableRowTagWorker;
import com.itextpdf.html2pdf.attach.impl.tags.DisplayTableTagWorker;
import com.itextpdf.html2pdf.attach.impl.tags.DivTagWorker;
import com.itextpdf.html2pdf.attach.impl.tags.HTagWorker;
import com.itextpdf.html2pdf.attach.impl.tags.HrTagWorker;
import com.itextpdf.html2pdf.attach.impl.tags.HtmlTagWorker;
import com.itextpdf.html2pdf.attach.impl.tags.ImgTagWorker;
import com.itextpdf.html2pdf.attach.impl.tags.InputTagWorker;
import com.itextpdf.html2pdf.attach.impl.tags.LiTagWorker;
import com.itextpdf.html2pdf.attach.impl.tags.LinkTagWorker;
import com.itextpdf.html2pdf.attach.impl.tags.MetaTagWorker;
import com.itextpdf.html2pdf.attach.impl.tags.ObjectTagWorker;
import com.itextpdf.html2pdf.attach.impl.tags.OptGroupTagWorker;
import com.itextpdf.html2pdf.attach.impl.tags.OptionTagWorker;
import com.itextpdf.html2pdf.attach.impl.tags.PTagWorker;
import com.itextpdf.html2pdf.attach.impl.tags.PageCountWorker;
import com.itextpdf.html2pdf.attach.impl.tags.PageMarginBoxWorker;
import com.itextpdf.html2pdf.attach.impl.tags.PlaceholderTagWorker;
import com.itextpdf.html2pdf.attach.impl.tags.PreTagWorker;
import com.itextpdf.html2pdf.attach.impl.tags.SelectTagWorker;
import com.itextpdf.html2pdf.attach.impl.tags.SpanTagWorker;
import com.itextpdf.html2pdf.attach.impl.tags.SvgTagWorker;
import com.itextpdf.html2pdf.attach.impl.tags.TableFooterTagWorker;
import com.itextpdf.html2pdf.attach.impl.tags.TableHeaderTagWorker;
import com.itextpdf.html2pdf.attach.impl.tags.TableTagWorker;
import com.itextpdf.html2pdf.attach.impl.tags.TdTagWorker;
import com.itextpdf.html2pdf.attach.impl.tags.TextAreaTagWorker;
import com.itextpdf.html2pdf.attach.impl.tags.ThTagWorker;
import com.itextpdf.html2pdf.attach.impl.tags.TitleTagWorker;
import com.itextpdf.html2pdf.attach.impl.tags.TrTagWorker;
import com.itextpdf.html2pdf.attach.impl.tags.UlOlTagWorker;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.css.resolve.func.counter.PageCountElementNode;
import com.itextpdf.html2pdf.html.TagConstants;
import com.itextpdf.html2pdf.util.TagProcessorMapping;
import com.itextpdf.styledxmlparser.css.page.PageMarginBoxContextNode;
import com.itextpdf.styledxmlparser.css.pseudo.CssPseudoElementUtil;
import com.itextpdf.styledxmlparser.node.IElementNode;

/**
 * Contains the actual mapping of the {@link DefaultTagWorkerFactory}.
 */
class DefaultTagWorkerMapping {

    /**
     * The worker mapping.
     */
    private static TagProcessorMapping<ITagWorkerCreator> workerMapping;

    static {
        workerMapping = new TagProcessorMapping<>();
        workerMapping.putMapping(TagConstants.A, (lhs, rhs) -> new ATagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.ABBR, (lhs, rhs) -> new AbbrTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.ADDRESS, (lhs, rhs) -> new DivTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.ARTICLE, (lhs, rhs) -> new DivTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.ASIDE, (lhs, rhs) -> new DivTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.B, (lhs, rhs) -> new SpanTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.BDI, (lhs, rhs) -> new SpanTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.BDO, (lhs, rhs) -> new SpanTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.BLOCKQUOTE, (lhs, rhs) -> new DivTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.BODY, (lhs, rhs) -> new BodyTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.BR, (lhs, rhs) -> new BrTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.BUTTON, (lhs, rhs) -> new ButtonTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.CAPTION, (lhs, rhs) -> new CaptionTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.CENTER, (lhs, rhs) -> new DivTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.CITE, (lhs, rhs) -> new SpanTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.CODE, (lhs, rhs) -> new SpanTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.COL, (lhs, rhs) -> new ColTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.COLGROUP, (lhs, rhs) -> new ColgroupTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.DD, (lhs, rhs) -> new LiTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.DEL, (lhs, rhs) -> new SpanTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.DFN, (lhs, rhs) -> new SpanTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.DIV, (lhs, rhs) -> new DivTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.DL, (lhs, rhs) -> new UlOlTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.DT, (lhs, rhs) -> new LiTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.EM, (lhs, rhs) -> new SpanTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.FIELDSET, (lhs, rhs) -> new DivTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.FIGCAPTION, (lhs, rhs) -> new DivTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.FIGURE, (lhs, rhs) -> new DivTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.FONT, (lhs, rhs) -> new SpanTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.FOOTER, (lhs, rhs) -> new DivTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.FORM, (lhs, rhs) -> new DivTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.H1, (lhs, rhs) -> new HTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.H2, (lhs, rhs) -> new HTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.H3, (lhs, rhs) -> new HTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.H4, (lhs, rhs) -> new HTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.H5, (lhs, rhs) -> new HTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.H6, (lhs, rhs) -> new HTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.HEADER, (lhs, rhs) -> new DivTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.HR, (lhs, rhs) -> new HrTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.HTML, (lhs, rhs) -> new HtmlTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.I, (lhs, rhs) -> new SpanTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.IMG, (lhs, rhs) -> new ImgTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.INPUT, (lhs, rhs) -> new InputTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.INS, (lhs, rhs) -> new SpanTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.KBD, (lhs, rhs) -> new SpanTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.LABEL, (lhs, rhs) -> new SpanTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.LEGEND, (lhs, rhs) -> new DivTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.LI, (lhs, rhs) -> new LiTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.LINK, (lhs, rhs) -> new LinkTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.MAIN, (lhs, rhs) -> new DivTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.MARK, (lhs, rhs) -> new SpanTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.META, (lhs, rhs) -> new MetaTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.NAV, (lhs, rhs) -> new DivTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.OBJECT, (lhs, rhs) -> new ObjectTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.OL, (lhs, rhs) -> new UlOlTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.OPTGROUP, (lhs, rhs) -> new OptGroupTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.OPTION, (lhs, rhs) -> new OptionTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.P, (lhs, rhs) -> new PTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.PRE, (lhs, rhs) -> new PreTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.Q, (lhs, rhs) -> new SpanTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.S, (lhs, rhs) -> new SpanTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.SAMP, (lhs, rhs) -> new SpanTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.SECTION, (lhs, rhs) -> new DivTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.SELECT, (lhs, rhs) -> new SelectTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.SMALL, (lhs, rhs) -> new SpanTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.SPAN, (lhs, rhs) -> new SpanTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.STRIKE, (lhs, rhs) -> new SpanTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.STRONG, (lhs, rhs) -> new SpanTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.SUB, (lhs, rhs) -> new SpanTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.SUP, (lhs, rhs) -> new SpanTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.SVG, (lhs, rhs) -> new SvgTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.TABLE, (lhs, rhs) -> new TableTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.TD, (lhs, rhs) -> new TdTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.TEXTAREA, (lhs, rhs) -> new TextAreaTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.TFOOT, (lhs, rhs) -> new TableFooterTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.TH, (lhs, rhs) -> new ThTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.THEAD, (lhs, rhs) -> new TableHeaderTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.TIME, (lhs, rhs) -> new SpanTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.TITLE, (lhs, rhs) -> new TitleTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.TR, (lhs, rhs) -> new TrTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.TT, (lhs, rhs) -> new SpanTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.U, (lhs, rhs) -> new SpanTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.UL, (lhs, rhs) -> new UlOlTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.VAR, (lhs, rhs) -> new SpanTagWorker(lhs, rhs));

        String placeholderPseudoElemName = CssPseudoElementUtil.createPseudoElementTagName(CssConstants.PLACEHOLDER);
        workerMapping.putMapping(placeholderPseudoElemName, (lhs, rhs) -> new PlaceholderTagWorker(lhs, rhs));

        workerMapping.putMapping(TagConstants.UL, CssConstants.INLINE, (lhs, rhs) -> new SpanTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.LI, CssConstants.INLINE, (lhs, rhs) -> new SpanTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.LI, CssConstants.INLINE_BLOCK, (lhs, rhs) -> new DivTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.DD, CssConstants.INLINE, (lhs, rhs) -> new SpanTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.DT, CssConstants.INLINE, (lhs, rhs) -> new SpanTagWorker(lhs, rhs));

        workerMapping.putMapping(TagConstants.SPAN, CssConstants.BLOCK, (lhs, rhs) -> new DivTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.SPAN, CssConstants.INLINE_BLOCK,
                (lhs, rhs) -> new DivTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.A, CssConstants.BLOCK, (lhs, rhs) -> new ABlockTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.A, CssConstants.INLINE_BLOCK,
                (lhs, rhs) -> new ABlockTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.A, CssConstants.TABLE_CELL, (lhs, rhs) -> new ABlockTagWorker(lhs, rhs));

        workerMapping.putMapping(TagConstants.LABEL, CssConstants.BLOCK, (lhs, rhs) -> new DivTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.LABEL, CssConstants.INLINE_BLOCK,
                (lhs, rhs) -> new DivTagWorker(lhs, rhs));

        workerMapping.putMapping(TagConstants.DIV, CssConstants.TABLE,
                (lhs, rhs) -> new DisplayTableTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.DIV, CssConstants.TABLE_ROW,
                (lhs, rhs) -> new DisplayTableRowTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.DIV, CssConstants.INLINE, (lhs, rhs) -> new SpanTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.DIV, CssConstants.INLINE_TABLE,
                (lhs, rhs) -> new DisplayTableTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.DIV, CssConstants.TABLE_CELL, (lhs, rhs) -> new TdTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.DIV, CssConstants.FLEX,
                (lhs, rhs) -> new DisplayFlexTagWorker(lhs, rhs));
        workerMapping.putMapping(TagConstants.SPAN, CssConstants.FLEX,
                (lhs, rhs) -> new DisplayFlexTagWorker(lhs, rhs));

        // pseudo elements mapping
        String beforePseudoElemName = CssPseudoElementUtil.createPseudoElementTagName(CssConstants.BEFORE);
        String afterPseudoElemName = CssPseudoElementUtil.createPseudoElementTagName(CssConstants.AFTER);
        workerMapping.putMapping(beforePseudoElemName, (lhs, rhs) -> new SpanTagWorker(lhs, rhs));
        workerMapping.putMapping(afterPseudoElemName, (lhs, rhs) -> new SpanTagWorker(lhs, rhs));
        workerMapping.putMapping(beforePseudoElemName, CssConstants.INLINE_BLOCK,
                (lhs, rhs) -> new DivTagWorker(lhs, rhs));
        workerMapping.putMapping(afterPseudoElemName, CssConstants.INLINE_BLOCK,
                (lhs, rhs) -> new DivTagWorker(lhs, rhs));
        workerMapping.putMapping(beforePseudoElemName, CssConstants.BLOCK, (lhs, rhs) -> new DivTagWorker(lhs, rhs));
        workerMapping.putMapping(afterPseudoElemName, CssConstants.BLOCK, (lhs, rhs) -> new DivTagWorker(lhs, rhs));
        // For now behaving like display:block in display:table case is sufficient
        workerMapping.putMapping(beforePseudoElemName, CssConstants.TABLE, (lhs, rhs) -> new DivTagWorker(lhs, rhs));
        workerMapping.putMapping(afterPseudoElemName, CssConstants.TABLE, (lhs, rhs) -> new DivTagWorker(lhs, rhs));
        workerMapping.putMapping(CssPseudoElementUtil.createPseudoElementTagName(TagConstants.IMG),
                (lhs, rhs) -> new ImgTagWorker(lhs, rhs));
        workerMapping.putMapping(CssPseudoElementUtil.createPseudoElementTagName(TagConstants.DIV),
                (lhs, rhs) -> new DivTagWorker(lhs, rhs));

        // custom elements mapping, implementation-specific
        workerMapping.putMapping(PageCountElementNode.PAGE_COUNTER_TAG,
                (lhs, rhs) -> new PageCountWorker(lhs, rhs));
        workerMapping.putMapping(PageMarginBoxContextNode.PAGE_MARGIN_BOX_TAG,
                (lhs, rhs) -> new PageMarginBoxWorker(lhs, rhs));
    }

    /**
     * Gets the default tag worker mapping.
     *
     * @return the default mapping
     */
    TagProcessorMapping<ITagWorkerCreator> getDefaultTagWorkerMapping() {
        return workerMapping;
    }

    /**
     * Instantiates a new {@link DefaultTagWorkerMapping} instance.
     */
    DefaultTagWorkerMapping() {
    }

    /**
     * Represents a function, which accepts {@link IElementNode} and {@link ProcessorContext},
     * and creates {@link ITagWorker} instance based on these parameters.
     */
    @FunctionalInterface
    public interface ITagWorkerCreator {
        /**
         * Creates an {@link ITagWorker} instance.
         * @param elementNode tag worker element node.
         * @param processorContext processor context.
         * @return {@link ITagWorker} instance.
         */
        ITagWorker create(IElementNode elementNode, ProcessorContext processorContext);
    }

}
