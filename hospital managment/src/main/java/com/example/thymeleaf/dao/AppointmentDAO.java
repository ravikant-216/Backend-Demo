package com.example.thymeleaf.dao;

import com.example.thymeleaf.entity.Appointment;
import com.example.thymeleaf.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentDAO extends JpaRepository<Appointment, Long> {
    List<Appointment> findByDoctorDoctorId(Long doctorId);
    Appointment getAppointmentByPatientPatientId(Long patientId);
    void deleteByPatientPatientId(Long patientId);
    void deleteByDoctorDoctorId(Long patientId);

}
