package org.com.fernando;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Tag;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Tag("integration")
class BaseRestResourceWithSpringTest {

    @LocalServerPort
    int port;
    TestRestTemplate restTemplate = new TestRestTemplate();
    HttpHeaders headers = new HttpHeaders();

    protected String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    protected String getJsonContentEncoded(String fileName) throws IOException {
        return encode64(getJsonContent(fileName));
    }

    protected String encode64(String raw) {
        return Base64.getEncoder().encodeToString(raw.getBytes());
    }

    protected String getJsonContent(String file) throws IOException {
        String filePath = "/json/" + file + ".json";
        return IOUtils.toString(
                BaseRestResourceWithSpringTest.class.getResourceAsStream(filePath),
                StandardCharsets.UTF_8);
    }
}