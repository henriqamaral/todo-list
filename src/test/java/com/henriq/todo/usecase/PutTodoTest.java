package com.henriq.todo.usecase;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.henriq.todo.domain.Task;
import com.henriq.todo.domain.Todo;
import com.henriq.todo.exception.NotFoundException;
import com.henriq.todo.gateway.TodoGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith({MockitoExtension.class})
public class PutTodoTest {

  @InjectMocks
  private PutTodo putTodo;

  @Mock
  private TodoGateway todoGateway;


  @Test
  void putSucceeds() {

    final var oldTask = new Task(1L, "new task changed", "do bla bla");
    final var oldTodo = new Todo(1L, "new Todo changed", "Description", List.of(oldTask));

    final var task = new Task(1L, "new task changed", "do bla bla");
    final var todo = new Todo(1L, "new Todo changed", "Description", List.of(task));

    when(todoGateway.getById(1L)).thenReturn(oldTodo);

    putTodo.execute(1L, todo);

    verify(todoGateway).getById(1L);
    verify(todoGateway).update(todo);
  }

  @Test
  void shouldFailWhenTodoNotExists() {


    final var task = new Task(1L, "new task changed", "do bla bla");
    final var todo = new Todo(1L, "new Todo changed", "Description", List.of(task));

    when(todoGateway.getById(1L)).thenReturn(null);

    final var notFoundException = assertThrows(NotFoundException.class,
                                               () -> putTodo.execute(1L, todo));

    verify(todoGateway).getById(1L);
    verify(todoGateway, never()).update(todo);

  }
}
