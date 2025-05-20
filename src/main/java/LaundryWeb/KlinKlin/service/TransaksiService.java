package LaundryWeb.KlinKlin.service;

import LaundryWeb.KlinKlin.dto.TransaksiDTO;
import LaundryWeb.KlinKlin.model.Layanan;
import LaundryWeb.KlinKlin.model.Transaksi;
import LaundryWeb.KlinKlin.model.User;
import LaundryWeb.KlinKlin.repository.LayananRepository;
import LaundryWeb.KlinKlin.repository.TransaksiRepository;
import LaundryWeb.KlinKlin.repository.UserRepository;
import LaundryWeb.KlinKlin.util.MapperUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransaksiService {

    @Autowired
    private TransaksiRepository transaksiRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LayananRepository layananRepository;

    public TransaksiDTO save(TransaksiDTO dto) {
        User pelanggan = null;
        if (dto.getPelangganId() != null && !dto.getPelangganId().isEmpty()) {
            pelanggan = userRepository.findById(dto.getPelangganId())
                    .orElseThrow(() -> new RuntimeException("Pelanggan tidak ditemukan"));
        }

        User kasir = userRepository.findById(dto.getKasirId())
                .orElseThrow(() -> new RuntimeException("Kasir tidak ditemukan"));
        Layanan layanan = layananRepository.findById(dto.getLayananId())
                .orElseThrow(() -> new RuntimeException("Layanan tidak ditemukan"));

        Transaksi transaksi = MapperUtil.toEntity(dto, pelanggan, kasir, layanan);
        transaksi.setTanggalTransaksi(LocalDateTime.now());
        Transaksi saved = transaksiRepository.save(transaksi);
        return MapperUtil.toDTO(saved);
    }

    public TransaksiDTO findById(String id) {
        return transaksiRepository.findById(id)
                .map(MapperUtil::toDTO)
                .orElse(null);
    }

    public List<TransaksiDTO> findAll() {
        return transaksiRepository.findAll()
                .stream()
                .map(MapperUtil::toDTO)
                .collect(Collectors.toList());
    }

    public void deleteById(String id) {
        transaksiRepository.findById(id).ifPresent(transaksi -> {
            transaksi.setDeletedAt(LocalDateTime.now());
            transaksiRepository.save(transaksi);
        });
    }
}
