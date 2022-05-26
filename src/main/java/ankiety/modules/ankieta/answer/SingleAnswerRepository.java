package ankiety.modules.ankieta.answer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SingleAnswerRepository extends JpaRepository<SingleAnswer,Long> {

    List<SingleAnswer> getSingleAnswersByQuestionOrderById(Question question);
}
