package az.edu.turing.springbootdemoapp1.service;


import az.edu.turing.springbootdemoapp1.domain.entity.UserEntity;
import az.edu.turing.springbootdemoapp1.domain.repository.UserRepository;
import az.edu.turing.springbootdemoapp1.exception.AlreadyExistsException;
import az.edu.turing.springbootdemoapp1.exception.InvalidInputException;
import az.edu.turing.springbootdemoapp1.exception.NotFoundException;
import az.edu.turing.springbootdemoapp1.mapper.UserMapper;
import az.edu.turing.springbootdemoapp1.model.dto.UserDto;
import az.edu.turing.springbootdemoapp1.model.dto.request.CreateUserRequest;
import az.edu.turing.springbootdemoapp1.model.dto.request.UpdateUserRequest;
import az.edu.turing.springbootdemoapp1.model.enums.UserStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserService {

    private final UserMapper userMapper;

    private final UserRepository userRepository;

    public Set<UserDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toSet());
    }

    public UserDto create(CreateUserRequest request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new InvalidInputException("Passwords don't match");
        }
        existsByUsername(request.getUsername());
        UserEntity savedUserEntity = userRepository.save(userMapper.toEntity(request));
        log.info("User created: {}", savedUserEntity);
        return userMapper.toDto(savedUserEntity);

    }

    public UserDto findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::toDto)
                .orElseThrow(() -> new NotFoundException("User with username " + username + "not found!"));


    }

    public UserDto update(Long id, @Valid UpdateUserRequest request) {
        existsByUsername(request.getUsername());
        UserEntity userEntity = findById(id);
        userEntity.setUsername(request.getUsername());
        userEntity.setPassword(request.getPassword());
        UserEntity updatedUserEntity = userRepository.save(userEntity);
        return userMapper.toDto(updatedUserEntity);
    }

    public UserDto updateStatus(Long id, UserStatus status) {
        return userRepository.findById(id)
                .map(userEntity -> {
                    userEntity.setStatus(status);
                    UserEntity updatedUserEntity = userRepository.save(userEntity);
                    return userMapper.toDto(updatedUserEntity);
                })
                .orElseThrow(() -> new NotFoundException("User with username " + id + "not found!"));

    }

    public void deleteById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("User with id " + id + "not found!");
        }
        //soft delete
        userRepository.findById(id)
                .ifPresent(userEntity -> {
                    userEntity.setStatus(UserStatus.DELETED);
                    userRepository.save(userEntity);
                });
        //hard delete
        userRepository.deleteById(id);
    }

    private void existsByUsername(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new AlreadyExistsException("User already exists!");
        }
    }

    private UserEntity findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id " + id + "not found!"));
    }
}


