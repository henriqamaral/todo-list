package com.henriq.todo.domain;

import com.henriq.todo.validator.SelfValidator;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
public class Task extends SelfValidator<Task> {

  @NotNull
  private final Long id;

  @NotEmpty
  private final String name;

  private final String description;

  public Task(Long id, String name, String description) {
    this.id = id;
    this.name = name;
    this.description = description;
    validateSelf();
  }

}
