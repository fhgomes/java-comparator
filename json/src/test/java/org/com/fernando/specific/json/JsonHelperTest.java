package org.com.fernando.specific.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.com.fernando.share.ObjectDirection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JsonHelperTest {

    @InjectMocks
    JsonHelper jsonHelper;

    @Spy
    ObjectMapper objectMapper = new ObjectMapper();

    String jsonName= "{\"name\":\"hi\"}";
    String jsonName2= "{\"name\":\"hi2\"}";
    String jsonAge= "{\"age\":31}";


    @Test
    @DisplayName("Should read json string to Object")
    void readAsJson() {
        JsonNode jsonNode = jsonHelper.readAsJson(jsonName, ObjectDirection.LEFT);
        assertEquals("hi", jsonNode.get("name").asText());
    }

    @Test
    @DisplayName("Should get diff from json values")
    void getJsonDiffValue() {
        JsonNode jsonNode = jsonHelper.readAsJson(jsonName, ObjectDirection.LEFT);
        JsonNode jsonNode2 = jsonHelper.readAsJson(jsonName2, ObjectDirection.RIGHT);
        List<String> diffs = jsonHelper.getJsonDiff(jsonNode, jsonNode2);
        assertAll(
                () -> assertEquals(1, diffs.size()),
                () -> assertEquals("Diff: replace - Path: /name", diffs.get(0))
        );
    }

    @Test
    @DisplayName("Should get diff from json field")
    void getJsonDiffField() {
        JsonNode jsonNode = jsonHelper.readAsJson(jsonName, ObjectDirection.LEFT);
        JsonNode jsonNode3 = jsonHelper.readAsJson(jsonAge, ObjectDirection.RIGHT);
        List<String> diffs = jsonHelper.getJsonDiff(jsonNode, jsonNode3);
        assertAll(
                () -> assertEquals(2, diffs.size()),
                () -> assertEquals("Diff: remove - Path: /name", diffs.get(0)),
                () -> assertEquals("Diff: add - Path: /age", diffs.get(1))
        );

    }
}