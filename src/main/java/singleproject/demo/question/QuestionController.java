package singleproject.demo.question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import singleproject.demo.dto.MultiResponseDto;
import singleproject.demo.dto.SingleResponseDto;

import javax.validation.constraints.Positive;
import java.util.List;

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
    @GetMapping("/{question-id}")
    public ResponseEntity getQuestion(@PathVariable("question-id") @Positive long questionId){
        QuestionDto.Response response = service.findQuestion(questionId);
        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getQuestions(@Positive @RequestParam int page,
                                       @Positive @RequestParam int size){
        Page<Question> questionPages = service.findQuestionPages(page-1, size);
        List<QuestionDto.Response> responses = service.findQuestions(page-1, size);
        return new ResponseEntity<>(new MultiResponseDto<>(responses, questionPages)
                ,HttpStatus.OK);
    }

    @DeleteMapping("/{question-id}")
    public ResponseEntity deleteQuestion(@PathVariable("question-id") @Positive long questionId){
        service.deleteQuestion(questionId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
