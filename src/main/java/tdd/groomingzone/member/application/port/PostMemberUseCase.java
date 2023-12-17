package tdd.groomingzone.member.application.port;

import tdd.groomingzone.member.application.MemberCommandResponse;

public interface PostMemberUseCase {

    MemberCommandResponse postMember(PostMemberCommand command);
}
