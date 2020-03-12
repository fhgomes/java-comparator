package org.com.fernando.core.service;

import org.com.fernando.core.domain.DataObject;
import org.com.fernando.share.ObjectDirection;
import org.com.fernando.share.ProcessStatus;
import org.com.fernando.share.data.DataComparableDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.com.fernando.util.encoding.BytesToStringUtil.bytesToString;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DataObjectFactoryTest {

    @InjectMocks
    DataObjectFactory dataObjectFactory;

    @Test
    @DisplayName("Should create Entity DataObject Right")
    void createNewObject() {
        DataObject data = dataObjectFactory.createNewObject("123", ObjectDirection.RIGHT, "content".getBytes());
        assertAll(
                () -> assertNull(data.getId()),
                () -> assertNull(data.getDecodedContent()),
                () -> assertEquals("123", data.getReferenceId()),
                () -> assertEquals(ObjectDirection.RIGHT, data.getDirection()),
                () -> assertEquals("content", bytesToString(data.getRawContent()))
        );
    }

    @Test
    @DisplayName("Should create Entity DataObject Left")
    void createNewObjectLeft() {
        DataObject data = dataObjectFactory.createNewObject("abc123", ObjectDirection.LEFT, "content".getBytes());
        assertAll(
                () -> assertNull(data.getId()),
                () -> assertNull(data.getDecodedContent()),
                () -> assertEquals("abc123", data.getReferenceId()),
                () -> assertEquals(ObjectDirection.LEFT, data.getDirection()),
                () -> assertEquals("content", bytesToString(data.getRawContent()))
        );
    }

    @Test
    @DisplayName("Should transform contents to DTO")
    void transformDataToDTO() {
        DataObject dataLeft = dataObjectFactory.createNewObject("abc123", ObjectDirection.LEFT, "contentL".getBytes());
        DataObject dataRight = dataObjectFactory.createNewObject("abc123", ObjectDirection.RIGHT, "contentR".getBytes());

        DataComparableDTO comparableDTO = dataObjectFactory
                .transformDataToDTO("abc123", Arrays.asList(dataLeft, dataRight));

        assertAll(
                () -> assertEquals("abc123", comparableDTO.getReferenceId()),
                () -> assertEquals(ProcessStatus.WAITING, comparableDTO.getProcessStatus()),
                () -> assertEquals(ObjectDirection.LEFT, comparableDTO.getContentLeft().getDirection()),
                () -> assertEquals("contentL", bytesToString(comparableDTO.getContentLeft().getRawContent())),
                () -> assertEquals(ObjectDirection.RIGHT, comparableDTO.getContentRight().getDirection()),
                () -> assertEquals("contentR", bytesToString(comparableDTO.getContentRight().getRawContent()))
        );
    }

    @Test
    @DisplayName("Should transform contents to DTO")
    void transformDataToDTOWithDifferentOrder() {
        DataObject dataLeft = dataObjectFactory.createNewObject("abc123", ObjectDirection.LEFT, "contentL".getBytes());
        DataObject dataRight = dataObjectFactory.createNewObject("abc123", ObjectDirection.RIGHT, "contentR".getBytes());

        DataComparableDTO comparableDTO = dataObjectFactory
                .transformDataToDTO("abc123", Arrays.asList(dataRight, dataLeft));

        assertAll(
                () -> assertEquals("abc123", comparableDTO.getReferenceId()),
                () -> assertEquals(ProcessStatus.WAITING, comparableDTO.getProcessStatus()),
                () -> assertEquals(ObjectDirection.LEFT, comparableDTO.getContentLeft().getDirection()),
                () -> assertEquals("contentL", bytesToString(comparableDTO.getContentLeft().getRawContent())),
                () -> assertEquals(ObjectDirection.RIGHT, comparableDTO.getContentRight().getDirection()),
                () -> assertEquals("contentR", bytesToString(comparableDTO.getContentRight().getRawContent()))
        );
    }

    @Test
    @DisplayName("Should transform contents to DTO when contents are missing")
    void transformDataToDTOWhenContentsAreMissing() {
        DataComparableDTO comparableDTO = dataObjectFactory
                .transformDataToDTO("abc123", Arrays.asList());

        assertAll(
                () -> assertEquals("abc123", comparableDTO.getReferenceId()),
                () -> assertEquals(ProcessStatus.WAITING, comparableDTO.getProcessStatus()),
                () -> assertNull(comparableDTO.getContentLeft()),
                () -> assertNull(comparableDTO.getContentRight())
        );
    }

    @Test
    @DisplayName("Should transform contents to DTO when left content is missing")
    void transformDataToDTOWhenLefContentIsMissing() {
        DataObject dataRight = dataObjectFactory.createNewObject("abc123", ObjectDirection.RIGHT, "contentR".getBytes());
        DataComparableDTO comparableDTO = dataObjectFactory
                .transformDataToDTO("abc123", Arrays.asList(dataRight));

        assertAll(
                () -> assertEquals("abc123", comparableDTO.getReferenceId()),
                () -> assertEquals(ProcessStatus.WAITING, comparableDTO.getProcessStatus()),
                () -> assertNull(comparableDTO.getContentLeft()),
                () -> assertEquals(ObjectDirection.RIGHT, comparableDTO.getContentRight().getDirection()),
                () -> assertEquals("contentR", bytesToString(comparableDTO.getContentRight().getRawContent()))
        );
    }

    @Test
    @DisplayName("Should transform contents to DTO when right content is missing")
    void transformDataToDTOWhenRightContentIsMissing() {
        DataObject dataLeft = dataObjectFactory.createNewObject("abc123", ObjectDirection.LEFT, "contentL".getBytes());
        DataComparableDTO comparableDTO = dataObjectFactory
                .transformDataToDTO("abc123", Arrays.asList(dataLeft));

        assertAll(
                () -> assertEquals("abc123", comparableDTO.getReferenceId()),
                () -> assertEquals(ProcessStatus.WAITING, comparableDTO.getProcessStatus()),
                () -> assertNull(comparableDTO.getContentRight()),
                () -> assertEquals(ObjectDirection.LEFT, comparableDTO.getContentLeft().getDirection()),
                () -> assertEquals("contentL", bytesToString(comparableDTO.getContentLeft().getRawContent()))
        );
    }


}