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
package com.itextpdf.html2pdf.attach.impl;

import com.itextpdf.commons.actions.EventManager;
import com.itextpdf.commons.actions.sequence.AbstractIdentifiableElement;
import com.itextpdf.commons.actions.sequence.SequenceId;
import com.itextpdf.commons.actions.sequence.SequenceIdManager;
import com.itextpdf.commons.utils.MessageFormatUtil;
import com.itextpdf.forms.form.element.IPlaceholderable;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.ProcessorContextCreator;
import com.itextpdf.html2pdf.actions.events.PdfHtmlProductEvent;
import com.itextpdf.html2pdf.attach.IHtmlProcessor;
import com.itextpdf.html2pdf.attach.ITagWorker;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.attach.impl.layout.HtmlDocument;
import com.itextpdf.html2pdf.attach.impl.layout.HtmlDocumentRenderer;
import com.itextpdf.html2pdf.attach.impl.layout.RunningElementContainer;
import com.itextpdf.html2pdf.attach.impl.tags.HtmlTagWorker;
import com.itextpdf.html2pdf.attach.impl.tags.RunningElementTagWorker;
import com.itextpdf.html2pdf.attach.util.LinkHelper;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.css.apply.ICssApplier;
import com.itextpdf.html2pdf.css.apply.util.CounterProcessorUtil;
import com.itextpdf.html2pdf.css.apply.util.PageBreakApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.TextDecorationApplierUtil;
import com.itextpdf.html2pdf.css.resolve.DefaultCssResolver;
import com.itextpdf.html2pdf.exceptions.Html2PdfException;
import com.itextpdf.html2pdf.html.TagConstants;
import com.itextpdf.html2pdf.logs.Html2PdfLogMessageConstant;
import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.IAbstractElement;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.font.FontInfo;
import com.itextpdf.layout.font.Range;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.layout.properties.RenderingMode;
import com.itextpdf.layout.renderer.DocumentRenderer;
import com.itextpdf.layout.renderer.MetaInfoContainer;
import com.itextpdf.styledxmlparser.css.CssDeclaration;
import com.itextpdf.styledxmlparser.css.CssFontFaceRule;
import com.itextpdf.styledxmlparser.css.ICssResolver;
import com.itextpdf.styledxmlparser.css.font.CssFontFace;
import com.itextpdf.styledxmlparser.css.pseudo.CssPseudoElementNode;
import com.itextpdf.styledxmlparser.css.pseudo.CssPseudoElementUtil;
import com.itextpdf.styledxmlparser.node.IElementNode;
import com.itextpdf.styledxmlparser.node.INode;
import com.itextpdf.styledxmlparser.node.ITextNode;
import com.itextpdf.styledxmlparser.util.FontFamilySplitterUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The default implementation to process HTML.
 */
public class DefaultHtmlProcessor implements IHtmlProcessor {

    /**
     * The logger instance.
     */
    private static final Logger logger = LoggerFactory.getLogger(DefaultHtmlProcessor.class);

    /**
     * Set of tags that do not map to any tag worker and that are deliberately excluded from the logging.
     */
    private static final Set<String> ignoredTags = Collections.unmodifiableSet(new HashSet<String>(Arrays.asList(
            TagConstants.HEAD,
            TagConstants.STYLE,
            // <tbody> is not supported via tag workers. Styles will be propagated anyway (most of them, but not all)
            // TODO in scope of DEVSIX-4258 we might want to introduce a tag worker for <tbody> and remove it from here
            TagConstants.TBODY)));

    /**
     * Set of tags to which we do not want to apply CSS to and that are deliberately excluded from the logging
     */
    private static final Set<String> ignoredCssTags = Collections.unmodifiableSet(new HashSet<String>(Arrays.asList(
            TagConstants.BR,
            TagConstants.LINK,
            TagConstants.META,
            TagConstants.TITLE,
            TagConstants.TR)));

