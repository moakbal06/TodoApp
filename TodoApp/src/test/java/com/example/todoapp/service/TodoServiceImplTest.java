package com.example.todoapp.service;

import com.example.todoapp.Exception.TodoNotFoundException;
import com.example.todoapp.model.Todo;
import com.example.todoapp.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TodoServiceImplTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoServiceImpl todoService;

    private Todo sampleTodo;
    private LocalDateTime testStartTime;

    @BeforeEach
    void setUp() {
        testStartTime = LocalDateTime.now();
        sampleTodo = Todo.builder()
                .userId("user1")
                .description("Test todo")
                .build();
    }

    @Test
    void addTodo_SetsDateAutomatically() {
        given(todoRepository.save(any(Todo.class))).willAnswer(invocation -> {
            Todo saved = invocation.getArgument(0);
            saved.setId("generated-id");
            return saved;
        });

        Todo result = todoService.addTodo(sampleTodo);

        assertNotNull(result.getDate(), "Date should be set automatically");
        long secondsDifference = ChronoUnit.SECONDS.between(testStartTime, result.getDate());
        assertTrue(secondsDifference >= 0 && secondsDifference <= 2,
                "Date should be close to current time (within 2 seconds)");
    }

    @Test
    void addTodo_SetsCompletedToFalse() {
        given(todoRepository.save(any(Todo.class))).willAnswer(invocation -> invocation.getArgument(0));

        Todo result = todoService.addTodo(sampleTodo);

        assertFalse(result.getCompleted(), "Completed should be set to false");
    }

    @Test
    void addTodo_ReturnsSavedTodo() {
        ArgumentCaptor<Todo> todoCaptor = ArgumentCaptor.forClass(Todo.class);
        given(todoRepository.save(any(Todo.class))).willAnswer(invocation -> {
            Todo saved = invocation.getArgument(0);
            saved.setId("generated-id");
            return saved;
        });

        Todo result = todoService.addTodo(sampleTodo);

        verify(todoRepository).save(todoCaptor.capture());
        Todo capturedTodo = todoCaptor.getValue();

        assertEquals("user1", capturedTodo.getUserId());
        assertEquals("Test todo", capturedTodo.getDescription());
        assertEquals(false, capturedTodo.getCompleted());
        assertNotNull(capturedTodo.getDate());
    }

    @Test
    void setDone_ThrowsException_WhenTodoNotFound() {
        Todo todoRequest = Todo.builder()
                .id("non-existent-id")
                .build();

        given(todoRepository.findById("non-existent-id")).willReturn(Optional.empty());

        assertThrows(TodoNotFoundException.class, () -> todoService.setDone(todoRequest),
                "Should throw TodoNotFoundException when todo is not found");
    }

    @Test
    void setUndone_ThrowsException_WhenTodoNotFound() {
        Todo todoRequest = Todo.builder()
                .id("non-existent-id")
                .build();

        given(todoRepository.findById("non-existent-id")).willReturn(Optional.empty());

        assertThrows(TodoNotFoundException.class, () -> todoService.setUndone(todoRequest),
                "Should throw TodoNotFoundException when todo is not found");
    }

    @Test
    void editTodo_PreservesCreationDate() throws TodoNotFoundException {
        LocalDateTime originalDate = LocalDateTime.of(2024, 1, 1, 12, 0, 0);
        Todo existingTodo = Todo.builder()
                .id("existing-id")
                .userId("user1")
                .description("Original description")
                .completed(false)
                .date(originalDate)
                .build();

        Todo updateRequest = Todo.builder()
                .id("existing-id")
                .userId("user1")
                .description("Updated description")
                .completed(true)
                .date(LocalDateTime.now())
                .build();

        given(todoRepository.findById("existing-id")).willReturn(Optional.of(existingTodo));
        given(todoRepository.save(any(Todo.class))).willAnswer(invocation -> invocation.getArgument(0));

        Todo result = todoService.editTodo(updateRequest);

        assertEquals(originalDate, result.getDate(), "Creation date should be preserved from existing todo");
        assertEquals("Updated description", result.getDescription(), "Description should be updated");
        assertTrue(result.getCompleted(), "Completed status should be updated");
    }

    @Test
    void editTodo_ThrowsException_WhenTodoNotFound() {
        Todo updateRequest = Todo.builder()
                .id("non-existent-id")
                .userId("user1")
                .description("Updated description")
                .build();

        given(todoRepository.findById("non-existent-id")).willReturn(Optional.empty());

        assertThrows(TodoNotFoundException.class, () -> todoService.editTodo(updateRequest),
                "Should throw TodoNotFoundException when todo is not found");
    }

    @Test
    void editTodo_ThrowsException_WithTurkishMessage_WhenIdIsNull() {
        Todo updateRequest = Todo.builder()
                .id(null)
                .userId("user1")
                .description("Updated description")
                .build();

        TodoNotFoundException exception = assertThrows(TodoNotFoundException.class,
                () -> todoService.editTodo(updateRequest));

        assertEquals("Todo düzenleme işlemi için ID gereklidir", exception.getMessage(),
                "Exception message should be in Turkish");
    }

    @Test
    void editTodo_ThrowsException_WithTurkishMessage_WhenIdIsEmpty() {
        Todo updateRequest = Todo.builder()
                .id("")
                .userId("user1")
                .description("Updated description")
                .build();

        TodoNotFoundException exception = assertThrows(TodoNotFoundException.class,
                () -> todoService.editTodo(updateRequest));

        assertEquals("Todo düzenleme işlemi için ID gereklidir", exception.getMessage(),
                "Exception message should be in Turkish");
    }

    @Test
    void editTodo_ThrowsException_WithTurkishMessage_WhenTodoNotFound() {
        Todo updateRequest = Todo.builder()
                .id("non-existent-id")
                .userId("user1")
                .description("Updated description")
                .build();

        given(todoRepository.findById("non-existent-id")).willReturn(Optional.empty());

        TodoNotFoundException exception = assertThrows(TodoNotFoundException.class,
                () -> todoService.editTodo(updateRequest));

        assertEquals("Todo bulunamadı!", exception.getMessage(),
                "Exception message should be in Turkish");
    }

    @Test
    void setDone_ThrowsException_WithTurkishMessage_WhenTodoNotFound() {
        Todo todoRequest = Todo.builder()
                .id("non-existent-id")
                .build();

        given(todoRepository.findById("non-existent-id")).willReturn(Optional.empty());

        TodoNotFoundException exception = assertThrows(TodoNotFoundException.class,
                () -> todoService.setDone(todoRequest));

        assertEquals("Todo bulunamadı!", exception.getMessage(),
                "Exception message should be in Turkish");
    }

    @Test
    void setUndone_ThrowsException_WithTurkishMessage_WhenTodoNotFound() {
        Todo todoRequest = Todo.builder()
                .id("non-existent-id")
                .build();

        given(todoRepository.findById("non-existent-id")).willReturn(Optional.empty());

        TodoNotFoundException exception = assertThrows(TodoNotFoundException.class,
                () -> todoService.setUndone(todoRequest));

        assertEquals("Todo bulunamadı!", exception.getMessage(),
                "Exception message should be in Turkish");
    }
}
