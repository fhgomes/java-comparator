package org.com.fernando.core.service;

import org.com.fernando.core.domain.DataObject;
import org.com.fernando.core.repository.DataObjectRepository;
import org.com.fernando.core.repository.DataObjectResultRepository;
import org.com.fernando.share.ObjectDirection;
import org.com.fernando.share.data.DataComparableDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DataObjectFindServiceTest {

    @InjectMocks
    DataObjectFindService dataObjectFindService;

    @Mock
    DataObjectRepository dataObjectRepository;

    @Mock
    DataObjectResultRepository dataObjectResultRepository;

    @Mock
    DataObjectFactory dataObjectFactory;

    @Mock
    DataObject dataLeftMock;

    @Mock
    DataObject dataRightMock;

    @Mock
    DataComparableDTO dataComparableMock;

    @Test
    @DisplayName("Should find comparable")
    void findComparableByReference() {
        when(dataObjectRepository.findByReferenceId("123")).thenReturn(Arrays.asList(dataLeftMock, dataRightMock));
        when(dataObjectFactory.transformDataToDTO("123", Arrays.asList(dataLeftMock, dataRightMock))).thenReturn(dataComparableMock);
        DataComparableDTO comparableByReference = dataObjectFindService.findComparableByReference("123");
        assertEquals(dataComparableMock, comparableByReference);
    }

    @Test
    @DisplayName("Should check if exists")
    void existsByReferenceAndDirection() {
        when(dataObjectRepository.existsByReferenceIdAndDirection("123", ObjectDirection.LEFT)).thenReturn(false);
        assertFalse(dataObjectFindService.existsByReferenceAndDirection("123", ObjectDirection.LEFT));
    }
}