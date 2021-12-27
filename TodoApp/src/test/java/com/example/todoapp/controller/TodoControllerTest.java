package com.example.todoapp.controller;


import com.example.todoapp.model.Todo;
import com.example.todoapp.security.AuthEntryPointJwt;
import com.example.todoapp.security.JwtUtils;
import com.example.todoapp.service.TodoService;
import com.example.todoapp.service.UserDetailsServiceImpl;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = TodoController.class)
@AutoConfigureMockMvc(addFilters = false)
@DisplayName("Todo Service Controller Test")
public class TodoControllerTest {

    private static final LocalDate Date = LocalDate.now();
    private static final String Description = "DEFAULT_DESCRIPTION";
    private static final String DefaultUserId = "Test";
    private static final String url = "/api/todo";

    private Todo todoRequestDTO;
    private Todo todoResponseDTO;
    private String todoId;
    private String userId;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    TodoService todoService;

    @MockBean
    PasswordEncoder bCryptPasswordEncoder;

    @MockBean
    JwtUtils jwtUtils;

    @MockBean
    AuthEntryPointJwt authEntryPoint;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;


    @BeforeEach
    void setUp() {
        todoId = UUID.randomUUID().toString();
        userId = UUID.randomUUID().toString();

        todoRequestDTO = Todo.builder()
                .userId(DefaultUserId)
                .description(Description)
                .build();

        todoResponseDTO = Todo.builder()
                .id(todoId)
                .description(Description)
                .build();
    }
    @Test
    void addTodoServiceControllerTest() throws Exception {
        objectMapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
        String content = objectMapper.writeValueAsString(todoRequestDTO);
        given(todoService.addTodo(todoRequestDTO)).willReturn(todoResponseDTO);
        String uri = url.concat("/addTodo");

        MockHttpServletRequestBuilder builder = post(uri)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        MockHttpServletResponse response = mockMvc.perform(builder).andReturn().getResponse();

        Todo requestContent = objectMapper.readValue(content, Todo.class);
        Todo responseContent = objectMapper.readValue(response.getContentAsString(), Todo.class);

        assertAll("Verify the TODO Controller POST condition",
                () -> assertEquals(201, response.getStatus()),
                () -> assertEquals(requestContent.getDescription(), responseContent.getDescription())
        );
    }

    @Test
    void editTodoServiceControllerTest() throws Exception {
        String content = objectMapper.writeValueAsString(todoRequestDTO);
        given(todoService.editTodo(todoRequestDTO)).willReturn(todoResponseDTO);
        String uri = url.concat("/editTodo");

        MockHttpServletRequestBuilder builder = put(uri)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        MockHttpServletResponse response = mockMvc.perform(builder).andReturn().getResponse();

        Todo requestContent = objectMapper.readValue(content, Todo.class);
        Todo responseContent = objectMapper.readValue(response.getContentAsString(), Todo.class);

        assertAll("Verify the TODO Controller PUT condition",
                () -> assertEquals(200, response.getStatus()),
                () -> assertEquals(requestContent.getDescription(), responseContent.getDescription())
        );
    }


    @Test
    void getAllUserTodoServiceControllerTest() throws Exception {
        String uri = url.concat("/getTodos/").concat(DefaultUserId);

        MockHttpServletRequestBuilder builder = get(uri).contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(builder).andReturn().getResponse();

        assertEquals(200, response.getStatus(), "Verify the TODO Controller Get condition");
    }

    @Test
    void completeTodoServiceControllerTest() throws Exception {
        String content = objectMapper.writeValueAsString(todoRequestDTO);

        String uri = url.concat("/setDone");

        MockHttpServletRequestBuilder builder = post(uri).contentType(MediaType.APPLICATION_JSON).content(content);

        MockHttpServletResponse response = mockMvc.perform(builder).andReturn().getResponse();

        assertEquals(200, response.getStatus(), "Verify the TODO Controller Get condition");
    }

    @Test
    void deleteTodoServiceControllerTest() throws Exception {
        String uri = url.concat("/deleteTodo/").concat(todoId);

        MockHttpServletRequestBuilder builder = delete(uri).contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(builder).andReturn().getResponse();

        assertEquals(204, response.getStatus(), "Verify the TODO Controller DELETE condition");
    }
}
