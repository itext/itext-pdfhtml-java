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
package com.itextpdf.html2pdf;

import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.type.UnitTest;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

@Category(UnitTest.class)
public class UriResolverTest extends ExtendedITextTest {

    @Rule
    public ExpectedException junitExpectedException = ExpectedException.none();

    @Test
    public void uriResolverTest01() throws MalformedURLException {
        UriResolver resolver = new UriResolver("file://c:/test/folder/index.html");
        Assert.assertEquals("file:/c:/test/folder/index.html", resolver.getBaseUri());
        Assert.assertEquals("file:/c:/test/folder/innerTest", resolver.resolveAgainstBaseUri("innerTest").toExternalForm());
        Assert.assertEquals("file:/c:/test/folder2/innerTest2", resolver.resolveAgainstBaseUri("../folder2/innerTest2").toExternalForm());
        Assert.assertEquals("file:/c:/test/folder/folder2/innerTest2", resolver.resolveAgainstBaseUri("/folder2/innerTest2").toExternalForm());
        Assert.assertEquals("file:/c:/test/folder/folder2/innerTest2", resolver.resolveAgainstBaseUri("//folder2/innerTest2").toExternalForm());
    }

    @Test
    public void uriResolverTest02() throws MalformedURLException {
        UriResolver resolver = new UriResolver("test/folder/index.html");
        String runFolder = Paths.get("").toUri().toURL().toExternalForm();
        Assert.assertEquals(runFolder + "test/folder/index.html", resolver.getBaseUri());
        Assert.assertEquals(runFolder + "test/folder/innerTest", resolver.resolveAgainstBaseUri("innerTest").toExternalForm());
        Assert.assertEquals(runFolder + "test/folder2/innerTest2", resolver.resolveAgainstBaseUri("../folder2/innerTest2").toExternalForm());
        Assert.assertEquals(runFolder + "test/folder/folder2/innerTest2", resolver.resolveAgainstBaseUri("/folder2/innerTest2").toExternalForm());
        Assert.assertEquals(runFolder + "test/folder/folder2/innerTest2", resolver.resolveAgainstBaseUri("//folder2/innerTest2").toExternalForm());
    }

    @Test
    public void uriResolverTest03() throws MalformedURLException {
        UriResolver resolver = new UriResolver("/test/folder/index.html");
        String rootFolder = Paths.get("").toAbsolutePath().getRoot().toUri().toURL().toExternalForm();
        Assert.assertEquals(rootFolder + "test/folder/index.html", resolver.getBaseUri());
        Assert.assertEquals(rootFolder + "test/folder/innerTest", resolver.resolveAgainstBaseUri("innerTest").toExternalForm());
        Assert.assertEquals(rootFolder + "test/folder2/innerTest2", resolver.resolveAgainstBaseUri("../folder2/innerTest2").toExternalForm());
        Assert.assertEquals(rootFolder + "test/folder/folder2/innerTest2", resolver.resolveAgainstBaseUri("/folder2/innerTest2").toExternalForm());
        Assert.assertEquals(rootFolder + "test/folder/folder2/innerTest2", resolver.resolveAgainstBaseUri("//folder2/innerTest2").toExternalForm());
    }

    @Test
    public void uriResolverTest04() throws MalformedURLException {
        UriResolver resolver = new UriResolver("index.html");
        String runFolder = Paths.get("").toUri().toURL().toExternalForm();
        Assert.assertEquals(runFolder + "index.html", resolver.getBaseUri());
        Assert.assertEquals(runFolder + "innerTest", resolver.resolveAgainstBaseUri("innerTest").toExternalForm());
        Assert.assertEquals(runFolder + "folder2/innerTest2", resolver.resolveAgainstBaseUri("/folder2/innerTest2").toExternalForm());
        Assert.assertEquals(runFolder + "folder2/innerTest2", resolver.resolveAgainstBaseUri("//folder2/innerTest2").toExternalForm());
    }

    @Test
    public void uriResolverTest05() throws MalformedURLException {
        UriResolver resolver = new UriResolver("/../test/folder/index.html");
        String rootFolder = Paths.get("").toAbsolutePath().getRoot().toUri().toURL().toExternalForm();
        Assert.assertEquals(rootFolder + "test/folder/index.html", resolver.getBaseUri());
        Assert.assertEquals(rootFolder + "test/folder/innerTest", resolver.resolveAgainstBaseUri("innerTest").toExternalForm());
        Assert.assertEquals(rootFolder + "test/folder2/innerTest2", resolver.resolveAgainstBaseUri("../folder2/innerTest2").toExternalForm());
        Assert.assertEquals(rootFolder + "test/folder/folder2/innerTest2", resolver.resolveAgainstBaseUri("/folder2/innerTest2").toExternalForm());
        Assert.assertEquals(rootFolder + "test/folder/folder2/innerTest2", resolver.resolveAgainstBaseUri("//folder2/innerTest2").toExternalForm());
    }

