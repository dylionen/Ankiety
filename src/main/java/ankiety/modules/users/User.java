package ankiety.modules.users;

import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(unique = true)
    private String userName;
    @Column(nullable = false)
    private String password;
    private Boolean active;
    //private String roles;


    private String firstName;
    private String lastName;
    @Column(nullable = false)
    private String mailAddress;


    private Timestamp createdDate;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public User(Long id, String userName, String password, Boolean active, String firstName, String lastName, String mailAddress, Set<Role> roles) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.active = active;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roles = roles;
        this.mailAddress = mailAddress;
        this.createdDate = new Timestamp(Calendar.getInstance().getTimeInMillis());
    }

    public User(UserRegistrationDTO dto, Set<Role> roles) {
        this.userName = dto.getUserName();
        this.password = new BCryptPasswordEncoder().encode(dto.getPassword());
        this.active = true;
        this.roles = roles;
        this.mailAddress = dto.getMailAddress();
        this.createdDate = new Timestamp(Calendar.getInstance().getTimeInMillis());
    }

}
