package LaundryWeb.KlinKlin.repository;

import LaundryWeb.KlinKlin.model.Pembayaran;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PembayaranRepository extends JpaRepository<Pembayaran, String> {
    List<Pembayaran> findByTransaksi_Id(String transaksiId);
}
