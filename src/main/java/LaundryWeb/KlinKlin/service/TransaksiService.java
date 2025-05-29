package LaundryWeb.KlinKlin.service;

import LaundryWeb.KlinKlin.dto.TransaksiDTO;
import LaundryWeb.KlinKlin.model.Layanan;
import LaundryWeb.KlinKlin.model.Transaksi;
import LaundryWeb.KlinKlin.model.User;
import LaundryWeb.KlinKlin.repository.LayananRepository;
import LaundryWeb.KlinKlin.repository.ReservasiRepository;
import LaundryWeb.KlinKlin.repository.TransaksiRepository;
import LaundryWeb.KlinKlin.repository.UserRepository;
import LaundryWeb.KlinKlin.util.MapperUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.LocalDate;
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

    @Autowired
    private ReservasiRepository reservasiRepository;

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

        BigDecimal hargaPerKg = layanan.getHargaPerKg();
        BigDecimal total = BigDecimal.ZERO;

        if (dto.getBeratKg() != null) {
            total = hargaPerKg.multiply(dto.getBeratKg());

            // Tambahkan biaya tetap Rp10.000 jika dari reservasi
            if (dto.getReservasiId() != null && !dto.getReservasiId().isEmpty()) {
                total = total.add(BigDecimal.valueOf(10000));
            }
        }

        dto.setTotal(total);

        if (dto.getStatus() == null || dto.getStatus().isEmpty()) {
            dto.setStatus("DITERIMA");
        }

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
        return transaksiRepository.findAllByDeletedAtIsNull()
                .stream()
                .map(MapperUtil::toDTO)
                .collect(Collectors.toList());
    }

    public TransaksiDTO update(TransaksiDTO dto) {
        Transaksi existing = transaksiRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Transaksi tidak ditemukan"));

        User pelanggan = null;
        if (dto.getPelangganId() != null && !dto.getPelangganId().isEmpty()) {
            pelanggan = userRepository.findById(dto.getPelangganId())
                    .orElseThrow(() -> new RuntimeException("Pelanggan tidak ditemukan"));
        }

        User kasir = userRepository.findById(dto.getKasirId())
                .orElseThrow(() -> new RuntimeException("Kasir tidak ditemukan"));

        Layanan layanan = layananRepository.findById(dto.getLayananId())
                .orElseThrow(() -> new RuntimeException("Layanan tidak ditemukan"));

        // Hitung ulang total jika null atau berat berubah
        boolean beratBerubah = dto.getBeratKg() != null
                && (existing.getBeratKg() == null || !dto.getBeratKg().equals(existing.getBeratKg()));
        boolean layananBerubah = dto.getLayananId() != null &&
                (existing.getLayanan() == null || !dto.getLayananId().equals(existing.getLayanan().getId()));

        if (dto.getTotal() == null || dto.getBeratKg() == null || beratBerubah || layananBerubah) {
            if (dto.getBeratKg() != null) {
                dto.setTotal(layanan.getHargaPerKg().multiply(dto.getBeratKg()));
            } else {
                dto.setTotal(null);
            }
        }

        if (dto.getStatus() == null || dto.getStatus().isEmpty()) {
            dto.setStatus(existing.getStatus() != null ? existing.getStatus().name() : null);
        }

        Transaksi updated = MapperUtil.toEntity(dto, pelanggan, kasir, layanan);
        updated.setTanggalTransaksi(existing.getTanggalTransaksi());
        updated.setDeletedAt(existing.getDeletedAt());

        Transaksi saved = transaksiRepository.save(updated);
        return MapperUtil.toDTO(saved);
    }

    public void deleteById(String id) {
        transaksiRepository.findById(id).ifPresent(transaksi -> {
            transaksi.setDeletedAt(LocalDateTime.now());
            transaksiRepository.save(transaksi);
        });
    }

    // Hitung jumlah transaksi hari ini
    public int countTransaksiHariIni() {
        LocalDateTime awalHari = LocalDate.now().atStartOfDay();
        LocalDateTime akhirHari = awalHari.plusDays(1);
        return transaksiRepository.countByTanggalTransaksiBetweenAndDeletedAtIsNull(awalHari, akhirHari);
    }

    // Total pemasukan hari ini
    public int getTotalPemasukanHariIni() {
        LocalDateTime awalHari = LocalDate.now().atStartOfDay();
        LocalDateTime akhirHari = awalHari.plusDays(1);
        BigDecimal total = transaksiRepository.sumTotalByTanggalTransaksiBetweenAndDeletedAtIsNull(awalHari, akhirHari);
        return total != null ? total.intValue() : 0;
    }

    // Hitung transaksi berdasarkan status
    public int countByStatus(Transaksi.Status status) {
        return transaksiRepository.countByStatus(status);
    }

    // Ambil 5 transaksi terbaru
    public List<TransaksiDTO> getTransaksiTerbaru(int jumlah) {
        Pageable limit = PageRequest.of(0, jumlah);
        return transaksiRepository.findTopByDeletedAtIsNull(limit)
                .stream()
                .map(MapperUtil::toDTO)
                .collect(Collectors.toList());
    }

    public List<TransaksiDTO> findByPelangganId(String pelangganId) {
        return transaksiRepository.findByPelanggan_IdAndDeletedAtIsNull(pelangganId)
                .stream()
                .map(MapperUtil::toDTO)
                .collect(Collectors.toList());
    }

}
