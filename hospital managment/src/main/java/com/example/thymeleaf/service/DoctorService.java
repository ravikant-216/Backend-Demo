package com.example.thymeleaf.service;

import com.example.thymeleaf.dao.DoctorRepository;
import com.example.thymeleaf.entity.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    @Autowired
    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public Optional<Doctor> getDoctorById(Long doctorId) {
        return doctorRepository.findById(doctorId);
    }

    public void createDoctor(Doctor doctor) {
        doctorRepository.save(doctor);
    }

    public void updateDoctor(Doctor doctor) {
        doctorRepository.save(doctor);
    }

    @Transactional
    public void deleteDoctor(Long doctorId) {
        doctorRepository.deleteById(doctorId);
    }

    public Doctor findByEmail(String username) {
        return doctorRepository.findByEmail(username);
    }
}
