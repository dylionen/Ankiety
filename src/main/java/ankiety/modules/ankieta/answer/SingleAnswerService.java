package ankiety.modules.ankieta.answer;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SingleAnswerService {
    public final SingleAnswerRepository singleAnswerRepository;


    public SingleAnswerService(SingleAnswerRepository singleAnswerRepository) {
        this.singleAnswerRepository = singleAnswerRepository;
    }

    public List<SingleAnswer> getSingleAnswersByQuestion(Question question){
        return singleAnswerRepository.getSingleAnswersByQuestionOrderById(question);
    }

    public SingleAnswer getSingleAnswer(Long id){
        return singleAnswerRepository.getById(id);
    }
}
