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

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("doctor")
public class DoctorController {
    private final DoctorService doctorService;
    private final PatientService patientService;
    private final AppointmentService appointmentService;

    @Autowired
    public DoctorController(DoctorService doctorService, PatientService patientService, AppointmentService appointmentService) {
        this.doctorService = doctorService;
        this.patientService = patientService;
        this.appointmentService = appointmentService;
    }

    @GetMapping("/edit-doctor/{doctorId}")
    public String showEditDoctorForm(@PathVariable("doctorId") Long doctorId,Model model) {
        Optional<Doctor> doctor = doctorService.getDoctorById(doctorId);
        if (doctor.isPresent()) {
            Doctor doctor1=doctor.get();
            doctor1.setDoctorId(doctorId);
            model.addAttribute("doctor", doctor1);
        }

        return "doctor/doctorEditForm";
    }

    @PostMapping("/edit-doctor")
    public String updateDoctor(@ModelAttribute("doctor") Doctor doctor) {
        doctorService.createDoctor(doctor);
        return "redirect:/doctor/home";
    }

    @GetMapping("/home")
    public  String home(Authentication authentication, Model model)
    {
        String username=authentication.getName() ;
        System.out.println(authentication.getDetails());
        Doctor doctor = doctorService.findByEmail(username);
        Long userId = doctor.getDoctorId();
        model.addAttribute("doctor",doctor);
        return "/doctor/doctorHome";
    }


    @GetMapping("/outpatients/{doctorId}")
    public String getOneDoctor(@PathVariable("doctorId") Long doctorId, Model model) {

        List<Appointment> appointments = appointmentService.getAppointmentByDocId(doctorId);
        List<Patient> patients = patientService.getPatientsByDoctorId(doctorId);

        model.addAttribute("patients", patients);
        model.addAttribute("appointments", appointments);

        if(!patients.isEmpty() && !appointments.isEmpty())  return "/doctor/showPatientByDocId";
        else  return "errorPage";
    }
}
