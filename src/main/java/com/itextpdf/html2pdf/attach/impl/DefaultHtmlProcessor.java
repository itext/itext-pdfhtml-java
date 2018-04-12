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
package com.itextpdf.html2pdf.attach.impl;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.Html2PdfProductInfo;
import com.itextpdf.html2pdf.LogMessageConstant;
import com.itextpdf.html2pdf.attach.IHtmlProcessor;
import com.itextpdf.html2pdf.attach.ITagWorker;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.attach.impl.layout.HtmlDocumentRenderer;
import com.itextpdf.html2pdf.attach.impl.layout.RunningElementContainer;
import com.itextpdf.html2pdf.attach.impl.tags.RunningElementTagWorker;
import com.itextpdf.html2pdf.attach.impl.tags.HtmlTagWorker;
import com.itextpdf.html2pdf.attach.util.LinkHelper;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.css.CssFontFaceRule;
import com.itextpdf.html2pdf.css.apply.ICssApplier;
import com.itextpdf.html2pdf.css.apply.util.PageBreakApplierUtil;
import com.itextpdf.html2pdf.css.pseudo.CssPseudoElementNode;
import com.itextpdf.html2pdf.css.pseudo.CssPseudoElementUtil;
import com.itextpdf.html2pdf.css.resolve.DefaultCssResolver;
import com.itextpdf.html2pdf.css.resolve.ICssResolver;
import com.itextpdf.html2pdf.exception.Html2PdfException;
import com.itextpdf.html2pdf.html.TagConstants;
import com.itextpdf.html2pdf.html.node.IElementNode;
import com.itextpdf.html2pdf.html.node.INode;
import com.itextpdf.html2pdf.html.node.ITextNode;
import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.font.FontInfo;
import com.itextpdf.layout.property.Property;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import com.itextpdf.kernel.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.io.util.MessageFormatUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * The default implementation to process HTML.
 */
public class DefaultHtmlProcessor implements IHtmlProcessor {

    /** The logger instance. */
    private static final Logger logger = LoggerFactory.getLogger(DefaultHtmlProcessor.class);

    /** Set of tags that do not map to any tag worker and that are deliberately excluded from the logging. */
    private static final Set<String> ignoredTags = Collections.unmodifiableSet(new HashSet<String>(Arrays.asList(
            TagConstants.HEAD,
            TagConstants.STYLE,
            // TODO <tbody> is not supported. Styles will be propagated anyway
            TagConstants.TBODY)));

    /** Set of tags to which we do not want to apply CSS to and that are deliberately excluded from the logging */
    private static final Set<String> ignoredCssTags = Collections.unmodifiableSet(new HashSet<String>(Arrays.asList(
            TagConstants.BR,
            TagConstants.LINK,
            TagConstants.META,
            TagConstants.TITLE,
            // Content from <tr> is thrown upwards to parent, in other cases CSS is inherited anyway
            TagConstants.TR)));

    /** Set of tags that might be not processed by some tag workers and that are deliberately excluded from the logging. */
    private static final Set<String> ignoredChildTags = Collections.unmodifiableSet(new HashSet<String>(Arrays.asList(
            TagConstants.BODY,
            TagConstants.LINK,
            TagConstants.META,
            TagConstants.SCRIPT,
            TagConstants.TITLE // TODO implement
            )));

    /** The processor context. */
    private ProcessorContext context;

    /** A list of parent objects that result from parsing the HTML. */
    private List<IPropertyContainer> roots;

    /** The CSS resolver. */
    private ICssResolver cssResolver;

