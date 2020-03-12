package org.com.fernando.util.encoding;

import org.com.fernando.share.contracts.IDecoder;
import org.springframework.stereotype.Component;

import java.util.Base64;

import static org.com.fernando.util.encoding.BytesToStringUtil.bytesToString;

@Component
public class Base64Decoder implements IDecoder {
    public String decode(String encoded) {
        return bytesToString(Base64.getDecoder().decode(encoded));
    }
}
