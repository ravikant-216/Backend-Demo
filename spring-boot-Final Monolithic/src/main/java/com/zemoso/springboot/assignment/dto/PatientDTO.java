package com.zemoso.springboot.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Long doctorId;

}
