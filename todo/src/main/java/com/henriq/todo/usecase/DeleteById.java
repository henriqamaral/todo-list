package com.henriq.todo.usecase;

import com.henriq.todo.exception.NotFoundException;
import com.henriq.todo.gateway.TodoGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteById {

  private final TodoGateway todoGateway;

  public void execute(Long id) {

    final var oldTodo = todoGateway.getById(id);

    oldTodo
      .ifPresentOrElse((old) -> todoGateway.deleteById(id),
                       () -> { throw new NotFoundException(id); }
      );
  }
}