    /**
     * Set of tags that might be not processed by some tag workers and that are deliberately excluded from the logging.
     */
    private static final Set<String> ignoredChildTags = Collections.unmodifiableSet(new HashSet<String>(Arrays.asList(
            TagConstants.BODY,
            TagConstants.LINK,
            TagConstants.META,
            TagConstants.SCRIPT,
            TagConstants.TITLE
    )));

    /**
     * The processor context.
     */
    private ProcessorContext context;

    /**
     * A list of parent objects that result from parsing the HTML.
     */
    private List<IPropertyContainer> roots;

    /**
     * The CSS resolver.
     */
    private ICssResolver cssResolver;

    /**
     * Instantiates a new default html processor.
     *
     * @param converterProperties the converter properties
     */
    public DefaultHtmlProcessor(ConverterProperties converterProperties) {
        this.context = ProcessorContextCreator.createProcessorContext(converterProperties);
    }

    /**
     * Sets properties to top-level layout elements converted from HTML.
     * This enables features set by user via HTML converter API and also changes properties defaults
     * to the ones specific to HTML-like behavior.
     * @param cssProperties HTML document-level css properties.
     * @param context processor context specific to the current HTML conversion.
     * @param propertyContainer top-level layout element converted from HTML.
     */
    public static void setConvertedRootElementProperties(Map<String, String> cssProperties, ProcessorContext context, IPropertyContainer propertyContainer) {
        propertyContainer.setProperty(Property.COLLAPSING_MARGINS, true);
        propertyContainer.setProperty(Property.RENDERING_MODE, RenderingMode.HTML_MODE);
        propertyContainer.setProperty(Property.FONT_PROVIDER, context.getFontProvider());
        if (context.getTempFonts() != null) {
            propertyContainer.setProperty(Property.FONT_SET, context.getTempFonts());
        }

        // TODO DEVSIX-2534
        List<String> fontFamilies = FontFamilySplitterUtil.splitFontFamily(cssProperties.get(CssConstants.FONT_FAMILY));
        if (fontFamilies != null && !propertyContainer.hasOwnProperty(Property.FONT)) {
            propertyContainer.setProperty(Property.FONT, fontFamilies.toArray(new String[0]));
        }
    }

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.attach.IHtmlProcessor#processElements(com.itextpdf.html2pdf.html.node.INode)
     */
    @Override
    public List<com.itextpdf.layout.element.IElement> processElements(INode root) {
        final SequenceId sequenceId = new SequenceId();
        EventManager.getInstance().onEvent(PdfHtmlProductEvent.createConvertHtmlEvent(sequenceId,
                context.getMetaInfoContainer().getMetaInfo()));

        context.reset();
        roots = new ArrayList<>();
        cssResolver = new DefaultCssResolver(root, context);
        context.getLinkContext().scanForIds(root);
        addFontFaceFonts();
        IElementNode html = findHtmlNode(root);
        IElementNode body = findBodyNode(root);

        // Force resolve styles to fetch default font size etc
        html.setStyles(cssResolver.resolveStyles(html, context.getCssContext()));

        // visit body
        visit(body);

        Div bodyDiv = (Div) roots.get(0);
        List<com.itextpdf.layout.element.IElement> elements = new ArrayList<>();
        // re-resolve body element styles in order to use them in top-level elements properties setting
        body.setStyles(cssResolver.resolveStyles(body, context.getCssContext()));
        for (IPropertyContainer propertyContainer : bodyDiv.getChildren()) {
            if (propertyContainer instanceof com.itextpdf.layout.element.IElement) {
                setConvertedRootElementProperties(body.getStyles(), context, propertyContainer);
                elements.add((com.itextpdf.layout.element.IElement) propertyContainer);
            }
        }
        cssResolver = null;
        roots = null;
        for (IElement element : elements) {
            updateSequenceId(element, sequenceId);
        }
        return elements;
    }

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.attach.IHtmlProcessor#processDocument(com.itextpdf.html2pdf.html.node.INode, com.itextpdf.kernel.pdf.PdfDocument)
     */
    @Override
    public Document processDocument(INode root, PdfDocument pdfDocument) {
        EventManager.getInstance().onEvent(PdfHtmlProductEvent.createConvertHtmlEvent(
                pdfDocument.getDocumentIdWrapper(), context.getMetaInfoContainer().getMetaInfo()));

        context.reset(pdfDocument);
        if (!context.hasFonts()) {
            throw new Html2PdfException(Html2PdfException.FONT_PROVIDER_CONTAINS_ZERO_FONTS);
        }
        roots = new ArrayList<>();
        cssResolver = new DefaultCssResolver(root, context);
        context.getLinkContext().scanForIds(root);
        addFontFaceFonts();
        root = findHtmlNode(root);

        if (context.getCssContext().isNonPagesTargetCounterPresent()) {
            visitToProcessCounters(root);
            context.getCssContext().getCounterManager().clearManager();
        }
        visit(root);
        HtmlDocument doc = (HtmlDocument) roots.get(0);
        // TODO DEVSIX-4261 more precise check if a counter was actually added to the document
        if (context.getCssContext().isPagesCounterPresent()) {
            if (doc.getRenderer() instanceof HtmlDocumentRenderer) {
                ((HtmlDocumentRenderer) doc.getRenderer()).processWaitingElement();
                int counter = 0;
                do {
                    ++counter;
                    doc.relayout();
                    if (counter >= context.getLimitOfLayouts()) {
                        logger.warn(MessageFormatUtil.format(
                                Html2PdfLogMessageConstant.EXCEEDED_THE_MAXIMUM_NUMBER_OF_RELAYOUTS));
                        break;
                    }
                } while (((DocumentRenderer) doc.getRenderer()).isRelayoutRequired());
            } else {
                logger.warn(Html2PdfLogMessageConstant.CUSTOM_RENDERER_IS_SET_FOR_HTML_DOCUMENT);
            }
        }
        cssResolver = null;
        roots = null;
        return doc;
    }

