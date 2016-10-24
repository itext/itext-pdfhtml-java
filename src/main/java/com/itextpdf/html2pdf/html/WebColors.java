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
package com.itextpdf.html2pdf.html;

import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.color.DeviceRgb;

import java.util.HashMap;
import java.util.StringTokenizer;

public class WebColors extends HashMap<String, float[]> {

    private static final long serialVersionUID = 3542523100813372896L;
    /** HashMap containing all the names and corresponding color values. */
    public static final WebColors NAMES = new WebColors();
    static {
        NAMES.put("aliceblue", new float[] { 0xf0, 0xf8, 0xff, 0xff });
        NAMES.put("antiquewhite", new float[] { 0xfa, 0xeb, 0xd7, 0xff });
        NAMES.put("aqua", new float[] { 0x00, 0xff, 0xff, 0xff });
        NAMES.put("aquamarine", new float[] { 0x7f, 0xff, 0xd4, 0xff });
        NAMES.put("azure", new float[] { 0xf0, 0xff, 0xff, 0xff });
        NAMES.put("beige", new float[] { 0xf5, 0xf5, 0xdc, 0xff });
        NAMES.put("bisque", new float[] { 0xff, 0xe4, 0xc4, 0xff });
        NAMES.put("black", new float[] { 0x00, 0x00, 0x00, 0xff });
        NAMES.put("blanchedalmond", new float[] { 0xff, 0xeb, 0xcd, 0xff });
        NAMES.put("blue", new float[] { 0x00, 0x00, 0xff, 0xff });
        NAMES.put("blueviolet", new float[] { 0x8a, 0x2b, 0xe2, 0xff });
        NAMES.put("brown", new float[] { 0xa5, 0x2a, 0x2a, 0xff });
        NAMES.put("burlywood", new float[] { 0xde, 0xb8, 0x87, 0xff });
        NAMES.put("cadetblue", new float[] { 0x5f, 0x9e, 0xa0, 0xff });
        NAMES.put("chartreuse", new float[] { 0x7f, 0xff, 0x00, 0xff });
        NAMES.put("chocolate", new float[] { 0xd2, 0x69, 0x1e, 0xff });
        NAMES.put("coral", new float[] { 0xff, 0x7f, 0x50, 0xff });
        NAMES.put("cornflowerblue", new float[] { 0x64, 0x95, 0xed, 0xff });
        NAMES.put("cornsilk", new float[] { 0xff, 0xf8, 0xdc, 0xff });
        NAMES.put("crimson", new float[] { 0xdc, 0x14, 0x3c, 0xff });
        NAMES.put("cyan", new float[] { 0x00, 0xff, 0xff, 0xff });
        NAMES.put("darkblue", new float[] { 0x00, 0x00, 0x8b, 0xff });
        NAMES.put("darkcyan", new float[] { 0x00, 0x8b, 0x8b, 0xff });
        NAMES.put("darkgoldenrod", new float[] { 0xb8, 0x86, 0x0b, 0xff });
        NAMES.put("darkgray", new float[] { 0xa9, 0xa9, 0xa9, 0xff });
        NAMES.put("darkgreen", new float[] { 0x00, 0x64, 0x00, 0xff });
        NAMES.put("darkkhaki", new float[] { 0xbd, 0xb7, 0x6b, 0xff });
        NAMES.put("darkmagenta", new float[] { 0x8b, 0x00, 0x8b, 0xff });
        NAMES.put("darkolivegreen", new float[] { 0x55, 0x6b, 0x2f, 0xff });
        NAMES.put("darkorange", new float[] { 0xff, 0x8c, 0x00, 0xff });
        NAMES.put("darkorchid", new float[] { 0x99, 0x32, 0xcc, 0xff });
        NAMES.put("darkred", new float[] { 0x8b, 0x00, 0x00, 0xff });
        NAMES.put("darksalmon", new float[] { 0xe9, 0x96, 0x7a, 0xff });
        NAMES.put("darkseagreen", new float[] { 0x8f, 0xbc, 0x8f, 0xff });
        NAMES.put("darkslateblue", new float[] { 0x48, 0x3d, 0x8b, 0xff });
        NAMES.put("darkslategray", new float[] { 0x2f, 0x4f, 0x4f, 0xff });
        NAMES.put("darkturquoise", new float[] { 0x00, 0xce, 0xd1, 0xff });
        NAMES.put("darkviolet", new float[] { 0x94, 0x00, 0xd3, 0xff });
        NAMES.put("deeppink", new float[] { 0xff, 0x14, 0x93, 0xff });
        NAMES.put("deepskyblue", new float[] { 0x00, 0xbf, 0xff, 0xff });
        NAMES.put("dimgray", new float[] { 0x69, 0x69, 0x69, 0xff });
        NAMES.put("dodgerblue", new float[] { 0x1e, 0x90, 0xff, 0xff });
        NAMES.put("firebrick", new float[] { 0xb2, 0x22, 0x22, 0xff });
        NAMES.put("floralwhite", new float[] { 0xff, 0xfa, 0xf0, 0xff });
        NAMES.put("forestgreen", new float[] { 0x22, 0x8b, 0x22, 0xff });
        NAMES.put("fuchsia", new float[] { 0xff, 0x00, 0xff, 0xff });
        NAMES.put("gainsboro", new float[] { 0xdc, 0xdc, 0xdc, 0xff });
        NAMES.put("ghostwhite", new float[] { 0xf8, 0xf8, 0xff, 0xff });
        NAMES.put("gold", new float[] { 0xff, 0xd7, 0x00, 0xff });
        NAMES.put("goldenrod", new float[] { 0xda, 0xa5, 0x20, 0xff });
        NAMES.put("gray", new float[] { 0x80, 0x80, 0x80, 0xff });
        NAMES.put("green", new float[] { 0x00, 0x80, 0x00, 0xff });
        NAMES.put("greenyellow", new float[] { 0xad, 0xff, 0x2f, 0xff });
        NAMES.put("honeydew", new float[] { 0xf0, 0xff, 0xf0, 0xff });
        NAMES.put("hotpink", new float[] { 0xff, 0x69, 0xb4, 0xff });
        NAMES.put("indianred", new float[] { 0xcd, 0x5c, 0x5c, 0xff });
        NAMES.put("indigo", new float[] { 0x4b, 0x00, 0x82, 0xff });
        NAMES.put("ivory", new float[] { 0xff, 0xff, 0xf0, 0xff });
        NAMES.put("khaki", new float[] { 0xf0, 0xe6, 0x8c, 0xff });
        NAMES.put("lavender", new float[] { 0xe6, 0xe6, 0xfa, 0xff });
        NAMES.put("lavenderblush", new float[] { 0xff, 0xf0, 0xf5, 0xff });
        NAMES.put("lawngreen", new float[] { 0x7c, 0xfc, 0x00, 0xff });
        NAMES.put("lemonchiffon", new float[] { 0xff, 0xfa, 0xcd, 0xff });
        NAMES.put("lightblue", new float[] { 0xad, 0xd8, 0xe6, 0xff });
        NAMES.put("lightcoral", new float[] { 0xf0, 0x80, 0x80, 0xff });
        NAMES.put("lightcyan", new float[] { 0xe0, 0xff, 0xff, 0xff });
        NAMES.put("lightgoldenrodyellow", new float[] { 0xfa, 0xfa, 0xd2, 0xff });
        NAMES.put("lightgreen", new float[] { 0x90, 0xee, 0x90, 0xff });
        NAMES.put("lightgrey", new float[] { 0xd3, 0xd3, 0xd3, 0xff });
        NAMES.put("lightpink", new float[] { 0xff, 0xb6, 0xc1, 0xff });
        NAMES.put("lightsalmon", new float[] { 0xff, 0xa0, 0x7a, 0xff });
        NAMES.put("lightseagreen", new float[] { 0x20, 0xb2, 0xaa, 0xff });
        NAMES.put("lightskyblue", new float[] { 0x87, 0xce, 0xfa, 0xff });
        NAMES.put("lightslategray", new float[] { 0x77, 0x88, 0x99, 0xff });
        NAMES.put("lightsteelblue", new float[] { 0xb0, 0xc4, 0xde, 0xff });
        NAMES.put("lightyellow", new float[] { 0xff, 0xff, 0xe0, 0xff });
        NAMES.put("lime", new float[] { 0x00, 0xff, 0x00, 0xff });
        NAMES.put("limegreen", new float[] { 0x32, 0xcd, 0x32, 0xff });
        NAMES.put("linen", new float[] { 0xfa, 0xf0, 0xe6, 0xff });
        NAMES.put("magenta", new float[] { 0xff, 0x00, 0xff, 0xff });
        NAMES.put("maroon", new float[] { 0x80, 0x00, 0x00, 0xff });
        NAMES.put("mediumaquamarine", new float[] { 0x66, 0xcd, 0xaa, 0xff });
        NAMES.put("mediumblue", new float[] { 0x00, 0x00, 0xcd, 0xff });
        NAMES.put("mediumorchid", new float[] { 0xba, 0x55, 0xd3, 0xff });
        NAMES.put("mediumpurple", new float[] { 0x93, 0x70, 0xdb, 0xff });
        NAMES.put("mediumseagreen", new float[] { 0x3c, 0xb3, 0x71, 0xff });
        NAMES.put("mediumslateblue", new float[] { 0x7b, 0x68, 0xee, 0xff });
        NAMES.put("mediumspringgreen", new float[] { 0x00, 0xfa, 0x9a, 0xff });
        NAMES.put("mediumturquoise", new float[] { 0x48, 0xd1, 0xcc, 0xff });
        NAMES.put("mediumvioletred", new float[] { 0xc7, 0x15, 0x85, 0xff });
        NAMES.put("midnightblue", new float[] { 0x19, 0x19, 0x70, 0xff });
        NAMES.put("mintcream", new float[] { 0xf5, 0xff, 0xfa, 0xff });
        NAMES.put("mistyrose", new float[] { 0xff, 0xe4, 0xe1, 0xff });
        NAMES.put("moccasin", new float[] { 0xff, 0xe4, 0xb5, 0xff });
        NAMES.put("navajowhite", new float[] { 0xff, 0xde, 0xad, 0xff });
        NAMES.put("navy", new float[] { 0x00, 0x00, 0x80, 0xff });
        NAMES.put("oldlace", new float[] { 0xfd, 0xf5, 0xe6, 0xff });
        NAMES.put("olive", new float[] { 0x80, 0x80, 0x00, 0xff });
        NAMES.put("olivedrab", new float[] { 0x6b, 0x8e, 0x23, 0xff });
        NAMES.put("orange", new float[] { 0xff, 0xa5, 0x00, 0xff });
        NAMES.put("orangered", new float[] { 0xff, 0x45, 0x00, 0xff });
        NAMES.put("orchid", new float[] { 0xda, 0x70, 0xd6, 0xff });
        NAMES.put("palegoldenrod", new float[] { 0xee, 0xe8, 0xaa, 0xff });
        NAMES.put("palegreen", new float[] { 0x98, 0xfb, 0x98, 0xff });
        NAMES.put("paleturquoise", new float[] { 0xaf, 0xee, 0xee, 0xff });
        NAMES.put("palevioletred", new float[] { 0xdb, 0x70, 0x93, 0xff });
        NAMES.put("papayawhip", new float[] { 0xff, 0xef, 0xd5, 0xff });
        NAMES.put("peachpuff", new float[] { 0xff, 0xda, 0xb9, 0xff });
        NAMES.put("peru", new float[] { 0xcd, 0x85, 0x3f, 0xff });
        NAMES.put("pink", new float[] { 0xff, 0xc0, 0xcb, 0xff });
        NAMES.put("plum", new float[] { 0xdd, 0xa0, 0xdd, 0xff });
        NAMES.put("powderblue", new float[] { 0xb0, 0xe0, 0xe6, 0xff });
        NAMES.put("purple", new float[] { 0x80, 0x00, 0x80, 0xff });
        NAMES.put("red", new float[] { 0xff, 0x00, 0x00, 0xff });
        NAMES.put("rosybrown", new float[] { 0xbc, 0x8f, 0x8f, 0xff });
        NAMES.put("royalblue", new float[] { 0x41, 0x69, 0xe1, 0xff });
        NAMES.put("saddlebrown", new float[] { 0x8b, 0x45, 0x13, 0xff });
        NAMES.put("salmon", new float[] { 0xfa, 0x80, 0x72, 0xff });
        NAMES.put("sandybrown", new float[] { 0xf4, 0xa4, 0x60, 0xff });
        NAMES.put("seagreen", new float[] { 0x2e, 0x8b, 0x57, 0xff });
        NAMES.put("seashell", new float[] { 0xff, 0xf5, 0xee, 0xff });
        NAMES.put("sienna", new float[] { 0xa0, 0x52, 0x2d, 0xff });
        NAMES.put("silver", new float[] { 0xc0, 0xc0, 0xc0, 0xff });
        NAMES.put("skyblue", new float[] { 0x87, 0xce, 0xeb, 0xff });
        NAMES.put("slateblue", new float[] { 0x6a, 0x5a, 0xcd, 0xff });
        NAMES.put("slategray", new float[] { 0x70, 0x80, 0x90, 0xff });
        NAMES.put("snow", new float[] { 0xff, 0xfa, 0xfa, 0xff });
        NAMES.put("springgreen", new float[] { 0x00, 0xff, 0x7f, 0xff });
        NAMES.put("steelblue", new float[] { 0x46, 0x82, 0xb4, 0xff });
        NAMES.put("tan", new float[] { 0xd2, 0xb4, 0x8c, 0xff });
        NAMES.put("teal", new float[] { 0x00, 0x80, 0x80, 0xff });
        NAMES.put("thistle", new float[] { 0xd8, 0xbf, 0xd8, 0xff });
        NAMES.put("tomato", new float[] { 0xff, 0x63, 0x47, 0xff });
        NAMES.put("transparent", new float[] { 0xff, 0xff, 0xff, 0x00 });
        NAMES.put("turquoise", new float[] { 0x40, 0xe0, 0xd0, 0xff });
        NAMES.put("violet", new float[] { 0xee, 0x82, 0xee, 0xff });
        NAMES.put("wheat", new float[] { 0xf5, 0xde, 0xb3, 0xff });
        NAMES.put("white", new float[] { 0xff, 0xff, 0xff, 0xff });
        NAMES.put("whitesmoke", new float[] { 0xf5, 0xf5, 0xf5, 0xff });
        NAMES.put("yellow", new float[] { 0xff, 0xff, 0x00, 0xff });
        NAMES.put("yellowgreen", new float[] { 0x9a, 0xcd, 0x32, 0xff });
    }

