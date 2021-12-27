package com.example.todoapp.model;

import com.couchbase.client.core.deps.com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@Document
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
    private String id;

    @NotEmpty
    @Field
    @Size(max=20)
    private String name;

    @Field
    @NotEmpty
    private String mail;

    @Field
    @ToString.Exclude
    private String password;


    public User(String username, String email, String encode) {
    }
}
