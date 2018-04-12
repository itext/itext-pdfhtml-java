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
package com.itextpdf.html2pdf.css;

import com.itextpdf.html2pdf.css.media.MediaDeviceDescription;
import com.itextpdf.html2pdf.css.resolve.CssContext;
import com.itextpdf.html2pdf.css.resolve.DefaultCssResolver;
import com.itextpdf.html2pdf.css.resolve.ICssResolver;
import com.itextpdf.html2pdf.css.util.CssUtils;
import com.itextpdf.html2pdf.html.IHtmlParser;
import com.itextpdf.html2pdf.html.TagConstants;
import com.itextpdf.html2pdf.html.impl.jsoup.JsoupHtmlParser;
import com.itextpdf.html2pdf.html.node.IDocumentNode;
import com.itextpdf.html2pdf.html.node.IElementNode;
import com.itextpdf.html2pdf.html.node.INode;
import com.itextpdf.html2pdf.resolver.resource.ResourceResolver;
import com.itextpdf.io.util.MessageFormatUtil;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.type.UnitTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(UnitTest.class)
public class CssStylesResolvingTest extends ExtendedITextTest {
    private static final String sourceFolder = "./src/test/resources/com/itextpdf/html2pdf/css/CssElementStylesResolvingTest/";

    @BeforeClass
    public static void beforeClass() {
    }

    @Test
    public void collectStylesDeclarationsTest01() throws IOException {
        test("collectStylesDeclarationsTest01.html", "html body p",
                "color: red", "text-align: center", "font-size: 11.25pt",
                "margin-bottom: 1em",
                "margin-left: 0",
                "margin-right: 0",
                "margin-top: 1em",
                "display: block",
                "font-family: times-roman");
    }

    @Test
    public void collectStylesDeclarationsTest02() throws IOException {
        test("collectStylesDeclarationsTest02.html", "html body p",
                "color: blue", "text-align: center", "font-style: italic", "font-size: 11.25pt",
                "margin-bottom: 1em",
                "margin-left: 0",
                "margin-right: 0",
                "margin-top: 1em",
                "display: block",
                "font-family: times-roman");
    }

    @Test
    public void collectStylesDeclarationsTest03() throws IOException {
        test("collectStylesDeclarationsTest03.html", "html body p",
                "color: red", "text-align: right", "font-size: 7.5pt",
                "margin-bottom: 1em",
                "margin-left: 0",
                "margin-right: 0",
                "margin-top: 1em",
                "display: block",
                "font-family: times-roman");
    }

    @Test
    public void stylesInheritanceTest01() throws IOException {
        test("stylesInheritanceTest01.html", "html body p span",
                "color: blue", "text-align: center", "font-style: italic", "font-size: 11.25pt", "font-family: times-roman");
    }

    @Test
    public void stylesInheritanceTest02() throws IOException {
        test("stylesInheritanceTest02.html", "html body p span",
                "color: black", "text-align: center", "font-style: italic", "font-size: 11.25pt", "font-family: times-roman");
    }

    @Test
    public void stylesInheritanceTest03() throws IOException {
        test("stylesInheritanceTest03.html", "html body p span",
                "color: green", "font-size: 12.0pt", "font-family: times-roman");
    }

    @Test
    public void stylesInheritanceTest04() throws IOException {
        test("stylesInheritanceTest04.html", "html body p span",
                "color: blue", "font-size: 12.0pt", "font-family: times-roman");
    }

    @Test
    public void stylesInheritanceTest05() throws IOException {
        test("stylesInheritanceTest05.html", "html body p span",
                "color: black", "font-size: 12.0pt", "font-family: times-roman");
    }

    @Test
    public void stylesInheritanceTest06() throws IOException {
        test("stylesInheritanceTest06.html", "html body p span",
                "margin-left: 20px",
                "margin-right: 0",
                "background-color: yellow",
                "font-size: 12.0pt", "font-family: times-roman");
    }

    @Test
    public void stylesInheritanceTest07() throws IOException {
        test("stylesInheritanceTest07.html", "html body div p span",
                "margin-left: 0",
                "padding-top: 10px",
                "background-color: yellow",
                "font-size: 12.0pt", "font-family: times-roman");
    }

    @Test
    public void stylesShorthandsTest01() throws IOException {
        test("stylesShorthandsTest01.html", "html body p",
                "border-bottom-style: dashed",
                "border-bottom-width: 5px",
                "border-left-style: dashed",
                "border-left-width: 5px",
                "border-right-style: dashed",
                "border-right-width: 5px",
                "border-top-style: dashed",
                "border-top-width: 5px",
                "border-bottom-color: red",
                "border-left-color: red",
                "border-right-color: red",
                "border-top-color: red",
                "font-size: 12.0pt",
                "margin-bottom: 1em",
                "margin-left: 0",
                "margin-right: 0",
                "margin-top: 1em",
                "display: block",
                "font-family: times-roman");
    }

