package org.com.fernando.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

import static com.fasterxml.jackson.databind.util.StdDateFormat.DATE_FORMAT_STR_ISO8601;

@Component
public class JsonUtils {

    private final ObjectMapper objectMapper;

    public JsonUtils() {
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();

        objectMapper.setDateFormat(new SimpleDateFormat(DATE_FORMAT_STR_ISO8601));
        objectMapper
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
