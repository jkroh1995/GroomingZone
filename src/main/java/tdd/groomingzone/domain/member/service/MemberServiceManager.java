package tdd.groomingzone.domain.member.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tdd.groomingzone.domain.member.entity.Member;
import tdd.groomingzone.domain.member.dto.MemberDto;
import tdd.groomingzone.domain.member.MemberService;
import tdd.groomingzone.global.exception.BusinessException;
import tdd.groomingzone.global.exception.ExceptionCode;

import java.util.List;

@Service
public class MemberServiceManager implements MemberService {

    private final MemberCommandService memberCommandService;
    private final MemberQueryService memberQueryService;
    private final MemberConverter memberConverter;
    private final PasswordEncoder passwordEncoder;
    private final MemberRolesGenerator memberRolesGenerator;

    public MemberServiceManager(MemberCommandService memberCommandService, MemberQueryService memberQueryService, MemberConverter memberConverter, PasswordEncoder passwordEncoder, MemberRolesGenerator memberRolesGenerator) {
        this.memberCommandService = memberCommandService;
        this.memberQueryService = memberQueryService;
        this.memberConverter = memberConverter;
        this.passwordEncoder = passwordEncoder;
        this.memberRolesGenerator = memberRolesGenerator;
    }

    @Override
    @Transactional
    public MemberDto.Response postMember(MemberDto.Post dto) {
        verifyExistsEmail(dto.email);
        dto.password = passwordEncoder.encode(dto.password);
        List<String> roles = memberRolesGenerator.generateMemberRoles(dto.email, dto.role);

        Member member = memberConverter.convertPostDtoToEntity(dto, roles);
        Member savedMember = memberCommandService.createMember(member);
        return memberConverter.convertEntityToResponseDto(savedMember);
    }

    private void verifyExistsEmail(String email) throws BusinessException{
        try {
            memberQueryService.readMemberByEmail(email);
        } catch (BusinessException be) {
            return;
        }
        throw new BusinessException(ExceptionCode.EMAIL_ALREADY_EXISTS);
    }
}
