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
import com.itextpdf.html2pdf.css.util.CssUtils;
import com.itextpdf.html2pdf.html.WebColors;
import com.itextpdf.html2pdf.html.node.IElement;
import com.itextpdf.layout.border.*;
import com.itextpdf.layout.property.Background;
import com.itextpdf.layout.property.Property;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;

import java.util.Map;

public class TableTagCssApplier implements ICssApplier {
    @Override
    public void apply(ProcessorContext context, IElement element, ITagWorker worker) {
        Map<String, String> cssProps = element.getStyles();

        if (cssProps.get(CssConstants.BACKGROUND_COLOR) != null) {
            Background background = new Background(WebColors.getRGBColor(cssProps.get(CssConstants.BACKGROUND_COLOR)));
            worker.getElementResult().setProperty(Property.BACKGROUND, background);
        }
        if (cssProps.get(CssConstants.BORDER_WIDTH) != null) {
            Float borderWidth = CssUtils.parseAbsoluteLength(cssProps.get(CssConstants.BORDER_WIDTH));
            String borderStyle = cssProps.get(CssConstants.BORDER_STYLE);
            String borderColor = cssProps.get(CssConstants.BORDER_COLOR);

            if (borderWidth != null && borderStyle != null) {
                Border border;
                switch (borderStyle.toLowerCase()) {
                    case CssConstants.SOLID:
                        border = new SolidBorder(borderWidth);
                        break;
                    case CssConstants.DASHED:
                        border = new DashedBorder(borderWidth);
                        break;
                    case CssConstants.DOTTED:
                        border = new DottedBorder(borderWidth);
                        break;
                    case CssConstants.DOUBLE:
                        border = new DoubleBorder(borderWidth);
                        break;
                    case CssConstants.GROOVE:
                        border = new GrooveBorder(borderWidth);
                        break;
                    case CssConstants.RIDGE:
                        border = new RidgeBorder(borderWidth);
                        break;
                    case CssConstants.INSET:
                        border = new InsetBorder(borderWidth);
                        break;
                    case CssConstants.OUTSET:
                        border = new OutsetBorder(borderWidth);
                        break;
                    default:
                        border = null;
                        break;
                }
                if (border != null) {
                    if (borderColor != null) {
                        border.setColor(WebColors.getRGBColor(borderColor));
                    }
                }
            }
        }
        if (cssProps.get(CssConstants.HEIGHT) != null) {
            worker.getElementResult().setProperty(Property.HEIGHT, CssUtils.parseAbsoluteLength(cssProps.get(CssConstants.HEIGHT)));
        }

        if (cssProps.get(CssConstants.TEXT_ALIGN) != null) {
            String align = cssProps.get(CssConstants.TEXT_ALIGN);
            switch (align) {
                case CssConstants.LEFT:
                    worker.getElementResult().setProperty(Property.TEXT_ALIGNMENT, TextAlignment.LEFT);
                    break;
                case CssConstants.RIGHT:
                    worker.getElementResult().setProperty(Property.TEXT_ALIGNMENT, TextAlignment.RIGHT);
                    break;
                case CssConstants.CENTER:
                    worker.getElementResult().setProperty(Property.TEXT_ALIGNMENT, TextAlignment.CENTER);
                    break;
                default: break;
            }
        }

        if (cssProps.get(CssConstants.WIDTH) != null) {
            worker.getElementResult().setProperty(Property.WIDTH, UnitValue.createPointValue(CssUtils.parseAbsoluteLength(cssProps.get(CssConstants.WIDTH))));
        }
    }
}