    /**
     * Recursively processes a node to preprocess target-counters.
     *
     * @param node the node
     */
    private void visitToProcessCounters(INode node) {
        if (node instanceof IElementNode) {
            final IElementNode element = (IElementNode) node;
            if (cssResolver instanceof DefaultCssResolver) {
                ((DefaultCssResolver) cssResolver).resolveContentAndCountersStyles(node, context.getCssContext());
            }
            CounterProcessorUtil.startProcessingCounters(context.getCssContext(), element);
            visitToProcessCounters(createPseudoElement(element, null, CssConstants.BEFORE));
            for (final INode childNode : element.childNodes()) {
                if (!context.isProcessingInlineSvg()) {
                    visitToProcessCounters(childNode);
                }
            }
            visitToProcessCounters(createPseudoElement(element, null, CssConstants.AFTER));
            CounterProcessorUtil.endProcessingCounters(context.getCssContext(), element);
        }
    }

    /**
     * Recursively processes a node converting HTML into PDF using tag workers.
     *
     * @param node the node
     */
    private void visit(INode node) {
        if (node instanceof IElementNode) {
            IElementNode element = (IElementNode) node;
            element.setStyles(cssResolver.resolveStyles(element, context.getCssContext()));
            if (!isDisplayable(element)) {
                return;
            }

            ITagWorker tagWorker = context.getTagWorkerFactory().getTagWorker(element, context);
            if (tagWorker == null) {
                if (!ignoredTags.contains(element.name())) {
                    logger.error(
                            MessageFormatUtil.format(
                                    Html2PdfLogMessageConstant.NO_WORKER_FOUND_FOR_TAG,
                                    element.name()));
                }
            } else {
                context.getState().push(tagWorker);
            }
            if (context.getState().getStack().size() == 1 &&
                    tagWorker != null && tagWorker.getElementResult() != null) {
                tagWorker.getElementResult().setProperty(
                        Property.META_INFO, new MetaInfoContainer(context.getMetaInfoContainer().getMetaInfo()));
            }
            if (tagWorker instanceof HtmlTagWorker) {
                ((HtmlTagWorker) tagWorker).processPageRules(node, cssResolver, context);
            }

            if (TagConstants.BODY.equals(element.name()) || TagConstants.HTML.equals(element.name())) {
                runApplier(element, tagWorker);
            }

            context.getOutlineHandler().addOutlineAndDestToDocument(tagWorker, element, context);
            TextDecorationApplierUtil.propagateTextDecorationProperties(element);
            CounterProcessorUtil.startProcessingCounters(context.getCssContext(), element);
            visit(createPseudoElement(element, tagWorker, CssConstants.BEFORE));
            visit(createPseudoElement(element, tagWorker, CssConstants.PLACEHOLDER));
            for (INode childNode : element.childNodes()) {
                if (!context.isProcessingInlineSvg()) {
                    visit(childNode);
                }
            }
            visit(createPseudoElement(element, tagWorker, CssConstants.AFTER));
            CounterProcessorUtil.endProcessingCounters(context.getCssContext(), element);

            if (tagWorker != null) {
                tagWorker.processEnd(element, context);
                LinkHelper.createDestination(tagWorker, element, context);
                context.getOutlineHandler().setDestinationToElement(tagWorker, element);
                context.getState().pop();

                if (!TagConstants.BODY.equals(element.name()) && !TagConstants.HTML.equals(element.name())) {
                    runApplier(element, tagWorker);
                }
                if (!context.getState().empty()) {
                    PageBreakApplierUtil.addPageBreakElementBefore(context, context.getState().top(), element, tagWorker);
                    tagWorker = processRunningElement(tagWorker, element, context);
                    boolean childProcessed = context.getState().top().processTagChild(tagWorker, context);
                    PageBreakApplierUtil.addPageBreakElementAfter(context, context.getState().top(), element, tagWorker);
                    if (!childProcessed && !ignoredChildTags.contains(element.name())) {
                        logger.error(
                                MessageFormatUtil.format(
                                        Html2PdfLogMessageConstant.WORKER_UNABLE_TO_PROCESS_OTHER_WORKER,
                                        context.getState().top().getClass().getName(),
                                        tagWorker.getClass().getName()));
                    }
                } else if (tagWorker.getElementResult() != null) {
                    roots.add(tagWorker.getElementResult());
                }

                if (tagWorker.getElementResult() != null && context.isContinuousContainerEnabled()) {
                    tagWorker.getElementResult().setProperty(Property.COLLAPSING_MARGINS, Boolean.FALSE);
                    tagWorker.getElementResult().setProperty(Property.TREAT_AS_CONTINUOUS_CONTAINER, true);
                }
            }

            element.setStyles(null);

        } else if (node instanceof ITextNode) {
            String content = ((ITextNode) node).wholeText();
            if (content != null) {
                if (!context.getState().empty()) {
                    boolean contentProcessed = context.getState().top().processContent(content, context);
                    if (!contentProcessed) {
                        logger.error(
                                MessageFormatUtil.format(
                                        Html2PdfLogMessageConstant.WORKER_UNABLE_TO_PROCESS_IT_S_TEXT_CONTENT,
                                        context.getState().top().getClass().getName()));
                    }
                } else {
                    logger.error(Html2PdfLogMessageConstant.NO_CONSUMER_FOUND_FOR_CONTENT);
                }

            }
        }
    }

