/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2022 iText Group NV
    Authors: iText Software.

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

import com.itextpdf.io.logs.IoLogMessageConstant;
import com.itextpdf.io.font.otf.Glyph;
import com.itextpdf.io.font.otf.GlyphLine;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.renderer.IRenderer;
import com.itextpdf.layout.renderer.TextRenderer;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import com.itextpdf.test.annotations.type.UnitTest;

import java.io.IOException;
import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(UnitTest.class)
public class PageCountRendererTest extends ExtendedITextTest {

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = IoLogMessageConstant.GET_NEXT_RENDERER_SHOULD_BE_OVERRIDDEN)
    })
    public void getNextRendererShouldBeOverriddenTest() {
        PageCountRenderer pageCountRenderer = new PageCountRenderer(new PageCountElement()) {
            // Nothing is overridden
        };

        Assert.assertEquals(PageCountRenderer.class, pageCountRenderer.getNextRenderer().getClass());
    }

    @Test
    @LogMessages(messages = {
            @LogMessage(messageTemplate = IoLogMessageConstant.CREATE_COPY_SHOULD_BE_OVERRIDDEN)
    })
    public void createCopyShouldBeOverriddenTest() throws IOException {
        PageCountRenderer pageCountRenderer = new CustomPageCountRenderer(new PageCountElement());

        Assert.assertEquals(CustomPageCountRenderer.class, pageCountRenderer.getNextRenderer().getClass());

        // This test checks for the log message being sent, so we should get there
        Assert.assertTrue(true);
    }

    static class CustomPageCountRenderer extends PageCountRenderer {
        public CustomPageCountRenderer(PageCountElement textElement) {
            super(textElement);
        }

        @Override
        public IRenderer getNextRenderer() {
            try {
                // In Java protected methods could be accessed within the same package as default ones,
                // but .NET works differently. Hence to test a log about #copyText being not overridden
                // we need to call it from inside the class.
                TextRenderer copy = createCopy(new GlyphLine(new ArrayList<Glyph>()),
                        PdfFontFactory.createFont());
                Assert.assertNotNull(copy);
            } catch (IOException e) {
                Assert.fail("We do not expect PdfFontFactory#createFont() to throw an exception here.");
            }
            return new CustomPageCountRenderer((PageCountElement) this.modelElement);
        }
    }
}
