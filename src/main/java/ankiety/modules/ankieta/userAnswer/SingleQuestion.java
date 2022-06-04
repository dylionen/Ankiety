package ankiety.modules.ankieta.userAnswer;

import ankiety.modules.ankieta.answer.Question;
import ankiety.modules.ankieta.answer.SingleAnswer;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SingleQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //id pytania
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    //jesli odpowiedz otwarta
    private String openQuestionAnsewer;


    // jesli ma jedna i wiele
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "single_question_answers",
            joinColumns = @JoinColumn(name = "single_question_id"),
            inverseJoinColumns = @JoinColumn(name = "single_answer_id")
    )
    private Set<SingleAnswer> answers;

}
