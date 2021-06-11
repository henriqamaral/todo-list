package com.henriq.todo.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import com.henriq.todo.exception.NotFoundException;
import com.henriq.todo.gateway.TodoGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DeleteByIdTest {

  @InjectMocks
  private DeleteById deleteById;

  @Mock
  private TodoGateway todoGateway;

  @Test
  void deleteByIdSucceeds() {

    deleteById.execute(1L);

    verify(todoGateway).deleteById(1L);
  }

  @Test
  void shouldReturnNotFoundException() {

    doThrow(new NotFoundException(1L)).when(todoGateway).deleteById(1L);

    final var notFoundException = assertThrows(NotFoundException.class,
                                               () -> deleteById.execute(1L));

    assertEquals("Id: 1 not found", notFoundException.getMessage());
  }

}
