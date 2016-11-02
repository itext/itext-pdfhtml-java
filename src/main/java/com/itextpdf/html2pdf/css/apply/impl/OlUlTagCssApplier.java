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
package com.itextpdf.html2pdf.css.apply.impl;

import com.itextpdf.html2pdf.attach.ITagWorker;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.css.apply.ICssApplier;
import com.itextpdf.html2pdf.css.apply.util.BackgroundApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.ListStyleApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.MarginApplierUtil;
import com.itextpdf.html2pdf.css.apply.util.PaddingApplierUtil;
import com.itextpdf.html2pdf.html.TagConstants;
import com.itextpdf.html2pdf.html.node.IElement;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.element.List;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.ListNumberingType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

public class OlUlTagCssApplier implements ICssApplier {

    @Override
    public void apply(ProcessorContext context, IElement element, ITagWorker tagWorker) {
        if (!(tagWorker.getElementResult() instanceof List)) {
            return;
        }
        Map<String, String> css = element.getStyles();

        List list = (List) tagWorker.getElementResult();
        String elementName = element.name();
        // Default style
        if (TagConstants.UL.equals(elementName)) {
            setDiscStyle(list);
        } else if (TagConstants.OL.equals(elementName)) {
            list.setListSymbol(ListNumberingType.DECIMAL);
        }

        //TODO problems with Pdf/A conversion. Avoid ZapfDingBats, Symbol font
        String style = css.get(CssConstants.LIST_STYLE_TYPE);
        if (CssConstants.DISC.equals(style)) {
            setDiscStyle(list);
        } else if (CssConstants.CIRCLE.equals(style)) {
            setCircleStyle(list);
        } else if (CssConstants.SQUARE.equals(style)) {
            setSquareStyle(list);
        } else if (CssConstants.DECIMAL.equals(style)) {
            list.setListSymbol(ListNumberingType.DECIMAL);
        } else if (CssConstants.DECIMAL_LEADING_ZERO.equals(style)) {
            list.setListSymbol(ListNumberingType.DECIMAL_LEADING_ZERO);
        } else if (CssConstants.UPPER_ALPHA.equals(style) || CssConstants.UPPER_LATIN.equals(style)) {
            list.setListSymbol(ListNumberingType.ENGLISH_UPPER);
        } else if (CssConstants.LOWER_ALPHA.equals(style) || CssConstants.LOWER_LATIN.equals(style)) {
            list.setListSymbol(ListNumberingType.ENGLISH_LOWER);
        } else if (CssConstants.UPPER_ROMAN.equals(style)) {
            list.setListSymbol(ListNumberingType.ROMAN_UPPER);
        } else if (CssConstants.LOWER_ROMAN.equals(style)) {
            list.setListSymbol(ListNumberingType.ROMAN_LOWER);
        } else if (CssConstants.LOWER_GREEK.equals(style)) {
            list.setListSymbol(ListNumberingType.GREEK_LOWER);
        } else if (CssConstants.NONE.equals(style)) {
            list.setListSymbol(new Text(""));
        } else {
            Logger logger = LoggerFactory.getLogger(OlUlTagCssApplier.class);
            logger.error("Not supported list style type: " + style);
        }

        ListStyleApplierUtil.applyListStyleImageProperty(css, context, list);
        BackgroundApplierUtil.applyBackground(css, context, list);
        MarginApplierUtil.applyMargins(css, context, list);
        PaddingApplierUtil.applyPaddings(css, context, list);

        // TODO DEVSIX-923
        list.setPaddingLeft(0);
    }

    private void setDiscStyle(List list) {
        Text symbol = new Text(String.valueOf((char)108)).setFont(createZapfDingBatsSafe());
        symbol.setTextRise(1.5f);
        symbol.setFontSize(4.5f);
        list.setListSymbol(symbol);
        list.setSymbolIndent(7.75f);
    }

    private void setSquareStyle(List list) {
        Text symbol = new Text(String.valueOf((char)110)).setFont(createZapfDingBatsSafe());
        symbol.setTextRise(1.5f);
        symbol.setFontSize(4.5f);
        list.setListSymbol(symbol);
        list.setSymbolIndent(7.75f);
    }

    private void setCircleStyle(List list) {
        Text symbol = new Text(String.valueOf((char)109)).setFont(createZapfDingBatsSafe());
        symbol.setTextRise(1.5f);
        symbol.setFontSize(4.5f);
        list.setListSymbol(symbol);
        list.setSymbolIndent(7.75f);
    }

    private PdfFont createZapfDingBatsSafe() {
        try {
            return PdfFontFactory.createFont(FontConstants.ZAPFDINGBATS);
        } catch (IOException exc) {
            Logger logger = LoggerFactory.getLogger(OlUlTagCssApplier.class);
            logger.error("Unable to create ZapfDingBats font", exc);
            return null;
        }
    }

}
