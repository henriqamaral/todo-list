package com.henriq.todo.exception;

public class NotFoundException extends RuntimeException {

  public NotFoundException(Long id) {
    super("Id: " + id + " not found");
  }
}
