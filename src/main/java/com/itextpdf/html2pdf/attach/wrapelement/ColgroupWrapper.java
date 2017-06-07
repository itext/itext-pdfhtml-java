/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2017 iText Group NV
    Authors: Bruno Lowagie, Paulo Soares, et al.
    
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
package com.itextpdf.html2pdf.attach.wrapelement;

import com.itextpdf.layout.property.UnitValue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ColgroupWrapper implements IWrapElement {
    private int span;
    private UnitValue width;
    private int[] indexToColMapping;
    //Those properties should be inherited from <colgroup> to <col> and are eventually applied to <td> or <th>
    private Map<String, String> cellCssProps;
    //Those properties shouldn't be applied to <td> or <th>private Map<String, String> ownCssProps;
    private Map<String, String> ownCssProps;
    private List<ColWrapper> columns = new ArrayList<>();

    public ColgroupWrapper(int span) {
        this.span = span;
    }

    public int getSpan() {
        return span;
    }

    public UnitValue getWidth() {
        return width;
    }

    public ColgroupWrapper setWidth(UnitValue width) {
        this.width = width;
        return this;
    }

    public Map<String, String> getCellCssProps() {
        return cellCssProps;
    }

    public ColgroupWrapper setCellCssProps(Map<String, String> cellCssProps) {
        this.cellCssProps = cellCssProps;
        return this;
    }

    public Map<String, String> getOwnCssProps() {
        return ownCssProps;
    }

    public ColgroupWrapper setOwnCssProps(Map<String, String> ownCssProps) {
        this.ownCssProps = ownCssProps;
        return this;
    }

    public List<ColWrapper> getColumns() {
        return columns;
    }

    public ColgroupWrapper finalizeCols() {
        if (indexToColMapping != null) {
            return this;
        }
        if (columns.isEmpty()) {
            columns.add(new ColWrapper(span).setCellCssProps(cellCssProps).setWidth(width));
        } else if (cellCssProps != null) {
            for (ColWrapper col : columns) {
                Map<String, String> colStyles = new HashMap<>(cellCssProps);
                if (col.getCellCssProps() != null) {
                    colStyles.putAll(col.getCellCssProps());
                }
                if (colStyles.size() > 0) {
                    col.setCellCssProps(colStyles);
                }
                if (col.getWidth() == null) {
                    col.setWidth(width);
                }
            }
        }
        columns = Collections.unmodifiableList(columns);
        int ncol = 0;
        for(ColWrapper col : columns) {
            ncol += col.getSpan();
        }
        indexToColMapping = new int[ncol];
        span = 0;
        for(int i = 0; i < columns.size(); ++i) {
            int colSpan = columns.get(i).getSpan();
            for (int j = 0; j < colSpan; ++j) {
                indexToColMapping[span + j] = i;
            }
            span += colSpan;
        }
        return this;
    }

    public ColWrapper getColumnByIndex(int index) {
        return columns.get(indexToColMapping[index]);
    }
}
