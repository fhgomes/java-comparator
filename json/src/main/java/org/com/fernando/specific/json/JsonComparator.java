package org.com.fernando.specific.json;

import com.fasterxml.jackson.databind.JsonNode;
import org.com.fernando.share.ObjectDirection;
import org.com.fernando.share.contracts.FileType;
import org.com.fernando.share.contracts.IDecoder;
import org.com.fernando.share.contracts.IFileSpecificComparator;
import org.com.fernando.share.data.CompareResultDTO;
import org.com.fernando.share.data.DataContentDTO;
import org.com.fernando.util.MessagesWrapper;
import org.com.fernando.util.encoding.DecoderFactory;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.com.fernando.specific.json.common.JsonMsgConstants.DIFF_JSON_FIELDS_NOT_EQUALS;
import static org.com.fernando.specific.json.common.JsonMsgConstants.DIFF_JSON_SIZE_NOT_EQUALS;
import static org.com.fernando.util.encoding.BytesToStringUtil.bytesToString;

@Component
public class JsonComparator implements IFileSpecificComparator {

    private final DecoderFactory decoderFactory;
    private final MessagesWrapper messagesWrapper;
    private final JsonHelper jsonHelper;

    public JsonComparator(DecoderFactory decoderFactory,
                          MessagesWrapper messagesWrapper,
                          JsonHelper jsonHelper) {
        this.decoderFactory = decoderFactory;
        this.messagesWrapper = messagesWrapper;
        this.jsonHelper = jsonHelper;
    }

    @Override
    public CompareResultDTO findDiff(DataContentDTO contentLeft, DataContentDTO contentRight) {
        IDecoder base64Decoder = decoderFactory.getDefaultDecoder();
        String leftDecoded = base64Decoder.decode(bytesToString(contentLeft.getRawContent()));
        String rightDecoded = base64Decoder.decode(bytesToString(contentRight.getRawContent()));
        return compareJson(leftDecoded, rightDecoded);
    }

    private CompareResultDTO compareJson(String leftDecoded, String rightDecoded) {
        JsonNode leftJObject = jsonHelper.readAsJson(leftDecoded, ObjectDirection.LEFT);
        JsonNode rightJObject = jsonHelper.readAsJson(rightDecoded, ObjectDirection.RIGHT);

        return compareObjects(leftJObject, rightJObject);
    }

    private CompareResultDTO compareObjects(JsonNode leftJObject, JsonNode rightJObject) {
        if (leftJObject.size() != rightJObject.size()) {
            String message = messagesWrapper.getMessage(DIFF_JSON_SIZE_NOT_EQUALS, leftJObject.size(), rightJObject.size());
            return new CompareResultDTO(false, 409, DIFF_JSON_SIZE_NOT_EQUALS, message);
        }
        return compareToInsight(leftJObject, rightJObject);
    }

    /**
     * Here i prefered to use some external Lib that already provides the diffs with more details.
     * The implementation of the diff is decoupled and transparent here because it's implemented in another class.
     * If i want to change the implementation i just need to change JsonHelper.getJsonDiff
     */
    private CompareResultDTO compareToInsight(JsonNode leftJObject, JsonNode rightJObject) {
        String message = messagesWrapper.getMessage(DIFF_JSON_FIELDS_NOT_EQUALS, leftJObject.size(), rightJObject.size());
        CompareResultDTO resultDTO = new CompareResultDTO(false, 409, DIFF_JSON_FIELDS_NOT_EQUALS, message);

        List<String> differences = jsonHelper.getJsonDiff(leftJObject, rightJObject);
        differences.forEach(resultDTO::addDifference);
        return resultDTO;
    }

    @Override
    public FileType getFileType() {
        return FileType.JSON;
    }
}
