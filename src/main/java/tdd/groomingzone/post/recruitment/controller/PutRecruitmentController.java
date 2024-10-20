package tdd.groomingzone.post.recruitment.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import tdd.groomingzone.global.time.Time;
import tdd.groomingzone.post.recruitment.dto.RecruitmentApiDto;
import tdd.groomingzone.post.recruitment.dto.SingleRecruitmentResponse;
import tdd.groomingzone.post.recruitment.dto.PutRecruitmentCommand;
import tdd.groomingzone.post.recruitment.service.PutRecruitmentUseCase;

@RestController
@RequestMapping("/recruitment")
public class PutRecruitmentController {

    private final PutRecruitmentUseCase putRecruitmentUseCase;
    private final Time time;

    public PutRecruitmentController(PutRecruitmentUseCase putRecruitmentUseCase, Time time) {
        this.putRecruitmentUseCase = putRecruitmentUseCase;
        this.time = time;
    }

    @PutMapping("/{recruitment-id}")
    public ResponseEntity<RecruitmentApiDto.Response> putRecruitment(@AuthenticationPrincipal UserDetails requestMember,
                                                                     @PathVariable("recruitment-id") long recruitmentId,
                                                                     @RequestBody RecruitmentApiDto.Put putDto) {
        PutRecruitmentCommand putRecruitmentCommand = PutRecruitmentCommand.of(putDto.getTitle(), putDto.getContent(), requestMember.getUsername(), recruitmentId, time.now());
        SingleRecruitmentResponse commandResponse = putRecruitmentUseCase.putRecruitment(putRecruitmentCommand);
        RecruitmentApiDto.Response responseDto = RecruitmentApiDto.Response.of(commandResponse);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
