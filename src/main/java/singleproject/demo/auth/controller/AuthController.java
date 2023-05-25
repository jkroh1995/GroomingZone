package singleproject.demo.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import singleproject.demo.auth.LoginDto;

@Controller
@RequestMapping("/login")
public class AuthController {

    @GetMapping
    public String getLoginPage(){
        return "login";
    }

    @PostMapping
    public String login(LoginDto loginDto){
        return "home";
    }
}
