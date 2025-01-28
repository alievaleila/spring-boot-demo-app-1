package az.edu.turing.springbootdemoapp1.service;

import az.edu.turing.springbootdemoapp1.domain.repository.UserRepository;
import az.edu.turing.springbootdemoapp1.exception.NotFoundException;
import az.edu.turing.springbootdemoapp1.mapper.UserMapper;
import az.edu.turing.springbootdemoapp1.model.dto.UserDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static az.edu.turing.springbootdemoapp1.common.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Spy
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @Test
    void findAll_Should_ReturnSuccess() {
        given(userRepository.findAll()).willReturn(Set.of(USER_ENTITY));
        Set<UserDto> users = userService.findAll();
        Assertions.assertNotNull(users);
        Assertions.assertFalse(users.isEmpty());
        assertEquals(Set.of(USER_DTO), users);

        then(userRepository).should(times(1)).findAll();

    }

    @Test
    void findByUsername_Should_ReturnSuccess() {
        given(userRepository.findByUsername(USERNAME)).willReturn(Optional.of(USER_ENTITY));

        UserDto userDto = userService.findByUsername(USERNAME);
        Assertions.assertNotNull(userDto);
        assertEquals(USER_DTO, userDto);
        then(userRepository).should(times(1)).findByUsername(USERNAME);

    }

    @Test
    void findByUsername_Should_ThrowNotFoundException_When_UsernameNotFound() {
        given(userRepository.findByUsername(USERNAME)).willReturn(Optional.empty());

        NotFoundException ex = assertThrows(NotFoundException.class, () -> userService.findByUsername(USERNAME));
        assertEquals("User with username admin@turing.edu.az not found!", ex.getMessage());

        then(userRepository).should(times(1)).findByUsername(USERNAME);
    }
}
