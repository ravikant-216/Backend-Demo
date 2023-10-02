package com.zemoso.springboot.assignment.controller;

import com.zemoso.springboot.assignment.entity.Doctor;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class DoctorControllerTest {

    @Mock
    private DoctorService doctorService;

    @InjectMocks
    private DoctorController doctorController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllDoctors() {
        // Test data
        List<Doctor> doctors = new ArrayList<>();
        doctors.add(new Doctor(1L, "Ravi Kant", "Nutan Nagar colony", "abc123"));
        doctors.add(new Doctor(2L, "Ranjan Kumar", "Nutan Nagar colony", "abc124"));

        // Mocking the service method
        when(doctorService.getAllDoctors()).thenReturn(doctors);

        // Call the controller method
        ResponseEntity<List<Doctor>> response = doctorController.getAllDoctors();

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals(doctors, response.getBody());
    }

    @Test
    void getDoctorById_existingId_shouldReturnDoctor() {
        // Test data
        Long doctorId = 1L;
        Doctor doctor = new Doctor(doctorId, "Ravi Kant", "Nutan Nagar colony", "abc123");

        // Mocking the service method
        when(doctorService.getDoctorById(doctorId)).thenReturn(doctor);

        // Call the controller method
        ResponseEntity<Doctor> response = doctorController.getDoctorById(doctorId);

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(doctorId, response.getBody().getId());
    }

    @Test
    void getDoctorById_nonExistingId_shouldReturnNotFound() {
        // Test data
        Long nonExistingId = 100L;

        // Mocking the service method to return null for non-existing ID
        when(doctorService.getDoctorById(nonExistingId)).thenReturn(null);

        // Call the controller method
        ResponseEntity<Doctor> response = doctorController.getDoctorById(nonExistingId);

        // Assertions
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void createDoctor() {
        // Test data
        Doctor doctorToCreate = new Doctor(null, "New Doctor", "Some Address", "new123");
        Doctor createdDoctor = new Doctor(1L, "New Doctor", "Some Address", "new123");

        // Mocking the service method
        when(doctorService.createDoctor(doctorToCreate)).thenReturn(createdDoctor);

        // Call the controller method
        ResponseEntity<Doctor> response = doctorController.createDoctor(doctorToCreate);

        // Assertions
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        assertEquals("New Doctor", response.getBody().getDoctorName());
    }

    @Test
    void updateDoctor() {
        // Test data
        Long doctorId = 1L;
        Doctor existingDoctor = new Doctor(doctorId, "Ravi Kant", "Nutan Nagar colony", "abc123");
        Doctor updatedDoctor = new Doctor(doctorId, "Updated Doctor", "Updated Address", "updated123");

        // Mocking the service method
        when(doctorService.updateDoctor(existingDoctor)).thenReturn(updatedDoctor);

        // Call the controller method
        ResponseEntity<Doctor> response = doctorController.updateDoctor(doctorId, existingDoctor);

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(doctorId, response.getBody().getId());
        assertEquals("Updated Doctor", response.getBody().getDoctorName());
    }

    @Test
    void deleteDoctor() {
        // Test data
        Long doctorId = 1L;

        // Call the controller method
        ResponseEntity<Void> response = doctorController.deleteDoctor(doctorId);

        // Assertions
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }
}
