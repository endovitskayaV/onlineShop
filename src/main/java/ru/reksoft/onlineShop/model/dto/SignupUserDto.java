package ru.reksoft.onlineShop.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.reksoft.onlineShop.validating.passwordMatch.PasswordMatch;
import ru.reksoft.onlineShop.validating.passwordMatch.PasswordsProvider;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@PasswordMatch
public class SignupUserDto implements PasswordsProvider{

    //TODO: make up regexp
    @NotBlank(message = "Enter email")
    @Email(message = "Enter valid email")//(regexp = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$" ,message = "Enter valid email")
    private String email;

    @Size(min = 5, message = "Password must be at least 5 characters length")
    private String password;

    private String confirmPassword;

    private long roleId;
}
