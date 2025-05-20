package LaundryWeb.KlinKlin.repository;

import LaundryWeb.KlinKlin.model.Layanan;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LayananRepository extends JpaRepository<Layanan, String> {
    List<Layanan> findAllByDeletedAtIsNull();
}
