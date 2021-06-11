package com.henriq.todo.gateway;

import com.henriq.todo.domain.Todo;

public interface TodoGateway {

  void create(final Todo todo);

  Todo getById(final Long todoId);

  void deleteById(final Long id);

  void update(final Todo todo);
}
