package com.henriq.todo.gateway;

import com.henriq.todo.domain.Todo;

import java.util.Optional;

public interface TodoGateway {

  void create(final Todo todo);

  Optional<Todo> getById(final Long todoId);

  void deleteById(final Long id);

  void update(final Todo todo);
}
