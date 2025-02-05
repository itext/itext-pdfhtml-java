/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2025 Apryse Group NV
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

    private static final int TARGET_COUNTER_MIN_PARAMS_SIZE = 2;
    private static final int TARGET_COUNTERS_MIN_PARAMS_SIZE = 3;

    private CssStyleSheetAnalyzer() {
    }

    /**
     * Helper method to check if non-page(s) target-counter(s) is present anywhere in the CSS.
     * If presence is detected, it may require additional treatment
     *
     * @param styleSheet CSS stylesheet to analyze
     * @return <code>true</code> in case any non-page(s) target-counter(s) are present in CSS declarations,
     * or <code>false</code> otherwise
     */
    public static boolean checkNonPagesTargetCounterPresence(CssStyleSheet styleSheet) {
        return checkNonPagesTargetCounterPresence(styleSheet.getStatements());
    }

    private static boolean checkNonPagesTargetCounterPresence(Collection<CssStatement> statements) {
        boolean nonPagesTargetCounterPresent = false;
        for (final CssStatement statement : statements) {
            boolean checkNonPagesTargetCounterPresenceResult = false;
            if (statement instanceof CssMarginRule) {
                checkNonPagesTargetCounterPresenceResult =
                        checkNonPagesTargetCounterPresence(((CssMarginRule) statement).getStatements());
            } else if (statement instanceof CssMediaRule) {
                checkNonPagesTargetCounterPresenceResult =
                        checkNonPagesTargetCounterPresence(((CssMediaRule) statement).getStatements());
            } else if (statement instanceof CssPageRule) {
                checkNonPagesTargetCounterPresenceResult =
                        checkNonPagesTargetCounterPresence(((CssPageRule) statement).getStatements());
            } else if (statement instanceof CssRuleSet) {
                checkNonPagesTargetCounterPresenceResult =
                        checkNonPagesTargetCounterPresence((CssRuleSet) statement);
            }
            nonPagesTargetCounterPresent = nonPagesTargetCounterPresent || checkNonPagesTargetCounterPresenceResult;
        }
        return nonPagesTargetCounterPresent;
    }

    private static boolean checkNonPagesTargetCounterPresence(CssRuleSet ruleSet) {
        boolean pagesCounterPresent = false;
        for (final CssDeclaration declaration : ruleSet.getImportantDeclarations()) {
            pagesCounterPresent = pagesCounterPresent || checkNonPagesTargetCounterPresence(declaration);
        }
        for (final CssDeclaration declaration : ruleSet.getNormalDeclarations()) {
            pagesCounterPresent = pagesCounterPresent || checkNonPagesTargetCounterPresence(declaration);
        }
        return pagesCounterPresent;
    }

    private static boolean checkNonPagesTargetCounterPresence(CssDeclaration declaration) {
        boolean nonPagesTargetCounterPresent = false;

        if (CssConstants.CONTENT.equals(declaration.getProperty())) {
            CssDeclarationValueTokenizer tokenizer = new CssDeclarationValueTokenizer(declaration.getExpression());
            CssDeclarationValueTokenizer.Token token;

            while ((token = tokenizer.getNextValidToken()) != null) {
                if (token.isString()) {
                    continue;
                }
                if (token.getValue().startsWith(CssConstants.TARGET_COUNTER + "(")) {
                    final String paramsStr = token.getValue()
                            .substring(CssConstants.TARGET_COUNTER.length() + 1, token.getValue().length() - 1);
                    final String[] params = paramsStr.split(",");
                    nonPagesTargetCounterPresent = nonPagesTargetCounterPresent ||
                            (params.length >= TARGET_COUNTER_MIN_PARAMS_SIZE &&
                                    !checkTargetCounterParamsForPageOrPagesReferencePresence(params));
                } else if (token.getValue().startsWith(CssConstants.TARGET_COUNTERS + "(")) {
                    final String paramsStr = token.getValue()
                            .substring(CssConstants.TARGET_COUNTERS.length() + 1, token.getValue().length() - 1);
                    final String[] params = paramsStr.split(",");
                    nonPagesTargetCounterPresent = nonPagesTargetCounterPresent ||
                            (params.length >= TARGET_COUNTERS_MIN_PARAMS_SIZE &&
                                    !checkTargetCounterParamsForPageOrPagesReferencePresence(params));
                }
            }
        }

        return nonPagesTargetCounterPresent;
    }

    /**
     * Helper method to check if counter(pages) or counters(pages) is present anywhere in the CSS.
     * If the presence is detected, it may require additional treatment
     *
     * @param styleSheet CSS stylesheet to analyze
     * @return <code>true</code> in case any "pages" counters are present in CSS declarations,
     * or <code>false</code> otherwise
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
                if (token.isString()) {
                    continue;
                }
                if (token.getValue().startsWith(CssConstants.COUNTERS + "(")) {
                    String paramsStr = token.getValue().substring(CssConstants.COUNTERS.length() + 1, token.getValue().length() - 1);
                    String[] params = paramsStr.split(",");
                    pagesCounterPresent = pagesCounterPresent || checkCounterFunctionParamsForPagesReferencePresence(params);
                } else if (token.getValue().startsWith(CssConstants.COUNTER + "(")) {
                    String paramsStr = token.getValue().substring(CssConstants.COUNTER.length() + 1, token.getValue().length() - 1);
                    String[] params = paramsStr.split(",");
                    pagesCounterPresent = pagesCounterPresent || checkCounterFunctionParamsForPagesReferencePresence(params);
                } else if (token.getValue().startsWith(CssConstants.TARGET_COUNTER + "(")) {
                    final String paramsStr = token.getValue()
                            .substring(CssConstants.TARGET_COUNTER.length() + 1, token.getValue().length() - 1);
                    final String[] params = paramsStr.split(",");
                    pagesCounterPresent = pagesCounterPresent ||
                            (params.length >= TARGET_COUNTER_MIN_PARAMS_SIZE &&
                                    checkTargetCounterParamsForPageOrPagesReferencePresence(params));
                } else if (token.getValue().startsWith(CssConstants.TARGET_COUNTERS + "(")) {
                    final String paramsStr = token.getValue()
                            .substring(CssConstants.TARGET_COUNTERS.length() + 1, token.getValue().length() - 1);
                    final String[] params = paramsStr.split(",");
                    pagesCounterPresent = pagesCounterPresent ||
                            (params.length >= TARGET_COUNTERS_MIN_PARAMS_SIZE &&
                                    checkTargetCounterParamsForPageOrPagesReferencePresence(params));
                }
            }
        }

        return pagesCounterPresent;
    }

    private static boolean checkCounterFunctionParamsForPagesReferencePresence(String[] params) {
        return params.length > 0 && CssConstants.PAGES.equals(params[0].trim());
    }

    private static boolean checkTargetCounterParamsForPageOrPagesReferencePresence(String[] params) {
        return CssConstants.PAGE.equals(params[1].trim()) || CssConstants.PAGES.equals(params[1].trim());
    }

}
