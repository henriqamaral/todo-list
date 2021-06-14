package com.henriq.todo.gateway.mongodb;

import com.henriq.todo.domain.Todo;
import com.henriq.todo.gateway.TodoGateway;
import com.henriq.todo.gateway.mongodb.document.TodoDocument;
import com.henriq.todo.gateway.mongodb.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TodoMongoDbGateway implements TodoGateway {

  private final TodoRepository todoRepository;

  @Override
  public void create(Todo todo) {
    todoRepository.save(TodoDocument.from(todo));
  }

  @Override
  public Optional<Todo> getById(Long todoId) {
    return todoRepository.findById(todoId).map(TodoDocument::to);
  }

  @Override
  public void deleteById(Long id) {
    todoRepository.deleteById(id);
  }

  @Override
  public void update(Todo todo) {
    todoRepository.save(TodoDocument.from(todo));
  }

  @Override
  public List<Todo> getAll() {
    return todoRepository.findAll().stream().map(TodoDocument::to).collect(Collectors.toList());
  }
}
