package org.com.fernando.core.service;

import org.com.fernando.share.ObjectDirection;
import org.com.fernando.share.exception.InvalidDataException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DataObjectValidatorTest {

    @InjectMocks
    DataObjectValidator dataObjectValidator;

    @Mock
    DataObjectFindService dataObjectFindService;

    @Test
    @DisplayName("Should throw ex when already exists Data")
    void validateAlreadyExist() {
        when(dataObjectFindService.existsByReferenceAndDirection("id", ObjectDirection.LEFT)).thenReturn(true);
        InvalidDataException ex = assertThrows(InvalidDataException.class,
                () -> dataObjectValidator.validateAlreadyExist("id", ObjectDirection.LEFT)
        );
        assertEquals("content.duplicated", ex.getCode());
    }

    @Test
    @DisplayName("Should not throw ex when not exists Data")
    void validateNotExist() {
        when(dataObjectFindService.existsByReferenceAndDirection("id", ObjectDirection.LEFT)).thenReturn(false);
        assertDoesNotThrow(() -> dataObjectValidator.validateAlreadyExist("id", ObjectDirection.LEFT));
    }

    @Test
    @DisplayName("Should validate valid content")
    void validateContent() {
        assertDoesNotThrow(() -> dataObjectValidator.validateContent("contentLongValid"));
    }

    @Test
    @DisplayName("Should throw ex when empty")
    void assertThrowsWhenEmpty() {
        InvalidDataException ex = assertThrows(InvalidDataException.class,
                () -> dataObjectValidator.validateContent("")
        );
        assertEquals("content.invalid.empty", ex.getCode());

    }

    @Test
    @DisplayName("Should throw ex when size less than min")
    void assertThrowsWhenWithLessThanMin() {
        InvalidDataException ex = assertThrows(InvalidDataException.class,
                () -> dataObjectValidator.validateContent("123")
        );
        assertEquals("content.invalid.min_size", ex.getCode());
    }
}