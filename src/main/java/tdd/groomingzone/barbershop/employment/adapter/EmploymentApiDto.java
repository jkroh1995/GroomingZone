package tdd.groomingzone.barbershop.employment.adapter;

import lombok.Builder;
import lombok.Getter;

public class EmploymentApiDto {

    @Getter
    public static class Response{
        private final Long workPlaceId;
        private final String workPlaceName;
        private final Long workerId;
        private final String workerNickName;

        @Builder
        private Response(Long workPlaceId, String workPlaceName, Long workerId, String workerNickName) {
            this.workPlaceId = workPlaceId;
            this.workPlaceName = workPlaceName;
            this.workerId = workerId;
            this.workerNickName = workerNickName;
        }
    }

    public static class Post {

    }
}
