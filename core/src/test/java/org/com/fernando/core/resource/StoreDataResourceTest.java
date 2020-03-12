package org.com.fernando.core.resource;

import org.com.fernando.core.service.DataObjectService;
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
class StoreDataResourceTest {

    @InjectMocks
    StoreDataResource storeDataResource;

    @Mock
    private DataObjectService dataObjectService;

    @Test
    @DisplayName("Should receive request saveDataLeft and delegate to service")
    void storeLeftData() {
        when(dataObjectService.saveDataLeft("abc123", "content".getBytes())).thenReturn("id");
        HttpEntity<String> response = storeDataResource.storeLeftData("abc123", "content".getBytes());
        assertAll(()-> assertEquals("id", response.getBody()));
    }

    @Test
    @DisplayName("Should receive request saveDataRight and delegate to service")
    void storeRightData() {
        when(dataObjectService.saveDataRight("abc123", "content".getBytes())).thenReturn("id");
        HttpEntity<String> response = storeDataResource.storeRightData("abc123", "content".getBytes());
        assertAll(()-> assertEquals("id", response.getBody()));
    }
}