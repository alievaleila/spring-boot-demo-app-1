package az.edu.turing.springbootdemoapp1.domain.repository;

import az.edu.turing.springbootdemoapp1.domain.entity.UserEntity;
import az.edu.turing.springbootdemoapp1.model.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserRepository {

    private final AtomicLong id = new AtomicLong();

    private static final Set<UserEntity> USERS = new HashSet<>();

    public Set<UserEntity> findAll() {
        return USERS;

    }

    public boolean existsByUsername(String userName) {
        return USERS.stream()
                .anyMatch(user -> user.getUsername().equals(userName));
    }

    public boolean existsById(Long id) {
        return USERS.stream()
                .anyMatch(user -> user.getId().equals(id));
    }

    public UserEntity save(UserEntity userEntity) {
        if (userEntity.getId() == null) {
            userEntity.setId(id.incrementAndGet());
            USERS.add(userEntity);
            return userEntity;
        } else {
            return null;
        }
    }

    public Optional<UserEntity> findByUsername(String username) {
        return USERS.stream()
                .filter(userEntity -> userEntity.getUsername().equals(username))
                .findFirst();
    }

    public Optional<UserEntity> findById(Long id) {
        return USERS.stream()
                .filter(userEntity -> userEntity.getId().equals(id))
                .findFirst();
    }

    public void deleteById(Long id) {
        USERS.stream()
                .filter(userEntity -> userEntity.getId().equals(id))
                .findFirst()
                .ifPresent(USERS::remove);

    }
}
