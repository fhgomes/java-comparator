package org.com.fernando.util.encoding;

import org.com.fernando.share.contracts.IDecoder;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class Base64Decoder implements IDecoder {
    public String decode(String encoded) {
        return new String(Base64.getDecoder().decode(encoded), StandardCharsets.UTF_8);
    }
}
