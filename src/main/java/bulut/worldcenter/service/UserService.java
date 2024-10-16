package bulut.worldcenter.service;

import bulut.worldcenter.model.Role;
import bulut.worldcenter.model.Stock;
import bulut.worldcenter.model.User;
import bulut.worldcenter.repository.RoleRepository;
import bulut.worldcenter.repository.StockRepository;
import bulut.worldcenter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private StockRepository stockRepository;

    public void registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Şifreyi şifrele

        if (user.getRoles() == null) {
            user.setRoles(new HashSet<>());
        }

        Role customerRole = roleRepository.findByName("ROLE_CUSTOMER");
        if (customerRole != null) {
            user.getRoles().add(customerRole);
        } else {
            customerRole = new Role("ROLE_CUSTOMER");
            roleRepository.save(customerRole);
            user.getRoles().add(customerRole);
        }

        userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User findCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(userDetails.getUsername()).orElse(null);
    }




}
