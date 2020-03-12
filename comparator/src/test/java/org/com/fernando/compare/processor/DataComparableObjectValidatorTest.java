package org.com.fernando.compare.processor;

import org.com.fernando.share.data.DataComparableDTO;
import org.com.fernando.share.data.DataContentDTO;
import org.com.fernando.share.exception.ComparingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DataComparableObjectValidatorTest {

    @InjectMocks
    DataComparableObjectValidator validator;

    @Test
    @DisplayName("Should throw error when content is null")
    void validateDataToCompareWhenNull() {
        ComparingException comparingException = assertThrows(ComparingException.class,
                () -> validator.validateDataToCompare(null));
        assertEquals("diff.invalid.content_missing", comparingException.getCode());
    }

    @Test
    @DisplayName("Should throw error when content left is null")
    void validateDataToCompareOnlyRight() {
        DataComparableDTO contents = new DataComparableDTO();
        contents.setContentRight(createContent("raw"));
        ComparingException comparingException = assertThrows(ComparingException.class,
                () -> validator.validateDataToCompare(contents));
        assertEquals("diff.invalid.left_not_received", comparingException.getCode());
    }

    @Test
    @DisplayName("Should throw error when content right is null")
    void validateDataToCompareOnlyLeft() {
        DataComparableDTO contents = new DataComparableDTO();
        contents.setContentLeft(createContent("raw"));
        ComparingException comparingException = assertThrows(ComparingException.class,
                () -> validator.validateDataToCompare(contents));
        assertEquals("diff.invalid.right_not_received", comparingException.getCode());
    }

    @Test
    @DisplayName("Should throw error when content left is empty")
    void validateDataToCompareLeftEmpty() {
        DataComparableDTO contents = new DataComparableDTO();
        contents.setContentLeft(createContent(null));
        contents.setContentRight(createContent("raw"));
        ComparingException comparingException = assertThrows(ComparingException.class,
                () -> validator.validateDataToCompare(contents));
        assertEquals("diff.invalid.left_without_content", comparingException.getCode());
    }

    @Test
    @DisplayName("Should throw error when content right is empty")
    void validateDataToCompareRightEmpty() {
        DataComparableDTO contents = new DataComparableDTO();
        contents.setContentRight(createContent(null));
        contents.setContentLeft(createContent("raw"));
        ComparingException comparingException = assertThrows(ComparingException.class,
                () -> validator.validateDataToCompare(contents));
        assertEquals("diff.invalid.right_without_content", comparingException.getCode());
    }

    @Test
    @DisplayName("Should not throw when content is valid")
    void validateDataToCompareWhenValid() {
        DataComparableDTO contents = new DataComparableDTO();
        contents.setContentRight(createContent("raw"));
        contents.setContentLeft(createContent("raw"));
        assertDoesNotThrow(() -> validator.validateDataToCompare(contents));
    }

    private DataContentDTO createContent(String raw) {
        DataContentDTO contentDTO = new DataContentDTO();
        contentDTO.setRawContent(raw);
        return contentDTO;
    }
}