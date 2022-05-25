package ankiety.modules.ankieta.answer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDTO {
    private Long id;
    private String query;
    private Boolean openQuestion;
    private Boolean multipleChoice;

    public QuestionDTO(Question question){
        this.id = question.getId();
        this.query = question.getQuery();
        this.openQuestion = question.getOpenQuestion();
        this.multipleChoice = question.getMultipleChoice();

    }
}
