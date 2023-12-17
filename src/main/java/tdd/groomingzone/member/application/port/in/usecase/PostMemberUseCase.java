package tdd.groomingzone.member.application.port.in.usecase;

import tdd.groomingzone.member.application.port.in.MemberCommandResponse;
import tdd.groomingzone.member.application.port.in.command.PostMemberCommand;

public interface PostMemberUseCase {

    MemberCommandResponse postMember(PostMemberCommand command);
}
