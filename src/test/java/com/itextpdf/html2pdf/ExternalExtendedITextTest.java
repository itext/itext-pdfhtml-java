/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2021 iText Group NV
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
package com.itextpdf.html2pdf;

import com.itextpdf.test.ExtendedITextTest;

//TODO DEVSIX-5699 Remove ExternalExtendedITextTest in java and .NET after upgrading of .netframework to 4.6 version
//This manually ported class was added to prevent problems with TLS12 version in .NET for netframework 4.5.
//In Java this will be an empty class, whereas in .NET it will not be empty because TLS12 is the default for JDK7+
//and netcoreapp2.0 only. But TLS12 is not default for netframework4.5 so we explicitly set TLS12 security protocol
//to avoid connection issues in ExternalExtendedITextTest in .NET
public class ExternalExtendedITextTest extends ExtendedITextTest {

}
