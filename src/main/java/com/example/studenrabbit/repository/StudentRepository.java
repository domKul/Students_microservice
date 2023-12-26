package com.example.studenrabbit.repository;

import com.example.studenrabbit.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student,Long> {

    boolean existsByEmail(String email);
    List<Student>findAllByStatus(Student.Status status);
    List<Student> findAllByEmailIn(List<String> emails);
}
