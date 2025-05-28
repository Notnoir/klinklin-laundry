package LaundryWeb.KlinKlin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import LaundryWeb.KlinKlin.model.User;
import LaundryWeb.KlinKlin.model.User.Role;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmailAndDeletedAtIsNull(String email);

    Optional<User> findByEmail(String email);

    List<User> findAllByDeletedAtIsNull();

    List<User> findAllByRole(Role kasir);

    List<User> findByRole(Role role);
}
