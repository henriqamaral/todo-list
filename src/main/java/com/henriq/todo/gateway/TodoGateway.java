package com.henriq.todo.gateway;

import com.henriq.todo.domain.Todo;

public interface TodoGateway {

  void create(final Todo todo);

  Todo getById(Long todoId);
}
