package LaundryWeb.KlinKlin.service;

import LaundryWeb.KlinKlin.model.Layanan;
import LaundryWeb.KlinKlin.repository.LayananRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LayananService {

    private final LayananRepository layananRepository;

    public List<Layanan> getAllActive() {
        return layananRepository.findAllByDeletedAtIsNull();
    }

    public Optional<Layanan> getById(String id) {
        return layananRepository.findById(id)
                .filter(l -> l.getDeletedAt() == null);
    }

    public Layanan save(Layanan layanan) {
        layanan.setId(UUID.randomUUID().toString());
        return layananRepository.save(layanan);
    }

    public void softDelete(String id) {
        layananRepository.findById(id).ifPresent(l -> {
            l.setDeletedAt(LocalDateTime.now());
            layananRepository.save(l);
        });
    }
}
