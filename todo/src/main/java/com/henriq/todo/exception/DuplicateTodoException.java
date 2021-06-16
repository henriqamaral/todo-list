package com.henriq.todo.exception;

public class DuplicateTodoException extends RuntimeException {

  public DuplicateTodoException(Long id) {
    super("Todo Id: " + id + " already in database");
  }
}
