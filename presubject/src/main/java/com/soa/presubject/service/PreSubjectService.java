package com.soa.presubject.service;

import com.soa.presubject.model.PreSubject;
import com.soa.presubject.repository.PreSubjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PreSubjectService {
    @Autowired
    private PreSubjectRepo preSubjectRepo;
    public Boolean checkIfStudentPassed(int idStudent, int idSubject) {
        for (PreSubject preSubject : preSubjectRepo.findAll()) {
            if(preSubject.getIdStudent() == idStudent) {
                if(preSubject.getIdSubject() == idSubject) {
                    return true;
                }
            }
        }
        return false;
    }

}
