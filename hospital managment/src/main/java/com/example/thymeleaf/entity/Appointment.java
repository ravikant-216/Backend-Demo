package com.example.thymeleaf.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;


import java.util.Date;
@Entity
@Table(name = "Appointment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="appointment_id")
    private Long appointmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String date;

    @DateTimeFormat(pattern = "HH:mm:ss")
    private String time;

    private String description;

    public Appointment(Doctor doctor, Patient patient, String date, String time, String description) {
        this.doctor = doctor;
        this.patient = patient;
        this.date = date;
        this.time = time;
        this.description = description;
    }
}
