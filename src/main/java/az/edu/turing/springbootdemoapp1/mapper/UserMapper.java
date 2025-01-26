package az.edu.turing.springbootdemoapp1.mapper;

import az.edu.turing.springbootdemoapp1.domain.entity.UserEntity;
import az.edu.turing.springbootdemoapp1.model.dto.UserDto;
import az.edu.turing.springbootdemoapp1.model.dto.request.CreateUserRequest;
import az.edu.turing.springbootdemoapp1.model.enums.UserStatus;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto toDto(UserEntity userEntity) {
        return UserDto.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .status(userEntity.getStatus())
                .build();
    }

    public UserEntity toEntity(UserDto userDto) {
        return UserEntity.builder()
                .id(userDto.getId())
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .status(userDto.getStatus())
                .build();
    }

    public UserEntity toEntity(CreateUserRequest request) {
        return UserEntity.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .status(UserStatus.ACTIVE)
                .build();
    }
}
