package com.soa.student.controller;

import com.soa.student.DTO.ResponseObject;
import com.soa.student.model.Student;
import com.soa.student.service.StudentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class StudentController {
    @Autowired
    private StudentService studentService;
    private final int id = 1;
    @GetMapping("/student/{id}")
    public ResponseEntity<ResponseObject<Student>> getStudentDetail(@PathVariable int id) {
        return ResponseEntity.ok().body(
                new ResponseObject<>("STUDENT PROCESS, Ok!", studentService.getStudentById(id))
        );
    }


}
