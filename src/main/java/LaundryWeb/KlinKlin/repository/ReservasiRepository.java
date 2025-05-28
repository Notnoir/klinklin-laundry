package LaundryWeb.KlinKlin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import LaundryWeb.KlinKlin.model.Reservasi;

@Repository
public interface ReservasiRepository extends JpaRepository<Reservasi, String> {
    // Bisa tambah method custom jika perlu, misal findByUserId, dsb.
}