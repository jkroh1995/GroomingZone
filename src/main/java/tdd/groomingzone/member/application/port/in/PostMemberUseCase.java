package tdd.groomingzone.member.application.port.in;

public interface PostMemberUseCase {

    MemberCommandResponse postMember(PostMemberCommand command);
}
