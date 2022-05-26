package ankiety.modules.ankieta.answer;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QuestionService {
    public final QuestionRepository questionRepository;
    public final SingleAnswerService singleAnswerService;

    public QuestionService(QuestionRepository questionRepository, SingleAnswerService singleAnswerService) {
        this.questionRepository = questionRepository;
        this.singleAnswerService = singleAnswerService;
    }

    public Question getQuestionById(Long id) {
        return questionRepository.getById(id);
    }

    public void saveQuestion(Question question) {
        questionRepository.save(question);
    }

    public SingleAnswer getSingleAnswerById(Long id) {
        return singleAnswerService.getSingleAnswer(id);
    }

    public void saveSingleAnswer(SingleAnswer singleAnswer) {
        singleAnswerService.singleAnswerRepository.save(singleAnswer);
    }

    @Transactional
    public void deteleSingleAnswer(SingleAnswer singleAnswer) {
        singleAnswerService.singleAnswerRepository.delete(singleAnswer);
    }
}