    private void runApplier(IElementNode element, ITagWorker tagWorker) {
        ICssApplier cssApplier = context.getCssApplierFactory().getCssApplier(element);
        if (cssApplier == null) {
            if (!ignoredCssTags.contains(element.name())) {
                logger.error(MessageFormatUtil.format(
                        Html2PdfLogMessageConstant.NO_CSS_APPLIER_FOUND_FOR_TAG, element.name()));
            }
        } else {
            cssApplier.apply(context, element, tagWorker);
        }
    }

    private ITagWorker processRunningElement(ITagWorker tagWorker, IElementNode element, ProcessorContext context) {
        String runningPrefix = CssConstants.RUNNING + "(";
        String positionVal;
        int endBracketInd;
        if (element.getStyles() == null
                || (positionVal = element.getStyles().get(CssConstants.POSITION)) == null
                || !positionVal.startsWith(runningPrefix)
                // closing bracket should be there and there should be at least one symbol between brackets
                || (endBracketInd = positionVal.indexOf(")")) <= runningPrefix.length()) {
            return tagWorker;
        }

        String runningElemName = positionVal.substring(runningPrefix.length(), endBracketInd).trim();
        if (runningElemName.isEmpty()) {
            return tagWorker;
        }

        RunningElementContainer runningElementContainer = new RunningElementContainer(element, tagWorker);
        context.getCssContext().getRunningManager().addRunningElement(runningElemName, runningElementContainer);

        return new RunningElementTagWorker(runningElementContainer);
    }

