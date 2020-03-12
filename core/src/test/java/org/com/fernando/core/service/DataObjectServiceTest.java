package org.com.fernando.core.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class DataObjectServiceTest {

    @InjectMocks
    private DataObjectService dataObjectService;

    @Test
    void saveDataLeft() {
        String json = "ewogICJuYW1lIjogImZlcm5hbmRvIiwKICAiYWdlIjogMzEKfQ==";
//        dataObjectService.saveDataLeft("abc123", json.getBytes());
        System.out.println(json.getBytes());

    }
}