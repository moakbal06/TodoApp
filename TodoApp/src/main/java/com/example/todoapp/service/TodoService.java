package com.example.todoapp.service;

import com.example.todoapp.Exception.TodoNotFoundException;
import com.example.todoapp.model.Todo;
import com.example.todoapp.payload.TodoRequest;
import com.example.todoapp.payload.TodoResponse;
import org.springframework.stereotype.Service;

import java.util.List;

public interface TodoService {

    Todo addTodo(Todo todo);

    Todo editTodo(Todo todo);

    void removeTodo(String id) throws TodoNotFoundException;

    Todo getUserTodo(String userId, String todoId) throws TodoNotFoundException;

    List<Todo> getUserTodoList(String userId);

    void setDone(Todo todoRequestDTO) throws TodoNotFoundException;

    void setUndone(Todo todoRequestDTO) throws TodoNotFoundException;
}
