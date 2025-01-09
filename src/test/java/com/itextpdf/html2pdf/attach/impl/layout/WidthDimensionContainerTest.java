/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2025 Apryse Group NV
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
package com.itextpdf.html2pdf.attach.impl.layout;

import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.styledxmlparser.css.CssContextNode;
import com.itextpdf.styledxmlparser.node.INode;
import com.itextpdf.test.ExtendedITextTest;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

@Tag("UnitTest")
public class WidthDimensionContainerTest extends ExtendedITextTest {

    @Test
    public void minFixContentDimensionTest() {
        INode iNode = null;
        Map<String, String> styles = new HashMap<>();
        styles.put("width", "20pt");

        CssContextNode cssContextNode = new CssContextNode(iNode) {
            @Override
            public INode parentNode() {
                return super.parentNode();
            }
        };

        cssContextNode.setStyles(styles);
        Paragraph paragraph = new Paragraph("Paragraph");

        WidthDimensionContainer widthDimensionContainer = new WidthDimensionContainer(cssContextNode, 500,
                paragraph.createRendererSubTree(), 0);

        Assertions.assertEquals(widthDimensionContainer.minContentDimension, 20, 0.0);
    }
}
