package com.zemoso.springboot.assignment.repository;

import com.zemoso.springboot.assignment.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findAllByDoctorId(Long doctorId);
    List<Patient> findAllByDoctorIsNull();
}
