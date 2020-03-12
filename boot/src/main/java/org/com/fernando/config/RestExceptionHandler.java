package org.com.fernando.config;

import org.com.fernando.share.exception.ComparingException;
import org.com.fernando.share.exception.InvalidResultDTO;
import org.com.fernando.util.MessagesWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  private final MessagesWrapper messagesWrapper;

  @Autowired
  public RestExceptionHandler(MessagesWrapper messagesWrapper) {
    this.messagesWrapper = messagesWrapper;
  }

  @ExceptionHandler(ComparingException.class)
  protected ResponseEntity<InvalidResultDTO> codeException(ComparingException ex) {
    String localizedMessage = findLocalizedMessage(ex.getCode(), ex.getMsgArgs());
    InvalidResultDTO exceptionResult = new InvalidResultDTO(ex.getCode(), localizedMessage);

    return ResponseEntity
      .status(ex.getHttpStatus())
      .body(exceptionResult);
  }

  private String findLocalizedMessage(String code, Object... arguments) {
    return messagesWrapper.getMessage(code, arguments, LocaleContextHolder.getLocale());
  }
}