    @Test
    public void uriResolverTest06() throws MalformedURLException {
        UriResolver resolver = new UriResolver("../test/folder/index.html");
        String parentFolder = Paths.get("").toAbsolutePath().getParent().toUri().toURL().toExternalForm();
        Assert.assertEquals(parentFolder + "test/folder/index.html", resolver.getBaseUri());
        Assert.assertEquals(parentFolder + "test/folder/innerTest", resolver.resolveAgainstBaseUri("innerTest").toExternalForm());
        Assert.assertEquals(parentFolder + "test/folder2/innerTest2", resolver.resolveAgainstBaseUri("../folder2/innerTest2").toExternalForm());
        Assert.assertEquals(parentFolder + "test/folder/folder2/innerTest2", resolver.resolveAgainstBaseUri("/folder2/innerTest2").toExternalForm());
        Assert.assertEquals(parentFolder + "test/folder/folder2/innerTest2", resolver.resolveAgainstBaseUri("//folder2/innerTest2").toExternalForm());
    }

    @Test
    public void uriResolverTest07() throws MalformedURLException {
        UriResolver resolver = new UriResolver("http://itextpdf.com/itext7");
        Assert.assertEquals("http://itextpdf.com/itext7", resolver.getBaseUri());
        Assert.assertEquals("http://itextpdf.com/innerTest", resolver.resolveAgainstBaseUri("innerTest").toExternalForm());
        Assert.assertEquals("http://itextpdf.com/folder2/innerTest2", resolver.resolveAgainstBaseUri("/folder2/innerTest2").toExternalForm());
        Assert.assertEquals("http://folder2.com/innerTest2", resolver.resolveAgainstBaseUri("//folder2.com/innerTest2").toExternalForm());
    }

    @Test
    public void uriResolverTest08() throws MalformedURLException {
        UriResolver resolver = new UriResolver("http://itextpdf.com/itext7/");
        Assert.assertEquals("http://itextpdf.com/itext7/", resolver.getBaseUri());
        Assert.assertEquals("http://itextpdf.com/itext7/innerTest", resolver.resolveAgainstBaseUri("innerTest").toExternalForm());
        Assert.assertEquals("http://itextpdf.com/folder2/innerTest2", resolver.resolveAgainstBaseUri("/folder2/innerTest2").toExternalForm());
        Assert.assertEquals("http://folder2.com/innerTest2", resolver.resolveAgainstBaseUri("//folder2.com/innerTest2").toExternalForm());
    }

    @Test
    public void uriResolverTest09() throws MalformedURLException {
        UriResolver resolver = new UriResolver("c:/test/folder/index.html");
        Assert.assertEquals("file:/c:/test/folder/index.html", resolver.getBaseUri());
        Assert.assertEquals("file:/c:/test/folder/innerTest", resolver.resolveAgainstBaseUri("innerTest").toExternalForm());
        Assert.assertEquals("file:/c:/test/folder2/innerTest2", resolver.resolveAgainstBaseUri("../folder2/innerTest2").toExternalForm());
        Assert.assertEquals("file:/c:/test/folder/folder2/innerTest2", resolver.resolveAgainstBaseUri("/folder2/innerTest2").toExternalForm());
        Assert.assertEquals("file:/c:/test/folder/folder2/innerTest2", resolver.resolveAgainstBaseUri("//folder2/innerTest2").toExternalForm());
    }

    @Test
    public void uriResolverTest10() throws MalformedURLException {
        junitExpectedException.expect(IllegalArgumentException.class);
        junitExpectedException.expectMessage("Invalid base URI: /C:/test/folder/index.html");
        UriResolver resolver = new UriResolver("/C:/test/folder/index.html");
    }

    @Test
    public void uriResolverTest11() throws MalformedURLException {
        junitExpectedException.expect(IllegalArgumentException.class);
        junitExpectedException.expectMessage("Invalid base URI: http://some-external-server.com/info_path%%20-%20way.html");
        UriResolver resolver = new UriResolver("http://some-external-server.com/info_path%%20-%20way.html");
    }

    @Test
    public void uriResolverTest12() throws MalformedURLException {
        junitExpectedException.expect(IllegalArgumentException.class);
        junitExpectedException.expectMessage("Invalid base URI: some:random:text:inside");
        UriResolver resolver = new UriResolver("some:random:text:inside");
    }

    @Test
    public void uriResolverTest13() throws MalformedURLException {
        junitExpectedException.expect(IllegalArgumentException.class);
        junitExpectedException.expectMessage("Invalid base URI: //test");
        UriResolver resolver = new UriResolver("//test");
    }

