package com.zemoso.springboot.assignment.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@Table(name = "doctor")
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

    @OneToMany(mappedBy = "doctor")
    private List<Patient> patients;


    public Doctor(long id, String doctorName, String doctorAddress, String doctorCode) {
        this.id=id;
        this.doctorCode=doctorCode;
        this.doctorName=doctorName;
        this.doctorAddress=doctorAddress;
    }
}
