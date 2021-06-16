package com.henriq.todo.gateway.mongodb.document;

import com.henriq.todo.domain.Task;
import lombok.Value;

import java.util.List;
import java.util.stream.Collectors;

@Value
public class TaskDocument {

  Long id;

  String name;

  String description;

  public static TaskDocument from(final Task task) {
    return new TaskDocument(task.getId(), task.getName(), task.getDescription());
  }

  public static List<TaskDocument> from(final List<Task> tasks) {
    return tasks.stream().map(TaskDocument::from).collect(Collectors.toList());
  }

  public Task to() {
    return new Task(this.getId(), this.getName(), this.getDescription());
  }

}
