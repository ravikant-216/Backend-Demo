package com.example.thymeleaf.service;

import com.example.thymeleaf.dao.AppointmentDAO;
import com.example.thymeleaf.entity.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    private final AppointmentDAO appointmentDAO;

    @Autowired
    public AppointmentService(AppointmentDAO appointmentDAO) {
        this.appointmentDAO = appointmentDAO;
    }

    public List<Appointment> getAllAppointments() {
        return appointmentDAO.findAll();
    }

    public Optional<Appointment> getAppointmentById(Long appointmentId) {
        return appointmentDAO.findById(appointmentId);
    }

    public void createAppointment(Appointment appointment) {
        appointmentDAO.save(appointment);
    }

    public Appointment updateAppointment(Appointment appointment) {
        return appointmentDAO.save(appointment);
    }

    public void deleteAppointment(Long appointmentId) {
        appointmentDAO.deleteById(appointmentId);
    }

    public List<Appointment> getAppointmentByDocId(Long id){
        return appointmentDAO.findByDoctorDoctorId(id);
    }
    public Appointment getAppointmentByPatientPatientId(Long id){
        return appointmentDAO.getAppointmentByPatientPatientId(id);
    }

    @Transactional
    public void deleteByPatientId(Long id){
        appointmentDAO.deleteByPatientPatientId(id);
    }

    @Transactional
    public void deleteByDoctorId(Long id){
        appointmentDAO.deleteByDoctorDoctorId(id);
    }
}
