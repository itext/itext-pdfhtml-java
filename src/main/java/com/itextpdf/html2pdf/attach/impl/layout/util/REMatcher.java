package com.itextpdf.html2pdf.attach.impl.layout.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class REMatcher {
    private Pattern pattern;
    private Matcher matcher;

    public REMatcher(String s) {
        pattern = Pattern.compile(s, Pattern.UNICODE_CHARACTER_CLASS | Pattern.DOTALL);
    }

    public void setStringForMatch(String s) {
        matcher = pattern.matcher(s);
    }

    public boolean find() {
        return matcher.find();
    }

    public String group(int index) {
        return matcher.group(index);
    }
}
