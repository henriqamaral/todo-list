package com.henriq.todo.gateway.http.json;

import com.henriq.todo.domain.Task;
import com.henriq.todo.domain.Todo;
import lombok.Value;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Value
public class TodoResource {

  Long id;

  String name;

  String description;

  List<TaskResource> tasks;

  public static TodoResource from(final Todo todo) {
    return new TodoResource(todo.getId(), todo.getName(), todo.getDescription(), TaskResource.from(todo.getTasks()));
  }

  public Todo to() {
    return new Todo(this.getId(), this.getName(), this.getDescription(), tasksTo());
  }

  private List<Task> tasksTo() {

    if(this.tasks != null) {
      return this.tasks.stream().map(TaskResource::to).collect(Collectors.toList());
    }
    return Collections.emptyList();
  }
}
