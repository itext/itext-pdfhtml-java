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
package com.itextpdf.html2pdf;

import com.itextpdf.commons.actions.contexts.IMetaInfo;
import com.itextpdf.html2pdf.attach.ITagWorkerFactory;
import com.itextpdf.html2pdf.attach.impl.OutlineHandler;
import com.itextpdf.html2pdf.css.apply.ICssApplierFactory;
import com.itextpdf.kernel.pdf.PdfAConformanceLevel;
import com.itextpdf.kernel.pdf.PdfOutputIntent;
import com.itextpdf.layout.font.FontProvider;
import com.itextpdf.styledxmlparser.css.media.MediaDeviceDescription;
import com.itextpdf.styledxmlparser.resolver.resource.IResourceRetriever;

import java.io.InputStream;

/**
 * Properties that will be used by the {@link com.itextpdf.html2pdf.HtmlConverter}.
 */
public class ConverterProperties {

    /**
     * Default maximum number of layouts.
     */
    private static final int DEFAULT_LIMIT_OF_LAYOUTS = 10;

    /**
     * The media device description.
     */
    private MediaDeviceDescription mediaDeviceDescription;

    /**
     * The font provider.
     */
    private FontProvider fontProvider;

    /**
     * The tag worker factory.
     */
    private ITagWorkerFactory tagWorkerFactory;

    /**
     * The CSS applier factory.
     */
    private ICssApplierFactory cssApplierFactory;

    /**
     * The outline handler.
     */
    private OutlineHandler outlineHandler;

    /**
     * The base URI.
     */
    private String baseUri;

    /**
     * The resource retriever.
     */
    private IResourceRetriever resourceRetriever;

    /**
     * Indicates whether an AcroForm should be created.
     */
    private boolean createAcroForm = false;

    /**
     * Character set used in conversion of input streams
     */
    private String charset;

    /**
     * Indicates whether the document should be opened in immediate flush or not
     */
    private boolean immediateFlush = true;

    /**
     * Maximum number of layouts.
     */
    private int limitOfLayouts = DEFAULT_LIMIT_OF_LAYOUTS;

    /**
     * Meta info that will be added to the events thrown by html2Pdf.
     */
    private IMetaInfo metaInfo;

    /**
     * enables continuous container for all elements.
     */
    private boolean continuousContainerEnabled;

    /**
     * Output intent for final destination device.
     */
    private PdfOutputIntent outputIntent;

    /**
     * Conformance level for conversion to pdf/a.
     */
    private PdfAConformanceLevel conformanceLevel;

    /**
     * Instantiates a new {@link ConverterProperties} instance.
     */
    public ConverterProperties() {
    }

    /**
     * Instantiates a new {@link ConverterProperties} instance based on another {@link ConverterProperties} instance
     * (copy constructor).
     *
     * @param other the other {@link ConverterProperties} instance
     */
    public ConverterProperties(ConverterProperties other) {
        this.mediaDeviceDescription = other.mediaDeviceDescription;
        this.fontProvider = other.fontProvider;
        this.tagWorkerFactory = other.tagWorkerFactory;
        this.cssApplierFactory = other.cssApplierFactory;
        this.baseUri = other.baseUri;
        this.resourceRetriever = other.resourceRetriever;
        this.createAcroForm = other.createAcroForm;
        this.outlineHandler = other.outlineHandler;
        this.charset = other.charset;
        this.metaInfo = other.metaInfo;
        this.limitOfLayouts = other.limitOfLayouts;
        this.immediateFlush = other.immediateFlush;
        this.continuousContainerEnabled = other.continuousContainerEnabled;
    }

    /**
     * Gets the media device description.
     * <p>
     * The properties of the multimedia device are taken into account when creating the SVG and
     * when processing the properties of the СSS.
     *
     * @return the media device description
     */
    public MediaDeviceDescription getMediaDeviceDescription() {
        return mediaDeviceDescription;
    }

