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

package com.itextpdf.html2pdf;

import com.itextpdf.html2pdf.attach.ITagWorkerFactory;
import com.itextpdf.html2pdf.attach.impl.OutlineHandler;
import com.itextpdf.html2pdf.css.apply.ICssApplierFactory;
import com.itextpdf.html2pdf.css.media.MediaDeviceDescription;
import com.itextpdf.layout.font.FontProvider;

/**
 * Properties that will be used by the converter.
 */
public class ConverterProperties {

    /** The media device description. */
    private MediaDeviceDescription mediaDeviceDescription;
    
    /** The font provider. */
    private FontProvider fontProvider;
    
    /** The tag worker factory. */
    private ITagWorkerFactory tagWorkerFactory;
    
    /** The CSS applier factory. */
    private ICssApplierFactory cssApplierFactory;
    
    /** The outline handler. */
    private OutlineHandler outlineHandler;
    
    /** The base URI. */
    private String baseUri;
    
    /** Indicates whether an AcroForm should be created. */
    private boolean createAcroForm = false;

    /** Character set used in conversion of input streams */
    private String charset;

    /** Indicates whether the document should be opened in immediate flush or not **/
    private boolean immediateFlush = true;

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
        this.createAcroForm = other.createAcroForm;
        this.outlineHandler = other.outlineHandler;
        this.charset = other.charset;
    }

    /**
     * Gets the media device description.
     *
     * @return the media device description
     */
    public MediaDeviceDescription getMediaDeviceDescription() {
        return mediaDeviceDescription;
    }

    /**
     * Sets the media device description.
     *
     * @param mediaDeviceDescription the media device description
     * @return the ConverterProperties instance
     */
    public ConverterProperties setMediaDeviceDescription(MediaDeviceDescription mediaDeviceDescription) {
        this.mediaDeviceDescription = mediaDeviceDescription;
        return this;
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
     * Sets the font provider. Please note that {@link FontProvider} instances cannot be reused across several documents
     * and thus as soon as you set this property, this {@link ConverterProperties} instance becomes only useful for a single
     * HTML conversion.
     *
     * @param fontProvider the font provider
     * @return the ConverterProperties instance
     */
    public ConverterProperties setFontProvider(FontProvider fontProvider) {
        this.fontProvider = fontProvider;
        return this;
    }

    /**
     * Gets the TagWorkerFactory instance.
     *
     * @return the TagWorkerFactory
     */
    public ITagWorkerFactory getTagWorkerFactory() {
        return tagWorkerFactory;
    }

    /**
     * Sets the TagWorkerFactory.
     *
     * @param tagWorkerFactory the TagWorkerFactory
     * @return the ConverterProperties instance
     */
    public ConverterProperties setTagWorkerFactory(ITagWorkerFactory tagWorkerFactory) {
        this.tagWorkerFactory = tagWorkerFactory;
        return this;
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
     * Sets the CSS applier factory.
     *
     * @param cssApplierFactory the CSS applier factory
     * @return the ConverterProperties instance
     */
    public ConverterProperties setCssApplierFactory(ICssApplierFactory cssApplierFactory) {
        this.cssApplierFactory = cssApplierFactory;
        return this;
    }

    /**
     * Gets the base URI.
     *
     * @return the base URI
     */
    public String getBaseUri() {
        return baseUri;
    }

    /**
     * Sets the base URI.
     *
     * @param baseUri the base URI
     * @return the ConverterProperties instance
     */
    public ConverterProperties setBaseUri(String baseUri) {
        this.baseUri = baseUri;
        return this;
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
     * Sets the createAcroForm value.
     *
     * @param createAcroForm true if an AcroForm needs to be created
     * @return the ConverterProperties instance
     */
    public ConverterProperties setCreateAcroForm(boolean createAcroForm) {
        this.createAcroForm = createAcroForm;
        return this;
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
     * Sets the outline handler. Please note that {@link OutlineHandler} is not thread safe, thus
     * as soon as you have set this property, this {@link ConverterProperties} instance cannot be used in converting multiple
     * HTMLs simultaneously.
     *
     * @param outlineHandler the outline handler
     * @return the ConverterProperties instance
     */
    public ConverterProperties setOutlineHandler(OutlineHandler outlineHandler) {
        this.outlineHandler = outlineHandler;
        return this;
    }

    /**
     * Gets the encoding charset.
     *
     * @return the charset
     */
    public String getCharset() {
        return charset;
    }

    /**
     * Sets the encoding charset.
     *
     * @param charset the charset
     * @return the ConverterProperties instance
     */
    public ConverterProperties setCharset(String charset) {
        this.charset = charset;
        return this;
    }

    /**
     * Checks if immediateFlush is set
     * @return true if immediateFlush is set, false if not.
     */
    public boolean isImmediateFlush(){
        return immediateFlush;
    }

    /**
     * set the immediate flush property of the layout document
     * This is used for convertToDocument methods and will be overwritten to
     * false if a page-counter declaration is present in the CSS of the HTML being
     * converted.
     * Has no effect when used in conjunction with convertToPdf or convertToElements
     * @param immediateFlush the immediate flush value
     * @return the ConverterProperties
     */
    public ConverterProperties setImmediateFlush(boolean immediateFlush){
        this.immediateFlush = immediateFlush;
        return this;
    }
}
