package com.example.todoapp.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

import java.io.Serializable;
import java.time.LocalDate;

@Document
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Todo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
    @JsonProperty("id")
    private String id;

    @Field
    @JsonProperty("userId")
    private String userId;

    @Field
    @JsonProperty("description")
    private String description;

    @Field
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    @JsonProperty("date")
    private LocalDate date;

    @Field
    Boolean completed;
}