    /**
     * Sets the media device description.
     * <p>
     * The properties of the multimedia device are taken into account when creating the SVG and
     * when processing the properties of the СSS.
     *
     * @param mediaDeviceDescription the media device description
     * @return the {@link ConverterProperties} instance
     */
    public ConverterProperties setMediaDeviceDescription(MediaDeviceDescription mediaDeviceDescription) {
        this.mediaDeviceDescription = mediaDeviceDescription;
        return this;
    }

    /**
     * Gets the font provider.
     * <p>
     * Please note that {@link FontProvider} instances cannot be reused across several documents
     * and thus as soon as you set this property, this {@link ConverterProperties} instance becomes only useful
     * for a single HTML conversion.
     *
     * @return the font provider
     */
    public FontProvider getFontProvider() {
        return fontProvider;
    }

    /**
     * Sets the font provider.
     * <p>
     * Please note that {@link FontProvider} instances cannot be reused across several documents
     * and thus as soon as you set this property, this {@link ConverterProperties} instance becomes only useful
     * for a single HTML conversion.
     *
     * @param fontProvider the font provider
     * @return the {@link ConverterProperties} instance
     */
    public ConverterProperties setFontProvider(FontProvider fontProvider) {
        this.fontProvider = fontProvider;
        return this;
    }

    /**
     * Gets maximum number of layouts.
     *
     * @return layouts limit
     */
    public int getLimitOfLayouts() {
        return limitOfLayouts;
    }

    /**
     * Sets maximum number of layouts.
     *
     * @param limitOfLayouts layouts limit
     * @return the {@link ConverterProperties} instance
     */
    public ConverterProperties setLimitOfLayouts(int limitOfLayouts) {
        this.limitOfLayouts = limitOfLayouts;
        return this;
    }

    /**
     * Gets the TagWorkerFactory instance.
     * <p>
     * The tagWorkerFactory is used to create {@link com.itextpdf.html2pdf.attach.ITagWorker}, which in turn
     * are used to convert the HTML tags to the PDF elements.
     *
     * @return the TagWorkerFactory
     */
    public ITagWorkerFactory getTagWorkerFactory() {
        return tagWorkerFactory;
    }

    /**
     * Sets the TagWorkerFactory.
     * <p>
     * The tagWorkerFactory is used to create {@link com.itextpdf.html2pdf.attach.ITagWorker}, which in turn
     * are used to convert the HTML tags to the PDF elements.
     *
     * @param tagWorkerFactory the TagWorkerFactory
     * @return the {@link ConverterProperties} instance
     */
    public ConverterProperties setTagWorkerFactory(ITagWorkerFactory tagWorkerFactory) {
        this.tagWorkerFactory = tagWorkerFactory;
        return this;
    }

    /**
     * Gets the CSS applier factory.
     * <p>
     * The cssApplierFactory is used to create {@link com.itextpdf.html2pdf.css.apply.ICssApplier}, which in turn
     * are used to convert the CSS properties to the PDF properties.
     *
     * @return the CSS applier factory
     */
    public ICssApplierFactory getCssApplierFactory() {
        return cssApplierFactory;
    }

    /**
     * Sets the CSS applier factory.
     * <p>
     * The cssApplierFactory is used to create {@link com.itextpdf.html2pdf.css.apply.ICssApplier}, which in turn
     * are used to convert the CSS properties to the PDF properties.
     *
     * @param cssApplierFactory the CSS applier factory
     * @return the {@link ConverterProperties} instance
     */
    public ConverterProperties setCssApplierFactory(ICssApplierFactory cssApplierFactory) {
        this.cssApplierFactory = cssApplierFactory;
        return this;
    }

    /**
     * Gets the base URI.
     * <p>
     * Base URI is used to resolve other URI.
     *
     * @return the base URI
     */
    public String getBaseUri() {
        return baseUri;
    }

