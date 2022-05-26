package ankiety.modules.ankieta.answer;

import ankiety.modules.ankieta.header.Header;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String query;

    // pytania mogą być otwarte, zamknięte wielokrotnego wyboru bądą jednokrotnego
    private Boolean openQuestion;

    private Boolean multipleChoice;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false)
    @JoinColumn(name = "question_id")
    private Set<SingleAnswer> answers;

    @ManyToOne
    @JoinColumn(name = "header_id")
    private Header header;
}
