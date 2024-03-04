package tdd.groomingzone.barbershop.employment.adapter.in.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import tdd.groomingzone.barbershop.employment.adapter.EmploymentApiDto;
import tdd.groomingzone.barbershop.employment.application.dto.PostEmploymentCommand;
import tdd.groomingzone.barbershop.employment.application.dto.PostEmploymentResponse;
import tdd.groomingzone.barbershop.employment.application.port.in.PostEmploymentUseCase;

@RestController
@RequestMapping("/barber-shop/{barber-shop-id}/employment")
public class PostEmploymentController {

    private final PostEmploymentUseCase postEmploymentUseCase;

    public PostEmploymentController(PostEmploymentUseCase postEmploymentUseCase) {
        this.postEmploymentUseCase = postEmploymentUseCase;
    }

    @PostMapping("/{worker-id}")
    public ResponseEntity<EmploymentApiDto.Response> postEmploymentByOwner(@AuthenticationPrincipal UserDetails requestMember,
                                                                           @PathVariable("barber-shop-id") Long barberShopId,
                                                                           @PathVariable("worker-id") Long workerId) {
        PostEmploymentCommand command = PostEmploymentCommand.of(requestMember.getUsername(), barberShopId, workerId);
        PostEmploymentResponse response = postEmploymentUseCase.postEmployment(command);
        EmploymentApiDto.Response responseDto = EmploymentApiDto.Response.builder()
                .workPlaceId(response.getWorkPlaceId())
                .workPlaceName(response.getWorkPlaceName())
                .workerId(response.getWorkerId())
                .workerNickName(response.getWorkerNickName())
                .build();
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }
}
