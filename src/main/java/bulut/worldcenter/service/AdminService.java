package bulut.worldcenter.service;

import bulut.worldcenter.model.Admin;
import bulut.worldcenter.model.Role;
import bulut.worldcenter.model.User;
import bulut.worldcenter.repository.AdminRepository;
import bulut.worldcenter.repository.RoleRepository;
import bulut.worldcenter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;

@Service
public class AdminService {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerAdmin(Admin admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));

        if (admin.getRoles() == null) {
            admin.setRoles(new HashSet<>());
        }

        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
        if (adminRole != null) {
            admin.getRoles().add(adminRole);
        } else {
            adminRole = new Role("ROLE_ADMIN");
            roleRepository.save(adminRole);
            admin.getRoles().add(adminRole);
        }

        adminRepository.save(admin);
    }

    public Optional<Admin> findByUsername(String username) {
        return adminRepository.findByUsername(username); // Optional olarak döndür
    }



}
