package com.dominik.studenrabbit.service;

import com.dominik.studenrabbit.model.Student;
import com.dominik.studenrabbit.repository.StudentRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class StudentServiceTest {
    @Autowired
    private StudentService service;
    @Autowired
    private StudentRepository studentRepository;

    private Student student0;
    private Student student1;
    private Student student2;
    private Student student3;

    @BeforeEach
    void setUp() {
        student0 = new Student(20000L,"firstName","lastName","email@email.com");
        student1 = new Student(20001L,"firstName1","lastName1","email1@email.com");
        student2 = new Student(20002L,"firstName2","lastName2","email2@email.com");
        student3 = new Student(20003L,"firstName3","lastName3","email3@email.com");
    }

    @AfterEach
     void afterEach() {
        studentRepository.deleteAll();
    }

    @Test
    void shouldAddStudent() {
        //Given
        //When
        Student student = service.addStudent(student0);
        //Then
        assertEquals(1,studentRepository.count());
        assertNotNull(student.getId());
        assertEquals("firstName",studentRepository.findById(student.getId()).get().getFirstName());
    }



}
