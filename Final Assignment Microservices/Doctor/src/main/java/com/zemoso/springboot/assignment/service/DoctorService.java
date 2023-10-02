package com.zemoso.springboot.assignment.service;


import com.zemoso.springboot.assignment.DTO.PatientDTO;
import com.zemoso.springboot.assignment.entity.Doctor;
import com.zemoso.springboot.assignment.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private static final String PATIENT_SERVICE_URL = "http://localhost:8081/patients/";
    private final RestTemplate restTemplate;

    @Autowired
    public DoctorService(DoctorRepository doctorRepository, RestTemplate restTemplate) {
        this.doctorRepository = doctorRepository;
        this.restTemplate = restTemplate;
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public Doctor getDoctorById(Long id) {
        return doctorRepository.findById(id).orElse(null);
    }

    public Doctor createDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public Doctor updateDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public void deleteDoctor(Long id) {

        restTemplate.exchange(
                PATIENT_SERVICE_URL+ "not_assigned/" + id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<PatientDTO>>() {}
        );

        doctorRepository.deleteById(id);
    }
}
