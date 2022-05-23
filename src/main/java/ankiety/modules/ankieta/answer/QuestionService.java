package ankiety.modules.ankieta.answer;

import org.springframework.stereotype.Service;

@Service
public class QuestionService {
    public final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Question getQuestionById(Long id) {
        return questionRepository.getById(id);
    }
}
