package com.henriq.todo.gateway.http.handler;

import com.henriq.todo.domain.Todo;
import com.henriq.todo.exception.DuplicateTodoException;
import com.henriq.todo.exception.NotFoundException;
import com.henriq.todo.exception.TaskIdException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

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

  @ResponseBody
  @ExceptionHandler(ConstraintViolationException.class)
  public HttpEntity<Message> handlerConstraintViolation(final ConstraintViolationException ex) {

    final Message message = new Message(ex
                                          .getConstraintViolations()
                                          .stream()
                                          .map(this::parseConstraintViolationMessage)
                                          .collect(Collectors.joining(",")));
    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.add("Content-Type", "application/json; charset=utf-8");

    return new ResponseEntity<>(message, responseHeaders, HttpStatus.BAD_REQUEST);
  }

  private String parseConstraintViolationMessage(ConstraintViolation<?> constraintViolation) {
    return String
      .format("%s value %s", constraintViolation.getPropertyPath(), constraintViolation.getMessage());
  }

  @ResponseBody
  @ExceptionHandler(DuplicateTodoException.class)
  public HttpEntity<Message> handlerNotFoundException(final DuplicateTodoException ex) {

    final Message message = new Message(ex.getMessage());
    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.add("Content-Type", "application/json; charset=utf-8");

    return new ResponseEntity<>(message, responseHeaders, HttpStatus.BAD_REQUEST);
  }


  @ResponseBody
  @ExceptionHandler(TaskIdException.class)
  public HttpEntity<Message> handlerTaskIdException(final TaskIdException ex) {

    final var description = ex.getMessage() + ex.getIds();
    final Message message = new Message(description);
    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.add("Content-Type", "application/json; charset=utf-8");

    return new ResponseEntity<>(message, responseHeaders, HttpStatus.BAD_REQUEST);
  }


}
