package tdd.groomingzone.member.adapter.in.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tdd.groomingzone.member.application.port.PostMemberCommand;
import tdd.groomingzone.member.application.MemberCommandResponse;
import tdd.groomingzone.member.adapter.dto.MemberApiDto;
import tdd.groomingzone.member.application.port.PostMemberUseCase;

@RestController
@RequestMapping("/member")
public class PostMemberController {

    private final PostMemberUseCase postMemberUseCase;

    public PostMemberController(PostMemberUseCase postMemberUseCase) {
        this.postMemberUseCase = postMemberUseCase;
    }

    @PostMapping
    public ResponseEntity<MemberApiDto.Response> postMember(@RequestBody MemberApiDto.Post dto){
        PostMemberCommand postMemberCommand = PostMemberCommand.of(dto.getEmail(),
                dto.getPassword(),
                dto.getNickName(),
                dto.getPhoneNumber(),
                dto.getRole());
        MemberCommandResponse commandResponse = postMemberUseCase.postMember(postMemberCommand);
        MemberApiDto.Response responseDto = MemberApiDto.Response.builder()
                .email(commandResponse.getEmail())
                .nickName(commandResponse.getNickName())
                .phoneNumber(commandResponse.getPhoneNumber())
                .role(commandResponse.getRole())
                .build();
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }
}
