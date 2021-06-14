package com.henriq.todo.gateway.mongodb.repository;

import com.henriq.todo.gateway.mongodb.document.TodoDocument;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends CrudRepository<TodoDocument, Long> {

  List<TodoDocument> findAll();

}
