package org.com.fernando.compare.encoding;

import org.springframework.stereotype.Component;

/**
 * Here we can implement some strategy to chose what Decoder we will use.
 * Just to exercise some patterns.
 *
 * We can use List<IDecoder> and each Decoder can know your algorith name
 */
@Component
public class DecoderFactory {
    private final Base64Decoder base64Decoder;

    public DecoderFactory(Base64Decoder base64Decoder) {
        this.base64Decoder = base64Decoder;
    }

    public IDecoder getDefaultDecoder() {
        return base64Decoder;
    }
}
