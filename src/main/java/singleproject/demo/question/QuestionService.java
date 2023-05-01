package singleproject.demo.question;

import org.springframework.stereotype.Service;
import singleproject.demo.exception.BusinessLogicException;
import singleproject.demo.exception.ExceptionCode;
import singleproject.demo.member.Member;
import singleproject.demo.member.service.MemberService;

import java.util.Optional;

@Service
public class QuestionService {

    private final MemberService memberService;
    private final QuestionRepository repository;

    public QuestionService(MemberService memberService, QuestionRepository repository) {
        this.memberService = memberService;
        this.repository = repository;
    }

    public QuestionDto.Response createQuestion(QuestionDto.Post questionPostDto) {
        Member member = memberService.getMember(questionPostDto.getMemberId());
        Question question = questionPostDto.questionPostDtoToQuestion();
        question.setMember(member);

        Question savedQuestion = repository.save(question);

        return savedQuestion.questionToQuestionResponseDto();
    }

    public QuestionDto.Response updateQuestion(long questionId, QuestionDto.Patch questionPatchDto) {
        Question findQuestion = findVerifiedQuestion(questionId);
        verifyQuestionAlreadyAnswered(findQuestion);
        verifyAuthoredMember(questionPatchDto, findQuestion);
        findQuestion.setText(questionPatchDto.getText());
        changePolicy(questionPatchDto, findQuestion);

        repository.save(findQuestion);
        return findQuestion.questionToQuestionResponseDto();
    }

    public void deleteQuestion(long questionId) {
        findVerifiedQuestion(questionId);
        repository.removeByQuestionId(questionId);
    }

    private Question findVerifiedQuestion(long questionId) {
        Optional<Question> optionalQuestion = repository.findByQuestionId(questionId);
        return optionalQuestion.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));
    }

    private void verifyAuthoredMember(QuestionDto.Patch questionPatchDto, Question findQuestion) {
        if(findQuestion.getMember().getMemberId() != questionPatchDto.getMemberId()){
            throw new BusinessLogicException(ExceptionCode.NOT_AUTHORED_MEMBER);
        }
    }

    private void changePolicy(QuestionDto.Patch questionPatchDto, Question findQuestion) {
        int policyNum = questionPatchDto.getPolicyNum();
        if(policyNum!=0 && findQuestion.getPolicy().getNumber()!=policyNum){
            findQuestion.setPolicy(policyNum);
        }
    }

    private void verifyQuestionAlreadyAnswered(Question findQuestion) {
        if(findQuestion.getQuestionStatus() == Question.QuestionStatus.QUESTION_ANSWERED){
            throw new BusinessLogicException(ExceptionCode.ALREADY_ANSWERED_QUESTION);
        }
    }

}
