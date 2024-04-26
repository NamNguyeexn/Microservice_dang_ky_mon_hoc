package com.soa.registration.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soa.registration.DTO.Classes;
import com.soa.registration.DTO.Presubject;
import com.soa.registration.DTO.ResponseObject;
import com.soa.registration.model.Registration;
import com.soa.registration.service.RegistrationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.DataInput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class RegistrationController {
    @Autowired
    private RegistrationService registrationService;
    private HttpSession httpSession;
    private final int idStudent = 1;
    private final RestTemplate restTemplate;
    private final String urlCheckIfStudentPassed = "http://localhost:8082/api/presubject/{id}";
    private final String urlGetClassesById = "http://localhost:8081/api/classes/{id}";
    @Autowired
    public RegistrationController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject<Registration>> getRegistrationDetail(@PathVariable int id) {
        if(registrationService.getRegistrationById(id) != null) {
            return ResponseEntity.ok().body(
                    new ResponseObject<>("REGISTRATION PROCESS, Ok!", registrationService.getRegistrationById(id))
            );
        } else {
            return ResponseEntity.ok().body(
                    new ResponseObject<>("REGISTRATION PROCESS, null!", null)
            );
        }
    }
    @PostMapping("/registration/save")
    public ResponseEntity<ResponseObject<List<Registration>>> saveRegistration(@RequestParam("idClasses") String[] idClassesStr){
        List<Integer> idClasses = new ArrayList<>();
//        //test string
//        String[] classesString = idClassesStr.split(",");

        for (String s : idClassesStr) {
//            System.out.println(s);
            idClasses.add(Integer.parseInt(s));
        }
//        System.out.println(idClasses);
//        int count = 0;
//        //////

        List<Registration> registrations = new ArrayList<>();
        for (int idClass : idClasses) {
            System.out.println(idClass);
            String responseObjectClasses = restTemplate.getForObject(
                    urlGetClassesById,
                    String.class,
                    idClass
            );

            //test String
//            count++;
//            System.out.println("TEST " + count);
//            System.out.println(responseObjectClasses);

            //////////

            if (responseObjectClasses == null) {
                return ResponseEntity.ok().body(
                        new ResponseObject<>("REGISTRATION PROCESS, Classes not found!", null)
                );
            } else {
                ObjectMapper mapper = new ObjectMapper();
                try {
                    ResponseObject responseClasses = mapper.readValue(responseObjectClasses, ResponseObject.class);
                    Classes classes = StringToClasses(responseClasses.getData().toString());
                    ResponseObject<Boolean> responseObjectPreSubject = restTemplate.getForObject(
                            urlCheckIfStudentPassed,
                            ResponseObject.class,
                            classes.getIdSubject()
                    );
                    System.out.println(responseObjectPreSubject);
                    if (responseObjectPreSubject.toString().compareTo("false") == 0) {
                        return ResponseEntity.ok().body(
                                new ResponseObject<>("REGISTRATION PROCESS, Class not found or student didn't pass, end processing registration!", null)
                        );
                    } else {
                            registrations.add(registrationService.updateRegistration(idStudent,classes));
                    }
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        }
        if (registrations.isEmpty()) {
            return ResponseEntity.ok().body(
                    new ResponseObject<>("REGISTRATION PROCESS, Fail!", null)
            );
        } else {
            return ResponseEntity.ok().body(
                    new ResponseObject<>("REGISTRATION PROCESS, Success!", registrations)
            );
        }

    }
    private Classes StringToClasses(String s) {
        Classes classes = new Classes();
        s = s.replaceAll("^\\{|}$", "").
                replaceAll("=", " ").
                replaceAll(",", "");
        String[] key = s.split(" ");
        classes.setId(Integer.parseInt(key[1].trim()));
        classes.setIdClassString(key[3].trim());
        classes.setIdSubject(Integer.parseInt(key[5].trim()));
        return classes;
    }
}
