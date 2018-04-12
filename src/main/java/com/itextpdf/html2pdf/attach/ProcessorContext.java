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
package com.itextpdf.html2pdf.attach;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.attach.impl.DefaultTagWorkerFactory;
import com.itextpdf.html2pdf.attach.impl.OutlineHandler;
import com.itextpdf.html2pdf.attach.impl.LinkContext;
import com.itextpdf.html2pdf.css.apply.ICssApplierFactory;
import com.itextpdf.html2pdf.css.apply.impl.DefaultCssApplierFactory;
import com.itextpdf.html2pdf.css.media.MediaDeviceDescription;
import com.itextpdf.html2pdf.css.resolve.CssContext;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.html2pdf.resolver.form.FormFieldNameResolver;
import com.itextpdf.html2pdf.resolver.form.RadioCheckResolver;
import com.itextpdf.html2pdf.resolver.resource.ResourceResolver;
import com.itextpdf.io.font.FontProgram;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.font.FontInfo;
import com.itextpdf.layout.font.FontProvider;
import com.itextpdf.layout.font.FontSet;

/**
 * Keeps track of the context of the processor.
 */
public class ProcessorContext {

    /** The font provider. */
    private FontProvider fontProvider;

    /** Temporary set of fonts used in the PDF. */
    private FontSet tempFonts;

    /** The resource resolver. */
    private ResourceResolver resourceResolver;

    /** The device description. */
    private MediaDeviceDescription deviceDescription;

    /** The tag worker factory. */
    private ITagWorkerFactory tagWorkerFactory;

    /** The CSS applier factory. */
    private ICssApplierFactory cssApplierFactory;

    /** The base URI. */
    private String baseUri;

    /** Indicates whether an AcroForm needs to be created. */
    private boolean createAcroForm;

    /** The form field name resolver. */
    private FormFieldNameResolver formFieldNameResolver;

    /** The radio check resolver. */
    private RadioCheckResolver radioCheckResolver;

    /** The outline handler. */
    private OutlineHandler outlineHandler;

    /** Indicates whether the document should be opened in immediate flush or not **/
    private boolean immediateFlush;

    // Variable fields

    /** The state. */
    private State state;

    /** The CSS context. */
    private CssContext cssContext;

    /** The link context */
    private LinkContext linkContext;

    /** The PDF document. */
    private PdfDocument pdfDocument;

    /**
     * Instantiates a new {@link ProcessorContext} instance.
     *
     * @param converterProperties a {@link ConverterProperties} instance
     */
    public ProcessorContext(ConverterProperties converterProperties) {
        if (converterProperties == null) {
            converterProperties = new ConverterProperties();
        }
        state = new State();

        deviceDescription = converterProperties.getMediaDeviceDescription();
        if (deviceDescription == null) {
            deviceDescription = MediaDeviceDescription.getDefault();
        }

        fontProvider = converterProperties.getFontProvider();
        if (fontProvider == null) {
            fontProvider = new DefaultFontProvider();
        }

        tagWorkerFactory = converterProperties.getTagWorkerFactory();
        if (tagWorkerFactory == null) {
            tagWorkerFactory = DefaultTagWorkerFactory.getInstance();
        }

        cssApplierFactory = converterProperties.getCssApplierFactory();
        if (cssApplierFactory == null) {
            cssApplierFactory = DefaultCssApplierFactory.getInstance();
        }

        baseUri = converterProperties.getBaseUri();
        if (baseUri == null) {
            baseUri = "";
        }

        outlineHandler = converterProperties.getOutlineHandler();
        if (outlineHandler == null) {
            outlineHandler = new OutlineHandler();
        }

        resourceResolver = new ResourceResolver(baseUri);

        cssContext = new CssContext();
        linkContext = new LinkContext();

        createAcroForm = converterProperties.isCreateAcroForm();
        formFieldNameResolver = new FormFieldNameResolver();
        radioCheckResolver = new RadioCheckResolver();
        immediateFlush = converterProperties.isImmediateFlush();
    }

    /**
     * Sets the font provider.
     *
     * @param fontProvider the new font provider
     */
    public void setFontProvider(FontProvider fontProvider) {
        this.fontProvider = fontProvider;
    }

    /**
     * Gets the state.
     *
     * @return the state
     */
    public State getState() {
        return state;
    }

