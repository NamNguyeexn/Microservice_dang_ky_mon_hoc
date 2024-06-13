package com.soa.registration.controller;

import com.soa.registration.DTO.Classes;
import com.soa.registration.DTO.ResponseObject;
import com.soa.registration.model.Registration;
import com.soa.registration.repository.RegistrationRepo;
import com.soa.registration.service.RegistrationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@CrossOrigin
@RequestMapping({"/api"})
public class RegistrationController {
    @Autowired
    private RegistrationService registrationService;
    @Autowired
    private RegistrationRepo registrationRepo;
    private HttpSession httpSession;
    private final int idStudent = 1;
    private final RestTemplate restTemplate;
    private final String urlCheckIfStudentPassed = "http://localhost:8082/api/presubject/{id}";
    private final String urlGetClassesById = "http://localhost:8081/api/classes/{id}";

    @Autowired
    public RegistrationController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping({"/{id}"})
    public ResponseEntity<ResponseObject<Registration>> getRegistrationDetail(@PathVariable int id) {
        if (registrationService.getRegistrationById(id) != null) {
            return ResponseEntity.ok().body(
                    new ResponseObject<>("Ok!", registrationService.getRegistrationById(id))
            );
        } else return ResponseEntity.ok().body(
                new ResponseObject<>("Null!", null)
        );
    }

    @PostMapping({"/registration/save"})
    public ResponseEntity<ResponseObject<Registration>> saveRegistration(Classes classes) {
        int idStu = 1;
        Registration registration = new Registration();
        registration = registrationService.updateRegistration(idStu, classes);
        if (registration == null) {
            return ResponseEntity.ok().body(
                    new ResponseObject<>("Null!", null)
            );
        } else return ResponseEntity.ok().body(
                new ResponseObject<>("Success!", registration)
        );
    }
}