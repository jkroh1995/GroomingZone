package singleproject.demo.question;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import singleproject.demo.dto.SingleResponseDto;

import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/v1/board")
public class QuestionController {

    private final QuestionService service;

    public QuestionController(QuestionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity postQuestion(@RequestBody QuestionDto.Post questionPostDto) {
        QuestionDto.Response response = service.createQuestion(questionPostDto);
        return new ResponseEntity<>(new SingleResponseDto<>(response)
                , HttpStatus.CREATED);
    }

    @PatchMapping("/{question-id}")
    public ResponseEntity patchQuestion(@PathVariable("question-id") @Positive long questionId,
                                        @RequestBody QuestionDto.Patch questionPatchDto){

        QuestionDto.Response response = service.updateQuestion(questionId, questionPatchDto);
        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }

    @DeleteMapping("/{question-id}")
    public ResponseEntity deleteQuestion(@PathVariable("question-id") @Positive long questionId){
        service.deleteQuestion(questionId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
