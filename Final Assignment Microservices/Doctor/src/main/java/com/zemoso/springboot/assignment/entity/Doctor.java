package com.zemoso.springboot.assignment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "doctors")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "doctor_name", nullable = false)
    private String doctorName;

    @Column(name = "doctor_address", nullable = false)
    private String doctorAddress;

    @Column(name = "doctor_code", nullable = false)
    private String doctorCode;

    // Getters and Setters

}
