package com.henriq.todo.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;

import com.henriq.todo.domain.Todo;
import com.henriq.todo.gateway.TodoGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CreateTodoTest {

  private CreateTodo createTodo;

  @Mock
  private TodoGateway todoGateway;


  @BeforeEach
  void setup() {
    createTodo = new CreateTodo(todoGateway);
  }

  @Test
  void shouldCreateTodoWithSuccess() {

    final var todo = new Todo(1L, "new Todo", "Description");

    createTodo.execute(todo);

    Mockito.verify(todoGateway).create(todo);
  }

  @Test
  void shouldFailCreateTodoWithIdSameId() {

    final var todo = new Todo(1L, "new super Todo", "Description");
    doThrow(new RuntimeException("error")).when(todoGateway).create(todo);

    final var mandatoryFieldsException = assertThrows(RuntimeException.class,
                                                      () -> createTodo.execute(todo));
    assertEquals("error", mandatoryFieldsException.getMessage());
  }

}
