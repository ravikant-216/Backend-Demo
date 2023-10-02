package com.example.thymeleaf.controller;

import com.example.thymeleaf.entity.Appointment;
import com.example.thymeleaf.entity.Doctor;
import com.example.thymeleaf.entity.Patient;
import com.example.thymeleaf.service.AppointmentService;
import com.example.thymeleaf.service.DoctorService;
import com.example.thymeleaf.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/patient")
public class PatientController {
    private final PatientService patientService;
    private final AppointmentService appointment;
    private  final DoctorService doctorService;

    @Autowired
    public PatientController(PatientService patientService, AppointmentService appointment, DoctorService doctorService) {
        this.patientService = patientService;
        this.appointment = appointment;
        this.doctorService = doctorService;
    }
    @GetMapping("/home")
    public  String home(Authentication authentication, Model model)
    {
        String username=authentication.getName() ;
        Patient patient = patientService.findByEmail(username);
        Long userId = patient.getPatientId();
        model.addAttribute("patient",patient);

        return "patient/patientHome";
    }

    @GetMapping("/save/{id}")
    public String showFormForUpdate(@PathVariable("id") Long id, Model model) {
        Optional<Patient> user = patientService.getPatientById(id);
        if(user.isPresent()){
            Patient myUser=user.get();
            model.addAttribute("user", myUser);
        }
        return "patient/addPatientForm";
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") Patient user) {
        patientService.createPatient(user);
        return "redirect:/patient/home";
    }

    @GetMapping("/outpatients/{patientId}")
    public String getOneDoctor(@PathVariable("patientId") Long patientId, Model model) {

        System.out.println("Executed");
        Optional<Patient> patientOptional = patientService.getPatientById(patientId);
        if (patientOptional.isPresent()) {
            Patient patient = patientOptional.get();
            patient.setPatientId(patientId);
            model.addAttribute("patient", patient);

            System.out.println(patient.getName());
            Appointment appointments = appointment.getAppointmentByPatientPatientId(patientId);
            model.addAttribute("appointments", appointments);
            System.out.println(appointments.getDoctor().getName());
        }
        return "patient/showPatientDetail";
    }
}
