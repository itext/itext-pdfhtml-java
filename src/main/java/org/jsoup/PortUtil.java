package org.jsoup;

import java.util.Properties;
import java.util.regex.Pattern;

public class PortUtil {

    public static boolean hasMatch(Pattern pattern, String input) {
        return pattern.matcher(input).find();
    }

}
