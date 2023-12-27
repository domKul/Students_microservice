package com.dominik.studenrabbit.controller;

import com.dominik.studenrabbit.model.Student;
import com.dominik.studenrabbit.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class StudentControllerTest {

    @Autowired
    private StudentService service;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private Student student;

    @Test
    void mockCheck() {
        assertNotNull(mockMvc);
    }

    @BeforeEach
    void dataForTests() {
       // student = new Student(20000L,"firstName","lastName","email@email.com");
    }

    @Test
    void shouldCreateSaveStudent() throws Exception {
        //Given
        student = new Student(20000L,"firstName","lastName","email@email.com");
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(20000))
                .andExpect(jsonPath("$.firstName").value("firstName"))
                .andExpect(jsonPath("$.lastName").value("lastName"))
                .andExpect(jsonPath("$.email").value("email@email.com"));

    }

}
