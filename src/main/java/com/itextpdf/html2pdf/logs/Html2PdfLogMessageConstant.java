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
package com.itextpdf.html2pdf.logs;

/**
 * Class that bundles all the error message templates as constants.
 */
public final class Html2PdfLogMessageConstant {

    /** The Constant CANNOT_RESOLVE_TARGET_COUNTER_VALUE. */
    public static final String CANNOT_RESOLVE_TARGET_COUNTER_VALUE
            = "Cannot resolve target-counter value with given target \"{0}\"";

    /** The Constant CUSTOM_RENDERER_IS_SET_FOR_HTML_DOCUMENT. */
    public static final String CUSTOM_RENDERER_IS_SET_FOR_HTML_DOCUMENT =
            "A custom renderer which doesn't extend HtmlDocumentRenderer is set for HtmlDocument. Counters and "
                    + "target-counters may be displayed incorrectly.";

    public static final String ANCHOR_LINK_NOT_HANDLED =
            "The anchor link was not handled. Could not create a destination for element \"{0}\" with ID \"{1}\", "
                    + "which is processed by \"{2}\" tag worker class.";

    /** The Constant CONTENT_PROPERTY_INVALID. */
    public static final String CONTENT_PROPERTY_INVALID =
            "Content property \"{0}\" is either invalid or uses unsupported function.";

    /** The Constant CSS_PROPERTY_IN_PERCENTS_NOT_SUPPORTED. */
    public static final String CSS_PROPERTY_IN_PERCENTS_NOT_SUPPORTED = "Css property {0} in percents is not supported";

    public static final String DEFAULT_VALUE_OF_CSS_PROPERTY_UNKNOWN =
            "Default value of the css property \"{0}\" is unknown.";

    /** The Constant ERROR_LOADING_FONT. */
    public static final String ERROR_LOADING_FONT = "Error while loading font";

    /** The Constant ERROR_PARSING_CSS_SELECTOR. */
    public static final String ERROR_PARSING_CSS_SELECTOR = "Error while parsing css selector: {0}";

    /** The Constant ERROR_RESOLVING_PARENT_STYLES. */
    public static final String ERROR_RESOLVING_PARENT_STYLES =
            "Element parent styles are not resolved. Styles for current element might be incorrect.";

    /** The Constant EXCEEDED_THE_MAXIMUM_NUMBER_OF_RELAYOUTS. */
    public static final String EXCEEDED_THE_MAXIMUM_NUMBER_OF_RELAYOUTS =
            "Exceeded the maximum number of relayouts. The resultant document may look not as expected. Because of the"
                    + " content being dynamic iText performs several relayouts to produce correct document.";

    /** The constant FLEX_PROPERTY_IS_NOT_SUPPORTED_YET. */
    public static final String FLEX_PROPERTY_IS_NOT_SUPPORTED_YET =
            "Flex related property {0}: {1} is not supported yet.";

    /** The Constant INPUT_TYPE_IS_INVALID. */
    public static final String INPUT_TYPE_IS_INVALID =
            "Input type {0} is invalid. The default text type will be used instead.";

    /** The Constant INPUT_TYPE_IS_NOT_SUPPORTED. */
    public static final String INPUT_TYPE_IS_NOT_SUPPORTED = "Input type {0} is not supported";

    /** The Constant INVALID_CSS_PROPERTY_DECLARATION. */
    public static final String INVALID_CSS_PROPERTY_DECLARATION = "Invalid css property declaration: {0}";

    /** The Constant INVALID_CSS_PROPERTY_DECLARATION. */
    public static final String INVALID_GRADIENT_DECLARATION = "Invalid gradient declaration: {0}";

    /** The Constant MARGIN_VALUE_IN_PERCENT_NOT_SUPPORTED. */
    public static final String MARGIN_VALUE_IN_PERCENT_NOT_SUPPORTED = "Margin value in percents not supported";

    /** The Constant NOT_SUPPORTED_LIST_STYLE_TYPE. */
    public static final String NOT_SUPPORTED_LIST_STYLE_TYPE = "Not supported list style type: {0}";

    /** The Constant NOT_SUPPORTED_TH_SCOPE_TYPE. */
    public static final String NOT_SUPPORTED_TH_SCOPE_TYPE =
            "Not supported th scope type: {0}. Document may not be compliant with PDF/UA standards.";

    /** The Constant NO_CONSUMER_FOUND_FOR_CONTENT. */
    public static final String NO_CONSUMER_FOUND_FOR_CONTENT = "No consumer found for content";

    /** The Constant NO_CSS_APPLIER_FOUND_FOR_TAG. */
    public static final String NO_CSS_APPLIER_FOUND_FOR_TAG = "No css applier found for tag {0}";

