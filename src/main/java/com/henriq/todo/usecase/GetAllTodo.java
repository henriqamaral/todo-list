package com.henriq.todo.usecase;

import com.henriq.todo.domain.Todo;
import com.henriq.todo.gateway.TodoGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllTodo {

  private final TodoGateway todoGateway;

  public List<Todo> execute() {
    return todoGateway.getAll();
  }
}
