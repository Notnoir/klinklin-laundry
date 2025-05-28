package LaundryWeb.KlinKlin.service;

import LaundryWeb.KlinKlin.model.User;
import LaundryWeb.KlinKlin.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import LaundryWeb.KlinKlin.dto.UserDTO;
import LaundryWeb.KlinKlin.util.MapperUtil;
import LaundryWeb.KlinKlin.model.User.Role;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDTO save(UserDTO userDto) {
        User user = MapperUtil.toEntity(userDto);

        // Hash password sebelum simpan
        String hashedPassword = passwordEncoder.encode(userDto.getPassword());
        user.setPassword(hashedPassword);

        User savedUser = userRepository.save(user);
        return MapperUtil.toDTO(savedUser);
    }

    public UserDTO findById(String id) {
        return userRepository.findById(id)
                .map(MapperUtil::toDTO)
                .orElse(null);
    }

    public List<UserDTO> findAll() {
        return userRepository.findAllByDeletedAtIsNull()
                .stream()
                .map(MapperUtil::toDTO)
                .collect(Collectors.toList());
    }

    public UserDTO update(String id, UserDTO userDto) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    // Update field yang boleh diubah, contoh:
                    existingUser.setFullName(userDto.getFullName());
                    existingUser.setEmail(userDto.getEmail());
                    existingUser.setUsername(userDto.getUsername());
                    existingUser.setRole(userDto.getRole());

                    // Kalau password ingin update, harus hashing dulu (contoh di bawah):
                    if (userDto.getPassword() != null && !userDto.getPassword().isBlank()) {
                        // Misal pakai BCryptPasswordEncoder
                        String hashedPassword = passwordEncoder.encode(userDto.getPassword());
                        existingUser.setPassword(hashedPassword);
                    }
                    User saved = userRepository.save(existingUser);
                    return MapperUtil.toDTO(saved);
                })
                .orElseThrow(() -> new RuntimeException("User dengan id " + id + " tidak ditemukan"));
    }

    public void deleteById(String id) {
        userRepository.findById(id).ifPresent(user -> {
            user.setDeletedAt(LocalDateTime.now());
            userRepository.save(user);
        });
    }

    public User findByEmail(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        return userOpt.orElse(null);
    }

    public List<User> getAllPelanggan() {
        return userRepository.findByRole(Role.PELANGGAN);
    }

    public User getById(String id) {
        return userRepository.findById(id).orElse(null);
    }

}
