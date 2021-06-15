package com.henriq.todo.domain;

import com.henriq.todo.validator.SelfValidator;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = false)
@Value
public class Task extends SelfValidator<Task> {

  @NotNull Long id;

  @NotEmpty String name;

  String description;

  public Task(Long id, String name, String description) {
    this.id = id;
    this.name = name;
    this.description = description;
    validateSelf();
  }

}
