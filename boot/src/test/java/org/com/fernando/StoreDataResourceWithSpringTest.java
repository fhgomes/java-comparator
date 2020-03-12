package org.com.fernando;

import org.com.fernando.share.exception.InvalidResultDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;

import java.io.IOException;
import java.util.Calendar;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class StoreDataResourceWithSpringTest extends BaseRestResourceWithSpringTest {

    private static final String API_PATH = "/api/v1/diff/%s";
    private static final String API_PATH_LEFT = API_PATH+"/left";
    private static final String API_PATH_RIGHT = API_PATH+"/right";

    @Nested
    class TestStoreLeft {
        @Test
        @DisplayName("Should insert left")
        void testStoreLeft() throws IOException {
            long referenceID = Calendar.getInstance().getTimeInMillis();

            ResponseEntity<String> response = insertData(referenceID, API_PATH_LEFT, getJsonContentEncoded("json1"));

            assertAll(
                    () -> assertEquals(202, response.getStatusCodeValue()),
                    () -> assertNotNull(response.getBody())
            );
        }

        @Test
        @DisplayName("Should return error when left already exists")
        void testStoreLeftDuplicated() throws IOException {
            long referenceID = Calendar.getInstance().getTimeInMillis();

            ResponseEntity<String> response = insertData(referenceID, API_PATH_LEFT, getJsonContentEncoded("json1"));
            ResponseEntity<InvalidResultDTO> responseDuplicated = insertDataInvalid(referenceID, API_PATH_LEFT, getJsonContentEncoded("json1"));

            assertAll(
                    () -> assertEquals(412, responseDuplicated.getStatusCodeValue()),
                    () -> assertEquals("content.duplicated", responseDuplicated.getBody().getCode()),
                    () -> assertEquals("Content already exists to this ID and Direction", responseDuplicated.getBody().getMessage())
            );
        }

        @Test
        @DisplayName("Should return error when left is empty")
        void testStoreLeftEmpty() {
            long referenceID = Calendar.getInstance().getTimeInMillis();

            ResponseEntity<InvalidResultDTO> response = insertDataInvalid(referenceID, API_PATH_LEFT, "");

            assertAll(
                    () -> assertEquals(412, response.getStatusCodeValue()),
                    () -> assertEquals("content.invalid.empty", response.getBody().getCode()),
                    () -> assertEquals("Content is empty", response.getBody().getMessage())
            );
        }

        @Test
        @DisplayName("Should return error when left is with less than min size")
        void testStoreLeftMinSize() {
            long referenceID = Calendar.getInstance().getTimeInMillis();

            ResponseEntity<InvalidResultDTO> response = insertDataInvalid(referenceID, API_PATH_LEFT, "123");

            assertAll(
                    () -> assertEquals(412, response.getStatusCodeValue()),
                    () -> assertEquals("content.invalid.min_size", response.getBody().getCode()),
                    () -> assertEquals("Content don't have the minimum size (4)", response.getBody().getMessage())
            );
        }
    }

    @Nested
    class TestStoreRight {
        @Test
        @DisplayName("Should insert right")
        void testStoreRight() throws IOException {
            long referenceID = Calendar.getInstance().getTimeInMillis();

            ResponseEntity<String> response = insertData(referenceID, API_PATH_RIGHT, getJsonContentEncoded("json1"));

            assertAll(
                    () -> assertEquals(202, response.getStatusCodeValue()),
                    () -> assertNotNull(response.getBody())
            );
        }

        @Test
        @DisplayName("Should return error when right already exists")
        void testStoreRightDuplicated() throws IOException {
            long referenceID = Calendar.getInstance().getTimeInMillis();

            ResponseEntity<String> response = insertData(referenceID, API_PATH_RIGHT, getJsonContentEncoded("json1"));
            ResponseEntity<InvalidResultDTO> responseDuplicated = insertDataInvalid(referenceID, API_PATH_RIGHT, getJsonContentEncoded("json1"));

            assertAll(
                    () -> assertEquals(412, responseDuplicated.getStatusCodeValue()),
                    () -> assertEquals("content.duplicated", responseDuplicated.getBody().getCode()),
                    () -> assertEquals("Content already exists to this ID and Direction", responseDuplicated.getBody().getMessage())
            );
        }

        @Test
        @DisplayName("Should return error when right is empty")
        void testStoreRightEmpty() {
            long referenceID = Calendar.getInstance().getTimeInMillis();

            ResponseEntity<InvalidResultDTO> response = insertDataInvalid(referenceID, API_PATH_RIGHT, "");

            assertAll(
                    () -> assertEquals(412, response.getStatusCodeValue()),
                    () -> assertEquals("content.invalid.empty", response.getBody().getCode()),
                    () -> assertEquals("Content is empty", response.getBody().getMessage())
            );
        }

        @Test
        @DisplayName("Should return error when RIGHT is with less than min size")
        void testStoreRightMinSize() {
            long referenceID = Calendar.getInstance().getTimeInMillis();

            ResponseEntity<InvalidResultDTO> response = insertDataInvalid(referenceID, API_PATH_RIGHT, "123");

            assertAll(
                    () -> assertEquals(412, response.getStatusCodeValue()),
                    () -> assertEquals("content.invalid.min_size", response.getBody().getCode()),
                    () -> assertEquals("Content don't have the minimum size (4)", response.getBody().getMessage())
            );
        }
    }

    private ResponseEntity<String> insertData(long referenceID, String apiPath, String body) {
        HttpHeaders headersContent = new HttpHeaders();
        headersContent.add("Content-Type", MediaType.APPLICATION_OCTET_STREAM_VALUE);
        HttpEntity<String> entity = new HttpEntity<>(body, headersContent);
        String path = format(apiPath, referenceID);
        ResponseEntity<String> exchange = restTemplate.exchange(
                createURLWithPort(path), HttpMethod.POST, entity, String.class);
        return exchange;
    }

    private ResponseEntity<InvalidResultDTO> insertDataInvalid(long referenceID, String apiPath, String body) {
        HttpHeaders headersContent = new HttpHeaders();
        headersContent.add("Content-Type", MediaType.APPLICATION_OCTET_STREAM_VALUE);
        HttpEntity<String> entity = new HttpEntity(body.getBytes(), headersContent);
        String path = format(apiPath, referenceID);
        ResponseEntity<InvalidResultDTO> exchange = restTemplate.exchange(
                createURLWithPort(path), HttpMethod.POST, entity, InvalidResultDTO.class);
        return exchange;
    }
}