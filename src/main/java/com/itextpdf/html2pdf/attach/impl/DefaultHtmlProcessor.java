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

import com.itextpdf.html2pdf.Html2PdfProductInfo;
import com.itextpdf.html2pdf.ResourceResolver;
import com.itextpdf.html2pdf.attach.IHtmlProcessor;
import com.itextpdf.html2pdf.attach.ITagWorker;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.attach.TagWorkerFactory;
import com.itextpdf.html2pdf.css.apply.CssApplierFactory;
import com.itextpdf.html2pdf.css.apply.ICssApplier;
import com.itextpdf.html2pdf.css.resolve.ICssResolver;
import com.itextpdf.html2pdf.html.TagConstants;
import com.itextpdf.html2pdf.html.node.IElement;
import com.itextpdf.html2pdf.html.node.INode;
import com.itextpdf.html2pdf.html.node.ITextNode;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.IPropertyContainer;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import com.itextpdf.html2pdf.Html2PdfProductInfo;
import com.itextpdf.kernel.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class DefaultHtmlProcessor implements IHtmlProcessor {

    private static final Logger logger = LoggerFactory.getLogger(DefaultHtmlProcessor.class);

    private ProcessorContext context;
    private ICssResolver cssResolver;
    private INode root;
    private ResourceResolver resourceResolver;
    private List<IPropertyContainer> roots;

    public DefaultHtmlProcessor(INode node, ICssResolver cssResolver, ResourceResolver resourceResolver) {
        this.cssResolver = cssResolver;
        this.root = node;
        this.resourceResolver = resourceResolver;
    }

    @Override
    public List<com.itextpdf.layout.element.IElement> processElements() {
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

        context = new ProcessorContext(cssResolver, resourceResolver);
        roots = new ArrayList<>();
        root = findBodyNode(root);
        visit(root);
        context = null;
        List<com.itextpdf.layout.element.IElement> elements = new ArrayList<>();
        for (IPropertyContainer propertyContainer : roots) {
            if (propertyContainer instanceof com.itextpdf.layout.element.IElement) {
                elements.add((com.itextpdf.layout.element.IElement) propertyContainer);
            }
        }
        roots = null;
        return elements;
    }

    @Override
    public Document processDocument(PdfDocument pdfDocument) {
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

        context = new ProcessorContext(cssResolver, pdfDocument, resourceResolver);
        roots = new ArrayList<>();
        visit(root);
        context = null;
        Document doc = (Document) roots.get(0);
        roots = null;
        return doc;
    }

    private void visit(INode node) {
        if (node instanceof IElement) {
            IElement element = (IElement) node;
            element.setStyles(context.getCssResolver().resolveStyles(element));

            ITagWorker tagWorker = TagWorkerFactory.getTagWorker(element, context);
            if (tagWorker == null) {
                logger.error("No worker found for tag " + (element).name());
            } else {
                context.getState().push(tagWorker);
            }

            for (INode childNode : element.childNodes()) {
                visit(childNode);
            }

            if (tagWorker != null) {
                tagWorker.processEnd(element, context);
                context.getState().pop();

                ICssApplier cssApplier = CssApplierFactory.getCssApplier(element.name());
                if (cssApplier == null) {
                    logger.error("No css applier found for tag " + element.name());
                } else {
                    cssApplier.apply(context, element, tagWorker);
                }

                if (!context.getState().empty()) {
                    boolean childProcessed = context.getState().top().processTagChild(tagWorker, context);
                    if (!childProcessed) {
                        logger.error(String.format("Worker of type %s wasn't able to process %s",
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
                        logger.error(String.format("Worker of type %s wasn't able to process it's text content",
                                context.getState().top().getClass().getName()));
                    }
                } else {
                    logger.error("No consumer found for content");
                }

            }
        }
    }

    private INode findBodyNode(INode node) {
        Queue<INode> q = new LinkedList<>();
        q.add(node);
        while (!q.isEmpty()) {
            INode currentNode = q.poll();
            if (currentNode instanceof IElement && ((IElement) currentNode).name().equals(TagConstants.BODY)) {
                return currentNode;
            }
            for (INode child : currentNode.childNodes()) {
                if (child instanceof IElement) {
                    q.add(child);
                }
            }
        }
        return null;
    }
}
