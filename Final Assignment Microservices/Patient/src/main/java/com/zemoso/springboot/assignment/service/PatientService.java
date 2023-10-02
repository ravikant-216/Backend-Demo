package com.zemoso.springboot.assignment.service;

import com.zemoso.springboot.assignment.entity.Patient;
import com.zemoso.springboot.assignment.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Patient getPatientById(Long id) {
        return patientRepository.findById(id).orElse(null);
    }

    public Patient createPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public Patient updatePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }
    public List<Patient> findPatientByDoctorIdIsNull(){
        return patientRepository.findAllByDoctorIdIsNull();
    }

    public List<Patient> findByDoctorId(Long id){
        return patientRepository.findAllByDoctorId(id);
    }

    public void saveAllNullPatient(Long id) {
       List<Patient>patients= patientRepository.findAllByDoctorId(id);
       for(Patient temp:patients){
           temp.setDoctorId(null);
           updatePatient(temp);
       }
    }
}
