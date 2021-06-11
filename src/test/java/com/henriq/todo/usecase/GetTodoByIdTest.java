package com.henriq.todo.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.henriq.todo.domain.Task;
import com.henriq.todo.domain.Todo;
import com.henriq.todo.exception.NotFoundException;
import com.henriq.todo.gateway.TodoGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class GetTodoByIdTest {

  @InjectMocks
  private GetTodoById getTodoById;

  @Mock
  private TodoGateway todoGateway;

  @Test
  void getByIdSucceeds() {

    final var task = new Task(1L, "new task", "do bla bla");
    final var todo = new Todo(1L, "new Todo", "Description", List.of(task));

    when(todoGateway.getById(1L)).thenReturn(Optional.of(todo));
    final var returnedTodo = getTodoById.execute(1L);

    Assertions.assertAll(
      () -> Assertions.assertNotNull(returnedTodo),
      () -> Assertions.assertEquals(todo, returnedTodo)
    );

    verify(todoGateway).getById(1L);
  }

  @Test
  void shouldReturnNotFoundException() {

    when(todoGateway.getById(1L)).thenReturn(Optional.empty());

    final var notFoundException = assertThrows(NotFoundException.class,
                                               () -> getTodoById.execute(1L));

    assertEquals("Id: 1 not found", notFoundException.getMessage());
  }

}
