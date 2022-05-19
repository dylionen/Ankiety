package ankiety.modules.ankieta.answer;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;

    // pytania mogą być otwarte, zamknięte wielokrotnego wyboru bądą jednokrotnego
    private Boolean openQuestion;

    private Boolean multipleChoice;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "question_id")
    private Set<SingleAnswer> answers;

}
