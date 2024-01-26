package tdd.groomingzone.member.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tdd.groomingzone.global.exception.BusinessException;
import tdd.groomingzone.global.exception.ExceptionCode;
import tdd.groomingzone.member.application.port.out.LoadMemberPort;
import tdd.groomingzone.member.application.port.in.usecase.PostMemberUseCase;
import tdd.groomingzone.member.application.port.in.command.PostMemberCommand;
import tdd.groomingzone.member.application.port.in.MemberCommandResponse;
import tdd.groomingzone.member.application.port.out.SaveMemberPort;
import tdd.groomingzone.member.domain.Member;

import java.time.LocalDateTime;

@Service
public class PostMemberService implements PostMemberUseCase {

    private final SaveMemberPort saveMemberPort;
    private final LoadMemberPort loadMemberPort;

    public PostMemberService(SaveMemberPort saveMemberPort, LoadMemberPort loadMemberPort) {
        this.saveMemberPort = saveMemberPort;
        this.loadMemberPort = loadMemberPort;
    }

    @Override
    @Transactional
    public MemberCommandResponse postMember(PostMemberCommand command) {
        verifyEmailDuplicate(command.getEmail());
        LocalDateTime createAt = LocalDateTime.now();
        Member member = Member.builder()
                .memberId(0)
                .email(command.getEmail())
                .password(command.getPassword())
                .nickName(command.getNickName())
                .phoneNumber(command.getPhoneNumber())
                .role(command.getRole())
                .createdAt(createAt)
                .modifiedAt(createAt)
                .provider("SERVER")
                .profileImageUrl(command.getProfileImageUrl())
                .build();
        Member savedMember = saveMemberPort.save(member);
        return MemberCommandResponse.of(savedMember.getMemberId(),
                savedMember.getEmail(),
                savedMember.getNickName(),
                savedMember.getPhoneNumber(),
                savedMember.getRole(),
                savedMember.getProvider(),
                savedMember.getProfileImageUrl());
    }

    private void verifyEmailDuplicate(String requestEmail) {
        if(loadMemberPort.findOptionalMemberByEmail(requestEmail).isPresent()){
            throw new BusinessException(ExceptionCode.EMAIL_ALREADY_EXISTS);
        }
    }
}
