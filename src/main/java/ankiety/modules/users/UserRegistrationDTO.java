package ankiety.modules.users;

import lombok.*;

import javax.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserRegistrationDTO {

    @NotEmpty(message = "Username must not be empty")
    @Size(min = 4, max = 64,message = "Username characters must have min 4 digits and max 64")
    private String userName;

    @NotEmpty(message = "Password must not be empty")
    @Size(min = 8, max = 64,message = "Password characters must have min 8 digits and max 64")
    private String password;

    @NotEmpty(message = "Password must not be empty")
    @Size(min = 8, max = 64,message = "Password characters must have min 8 digits and max 64")
    private String repeatPassword;

    @NotEmpty(message = "Email address must not be empty")
    @Email(message = "Email should be valid")
    private String mailAddress;

    @NotEmpty(message = "Role must not empty")
    private String role;

    @AssertTrue(message = "Password should match")
    public boolean isPasswordsEqual() {
        return password.equals(repeatPassword);
    }

}
