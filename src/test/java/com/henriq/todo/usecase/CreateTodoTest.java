package com.henriq.todo.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.henriq.todo.domain.Task;
import com.henriq.todo.domain.Todo;
import com.henriq.todo.exception.DuplicateTodoException;
import com.henriq.todo.gateway.TodoGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

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

    verify(todoGateway).create(todo);
  }

  @Test
  void shouldFailCreateTodoWithIdSameId() {

    when(todoGateway.getById(1L))
      .thenReturn(Optional.of(new Todo(1L, "name", null, null)));

    final var task = new Task(1L, "new task", "do bla bla");
    final var todo = new Todo(1L, "new super Todo", "Description", List.of(task));

    final var mandatoryFieldsException = assertThrows(DuplicateTodoException.class,
                                                      () -> createTodo.execute(todo));

    assertEquals("Todo Id: 1 already in database", mandatoryFieldsException.getMessage());
  }

}