    /**
     * Sets the base URI.
     * <p>
     * Base URI is used to resolve other URI.
     *
     * @param baseUri the base URI
     * @return the {@link ConverterProperties} instance
     */
    public ConverterProperties setBaseUri(String baseUri) {
        this.baseUri = baseUri;
        return this;
    }

    /**
     * Gets the resource retriever.
     * <p>
     * The resourceRetriever is used to retrieve data from resources by URL.
     *
     * @return the resource retriever
     */
    public IResourceRetriever getResourceRetriever() {
        return resourceRetriever;
    }

    /**
     * Sets the resource retriever.
     * <p>
     * The resourceRetriever is used to retrieve data from resources by URL.
     *
     * @param resourceRetriever the resource retriever
     * @return the {@link ConverterProperties} instance
     */
    public ConverterProperties setResourceRetriever(IResourceRetriever resourceRetriever) {
        this.resourceRetriever = resourceRetriever;
        return this;
    }

    /**
     * Check if the createAcroForm flag is set.
     * <p>
     * If createAcroForm is set, then when the form is encountered in HTML, AcroForm will be created, otherwise
     * a visually identical, but not functional element will be created. Please bare in mind that the created
     * Acroform may visually differ a bit from the HTML one.
     *
     * @return the createAcroForm flag
     */
    public boolean isCreateAcroForm() {
        return createAcroForm;
    }

    /**
     * Sets the createAcroForm value.
     * <p>
     * If createAcroForm is set, then when the form is encountered in HTML, AcroForm will be created, otherwise
     * a visually identical, but not functional element will be created. Please bare in mind that the created
     * Acroform may visually differ a bit from the HTML one.
     * <p>
     * When enabling this acroform creation, it will disable the immediateFlushing property.
     *
     * @param createAcroForm true if an AcroForm needs to be created
     *
     * @return the {@link ConverterProperties} instance
     */
    public ConverterProperties setCreateAcroForm(boolean createAcroForm) {
        this.createAcroForm = createAcroForm;
        return this;
    }

    /**
     * Gets the outline handler.
     * <p>
     * If outlineHandler is specified, then outlines will be created in the PDF
     * for HTML tags specified in outlineHandler.
     * <p>
     * Please note that {@link OutlineHandler} is not thread safe, thus
     * as soon as you have set this property, this {@link ConverterProperties} instance cannot be used in
     * converting multiple HTMLs simultaneously.
     *
     * @return the outline handler
     */
    public OutlineHandler getOutlineHandler() {
        return outlineHandler;
    }

    /**
     * Sets the outline handler.
     * <p>
     * If outlineHandler is specified, then outlines will be created in the PDF
     * for HTML tags specified in outlineHandler.
     * <p>
     * Please note that {@link OutlineHandler} is not thread safe, thus
     * as soon as you have set this property, this {@link ConverterProperties} instance cannot be used in
     * converting multiple HTMLs simultaneously.
     *
     * @param outlineHandler the outline handler
     * @return the {@link ConverterProperties} instance
     */
    public ConverterProperties setOutlineHandler(OutlineHandler outlineHandler) {
        this.outlineHandler = outlineHandler;
        return this;
    }

    /**
     * Gets the encoding charset.
     * <p>
     * Charset is used to correctly decode an HTML file.
     *
     * @return the charset
     */
    public String getCharset() {
        return charset;
    }

    /**
     * Sets the encoding charset.
     * <p>
     * Charset is used to correctly decode an HTML file.
     *
     * @param charset the charset
     * @return the {@link ConverterProperties} instance
     */
    public ConverterProperties setCharset(String charset) {
        this.charset = charset;
        return this;
    }

    /**
     * Sets pdf document output intent (final destination device) to reproduce the color in the PDF.
     * Required parameter, when converting to pdf/a one have to specify an explicit output intent.
     *
     * <p>
     * Note, output intent isn't applicable for HtmlConverter#convertToElements methods
     * (e.g. {@link HtmlConverter#convertToElements(InputStream, ConverterProperties)})
     *
     * @param outputIntent a {@link PdfOutputIntent} instance
     *
     * @return the {@link ConverterProperties} instance
     */
    public ConverterProperties setDocumentOutputIntent(PdfOutputIntent outputIntent) {
        this.outputIntent = outputIntent;
        return this;
    }

