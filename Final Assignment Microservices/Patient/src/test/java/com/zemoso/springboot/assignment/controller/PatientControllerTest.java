package com.zemoso.springboot.assignment.controller;

import com.zemoso.springboot.assignment.entity.Patient;
import com.zemoso.springboot.assignment.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class PatientControllerTest {

    @Mock
    private PatientService patientService;

    @InjectMocks
    private PatientController patientController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllPatients() {
        // Arrange
        List<Patient> patients = new ArrayList<>();
        patients.add(new Patient(1L, "ravi", "kant", "ravi.kant@gmail.com", null));
        patients.add(new Patient(2L, "shivam", "kumar", "shivam.kumar@gmail.com", null));
        when(patientService.getAllPatients()).thenReturn(patients);

        // Act
        ResponseEntity<List<Patient>> response = patientController.getAllPatients();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void getPatientById_existingId_shouldReturnPatient() {
        // Arrange
        Long patientId = 1L;
        Patient patient = new Patient(patientId, "ravi", "kant", "ravi.kant@gmail.com", null);
        when(patientService.getPatientById(patientId)).thenReturn(patient);

        // Act
        ResponseEntity<Patient> response = patientController.getPatientById(patientId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(patientId, response.getBody().getId());
    }

    @Test
    void getPatientById_nonExistingId_shouldReturnNotFound() {
        // Arrange
        Long nonExistingId = 100L;
        when(patientService.getPatientById(nonExistingId)).thenReturn(null);

        // Act
        ResponseEntity<Patient> response = patientController.getPatientById(nonExistingId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void createPatient() {
        // Arrange
        Patient patientToCreate = new Patient(null, "New", "Patient", "new.patient@gmail.com", null);
        Patient createdPatient = new Patient(1L, "New", "Patient", "new.patient@gmail.com", null);
        when(patientService.createPatient(patientToCreate)).thenReturn(createdPatient);

        // Act
        ResponseEntity<Patient> response = patientController.createPatient(patientToCreate);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        assertEquals("New", response.getBody().getFirstName());
    }

    @Test
    void updatePatient() {
        // Arrange
        Long patientId = 1L;
        Patient existingPatient = new Patient(patientId, "ravi", "kant", "ravi.kant@gmail.com", null);
        Patient updatedPatient = new Patient(patientId, "Updated", "Patient", "updated.patient@gmail.com", null);
        when(patientService.updatePatient(existingPatient)).thenReturn(updatedPatient);

        // Act
        ResponseEntity<Patient> response = patientController.updatePatient(patientId, existingPatient);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(patientId, response.getBody().getId());
        assertEquals("Updated", response.getBody().getFirstName());
    }

    @Test
    void setNullToPatient() {
        // Arrange
        Long doctorId = 54637L;

        // Act
        ResponseEntity<Void> response = patientController.setNullToPatient(doctorId);

        // Assert
        verify(patientService, times(1)).saveAllNullPatient(doctorId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void deletePatient_existingId_shouldReturnNoContent() {
        // Arrange
        Long patientId = 1L;

        // Act
        ResponseEntity<String> response = patientController.deletePatient(patientId);

        // Assert
        verify(patientService, times(1)).deletePatient(patientId);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Patient with Id " + patientId + " deleted successfully", response.getBody());
    }

    @Test
    void deletePatient_nonExistingId_shouldReturnNotFound() {
        // Arrange
        Long nonExistingId = 100L;
        doThrow(new RuntimeException("Patient not found")).when(patientService).deletePatient(nonExistingId);

        // Act
        ResponseEntity<String> response = patientController.deletePatient(nonExistingId);

        // Assert
        verify(patientService, times(1)).deletePatient(nonExistingId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Patient not found", response.getBody());
    }

    @Test
    void patientByDoctorId() {
        // Arrange
        Long doctorId = 1L;
        List<Patient> patients = new ArrayList<>();
        patients.add(new Patient(1L, "ravi", "kant", "ravi.kant@gmail.com", doctorId));
        when(patientService.findByDoctorId(doctorId)).thenReturn(patients);

        // Act
        ResponseEntity<List<Patient>> response = patientController.patientByDoctorId(doctorId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(doctorId, response.getBody().get(0).getDoctorId());
    }

    @Test
    void patientByNullValue() {
        // Arrange
        List<Patient> patients = new ArrayList<>();
        patients.add(new Patient(1L, "ravi", "kant", "ravi.kant@gmail.com", null));
        patients.add(new Patient(2L, "shivam", "kumar", "shivam.kumar@gmail.com", null));
        when(patientService.findPatientByDoctorIdIsNull()).thenReturn(patients);

        // Act
        ResponseEntity<List<Patient>> response = patientController.patientByNullValue();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertNull(response.getBody().get(0).getDoctorId());
    }
}
