package com.henriq.todo.e2e;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import com.henriq.todo.gateway.http.json.TaskResource;
import com.henriq.todo.gateway.http.json.TodoResource;
import com.henriq.todo.gateway.mongodb.document.TaskDocument;
import com.henriq.todo.gateway.mongodb.document.TodoDocument;
import com.henriq.todo.gateway.mongodb.repository.TodoRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

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

  @Nested
  class GetAll {

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

  }

  @Nested
  class GetById {

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

  @Nested
  class CreateTodo {

    @Test
    void shouldFailTodoWithNoTaskAndIdNull() {

      final var todoNoId = new TodoResource(null, "name", "description", null);

      webTestClient
        .post()
        .uri("/todos")
        .body(Mono.just(todoNoId), TodoResource.class)
        .exchange()
        .expectStatus()
        .isBadRequest()
        .expectBody()
        .jsonPath("$.description").isEqualTo("id value must not be null");
    }

    @Test
    void shouldFailTodoWithNoTaskAndIdNullAndNameNull() {

      final var todoNoIdNoName = new TodoResource(null, null, "description", null);

      webTestClient
        .post()
        .uri("/todos")
        .body(Mono.just(todoNoIdNoName), TodoResource.class)
        .exchange()
        .expectStatus()
        .isBadRequest()
        .expectBody()
        .jsonPath("$.description").value(Matchers.anything("id value must not be null")
      );
    }

    @Test
    void shouldFailTodoWithNoTaskAndNameNull() {

      final var todoNoIdNoName = new TodoResource(1L, null, "description", null);

      webTestClient
        .post()
        .uri("/todos")
        .body(Mono.just(todoNoIdNoName), TodoResource.class)
        .exchange()
        .expectStatus()
        .isBadRequest()
        .expectBody()
        .jsonPath("$.description").isEqualTo("name value must not be empty");
    }

    @Test
    void shouldFailCreateTodoWithAlreadyId() {

      final var todoInDb = new TodoDocument(1L, "todo no task", "description", null);
      todoRepository.save(todoInDb);

      final var newTodo = new TodoResource(1L, "new name", "description", null);

      webTestClient
        .post()
        .uri("/todos")
        .body(Mono.just(newTodo), TodoResource.class)
        .exchange()
        .expectStatus()
        .isBadRequest()
        .expectBody()
        .jsonPath("$.description").isEqualTo("Todo Id: 1 already in database");
    }

    @Test
    void shouldFailCreateTodoWithTaskWithDuplicatedIds() {

      final var task = new TaskResource(1L, "task one", "description");
      final var taskWithDuplicatedId = new TaskResource(1L, "task duplicated id", "description");
      final var newTodo = new TodoResource(1L, "new name", "description", List.of(task, taskWithDuplicatedId));

      webTestClient
        .post()
        .uri("/todos")
        .body(Mono.just(newTodo), TodoResource.class)
        .exchange()
        .expectStatus()
        .isBadRequest()
        .expectBody()
        .jsonPath("$.description").isEqualTo("Task ids must be unique.[1, 1]");
    }

    @Test
    void shouldFailCreateTodoWithTaskWithNoId() {

      final var taskWihNoId = new TaskResource(null, "task one", "description");
      final var newTodo = new TodoResource(1L, "new name", "description", List.of(taskWihNoId));

      webTestClient
        .post()
        .uri("/todos")
        .body(Mono.just(newTodo), TodoResource.class)
        .exchange()
        .expectStatus()
        .isBadRequest()
        .expectBody()
        .jsonPath("$.description").isEqualTo("id value must not be null");
    }

    @Test
    void shouldFailCreateTodoWithTaskWithEmptyName() {

      final var taskWihNoName = new TaskResource(1L, "", "description");
      final var newTodo = new TodoResource(1L, "new name", "description", List.of(taskWihNoName));

      webTestClient
        .post()
        .uri("/todos")
        .body(Mono.just(newTodo), TodoResource.class)
        .exchange()
        .expectStatus()
        .isBadRequest()
        .expectBody()
        .jsonPath("$.description").isEqualTo("name value must not be empty");
    }

    @Test
    void shouldCreateTodoWithNoTask() {

      final var newTodo = new TodoResource(1L, "new name", "description", null);

      webTestClient
        .post()
        .uri("/todos")
        .body(Mono.just(newTodo), TodoResource.class)
        .exchange()
        .expectStatus()
        .isCreated();
    }

    @Test
    void shouldCreateTodoWithTask() {

      final var task = new TaskResource(1L, "task", "description");
      final var newTodo = new TodoResource(1L, "new name", "description", List.of(task));

      webTestClient
        .post()
        .uri("/todos")
        .body(Mono.just(newTodo), TodoResource.class)
        .exchange()
        .expectStatus()
        .isCreated();

      final var todo = todoRepository.findById(1L);
      assertThat(todo)
        .isPresent()
        .get()
        .extracting("id", "name", "description")
        .contains(1L, "new name", "description");

    }

  }

  @Nested
  class UpdateTodo {

    @Test
    void shouldFailUpdateWithTodoNotFound() {

      final var task = new TaskResource(1L, "task", "description");
      final var updateTodo = new TodoResource(1L, "new name", "description", List.of(task));

      webTestClient
        .put()
        .uri("/todos/{id}", 1)
        .body(Mono.just(updateTodo), TodoResource.class)
        .exchange()
        .expectStatus()
        .isNotFound()
        .expectBody()
        .jsonPath("$.description").isEqualTo("Id: 1 not found");
    }

    @Test
    void shouldFailUpdateWithTodoWithTasksDuplicated() {

      final var task = new TaskResource(1L, "task", "description");
      final var taskWithDuplicatedId = new TaskResource(1L, "task duplicated id", "description");
      final var updateTodo = new TodoResource(1L, "new name", "description", List.of(task, taskWithDuplicatedId));

      webTestClient
        .put()
        .uri("/todos/{id}", 1)
        .body(Mono.just(updateTodo), TodoResource.class)
        .exchange()
        .expectStatus()
        .isBadRequest()
        .expectBody()
        .jsonPath("$.description").isEqualTo("Task ids must be unique.[1, 1]");
    }

    @Test
    void shouldUpdateTodoWithSuccess() {

      final var todoInDb = new TodoDocument(1L, "todo no task", "description", null);
      todoRepository.save(todoInDb);

      final var task = new TaskResource(1L, "new task", "description");
      final var updateTodo = new TodoResource(1L, "todo with task", "description", List.of(task));

      webTestClient
        .put()
        .uri("/todos/{id}", 1)
        .body(Mono.just(updateTodo), TodoResource.class)
        .exchange()
        .expectStatus()
        .isNoContent();

      final var todoUpdated = todoRepository.findById(1L);

      assertThat(todoUpdated)
        .isPresent()
        .get()
        .extracting("id", "name", "description", "tasks")
        .doesNotContainNull()
        .contains(1L, "todo with task", "description",
                  List.of(new TaskDocument(1L, "new task", "description")));
    }


  }

  @Nested
  class DeleteTodo {

    @Test
    void shouldFailDeleteWithTodoNotFound() {

      webTestClient
        .delete()
        .uri("/todos/{id}", 1)
        .exchange()
        .expectStatus()
        .isNotFound()
        .expectBody()
        .jsonPath("$.description").isEqualTo("Id: 1 not found");
    }

    @Test
    void shouldDeleteTodo() {

      final var todoTobeDeleted = new TodoDocument(1L, "todo no task", "description", null);
      todoRepository.save(todoTobeDeleted);

      webTestClient
        .delete()
        .uri("/todos/{id}", 1)
        .exchange()
        .expectStatus()
        .isAccepted()
        .expectBody()
        .isEmpty();

      final var todo = todoRepository.findById(1L);
      Assertions.assertFalse(todo.isPresent());
    }

  }



}
