package com.soa.classes.service;

import com.soa.classes.model.Classes;
import com.soa.classes.repository.ClassesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassesService {
    @Autowired
    private ClassesRepo classesRepo;
    public List<Classes> getAllClasses() {
        return classesRepo.findAll();
    }
    public Classes getClassesById(int id) {
        for (Classes classes : classesRepo.findAll()) {
            if (classes.getId() == id) {
                return classes;
            }
        }
        return null;
    }
    public Classes createClasses(Classes classes) {
        return classesRepo.save(classes);
    }
    public Classes updateClasses(Classes classes) {
        return classesRepo.save(classes);
    }
}
