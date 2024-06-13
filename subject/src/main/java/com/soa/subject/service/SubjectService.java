package com.soa.subject.service;

import com.soa.subject.model.Subject;
import com.soa.subject.repository.SubjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SubjectService {
    @Autowired
    private SubjectRepo subjectRepo;

    public Subject getSubjectById(int id) {
        return subjectRepo.findById(id).orElse(null);
    }

    public List<Subject> getAllSubjects() {
        return subjectRepo.findAll();
    }

    public Subject createSubject(Subject subject) {
        return subjectRepo.save(subject);
    }

}

