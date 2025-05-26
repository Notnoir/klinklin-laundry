package LaundryWeb.KlinKlin.repository;

import LaundryWeb.KlinKlin.model.Transaksi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface TransaksiRepository extends JpaRepository<Transaksi, String> {
    List<Transaksi> findAllByDeletedAtIsNull();

    List<Transaksi> findByPelanggan_IdAndDeletedAtIsNull(String pelangganId);

    int countByTanggalTransaksiBetweenAndDeletedAtIsNull(LocalDateTime start, LocalDateTime end);

    @Query("SELECT SUM(t.total) FROM Transaksi t WHERE t.tanggalTransaksi BETWEEN :start AND :end AND t.deletedAt IS NULL")
    BigDecimal sumTotalByTanggalTransaksiBetweenAndDeletedAtIsNull(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);

    int countByStatus(Transaksi.Status status);

    @Query(value = "SELECT * FROM transaksi WHERE deleted_at IS NULL ORDER BY tanggal_transaksi DESC", nativeQuery = true)
    List<Transaksi> findTopByDeletedAtIsNull(Pageable pageable);

}
