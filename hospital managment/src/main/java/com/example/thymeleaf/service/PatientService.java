package com.example.thymeleaf.service;

import com.example.thymeleaf.dao.PatientRepository;
import com.example.thymeleaf.entity.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Optional<Patient> getPatientById(Long patientId) {
        return patientRepository.findById(patientId);
    }

    public void createPatient(Patient patient) {
        patientRepository.save(patient);
    }

    public Patient updatePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    @Transactional
    public void deletePatient(Long patientId) {
        patientRepository.deleteById(patientId);
    }

    @Transactional
    public  void deleteByDoctorId(Long id){
       patientRepository.deleteByDoctorDoctorId(id);
    }

    public List<Patient> getPatientsByDoctorId(Long doctorId) {
        return patientRepository.findByDoctorDoctorId(doctorId);
    }

    public Patient findByEmail(String username) {
        return patientRepository.findByEmail(username);
    }
}
