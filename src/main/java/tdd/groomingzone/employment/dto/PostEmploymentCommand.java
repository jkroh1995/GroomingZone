package tdd.groomingzone.employment.dto;

import lombok.Data;

@Data
public final class PostEmploymentCommand {
    private final String requestMemberEmail;
    private final Long barberShopId;
    private final Long workerId;

    public PostEmploymentCommand(String requestMemberEmail, Long barberShopId, Long workerId) {
        this.requestMemberEmail = requestMemberEmail;
        this.barberShopId = barberShopId;
        this.workerId = workerId;
    }

    public static PostEmploymentCommand of(String requestMemberEmail, long barberShopId, Long workerId) {
        return new PostEmploymentCommand(requestMemberEmail, barberShopId, workerId);
    }
}
