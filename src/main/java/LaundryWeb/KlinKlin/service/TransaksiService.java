package LaundryWeb.KlinKlin.service;

import LaundryWeb.KlinKlin.model.Transaksi;
import LaundryWeb.KlinKlin.repository.TransaksiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransaksiService {

    private final TransaksiRepository transaksiRepository;

    public List<Transaksi> getAll() {
        return transaksiRepository.findAllByDeletedAtIsNull();
    }

    public Optional<Transaksi> getById(String id) {
        return transaksiRepository.findById(id)
                .filter(t -> t.getDeletedAt() == null);
    }

    public Transaksi save(Transaksi transaksi) {
        transaksi.setId(UUID.randomUUID().toString());
        transaksi.setTanggalTransaksi(LocalDateTime.now());
        return transaksiRepository.save(transaksi);
    }

    public void softDelete(String id) {
        transaksiRepository.findById(id).ifPresent(t -> {
            t.setDeletedAt(LocalDateTime.now());
            transaksiRepository.save(t);
        });
    }
}
