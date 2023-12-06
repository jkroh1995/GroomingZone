package tdd.groomingzone.domain.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tdd.groomingzone.domain.member.MemberService;
import tdd.groomingzone.domain.member.dto.MemberDto;

@RestController
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<MemberDto.Response> postMember(@RequestBody MemberDto.Post dto){
        MemberDto.Response responseDto = memberService.postMember(dto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }
}
