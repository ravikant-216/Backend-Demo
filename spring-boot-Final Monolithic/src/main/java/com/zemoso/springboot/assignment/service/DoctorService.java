package com.zemoso.springboot.assignment.service;

import com.zemoso.springboot.assignment.dto.DoctorDTO;
import com.zemoso.springboot.assignment.entity.Doctor;
import com.zemoso.springboot.assignment.repository.DoctorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public List<DoctorDTO> getAllDoctors() {
        List<Doctor> doctors = doctorRepository.findAll();
        return doctors.stream()
                .map(this::convertToDto)
                .toList();
    }

    public DoctorDTO getDoctorById(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Doctor not found with id " + id));
        return convertToDto(doctor);
    }

    public DoctorDTO createDoctor(DoctorDTO doctorDTO) {
        Doctor doctor = convertToEntity(doctorDTO);
        Doctor savedDoctor = doctorRepository.save(doctor);
        return convertToDto(savedDoctor);
    }

    public DoctorDTO updateDoctor(DoctorDTO doctorDTO) {
        Doctor existingDoctor = doctorRepository.findById(doctorDTO.getId())
                .orElseThrow(() -> new NoSuchElementException("Doctor not found with id " + doctorDTO.getId()));

        existingDoctor.setDoctorName(doctorDTO.getDoctorName());
        existingDoctor.setDoctorAddress(doctorDTO.getDoctorAddress());
        existingDoctor.setDoctorCode(doctorDTO.getDoctorCode());
        Doctor updatedDoctor = doctorRepository.save(existingDoctor);
        return convertToDto(updatedDoctor);
    }

    public void deleteDoctor(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Doctor not found with id " + id));

        doctor.getPatients().forEach(patient -> patient.setDoctor(null));
        doctorRepository.delete(doctor);
    }

    private DoctorDTO convertToDto(Doctor doctor) {
        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setId(doctor.getId());
        doctorDTO.setDoctorName(doctor.getDoctorName());
        doctorDTO.setDoctorAddress(doctor.getDoctorAddress());
        doctorDTO.setDoctorCode(doctor.getDoctorCode());
        return doctorDTO;
    }
    private Doctor convertToEntity(DoctorDTO doctorDTO) {
        Doctor doctor = new Doctor();
        doctor.setId(doctorDTO.getId());
        doctor.setDoctorName(doctorDTO.getDoctorName());
        doctor.setDoctorAddress(doctorDTO.getDoctorAddress());
        doctor.setDoctorCode(doctorDTO.getDoctorCode());
        return doctor;
    }
}