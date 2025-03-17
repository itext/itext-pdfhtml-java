package com.itextpdf.html2pdf.css.resolve;

import com.itextpdf.html2pdf.ExtendedHtmlConversionITextTest;
import com.itextpdf.styledxmlparser.logs.StyledXmlParserLogMessageConstant;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;

@Tag("IntegrationTest")
public class CssVariableResolverTest extends ExtendedHtmlConversionITextTest {
    private static final String SOURCE_FOLDER = "./src/test/resources/com/itextpdf/html2pdf/css"
            + "/CssVariableResolverTest/";
    private static final String DESTINATION_FOLDER = "./target/test/com/itextpdf/html2pdf/css"
            + "/CssVariableResolverTest/";

    @BeforeAll
    public static void beforeClass() {
        createOrClearDestinationFolder(DESTINATION_FOLDER);
    }

    @Test
    public void variableScopeTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("variableScope", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void reversedDeclarationVariableScopeTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("reversedDeclarationVariableScope", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void defaultValueTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("defaultValue", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void incorrectDefaultValueTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("incorrectDefaultValue", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void simpleRootSelectorTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("simpleRootSelector", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void varInSameContextTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("varInSameContext", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @LogMessages(messages = @LogMessage(messageTemplate = StyledXmlParserLogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION))
    @Test
    public void varOverridingTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("varOverriding", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @LogMessages(messages = @LogMessage(messageTemplate = StyledXmlParserLogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION))
    @Test
    public void varInheritanceTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("varInheritance", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @LogMessages(messages = @LogMessage(messageTemplate = StyledXmlParserLogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION))
    @Test
    public void rootSelectorTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("rootSelector", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void backgroundTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("background", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void varAsShorthandTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("varAsShorthand", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void varInShorthandTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("varInShorthand", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void varsInShorthandTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("varsInShorthand", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @LogMessages(messages = @LogMessage(messageTemplate = StyledXmlParserLogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION))
    @Test
    public void defaultValuesWithValidationTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("defaultValuesWithValidation", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @LogMessages(messages = @LogMessage(messageTemplate = StyledXmlParserLogMessageConstant.INVALID_CSS_PROPERTY_DECLARATION))
    @Test
    public void extraSpacesInVarTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("extraSpacesInVar", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void varInStyleAttributeTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("varInStyleAttribute", SOURCE_FOLDER, DESTINATION_FOLDER);
    }

    @Test
    public void complexTest() throws IOException, InterruptedException {
        convertToPdfAndCompare("complex", SOURCE_FOLDER, DESTINATION_FOLDER);
    }
}
