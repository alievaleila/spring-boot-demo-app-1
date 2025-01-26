package az.edu.turing.springbootdemoapp1.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {

    @Email
    private String username;

    @Pattern(regexp = ("([A-Z])+"))
    private String password;

    @NotBlank
    private String confirmPassword;

}
