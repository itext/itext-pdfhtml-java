/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2018 iText Group NV
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
package com.itextpdf.html2pdf;

import com.itextpdf.io.util.UrlUtil;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.test.ExtendedITextTest;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.Assert;

public class ExtendedFontPropertiesTest extends ExtendedITextTest {

    private static final String HTML_TOP = "<html><head>";
    private static final String HTML_BEGIN_BODY = "</head><body>";
    private static final String HTML_BOTTOM = "</body></html>";
    private static final String TR_OPEN = "<tr>";
    private static final String TD_OPEN = "<td>";
    private static final String TD_CLOSE = "</td>";
    private static final String TR_CLOSE = "</tr>";
    private static final String TABLE_OPEN = "<table>";
    private static final String TABLE_CLOSE = "</table>";
    private static final String DOCUMENT_PREFIX = "FOR_VISUAL_CMP_";
    private static final String COLUMN_DECLARATIONS = "<col width='50'><col width='50'><col width='50'><col width='300'>";
    private static final String TH_FONT_FAMILY = "<th scope='col'>Font-family</th>";
    private static final String TH_FONT_WEIGHT = "<th scope='col'>Font-weight</th>";
    private static final String TH_FONT_STYLE = "<th scope='col'>Font-style</th>";
    private static final String TH_RESULT = "<th scope='col' >Result</th>";
    private static final String TITLE_TAG = "<title>Font Family Test</title>";
    private static final String HTML_TITLE = "<h1>Font Family Test</h1>";
    private static final String TD_STYLE = "<td style = \"";
    private static final String FONT_FAMILY = " font-family: '";
    private static final String FONT_STYLE = "; font-style: ";
    private static final String FONT_WEIGHT = "'; font-weight: ";

    public void runTest(String htmlString, String sourceFolder, String destinationFolder, String fileName, String testName) throws IOException, InterruptedException {
        String outPdf = destinationFolder + fileName + ".pdf";
        String cmpPdf = sourceFolder + "cmp_" + fileName + ".pdf";
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outPdf));
        Document doc = new Document(pdfDoc);
        byte[] bytes = htmlString.getBytes(StandardCharsets.UTF_8);

        // save to html
        generateTestHtml(destinationFolder, fileName, bytes);

        // Convert to elements
        writeToDocument(doc, bytes);
        Assert.assertNull(new CompareTool().compareByContent(outPdf, cmpPdf, destinationFolder, "diff_" + testName + "_"));
    }

    private void writeToDocument(Document doc, byte[] bytes) throws IOException {
        InputStream in = new ByteArrayInputStream(bytes);
        List<IElement> arrayList = HtmlConverter.convertToElements(in);
        for (IElement element : arrayList) {
            if (element instanceof IBlockElement) {
                doc.add((IBlockElement) element);
            }
        }
        doc.close();
    }

    private void generateTestHtml(String destinationFolder, String fileName, byte[] bytes) throws IOException {
        String htmlPath = destinationFolder + DOCUMENT_PREFIX + fileName + ".html";
        FileOutputStream out = new FileOutputStream(htmlPath);
        System.out.println("html: file:///" + UrlUtil.toNormalizedURI(htmlPath).getPath() + "\n");
        out.close();
    }

    public String buildDocumentTree(String[] fontFamilies, String[] fontWeights, String[] fontStyles, String[] cssFiles, String text) {
        StringBuilder builder = new StringBuilder();
        builder.append(HTML_TOP);
        if (cssFiles != null) {
            for (String css : cssFiles) {
                builder.append(" <link href='").append(css).append("' rel='stylesheet' type='text/css'>\n ");
            }
        }
        //Build Html top
        String styleTag = "<style>\n" +
                " th, td {text-align: center;\n" +
                " height: 45px; border: 1px solid black; }\n" +
                " table {font-family: Courier; }" +
                " </style>\n";

        builder.append(TITLE_TAG);
        builder.append(styleTag);
        builder.append(HTML_BEGIN_BODY);
        builder.append(HTML_TITLE);
        builder.append(TABLE_OPEN);
        builder.append(COLUMN_DECLARATIONS);
        builder.append(TR_OPEN);
        builder.append(TH_FONT_FAMILY);
        builder.append(TH_FONT_WEIGHT);
        builder.append(TH_FONT_STYLE);
        builder.append(TH_RESULT);
        builder.append(TR_CLOSE);

        //Build body content
        for (String name : fontFamilies) {
            for (String weight : fontWeights) {
                for (String style : fontStyles) {
                    // the tr
                    builder
                            .append(TR_OPEN);
                    // the first td
                    builder
                            .append(TD_OPEN)
                            .append(name)
                            .append(TD_CLOSE);
                    // the second td
                    builder
                            .append(TD_OPEN)
                            .append(weight)
                            .append(TD_CLOSE);
                    // the third td
                    builder
                            .append(TD_OPEN)
                            .append(style)
                            .append(TD_CLOSE);
                    // the fourth td
                    builder
                            .append(TD_STYLE)
                            .append(FONT_FAMILY)
                            .append(name)
                            .append(FONT_WEIGHT)
                            .append(weight)
                            .append(FONT_STYLE)
                            .append(style)
                            .append("\";>")
                            .append(text)
                            .append(TD_CLOSE);
                    // the tr
                    builder.append(TR_CLOSE);
                }
            }
        }
        builder.append(TABLE_CLOSE);
        builder.append(HTML_BOTTOM);
        return builder.toString();
    }
}
