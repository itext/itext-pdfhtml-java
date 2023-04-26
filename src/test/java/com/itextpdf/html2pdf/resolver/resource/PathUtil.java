/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2023 Apryse Group NV
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
package com.itextpdf.html2pdf.resolver.resource;

import java.nio.file.Paths;

final class PathUtil{
    private static boolean isRunOnJava = false;

// Android-Conversion-Skip-Block-Start (during Android conversion relative paths "./src/test/resources" will be replaced with absolute paths)
    static {
        isRunOnJava = true;
    }
// Android-Conversion-Skip-Block-End

    private PathUtil() {}

    static String getAbsolutePathToResourcesForHtmlResourceResolverTest() {
        if (isRunOnJava) {
            return Paths.get("").toAbsolutePath().toString() + "/src/test/resources/com/itextpdf/html2pdf/"
                    + "resolver/resource/HtmlResourceResolverTest/res";
        }
        // Test is run on Android, so "./src/test/resources" substring will be replaced as abosulte path.
        return "./src/test/resources/com/itextpdf/html2pdf/resolver/resource/HtmlResourceResolverTest/res";
    }

    static String getUriToResourcesForHtmlResourceResolverTest() {
        if (isRunOnJava) {
            // It is important to put a trailing slash in the end: if you specify base URI via absolute URI string,
            // you need to follow URI standards, in which a path without trailing slash is referring to a file.
            return Paths.get("").toUri() + "src/test/resources/com/itextpdf/html2pdf/resolver/"
                    + "resource/HtmlResourceResolverTest/res/";
        }
        // Test is run on Android, so "./src/test/resources" substring will be replaced as abosulte path.
        // It is important to put a trailing slash in the end: if you specify base URI via absolute URI string,
        // you need to follow URI standards, in which a path without trailing slash is referring to a file.
        return "./src/test/resources/com/itextpdf/html2pdf/resolver/resource/HtmlResourceResolverTest/res/";
    }
}
