package singleproject.demo.question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import singleproject.demo.exception.BusinessLogicException;
import singleproject.demo.exception.ExceptionCode;
import singleproject.demo.member.Member;
import singleproject.demo.member.service.MemberService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        verifyAuthoredMember(findQuestion.getMember().getMemberId(), questionId);
        findQuestion.setText(questionPatchDto.getText());
        changePolicy(questionPatchDto, findQuestion);

        repository.save(findQuestion);
        return findQuestion.questionToQuestionResponseDto();
    }

    public void deleteQuestion(long questionId) {
        Question question = findVerifiedQuestion(questionId);
        question.setQuestionStatus(Question.QuestionStatus.QUESTION_DELETED);
    }

    public QuestionDto.Response findQuestion(long questionId) {
        Question question = findVerifiedQuestion(questionId);
        if(question.getQuestionStatus() == Question.QuestionStatus.QUESTION_DELETED){
            throw new BusinessLogicException(ExceptionCode.QUESTION_DELETED);
        }
        if(question.getPolicy() == Question.PublicPolicy.SECRET){
            verifyAuthoredMember(question.getMember().getMemberId(), questionId);
        }
        return question.questionToQuestionResponseDto();
    }

    public List<QuestionDto.Response> findQuestions(int page, int size) {
        Page<Question> pageQuestions = findQuestionPages(page, size);
        List<Question> questions = pageQuestions.getContent();
        return questions.stream()
                .map(question ->
                    new QuestionDto.Response(
                            question.getQuestionId(),
                            question.getMember().getMemberId(),
                            question.getHeader(),
                            question.getText(),
                            question.getPolicy(),
                            question.getComments()
                    )
                )
                .collect(Collectors.toList());
    }

    public Page<Question> findQuestionPages(int page, int size) {
        return repository.findAll(PageRequest.of(page, size));
    }

    private Question findVerifiedQuestion(long questionId) {
        Optional<Question> optionalQuestion = repository.findByQuestionId(questionId);
        return optionalQuestion.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));
    }

    private void verifyAuthoredMember(long memberIdOfFindQuestion, long memberIdOfRequest) {
        if(memberIdOfFindQuestion != memberIdOfRequest){
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
            throw new BusinessLogicException(ExceptionCode.QUESTION_ALREADY_ANSWERED);
        }
    }
}