    /**
     * Adds @font-face fonts to the FontProvider.
     */
    private void addFontFaceFonts() {
        if (cssResolver instanceof DefaultCssResolver) {
            for (CssFontFaceRule fontFace : ((DefaultCssResolver) cssResolver).getFonts()) {
                boolean findSupportedSrc = false;
                List<CssDeclaration> declarations = fontFace.getProperties();
                CssFontFace ff = CssFontFace.create(declarations);
                if (ff != null) {
                    for (CssFontFace.CssFontFaceSrc src : ff.getSources()) {
                        if (createFont(ff.getFontFamily(), src, fontFace.resolveUnicodeRange())) {
                            findSupportedSrc = true;
                            break;
                        }
                    }
                }
                if (!findSupportedSrc) {
                    logger.error(MessageFormatUtil.format(
                            Html2PdfLogMessageConstant.UNABLE_TO_RETRIEVE_FONT, fontFace));
                }
            }
        }
    }

    /**
     * Creates a font and adds it to the context.
     *
     * @param fontFamily   the font family
     * @param src          the source of the font
     * @param unicodeRange the unicode range
     * @return true, if successful
     */
    private boolean createFont(String fontFamily, CssFontFace.CssFontFaceSrc src, Range unicodeRange) {
        if (!CssFontFace.isSupportedFontFormat(src.getFormat())) {
            return false;
        } else if (src.isLocal()) { // to method with lazy initialization
            Collection<FontInfo> fonts = context.getFontProvider().getFontSet().get(src.getSrc());
            if (fonts.size() > 0) {
                for (FontInfo fi : fonts) {
                    context.addTemporaryFont(fi, fontFamily);
                }
                return true;
            } else {
                return false;
            }
        } else {
            try {
                // Cache at resource resolver level only, at font level we will create font in any case.
                // The instance of fontProgram will be collected by GC if the is no need in it.
                byte[] bytes = context.getResourceResolver().retrieveBytesFromResource(src.getSrc());
                if (bytes != null) {
                    FontProgram fp = FontProgramFactory.createFont(bytes, false);
                    context.addTemporaryFont(fp, PdfEncodings.IDENTITY_H, fontFamily, unicodeRange);
                    return true;
                }
            } catch (Exception ignored) {
            }
            return false;
        }
    }

    /**
     * Creates a pseudo element (before and after CSS).
     *
     * @param node              the node
     * @param tagWorker         the tagWorker
     * @param pseudoElementName the pseudo element name
     * @return created pseudo element
     */
    private static CssPseudoElementNode createPseudoElement(IElementNode node,
                                                            ITagWorker tagWorker, String pseudoElementName) {
        switch (pseudoElementName) {
            case CssConstants.BEFORE:
            case CssConstants.AFTER:
                if (!CssPseudoElementUtil.hasBeforeAfterElements(node)) {
                    return null;
                }
                break;
            case CssConstants.PLACEHOLDER:
                if (!(TagConstants.INPUT.equals(node.name()) || TagConstants.TEXTAREA.equals(node.name())) || // TODO DEVSIX-1944: Resolve the issue and remove the line
                        null == tagWorker
                        || !(tagWorker.getElementResult() instanceof IPlaceholderable)
                        || null == ((IPlaceholderable) tagWorker.getElementResult()).getPlaceholder()) {
                    return null;
                }
                break;
            default:
                return null;
        }
        return new CssPseudoElementNode(node, pseudoElementName);
    }

