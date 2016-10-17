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
import com.itextpdf.html2pdf.attach.impl.tags.ImageTagWorker;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.css.apply.ICssApplier;
import com.itextpdf.html2pdf.html.WebColors;
import com.itextpdf.html2pdf.html.node.INode;
import com.itextpdf.layout.border.*;
import com.itextpdf.layout.property.Property;
import com.itextpdf.layout.property.UnitValue;

import java.util.Map;

public class ImgTagCssApplier implements ICssApplier {
    @Override
    public void apply(ProcessorContext context, INode node, ITagWorker worker) {
        Map<String, String> cssProps = context.getCssResolver().resolveStyles(node);

        if (cssProps.get(CssConstants.ALIGN) != null) {
            String align = cssProps.get(CssConstants.ALIGN);
            switch (align) {
                case "left":
                    worker.getElementResult().setProperty(Property.HORIZONTAL_ALIGNMENT, Property.LEFT);
                    break;
                case "right":
                    worker.getElementResult().setProperty(Property.HORIZONTAL_ALIGNMENT, Property.RIGHT);
                    break;
                case "middle":
                    break;
                case "bottom":
                    worker.getElementResult().setProperty(Property.VERTICAL_ALIGNMENT, Property.BOTTOM);
                    break;
                case "top":
                    worker.getElementResult().setProperty(Property.VERTICAL_ALIGNMENT, Property.TOP);
                    break;
                default: break;
            }

        }
        if (cssProps.get(CssConstants.BORDER) != null) {
            worker.getElementResult().setProperty(Property.BORDER, new SolidBorder(Float.valueOf(cssProps.get(CssConstants.BORDER))));
        }
        if (cssProps.get(CssConstants.BORDER_WIDTH) != null) {
            Float borderWidth = Float.valueOf(cssProps.get(CssConstants.BORDER_WIDTH));
            String borderStyle = cssProps.get(CssConstants.BORDER_STYLE);
            String borderColor = cssProps.get(CssConstants.BORDER_COLOR);

            if (borderWidth != null && borderStyle != null) {
                Border border;
                switch (borderStyle.toLowerCase()) {
                    case "solid":
                        border = new SolidBorder(borderWidth);
                        break;
                    case "dashed":
                        border = new DashedBorder(borderWidth);
                        break;
                    case "dotted":
                        border = new DottedBorder(borderWidth);
                        break;
                    case "double":
                        border = new DoubleBorder(borderWidth);
                        break;
                    case "groove":
                        border = new GrooveBorder(borderWidth);
                        break;
                    case "ridge":
                        border = new RidgeBorder(borderWidth);
                        break;
                    case "inset":
                        border = new InsetBorder(borderWidth);
                        break;
                    case "outset":
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
            ((ImageTagWorker)worker).getImage().setAutoScale(false);
            ((ImageTagWorker)worker).getImage().setProperty(Property.HEIGHT, UnitValue.createPointValue(Float.valueOf(cssProps.get(CssConstants.HEIGHT))));
        }
        if (cssProps.get(CssConstants.HSPACE) != null) {
            float horizontalSpace = Float.valueOf(cssProps.get(CssConstants.HSPACE));
            worker.getElementResult().setProperty(Property.MARGIN_LEFT, horizontalSpace);
            worker.getElementResult().setProperty(Property.MARGIN_RIGHT, horizontalSpace);
        }
        if (cssProps.get(CssConstants.VSPACE) != null) {
            float verticalSpace = Float.valueOf(cssProps.get(CssConstants.VSPACE));
            worker.getElementResult().setProperty(Property.MARGIN_TOP, verticalSpace);
            worker.getElementResult().setProperty(Property.MARGIN_BOTTOM, verticalSpace);
        }
        if (cssProps.get(CssConstants.WIDTH) != null) {
            ((ImageTagWorker)worker).getImage().setAutoScale(false);
            ((ImageTagWorker)worker).getImage().setProperty(Property.WIDTH, UnitValue.createPointValue(Float.valueOf(cssProps.get(CssConstants.WIDTH))));
        }
    }
}
