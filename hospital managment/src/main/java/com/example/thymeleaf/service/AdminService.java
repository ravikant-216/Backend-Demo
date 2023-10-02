package com.example.thymeleaf.service;

import com.example.thymeleaf.dao.AdminRepository;
import com.example.thymeleaf.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    private final AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    public Optional<Admin> getAdminById(Long adminId) {
        return adminRepository.findById(adminId);
    }

    public Admin createAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    public Admin updateAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    public void deleteAdmin(Long adminId) {
        adminRepository.deleteById(adminId);
    }

    public Admin findByEmail(String username) {
        return  adminRepository.findByEmail(username);
    }
}
