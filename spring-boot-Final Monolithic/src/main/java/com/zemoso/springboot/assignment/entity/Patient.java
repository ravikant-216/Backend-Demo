package com.zemoso.springboot.assignment.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "patients")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="first_name", nullable = false)
    private String firstName;

    @Column(name="last_name", nullable = false)
    private String lastName;

    @Column(name="email", nullable = false)
    private String email;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = true)
    private Doctor doctor;

    public Patient(Long id, String firstName,String lastName,String email){
        this.firstName=firstName;
        this.lastName=lastName;
        this.id=id;
        this.email=email;
        this.doctor=null;
    }
}
