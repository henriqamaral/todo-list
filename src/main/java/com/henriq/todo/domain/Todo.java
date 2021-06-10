package com.henriq.todo.domain;

import com.henriq.todo.validator.SelfValidator;
import lombok.Getter;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


public class Todo extends SelfValidator<Todo> {

  @NotNull
  @Getter
  private final Long id;

  @NotEmpty
  @Getter
  private final String name;

  @Getter
  private final String description;

  @Getter
  private final List<Task> tasks;

  public Todo(Long id, String name, String description,
              List<Task> tasks) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.tasks = tasks;
    validateSelf();
  }
}
