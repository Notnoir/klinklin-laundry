package LaundryWeb.KlinKlin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import LaundryWeb.KlinKlin.model.StatusLog;

public interface StatusLogRepository extends JpaRepository<StatusLog, String> {
    List<StatusLog> findByTransaksi_IdOrderByWaktuAsc(String transaksiId);
}
