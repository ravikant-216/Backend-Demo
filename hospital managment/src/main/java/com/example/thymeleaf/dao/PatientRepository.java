package com.example.thymeleaf.dao;

import com.example.thymeleaf.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findByDoctorDoctorId(Long doctorId);
    void deleteByDoctorDoctorId(Long patientId);

    Patient findByEmail(String email);
}
