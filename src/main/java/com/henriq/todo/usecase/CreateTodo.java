package com.henriq.todo.usecase;

import com.henriq.todo.domain.Todo;
import com.henriq.todo.exception.DuplicateTodoException;
import com.henriq.todo.gateway.TodoGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateTodo {

  private final TodoGateway todoGateway;

  public void execute(final Todo todo) {
    todoGateway
      .getById(todo.getId())
      .ifPresentOrElse((oldTodo) -> {throw new DuplicateTodoException(todo.getId());},
                       () -> todoGateway.create(todo)
      );
  }
}
