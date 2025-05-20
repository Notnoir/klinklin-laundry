package LaundryWeb.KlinKlin.repository;

import LaundryWeb.KlinKlin.model.Transaksi;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TransaksiRepository extends JpaRepository<Transaksi, String> {
    List<Transaksi> findAllByDeletedAtIsNull();

    List<Transaksi> findByPelanggan_IdAndDeletedAtIsNull(String pelangganId);
}
