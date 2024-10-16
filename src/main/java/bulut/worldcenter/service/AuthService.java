package bulut.worldcenter.service;

import bulut.worldcenter.model.Admin;
import bulut.worldcenter.model.User;
import bulut.worldcenter.repository.AdminRepository;
import bulut.worldcenter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AdminRepository adminRepository;

    public boolean loginuser(String username, String password) throws AuthenticationException {
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isPresent()) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            return authentication.isAuthenticated();
        }

        return false;
    }

    public boolean loginadmin(String username, String password) throws AuthenticationException {
        Optional<Admin> optionalAdmin = adminRepository.findByUsername(username); // Doğru şekilde Admin tipini kullan

        if (optionalAdmin.isPresent()) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            return authentication.isAuthenticated();
        }

        return false;
    }


}