package com.henriq.todo.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.henriq.todo.domain.Task;
import com.henriq.todo.domain.Todo;
import com.henriq.todo.gateway.TodoGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class GetAllTodoTest {

  @InjectMocks
  private GetAllTodo getAllTodo;

  @Mock
  private TodoGateway todoGateway;

  @Test
  void getAllSucceeds() {

    final var task = new Task(1L, "new task", "do bla bla");
    final var todo = new Todo(1L, "new Todo", "Description", List.of(task));
    Mockito.when(todoGateway.getAll()).thenReturn(List.of(todo));

    final List<Todo> todos = getAllTodo.execute();

    assertThat(todos)
      .isNotEmpty()
      .contains(todo);

    Mockito.verify(todoGateway).getAll();
  }

  @Test
  void shouldReturnEmptyListNoDataFound() {

    Mockito.when(todoGateway.getAll()).thenReturn(Collections.emptyList());

    final List<Todo> todos = getAllTodo.execute();

    assertThat(todos)
      .isEmpty();

    Mockito.verify(todoGateway).getAll();
  }

}