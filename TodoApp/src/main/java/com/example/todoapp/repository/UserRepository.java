package com.example.todoapp.repository;

import com.example.todoapp.model.User;
import org.springframework.data.couchbase.core.query.N1qlPrimaryIndexed;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@N1qlPrimaryIndexed
@ViewIndexed(designDoc = "user")
public interface UserRepository extends CouchbaseRepository<User,String> {

    Optional<User> findByName(String name);
    Optional<User> findByMail(String mail);
    Boolean existsByMail(String mail);
    Boolean existsByName(String name);
}
