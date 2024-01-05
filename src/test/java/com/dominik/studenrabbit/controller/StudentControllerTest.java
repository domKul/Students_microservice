package com.dominik.studenrabbit.controller;

import com.dominik.studenrabbit.model.Student;
import com.dominik.studenrabbit.repository.StudentRepository;
import com.dominik.studenrabbit.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class StudentControllerTest {

    @MockBean
    private StudentService service;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private StudentController studentController;
    private Student student;
    private Student student1;
    private Student student2;
    private Student student3;
    private Student student4;
    private Student student5;

    @Test
    void mockCheck() {
        assertNotNull(mockMvc);
    }

    @BeforeEach
    void dataForTests() {
        student = new Student(20000L,"firstName","lastName","email@email.com");
        student.setStatus(Student.Status.INACTIVE);
        studentRepository.save(student);
        student1 = new Student(20001L,"firstName1","lastName1","email1@email.com");
        studentRepository.save(student1);
        student2 = new Student(20002L,"firstName2","lastName2","email2@email.com");
        studentRepository.save(student2);
        student3 = new Student(20003L,"firstName3","lastName3","email3@email.com");
        student3.setStatus(Student.Status.ACTIVE);
        studentRepository.save(student3);
        student4 = new Student(20004L,"firstName4","lastName4","email4@email.com");
        student4.setStatus(Student.Status.ACTIVE);
        studentRepository.save(student4);
        student5 = new Student(20005L,"firstName5","lastName5","email5@email.com");
        student5.setStatus(Student.Status.ACTIVE);
        studentRepository.save(student5);
    }

    @Test
    void shouldCreateSaveStudent() throws Exception {
        //Given
        student = new Student(20000L,"firstName","lastName","emailnew@email.com");
        when(service.addStudent(student)).thenReturn(student);
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(student.getId()))
                .andExpect(jsonPath("$.firstName").value("firstName"))
                .andExpect(jsonPath("$.lastName").value("lastName"))
                .andExpect(jsonPath("$.email").value("emailnew@email.com"));
    }

    @Test
    void shouldReturnAllStudentsWhenStatusIsNull() throws Exception {
        // Given
        List<Student> all = studentRepository.findAll();
        when(service.students(null)).thenReturn(all);
        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(6))
                .andExpect(jsonPath("$[0].firstName").value(all.get(0).getFirstName()))
                .andExpect(jsonPath("$[0].lastName").value(all.get(0).getLastName()))
                .andExpect(jsonPath("$[0].email").value(all.get(0).getEmail()));
    }

    @Test
    void shouldReturnAllStudentsWhenStatusIsACTIVE() throws Exception {
        // Given
        when(service.students(Student.Status.ACTIVE)).thenReturn(List.of(student3,student4,student5));
        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/students")
                        .param("status","ACTIVE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(3));
    }

    @Test
    void shouldReturnStudentWithGivenId() throws Exception {
        // Given
        Long id = student4.getId();
        when(service.getStudentById(id)).thenReturn(student4);
        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/students/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.firstName").value("firstName4"))
                .andExpect(jsonPath("$.lastName").value("lastName4"))
                .andExpect(jsonPath("$.email").value("email4@email.com"));
    }

    @Test
    void shouldModifyStudentById() throws Exception {
        // Given
        long id = student.getId();
        Student modifiedStudent = new Student(id, "firstNameModified", "lastNameModiefied", "emailModified@email.com");
        modifiedStudent.setStatus(Student.Status.ACTIVE);
        doReturn(modifiedStudent).when(service).updateStudent(id, modifiedStudent);
        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.put("/students/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modifiedStudent)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("firstNameModified"))
                .andExpect(jsonPath("$.lastName").value("lastNameModiefied"))
                .andExpect(jsonPath("$.email").value("emailModified@email.com"));
        // Verify
        verify(service, times(1)).updateStudent(id,modifiedStudent);
    }

    @Test
    void shouldPatchStudentById() throws Exception {
        // Given
        long id = 20006L;
        Student student6 = new Student(id, "firstName6", "lastName6", "emai6l@email.com");
        student6.setStatus(Student.Status.ACTIVE);
        studentRepository.save(student6);
        Student modifiedStudent = new Student();
        modifiedStudent.setFirstName("firstNameModified");
        modifiedStudent.setLastName(student6.getLastName());
        modifiedStudent.setEmail(student6.getEmail());
        doReturn(modifiedStudent).when(service).patchStudent(id, modifiedStudent);
        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.patch("/students/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modifiedStudent)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("firstNameModified"))
                .andExpect(jsonPath("$.lastName").value("lastName6"))
                .andExpect(jsonPath("$.email").value("emai6l@email.com"));
        // Verify
        verify(service, times(1)).patchStudent(id, modifiedStudent);
    }

    @Test
    void shouldGetStudentsByEmail() throws Exception {
        // Given
        List<String> emails = Arrays.asList("email1@email.com", "email2@email.com");
        List<Student> students = new ArrayList<>();
        students.add(student1);
        students.add(student2);
        when(service.getStudentByEmail(emails)).thenReturn(students);
        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/students/email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(emails)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
        // Verify
        verify(service, times(1)).getStudentByEmail(emails);
    }
}
