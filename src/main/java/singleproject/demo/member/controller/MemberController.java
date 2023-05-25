package singleproject.demo.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import singleproject.demo.member.dto.MemberDto;
import singleproject.demo.member.service.MemberService;

@Controller
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/register")
    public String postMember(@ModelAttribute MemberDto.Post post) {
        memberService.createMember(post);
        return "redirect:/";
    }

    @GetMapping("/register")
    public String join() {
        return "register";
    }
}
