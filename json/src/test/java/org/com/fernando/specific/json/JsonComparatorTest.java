package org.com.fernando.specific.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.com.fernando.share.ObjectDirection;
import org.com.fernando.share.contracts.FileType;
import org.com.fernando.share.data.CompareResultDTO;
import org.com.fernando.share.data.DataContentDTO;
import org.com.fernando.util.MessagesWrapper;
import org.com.fernando.util.encoding.Base64Decoder;
import org.com.fernando.util.encoding.DecoderFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JsonComparatorTest {

    @InjectMocks
    JsonComparator jsonComparator;

    @Mock
    DecoderFactory decoderFactory;

    @Mock
    Base64Decoder base64Decoder;

    @Mock
    MessagesWrapper messagesWrapper;

    @Mock
    JsonHelper jsonHelper;

    @Mock
    DataContentDTO dataLeftMock;

    @Mock
    DataContentDTO dataRightMock;

    String jsonName= "{\"name\":\"hi\"}";
    String jsonName2= "{\"name\":\"hi2\"}";
    String jsonNameAge = "{\"name\":\"hi\", \"age\":31}";

    @Nested
    class TestsDiff {
        @BeforeEach
        void setUp() {
            when(decoderFactory.getDefaultDecoder()).thenReturn(base64Decoder);
        }

        @Test
        @DisplayName("Should find diff when structure is different")
        void findDiffStructure() throws IOException {
            configureJsonMocks("encL", jsonName, ObjectDirection.LEFT);
            configureJsonMocks("encR", jsonNameAge, ObjectDirection.RIGHT);

            when(dataLeftMock.getRawContent()).thenReturn("encL".getBytes());
            when(dataRightMock.getRawContent()).thenReturn("encR".getBytes());

            CompareResultDTO result = jsonComparator.findDiff(dataLeftMock, dataRightMock);

            assertAll(
                    () -> assertFalse(result.isValidEquals()),
                    () -> assertEquals("diff.json.size_not_equals", result.getCode()),
                    () -> assertTrue(result.getDifferences().isEmpty())
            );
        }

        @Test
        @DisplayName("Should find diff when structure is different")
        void findDiffValues() throws IOException {
            JsonNode nodeL = configureJsonMocks("encL", jsonName, ObjectDirection.LEFT);
            JsonNode nodeR = configureJsonMocks("encR", jsonName2, ObjectDirection.RIGHT);

            when(dataLeftMock.getRawContent()).thenReturn("encL".getBytes());
            when(dataRightMock.getRawContent()).thenReturn("encR".getBytes());
            when(jsonHelper.getJsonDiff(nodeL, nodeR)).thenReturn(Arrays.asList("diff value"));

            CompareResultDTO result = jsonComparator.findDiff(dataLeftMock, dataRightMock);
            assertAll(
                    () -> assertFalse(result.isValidEquals()),
                    () -> assertEquals("diff.json.fields_not_equals", result.getCode()),
                    () -> assertEquals("diff value", result.getDifferences().get(0))
            );
        }

        private JsonNode configureJsonMocks(String encoded, String json, ObjectDirection dir) throws JsonProcessingException {
            when(base64Decoder.decode(encoded)).thenReturn(json);
            JsonNode node = new ObjectMapper().readTree(json);
            when(jsonHelper.readAsJson(json, dir)).thenReturn(node);
            return node;
        }
    }

    @Test
    @DisplayName("Should return correct file type")
    void getFileType() {
        assertEquals(FileType.JSON, jsonComparator.getFileType());
    }
}