package com.zemoso.springboot.assignment.controller;

import com.zemoso.springboot.assignment.dto.DoctorDTO;
import com.zemoso.springboot.assignment.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/doctors")
public class DoctorController {


    private final DoctorService doctorService;
    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping("/")
    public ResponseEntity<List<DoctorDTO>> getAllDoctor() {
        List<DoctorDTO> doctorDTOS = doctorService.getAllDoctors();
        return ResponseEntity.ok(doctorDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDTO> getDoctorById(@PathVariable Long id) {
        DoctorDTO doctorDTO = doctorService.getDoctorById(id);
        if (doctorDTO != null) {
            return ResponseEntity.ok(doctorDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/")
    public ResponseEntity<DoctorDTO> createDoctor(@RequestBody DoctorDTO doctorDTO) {
        DoctorDTO createDoctorDTo = doctorService.createDoctor(doctorDTO);
        return new ResponseEntity<>(createDoctorDTo, HttpStatus.CREATED);
    }

    @PutMapping("/")
    public ResponseEntity<DoctorDTO> updateDoctor(@RequestBody DoctorDTO doctorDTO) {
        DoctorDTO updatedDoctorDTO = doctorService.updateDoctor(doctorDTO);
        return ResponseEntity.ok(updatedDoctorDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }
}
