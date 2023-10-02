package com.zemoso.springboot.assignment.service;

import com.zemoso.springboot.assignment.dto.PatientDTO;
import com.zemoso.springboot.assignment.entity.Doctor;
import com.zemoso.springboot.assignment.entity.Patient;
import com.zemoso.springboot.assignment.repository.DoctorRepository;
import com.zemoso.springboot.assignment.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @InjectMocks
    private PatientService patientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllPatient_ReturnsListOfPatientDTOs() {
        // Arrange
        List<Patient> patients = new ArrayList<>();
        patients.add(new Patient(1L, "John", "Doe", "john.doe@example.com", new Doctor()));
        patients.add(new Patient(2L, "Jane", "Smith", "jane.smith@example.com", new Doctor()));

        when(patientRepository.findAll()).thenReturn(patients);

        // Act
        List<PatientDTO> result = patientService.getAllPatient();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getFirstName());
        assertEquals("Doe", result.get(0).getLastName());
        assertEquals("john.doe@example.com", result.get(0).getEmail());
        // Verify that the repository method was called
        verify(patientRepository, times(1)).findAll();
    }

    @Test
    void getPatientById_ExistingId_ReturnsPatientDTO() {
        // Arrange
        Long id = 1L;
        Patient patient = new Patient(id, "John", "Doe", "john.doe@example.com", new Doctor());

        when(patientRepository.findById(id)).thenReturn(Optional.of(patient));

        // Act
        PatientDTO result = patientService.getPatientById(id);

        // Assert
        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("john.doe@example.com", result.getEmail());
        // Verify that the repository method was called
        verify(patientRepository, times(1)).findById(id);
    }

    @Test
    void getPatientById_NonexistentId_ThrowsNoSuchElementException() {
        // Arrange
        Long id = 1L;

        when(patientRepository.findById(id)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(NoSuchElementException.class, () -> patientService.getPatientById(id));
        // Verify that the repository method was called
        verify(patientRepository, times(1)).findById(id);
    }

    @Test
    void createPatient_ValidInput_ReturnsCreatedPatientDTO() {
        // Arrange
        PatientDTO patientDTO = new PatientDTO(1L, "John", "Doe", "john.doe@example.com", 1L);
        Doctor doctor = new Doctor(1L, "Dr. John", "123 Main St", "D1234");
        Patient createdPatient = new Patient(1L, "John", "Doe", "john.doe@example.com", doctor);

        when(doctorRepository.findById(patientDTO.getDoctorId())).thenReturn(Optional.of(doctor));
        when(patientRepository.save(any(Patient.class))).thenReturn(createdPatient);

        // Act
        PatientDTO result = patientService.createPatient(patientDTO);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("john.doe@example.com", result.getEmail());
        // Verify that the repository methods were called
        verify(doctorRepository, times(1)).findById(patientDTO.getDoctorId());
        verify(patientRepository, times(1)).save(any(Patient.class));
    }

    @Test
    void createPatient_NonexistentDoctorId_ThrowsNoSuchElementException() {
        // Arrange
        PatientDTO patientDTO = new PatientDTO(1L, "John", "Doe", "john.doe@example.com", 1L);

        when(doctorRepository.findById(patientDTO.getDoctorId())).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(NoSuchElementException.class, () -> patientService.createPatient(patientDTO));
        // Verify that the repository method was called
        verify(doctorRepository, times(1)).findById(patientDTO.getDoctorId());
        // Ensure that the patientRepository.save() method was not called
        verify(patientRepository, never()).save(any(Patient.class));
    }

    @Test
    void updatePatient_ValidInput_ReturnsUpdatedPatientDTO() {
        // Arrange
        PatientDTO patientDTO = new PatientDTO(1L, "John", "Doe", "john.doe@example.com", 1L);
        Doctor doctor = new Doctor(1L, "Dr. John", "123 Main St", "D1234");
        Patient existingPatient = new Patient(1L, "Existing", "Patient", "existing.patient@example.com", doctor);
        Patient updatedPatient = new Patient(1L, "John", "Doe", "john.doe@example.com", doctor);

        when(patientRepository.findById(patientDTO.getId())).thenReturn(Optional.of(existingPatient));
        when(doctorRepository.findById(patientDTO.getDoctorId())).thenReturn(Optional.of(doctor));
        when(patientRepository.save(any(Patient.class))).thenReturn(updatedPatient);

        // Act
        PatientDTO result = patientService.updatePatient(patientDTO);


        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("john.doe@example.com", result.getEmail());
        // Verify that the repository methods were called
        verify(patientRepository, times(1)).findById(patientDTO.getId());
        verify(doctorRepository, times(1)).findById(patientDTO.getDoctorId());
        verify(patientRepository, times(1)).save(any(Patient.class));
    }

    @Test
    void updatePatient_NonexistentPatientId_ThrowsNoSuchElementException() {
        // Arrange
        PatientDTO patientDTO = new PatientDTO(1L, "John", "Doe", "john.doe@example.com", 1L);

        when(patientRepository.findById(patientDTO.getId())).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(NoSuchElementException.class, () -> patientService.updatePatient(patientDTO));
        // Verify that the repository method was called
        verify(patientRepository, times(1)).findById(patientDTO.getId());
        // Ensure that the doctorRepository.findById() and patientRepository.save() methods were not called
        verify(doctorRepository, never()).findById(patientDTO.getDoctorId());
        verify(patientRepository, never()).save(any(Patient.class));
    }

    @Test
    void deletePatient_ExistingId_DeletesPatient() {
        // Arrange
        Long patientId = 1L;

        // Act
        patientService.deletePatient(patientId);

        // Verify that the repository method was called
        verify(patientRepository, times(1)).deleteById(patientId);
    }
}
