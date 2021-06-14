package com.henriq.todo.gateway.http.handler;

import com.henriq.todo.exception.NotFoundException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ResponseBody
  @ExceptionHandler(NotFoundException.class)
  public HttpEntity<Message> handlerNotFoundException(final NotFoundException ex) {

    final Message message = new Message(ex.getMessage());
    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.add("Content-Type", "application/json; charset=utf-8");

    return new ResponseEntity<>(message, responseHeaders, HttpStatus.NOT_FOUND);
  }

}
