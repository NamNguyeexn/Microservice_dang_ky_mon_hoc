package com.soa.subject.controller;

import com.soa.subject.DTO.ResponseObject;
import com.soa.subject.model.Subject;
import com.soa.subject.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/subject")
public class SubjectController {
    @Autowired
    private SubjectService subjectService;
    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject<Subject>> getSubjectDetail(@PathVariable int id) {
        return ResponseEntity.ok().body(
                new ResponseObject<>("SUBJECT PROCESS, Ok", subjectService.getSubjectById(id))
        );
    }
    @GetMapping("/getAll")
    public ResponseEntity<ResponseObject<List<Subject>>> getAllSubjectDetail(){
        if (subjectService.getAllSubjects() == null) {
            return ResponseEntity.ok().body(
                    new ResponseObject<>("SUBJECT PROCESS, Null", null)
            );
        } else {
            return ResponseEntity.ok().body(
                    new ResponseObject<>("SUBJECT PROCESS, Ok", subjectService.getAllSubjects())
            );
        }
    }

}
