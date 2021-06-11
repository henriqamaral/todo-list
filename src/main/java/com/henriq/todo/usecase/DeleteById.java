package com.henriq.todo.usecase;

import com.henriq.todo.gateway.TodoGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteById {

  private final TodoGateway todoGateway;

  public void execute(Long id) {

    todoGateway.deleteById(id);
  }
}
