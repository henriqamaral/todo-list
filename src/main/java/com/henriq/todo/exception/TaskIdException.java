package com.henriq.todo.exception;

import com.henriq.todo.domain.Task;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

public class TaskIdException extends RuntimeException {

  @Getter
  private final List<Long> ids;

  public TaskIdException(List<Task> tasks) {
    super("Task ids must be unique.");
    ids = tasks.stream().map(Task::getId).collect(Collectors.toList());
  }


}
