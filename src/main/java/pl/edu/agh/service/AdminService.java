package pl.edu.agh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.agh.model.users.Admin;
import pl.edu.agh.repository.users.AdminRepository;

@Service
public class AdminService {
    private final AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public Admin findByEmail(String email) {
        return this.adminRepository.findByEmail(email);
    }

    public void addExample() {
        String password = "Haslo1234$";
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        Admin admin = new Admin("Admin", "Admin", "admin@wp.pl", "123123123", 1, hashedPassword);
        adminRepository.save(admin);
    }

}