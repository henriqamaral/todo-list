package com.henriq.todo.gateway.http;

import com.henriq.todo.gateway.http.json.TodoResource;
import com.henriq.todo.usecase.GetAllTodo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
public class TodoController {

  private final GetAllTodo getAllTodo;

  @GetMapping
  public List<TodoResource> getAll() {
    return getAllTodo.execute().stream().map(TodoResource::from).collect(Collectors.toList());
  }
}