    /** The Constant NO_IPROPERTYCONTAINER_RESULT_FOR_THE_TAG. */
    public static final String NO_IPROPERTYCONTAINER_RESULT_FOR_THE_TAG =
            "Tag worker does not produce IPropertyContainer for \"{0}\" tag. An outline for \"{0}\" tag will not be "
                    + "created.";

    /** The Constant NO_WORKER_FOUND_FOR_TAG. */
    public static final String NO_WORKER_FOUND_FOR_TAG = "No worker found for tag {0}";

    /** The Constant PADDING_VALUE_IN_PERCENT_NOT_SUPPORTED. */
    public static final String PADDING_VALUE_IN_PERCENT_NOT_SUPPORTED = "Padding value in percents not supported";

    public static final String PAGE_MARGIN_BOX_CONTENT_CANNOT_BE_DRAWN = "Page margin box {0} content cannot be drawn.";

    public static final String PAGE_MARGIN_BOX_SOME_PROPERTIES_NOT_PROCESSED =
            "Page margin box margin, padding, height and width properties are not processed. Passed styles container "
                    + "shall be of PageMarginBoxContextNode type.";

    /** The Constant PAGE_SIZE_VALUE_IS_INVALID. */
    public static final String PAGE_SIZE_VALUE_IS_INVALID = "Page size value {0} is invalid.";

    /** The Constant PDF_DOCUMENT_NOT_PRESENT. */
    public static final String PDF_DOCUMENT_NOT_PRESENT = "PdfDocument is not present";

    /** The Constant QUOTES_PROPERTY_INVALID. */
    public static final String QUOTES_PROPERTY_INVALID =
            "Quote property \"{0}\" is invalid. It should contain even number of <string> values.";

    /** The Constant TEXT_DECORATION_BLINK_NOT_SUPPORTED. */
    public static final String TEXT_DECORATION_BLINK_NOT_SUPPORTED = "text-decoration: blink not supported";

    /** The Constant HSL_COLOR_NOT_SUPPORTED. */
    public static final String HSL_COLOR_NOT_SUPPORTED = "Hsl colors are not supported";

    /** The Constant UNABLE_TO_PROCESS_EXTERNAL_CSS_FILE. */
    public static final String UNABLE_TO_PROCESS_EXTERNAL_CSS_FILE = "Unable to process external css file";

    /** The Constant UNABLE_TO_PROCESS_SVG. */
    public static final String UNABLE_TO_PROCESS_SVG_ELEMENT = "Unable to process an SVG element";

    /** The Constant UNABLE_TO_RESOLVE_COUNTER. */
    public static final String UNABLE_TO_RESOLVE_COUNTER = "Unable to resolve counter \"{0}\"";

    /** The Constant UNABLE_TO_RETRIEVE_FONT. */
    public static final String UNABLE_TO_RETRIEVE_FONT = "Unable to retrieve font:\n {0}";

    /** The Constant UNABLE_TO_RETRIEVE_IMAGE_WITH_GIVEN_BASE_URI. */
    public static final String UNABLE_TO_RETRIEVE_IMAGE_WITH_GIVEN_BASE_URI =
            "Unable to retrieve image with given base URI ({0}) and image source path ({1})";

    /** The Constant UNABLE_TO_RETRIEVE_STREAM_WITH_GIVEN_BASE_URI. */
    public static final String UNABLE_TO_RETRIEVE_STREAM_WITH_GIVEN_BASE_URI =
            "Unable to retrieve stream with given base URI ({0}) and source path ({1})";

    /** The Constant UNEXPECTED_VALUE_OF_OBJECT_FIT_PROPERTY. */
    public static final String UNEXPECTED_VALUE_OF_OBJECT_FIT =
            "Unexpected value of object-fit property: {0}. Will be processed as default";

    /** The Constant UNKNOWN_MARGIN_BOX_CHILD. */
    public static final String UNKNOWN_MARGIN_BOX_CHILD = "Unknown margin box child";

    /** The Constant WORKER_UNABLE_TO_PROCESS_IT_S_TEXT_CONTENT. */
    public static final String WORKER_UNABLE_TO_PROCESS_IT_S_TEXT_CONTENT =
            "Worker of type {0} unable to process it`s text content";

    /** The Constant WORKER_UNABLE_TO_PROCESS_OTHER_WORKER. */
    public static final String WORKER_UNABLE_TO_PROCESS_OTHER_WORKER = "Worker of type {0} unable to process {1}";

    /** The Constant ELEMENT_DOES_NOT_FIT_CURRENT_AREA. */
    public static final String ELEMENT_DOES_NOT_FIT_CURRENT_AREA = "Element does not fit current area";

    private Html2PdfLogMessageConstant() {
        //Private constructor will prevent the instantiation of this class directly
    }
}
