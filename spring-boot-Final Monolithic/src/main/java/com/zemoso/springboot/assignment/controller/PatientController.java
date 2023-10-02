package com.zemoso.springboot.assignment.controller;

import com.zemoso.springboot.assignment.dto.PatientDTO;
import com.zemoso.springboot.assignment.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/patient")
public class PatientController {


    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/")
    public ResponseEntity<List<PatientDTO>> getAllPatient() {
        List<PatientDTO> patientDTOS = patientService.getAllPatient();
        return ResponseEntity.ok(patientDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> getPatientById(@PathVariable Long id) {
        PatientDTO patientDTO = patientService.getPatientById(id);
        if(patientDTO!=null)
        return ResponseEntity.ok(patientDTO);
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/")
    public ResponseEntity<PatientDTO> createPatient(@RequestBody PatientDTO patientDTO) {
        PatientDTO createPatientDTO = patientService.createPatient(patientDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createPatientDTO);
    }
    @GetMapping("/byDoctorId/{id}")
    public  ResponseEntity<List<PatientDTO>> getPatientByDoctorId(@PathVariable Long id){
        List<PatientDTO> patientDTOS = patientService.getAllPatientByDoctorId(id);
        return ResponseEntity.ok(patientDTOS);
    }

    @PutMapping("/")
    public ResponseEntity<PatientDTO> updatePatient(@RequestBody PatientDTO patientDTO) {
        PatientDTO updatedEmployee = patientService.updatePatient(patientDTO);
        return ResponseEntity.ok(updatedEmployee);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/without-doctor")
    public ResponseEntity<List<PatientDTO>> getPatientsWithoutDoctor() {
        List<PatientDTO> patientsWithoutDoctor = patientService.getPatientsWithNullDoctor();
        return ResponseEntity.ok(patientsWithoutDoctor);
    }
}
