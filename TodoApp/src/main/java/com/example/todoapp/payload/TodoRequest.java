package com.example.todoapp.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class TodoRequest implements Serializable {
    @JsonProperty("title")
    @NotNull
    String title;
    @JsonProperty("date")
    @NotNull
    LocalDate date;
    @JsonProperty("description")
    String description;
    @JsonProperty("userId")
    String userId;
}
