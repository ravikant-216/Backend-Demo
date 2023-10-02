package com.example.thymeleaf.entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Admin")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminId;

    private String name;

    private String email;

    private String password;
    public Admin(String name,String email,String password){
        this.name=name;
        this.email=email;
        this.password=password;
    }
}

