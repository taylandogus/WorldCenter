package bulut.worldcenter.controller;

import bulut.worldcenter.model.Admin;
import bulut.worldcenter.model.Stock;
import bulut.worldcenter.model.User;
import bulut.worldcenter.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/registeradmin")
    public String registerAdmin(@ModelAttribute Admin admin) {

            adminService.registerAdmin(admin);
            return "redirect:/login";

    }


    @GetMapping("/adminregister")
    public String adminRegister(Model model) {
        model.addAttribute("admin", new Admin());
        return "adminregister";
    }

    @GetMapping("/adminhome")
    public String adminHome(Model model) {
        model.addAttribute("stock", new Stock());
        model.addAttribute("admin", new Admin());
        return "adminhome";
    }







}
