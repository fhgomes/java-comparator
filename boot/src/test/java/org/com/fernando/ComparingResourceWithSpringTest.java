package org.com.fernando;

import org.com.fernando.share.data.CompareResultDTO;
import org.com.fernando.share.exception.InvalidResultDTO;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;

import java.io.IOException;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class ComparingResourceWithSpringTest extends BaseRestResourceWithSpringTest {

    private static final String API_PATH = "/api/v1/diff/%s";
    private static final String API_PATH_LEFT = API_PATH+"/left";
    private static final String API_PATH_RIGHT = API_PATH+"/right";

    @Nested
    class TestCompareSame {
        @Test
        @DisplayName("Should insert left and right and compare equals")
        void testCompareEqual() throws IOException {
            long referenceID = Calendar.getInstance().getTimeInMillis();

            insertData(referenceID, API_PATH_LEFT, getJsonContentEncoded("json1"));
            insertData(referenceID, API_PATH_RIGHT, getJsonContentEncoded("json1"));

            ResponseEntity<CompareResultDTO> response = callCompare(referenceID, CompareResultDTO.class);
            assertAll(
                    () -> assertEquals(true, response.getBody().isValidEquals()),
                    () -> assertEquals("The contents are the same", response.getBody().getMessage()),
                    () -> assertEquals(200, response.getBody().getStatus())
            );
        }
    }

    @Nested
    class TestCompareDifferent {
        @Test
        @DisplayName("Should insert left and right and compare not same values")
        void testCompareDifferentValues() throws IOException {
            long referenceID = Calendar.getInstance().getTimeInMillis();

            insertData(referenceID, API_PATH_LEFT, getJsonContentEncoded("json1"));
            insertData(referenceID, API_PATH_RIGHT, getJsonContentEncoded("json2_different_value"));

            ResponseEntity<CompareResultDTO> response = callCompare(referenceID, CompareResultDTO.class);
            assertAll(
                    () -> assertEquals(false, response.getBody().isValidEquals()),
                    () -> assertEquals("There are some fields not equal", response.getBody().getMessage()),
                    () -> assertEquals(409, response.getBody().getStatus()),
                    () -> assertEquals("Diff: replace - Path: /name", response.getBody().getDifferences().get(0))
            );
        }

        @Test
        @DisplayName("Should insert left and right and compare not same values")
        void testCompareDifferentStructure() throws IOException {
            long referenceID = Calendar.getInstance().getTimeInMillis();

            insertData(referenceID, API_PATH_LEFT, getJsonContentEncoded("json1"));
            insertData(referenceID, API_PATH_RIGHT, getJsonContentEncoded("json3_different_structure"));

            ResponseEntity<CompareResultDTO> response = callCompare(referenceID, CompareResultDTO.class);
            assertAll(
                    () -> assertEquals(false, response.getBody().isValidEquals()),
                    () -> assertEquals("The size of elements in JSON content are not the same. Left(2) - Right(3)", response.getBody().getMessage()),
                    () -> assertEquals(409, response.getBody().getStatus())
            );
        }
    }

    @Nested
    class TestCompareDifferentNotReady {
        @Test
        @DisplayName("Should insert only left and compare should return error")
        void testCompareOnlyLeft() throws IOException {
            long referenceID = Calendar.getInstance().getTimeInMillis();

            insertData(referenceID, API_PATH_LEFT, getJsonContentEncoded("json1"));

            ResponseEntity<CompareResultDTO> response = callCompare(referenceID, CompareResultDTO.class);
            assertAll(
                    () -> assertEquals("Right content is not posted yet", response.getBody().getMessage()),
                    () -> assertEquals(409, response.getStatusCode().value())
            );
        }

        @Test
        @DisplayName("Should insert only right and compare should return error")
        void testCompareOnlyRight() throws IOException {
            long referenceID = Calendar.getInstance().getTimeInMillis();

            insertData(referenceID, API_PATH_RIGHT, getJsonContentEncoded("json1"));

            ResponseEntity<CompareResultDTO> response = callCompare(referenceID, CompareResultDTO.class);
            assertAll(
                    () -> assertEquals("Left content is not posted yet", response.getBody().getMessage()),
                    () -> assertEquals(409, response.getStatusCode().value())
            );
        }
    }

    private ResponseEntity callCompare(long referenceID, Class responseType) {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        String path = String.format(API_PATH, referenceID);
        return restTemplate.exchange(
                createURLWithPort(path), HttpMethod.GET, entity, responseType);
    }

    private void insertData(long referenceID, String apiPathLeft, String enconded) {
        HttpHeaders headersContent = new HttpHeaders();
        headersContent.add("Content-Type", MediaType.APPLICATION_OCTET_STREAM_VALUE);
        HttpEntity<String> entityLeft = new HttpEntity(enconded.getBytes(), headersContent);
        String pathLeft = String.format(apiPathLeft, referenceID);
        restTemplate.exchange(
                createURLWithPort(pathLeft), HttpMethod.POST, entityLeft, String.class);
    }
}