    @Test
    public void uriResolverTest14() throws MalformedURLException {
        UriResolver resolver = new UriResolver("base/uri/index.html");
        String runFolder = Paths.get("").toUri().toURL().toExternalForm();
        Assert.assertEquals(runFolder + "base/uri/index.html", resolver.getBaseUri());
        Assert.assertEquals("file:/c:/test/folder/img.txt", resolver.resolveAgainstBaseUri("file:/c:/test/folder/img.txt").toExternalForm());
        Assert.assertEquals("file://c:/test/folder/img.txt", resolver.resolveAgainstBaseUri("file://c:/test/folder/img.txt").toExternalForm());
        Assert.assertEquals("file:/c:/test/folder/data.jpg", resolver.resolveAgainstBaseUri("file:///c:/test/folder/data.jpg").toExternalForm());
        Assert.assertEquals("file:/c:/test/folder/data.jpg", resolver.resolveAgainstBaseUri("c:/test/folder/data.jpg").toExternalForm());
    }

    @Test
    public void uriResolverTest15() throws MalformedURLException {
        UriResolver resolver = new UriResolver("file:/C:/test/folder/index.html");
        Assert.assertEquals("file:/C:/test/folder/index.html", resolver.getBaseUri());
        Assert.assertEquals("file:/C:/test/folder/innerTest", resolver.resolveAgainstBaseUri("innerTest").toExternalForm());
        Assert.assertEquals("file:/C:/test/folder2/innerTest2", resolver.resolveAgainstBaseUri("../folder2/innerTest2").toExternalForm());
        Assert.assertEquals("file:/C:/test/folder/folder2/innerTest2", resolver.resolveAgainstBaseUri("/folder2/innerTest2").toExternalForm());
        Assert.assertEquals("file:/C:/test/folder/folder2/innerTest2", resolver.resolveAgainstBaseUri("//folder2/innerTest2").toExternalForm());
    }

    @Test
    public void uriResolverTest16() throws MalformedURLException {
        UriResolver resolver = new UriResolver("file:///C:/test/folder/index.html");
        Assert.assertEquals("file:/C:/test/folder/index.html", resolver.getBaseUri());
        Assert.assertEquals("file:/C:/test/folder/innerTest", resolver.resolveAgainstBaseUri("innerTest").toExternalForm());
        Assert.assertEquals("file:/C:/test/folder2/innerTest2", resolver.resolveAgainstBaseUri("../folder2/innerTest2").toExternalForm());
        Assert.assertEquals("file:/C:/test/folder/folder2/innerTest2", resolver.resolveAgainstBaseUri("/folder2/innerTest2").toExternalForm());
        Assert.assertEquals("file:/C:/test/folder/folder2/innerTest2", resolver.resolveAgainstBaseUri("//folder2/innerTest2").toExternalForm());
    }

    @Test
    public void uriResolverTest17() throws MalformedURLException {
        UriResolver resolver = new UriResolver("file:C:/test/folder/index.html");
        Assert.assertEquals("file:/C:/test/folder/index.html", resolver.getBaseUri());
        Assert.assertEquals("file:/C:/test/folder/innerTest", resolver.resolveAgainstBaseUri("innerTest").toExternalForm());
        Assert.assertEquals("file:/C:/test/folder2/innerTest2", resolver.resolveAgainstBaseUri("../folder2/innerTest2").toExternalForm());
        Assert.assertEquals("file:/C:/test/folder/folder2/innerTest2", resolver.resolveAgainstBaseUri("/folder2/innerTest2").toExternalForm());
        Assert.assertEquals("file:/C:/test/folder/folder2/innerTest2", resolver.resolveAgainstBaseUri("//folder2/innerTest2").toExternalForm());
    }

    @Test
    public void uriResolverTest18() throws MalformedURLException {
        UriResolver resolver = new UriResolver("C:/test/folder/index.html");
        Assert.assertEquals("file:/C:/test/folder/index.html", resolver.getBaseUri());
        Assert.assertEquals("file:/C:/test/folder/innerTest", resolver.resolveAgainstBaseUri("innerTest").toExternalForm());
        Assert.assertEquals("file:/C:/test/folder2/innerTest2", resolver.resolveAgainstBaseUri("../folder2/innerTest2").toExternalForm());
        Assert.assertEquals("file:/C:/test/folder/folder2/innerTest2", resolver.resolveAgainstBaseUri("/folder2/innerTest2").toExternalForm());
        Assert.assertEquals("file:/C:/test/folder/folder2/innerTest2", resolver.resolveAgainstBaseUri("//folder2/innerTest2").toExternalForm());
    }

    // TODO some tests with unix-like paths?
}
