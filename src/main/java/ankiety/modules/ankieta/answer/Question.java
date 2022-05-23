package ankiety.modules.ankieta.answer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
