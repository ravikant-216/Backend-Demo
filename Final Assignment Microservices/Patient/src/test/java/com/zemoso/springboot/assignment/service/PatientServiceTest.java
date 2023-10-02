package com.zemoso.springboot.assignment.service;

import com.zemoso.springboot.assignment.entity.Patient;
import com.zemoso.springboot.assignment.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientService patientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllPatients() {
        // Arrange
        List<Patient> patients = new ArrayList<>();
        patients.add(new Patient(1L, "ravi", "kant", "ravi.kant@gmail.com", null));
        patients.add(new Patient(2L, "shivam", "kumar", "shivam.kumar@gmail.com", 1L));
        when(patientRepository.findAll()).thenReturn(patients);

        // Act
        List<Patient> result = patientService.getAllPatients();

        // Assert
        assertEquals(2, result.size());
    }

    @Test
    void getPatientById_existingId_shouldReturnPatient() {
        // Arrange
        Long patientId = 1L;
        Patient patient = new Patient(patientId, "ravi", "kant", "ravi.kant@gmail.com", null);
        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patient));

        // Act
        Patient result = patientService.getPatientById(patientId);

        // Assert
        assertEquals(patient, result);
    }

    @Test
    void getPatientById_nonExistingId_shouldReturnNull() {
        // Arrange
        Long nonExistingId = 100L;
        when(patientRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        // Act
        Patient result = patientService.getPatientById(nonExistingId);

        // Assert
        assertNull(result);
    }

    @Test
    void createPatient() {
        // Arrange
        Patient patient = new Patient(null, "New", "Patient", "new.patient@gmail.com", null);
        when(patientRepository.save(patient)).thenReturn(new Patient(1L, "New", "Patient", "new.patient@gmail.com", null));

        // Act
        Patient result = patientService.createPatient(patient);

        // Assert
        assertEquals("New", result.getFirstName());
        assertNull(result.getDoctorId());
    }

    @Test
    void updatePatient() {
        // Arrange
        Patient patient = new Patient(1L, "ravi", "kant", "ravi.kant@gmail.com", null);
        when(patientRepository.save(patient)).thenReturn(patient);

        // Act
        Patient result = patientService.updatePatient(patient);

        // Assert
        assertEquals("ravi", result.getFirstName());
    }

    @Test
    void deletePatient() {
        // Arrange
        Long patientId = 1L;

        // Act
        patientService.deletePatient(patientId);

        // Assert
        verify(patientRepository, times(1)).deleteById(patientId);
    }

    @Test
    void findPatientByDoctorIdIsNull() {
        // Arrange
        List<Patient> patients = new ArrayList<>();
        patients.add(new Patient(1L, "ravi", "kant", "ravi.kant@gmail.com", null));
        patients.add(new Patient(2L, "shivam", "kumar", "shivam.kumar@gmail.com", null));
        when(patientRepository.findAllByDoctorIdIsNull()).thenReturn(patients);

        // Act
        List<Patient> result = patientService.findPatientByDoctorIdIsNull();

        // Assert
        assertEquals(2, result.size());
    }

    @Test
    void findByDoctorId() {
        // Arrange
        Long doctorId = 1L;
        List<Patient> patients = new ArrayList<>();
        patients.add(new Patient(1L, "ravi", "kant", "ravi.kant@gmail.com", doctorId));
        when(patientRepository.findAllByDoctorId(doctorId)).thenReturn(patients);

        // Act
        List<Patient> result = patientService.findByDoctorId(doctorId);

        // Assert
        assertEquals(1, result.size());
        assertEquals(doctorId, result.get(0).getDoctorId());
    }

    @Test
    void saveAllNullPatient() {
        // Arrange
        Long doctorId = 1L;
        List<Patient> patients = new ArrayList<>();
        patients.add(new Patient(1L, "ravi", "kant", "ravi.kant@gmail.com", doctorId));
        patients.add(new Patient(2L, "shivam", "kumar", "shivam.kumar@gmail.com", doctorId));
        when(patientRepository.findAllByDoctorId(doctorId)).thenReturn(patients);
        when(patientRepository.save(any())).thenReturn(null);

        // Act
        patientService.saveAllNullPatient(doctorId);

        // Assert
        verify(patientRepository, times(2)).save(any());
    }
}
