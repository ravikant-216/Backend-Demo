package com.example.thymeleaf.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Patient")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_id")
    private Long patientId;

    private String name;

    private String email;

    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    public Patient(String name, String email, String password, Doctor doctor) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.doctor = doctor;
    }


}
