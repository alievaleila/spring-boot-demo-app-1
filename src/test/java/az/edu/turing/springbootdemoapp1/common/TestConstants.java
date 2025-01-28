package az.edu.turing.springbootdemoapp1.common;

import az.edu.turing.springbootdemoapp1.domain.entity.UserEntity;
import az.edu.turing.springbootdemoapp1.model.dto.UserDto;
import az.edu.turing.springbootdemoapp1.model.enums.UserStatus;


public interface TestConstants {

    Long ID = 1L;
    String USERNAME = "admin@turing.edu.az";
    String PASSWORD = "Admin1234!";

    UserEntity USER_ENTITY = UserEntity.builder()
            .id(ID)
            .username(USERNAME)
            .password(PASSWORD)
            .status(UserStatus.ACTIVE)
            .build();

    UserDto USER_DTO = UserDto.builder()
            .id(ID)
            .username(USERNAME)
            .password(PASSWORD)
            .status(UserStatus.ACTIVE)
            .build();

}
