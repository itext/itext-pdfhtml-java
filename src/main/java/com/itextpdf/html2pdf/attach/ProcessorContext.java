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
import com.itextpdf.html2pdf.css.apply.impl.DefaultCssApplierFactory;
import com.itextpdf.html2pdf.css.apply.ICssApplierFactory;
import com.itextpdf.html2pdf.css.media.MediaDeviceDescription;
import com.itextpdf.html2pdf.css.resolve.CssContext;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.html2pdf.resolver.form.FormFieldNameResolver;
import com.itextpdf.html2pdf.resolver.resource.ResourceResolver;
import com.itextpdf.io.font.FontProgram;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.font.FontInfo;
import com.itextpdf.layout.font.FontProvider;
import com.itextpdf.layout.font.FontSet;

public class ProcessorContext {

    private FontProvider fontProvider;
    private FontSet tempFonts;
    private ResourceResolver resourceResolver;
    private MediaDeviceDescription deviceDescription;
    private ITagWorkerFactory tagWorkerFactory;
    private ICssApplierFactory cssApplierFactory;
    private String baseUri;
    private boolean createAcroForm;
    private FormFieldNameResolver formFieldNameResolver;
    private OutlineHandler outlineHandler;

    // Variable fields
    private State state;
    private CssContext cssContext;
    private PdfDocument pdfDocument;

    public ProcessorContext(ConverterProperties converterProperties) {
        if (converterProperties == null) {
            converterProperties = new ConverterProperties();
        }
        state = new State();

        deviceDescription = converterProperties.getMediaDeviceDescription();
        if (deviceDescription == null) {
            deviceDescription = MediaDeviceDescription.createDefault();
        }

        fontProvider = converterProperties.getFontProvider();
        if (fontProvider == null) {
            fontProvider = new DefaultFontProvider();
        }

        tagWorkerFactory = converterProperties.getTagWorkerFactory();
        if (tagWorkerFactory == null) {
            tagWorkerFactory = new DefaultTagWorkerFactory();
        }

        cssApplierFactory = converterProperties.getCssApplierFactory();
        if (cssApplierFactory == null) {
            cssApplierFactory = new DefaultCssApplierFactory();
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

        createAcroForm = converterProperties.isCreateAcroForm();
        formFieldNameResolver = new FormFieldNameResolver();
    }

    public void setFontProvider(FontProvider fontProvider) {
        this.fontProvider = fontProvider;
    }

    public State getState() {
        return state;
    }

    public PdfDocument getPdfDocument() {
        return pdfDocument;
    }

    public FontProvider getFontProvider() {
        return fontProvider;
    }

    public FontSet getTempFonts() {
        return tempFonts;
    }

    public ResourceResolver getResourceResolver() {
        return resourceResolver;
    }

    public MediaDeviceDescription getDeviceDescription() {
        return deviceDescription;
    }

    public ITagWorkerFactory getTagWorkerFactory() {
        return tagWorkerFactory;
    }

    public ICssApplierFactory getCssApplierFactory() {
        return cssApplierFactory;
    }

    public CssContext getCssContext() {
        return cssContext;
    }

    public boolean isCreateAcroForm() {
        return createAcroForm;
    }

    public FormFieldNameResolver getFormFieldNameResolver() {
        return formFieldNameResolver;
    }

    public OutlineHandler getOutlineHandler() {
        return outlineHandler;
    }

    /**
     * Add temporary font from @font-face.
     */
    public void addTemporaryFont(FontInfo fontInfo, String alias) {
        if (tempFonts == null) tempFonts = new FontSet();
        tempFonts.addFont(fontInfo, alias);
    }

    /**
     * Add temporary font from @font-face.
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

    public void reset() {
        this.pdfDocument = null;
        this.state = new State();
        this.resourceResolver.resetCache();
        this.cssContext = new CssContext();
        this.formFieldNameResolver.reset();
        //Reset font provider. PdfFonts shall be reseted.
        this.fontProvider = new FontProvider(this.fontProvider.getFontSet());
        this.tempFonts = null;
    }

    public void reset(PdfDocument pdfDocument) {
        reset();
        this.pdfDocument = pdfDocument;
    }
}
