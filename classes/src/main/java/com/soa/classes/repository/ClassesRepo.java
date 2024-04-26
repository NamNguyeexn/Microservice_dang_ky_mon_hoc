package com.soa.classes.repository;

import com.soa.classes.model.Classes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassesRepo extends JpaRepository<Classes, Integer> {
}
