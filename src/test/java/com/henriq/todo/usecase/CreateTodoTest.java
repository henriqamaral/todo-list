package com.henriq.todo.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;

import com.henriq.todo.domain.Task;
import com.henriq.todo.domain.Todo;
import com.henriq.todo.gateway.TodoGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class CreateTodoTest {

  @InjectMocks
  private CreateTodo createTodo;

  @Mock
  private TodoGateway todoGateway;

  @Test
  void shouldCreateTodoWithSuccess() {

    final var task = new Task(1L, "new task", "do bla bla");
    final var todo = new Todo(1L, "new Todo", "Description", List.of(task));

    createTodo.execute(todo);

    Mockito.verify(todoGateway).create(todo);
  }

  @Test
  void shouldFailCreateTodoWithIdSameId() {

    final var task = new Task(1L, "new task", "do bla bla");
    final var todo = new Todo(1L, "new super Todo", "Description", List.of(task));
    doThrow(new RuntimeException("error")).when(todoGateway).create(todo);

    final var mandatoryFieldsException = assertThrows(RuntimeException.class,
                                                      () -> createTodo.execute(todo));

    assertEquals("error", mandatoryFieldsException.getMessage());
    Mockito.verify(todoGateway).create(todo);
  }

}
