package org.com.fernando.specific.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flipkart.zjsonpatch.JsonDiff;
import org.com.fernando.share.ObjectDirection;
import org.com.fernando.share.exception.ComparingException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.com.fernando.specific.json.common.JsonMsgConstants.DIFF_JSON_ERROR_INVALID_STRUCTURE;

@Component
public class JsonHelper {

    private final ObjectMapper objectMapper;

    public JsonHelper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public JsonNode readAsJson(String decoded, ObjectDirection direction) {
        try {
            return objectMapper.readTree(decoded);
        } catch (JsonProcessingException e) {
            throw new ComparingException(DIFF_JSON_ERROR_INVALID_STRUCTURE, new Object[]{direction});
        }
    }

    public List<String> getJsonDiff(JsonNode leftJObject, JsonNode rightJObject) {
        List<String> diffs = new ArrayList<>();
        JsonNode jsonDiffNode = JsonDiff.asJson(leftJObject, rightJObject);
        jsonDiffNode.forEach(diff -> diffs.add(diffToString(diff)));
        return diffs;
    }

    private String diffToString(JsonNode diff) {
        StringBuilder difference = new StringBuilder();
        difference.append("Diff: ");
        difference.append(diff.get("op").asText());
        difference.append(" - ");
        difference.append("Path: ");
        difference.append(diff.get("path").asText());
        return difference.toString();
    }
}
