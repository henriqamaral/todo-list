package com.henriq.todo.gateway.http;

import com.henriq.todo.gateway.http.json.TodoResource;
import com.henriq.todo.usecase.CreateTodo;
import com.henriq.todo.usecase.GetAllTodo;
import com.henriq.todo.usecase.GetTodoById;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
public class TodoController {

  private final GetAllTodo getAllTodo;

  private final GetTodoById getTodoById;

  private final CreateTodo createTodo;

  @GetMapping
  public List<TodoResource> getAll() {
    return getAllTodo.execute().stream().map(TodoResource::from).collect(Collectors.toList());
  }

  @GetMapping("/{id}")
  public TodoResource getById(@PathVariable("id") Long id) {
    return TodoResource.from(getTodoById.execute(id));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void create(@RequestBody final TodoResource todoResource) {
    createTodo.execute(todoResource.to());
  }
}
