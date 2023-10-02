package com.example.thymeleaf.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.Table;

@Entity
@Table(name = "Doctor")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctor_id")
    private Long doctorId;

    private String name;

    private String email;

    private String password;

   public Doctor(String name,String email,String password){
       this.email=email;
       this.name=name;
       this.password=password;
   }
}
