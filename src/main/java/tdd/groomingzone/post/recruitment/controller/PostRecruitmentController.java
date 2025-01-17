package tdd.groomingzone.post.recruitment.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tdd.groomingzone.post.recruitment.dto.RecruitmentApiDto;
import tdd.groomingzone.post.recruitment.dto.SingleRecruitmentResponse;
import tdd.groomingzone.post.recruitment.dto.PostRecruitmentCommand;
import tdd.groomingzone.post.recruitment.service.PostRecruitmentUseCase;

@RestController
@RequestMapping("/recruitment")
public class PostRecruitmentController {

    private final PostRecruitmentUseCase postRecruitmentUseCase;

    public PostRecruitmentController(PostRecruitmentUseCase postRecruitmentUseCase) {
        this.postRecruitmentUseCase = postRecruitmentUseCase;
    }

    @PostMapping
    public ResponseEntity<RecruitmentApiDto.Response> postRecruitment(@AuthenticationPrincipal UserDetails writer,
                                                                      @RequestBody RecruitmentApiDto.Post postDto){
        PostRecruitmentCommand postRecruitmentCommand = PostRecruitmentCommand.of(writer.getUsername(), postDto.getTitle(), postDto.getContent(), postDto.getType());
        SingleRecruitmentResponse commandResponse = postRecruitmentUseCase.postRecruitment(postRecruitmentCommand);
        RecruitmentApiDto.Response responseDto = RecruitmentApiDto.Response.of(commandResponse);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }
}
