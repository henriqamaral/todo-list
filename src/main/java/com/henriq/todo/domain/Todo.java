package com.henriq.todo.domain;

import com.henriq.todo.validator.SelfValidator;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@EqualsAndHashCode(callSuper = true)
@Value
public class Todo extends SelfValidator<Todo> {

  @NotNull
  Long id;

  @NotEmpty
  String name;

  String description;

  List<Task> tasks;

  public Todo(Long id, String name, String description, List<Task> tasks) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.tasks = List.copyOf(tasks);
    validateSelf();
  }
}

