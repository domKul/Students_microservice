package com.dominik.studenrabbit.service;

import com.dominik.studenrabbit.exception.StudentError;
import com.dominik.studenrabbit.exception.StudentException;
import com.dominik.studenrabbit.model.Student;
import com.dominik.studenrabbit.repository.StudentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
public class StudentServiceTest {
    @InjectMocks
    private StudentService service;
    @Mock
    private StudentRepository studentRepository;

    private Student student0;
    private Student student1;
    private Student student2;
    private Student student3;

    @BeforeEach
    void setUp() {
        student0 = new Student(20000L,"firstName","lastName","email@email.com");
        student1 = new Student(20001L,"firstName1","lastName1","email1@email.com");
        student1.setStatus(Student.Status.ACTIVE);
        studentRepository.save(student1);
        student2 = new Student(20002L,"firstName2","lastName2","email2@email.com");
        student2.setStatus(Student.Status.ACTIVE);
        student3 = new Student(20003L,"firstName3","lastName3","email3@email.com");
    }

    @AfterEach
     void afterEach() {
        studentRepository.deleteAll();
    }

    @Test
    void shouldAddStudent() {
        //Given
        when(service.addStudent(student0)).thenReturn(student0);
        //When
        Student addingStudent = service.addStudent(student0);
        //Then
        assertEquals(student0.getFirstName(),addingStudent.getFirstName());

    }

    @Test
    void shouldFindAllStudentsWithActiveStatus(){
        //Given
        List<Student> expectedList = List.of(student1, student2);
        when(service.students(Student.Status.ACTIVE)).thenReturn(expectedList);
        //When
        List<Student> findActiveStudents = service.students(Student.Status.ACTIVE);

        //Then
        assertEquals(expectedList.size(),findActiveStudents.size());
    }
    @Test
    void shouldFindAllStudentsWithNoStatus(){
        //Given
        List<Student> expectedList = List.of(student1, student2,student3);
        when(service.students(null)).thenReturn(expectedList);
        //When
        List<Student> findActiveStudents = service.students(null);
        //Then
        assertEquals(expectedList.size(),findActiveStudents.size());
    }

    @Test
    void shouldFindStudentById(){
        //Given
        long studenId = student1.getId();
        when(studentRepository.findById(studenId)).thenReturn(Optional.ofNullable(student1));
        //When
        Student getById = service.getStudentById(studenId);
        //Then
        assertEquals(student1.getId(),getById.getId());
        assertEquals(student1.getFirstName(),getById.getFirstName());
        assertEquals(student1.getLastName(),getById.getLastName());
        assertEquals(student1.getEmail(),getById.getEmail());

    }

    @Test
    void shouldThrowExceptionByPassingWrongId(){
        //Given
        long wrongId = 12;
        //When
        StudentException studentException = assertThrows(StudentException.class,
                () -> service.getStudentById(wrongId));

        assertEquals(StudentError.STUDENT_NOT_FOUND.getMessage(),studentException.getStudentError().getMessage());

    }

    @Test
    void shouldThrowExceptionIfStudentStatusIsNotActive() {
        //Given
        when(studentRepository.findById(12L)).thenReturn(Optional.ofNullable(student0));

        //When
        StudentException studentException = assertThrows(StudentException.class, () -> service.getStudentById(12L));

        //Then
        assertEquals(StudentError.STUDENT_IS_NOT_ACTIVE.getMessage(),studentException.getStudentError().getMessage());

    }


//    @Test
//    void shouldUpdateStudent(){
//        //Given
//        long id = student2.getId();
//        Student studentUpdate = new Student(id,"firstNameUpdate","lastNameUpdate","emailUpdate@email.com");
//        when(studentRepository.findById(id)).thenReturn(Optional.ofNullable(student2));
//        when(studentRepository.existsByEmail(studentUpdate.getEmail())).thenReturn(false);
//
//
//        //When
//        Student update = service.updateStudent(id, studentUpdate);
//
//        //Then
//        assertEquals(studentUpdate.getId(),update.getId());
//        assertEquals(studentUpdate.getFirstName(),update.getFirstName());
//        assertEquals(studentUpdate.getLastName(),update.getLastName());
//
//    }

}
