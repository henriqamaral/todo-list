package com.henriq.todo.gateway.http.json;

import com.henriq.todo.domain.Task;
import lombok.Value;

import java.util.List;
import java.util.stream.Collectors;

@Value
public class TaskResource {

  Long id;

  String name;

  String description;

  public static TaskResource from(final Task task) {
    return new TaskResource(task.getId(), task.getName(), task.getDescription());
  }

  public static List<TaskResource> from(final List<Task> tasks) {
    return tasks.stream().map(TaskResource::from).collect(Collectors.toList());
  }

}
