package org.com.fernando.compare.resource;

import org.com.fernando.compare.processor.ComparatorService;
import org.com.fernando.share.data.CompareResultDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.ALL_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1/diff")
public class ComparingResource {

    private final ComparatorService comparatorService;

    public ComparingResource(ComparatorService comparatorService) {
        this.comparatorService = comparatorService;
    }

    @GetMapping(value = "/{id}", consumes = ALL_VALUE, produces = APPLICATION_JSON_VALUE)
    public HttpEntity<CompareResultDTO> compare(@PathVariable("id") String id) {
        CompareResultDTO result = comparatorService.compare(id);
        return ResponseEntity.status(result.getStatus()).body(result);
    }
}