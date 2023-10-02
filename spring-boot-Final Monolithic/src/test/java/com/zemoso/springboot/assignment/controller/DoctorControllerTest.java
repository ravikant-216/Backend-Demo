package com.zemoso.springboot.assignment.controller;

import com.zemoso.springboot.assignment.dto.DoctorDTO;
import com.zemoso.springboot.assignment.service.DoctorService;
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

class DoctorControllerTest {

    @Mock
    private DoctorService doctorService;

    @InjectMocks
    private DoctorController doctorController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllDoctors_ReturnsListOfDoctorDTOs() {
        // Arrange
        List<DoctorDTO> doctorDTOList = new ArrayList<>();
        doctorDTOList.add(new DoctorDTO(1L, "Dr. John Doe", "123 Main St", "D1234"));
        doctorDTOList.add(new DoctorDTO(2L, "Dr. Jane Smith", "456 Oak Ave", "D5678"));

        when(doctorService.getAllDoctors()).thenReturn(doctorDTOList);

        // Act
        ResponseEntity<List<DoctorDTO>> response = doctorController.getAllDoctor();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("Dr. John Doe", response.getBody().get(0).getDoctorName());
        assertEquals("123 Main St", response.getBody().get(0).getDoctorAddress());
        assertEquals("D1234", response.getBody().get(0).getDoctorCode());
        // Verify that the service method was called
        verify(doctorService, times(1)).getAllDoctors();
    }

    @Test
    void getDoctorById_ExistingId_ReturnsDoctorDTO() {
        // Arrange
        Long id = 1L;
        DoctorDTO doctorDTO = new DoctorDTO(id, "Dr. John Doe", "123 Main St", "D1234");

        when(doctorService.getDoctorById(id)).thenReturn(doctorDTO);

        // Act
        ResponseEntity<DoctorDTO> response = doctorController.getDoctorById(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Dr. John Doe", response.getBody().getDoctorName());
        assertEquals("123 Main St", response.getBody().getDoctorAddress());
        assertEquals("D1234", response.getBody().getDoctorCode());
        // Verify that the service method was called
        verify(doctorService, times(1)).getDoctorById(id);
    }

    @Test
    void getDoctorById_NonexistentId_ReturnsNotFound() {
        // Arrange
        Long id = 1L;

        when(doctorService.getDoctorById(id)).thenReturn(null);

        // Act
        ResponseEntity<DoctorDTO> response = doctorController.getDoctorById(id);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        // Verify that the service method was called
        verify(doctorService, times(1)).getDoctorById(id);
    }


    @Test
    void createDoctor_ValidInput_ReturnsCreatedDoctorDTO() {
        // Arrange
        DoctorDTO doctorDTO = new DoctorDTO(1L, "Dr. John Doe", "123 Main St", "D1234");
        DoctorDTO createdDoctorDTO = new DoctorDTO(1L, "Dr. John Doe", "123 Main St", "D1234");

        when(doctorService.createDoctor(doctorDTO)).thenReturn(createdDoctorDTO);

        // Act
        ResponseEntity<DoctorDTO> response = doctorController.createDoctor(doctorDTO);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode()); // Change HttpStatus.OK to HttpStatus.CREATED
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        assertEquals("Dr. John Doe", response.getBody().getDoctorName());
        assertEquals("123 Main St", response.getBody().getDoctorAddress());
        assertEquals("D1234", response.getBody().getDoctorCode());
        // Verify that the service method was called
        verify(doctorService, times(1)).createDoctor(doctorDTO);
    }


    @Test
    void updateDoctor_ValidInput_ReturnsUpdatedDoctorDTO() {
        // Arrange
        DoctorDTO doctorDTO = new DoctorDTO(1L, "Dr. John Doe", "123 Main St", "D1234");
        DoctorDTO updatedDoctorDTO = new DoctorDTO(1L, "Dr. John Doe", "456 Oak Ave", "D5678");

        when(doctorService.updateDoctor(doctorDTO)).thenReturn(updatedDoctorDTO);

        // Act
        ResponseEntity<DoctorDTO> response = doctorController.updateDoctor(doctorDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        assertEquals("Dr. John Doe", response.getBody().getDoctorName());
        assertEquals("456 Oak Ave", response.getBody().getDoctorAddress());
        assertEquals("D5678", response.getBody().getDoctorCode());
        // Verify that the service method was called
        verify(doctorService, times(1)).updateDoctor(doctorDTO);
    }

    @Test
    void deleteDoctor_ExistingId_ReturnsNoContent() {
        // Arrange
        Long id = 1L;

        // Act
        ResponseEntity<Void> response = doctorController.deleteDoctor(id);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        // Verify that the service method was called
        verify(doctorService, times(1)).deleteDoctor(id);
    }
}
