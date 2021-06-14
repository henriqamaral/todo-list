package com.henriq.todo.e2e;

import static org.springframework.http.MediaType.APPLICATION_JSON;

import com.henriq.todo.gateway.mongodb.document.TaskDocument;
import com.henriq.todo.gateway.mongodb.document.TodoDocument;
import com.henriq.todo.gateway.mongodb.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TodoIntegration {

  @Autowired
  private WebTestClient webTestClient;

  @Autowired
  private TodoRepository todoRepository;

  @BeforeEach
  void setup() {
    todoRepository.deleteAll();
  }

  @Test
  void shouldReturnEmptyList() {

    webTestClient
      .get()
        .uri("/todos")
      .exchange()
        .expectStatus()
        .isOk()
      .expectHeader()
      .contentType(APPLICATION_JSON)
      .expectBody()
      .jsonPath("$.length()").isEqualTo(0)
      .json("[]");
  }

  @Test
  void shouldReturnTodoList() {

    final var todoNoTask = new TodoDocument(1L, "todo no task", "description", null);
    final var task = new TaskDocument(1L, "task", "description");
    final var todoWithTask = new TodoDocument(2L, "todo with task", "second description", List.of(task));

    todoRepository.saveAll(List.of(todoNoTask, todoWithTask));

    webTestClient
      .get()
        .uri("/todos")
      .exchange()
        .expectStatus()
        .isOk()
      .expectHeader()
        .contentType(APPLICATION_JSON)
      .expectBody()
        .jsonPath("$.length()").isEqualTo(2)
        .jsonPath("$[0].id").isEqualTo(1)
        .jsonPath("$[0].name").isEqualTo("todo no task")
        .jsonPath("$[0].description").isEqualTo("description")
        .jsonPath("$[0].tasks").isEmpty()
        .jsonPath("$[1].id").isEqualTo(2)
        .jsonPath("$[1].name").isEqualTo("todo with task")
        .jsonPath("$[1].description").isEqualTo("second description")
        .jsonPath("$[1].tasks").isNotEmpty()
        .jsonPath("$[1].tasks[0].id").isEqualTo(1)
        .jsonPath("$[1].tasks[0].name").isEqualTo("task")
        .jsonPath("$[1].tasks[0].description").isEqualTo("description");
  }

  @Test
  void shouldReturnNotFoundIdNotExist() {

    webTestClient
      .get()
        .uri("/todos/{id}", 1)
      .exchange()
        .expectStatus()
        .isNotFound()
      .expectBody()
        .jsonPath("$.description").isEqualTo("Id: 1 not found");
  }

  @Test
  void shouldReturnTodoWithNoTaskById() {

    final var todoNoTask = new TodoDocument(1L, "todo no task", "description", null);
    todoRepository.save(todoNoTask);

    webTestClient
      .get()
        .uri("/todos/{id}", 1)
      .exchange()
        .expectStatus()
        .isOk()
      .expectBody()
        .jsonPath("$.id").isEqualTo(1)
        .jsonPath("$.name").isEqualTo("todo no task")
        .jsonPath("$.description").isEqualTo("description")
        .jsonPath("$.tasks").isEmpty();
  }

  @Test
  void shouldReturnTodoWithTaskById() {

    final var task = new TaskDocument(1L, "task", "description");
    final var todoWithTask = new TodoDocument(2L, "todo with task", "second description", List.of(task));
    todoRepository.save(todoWithTask);

    webTestClient
      .get()
        .uri("/todos/{id}", 2)
      .exchange()
        .expectStatus()
        .isOk()
      .expectBody()
        .jsonPath("$.id").isEqualTo(2)
        .jsonPath("$.name").isEqualTo("todo with task")
        .jsonPath("$.description").isEqualTo("second description")
        .jsonPath("$.tasks").isNotEmpty()
        .jsonPath("$.tasks[0].id").isEqualTo(1)
        .jsonPath("$.tasks[0].name").isEqualTo("task")
        .jsonPath("$.tasks[0].description").isEqualTo("description");
  }

}
