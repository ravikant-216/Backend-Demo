package com.zemoso.springboot.assignment.controller;

import com.zemoso.springboot.assignment.entity.Patient;
import com.zemoso.springboot.assignment.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Patient>> getAllPatients() {
        List<Patient> patients = patientService.getAllPatients();
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        Patient patient = patientService.getPatientById(id);
        if (patient != null) {
            return new ResponseEntity<>(patient, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/")
    public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) {
        Patient createdPatient = patientService.createPatient(patient);
        return new ResponseEntity<>(createdPatient, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @RequestBody Patient patient) {
        patient.setId(id);
        Patient updatedPatient = patientService.updatePatient(patient);
        return new ResponseEntity<>(updatedPatient, HttpStatus.OK);
    }

    @RequestMapping("/not_assigned/{id}")
    public ResponseEntity<Void> setNullToPatient(@PathVariable Long id) {
        patientService.saveAllNullPatient(id);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePatient(@PathVariable Long id) {
        try {
            patientService.deletePatient(id);
            return new ResponseEntity<>("Patient with Id " + id + " deleted successfully", HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Patient not found", HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/doctor/{id}")
    public  ResponseEntity<List<Patient>> patientByDoctorId(@PathVariable Long id){
        List<Patient> patients = patientService.findByDoctorId(id);
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }
    @GetMapping("/with_no_doctor")
    public  ResponseEntity<List<Patient>> patientByNullValue(){
        List<Patient> patients = patientService.findPatientByDoctorIdIsNull();
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }
}
