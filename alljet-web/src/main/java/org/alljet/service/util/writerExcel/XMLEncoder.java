/*
 * FileName: XMLEncoder.java
 * Author:   v_qinyuchen
 * Date:     2016年1月18日 上午11:42:28
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package org.alljet.service.util.writerExcel;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author v_qinyuchen
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class XMLEncoder {
    // private static final String[] xmlCode = new String[256];
    //
    // static {
    // // Special characters
    // xmlCode['\''] = "'";
    // xmlCode['\"'] = "\""; // double quote
    // xmlCode['&'] = "&"; // ampersand
    // xmlCode['<'] = "<"; // lower than
    // xmlCode['>'] = ">"; // greater than
    // xmlCode['&'] = "&";
    // }

    private static final String[] xmlCode = new String[256];

    static {
        // Special characters
        xmlCode['\''] = "'";
        xmlCode['\"'] = "\""; // double quote
        xmlCode['&'] = "&"; // ampersand
        xmlCode['<'] = "&lt;"; // lower than
        xmlCode['>'] = "&gt;"; // greater than
        xmlCode['&'] = "&amp;";
    }

    /**
     * <p>
     * Encode the given text into xml.
     * </p>
     * 
     * @param string the text to encode
     * @return the encoded string
     */
    public static String encode(String string) {
        if (string == null)
            return "";
        int n = string.length();
        char character;
        String xmlchar;
        StringBuffer buffer = new StringBuffer();
        // loop over all the characters of the String.
        for (int i = 0; i < n; i++) {
            character = string.charAt(i);
            // the xmlcode of these characters are added to a StringBuffer one by one
            try {
                xmlchar = xmlCode[character];
                if (xmlchar == null) {
                    buffer.append(character);
                } else {
                    buffer.append(xmlCode[character]);
                }
            } catch (ArrayIndexOutOfBoundsException aioobe) {
                buffer.append(character);
            }
        }
        return buffer.toString();
    }
}
