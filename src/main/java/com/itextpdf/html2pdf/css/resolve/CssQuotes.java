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
package com.itextpdf.html2pdf.css.resolve;

import com.itextpdf.html2pdf.LogMessageConstant;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.css.parse.CssDeclarationValueTokenizer;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.ArrayList;

public class CssQuotes {
    private static final String EMPTY_QUOTE = "";

    private ArrayList<String> openQuotes;
    private ArrayList<String> closeQuotes;

    private CssQuotes(ArrayList<String> openQuotes, ArrayList<String> closeQuotes) {
        this.openQuotes = openQuotes;
        this.closeQuotes = closeQuotes;
    }

    public static CssQuotes createQuotes(String quotesString, boolean fallbackToDefault) {
        boolean error = false;
        ArrayList<ArrayList<String>> quotes = new ArrayList<>(2);
        quotes.add(new ArrayList<String>());
        quotes.add(new ArrayList<String>());
        if (quotesString != null) {
            if (quotesString.equals(CssConstants.NONE)) {
                quotes.get(0).add(EMPTY_QUOTE);
                quotes.get(1).add(EMPTY_QUOTE);
                return new CssQuotes(quotes.get(0), quotes.get(1));
            }
            CssDeclarationValueTokenizer tokenizer = new CssDeclarationValueTokenizer(quotesString);
            CssDeclarationValueTokenizer.Token token;
            for (int i = 0; ((token = tokenizer.getNextValidToken()) != null); ++i) {
                if (token.isString()) {
                    quotes.get(i % 2).add(token.getValue());
                } else {
                    error = true;
                    break;
                }
            }
            if (quotes.get(0).size() == quotes.get(1).size() && !quotes.get(0).isEmpty() && !error) {
                return new CssQuotes(quotes.get(0), quotes.get(1));
            } else {
                LoggerFactory.getLogger(CssQuotes.class).error(MessageFormat.format(LogMessageConstant.QUOTES_PROPERTY_INVALID, quotesString));
            }
        }
        return fallbackToDefault ? createDefaultQuotes() : null;
    }

    public static CssQuotes createDefaultQuotes() {
        ArrayList<String> openQuotes = new ArrayList<>();
        ArrayList<String> closeQuotes = new ArrayList<>();
        openQuotes.add("\u00ab");
        closeQuotes.add("\u00bb");
        return new CssQuotes(openQuotes, closeQuotes);
    }

    public String resolveQuote(String value, CssContext context) {
        int depth = context.getQuotesDepth();
        if (CssConstants.OPEN_QUOTE.equals(value)) {
            increaseDepth(context);
            return getQuote(depth, openQuotes);
        } else if (CssConstants.CLOSE_QUOTE.equals(value)) {
            decreaseDepth(context);
            return getQuote(depth - 1, closeQuotes);
        } else if (CssConstants.NO_OPEN_QUOTE.equals(value)) {
            increaseDepth(context);
            return EMPTY_QUOTE;
        } else if (CssConstants.NO_CLOSE_QUOTE.equals(value)) {
            decreaseDepth(context);
            return EMPTY_QUOTE;
        }
        return null;
    }

    private void increaseDepth(CssContext context) {
        context.setQuotesDepth(context.getQuotesDepth() + 1);
    }

    private void decreaseDepth(CssContext context) {
        if (context.getQuotesDepth() > 0) {
            context.setQuotesDepth(context.getQuotesDepth() - 1);
        }
    }

    private String getQuote(int depth, ArrayList<String> quotes) {
        if (depth >= quotes.size()) {
            return quotes.get(quotes.size() - 1);
        }
        if (depth < 0) {
            return EMPTY_QUOTE;
        }
        return quotes.get(depth);
    }
}
