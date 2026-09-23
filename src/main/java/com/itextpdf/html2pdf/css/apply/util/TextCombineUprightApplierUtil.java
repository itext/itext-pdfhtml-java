/*
	This file is part of the iText (R) project.
	Copyright (c) 1998-2026 Apryse Group NV
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
package com.itextpdf.html2pdf.css.apply.util;

import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.layout.properties.TextCombineUpright;
import com.itextpdf.styledxmlparser.css.CommonCssConstants;

import java.util.Map;

/**
 * Applies the CSS Level 3 text-combine-upright property.
 */
public final class TextCombineUprightApplierUtil {
	private TextCombineUprightApplierUtil() {
	}

	/**
	 * Maps resolved CSS values to layout properties. The renderer ignores combination in horizontal writing.
	 *
	 * @param cssProps resolved CSS properties
	 * @param element target layout element
	 */
	public static void applyTextCombineUpright(Map<String, String> cssProps, IPropertyContainer element) {
		String value = cssProps.get(CommonCssConstants.TEXT_COMBINE_UPRIGHT);
		if (CommonCssConstants.ALL.equals(value)) {
			element.setProperty(Property.TEXT_COMBINE_UPRIGHT, TextCombineUpright.ALL);
		} else if (CommonCssConstants.NONE.equals(value)) {
			element.setProperty(Property.TEXT_COMBINE_UPRIGHT, TextCombineUpright.NONE);
		}
	}
}
