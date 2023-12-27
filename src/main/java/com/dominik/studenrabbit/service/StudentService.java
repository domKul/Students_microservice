package com.dominik.studenrabbit.service;

import com.dominik.studenrabbit.exception.StudentError;
import com.dominik.studenrabbit.exception.StudentException;
import com.dominik.studenrabbit.model.Student;
import com.dominik.studenrabbit.repository.StudentRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Transactional
    public Student addStudent(Student student) {
        validEmail(student.getEmail());
        return studentRepository.save(student);
    }

    public List<Student> students(Student.Status status) {
        if (status != null){
            return studentRepository.findAllByStatus(status);
        }

        return studentRepository.findAll();
    }

    public Student getStudentById(long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentException(StudentError.STUDENT_NOT_FOUND));
        if(!Student.Status.ACTIVE.equals(student.getStatus())){
            throw new StudentException(StudentError.STUDENT_IS_NOT_ACTIVE);
        }
        return student;
    }

    public List<Student> getStudentByEmail(List<String> email) {
        return studentRepository.findAllByEmailIn(email);
    }
    @Transactional
    public Student updateStudent(long id, Student student) {
        return studentRepository.findById(id)
                .map(studentFromDB -> {
                    if (!studentFromDB.getEmail().equals(student.getEmail()) &&
                            studentRepository.existsByEmail(student.getEmail())) {
                        throw new StudentException(StudentError.STUDENT_EMAIL_ALREADY_EXISTS);
                    }
                    studentFromDB.setFirstName(student.getFirstName());
                    studentFromDB.setLastName(student.getLastName());
                    studentFromDB.setEmail(student.getEmail());
                    return studentRepository.save(studentFromDB);
                })
                .orElseThrow(() -> new StudentException(StudentError.STUDENT_NOT_FOUND));
    }

    @Transactional
    public Student patchStudent(long id, Student student) {
        return studentRepository.findById(id)
                .map(s -> {
                    if (!StringUtils.isEmpty(student.getFirstName())) {
                        s.setFirstName(student.getFirstName());
                    }
                    if (!StringUtils.isEmpty(student.getLastName())) {
                        s.setLastName(s.getLastName());
                    }
                    if (!StringUtils.isEmpty(student.getEmail())) {
                        validEmail(student.getEmail());
                        s.setEmail(student.getEmail());
                    }
                    return studentRepository.save(s);
                }).orElseThrow(() -> new StudentException(StudentError.STUDENT_NOT_FOUND));
    }

    private void validEmail(String email){
        if(studentRepository.existsByEmail(email)){
            throw new StudentException(StudentError.STUDENT_EMAIL_ALREADY_EXISTS);
        }
    }
}


