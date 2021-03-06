package ankiety.modules.ankieta.link;

import ankiety.modules.ankieta.header.Header;
import ankiety.modules.ankieta.userAnswer.Answer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String key;

    @ManyToOne
    @JoinColumn(name = "header_id")
    private Header header;

    @OneToOne(cascade = {CascadeType.ALL})
    private Answer answer;

    public Link() {
        this.key = new BCryptPasswordEncoder().encode(new Date().toString())
                .replace("$2a$10$","")
                .replace(".","")
                .replace(",","")
                .replace("/","");
    }

}
