package team.avgmax.rabbit.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login/google")
    public String loginWithGoogle() {
        return "redirect:/oauth2/authorization/google";
    }

    @GetMapping("/login/naver")
    public String loginWithNaver() {
        return "redirect:/oauth2/authorization/naver";
    }
}