    /**
     * Sets the generation and strictness level of the PDF/A that must be followed.
     * Required parameter, when converting to pdf/a one have to specify an explicit pdf/a conformance level.
     *
     * @param conformanceLevel a {@link PdfAConformanceLevel} constant
     *
     * @return the {@link ConverterProperties} instance
     */
    public ConverterProperties setPdfAConformanceLevel(PdfAConformanceLevel conformanceLevel) {
        this.conformanceLevel = conformanceLevel;
        return this;
    }

    /**
     * Gets pdf document output intent (final destination device) to reproduce the color in the PDF.
     *
     * <p>
     * Note, output intent isn't applicable for HtmlConverter#convertToElements methods
     * (e.g. {@link HtmlConverter#convertToElements(InputStream, ConverterProperties)})
     *
     * @return pdf output intent
     */
    public PdfOutputIntent getDocumentOutputIntent() {
        return outputIntent;
    }

    /**
     * Gets the generation and strictness level of the PDF/A that must be followed.
     *
     * @return pdf/a conformance level
     */
    public PdfAConformanceLevel getConformanceLevel() {
        return conformanceLevel;
    }

    /**
     * Checks if immediateFlush is set.
     * <p>
     * This is used for {@link com.itextpdf.html2pdf.HtmlConverter#convertToDocument} methods and will be
     * overwritten to false if a page-counter declaration is present in the CSS of the HTML being converted.
     * Has no effect when used in conjunction with {@link com.itextpdf.html2pdf.HtmlConverter#convertToPdf}
     * or {@link com.itextpdf.html2pdf.HtmlConverter#convertToElements}.
     *
     * @return true if immediateFlush is set, false if not
     */
    public boolean isImmediateFlush() {
        return immediateFlush;
    }

    /**
     * Set the immediate flush property of the layout document.
     * <p>
     * This is used for {@link com.itextpdf.html2pdf.HtmlConverter#convertToDocument} methods and will be
     * overwritten to false if a page-counter declaration is present in the CSS of the HTML being converted.
     * Has no effect when used in conjunction with {@link com.itextpdf.html2pdf.HtmlConverter#convertToPdf}
     * or {@link com.itextpdf.html2pdf.HtmlConverter#convertToElements}.
     *
     * @param immediateFlush the immediate flush value
     * @return the {@link ConverterProperties} instance
     */
    public ConverterProperties setImmediateFlush(boolean immediateFlush) {
        this.immediateFlush = immediateFlush;
        return this;
    }

    /**
     * Gets html meta info.
     * <p>
     * This meta info will be used to determine event origin.
     *
     * @return converter's {@link IMetaInfo}
     */
    IMetaInfo getEventMetaInfo() {
        return metaInfo == null ? HtmlConverter.createPdf2HtmlMetaInfo() : metaInfo;
    }

    /**
     * Sets html meta info.
     * <p>
     * This meta info will be used to determine event origin.
     *
     * @param metaInfo meta info to set
     * @return the {@link ConverterProperties} instance
     */
    public ConverterProperties setEventMetaInfo(IMetaInfo metaInfo) {
        this.metaInfo = metaInfo;
        return this;
    }

    /**
     * check if continuous container is enabled.
     *
     * @return true if enabled, false otherwise
     */
    public boolean isContinuousContainerEnabled() {
        return continuousContainerEnabled;
    }

    /**
     * Sets continuous container support.
     *
     * @param value true to set continuous container, false otherwise
     * @return the {@link ConverterProperties} instance
     */
    public ConverterProperties setContinuousContainerEnabled(boolean value) {
        continuousContainerEnabled = value;
        return this;
    }
}
