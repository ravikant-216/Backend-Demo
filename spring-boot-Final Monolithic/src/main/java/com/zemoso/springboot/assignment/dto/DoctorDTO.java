package com.zemoso.springboot.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDTO {

    private Long id;
    private String doctorName;
    private String doctorAddress;
    private String doctorCode;

}
