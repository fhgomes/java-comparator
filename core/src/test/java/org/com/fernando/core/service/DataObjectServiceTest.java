package org.com.fernando.core.service;

import org.com.fernando.core.domain.DataObject;
import org.com.fernando.core.producer.CompareProducer;
import org.com.fernando.core.repository.DataObjectRepository;
import org.com.fernando.core.repository.DataObjectResultRepository;
import org.com.fernando.share.ObjectDirection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class DataObjectServiceTest {

    @InjectMocks
    private DataObjectService dataObjectService;

    @Mock
    DataObjectValidator dataObjectValidator;

    @Mock
    DataObjectRepository dataObjectRepository;
    @Mock
    DataObjectResultRepository dataObjectResultRepository;
    @Mock
    CompareProducer compareProducer;

    @Mock
    DataObjectFactory dataObjectFactory;
    @Mock
    DataObject objectSavedMock;
    @Mock
    DataObject objectToSaveMock;

    @Test
    @DisplayName("Should save left")
    void saveDataLeft() {
        when(dataObjectFactory.createNewObject("abc123", ObjectDirection.LEFT, "cont".getBytes())).thenReturn(objectToSaveMock);
        when(dataObjectRepository.save(objectToSaveMock)).thenReturn(objectSavedMock);
        when(objectSavedMock.getId()).thenReturn("abc123qwerty");

        String id = dataObjectService.saveDataLeft("abc123", "cont".getBytes());

        assertEquals("abc123qwerty", id);
    }

    @Test
    @DisplayName("Should save right")
    void saveDataRight() {
        when(dataObjectFactory.createNewObject("abc123", ObjectDirection.RIGHT, "cont".getBytes())).thenReturn(objectToSaveMock);
        when(dataObjectRepository.save(objectToSaveMock)).thenReturn(objectSavedMock);
        when(objectSavedMock.getId()).thenReturn("abc123qwerty");

        String id = dataObjectService.saveDataRight("abc123", "cont".getBytes());

        assertEquals("abc123qwerty", id);
    }

    @Test
    @DisplayName("Should call validations when save")
    void saveDoValidationsWhenSave() {
        when(dataObjectFactory.createNewObject("abc123", ObjectDirection.RIGHT, "cont".getBytes())).thenReturn(objectToSaveMock);
        when(dataObjectRepository.save(objectToSaveMock)).thenReturn(objectSavedMock);
        when(objectSavedMock.getId()).thenReturn("abc123qwerty");

        String id = dataObjectService.saveDataRight("abc123", "cont".getBytes());

        assertAll(
                () -> assertEquals("abc123qwerty", id),
                () -> verify(dataObjectValidator).validateContent("cont".getBytes()),
                () -> verify(dataObjectValidator).validateAlreadyExist("abc123", ObjectDirection.RIGHT)
        );
    }
}