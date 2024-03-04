package tdd.groomingzone.barbershop.employment.application.dto;

import lombok.Data;

@Data
public final class PostEmploymentResponse {
    private final Long workPlaceId;
    private final String workPlaceName;
    private final Long workerId;
    private final String workerNickName;

    public PostEmploymentResponse(Long workPlaceId, String workPlaceName, Long workerId, String workerNickName) {
        this.workPlaceId = workPlaceId;
        this.workPlaceName = workPlaceName;
        this.workerId = workerId;
        this.workerNickName = workerNickName;
    }

    public static PostEmploymentResponse of(Long workPlaceId, String workPlaceName, Long workerId, String workerNickName) {
        return new PostEmploymentResponse(workPlaceId, workPlaceName, workerId, workerNickName);
    }
}
