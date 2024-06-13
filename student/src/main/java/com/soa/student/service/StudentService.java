package com.soa.student.service;

import com.soa.student.model.Student;
import com.soa.student.repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class StudentService {
    @Autowired
    private StudentRepo studentRepo;

    public List<Student> getAllStudents() {
        return studentRepo.findAll();
    }

    public Student getStudentByIdString(String idStudentString) {
        for (Student s : studentRepo.findAll()) {
            if (s.getIdStudentString().equals(idStudentString)) {
                return s;
            }
        }
        return null;
    }

    public Student getStudentById(int idStudent) {
        for (Student s : studentRepo.findAll()) {
            if (s.getId() == idStudent) {
                return s;
            }
        }
        return null;
    }

    public Student createStudent(Student student) {
        return studentRepo.save(student);
    }

    public void deleteStudentById(int idStudent) {
        studentRepo.deleteById(idStudent);
    }
}
