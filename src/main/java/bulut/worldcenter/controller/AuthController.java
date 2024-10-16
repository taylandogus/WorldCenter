package bulut.worldcenter.controller;

import bulut.worldcenter.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public RedirectView login(@RequestParam String username, @RequestParam String password, RedirectAttributes attributes) {
        try {
            // Admin giriş kontrolü
            if (authService.loginadmin(username, password)) {
                return new RedirectView("/adminhome");
            }

            // Kullanıcı giriş kontrolü
            if (authService.loginuser(username, password)) {
                return new RedirectView("/home");
            }

            // Giriş başarısızsa
            return new RedirectView("/login?error=true");
        } catch (AuthenticationException e) {
            return new RedirectView("/login?error=true");
        }
    }


}
