package com.henriq.todo.usecase;

import com.henriq.todo.domain.Todo;
import com.henriq.todo.exception.NotFoundException;
import com.henriq.todo.gateway.TodoGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetTodoById {

  private final TodoGateway todoGateway;

  public Todo execute(Long todoId) {
    return todoGateway.getById(todoId)
      .orElseThrow(() -> new NotFoundException(todoId));
  }
}
