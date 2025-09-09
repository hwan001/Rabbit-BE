package team.avgmax.rabbit.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class LoginController {

    @GetMapping("/login/{provider}")
    public String loginWithProvider(@PathVariable String provider) {
        return "redirect:/oauth2/authorization/" + provider;
    }
}
