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
package com.itextpdf.html2pdf.attach.impl;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.Html2PdfProductInfo;
import com.itextpdf.html2pdf.LogMessageConstant;
import com.itextpdf.html2pdf.attach.IHtmlProcessor;
import com.itextpdf.html2pdf.attach.ITagWorker;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.css.apply.ICssApplier;
import com.itextpdf.html2pdf.css.resolve.DefaultCssResolver;
import com.itextpdf.html2pdf.css.resolve.ICssResolver;
import com.itextpdf.html2pdf.html.HtmlUtils;
import com.itextpdf.html2pdf.html.TagConstants;
import com.itextpdf.html2pdf.html.node.IElementNode;
import com.itextpdf.html2pdf.html.node.INode;
import com.itextpdf.html2pdf.html.node.ITextNode;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.property.Property;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import com.itextpdf.html2pdf.Html2PdfProductInfo;
import com.itextpdf.kernel.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class DefaultHtmlProcessor implements IHtmlProcessor {

    private static final Logger logger = LoggerFactory.getLogger(DefaultHtmlProcessor.class);

    // The tags that do not map into any workers and are deliberately excluded from the logging
    private static final Set<String> ignoredTags = Collections.unmodifiableSet(new HashSet<String>(Arrays.asList(
            TagConstants.HEAD,
            TagConstants.STYLE,
            // TODO <tbody> is not supported. Styles will be propagated anyway
            TagConstants.TBODY)));

    // The tags we do not want to apply css to and therefore exclude from the logging
    private static final Set<String> ignoredCssTags = Collections.unmodifiableSet(new HashSet<String>(Arrays.asList(
            TagConstants.BR,
            TagConstants.TITLE,
            TagConstants.META,
            // Content from <tr> is thrown upwards to parent, in other cases css is inherited anyway
            TagConstants.TR)));

    private ProcessorContext context;
    private List<IPropertyContainer> roots;
    private ICssResolver cssResolver;

    public DefaultHtmlProcessor(ConverterProperties converterProperties) {
        this.context = new ProcessorContext(converterProperties);
    }

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
        cssResolver = new DefaultCssResolver(root, context.getDeviceDescription(), context.getResourceResolver());
        IElementNode html = findHtmlNode(root);
        IElementNode body = findBodyNode(root);
        // Force resolve styles to fetch default font size etc
        html.setStyles(cssResolver.resolveStyles(html));
        body.setStyles(cssResolver.resolveStyles(body));
        for (INode node : body.childNodes()) {
            if (node instanceof IElementNode) {
                visit(node);
            } else if (node instanceof ITextNode) {
                logger.error(MessageFormat.format(LogMessageConstant.TEXT_WAS_NOT_PROCESSED, ((ITextNode) node).wholeText()));
            }
        }
        List<com.itextpdf.layout.element.IElement> elements = new ArrayList<>();
        for (IPropertyContainer propertyContainer : roots) {
            if (propertyContainer instanceof com.itextpdf.layout.element.IElement) {
                propertyContainer.setProperty(Property.COLLAPSING_MARGINS, true);
                elements.add((com.itextpdf.layout.element.IElement) propertyContainer);
            }
        }
        cssResolver = null;
        roots = null;
        return elements;
    }

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
        // TODO store html version from document type in context if necessary
        roots = new ArrayList<>();
        cssResolver = new DefaultCssResolver(root, context.getDeviceDescription(), context.getResourceResolver());
        root = findHtmlNode(root);
        visit(root);
        Document doc = (Document) roots.get(0);
        cssResolver = null;
        roots = null;
        return doc;
    }

    private void visit(INode node) {
        if (node instanceof IElementNode) {
            IElementNode element = (IElementNode) node;
            element.setStyles(cssResolver.resolveStyles(element));
            if (!isDisplayable(element)) {
                return;
            }


            ITagWorker tagWorker = context.getTagWorkerFactory().getTagWorkerInstance(element, context);
            if (tagWorker == null) {
                // For stylesheet links it looks ugly, but log errors will be printed for other <link> elements, not css links
                if (!ignoredTags.contains(element.name()) && !HtmlUtils.isStyleSheetLink(element)) {
                    logger.error(MessageFormat.format(LogMessageConstant.NO_WORKER_FOUND_FOR_TAG, (element).name()));
                }
            } else {
                context.getState().push(tagWorker);
            }

            for (INode childNode : element.childNodes()) {
                visit(childNode);
            }

            if (tagWorker != null) {
                tagWorker.processEnd(element, context);
                context.getState().pop();

                ICssApplier cssApplier = context.getCssApplierFactory().getCssApplier(element.name());
                if (cssApplier == null) {
                    if (!ignoredCssTags.contains(element.name())) {
                        logger.error(MessageFormat.format(LogMessageConstant.NO_CSS_APPLIER_FOUND_FOR_TAG, element.name()));
                    }
                } else {
                    cssApplier.apply(context, element, tagWorker);
                }

                if (!context.getState().empty()) {
                    boolean childProcessed = context.getState().top().processTagChild(tagWorker, context);
                    if (!childProcessed) {
                        logger.error(MessageFormat.format(LogMessageConstant.WORKER_UNABLE_TO_PROCESS_OTHER_WORKER,
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
                        logger.error(MessageFormat.format(LogMessageConstant.WORKER_UNABLE_TO_PROCESS_IT_S_TEXT_CONTENT,
                                context.getState().top().getClass().getName()));
                    }
                } else {
                    logger.error(LogMessageConstant.NO_CONSUMER_FOUND_FOR_CONTENT);
                }

            }
        }
    }

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

    private IElementNode findHtmlNode(INode node) {
        return findElement(node, TagConstants.HTML);
    }

    private IElementNode findBodyNode(INode node) {
        return findElement(node, TagConstants.BODY);
    }

    private static boolean isDisplayable(IElementNode element) {
        if (element != null && element.getStyles() != null && CssConstants.NONE.equals(element.getStyles().get(CssConstants.DISPLAY))) {
            return false;
        }
        return element != null;
    }
}
