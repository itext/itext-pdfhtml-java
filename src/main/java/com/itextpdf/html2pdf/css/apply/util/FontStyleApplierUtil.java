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
package com.itextpdf.html2pdf.css.apply.util;

import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.css.apply.impl.PTagCssApplier;
import com.itextpdf.html2pdf.css.util.CssUtils;
import com.itextpdf.kernel.color.WebColors;
import com.itextpdf.kernel.pdf.canvas.PdfCanvasConstants;
import com.itextpdf.layout.ElementPropertyContainer;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.property.Property;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

public final class FontStyleApplierUtil {

    private static final Logger logger = LoggerFactory.getLogger(PTagCssApplier.class);

    private FontStyleApplierUtil() {
    }

    public static void applyFontStyles(Map<String, String> cssProps, ProcessorContext context, IPropertyContainer element) {
        if (cssProps.get(CssConstants.FONT_FAMILY) != null) {
            try {
                element.setProperty(Property.FONT, context.getFontResolver().getFont(cssProps.get(CssConstants.FONT_FAMILY)));
            } catch (IOException exc) {
                logger.error("Could not load font", exc);
            }
        }
        float em = CssUtils.parseAbsoluteLength(cssProps.get(CssConstants.FONT_SIZE));
        if (em != 0) {
            element.setProperty(Property.FONT_SIZE, em);
        }
        if (cssProps.get(CssConstants.FONT_WEIGHT) != null) {
            // TODO move to font selection mechanism
            String fontWeight = cssProps.get(CssConstants.FONT_WEIGHT);
            if (CssConstants.BOLD.equalsIgnoreCase(fontWeight)) {
                element.setProperty(Property.BOLD_SIMULATION, true);
            } else if (CssConstants.NORMAL.equalsIgnoreCase(fontWeight)) {
                element.setProperty(Property.BOLD_SIMULATION, false);
            }
        }
        if (cssProps.get(CssConstants.COLOR) != null) {
            element.setProperty(Property.FONT_COLOR, WebColors.getRGBColor(cssProps.get(CssConstants.COLOR)));
        }

        String align = cssProps.get(CssConstants.TEXT_ALIGN);
        if (CssConstants.LEFT.equals(align)) {
            element.setProperty(Property.TEXT_ALIGNMENT, TextAlignment.LEFT);
        } else if (CssConstants.RIGHT.equals(align)) {
            element.setProperty(Property.TEXT_ALIGNMENT, TextAlignment.RIGHT);
        } else if (CssConstants.CENTER.equals(align)) {
            element.setProperty(Property.TEXT_ALIGNMENT, TextAlignment.CENTER);
        }

        String textDecoration = cssProps.get(CssConstants.TEXT_DECORATION);
        ElementPropertyContainer elementPropertyContainer = null;
        if (element instanceof ElementPropertyContainer) {
            elementPropertyContainer = (ElementPropertyContainer) element;
        }
        if (elementPropertyContainer != null) {
            if (CssConstants.BLINK.equals(textDecoration)) {
                logger.error("text-decoration: blink not supported");
            } else if (CssConstants.LINE_THROUGH.equals(textDecoration)) {
                elementPropertyContainer.setUnderline(null, .75f, 0, 0, 1/4f, PdfCanvasConstants.LineCapStyle.BUTT);
            } else if (CssConstants.OVERLINE.equals(textDecoration)) {
                elementPropertyContainer.setUnderline(null, .75f, 0, 0, 9/10f, PdfCanvasConstants.LineCapStyle.BUTT);
            } else if (CssConstants.UNDERLINE.equals(textDecoration)) {
                elementPropertyContainer.setUnderline(null, .75f, 0, 0, -1/10f, PdfCanvasConstants.LineCapStyle.BUTT);
            } else if (CssConstants.NONE.equals(textDecoration)) {
                elementPropertyContainer.setProperty(Property.UNDERLINE, null);
            }
        }

        // TODO text-transform

        String textIndent = cssProps.get(CssConstants.TEXT_INDENT);
        if (textIndent != null) {
            UnitValue textIndentValue = CssUtils.parseLengthValueToPt(textIndent, em);
            if (textIndentValue.isPointValue()) {
                element.setProperty(Property.FIRST_LINE_INDENT, textIndentValue.getValue());
            } else {
                logger.error("text-indent in percents is not supported");
            }
        }

        String letterSpacing = cssProps.get(CssConstants.LETTER_SPACING);
        if (letterSpacing != null) {
            UnitValue letterSpacingValue = CssUtils.parseLengthValueToPt(letterSpacing, em);
            if (letterSpacingValue.isPointValue()) {
                element.setProperty(Property.CHARACTER_SPACING, letterSpacingValue.getValue());
            } else {
                // browsers ignore values in percents
            }
        }

    }

}
