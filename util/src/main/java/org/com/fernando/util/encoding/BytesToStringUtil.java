package org.com.fernando.util.encoding;

import java.nio.charset.StandardCharsets;

public class BytesToStringUtil {
    public static String bytesToString(byte[] rawContent) {
        return new String(rawContent, StandardCharsets.UTF_8);
    }
}
