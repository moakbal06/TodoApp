package com.example.todoapp.repository;

import com.example.todoapp.model.Todo;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRepository extends CouchbaseRepository<Todo,String> {

    List<Todo> findAllByUserId(String id);
    Optional<Todo> findByUserIdAndId(String userId,String id);

    void deleteById(String id);
}
