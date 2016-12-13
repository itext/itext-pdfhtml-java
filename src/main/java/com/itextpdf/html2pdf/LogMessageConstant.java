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

public final class LogMessageConstant {

    public static final String ERROR_LOADING_FONT = "Error while loading font";
    public static final String ERROR_PARSING_CSS_SELECTOR = "Error while parsing css selector";
    public static final String ERROR_RESOLVING_PARENT_STYLES = "Element parent styles are not resolved. Styles for current element might be incorrect.";
    public static final String HEIGHT_VALUE_IN_PERCENT_NOT_SUPPORTED = "Height value in percent not supported";
    public static final String INVALID_CSS_PROPERTY_DECLARATION = "Invalid css property declaration: {0}";
    public static final String MARGIN_VALUE_IN_PERCENT_NOT_SUPPORTED = "Margin value in percents not supported";
    public static final String NO_CONSUMER_FOUND_FOR_CONTENT = "No consumer found for content";
    public static final String NO_CSS_APPLIER_FOUND_FOR_TAG = "No css applier found for tag {0}";
    public static final String NO_WORKER_FOUND_FOR_TAG = "No worker found for tag {0}";
    public static final String NOT_SUPPORTED_LIST_STYLE_TYPE = "Not supported list style type: {0}";
    public static final String PADDING_VALUE_IN_PERCENT_NOT_SUPPORTED = "Padding value in percents not supported";
    public static final String RULE_IS_NOT_SUPPORTED = "The rule @{0} is unsupported. All selectors in this rule will be ignored.";
    public static final String TEXT_WAS_NOT_PROCESSED = "Text was not processed: {0}";
    public static final String TEXT_DECORATION_BLINK_NOT_SUPPORTED = "text-decoration: blink not supported";
    public static final String TEXT_INDENT_IN_PERCENTS_IS_NOT_SUPPORTED = "text-indent in percents is not supported";
    public static final String UNABLE_TO_PROCESS_EXTERNAL_CSS_FILE = "Unable to process external css file";
    public static final String UNABLE_TO_RETRIEVE_IMAGE_WITH_GIVEN_BASE_URI = "Unable to retrieve image with given base URI ({0}) and image source path ({1})";
    public static final String UNABLE_TO_RESOLVE_FONT = "Unable to resolve font: {0}. The default one will be used instead";
    public static final String UNKNOWN_ABSOLUTE_METRIC_LENGTH_PARSED = "Unknown absolute metric length parsed \"{0}\".";
    public static final String WORKER_UNABLE_TO_PROCESS_OTHER_WORKER = "Worker of type {0} unable to process {1}";
    public static final String WORKER_UNABLE_TO_PROCESS_IT_S_TEXT_CONTENT = "Worker of type {0} unable to process it's text content";

    private LogMessageConstant() {
    }

}
