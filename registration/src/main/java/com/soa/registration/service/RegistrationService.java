package com.soa.registration.service;

import com.soa.registration.DTO.Classes;
import com.soa.registration.model.Registration;
import com.soa.registration.repository.RegistrationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RegistrationService {
    @Autowired
    private RegistrationRepo registrationRepo;
    public Registration createRegistration (Registration registration) {
        return registrationRepo.save(registration);
    }
    public List<Registration> getAllRegistration(){
        return registrationRepo.findAll();
    }
//    public List<Registration> updateRegistration (int idStu,int[] idClasses) {
//
//        List<Registration> list = new ArrayList<>();
//        for (int i = 0; i < idClasses.length; i++){
//            int idClass = idClasses[i];
//            int idSubject = classesService.getClassesById(idClass).getSubject().getId();
//            Registration registration = new Registration();
//            registration.setId((int)count() + 1);
//            registration.setIdStudent(idStu);
//            registration.setClasses(classesService.getClassesById(idClass));
//            list.add(registration);
//            registrationRepo.save(registration);
//        }
//        if(list.size() == 0){
//            return null;
//        }
//        else return list;
//    }
    public Registration updateRegistration (int idStu, Classes classes) {
            Registration registration = new Registration();
            registration.setId((int)count() + 1);
            registration.setIdStudent(idStu);
            registration.setIdClasses(classes.getId());
            return registrationRepo.save(registration);
    }
    public Registration getRegistrationById(int id) {
        for(Registration registration : registrationRepo.findAll()) {
            if (registration.getId() == id) {
                return registration;
            }
        }
        return null;
    }
    public long count(){
        return registrationRepo.count();
    }
}
