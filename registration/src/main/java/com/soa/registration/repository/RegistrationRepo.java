package com.soa.registration.repository;

import com.soa.registration.model.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationRepo extends JpaRepository<Registration, Integer> {
}
