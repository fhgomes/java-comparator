package org.com.fernando.compare.processor;

import org.com.fernando.share.data.DataComparableDTO;
import org.com.fernando.share.data.DataContentDTO;
import org.com.fernando.share.exception.ComparingException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import static java.util.Objects.isNull;

@Service
public class DataComparableObjectValidator {

    public void validateDataToCompare(DataComparableDTO contents) {
        /*
          Here this validation is more an protection to isolated an ensure that we will not face a nullPointer
         */
        if (isNull(contents)) {
            throw new ComparingException("diff.invalid.content_missing");
        }

        validateLeft(contents);
        validateRight(contents);
    }

    private void validateLeft(DataComparableDTO contents) {
        validateContent(contents.getContentLeft(), "diff.invalid.left_not_received", "diff.invalid.left_without_content");
    }

    private void validateRight(DataComparableDTO contents) {
        validateContent(contents.getContentRight(), "diff.invalid.right_not_received", "diff.invalid.right_without_content");
    }

    private void validateContent(DataContentDTO content, String msgMissing, String messageEmpty) {
        if (isNull(content)) {
            throw new ComparingException(msgMissing);
        }

        if (StringUtils.isEmpty(content.getRawContent())) {
            throw new ComparingException(messageEmpty);
        }
    }
}