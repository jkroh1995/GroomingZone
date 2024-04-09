package tdd.groomingzone.barbershop.employment.adapter;

import lombok.Builder;
import lombok.Getter;

public class EmploymentApiDto {

    @Getter
    public static class Response{
        private final Long workerId;
        private final String workerNickName;
        private final String workerProfileImage;

        @Builder
        private Response(Long workerId, String workerNickName, String workerProfileImage) {
            this.workerId = workerId;
            this.workerNickName = workerNickName;
            this.workerProfileImage = workerProfileImage;
        }
    }

    public static class Post {

    }
}
