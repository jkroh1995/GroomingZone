package singleproject.demo.user;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository repository;

    public MemberService(MemberRepository repository) {
        this.repository = repository;
    }

    public void createMember(MemberPostDto memberPostDto) {
        verifyEmail(memberPostDto.getEmail());
        Member member = initUser(memberPostDto);

        repository.save(member);
    }

    private Member initUser(MemberPostDto memberPostDto) {
        Member user = memberPostDto.memberPostDtoToMember();
        user.setStamp(new Stamp());
        return user;
    }

    private void verifyEmail(String email) {
        Optional<Member> findUser = repository.findByEmail(email);
        if(findUser.isPresent()){
            throw new BusinessLogicException(ExceptionCode.EMAIL_ALREADY_EXISTS);
        }
    }
}
