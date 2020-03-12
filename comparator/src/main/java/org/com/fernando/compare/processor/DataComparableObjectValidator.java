package org.com.fernando.compare.processor;

import org.com.fernando.share.data.DataComparableDTO;
import org.com.fernando.share.data.DataContentDTO;
import org.com.fernando.share.exception.ComparingException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import static java.util.Objects.isNull;
import static org.com.fernando.compare.common.ComparingMsgConstants.*;
import static org.com.fernando.util.encoding.BytesToStringUtil.bytesToString;

@Service
public class DataComparableObjectValidator {

    public void validateDataToCompare(DataComparableDTO contents) {
        /*
          Here this validation is more an protection to isolated an ensure that we will not face a nullPointer
         */
        if (isNull(contents)) {
            throw new ComparingException(DIFF_INVALID_CONTENT_MISSING);
        }

        validateLeft(contents);
        validateRight(contents);
    }

    private void validateLeft(DataComparableDTO contents) {
        validateContent(contents.getContentLeft(), DIFF_INVALID_LEFT_NOT_RECEIVED, DIFF_INVALID_LEFT_WITHOUT_CONTENT);
    }

    private void validateRight(DataComparableDTO contents) {
        validateContent(contents.getContentRight(), DIFF_INVALID_RIGHT_NOT_RECEIVED, DIFF_INVALID_RIGHT_WITHOUT_CONTENT);
    }

    private void validateContent(DataContentDTO content, String msgMissing, String messageEmpty) {
        if (isNull(content)) {
            throw new ComparingException(msgMissing);
        }

        if (isNull(content.getRawContent()) || StringUtils.isEmpty(bytesToString(content.getRawContent()))) {
            throw new ComparingException(messageEmpty);
        }
    }
}