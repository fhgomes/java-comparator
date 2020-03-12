package org.com.fernando.compare.resource;

import org.com.fernando.compare.processor.ComparatorService;
import org.com.fernando.share.data.CompareResultDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ComparingResourceTest {

    @InjectMocks
    ComparingResource comparingResource;

    @Mock
    ComparatorService comparatorService;

    @Test
    @DisplayName("Should receive and delegate to service")
    void compare() {
        CompareResultDTO result = new CompareResultDTO(true, 200, "code", "msg");
        when(comparatorService.compare("abc123")).thenReturn(result);
        HttpEntity<CompareResultDTO> response = comparingResource.compare("abc123");
        assertAll(
                ()-> assertEquals(200, response.getBody().getStatus()),
                ()-> assertEquals("code", response.getBody().getCode()));
    }
}