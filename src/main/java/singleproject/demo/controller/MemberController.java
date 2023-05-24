package singleproject.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import singleproject.demo.dto.MemberDto;

@Controller
@RequestMapping("/member")
public class MemberController {

    @PostMapping("/register")
    public String createMember(MemberDto.Post post) {
        return "home";
    }

    @GetMapping("/register")
    public String join() {
        return "register";
    }
}
