package singleproject.demo.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import singleproject.demo.dto.SingleResponseDto;
import singleproject.demo.member.dto.MemberDto;
import singleproject.demo.member.service.MemberService;

import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/v1/member")
public class MemberController {
    private final MemberService service;

    public MemberController(MemberService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity postMember(@RequestBody MemberDto.Post memberPostDto){
        MemberDto.Response responseDto = service.createMember(memberPostDto);
        return new ResponseEntity<>(new SingleResponseDto<>(responseDto), HttpStatus.OK);
    }

    @GetMapping("/{member-id}")
    public ResponseEntity getMember(@PathVariable("member-id") @Positive long memberId){
        return new ResponseEntity<>(
                new SingleResponseDto<>(service.findMember(memberId))
                , HttpStatus.OK);
    }
}
