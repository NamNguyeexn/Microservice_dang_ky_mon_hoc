package com.soa.subject.repository;

import com.soa.subject.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepo extends JpaRepository<Subject, Integer> {
}