    /**
     * Instantiates a new default html processor.
     *
     * @param converterProperties the converter properties
     */
    public DefaultHtmlProcessor(ConverterProperties converterProperties) {
        this.context = new ProcessorContext(converterProperties);
    }

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.attach.IHtmlProcessor#processElements(com.itextpdf.html2pdf.html.node.INode)
     */
    @Override
    public List<com.itextpdf.layout.element.IElement> processElements(INode root) {
        String licenseKeyClassName = "com.itextpdf.licensekey.LicenseKey";
        String licenseKeyProductClassName = "com.itextpdf.licensekey.LicenseKeyProduct";
        String licenseKeyFeatureClassName = "com.itextpdf.licensekey.LicenseKeyProductFeature";
        String checkLicenseKeyMethodName = "scheduledCheck";

        try {
            Class licenseKeyClass = Class.forName(licenseKeyClassName);
            Class licenseKeyProductClass = Class.forName(licenseKeyProductClassName);
            Class licenseKeyProductFeatureClass = Class.forName(licenseKeyFeatureClassName);

            Object licenseKeyProductFeatureArray = Array.newInstance(licenseKeyProductFeatureClass, 0);

            Class[] params = new Class[] {
                    String.class,
                    Integer.TYPE,
                    Integer.TYPE,
                    licenseKeyProductFeatureArray.getClass()
            };

            Constructor licenseKeyProductConstructor = licenseKeyProductClass.getConstructor(params);

            Object licenseKeyProductObject = licenseKeyProductConstructor.newInstance(
                    Html2PdfProductInfo.PRODUCT_NAME,
                    Html2PdfProductInfo.MAJOR_VERSION,
                    Html2PdfProductInfo.MINOR_VERSION,
                    licenseKeyProductFeatureArray
            );

            Method method = licenseKeyClass.getMethod(checkLicenseKeyMethodName, licenseKeyProductClass);
            method.invoke(null, licenseKeyProductObject);
        } catch (Exception e) {
            if ( ! Version.isAGPLVersion() ) {
                throw new RuntimeException(e.getCause());
            }
        }

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
        for (IPropertyContainer propertyContainer : bodyDiv.getChildren()) {
            if (propertyContainer instanceof com.itextpdf.layout.element.IElement) {
                propertyContainer.setProperty(Property.COLLAPSING_MARGINS, true);
                propertyContainer.setProperty(Property.FONT_PROVIDER, context.getFontProvider());
                if (context.getTempFonts() != null) {
                    propertyContainer.setProperty(Property.FONT_SET, context.getTempFonts());
                }
                elements.add((com.itextpdf.layout.element.IElement) propertyContainer);
            }
        }
        cssResolver = null;
        roots = null;
        return elements;
    }

