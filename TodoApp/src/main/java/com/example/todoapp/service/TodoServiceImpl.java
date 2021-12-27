package com.example.todoapp.service;

import com.example.todoapp.Exception.TodoNotFoundException;
import com.example.todoapp.model.Todo;
import com.example.todoapp.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TodoServiceImpl implements TodoService{

    @Autowired
    TodoRepository todoRepository;


    @Override
    public Todo addTodo(Todo todoRequest) {

        todoRequest.setCompleted(false);
        Todo save = todoRepository.save(todoRequest);

        return save;
    }

    @Override
    public Todo editTodo(Todo todoRequest)  {
        return  todoRepository.save(todoRequest);
    }

    @Override
    public void removeTodo(String id) {
        todoRepository.deleteById(id);
    }

    @Override
    public Todo getUserTodo(String userId, String todoId) throws TodoNotFoundException {
        Todo todo = findByUserIdAndId(userId, todoId);
        return todo;
    }


    @Override
    public List<Todo> getUserTodoList(String userId) {
        List<Todo> todoList = todoRepository.findAllByUserId(userId);

        return todoList;
    }

    @Override
    public void setDone(Todo todoRequestDTO) throws TodoNotFoundException {
        Todo todo = todoRepository.findById(todoRequestDTO.getId()).orElseThrow(() -> new TodoNotFoundException("Todo can not found !"));
        todo.setCompleted(true);
        todoRepository.save(todo);

    }
    @Override
    public void setUndone(Todo todoRequestDTO) throws TodoNotFoundException {
        Todo todo = todoRepository.findById(todoRequestDTO.getId()).orElseThrow(() -> new TodoNotFoundException("Todo can not found !"));
        todo.setCompleted(false);
        todoRepository.save(todo);

    }
    private Todo findByUserIdAndId(String userId, String todoId) throws TodoNotFoundException {
        return todoRepository.findByUserIdAndId( userId,todoId).orElseThrow(() -> new TodoNotFoundException("Todo can not found !"));
    }
    private Todo findById( String todoId) throws TodoNotFoundException {
        return todoRepository.findById( todoId).orElseThrow(() -> new TodoNotFoundException("Todo can not found !"));
    }
}
