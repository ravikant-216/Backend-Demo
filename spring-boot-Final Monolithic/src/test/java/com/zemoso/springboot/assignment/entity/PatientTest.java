package com.zemoso.springboot.assignment.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PatientTest {

    private Patient patient;

    @BeforeEach
    void setUp() {
        // Initialize the Patient instance before each test
        patient = new Patient();
    }

    @AfterEach
    void tearDown() {
        // Clean up after each test if needed
        patient=null;
    }

    @Test
    void testGetId() {
        // Set a value for id using setter method
        patient.setId(1L);
        // Check if the getter method returns the same value
        assertEquals(1L, patient.getId());
    }

    @Test
    void testGetFirstName() {
        patient.setFirstName("Ravi");
        assertEquals("Ravi", patient.getFirstName());
    }

    @Test
    void testGetLastName() {
        patient.setLastName("Kant");
        assertEquals("Kant", patient.getLastName());
    }

    @Test
    void testGetEmail() {
        patient.setEmail("ravi.kant@example.com");
        assertEquals("ravi.kant@example.com", patient.getEmail());
    }

    @Test
    void testGetDoctor() {
        // Create a Doctor instance and associate it with the Patient
        Doctor doctor = new Doctor();
        doctor.setDoctorName("Dr. Asha Rani");
        doctor.setDoctorAddress("456 Park Ave");
        doctor.setDoctorCode("D5678");

        patient.setDoctor(doctor);

        // Check if the Patient's getDoctor() method returns the correct doctor
        assertNotNull(patient.getDoctor());
        assertEquals("Dr. Asha Rani", patient.getDoctor().getDoctorName());
    }

    @Test
    void testSetId() {
        // Set a value for id using setter method
        patient.setId(1L);
        // Check if the getter method returns the same value
        assertEquals(1L, patient.getId());
    }

    @Test
    void testSetFirstName() {
        patient.setFirstName("Ravi");
        assertEquals("Ravi", patient.getFirstName());
    }

    @Test
    void testSetLastName() {
        patient.setLastName("Kant");
        assertEquals("Kant", patient.getLastName());
    }

    @Test
    void testSetEmail() {
        patient.setEmail("ravi.kant@example.com");
        assertEquals("ravi.kant@example.com", patient.getEmail());
    }

    @Test
    void testSetDoctor() {
        // Create a Doctor instance and associate it with the Patient
        Doctor doctor = new Doctor();
        doctor.setDoctorName("Dr. Asha Rani");
        doctor.setDoctorAddress("456 Park Ave");
        doctor.setDoctorCode("D5678");

        patient.setDoctor(doctor);

        // Check if the Patient's getDoctor() method returns the correct doctor
        assertNotNull(patient.getDoctor());
        assertEquals("Dr. Asha Rani", patient.getDoctor().getDoctorName());
    }
}