    /* (non-Javadoc)
     * @see com.itextpdf.html2pdf.attach.IHtmlProcessor#processDocument(com.itextpdf.html2pdf.html.node.INode, com.itextpdf.kernel.pdf.PdfDocument)
     */
    @Override
    public Document processDocument(INode root, PdfDocument pdfDocument) {
        String licenseKeyClassName = "com.itextpdf.licensekey.LicenseKey";
        String licenseKeyProductClassName = "com.itextpdf.licensekey.LicenseKeyProduct";
        String licenseKeyFeatureClassName = "com.itextpdf.licensekey.LicenseKeyProductFeature";
        String checkLicenseKeyMethodName = "scheduledCheck";

        try {
            Class licenseKeyClass = Class.forName(licenseKeyClassName);
            Class licenseKeyProductClass = Class.forName(licenseKeyProductClassName);
            Class licenseKeyProductFeatureClass = Class.forName(licenseKeyFeatureClassName);

            Object licenseKeyProductFeatureArray = Array.newInstance(licenseKeyProductFeatureClass, 0);

            Class[] params = new Class[] {
                    String.class,
                    Integer.TYPE,
                    Integer.TYPE,
                    licenseKeyProductFeatureArray.getClass()
            };

            Constructor licenseKeyProductConstructor = licenseKeyProductClass.getConstructor(params);

            Object licenseKeyProductObject = licenseKeyProductConstructor.newInstance(
                    Html2PdfProductInfo.PRODUCT_NAME,
                    Html2PdfProductInfo.MAJOR_VERSION,
                    Html2PdfProductInfo.MINOR_VERSION,
                    licenseKeyProductFeatureArray
            );

            Method method = licenseKeyClass.getMethod(checkLicenseKeyMethodName, licenseKeyProductClass);
            method.invoke(null, licenseKeyProductObject);
        } catch (Exception e) {
            if ( ! Version.isAGPLVersion() ) {
                throw new RuntimeException(e.getCause());
            }
        }

        context.reset(pdfDocument);
        if (!context.hasFonts()) {
            throw new Html2PdfException(Html2PdfException.FontProviderContainsZeroFonts);
        }
        // TODO store html version from document type in context if necessary
        roots = new ArrayList<>();
        cssResolver = new DefaultCssResolver(root, context);
        context.getLinkContext().scanForIds(root);
        addFontFaceFonts();
        root = findHtmlNode(root);

        visit(root);
        Document doc = (Document) roots.get(0);
        // TODO more precise check if a counter was actually added to the document
        if (context.getCssContext().isPagesCounterPresent() && doc.getRenderer() instanceof HtmlDocumentRenderer) {
            doc.relayout();
        }
        cssResolver = null;
        roots = null;
        return doc;
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
                    logger.error(MessageFormatUtil.format(LogMessageConstant.NO_WORKER_FOUND_FOR_TAG, (element).name()));
                }
            } else {
                context.getState().push(tagWorker);
            }
            if (tagWorker instanceof HtmlTagWorker) {
                ((HtmlTagWorker) tagWorker).processPageRules(node, cssResolver, context);
            }

            context.getOutlineHandler().addOutline(tagWorker, element, context);

            visitPseudoElement(element, CssConstants.BEFORE);
            if (element.name().equals(TagConstants.BODY) || element.name().equals(TagConstants.HTML))
                runApplier(element, tagWorker);
            for (INode childNode : element.childNodes()) {
                visit(childNode);
            }
            visitPseudoElement(element, CssConstants.AFTER);

            if (tagWorker != null) {
                tagWorker.processEnd(element, context);
                LinkHelper.createDestination(tagWorker, element, context);
                context.getOutlineHandler().addDestination(tagWorker, element);
                context.getState().pop();

                if (!element.name().equals(TagConstants.BODY) && !element.name().equals(TagConstants.HTML))
                    runApplier(element, tagWorker);
                if (!context.getState().empty()) {
                    PageBreakApplierUtil.addPageBreakElementBefore(context, context.getState().top(), element, tagWorker);
                    tagWorker = processRunningElement(tagWorker, element, context);
                    boolean childProcessed = context.getState().top().processTagChild(tagWorker, context);
                    PageBreakApplierUtil.addPageBreakElementAfter(context, context.getState().top(), element, tagWorker);
                    if (!childProcessed && !ignoredChildTags.contains(element.name())) {
                        logger.error(MessageFormatUtil.format(LogMessageConstant.WORKER_UNABLE_TO_PROCESS_OTHER_WORKER,
                                context.getState().top().getClass().getName(), tagWorker.getClass().getName()));
                    }
                } else if (tagWorker.getElementResult() != null) {
                    roots.add(tagWorker.getElementResult());
                }
            }

            element.setStyles(null);

        } else if (node instanceof ITextNode) {
            String content = ((ITextNode) node).wholeText();
            if (content != null) {
                if (!context.getState().empty()) {
                    boolean contentProcessed = context.getState().top().processContent(content, context);
                    if (!contentProcessed) {
                        logger.error(MessageFormatUtil.format(LogMessageConstant.WORKER_UNABLE_TO_PROCESS_IT_S_TEXT_CONTENT,
                                context.getState().top().getClass().getName()));
                    }
                } else {
                    logger.error(LogMessageConstant.NO_CONSUMER_FOUND_FOR_CONTENT);
                }

            }
        }
    }

    private void runApplier(IElementNode element, ITagWorker tagWorker) {
        ICssApplier cssApplier = context.getCssApplierFactory().getCssApplier(element);
        if (cssApplier == null) {
            if (!ignoredCssTags.contains(element.name())) {
                logger.error(MessageFormatUtil.format(LogMessageConstant.NO_CSS_APPLIER_FOUND_FOR_TAG, element.name()));
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

        // TODO For now the whole ITagWorker of the running element is preserved inside RunningElementContainer
        // for the sake of future processing in page margin box. This is somewhat a workaround and storing
        // tag workers might be easily seen as something undesirable, however at least for now it seems to be
        // most suitable solution because:
        // - in any case, processing of the whole running element with it's children should be done in
        //   "normal flow", i.e. in DefaultHtmlProcessor, based on the spec that says that element should be
        //   processed as it was still in the same position in DOM, but visually as if "display: none" was set.
        // - the whole process would need to be repeated in PageContextProcessor again, so it's a double work;
        //   also currently there is still no convenient way for unifying the processing here and in
        //   PageContextProcessor, currently only running elements require processing of the whole hierarchy of
        //   children outside of the default DOM processing and also it's unclear whether this code would be suitable
        //   for the simplified approach of processing all other children of page margin boxes.
        // - ITagWorker is only publicly passed to the constructor, but there is no exposed way to get it out of
        //   RunningElementContainer, so it would be fairly easy to change this approach in future if needed.
        RunningElementContainer runningElementContainer = new RunningElementContainer(element, tagWorker);
        context.getCssContext().getRunningManager().addRunningElement(runningElemName, runningElementContainer);

        return new RunningElementTagWorker(runningElementContainer);
    }

    /**
     * Adds @font-face fonts to the FontProvider.
     */
    private void addFontFaceFonts() {
        //TODO Shall we add getFonts() to ICssResolver?
        if (cssResolver instanceof DefaultCssResolver) {
            for (CssFontFaceRule fontFace : ((DefaultCssResolver) cssResolver).getFonts()) {
                boolean findSupportedSrc = false;
                FontFace ff = FontFace.create(fontFace.getProperties());
                if (ff != null) {
                    for (FontFace.FontFaceSrc src : ff.getSources()) {
                        if (createFont(ff.getFontFamily(), src)) {
                            findSupportedSrc = true;
                            break;
                        }
                    }
                }
                if (!findSupportedSrc) {
                    logger.error(MessageFormatUtil.format(LogMessageConstant.UNABLE_TO_RETRIEVE_FONT, fontFace));
                }
            }
        }
    }

    /**
     * Creates a font and adds it to the context.
     *
     * @param fontFamily the font family
     * @param src the source of the font
     * @return true, if successful
     */
    private boolean createFont(String fontFamily, FontFace.FontFaceSrc src) {
        if (!supportedFontFormat(src.format)) {
            return false;
        } else if (src.isLocal) { // to method with lazy initialization
            Collection<FontInfo> fonts = context.getFontProvider().getFontSet().get(src.src);
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
                byte[] bytes = context.getResourceResolver().retrieveStream(src.src);
                if (bytes != null) {
                    FontProgram fp = FontProgramFactory.createFont(bytes, false);
                    context.addTemporaryFont(fp, PdfEncodings.IDENTITY_H, fontFamily);
                    return true;
                }
            } catch (Exception ignored) {
            }
            return false;
        }
    }

    /**
     * Checks whether in general we support requested font format.
     *
     * @param format {@link com.itextpdf.html2pdf.attach.impl.FontFace.FontFormat}
     * @return true, if supported or unrecognized.
     */
    private boolean supportedFontFormat(FontFace.FontFormat format) {
        switch (format) {
            case None:
            case TrueType:
            case OpenType:
            case WOFF:
            case WOFF2:
                return true;
            default:
                return false;
        }
    }

    /**
     * Processes a pseudo element (before and after CSS).
     *
     * @param node the node
     * @param pseudoElementName the pseudo element name
     */
    private void visitPseudoElement(IElementNode node, String pseudoElementName) {
        if (CssPseudoElementUtil.hasBeforeAfterElements(node)) {
            visit(new CssPseudoElementNode(node, pseudoElementName));
        }
    }

    /**
     * Find an element in a node.
     *
     * @param node the node
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

}
