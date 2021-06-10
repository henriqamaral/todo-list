package com.henriq.todo.usecase;

import com.henriq.todo.domain.Todo;
import com.henriq.todo.gateway.TodoGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateTodo {

  private final TodoGateway todoGateway;

  public void execute(final Todo todo) {
    todoGateway.create(todo);
  }
}
