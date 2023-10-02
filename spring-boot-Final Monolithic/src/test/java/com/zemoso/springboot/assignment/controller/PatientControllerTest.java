package com.zemoso.springboot.assignment.controller;

import com.zemoso.springboot.assignment.dto.PatientDTO;
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

import static org.junit.jupiter.api.Assertions.*;
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
    void getAllPatients_ReturnsListOfPatientDTOs() {
        // Arrange
        List<PatientDTO> patientDTOList = new ArrayList<>();
        patientDTOList.add(new PatientDTO(1L, "John", "Doe", "john.doe@example.com", 1L));
        patientDTOList.add(new PatientDTO(2L, "Jane", "Smith", "jane.smith@example.com", 1L));

        when(patientService.getAllPatient()).thenReturn(patientDTOList);

        // Act
        ResponseEntity<List<PatientDTO>> response = patientController.getAllPatient();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("John", response.getBody().get(0).getFirstName());
        assertEquals("Doe", response.getBody().get(0).getLastName());
        assertEquals("john.doe@example.com", response.getBody().get(0).getEmail());
        // Verify that the service method was called
        verify(patientService, times(1)).getAllPatient();
    }

    @Test
    void getPatientById_ExistingId_ReturnsPatientDTO() {
        // Arrange
        Long id = 1L;
        PatientDTO patientDTO = new PatientDTO(id, "John", "Doe", "john.doe@example.com", 1L);

        when(patientService.getPatientById(id)).thenReturn(patientDTO);

        // Act
        ResponseEntity<PatientDTO> response = patientController.getPatientById(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("John", response.getBody().getFirstName());
        assertEquals("Doe", response.getBody().getLastName());
        assertEquals("john.doe@example.com", response.getBody().getEmail());
        // Verify that the service method was called
        verify(patientService, times(1)).getPatientById(id);
    }

    @Test
    void getPatientById_NonexistentId_ReturnsNotFound() {
        // Arrange
        Long id = 1L;

        when(patientService.getPatientById(id)).thenReturn(null);

        // Act
        ResponseEntity<PatientDTO> response = patientController.getPatientById(id);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        // Verify that the service method was called
        verify(patientService, times(1)).getPatientById(id);
    }

    @Test
    void createPatient_ValidInput_ReturnsCreatedPatientDTO() {
        // Arrange
        PatientDTO patientDTO = new PatientDTO(1L, "John", "Doe", "john.doe@example.com", 1L);
        PatientDTO createdPatientDTO = new PatientDTO(1L, "John", "Doe", "john.doe@example.com", 1L);

        when(patientService.createPatient(patientDTO)).thenReturn(createdPatientDTO);

        // Act
        ResponseEntity<PatientDTO> response = patientController.createPatient(patientDTO);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        assertEquals("John", response.getBody().getFirstName());
        assertEquals("Doe", response.getBody().getLastName());
        assertEquals("john.doe@example.com", response.getBody().getEmail());
        // Verify that the service method was called
        verify(patientService, times(1)).createPatient(patientDTO);
    }

    @Test
    void updatePatient_ValidInput_ReturnsUpdatedPatientDTO() {
        // Arrange
        PatientDTO patientDTO = new PatientDTO(1L, "John", "Doe", "john.doe@example.com", 1L);
        PatientDTO updatedPatientDTO = new PatientDTO(1L, "John", "Doe", "john.doe@example.com", 1L);

        when(patientService.updatePatient(patientDTO)).thenReturn(updatedPatientDTO);

        // Act
        ResponseEntity<PatientDTO> response = patientController.updatePatient(patientDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        assertEquals("John", response.getBody().getFirstName());
        assertEquals("Doe", response.getBody().getLastName());
        assertEquals("john.doe@example.com", response.getBody().getEmail());
        // Verify that the service method was called
        verify(patientService, times(1)).updatePatient(patientDTO);
    }

    @Test
    void deletePatient_ExistingId_ReturnsNoContent() {
        // Arrange
        Long id = 1L;

        // Act
        ResponseEntity<Void> response = patientController.deletePatient(id);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        // Verify that the service method was called
        verify(patientService, times(1)).deletePatient(id);
    }
}
