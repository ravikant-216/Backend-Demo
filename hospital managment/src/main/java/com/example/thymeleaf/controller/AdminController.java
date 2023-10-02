package com.example.thymeleaf.controller;

import com.example.thymeleaf.entity.Admin;
import com.example.thymeleaf.entity.Appointment;
import com.example.thymeleaf.entity.Doctor;
import com.example.thymeleaf.entity.Patient;
import com.example.thymeleaf.service.AdminService;
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
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    private final DoctorService doctorService;
    private final PatientService patientService;
    private final AppointmentService appointmentService;

    @Autowired
    public AdminController(AdminService adminService, DoctorService doctorService, PatientService patientService, AppointmentService appointmentService) {
        this.adminService = adminService;
        this.doctorService = doctorService;
        this.patientService = patientService;
        this.appointmentService = appointmentService;
    }

    // Display the Doctor Form for adding a new doctor


    @GetMapping("/home")
    public  String home(Authentication authentication, Model model)
    {
        String username=authentication.getName() ;
        Admin admin = adminService.findByEmail(username);
        Long userId = admin.getAdminId();
        model.addAttribute("admin",admin);
        return "admin/adminHome";
    }

    @GetMapping("/add-doctor")
    public String showDoctorForm(Model model) {
        model.addAttribute("doctor", new Doctor());
        return "/admin/doctorEditForm";
    }

    // Display all doctors
    @GetMapping("/view-doctors")
    public String viewDoctors(Model model) {
        List<Doctor> doctors = doctorService.getAllDoctors();
        model.addAttribute("doctors", doctors);
        System.out.println(doctors);
        return "/admin/doctorList";
    }

    // Display the Edit Doctor Form for a specific doctor
    @GetMapping("/edit-doctor/{doctorId}")
    public String showEditDoctorForm(@PathVariable("doctorId") Long doctorId,Model model) {
        Optional<Doctor> doctor = doctorService.getDoctorById(doctorId);
        if (doctor.isPresent()) {
            Doctor doctor1=doctor.get();
            doctor1.setDoctorId(doctorId);
            model.addAttribute("doctor", doctor1);
            System.out.println(doctor1.getPassword());
        }

        return "admin/doctorEditForm";
    }

    // Update an existing doctor
    @PostMapping("/edit-doctor")
    public String updateDoctor(@ModelAttribute("doctor") Doctor doctor) {
        System.out.println("\n\nBefore "+ doctor.getPassword());
        if(doctor.getPassword().isEmpty())
            doctor.setPassword(doctor.getEmail());
        System.out.println("\n\nAfter "+ doctor.getPassword());
        doctorService.createDoctor(doctor);
        return "redirect:/admin/view-doctors";
    }

    @GetMapping("/patient/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        appointmentService.deleteByPatientId(id);
        patientService.deletePatient(id);
        return "redirect:/admin/patient/list";
    }

    @GetMapping("/patient/list")
    public String userList(Model model) {
        List<Patient> users = patientService.getAllPatients();
        model.addAttribute("users", users);
        return "admin/list-patients";
    }

    // Delete a doctor
    @GetMapping("/delete-doctor/{doctorId}")
    public String deleteDoctor(@PathVariable("doctorId") Long doctorId) {

        List<Appointment> appointments= appointmentService.getAppointmentByDocId(doctorId);
        for(Appointment temp:appointments){
            appointmentService.deleteAppointment(temp.getAppointmentId());
        }

        List<Patient> patients = patientService.getPatientsByDoctorId(doctorId);
        for (Patient patient : patients) {
            patientService.deletePatient(patient.getPatientId());
        }

        doctorService.deleteDoctor(doctorId);
        return "redirect:/admin/view-doctors";
    }

    @GetMapping("/outpatients/{doctorId}")
    public String getOneDoctor(@PathVariable("doctorId") Long doctorId, Model model) {

            List<Appointment> appointments = appointmentService.getAppointmentByDocId(doctorId);
            List<Patient> patients = patientService.getPatientsByDoctorId(doctorId);

            model.addAttribute("patients", patients);
            model.addAttribute("appointments", appointments);

        if(!patients.isEmpty() && !appointments.isEmpty())  return "showPatientByDocId";
        else  return "errorPage";
    }
}