    @Test
    public void htmlStylesConvertingTest01() throws IOException {
        test("htmlStylesConvertingTest01.html", "html body b p",
                "font-weight: bold",
                "font-size: 12.0pt",
                "margin-bottom: 1em",
                "margin-left: 0",
                "margin-right: 0",
                "margin-top: 1em",
                "display: block",
                "font-family: times-roman");
    }

    @Test
    public void htmlStylesConvertingTest02() throws IOException {
        test("htmlStylesConvertingTest01.html", "html body b i p",
                "font-weight: bold", "font-style: italic",
                "font-size: 12.0pt",
                "margin-bottom: 1em",
                "margin-left: 0",
                "margin-right: 0",
                "margin-top: 1em",
                "display: block",
                "font-family: times-roman");
    }

    @Test
    public void htmlStylesConvertingTest03() throws IOException {
        test("htmlStylesConvertingTest01.html", "html body i p",
                "font-style: italic",
                "font-size: 12.0pt",
                "margin-bottom: 1em",
                "margin-left: 0",
                "margin-right: 0",
                "margin-top: 1em",
                "display: block",
                "font-family: times-roman");
    }

    @Test
    public void htmlStylesConvertingTest04() throws IOException {
        test("htmlStylesConvertingTest01.html", "html body i center p",
                "font-style: italic", "text-align: center",
                "font-size: 12.0pt",
                "margin-bottom: 1em",
                "margin-left: 0",
                "margin-right: 0",
                "margin-top: 1em",
                "display: block",
                "font-family: times-roman");
    }

    @Test
    public void htmlStylesConvertingTest05() throws IOException {
        test("htmlStylesConvertingTest05.html", "html body table",
                "border-bottom-style: solid", "border-left-style: solid", "border-right-style: solid", "border-top-style: solid",
                "border-bottom-width: 2px", "border-left-width: 2px", "border-right-width: 2px", "border-top-width: 2px",
                "font-size: 12.0pt",
                "margin-bottom: 0",
                "margin-left: 0",
                "margin-right: 0",
                "margin-top: 0",
                "text-indent: 0",
                "display: table",
                "border-spacing: 2px",
                "font-family: times-roman",
                "border-bottom-color: currentcolor",
                "border-left-color: currentcolor",
                "border-right-color: currentcolor",
                "border-top-color: currentcolor");
    }

    @Test
    public void htmlStylesConvertingTest06() throws IOException {
        test("htmlStylesConvertingTest05.html", "html body table tbody tr",
                "background-color: yellow",
                "font-size: 12.0pt",
                "text-indent: 0",
                "vertical-align: middle",
                "display: table-row",
                "border-spacing: 2px",
                "font-family: times-roman");
    }

    @Test
    public void htmlStylesConvertingTest07() throws IOException {
        test("htmlStylesConvertingTest07.html", "html body p font span",
                "font-size: 18.0pt", "font-family: verdana", "color: blue");
    }

    @Test
    public void htmlStylesConvertingTest08() throws IOException {
        test("htmlStylesConvertingTest08.html", "html body p font span",
                "font-size: 18.0pt", "font-family: verdana", "color: blue");
    }

    @Test
    public void htmlStylesConvertingTest09() throws IOException {
        test("htmlStylesConvertingTest08.html", "html body div center",
                "text-align: center",
                "display: block",
                "font-size: 12.0pt",
                "font-family: times-roman");
    }

    @Test
    public void htmlStylesConvertingTest10() throws IOException {
        test("htmlStylesConvertingTest10.html", "html body p font span",
                "font-size: 7.5pt", "font-family: verdana", "color: blue");
    }

    @Test
    public void htmlStylesConvertingTest11() throws IOException {
        test("htmlStylesConvertingTest10.html", "html body",
                "background-color: yellow",
                "font-size: 12.0pt",
                "display: block",
                "font-family: times-roman");
    }

    private void resolveStylesForTree(INode node, ICssResolver cssResolver, CssContext context) {
        if (node instanceof IElementNode) {
            IElementNode element = (IElementNode)node;
            element.setStyles(cssResolver.resolveStyles((IElementNode)node, context));
            if (TagConstants.HTML.equals(element.name())) {
                context.setRootFontSize(element.getStyles().get(CssConstants.FONT_SIZE));
            }
        }

        for (INode child : node.childNodes()) {
            resolveStylesForTree(child, cssResolver, context);
        }
    }

