package com.henriq.todo.gateway.http.json;

import com.henriq.todo.domain.Todo;
import lombok.Value;

import java.util.List;

@Value
public class TodoResource {

  Long id;

  String name;

  String description;

  List<TaskResource> tasks;

  public static TodoResource from(final Todo todo) {
    return new TodoResource(todo.getId(), todo.getName(), todo.getDescription(), TaskResource.from(todo.getTasks()));
  }


}
