package com.zemoso.springboot.assignment.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

class DoctorTest {

    private Doctor doctor;

    @BeforeEach
    void setUp() {
        // Initialize the Doctor instance before each test
        doctor = new Doctor();
    }

    @AfterEach
    void tearDown() {
        doctor=null;
    }

    @Test
    void testGetId() {
        // Set a value for id using setter method
        doctor.setId(1L);
        // Check if the getter method returns the same value
        assertEquals(1L, doctor.getId());
    }

    @Test
    void testGetDoctorName() {
        doctor.setDoctorName("Dr. Ravi Kant");
        assertEquals("Dr. Ravi Kant", doctor.getDoctorName());
    }

    @Test
    void testGetDoctorAddress() {
        doctor.setDoctorAddress("123 Nutan Nagar");
        assertEquals("123 Nutan Nagar", doctor.getDoctorAddress());
    }

    @Test
    void testGetDoctorCode() {
        doctor.setDoctorCode("D1234");
        assertEquals("D1234", doctor.getDoctorCode());
    }

    @Test
    void testGetPatients() {
        // Create a Patient instance and associate it with the Doctor
        Patient patient = new Patient();
        patient.setFirstName("Ranjan");
        patient.setLastName("Doe");
        patient.setEmail("ranjan.doe@example.com");

        doctor.setPatients(List.of(patient));

        // Check if the Doctor's getPatients() method returns the correct list of patients
        assertNotNull(doctor.getPatients());
        assertEquals(1, doctor.getPatients().size());
        assertEquals("Ranjan", doctor.getPatients().get(0).getFirstName());
    }

    @Test
    void testSetId() {
        // Set a value for id using setter method
        doctor.setId(1L);
        // Check if the getter method returns the same value
        assertEquals(1L, doctor.getId());
    }

    @Test
    void testSetDoctorName() {
        doctor.setDoctorName("Dr. Ravi Kant");
        assertEquals("Dr. Ravi Kant", doctor.getDoctorName());
    }

    @Test
    void testSetDoctorAddress() {
        doctor.setDoctorAddress("123 Nutan Nagar");
        assertEquals("123 Nutan Nagar", doctor.getDoctorAddress());
    }

    @Test
    void testSetDoctorCode() {
        doctor.setDoctorCode("D1234");
        assertEquals("D1234", doctor.getDoctorCode());
    }

    @Test
    void testSetPatients() {
        // Create a Patient instance and associate it with the Doctor
        Patient patient = new Patient();
        patient.setFirstName("Ranjan");
        patient.setLastName("Doe");
        patient.setEmail("ranjan.doe@example.com");

        doctor.setPatients(List.of(patient));


        assertNotNull(doctor.getPatients());
        assertEquals(1, doctor.getPatients().size());
        assertEquals("Ranjan", doctor.getPatients().get(0).getFirstName());
    }
}
