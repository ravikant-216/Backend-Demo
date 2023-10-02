package com.zemoso.springboot.assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import  com.zemoso.springboot.assignment.entity.Patient;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    public List<Patient> findAllByDoctorId(Long id);
    public List<Patient> findAllByDoctorIdIsNull();
}
