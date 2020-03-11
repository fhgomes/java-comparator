package org.com.fernando.config;

import org.com.fernando.share.exception.ComparingException;
import org.com.fernando.share.exception.InvalidResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  private final MessageSource messageSource;

  @Autowired
  public RestExceptionHandler(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  @ExceptionHandler(ComparingException.class)
  protected ResponseEntity<InvalidResultDTO> codeException(ComparingException ex) {
    String localizedMessage = findLocalizedMessage(ex.getCode(), new Object());
    InvalidResultDTO exceptionResult = new InvalidResultDTO(ex.getCode(), localizedMessage);

    return ResponseEntity
      .status(ex.getHttpStatus())
      .body(exceptionResult);
  }

  /**
   * Internalization, find message and also, apply the arguments
   *
   * @param code      I18N message code
   * @param arguments Arguments for the message
   * @return Localized message
   */
  private String findLocalizedMessage(String code, Object... arguments) {
    return messageSource.getMessage(code, arguments, LocaleContextHolder.getLocale());
  }
}