package bulut.worldcenter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/hakkimizda")
    public String hakkimizda(){
        return "hakkimizda";
    }

    @GetMapping("/iletisim")
    public String iletisim(){
        return "iletisim";
    }

    @GetMapping("/yardim")
    public String yardim(){
        return "yardim";
    }

    @GetMapping("/hizmetlerimiz")
    public String hizmetlerimiz(){
        return "hizmetlerimiz";
    }
}
