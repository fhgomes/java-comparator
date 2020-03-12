package org.com.fernando.compare.consumer;

import org.com.fernando.compare.processor.ComparatorService;
import org.com.fernando.share.contracts.assync.CompareMessage;
import org.com.fernando.share.contracts.assync.Queues;
import org.com.fernando.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.TextMessage;

import static java.lang.String.format;

@Component
public class CompareConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(CompareConsumer.class);

    private final ComparatorService comparatorService;
    private final JsonUtils jsonUtils;

    public CompareConsumer(ComparatorService comparatorService, JsonUtils jsonUtils) {
        this.comparatorService = comparatorService;
        this.jsonUtils = jsonUtils;
    }

    @JmsListener(destination = "${" + Queues.QUEUE_COMPARE + ":" + Queues.QUEUE_COMPARE_DEFAULT + "}")
    public void consumer(final TextMessage mJson) {
        try {
            final CompareMessage message = jsonUtils.getObjectMapper().readValue(mJson.getText(), CompareMessage.class);
            comparatorService.compare(message.getRefId());
        } catch (Exception e) {
            LOG.error(format("Error trying to compare: %s. Cause: %s", mJson, e.getMessage()), e);
        }
    }
}