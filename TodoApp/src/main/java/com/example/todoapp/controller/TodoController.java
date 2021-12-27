package com.example.todoapp.controller;


import com.example.todoapp.Exception.TodoNotFoundException;
import com.example.todoapp.model.Todo;
import com.example.todoapp.payload.MessageResponse;
import com.example.todoapp.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/todo")
public class TodoController {

    @Autowired
    TodoService todoService;

    @GetMapping("getTodos/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Todo> getAllUserTodo(@PathVariable String userId) {
        return todoService.getUserTodoList(userId);
    }

    @PostMapping("/addTodo")
    @ResponseStatus(HttpStatus.CREATED)
    public Todo addTodo(@RequestBody @Valid Todo todoRequestDTO) {
         return todoService.addTodo(todoRequestDTO);
    }

    @PutMapping("/editTodo")
    @ResponseStatus(HttpStatus.OK)
    public Todo editTodo(@RequestBody @Valid Todo todoRequestDTO) {
           return  todoService.editTodo(todoRequestDTO);

    }

    @PostMapping("/setDone")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<MessageResponse> setDone(@RequestBody @Valid Todo todoRequestDTO)
    {
            try {
                todoService.setDone(todoRequestDTO);
                return new ResponseEntity<>(null, HttpStatus.OK);
            } catch (Exception e) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse(e.getMessage()));
            }
    }
    @PostMapping("/setUndone")
    @ResponseStatus(HttpStatus.CREATED)
    public  ResponseEntity<MessageResponse>  setUndone(@RequestBody @Valid Todo todoRequestDTO)  {
        try {
            todoService.setUndone(todoRequestDTO);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(e.getMessage()));
        }    }

    @DeleteMapping("/deleteTodo/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTodo(@PathVariable String id) throws TodoNotFoundException {
        todoService.removeTodo(id);
    }
}
