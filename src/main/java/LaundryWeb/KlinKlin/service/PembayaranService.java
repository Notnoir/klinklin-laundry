package LaundryWeb.KlinKlin.service;

import LaundryWeb.KlinKlin.model.Pembayaran;
import LaundryWeb.KlinKlin.repository.PembayaranRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import LaundryWeb.KlinKlin.repository.TransaksiRepository;

@Service
@RequiredArgsConstructor
public class PembayaranService {

    private final PembayaranRepository pembayaranRepository;
    private final TransaksiRepository transaksiRepository;

    public List<Pembayaran> getByTransaksi(String transaksiId) {
        return pembayaranRepository.findByTransaksi_Id(transaksiId);
    }

    public Pembayaran save(Pembayaran pembayaran) {
        pembayaran.setId(UUID.randomUUID().toString());
        pembayaran.setWaktuBayar(LocalDateTime.now());
        return pembayaranRepository.save(pembayaran);
    }
}
