package com.zemoso.springboot.assignment.service;

import com.zemoso.springboot.assignment.dto.DoctorDTO;
import com.zemoso.springboot.assignment.entity.Doctor;
import com.zemoso.springboot.assignment.repository.DoctorRepository;
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

class DoctorServiceTest {

    @Mock
    private DoctorRepository doctorRepository;

    @InjectMocks
    private DoctorService doctorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllDoctors() {
        // Arrange
        List<Doctor> doctorList = new ArrayList<>();
        doctorList.add(new Doctor(1L, "Dr. Ravi Kant", "123 Main St", "D1234"));
        doctorList.add(new Doctor(2L, "Dr. Asha Rani", "456 Park Ave", "D5678"));

        when(doctorRepository.findAll()).thenReturn(doctorList);

        // Act
        List<DoctorDTO> result = doctorService.getAllDoctors();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Dr. Ravi Kant", result.get(0).getDoctorName());
        assertEquals("Dr. Asha Rani", result.get(1).getDoctorName());
        // Verify that the repository method was called
        verify(doctorRepository, times(1)).findAll();
    }

    @Test
    void testGetDoctorById() {
        // Arrange
        Doctor doctor = new Doctor(1L, "Dr. Ravi Kant", "123 Main St", "D1234");

        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));

        // Act
        DoctorDTO result = doctorService.getDoctorById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Dr. Ravi Kant", result.getDoctorName());
    }

    @Test
    void testGetDoctorById_InvalidId() {
        // When doctor with the specified id is not found, an exception should be thrown
        when(doctorRepository.findById(1L)).thenReturn(Optional.empty());

        // Call the service method and expect an exception
        assertThrows(NoSuchElementException.class, () -> doctorService.getDoctorById(1L));
    }

    @Test
    void testCreateDoctor() {
        // Arrange
        DoctorDTO doctorDTO = new DoctorDTO(null, "Dr. Ravi Kant", "123 Main St", "D1234");
        Doctor createdDoctor = new Doctor(1L, "Dr. Ravi Kant", "123 Main St", "D1234");

        when(doctorRepository.save(any(Doctor.class))).thenReturn(createdDoctor);

        // Act
        DoctorDTO result = doctorService.createDoctor(doctorDTO);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Dr. Ravi Kant", result.getDoctorName());
        // Verify that the repository method was called
        verify(doctorRepository, times(1)).save(any(Doctor.class));
    }

    @Test
    void testUpdateDoctor() {
        // Arrange
        DoctorDTO doctorDTO = new DoctorDTO(1L, "Dr. Ravi Kant", "123 Main St", "D1234");
        Doctor existingDoctor = new Doctor(1L, "Dr. Asha Rani", "456 Park Ave", "D5678");
        Doctor updatedDoctor = new Doctor(1L, "Dr. Ravi Kant", "123 Main St", "D1234");

        when(doctorRepository.findById(doctorDTO.getId())).thenReturn(Optional.of(existingDoctor));
        when(doctorRepository.save(any(Doctor.class))).thenReturn(updatedDoctor);

        // Act
        DoctorDTO result = doctorService.updateDoctor(doctorDTO);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Dr. Ravi Kant", result.getDoctorName());
        // Verify that the repository method was called
        verify(doctorRepository, times(1)).findById(doctorDTO.getId());
        verify(doctorRepository, times(1)).save(any(Doctor.class));
    }

    @Test
    void testUpdateDoctor_InvalidId() {
        // When doctor with the specified id is not found, an exception should be thrown
        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setId(1L);

        when(doctorRepository.findById(1L)).thenReturn(Optional.empty());

        // Call the service method and expect an exception
        assertThrows(NoSuchElementException.class, () -> doctorService.updateDoctor(doctorDTO));
    }

    @Test
    void testDeleteDoctor() {
        // Arrange
        Long doctorId = 1L;

        // Act
        doctorService.deleteDoctor(doctorId);

        // Verify that the repository method was called
        verify(doctorRepository, times(1)).deleteById(doctorId);
    }
}
