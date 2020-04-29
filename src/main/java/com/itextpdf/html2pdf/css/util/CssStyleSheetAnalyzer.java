package com.itextpdf.html2pdf.css.util;

import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.styledxmlparser.css.CssDeclaration;
import com.itextpdf.styledxmlparser.css.CssRuleSet;
import com.itextpdf.styledxmlparser.css.CssStatement;
import com.itextpdf.styledxmlparser.css.CssStyleSheet;
import com.itextpdf.styledxmlparser.css.media.CssMediaRule;
import com.itextpdf.styledxmlparser.css.page.CssMarginRule;
import com.itextpdf.styledxmlparser.css.page.CssPageRule;
import com.itextpdf.styledxmlparser.css.parse.CssDeclarationValueTokenizer;

import java.util.Collection;

/**
 * Helper class to analyze the CSS stylesheet, e.g. for presence of some constructs
 */
public class CssStyleSheetAnalyzer {

    private CssStyleSheetAnalyzer() {
    }

    /**
     * Helper method to check if counter(pages) or counters(page) is present anywhere in the CSS.
     * If the presence is detected, it may require additional treatment
     * @param styleSheet CSS stylesheet to analyze
     */
    public static boolean checkPagesCounterPresence(CssStyleSheet styleSheet) {
        return checkPagesCounterPresence(styleSheet.getStatements());
    }

    private static boolean checkPagesCounterPresence(Collection<CssStatement> statements) {
        boolean pagesCounterPresent = false;
        for (CssStatement statement : statements) {
            if (statement instanceof CssMarginRule) {
                pagesCounterPresent = pagesCounterPresent || checkPagesCounterPresence(((CssMarginRule) statement).getStatements());
            } else if (statement instanceof CssMediaRule) {
                pagesCounterPresent = pagesCounterPresent || checkPagesCounterPresence(((CssMediaRule) statement).getStatements());
            } else if (statement instanceof CssPageRule) {
                pagesCounterPresent = pagesCounterPresent || checkPagesCounterPresence(((CssPageRule) statement).getStatements());
            } else if (statement instanceof CssRuleSet) {
                pagesCounterPresent = pagesCounterPresent || checkPagesCounterPresence((CssRuleSet) statement);
            }
        }
        return pagesCounterPresent;
    }

    private static boolean checkPagesCounterPresence(CssRuleSet ruleSet) {
        boolean pagesCounterPresent = false;
        for (CssDeclaration declaration : ruleSet.getImportantDeclarations()) {
            pagesCounterPresent = pagesCounterPresent || checkPagesCounterPresence(declaration);
        }
        for (CssDeclaration declaration : ruleSet.getNormalDeclarations()) {
            pagesCounterPresent = pagesCounterPresent || checkPagesCounterPresence(declaration);
        }
        return pagesCounterPresent;
    }

    private static boolean checkPagesCounterPresence(CssDeclaration declaration) {
        boolean pagesCounterPresent = false;

        // MDN: The counters() function can be used with any CSS property, but support for properties other
        // than content is experimental, and support for the type-or-unit parameter is sparse.
        // iText also does not support counter(pages) anywhere else for now
        if (CssConstants.CONTENT.equals(declaration.getProperty())) {
            CssDeclarationValueTokenizer tokenizer = new CssDeclarationValueTokenizer(declaration.getExpression());
            CssDeclarationValueTokenizer.Token token;

            while ((token = tokenizer.getNextValidToken()) != null) {
                if (!token.isString()) {
                    if (token.getValue().startsWith(CssConstants.COUNTERS + "(")) {
                        String paramsStr = token.getValue().substring(CssConstants.COUNTERS.length() + 1, token.getValue().length() - 1);
                        String[] params = paramsStr.split(",");
                        pagesCounterPresent = pagesCounterPresent || checkCounterFunctionParamsForPagesReferencePresence(params);
                    } else if (token.getValue().startsWith(CssConstants.COUNTER + "(")) {
                        String paramsStr = token.getValue().substring(CssConstants.COUNTER.length() + 1, token.getValue().length() - 1);
                        String[] params = paramsStr.split(",");
                        pagesCounterPresent = pagesCounterPresent || checkCounterFunctionParamsForPagesReferencePresence(params);
                    }
                }
            }
        }

        return pagesCounterPresent;
    }

    private static boolean checkCounterFunctionParamsForPagesReferencePresence(String[] params) {
        return params.length > 0 && CssConstants.PAGES.equals(params[0].trim());
    }

}
