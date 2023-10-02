package com.example.thymeleaf.dao;

import com.example.thymeleaf.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Doctor findByEmail(String email);
    // Additional custom queries can be defined here if needed
}
