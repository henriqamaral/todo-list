package com.henriq.todo.usecase;

import com.henriq.todo.domain.Todo;
import com.henriq.todo.exception.NotFoundException;
import com.henriq.todo.gateway.TodoGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PutTodo {

  private final TodoGateway todoGateway;

  public void execute(final Long id, final Todo todo) {

    final var oldTodo = todoGateway.getById(id);

    oldTodo
      .ifPresentOrElse((old) -> todoGateway.update(todo),
                       () -> { throw new NotFoundException(id); }
      );
  }
}
