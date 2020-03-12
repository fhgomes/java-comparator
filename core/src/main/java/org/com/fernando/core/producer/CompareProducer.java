package org.com.fernando.core.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.com.fernando.share.contracts.assync.CompareMessage;
import org.com.fernando.share.contracts.assync.Queues;
import org.com.fernando.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import static java.lang.String.format;

@Component
public class CompareProducer {

    private static final Logger LOG = LoggerFactory.getLogger(CompareProducer.class);
    private static final String PRODUCE_MSG = "Scheduled compare [%s]";

    private final JmsTemplate jmsTemplate;
    private final JsonUtils jsonUtils;

    @Value("${" + Queues.QUEUE_COMPARE + ":" + Queues.QUEUE_COMPARE_DEFAULT + "}")
    private String queue;

    public CompareProducer(JmsTemplate jmsTemplate, JsonUtils jsonUtils) {
        this.jmsTemplate = jmsTemplate;
        this.jsonUtils = jsonUtils;
    }

    public void produce(final CompareMessage data) {
        String strDebug = format(PRODUCE_MSG, data.getRefId());
        LOG.debug(strDebug);
        try {
            this.jmsTemplate.convertAndSend(queue, jsonUtils.getObjectMapper().writeValueAsString(data), message ->  message);
        } catch (JsonProcessingException e) {
            LOG.error(format("Error trying to schedule compare: %s. Cause: %s", data.getRefId(), e.getMessage()), e);
        }
    }

}