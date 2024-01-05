package com.dominik.studenrabbit.controller;

import com.dominik.studenrabbit.model.Student;
import com.dominik.studenrabbit.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @PostMapping
    public Student addStudent(@RequestBody @Valid Student student) {
        return service.addStudent(student);
    }

    @GetMapping
    public List<Student> getAllStudents(@RequestParam(required = false) Student.Status status) {
        return service.students(status);
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable long id) {
        return service.getStudentById(id);
    }

    @PutMapping("/{id}")
    public Student modifyStudent(@PathVariable long id, @Valid @RequestBody Student student) {
        return service.updateStudent(id, student);
    }

    @PatchMapping("/{id}")
    public Student patchStudentById(@PathVariable long id, @RequestBody Student student) {
        return service.patchStudent(id, student);
    }

    @PostMapping("/email")
    public List<Student> getByEmail(@RequestBody List<String> email) {
        return service.getStudentByEmail(email);
    }
}
