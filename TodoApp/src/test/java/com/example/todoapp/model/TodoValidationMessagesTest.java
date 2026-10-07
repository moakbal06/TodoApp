package com.example.todoapp.model;

import org.junit.jupiter.api.Test;

import javax.validation.constraints.NotBlank;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TodoValidationMessagesTest {

    @Test
    void userId_ValidationMessage_ShouldBeInTurkish() throws NoSuchFieldException {
        Field userIdField = Todo.class.getDeclaredField("userId");
        NotBlank notBlankAnnotation = userIdField.getAnnotation(NotBlank.class);

        assertNotNull(notBlankAnnotation, "userId should have @NotBlank annotation");
        assertEquals("Kullanıcı ID'si zorunludur", notBlankAnnotation.message(),
                "userId validation message should be in Turkish");
    }

    @Test
    void description_ValidationMessage_ShouldBeInTurkish() throws NoSuchFieldException {
        Field descriptionField = Todo.class.getDeclaredField("description");
        NotBlank notBlankAnnotation = descriptionField.getAnnotation(NotBlank.class);

        assertNotNull(notBlankAnnotation, "description should have @NotBlank annotation");
        assertEquals("Açıklama zorunludur", notBlankAnnotation.message(),
                "description validation message should be in Turkish");
    }
}
