package org.com.fernando;

import org.com.fernando.share.data.CompareResultDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Calendar;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class ComparingResourceWithSpringTest extends BaseRestResourceWithSpringTest {

    private static final String API_PATH = "/api/v1/diff/%s";
    private static final String API_PATH_LEFT = API_PATH+"/left";
    private static final String API_PATH_RIGHT = API_PATH+"/right";

    @Test
    @DisplayName("Should insert left and right and compare equals")
    void testCompareEqual() {
        long referenceID = Calendar.getInstance().getTimeInMillis();

        HttpEntity<String> entityLeft = new HttpEntity<>("ewogICJuYW1lIjogImZlcm5hbmRvIiwKICAiYWdlIjogMzEKfQ==", headers);
        String pathLeft = String.format(API_PATH_LEFT, referenceID);
        ResponseEntity<String> responseLeft = restTemplate.exchange(
                createURLWithPort(pathLeft), HttpMethod.POST, entityLeft, String.class);

        HttpEntity<String> entityRight = new HttpEntity<>("ewogICJuYW1lIjogImZlcm5hbmRvIiwKICAiYWdlIjogMzEKfQ==", headers);
        String pathRight = String.format(API_PATH_RIGHT, referenceID);
        ResponseEntity<String> responseRight = restTemplate.exchange(
                createURLWithPort(pathRight), HttpMethod.POST, entityRight, String.class);

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        String path = String.format(API_PATH, referenceID);
        ResponseEntity<CompareResultDTO> response = restTemplate.exchange(
                createURLWithPort(path), HttpMethod.GET, entity, CompareResultDTO.class);
        assertEquals(true, response.getBody().isValidEquals());
    }
}