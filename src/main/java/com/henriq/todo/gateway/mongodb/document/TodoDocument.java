package com.henriq.todo.gateway.mongodb.document;


import com.henriq.todo.domain.Task;
import com.henriq.todo.domain.Todo;
import lombok.Value;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.constraints.NotEmpty;

@Value
@Document(collection = "todos")
public class TodoDocument {

  Long id;

  String name;

  String description;

  List<TaskDocument> tasks;

  public static TodoDocument from(final Todo todo) {
    return new TodoDocument(todo.getId(), todo.getName(), todo.getDescription(),
                            TaskDocument.from(todo.getTasks()));
  }

  public Todo to() {
    return new Todo(this.getId(), this.getName(), this.getDescription(), tasksTo());
  }

  private List<Task> tasksTo() {

    if(this.tasks != null) {
      return this.tasks.stream().map(TaskDocument::to).collect(Collectors.toList());
    }
    return Collections.emptyList();
  }


}
