package org.com.fernando.compare.processor;

import org.com.fernando.core.service.DataObjectService;
import org.com.fernando.share.data.DataComparableDTO;
import org.springframework.stereotype.Service;

@Service
public class JsonComparator {

    /**
     * Here i'm using some core service, but someday i can simple replace it by some rest service
     */
    private final DataObjectService dataObjectService;

    public JsonComparator(DataObjectService dataObjectService) {
        this.dataObjectService = dataObjectService;
    }

    public String compare(String id) {
        DataComparableDTO dataComparable = dataObjectService.findDataByReference(id);
        return "OK";
    }
}
