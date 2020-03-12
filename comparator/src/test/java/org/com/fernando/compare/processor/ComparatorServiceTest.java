package org.com.fernando.compare.processor;

import org.com.fernando.compare.factory.SpecificComparatorFactory;
import org.com.fernando.core.service.DataObjectService;
import org.com.fernando.share.contracts.FileType;
import org.com.fernando.share.contracts.IFileSpecificComparator;
import org.com.fernando.share.data.CompareResultDTO;
import org.com.fernando.share.data.DataComparableDTO;
import org.com.fernando.share.data.DataContentDTO;
import org.com.fernando.share.exception.ComparingException;
import org.com.fernando.util.MessagesWrapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ComparatorServiceTest {

    @InjectMocks
    ComparatorService comparatorService;

    @Mock
    DataObjectService dataObjectService;

    @Mock
    DataComparableObjectValidator dataComparableObjectValidator;

    @Mock
    MessagesWrapper messagesWrapper;

    @Mock
    SpecificComparatorFactory specificComparatorFactory;
    @Mock
    FakeJsonComparator fakeJsonComparatorMock;

    @Mock
    private DataComparableDTO dataComparableMock;
    @Mock
    private DataContentDTO dataContentLeftMock;
    @Mock
    private DataContentDTO dataContentRightMock;
    @Mock
    private CompareResultDTO resultMock;

    @Test
    @DisplayName("Should compare and result equals")
    void compare() {
        when(dataObjectService.findComparableByReference("123")).thenReturn(dataComparableMock);
        when(dataComparableMock.getContentLeft()).thenReturn(dataContentLeftMock);
        when(dataComparableMock.getContentRight()).thenReturn(dataContentRightMock);
        when(dataContentLeftMock.getRawContent()).thenReturn("content");
        when(dataContentRightMock.getRawContent()).thenReturn("content");

        CompareResultDTO result = comparatorService.compare("123");
        assertAll(
                () -> assertEquals(true, result.isValidEquals()),
                () -> assertEquals(200, result.getStatus()),
                () -> assertEquals("diff.equals", result.getCode())
        );
    }

    @Test
    @DisplayName("Should compare and result equals")
    void compareWhenDifferent() {
        when(specificComparatorFactory.getComparatorFor(FileType.JSON)).thenReturn(fakeJsonComparatorMock);
        when(dataObjectService.findComparableByReference("123")).thenReturn(dataComparableMock);
        when(dataComparableMock.getContentLeft()).thenReturn(dataContentLeftMock);
        when(dataComparableMock.getContentRight()).thenReturn(dataContentRightMock);
        when(dataContentLeftMock.getRawContent()).thenReturn("content");
        when(dataContentRightMock.getRawContent()).thenReturn("contentDiff");

        when(fakeJsonComparatorMock.findDiff(dataContentLeftMock, dataContentRightMock)).thenReturn(resultMock);
        when(resultMock.getCode()).thenReturn("invalid.code");
        when(resultMock.getStatus()).thenReturn(409);

        CompareResultDTO result = comparatorService.compare("123");
        assertAll(
                () -> assertEquals(false, result.isValidEquals()),
                () -> assertEquals(409, result.getStatus()),
                () -> assertEquals("invalid.code", result.getCode())
        );
    }

    @Test
    @DisplayName("Should throw error when invalid on validator")
    void compareWhenValidatorThrowEx() {
        when(dataObjectService.findComparableByReference("123")).thenReturn(dataComparableMock);
        doThrow(new ComparingException("invalid"))
                .when(dataComparableObjectValidator).validateDataToCompare(dataComparableMock);
        CompareResultDTO result = comparatorService.compare("123");
        assertAll(
                () -> assertEquals(false, result.isValidEquals()),
                () -> assertEquals(409, result.getStatus()),
                () -> assertEquals("invalid", result.getCode())
        );
    }

    class FakeJsonComparator implements IFileSpecificComparator {

        @Override
        public CompareResultDTO findDiff(DataContentDTO contentLeft, DataContentDTO contentRight) {
            return null;
        }

        @Override
        public FileType getFileType() {
            return null;
        }
    }
}