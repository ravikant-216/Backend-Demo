package com.zemoso.springboot.assignment.DTO;

import lombok.Data;

@Data
public class PatientDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Long doctorId;


}
