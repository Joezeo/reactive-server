package com.toocol.common.utils;

import static com.toocol.common.constants.StringConstants.SLASH;

/**
 * @author ：JoeZane (joezane.cn@gmail.com)
 * @date: 2022/3/12 21:00
 * @version: 0.0.1
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    public static String reviseUri(String uri) {
        uri = uri.trim();
        if (startsWith(uri, SLASH)) {
            uri = uri.substring(1);
        }
        if (endsWith(uri, SLASH)) {
            uri = uri.substring(0, uri.length() - 1);
        }
        return uri.toLowerCase();
    }

}
