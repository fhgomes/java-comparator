package org.com.fernando.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.MissingFormatArgumentException;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Component
public class MessagesWrapper {
    private static final String MSG_NOT_CONFIGURED = "i18n.msg.not_configured: %s";
    private static final String MSG_WITH_WRONG_PARAMS = "i18n.msg.wrong_params: %s - %s";

    private static final Logger LOG = LoggerFactory.getLogger(MessagesWrapper.class);

    private MessageSourceAccessor accessor;

    @Autowired
    public MessagesWrapper(@Qualifier("messageSource") MessageSource messageSource) {
        accessor = new MessageSourceAccessor(messageSource, LocaleContextHolder.getLocale());
    }

    public String get(String code) {
        try {
            return accessor.getMessage(code);
        } catch (NoSuchMessageException e) {
            String msgReturn = format(MSG_NOT_CONFIGURED, code);
            LOG.warn(msgReturn);
            return msgReturn;
        }
    }

    public String getMessage(final String keyMessage, Object... arguments) {
        try {
            String message = accessor.getMessage(keyMessage, LocaleContextHolder.getLocale());
            if(StringUtils.isEmpty(message)) {
                throw new NoSuchMessageException(keyMessage);
            }
            return format(message, arguments);
        } catch (NoSuchMessageException e) {
            String msgReturn = format(MSG_NOT_CONFIGURED, keyMessage);
            LOG.warn(msgReturn);
            return msgReturn;
        } catch (MissingFormatArgumentException e) {
            String collect = Arrays.stream(arguments).map(Object::toString).collect(Collectors.joining(","));
            String msgReturn = format(MSG_WITH_WRONG_PARAMS, keyMessage, collect);
            LOG.warn(msgReturn);
            return msgReturn;
        }
    }
}