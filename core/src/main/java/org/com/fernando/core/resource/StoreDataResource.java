package org.com.fernando.core.resource;

import org.com.fernando.core.service.DataObjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/diff")
public class StoreDataResource {

    private final DataObjectService dataObjectService;

    public StoreDataResource(DataObjectService dataObjectService) {
        this.dataObjectService = dataObjectService;
    }

    @PostMapping(value = "/{id}/left")
    public ResponseEntity<String> storeLeftData(@PathVariable("id") String refId,
                                                @RequestBody String content) {
        String savedId = dataObjectService.saveDataLeft(refId, content);
        return ResponseEntity.accepted().body(savedId);
    }

    @PostMapping(value = "/{id}/right")
    public ResponseEntity<String> storeRightData(@PathVariable("id") String refId,
                                                 @RequestBody String content) {
        String savedId = dataObjectService.saveDataRight(refId, content);
        return ResponseEntity.accepted().body(savedId);
    }

}