    private void test(String fileName, String elementPath, String... expectedStyles) throws IOException {
        String filePath = sourceFolder + fileName;
        IHtmlParser parser = new JsoupHtmlParser();
        IDocumentNode document = parser.parse(new FileInputStream(filePath), "UTF-8");
        ICssResolver cssResolver = new DefaultCssResolver(document, MediaDeviceDescription.createDefault(), new ResourceResolver(""));
        CssContext context = new CssContext();
        resolveStylesForTree(document, cssResolver, context);

        IElementNode element = findElement(document, elementPath);
        if (element == null) {
            Assert.fail(MessageFormatUtil.format("Element at path \"{0}\" was not found.", elementPath));
        }
        Map<String, String> elementStyles = element.getStyles();
        Set<String> expectedStylesSet = new HashSet<>(Arrays.asList(expectedStyles));
        Set<String> actualStylesSet = stylesMapToHashSet(elementStyles);
        Assert.assertTrue(getDifferencesMessage(expectedStylesSet, actualStylesSet), setsAreEqual(expectedStylesSet, actualStylesSet));
    }

    private IElementNode findElement(INode root, String ancestryPath) {
        INode currElement = root;
        String[] ancestors = ancestryPath.split(" ");
        int ancestorPathIndex = 0;

        boolean foundElement = false;
        while (ancestorPathIndex < ancestors.length) {
            String ancestor = ancestors[ancestorPathIndex];
            int ancestorIndex = 0;
            int dash = ancestor.indexOf('-');
            if (dash > 0) {
                ancestorIndex = Integer.parseInt(ancestor.substring(dash + 1, ancestor.length()));
                ancestor = ancestor.substring(0, dash);
            }

            int sameNameInd = 0;
            foundElement = false;
            for (INode nextKid : currElement.childNodes()) {
                if (nextKid instanceof IElementNode && ((IElementNode) nextKid).name().equals(ancestor) && sameNameInd++ == ancestorIndex) {
                    currElement = nextKid;
                    foundElement = true;
                    break;
                }
            }
            if (foundElement) {
                ++ancestorPathIndex;
            } else {
                break;
            }
        }

        return foundElement ? (IElementNode) currElement : null;
    }

    private Set<String> stylesMapToHashSet(Map<String, String> stylesMap) {
        Set<String> stylesSet = new HashSet<>();
        for (Map.Entry<String, String> entry : stylesMap.entrySet()) {
            stylesSet.add(entry.getKey() + ": " +entry.getValue());
        }
        return stylesSet;
    }

    private String getDifferencesMessage(Set<String> expectedStyles, Set<String> actualStyles) {
        StringBuilder sb = new StringBuilder("Resolved styles are different from expected!");
        Set<String> expCopy = new TreeSet<>(expectedStyles);
        Set<String> actCopy = new TreeSet<>(actualStyles);
        expCopy.removeAll(actualStyles);
        actCopy.removeAll(expectedStyles);
        if (!expCopy.isEmpty()) {
            sb.append("\nExpected but not found properties:\n");
            for (String expProp : expCopy) {
                sb.append(expProp).append('\n');
            }
        }
        if (!actCopy.isEmpty()) {
            sb.append("\nNot expected but found properties:\n");
            for (String actProp : actCopy) {
                sb.append(actProp).append('\n');
            }
        }
        return sb.toString();
    }

    private boolean setsAreEqual(Set<String> expectedStyles, Set<String> actualStyles) {
        boolean sizesEqual = expectedStyles.size() == actualStyles.size();
        boolean elementsEqual = true;
        for (String str : actualStyles) {
            if (str.startsWith("font-size")) {
                if (!compareFloatProperty(expectedStyles, actualStyles, "font-size")) {
                    elementsEqual = false;
                    break;
                }
            } else if (!expectedStyles.contains(str)) {
                elementsEqual = false;
                break;
            }
        }
        return sizesEqual && elementsEqual;
    }

    private boolean compareFloatProperty(Set<String> expectedStyles, Set<String> actualStyles, String propertyName) {
        String containsExpected = null;
        for (String str : expectedStyles) {
            if (str.startsWith(propertyName)) {
                containsExpected = str;
            }
        }
        String containsActual = null;
        for (String str : actualStyles) {
            if (str.startsWith(propertyName)) {
                containsActual = str;
            }
        }

        if (containsActual == null && containsExpected == null) {
            return true;
        }

        if (containsActual != null && containsExpected != null) {
            containsActual = containsActual.substring(propertyName.length() + 1).trim();
            containsExpected = containsExpected.substring(propertyName.length() + 1).trim();
            float actual = CssUtils.parseAbsoluteLength(containsActual, CssConstants.PT);
            float expected = CssUtils.parseAbsoluteLength(containsExpected, CssConstants.PT);
            return Math.abs(actual - expected) < 0.0001;
        } else {
            return false;
        }
    }

}
