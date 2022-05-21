package ankiety.modules.ankieta.header;

import ankiety.modules.ankieta.answer.Question;
import ankiety.modules.ankieta.answer.SingleAnswer;
import ankiety.modules.users.User;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Header {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Timestamp addedDate;

    private Timestamp modifiedDate;

    private Boolean createMode;

    private String title;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "header_id")
    private Set<Question> questions;


}
