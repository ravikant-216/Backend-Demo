package com.example.thymeleaf.controller;

import com.example.thymeleaf.entity.Appointment;
import com.example.thymeleaf.entity.Doctor;
import com.example.thymeleaf.entity.Patient;
import com.example.thymeleaf.service.AppointmentService;
import com.example.thymeleaf.service.DoctorService;
import com.example.thymeleaf.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/appointment")
public class AppointmentController {
    private final PatientService patientService;
    private final AppointmentService appointmentService;
    private final DoctorService doctorService;

    @Autowired
    public AppointmentController(PatientService patientService, AppointmentService appointmentService, DoctorService doctorService) {
        this.patientService = patientService;
        this.appointmentService = appointmentService;
        this.doctorService = doctorService;
    }

    @GetMapping("/patient/add")
    public String showAddPatientForm(Model model) {
        List<Doctor> doctors = doctorService.getAllDoctors();
        model.addAttribute("appointment", new Appointment());
        model.addAttribute("doctors", doctors);
        return "admin/addAppointment";
    }
    @GetMapping("/patient/add/{patientId}")
    public String updatePatientForm(@PathVariable Long patientId, Model model) {
        List<Doctor> doctors = doctorService.getAllDoctors();

        model.addAttribute("appointment",appointmentService.getAppointmentByPatientPatientId(patientId));
        model.addAttribute("doctors", doctors);
        return "admin/addAppointment";
    }

    @PostMapping("/patient/save")
    public String saveAppointment(@ModelAttribute("appointment") Appointment appointment) {
        Optional<Doctor> doctor = doctorService.getDoctorById(appointment.getDoctor().getDoctorId());
        Patient patient= appointment.getPatient();
        if(doctor.isPresent()) {
            patient.setDoctor(doctor.get());
        }
        else {
           throw new RuntimeException();
        }
        patient.setPassword(patient.getEmail());
        patientService.createPatient(patient);
        appointment.setPatient(patient);
        appointmentService.createAppointment(appointment);
        return "redirect:/admin/patient/list";
    }

}

