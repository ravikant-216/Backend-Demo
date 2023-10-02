package com.example.thymeleaf.service;


import com.example.thymeleaf.dao.AdminRepository;
import com.example.thymeleaf.dao.DoctorRepository;
import com.example.thymeleaf.dao.PatientRepository;
import com.example.thymeleaf.entity.Admin;
import com.example.thymeleaf.entity.Doctor;
import com.example.thymeleaf.entity.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByEmail(email);
        if (admin != null) {
            return buildUserDetails(admin);
        }

        Doctor doctor = doctorRepository.findByEmail(email);
        if (doctor != null) {
            return buildUserDetails(doctor);
        }

        Patient patient = patientRepository.findByEmail(email);
        if (patient != null) {
            return buildUserDetails(patient);
        }

        throw new UsernameNotFoundException("User not found with email: " + email);
    }

    private UserDetails buildUserDetails(Object user) {
        String username;
        String password;
        List<GrantedAuthority> authorities;

        if (user instanceof Admin) {
            Admin admin = (Admin) user;
            username = admin.getEmail();
            password = admin.getPassword();
            authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else if (user instanceof Doctor) {
            Doctor doctor = (Doctor) user;
            username = doctor.getEmail();
            password = doctor.getPassword();
            authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_DOCTOR"));
        } else {
            Patient patient = (Patient) user;
            username = patient.getEmail();
            password = patient.getPassword();
            authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_PATIENT"));
        }

        return new User(username, password, authorities);
    }
}