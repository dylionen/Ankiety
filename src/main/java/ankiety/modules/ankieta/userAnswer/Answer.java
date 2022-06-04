package ankiety.modules.ankieta.userAnswer;

import ankiety.modules.ankieta.link.Link;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Timestamp completionDate;

    @OneToOne(cascade = {CascadeType.ALL})
    private Link link;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "answer_id")
    @OrderBy("id ASC")
    private Set<SingleQuestion> questions;

    public Answer() {
        this.completionDate = new Timestamp(System.currentTimeMillis());

    }
}