    /**
     * Gets the PDF document.
     *
     * @return the PDF document
     */
    public PdfDocument getPdfDocument() {
        return pdfDocument;
    }

    /**
     * Gets the font provider.
     *
     * @return the font provider
     */
    public FontProvider getFontProvider() {
        return fontProvider;
    }

    /**
     * Gets the temporary set of fonts.
     *
     * @return the set of fonts
     */
    public FontSet getTempFonts() {
        return tempFonts;
    }

    /**
     * Gets the resource resolver.
     *
     * @return the resource resolver
     */
    public ResourceResolver getResourceResolver() {
        return resourceResolver;
    }

    /**
     * Gets the device description.
     *
     * @return the device description
     */
    public MediaDeviceDescription getDeviceDescription() {
        return deviceDescription;
    }

    /**
     * Gets the tag worker factory.
     *
     * @return the tag worker factory
     */
    public ITagWorkerFactory getTagWorkerFactory() {
        return tagWorkerFactory;
    }

    /**
     * Gets the CSS applier factory.
     *
     * @return the CSS applier factory
     */
    public ICssApplierFactory getCssApplierFactory() {
        return cssApplierFactory;
    }

    /**
     * Gets the CSS context.
     *
     * @return the CSS context
     */
    public CssContext getCssContext() {
        return cssContext;
    }

    /**
     * Gets the link context.
     *
     * @return the link context
     */
    public LinkContext getLinkContext() {
        return linkContext;
    }

    /**
     * Checks if is an AcroForm needs to be created.
     *
     * @return true, an AcroForm should be created
     */
    public boolean isCreateAcroForm() {
        return createAcroForm;
    }

    /**
     * Gets the form field name resolver.
     *
     * @return the form field name resolver
     */
    public FormFieldNameResolver getFormFieldNameResolver() {
        return formFieldNameResolver;
    }

    /**
     * Gets the radio check resolver.
     *
     * @return the radio check resolver
     */
    public RadioCheckResolver getRadioCheckResolver() {
        return radioCheckResolver;
    }


    /**
     * Gets the outline handler.
     *
     * @return the outline handler
     */
    public OutlineHandler getOutlineHandler() {
        return outlineHandler;
    }

    /**
     * Add temporary font from @font-face.
     *
     * @param fontInfo the font info
     * @param alias the alias
     */
    public void addTemporaryFont(FontInfo fontInfo, String alias) {
        if (tempFonts == null) tempFonts = new FontSet();
        tempFonts.addFont(fontInfo, alias);
    }

    /**
     * Add temporary font from @font-face.
     *
     * @param fontProgram the font program
     * @param encoding the encoding
     * @param alias the alias
     */
    public void addTemporaryFont(FontProgram fontProgram, String encoding, String alias) {
        if (tempFonts == null) tempFonts = new FontSet();
        tempFonts.addFont(fontProgram, encoding, alias);
    }

    /**
     * Check fonts in font provider and temporary font set.
     *
     * @return true, if there is at least one font either in FontProvider or temporary FontSet.
     * @see #addTemporaryFont(FontInfo, String)
     * @see #addTemporaryFont(FontProgram, String, String)
     */
    public boolean hasFonts() {
        return !fontProvider.getFontSet().isEmpty()
                || (tempFonts != null && !tempFonts.isEmpty());
    }

    /**
     * Resets the context.
     */
    public void reset() {
        this.pdfDocument = null;
        this.state = new State();
        this.resourceResolver.resetCache();
        this.cssContext = new CssContext();
        this.linkContext = new LinkContext();
        this.formFieldNameResolver.reset();
        //Reset font provider. PdfFonts shall be reseted.
        this.fontProvider = new FontProvider(this.fontProvider.getFontSet());
        this.tempFonts = null;
        this.outlineHandler.reset();
    }

    /**
     * Resets the context, and assigns a new PDF document.
     *
     * @param pdfDocument the new PDF document for the context
     */
    public void reset(PdfDocument pdfDocument) {
        reset();
        this.pdfDocument = pdfDocument;
    }

    /**
     * Gets the baseURI: the URI which has been set manually or the directory of the html file in case when baseURI hasn't been set manually.
     *
     * @return the baseUri
     */
    public String getBaseUri(){
        return baseUri;
    }


    /**
     * Checks if immediateFlush is set
     * @return true if immediateFlush is set, false if not.
     */
    public boolean isImmediateFlush(){
        return immediateFlush;
    }
}
