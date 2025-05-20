package LaundryWeb.KlinKlin.service;

import LaundryWeb.KlinKlin.model.User;
import LaundryWeb.KlinKlin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAllActiveUsers() {
        return userRepository.findAllByDeletedAtIsNull();
    }

    public Optional<User> getById(String id) {
        return userRepository.findById(id)
                .filter(user -> user.getDeletedAt() == null);
    }

    public User save(User user) {
        user.setId(UUID.randomUUID().toString());
        user.setCreatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    public void softDelete(String id) {
        userRepository.findById(id).ifPresent(user -> {
            user.setDeletedAt(LocalDateTime.now());
            userRepository.save(user);
        });
    }
}