    public static boolean isColorProperty(String value) {
        return value.contains("rgb(") || value.contains("rgba(") || value.contains("#") || WebColors.NAMES.containsKey(value.toLowerCase());
    }

    /**
     * Gives you a BaseColor based on a name.
     *
     * @param name
     *            a name such as black, violet, cornflowerblue or #RGB or
     *            #RRGGBB or RGB or RRGGBB or rgb(R,G,B) or #RRGGBBAA
     * @return the corresponding BaseColor object. Never returns null.
     * @throws IllegalArgumentException
     *             if the String isn't a know representation of a color.
     */
    public static Color getRGBColor(String name) {
        float[] color = { 0, 0, 0, 255 };
        String colorName = name.toLowerCase();
        boolean colorStrWithoutHash = missingHashColorFormat(colorName);
        if (colorName.startsWith("#") || colorStrWithoutHash) {
            if (!colorStrWithoutHash) {
                // lop off the # to unify hex parsing.
                colorName = colorName.substring(1);
            }
            if (colorName.length() == 3) {
                String red = colorName.substring(0, 1);
                color[0] = Integer.parseInt(red + red, 16);
                String green = colorName.substring(1, 2);
                color[1] = Integer.parseInt(green + green, 16);
                String blue = colorName.substring(2);
                color[2] = Integer.parseInt(blue + blue, 16);
                return new DeviceRgb(color[0], color[1], color[2]);
            }
            if (colorName.length() == 6) {
                color[0] = Integer.parseInt(colorName.substring(0, 2), 16);
                color[1] = Integer.parseInt(colorName.substring(2, 4), 16);
                color[2] = Integer.parseInt(colorName.substring(4), 16);
                return new DeviceRgb(color[0], color[1], color[2]);
            }
            if (colorName.length() == 8) {
                color[0] = Integer.parseInt(colorName.substring(0, 2), 16);
                color[1] = Integer.parseInt(colorName.substring(2, 4), 16);
                color[2] = Integer.parseInt(colorName.substring(4, 6), 16);
                color[3] = Integer.parseInt(colorName.substring(6), 16);
                //@TODO DEVSIX-896 RGBA Colors
                return new DeviceRgb(color[0], color[1], color[2]);
            }
            throw new IllegalArgumentException("Unknown color format.");
        }

        if (colorName.startsWith("rgb(")) {
            final String delim = "rgb(), \t\r\n\f";
            StringTokenizer tok = new StringTokenizer(colorName, delim);
            for (int k = 0; k < 3; ++k) {
                if (tok.hasMoreElements()) {
                    color[k] = getRGBChannelValue(tok.nextToken());
                    color[k] = Math.max(0, color[k]);
                    color[k] = Math.min(255, color[k]);
                }
            }
            return new DeviceRgb(color[0], color[1], color[2]);
        }

        //@TODO DEVSIX-896 RGBA Colors
//        if (colorName.startsWith("rgba(")) {
//            final String delim = "rgba(), \t\r\n\f";
//            StringTokenizer tok = new StringTokenizer(colorName, delim);
//            for (int k = 0; k < 3; ++k) {
//                if (tok.hasMoreElements()) {
//                    color[k] = getRGBChannelValue(tok.nextToken());
//                    color[k] = Math.max(0, color[k]);
//                    color[k] = Math.min(255, color[k]);
//                }
//            }
//            if (tok.hasMoreElements()) {
//                color[3] = (int)(255 * Float.parseFloat(tok.nextToken()) + 0.5);
//            }
//            return new BaseColor(color[0], color[1], color[2], color[3]);
//        }

//        if (!NAMES.containsKey(colorName)) {
//            throw new IllegalArgumentException(
//                    MessageLocalization.getComposedMessage("color.not.found",
//                            new String[] { colorName }));
//        }
        color = NAMES.get(colorName);
        //@TODO DEVSIX-896 RGBA Colors
        return new DeviceRgb(color[0], color[1], color[2]);
    }

    private static int getRGBChannelValue(String rgbChannel) {
        if (rgbChannel.endsWith("%")) {
            return Integer.parseInt(rgbChannel.substring(0,
                    rgbChannel.length() - 1)) * 255 / 100;
        } else {
            return Integer.parseInt(rgbChannel);
        }

    }

    /**
     * A web color string without the leading # will be 3 or 6 characters long
     * and all those characters will be hex digits. NOTE: colStr must be all
     * lower case or the current hex letter test will fail.
     *
     * @param colStr
     *            A non-null, lower case string that might describe an RGB color
     *            in hex.
     * @return Is this a web color hex string without the leading #?
     * @since 5.0.6
     */
    private static boolean missingHashColorFormat(String colStr) {
        int len = colStr.length();
        if (len == 3 || len == 6) {
            // and it just contains hex chars 0-9, a-f, A-F
            String match = "[0-9a-f]{" + len + "}";
            return colStr.matches(match);
        }
        return false;
    }
}
