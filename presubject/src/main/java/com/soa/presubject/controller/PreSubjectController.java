package com.soa.presubject.controller;

import com.soa.presubject.DTO.ResponseObject;
import com.soa.presubject.service.PreSubjectService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class PreSubjectController {
    @Autowired
    private PreSubjectService preSubjectService;
    private HttpSession session;
    @GetMapping("/presubject/{idSubject}")
    public ResponseEntity<ResponseObject<Boolean>> checkIfStudentPassed(@PathVariable int idSubject) {
        int idStudent = 1;
        if (preSubjectService.checkIfStudentPassed(idStudent, idSubject)) {
            return ResponseEntity.ok().body(
                    new ResponseObject<>("PRESUBJECT PROCESS, Student passed this subject", true)
            );
        } else {
            return ResponseEntity.ok().body(
                    new ResponseObject<>("PRESUBJECT PROCESS, Student didn't pass this subject", false)
            );
        }
    }
}
