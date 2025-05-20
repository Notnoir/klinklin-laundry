package LaundryWeb.KlinKlin.service;

import LaundryWeb.KlinKlin.model.Transaksi;
import LaundryWeb.KlinKlin.model.TransaksiLog;
import LaundryWeb.KlinKlin.repository.TransaksiLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransaksiLogService {

    private final TransaksiLogRepository transaksiLogRepository;

    public List<TransaksiLog> getByTransaksi(String transaksiId) {
        return transaksiLogRepository.findByTransaksi_IdOrderByTanggalLogDesc(transaksiId);
    }

    public TransaksiLog log(Transaksi transaksi, String aksi, String deskripsi) {
        TransaksiLog log = new TransaksiLog();
        log.setId(UUID.randomUUID().toString());
        log.setTransaksi(transaksi);
        log.setAksi(aksi);
        log.setDeskripsi(deskripsi);
        log.setTanggalLog(LocalDateTime.now());
        return transaksiLogRepository.save(log);
    }
}
