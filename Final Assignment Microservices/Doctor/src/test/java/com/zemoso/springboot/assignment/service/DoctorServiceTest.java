package com.zemoso.springboot.assignment.service;

import com.zemoso.springboot.assignment.entity.Doctor;
import com.zemoso.springboot.assignment.repository.DoctorRepository;
import org.h2.command.dml.MergeUsing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DoctorServiceTest {
    @Mock
    private DoctorRepository doctorRepository;

    @InjectMocks
    private DoctorService doctorService;
    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllDoctors() {
        List<Doctor> doctors = new ArrayList<>();
        doctors.add(new Doctor(1L, "Ravi Kant", "Nutan Nagar Colony", "doc123"));
        doctors.add(new Doctor(2L, "Ranjan Kant", "Nutan Nagar Colony", "doc124"));
        when(doctorRepository.findAll()).thenReturn(doctors);

        // Act
        List<Doctor> result = doctorService.getAllDoctors();

        // Assert
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
    }

    @Test
    void getDoctorById_existingId_shouldReturnDoctor() {
        // Arrange
        Long doctorId = 1L;
        Doctor doctor = new Doctor(doctorId, "Ravi Kant", "Nutan Nagar Colony", "doc123");
        when(doctorRepository.findById(doctorId)).thenReturn(Optional.of(doctor));

        // Act
        Doctor result = doctorService.getDoctorById(doctorId);

        // Assert
        assertNotNull(result);
        assertEquals(doctorId, result.getId());
    }

    @Test
    void getDoctorById_nonExistingId_shouldReturnNull() {
        // Arrange
        Long nonExistingId = 100L;
        when(doctorRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        // Act
        Doctor result = doctorService.getDoctorById(nonExistingId);

        // Assert
        assertNull(result);
    }

    @Test
    void createDoctor() {
        // Arrange
        Doctor doctorToCreate = new Doctor(null, "New Doctor", "Some Address", "new123");
        Doctor createdDoctor = new Doctor(1L, "New Doctor", "Some Address", "new123");
        when(doctorRepository.save(doctorToCreate)).thenReturn(createdDoctor);

        // Act
        Doctor result = doctorService.createDoctor(doctorToCreate);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("New Doctor", result.getDoctorName());
    }

    @Test
    void updateDoctor() {
        // Arrange
        Long doctorId = 1L;
        Doctor existingDoctor = new Doctor(doctorId, "Ravi Kant", "Nutan Nagar Colony", "doc123");
        Doctor updatedDoctor = new Doctor(doctorId, "Updated Doctor", "Updated Address", "updated123");
        when(doctorRepository.save(existingDoctor)).thenReturn(updatedDoctor);

        // Act
        Doctor result = doctorService.updateDoctor(existingDoctor);

        // Assert
        assertNotNull(result);
        assertEquals(doctorId, result.getId());
        assertEquals("Updated Doctor", result.getDoctorName());
    }

    @Test
    void deleteDoctor() {

        Long doctorId = 1L;

        doctorService.deleteDoctor(doctorId);

        verify(doctorRepository, times(1)).deleteById(doctorId);
    }
}
