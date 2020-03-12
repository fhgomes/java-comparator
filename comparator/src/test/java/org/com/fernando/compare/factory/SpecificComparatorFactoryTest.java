package org.com.fernando.compare.factory;

import org.com.fernando.share.contracts.FileType;
import org.com.fernando.share.contracts.IFileSpecificComparator;
import org.com.fernando.share.data.CompareResultDTO;
import org.com.fernando.share.data.DataContentDTO;
import org.com.fernando.share.exception.ComparingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SpecificComparatorFactoryTest {

    @InjectMocks
    private SpecificComparatorFactory specificComparatorFactory;

    @Spy
    private List<IFileSpecificComparator> comparators;

    @Test
    @DisplayName("Should return Json comparator")
    void getComparatorFor() {
        when(comparators.stream())
                .thenReturn(Stream.of(new FakeJsonComparator(), new FakeXMLComparator()));

        IFileSpecificComparator comparatorFor = specificComparatorFactory.getComparatorFor(FileType.JSON);
        assertTrue(comparatorFor instanceof FakeJsonComparator);
    }

    @Test
    @DisplayName("Should throw error when comparators is empty")
    void getComparatorForEmpty() {
        when(comparators.stream())
                .thenReturn(Stream.of());

        assertThrows(ComparingException.class,
                () -> specificComparatorFactory.getComparatorFor(FileType.JSON));
    }

    @Test
    @DisplayName("Should throw error when comparators is not implemented")
    void getComparatorForNotImplemented() {
        when(comparators.stream())
                .thenReturn(Stream.of(new FakeJsonComparator(), new FakeXMLComparator()));

        assertThrows(ComparingException.class,
                () -> specificComparatorFactory.getComparatorFor(FileType.CSV));
    }

    class FakeJsonComparator implements IFileSpecificComparator {

        @Override
        public CompareResultDTO findDiff(DataContentDTO contentLeft, DataContentDTO contentRight) {
            return null;
        }

        @Override
        public FileType getFileType() {
            return FileType.JSON;
        }
    }

    class FakeXMLComparator implements IFileSpecificComparator {

        @Override
        public CompareResultDTO findDiff(DataContentDTO contentLeft, DataContentDTO contentRight) {
            return null;
        }

        @Override
        public FileType getFileType() {
            return FileType.XML;
        }
    }
}