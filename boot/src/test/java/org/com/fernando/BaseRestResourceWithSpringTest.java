package org.com.fernando;

import org.junit.jupiter.api.Tag;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;

@Tag("integration")
class BaseRestResourceWithSpringTest {

    @LocalServerPort
    int port;
    TestRestTemplate restTemplate = new TestRestTemplate();
    HttpHeaders headers = new HttpHeaders();

    protected String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}