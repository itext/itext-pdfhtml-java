package org.jsoup.examples;

import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.MessageFormat;

/**
 * Example program to list links from a URL.
 */
public class ListLinks {
    public static void main(String[] args) throws IOException {
        Validate.isTrue(args.length == 1, "usage: supply url to fetch");
        String url = args[0];
        print("Fetching {0}...", url);

        Document doc = Jsoup.connect(url).get();
        Elements links = doc.select("a[href]");
        Elements media = doc.select("[src]");
        Elements imports = doc.select("link[href]");

        print("\nMedia: ({0})", media.size());
        for (Element src : media) {
            if (src.tagName().equals("img"))
                print(" * {0}: <{1}> {2}x{3} ({4})",
                        src.tagName(), src.attr("abs:src"), src.attr("width"), src.attr("height"),
                        trim(src.attr("alt"), 20));
            else
                print(" * {0}: <{1}>", src.tagName(), src.attr("abs:src"));
        }

        print("\nImports: ({0})", imports.size());
        for (Element link : imports) {
            print(" * {0} <{1}> ({2})", link.tagName(),link.attr("abs:href"), link.attr("rel"));
        }

        print("\nLinks: ({0})", links.size());
        for (Element link : links) {
            print(" * a: <{0}>  ({1})", link.attr("abs:href"), trim(link.text(), 35));
        }
    }

    private static void print(String msg, Object... args) {
        System.out.println(MessageFormat.format(msg, args));
    }

    private static String trim(String s, int width) {
        if (s.length() > width)
            return s.substring(0, width-1) + ".";
        else
            return s;
    }
}
