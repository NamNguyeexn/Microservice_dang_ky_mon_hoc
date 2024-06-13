package com.soa.classes.controller;

import com.soa.classes.DTO.ResponseObject;
import com.soa.classes.model.Classes;
import com.soa.classes.service.ClassesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/classes")
public class ClassesController {
    @Autowired
    private ClassesService classesService;
    @GetMapping
    public ResponseEntity<ResponseObject<List<Classes>>> getAllClasses () {
        if(!classesService.getAllClasses().isEmpty()) {
            return ResponseEntity.ok().body(
                    new ResponseObject<>("CLASSES PROCESS, Get Full Classes", classesService.getAllClasses()
                    ));
        }
        else {
            return ResponseEntity.ok().body(
                    new ResponseObject<>("CLASSES PROCESS, Empty Database Classes", null)
            );
        }
    }
    @PostMapping("/{id}")
    public ResponseEntity<ResponseObject<Classes>> updateClasses(@PathVariable int id, @RequestBody Classes classes) {
        if(classesService.getClassesById(id) == null) {
            return ResponseEntity.ok().body(
                    new ResponseObject<>("Cannot get classes!", null)
            );
        } else {
            return ResponseEntity.ok().body(
                    new ResponseObject<>("Get classes success", classesService.updateClasses(classes))
            );
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject<Classes>> getClassById(@PathVariable int id) {
        if(classesService.getClassesById(id) == null) {
            return ResponseEntity.ok().body(
                    new ResponseObject<>("null!", null)
            );
        } else {
            return ResponseEntity.ok().body(
                    new ResponseObject<>("ok!", classesService.getClassesById(id))
            );
        }
    }



}
