package LaundryWeb.KlinKlin.repository;

import LaundryWeb.KlinKlin.model.Pembayaran;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface PembayaranRepository extends JpaRepository<Pembayaran, String> {
    List<Pembayaran> findByTransaksi_Id(String transaksiId);

    @Query("SELECT SUM(p.totalBayar) FROM Pembayaran p WHERE p.transaksi.id = :transaksiId")
    BigDecimal sumTotalBayarByTransaksiId(@Param("transaksiId") String transaksiId);

    Optional<Pembayaran> findByTransaksiIdAndStatus(String transaksiId, Pembayaran.StatusPembayaran status);

    List<Pembayaran> findByTransaksiPelangganEmail(String email);

    List<Pembayaran> findAllByTransaksiPelangganId(String pelangganId);

}
