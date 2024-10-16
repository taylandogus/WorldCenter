package bulut.worldcenter.controller;

import bulut.worldcenter.model.Stock;
import bulut.worldcenter.model.User;
import bulut.worldcenter.model.UserStock;
import bulut.worldcenter.service.StockService;
import bulut.worldcenter.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private StockService stockService;


    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }


    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {
        userService.registerUser(user);
        return "redirect:/login";
    }

    @GetMapping("/")
    public String homepage(){
        return "homepage";
    }

    @GetMapping("/home")
    public String home(Model model, Principal principal) {
        String username = principal.getName();
        User user = userService.findByUsername(username).orElse(null);

        if (user != null) {
            model.addAttribute("name", user.getName());
            model.addAttribute("lastname", user.getLastname());
            model.addAttribute("email", user.getEmail());
            model.addAttribute("idnumber", user.getIdnumber());
            model.addAttribute("username", user.getUsername());
            model.addAttribute("balance", user.getBalance());
        }

        return "home";
    }


    @GetMapping("/login")
    public String login(Model model, Principal principal) {
        if (principal != null) {
            return "redirect:/home";
        }
        return "login";
    }



    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (authentication != null) {
            // Kullanıcıyı çıkış yaptır
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/login?logout";
    }

    @GetMapping("/adminallusers")
    public String getAllUsers(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "adminallusers";
    }

    @GetMapping("/edituser/{id}")
    public String editUser(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "edituser";
    }

    @PostMapping("/updateuser")
    public String updateUser(@ModelAttribute("user") User updatedUser) {
        User existingUser = userService.getUserById(updatedUser.getId());

        if(updatedUser.getUsername() != null && !updatedUser.getUsername().isEmpty()){
            existingUser.setUsername(updatedUser.getUsername());
        }
        if(updatedUser.getEmail() != null && !updatedUser.getEmail().isEmpty()){
            existingUser.setEmail(updatedUser.getEmail());
        }
        if(updatedUser.getBalance() != 0.0 ){
            existingUser.setBalance(updatedUser.getBalance());
        }
        userService.updateUser(existingUser);
        return "redirect:/adminallusers";

    }

    @PostMapping("/deleteuser/{id}")
    public String deleteUser(@PathVariable Long id, Model model) {
       userService.deleteUser(id);
       return "redirect:/adminallusers";
    }

    @GetMapping("/userstock")
    public String userStock(Model model, Principal principal) {
        String username = principal.getName();
        User user = userService.findByUsername(username).orElse(null);
        List<Stock> allStocks = stockService.getAllStocks();
        List<UserStock> userStocks = stockService.getAllUserStocks(username);

        if (user != null) {
            model.addAttribute("user", user);
            model.addAttribute("userStocks", userStocks);
        }

        model.addAttribute("stocks", allStocks);
        model.addAttribute("stock", new Stock());
        return "userstock";
    }










}
