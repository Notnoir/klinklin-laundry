package LaundryWeb.KlinKlin.service;

import LaundryWeb.KlinKlin.model.Pembayaran;
import LaundryWeb.KlinKlin.model.Transaksi;
import LaundryWeb.KlinKlin.repository.PaymentHistoryRepository;
import LaundryWeb.KlinKlin.repository.PembayaranRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import LaundryWeb.KlinKlin.repository.TransaksiRepository;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PembayaranService {
    private final PembayaranRepository pembayaranRepository;
    private final TransaksiRepository transaksiRepository;
    private final PaymentHistoryRepository paymentHistoryRepository;

    public Pembayaran buatPembayaran(String transaksiId, BigDecimal totalBayar) {
        Transaksi transaksi = transaksiRepository.findById(transaksiId)
                .filter(t -> t.getDeletedAt() == null)
                .orElseThrow(() -> new RuntimeException("Transaksi tidak ditemukan"));

        // Validasi: apakah sudah pernah dibayar
        List<Pembayaran> existing = pembayaranRepository.findByTransaksi_Id(transaksiId);
        if (!existing.isEmpty()) {
            throw new RuntimeException("Transaksi ini sudah memiliki pembayaran");
        }

        Pembayaran pembayaran = new Pembayaran();
        pembayaran.setId(UUID.randomUUID().toString());
        pembayaran.setTransaksi(transaksi);
        pembayaran.setTotalBayar(totalBayar);
        pembayaran.setWaktuBayar(LocalDateTime.now());

        return pembayaranRepository.save(pembayaran);
    }

    public List<Pembayaran> getByTransaksi(String transaksiId) {
        return pembayaranRepository.findByTransaksi_Id(transaksiId);
    }

    public Optional<Pembayaran> getById(String id) {
        return pembayaranRepository.findById(id);
    }
}