    /**
     * Find an element in a node.
     *
     * @param node    the node
     * @param tagName the tag name
     * @return the element node
     */
    private IElementNode findElement(INode node, String tagName) {
        LinkedList<INode> q = new LinkedList<>();
        q.add(node);
        while (!q.isEmpty()) {
            INode currentNode = q.getFirst();
            q.removeFirst();
            if (currentNode instanceof IElementNode && ((IElementNode) currentNode).name().equals(tagName)) {
                return (IElementNode) currentNode;
            }
            for (INode child : currentNode.childNodes()) {
                if (child instanceof IElementNode) {
                    q.add(child);
                }
            }
        }
        return null;
    }

    /**
     * Find the HTML node.
     *
     * @param node the node
     * @return the i element node
     */
    private IElementNode findHtmlNode(INode node) {
        return findElement(node, TagConstants.HTML);
    }

    /**
     * Find the BODY node.
     *
     * @param node the node
     * @return the i element node
     */
    private IElementNode findBodyNode(INode node) {
        return findElement(node, TagConstants.BODY);
    }

    /**
     * Checks if an element should be displayed.
     *
     * @param element the element
     * @return true, if the element should be displayed
     */
    private boolean isDisplayable(IElementNode element) {
        if (element != null && element.getStyles() != null && CssConstants.NONE.equals(element.getStyles().get(CssConstants.DISPLAY))) {
            return false;
        }
        if (isPlaceholder(element)) {
            return true;
        }
        if (element instanceof CssPseudoElementNode) {
            if (element.childNodes().isEmpty()) {
                return false;
            }
            boolean hasStyles = element.getStyles() != null;
            String positionVal = hasStyles ? element.getStyles().get(CssConstants.POSITION) : null;
            String displayVal = hasStyles ? element.getStyles().get(CssConstants.DISPLAY) : null;
            boolean containsNonEmptyChildNode = false;
            boolean containsElementNode = false;
            for (int i = 0; i < element.childNodes().size(); i++) {
                if (element.childNodes().get(i) instanceof ITextNode) {
                    containsNonEmptyChildNode = true;
                    break;
                } else if (element.childNodes().get(i) instanceof IElementNode) {
                    containsElementNode = true;
                }
            }
            return containsElementNode || containsNonEmptyChildNode || CssConstants.ABSOLUTE.equals(positionVal) || CssConstants.FIXED.equals(positionVal)
                    || displayVal != null && !CssConstants.INLINE.equals(displayVal);
        }
        return element != null;
    }

    private boolean isPlaceholder(IElementNode element) {
        return element instanceof CssPseudoElementNode && CssConstants.PLACEHOLDER.equals(((CssPseudoElementNode) element).getPseudoElementName());
    }

    private static void updateSequenceId(IElement element, SequenceId sequenceId) {
        if (element instanceof AbstractIdentifiableElement) {
            final AbstractIdentifiableElement identifiableElement = (AbstractIdentifiableElement) element;

            if (SequenceIdManager.getSequenceId(identifiableElement) == sequenceId) {
                // potential cyclic reference case: element has been processed already
                return;
            }

            SequenceIdManager.setSequenceId(identifiableElement, sequenceId);

            if (identifiableElement instanceof IAbstractElement) {
                IAbstractElement abstractElement = (IAbstractElement) identifiableElement;
                updateChildren(abstractElement.getChildren(), sequenceId);
            }
        }
    }

    private static void updateChildren(List<IElement> children, SequenceId sequenceId) {
        if (children == null) {
            return;
        }
        for (IElement child : children) {
            updateSequenceId(child, sequenceId);
        }
    }
}
