package az.edu.turing.springbootdemoapp1.domain.entity;

import az.edu.turing.springbootdemoapp1.model.enums.UserStatus;
import lombok.*;

@Data
//@ToString (exclude="password")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    private Long id;
    private String username;

    @ToString.Exclude
    private String password;
    private UserStatus status;
}
