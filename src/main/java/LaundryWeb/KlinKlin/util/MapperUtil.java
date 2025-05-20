package LaundryWeb.KlinKlin.util;

import LaundryWeb.KlinKlin.dto.*;
import LaundryWeb.KlinKlin.model.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class MapperUtil {

    // User
    public static User toEntity(UserDTO dto) {
        if (dto == null)
            return null;
        User user = new User();
        user.setId(dto.getId() != null ? dto.getId() : UUID.randomUUID().toString());
        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());
        return user;
    }

    public static UserDTO toDTO(User user) {
        if (user == null)
            return null;
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword());
        dto.setRole(user.getRole());
        return dto;
    }

    // Layanan
    public static Layanan toEntity(LayananDTO dto) {
        if (dto == null)
            return null;

        Layanan layanan = new Layanan();
        layanan.setId(dto.getId() != null ? dto.getId() : UUID.randomUUID().toString());
        layanan.setNamaLayanan(dto.getNamaLayanan());
        layanan.setHargaPerKg(dto.getHargaPerKg());
        layanan.setDeletedAt(null);

        return layanan;
    }

    public static LayananDTO toDTO(Layanan entity) {
        if (entity == null)
            return null;

        LayananDTO dto = new LayananDTO();
        dto.setId(entity.getId());
        dto.setNamaLayanan(entity.getNamaLayanan());
        dto.setHargaPerKg(entity.getHargaPerKg());

        return dto;
    }

    public static Transaksi toEntity(TransaksiDTO dto, User pelanggan, User kasir, Layanan layanan) {
        if (dto == null)
            return null;
        Transaksi t = new Transaksi();

        // ID
        if (dto.getId() == null || dto.getId().isEmpty()) {
            t.setId(UUID.randomUUID().toString());
        } else {
            t.setId(dto.getId());
        }

        // Pelanggan
        t.setPelanggan(pelanggan);

        // Hindari NullPointerException
        if (pelanggan != null) {
            t.setNamaPelanggan(pelanggan.getFullName());
        } else {
            t.setNamaPelanggan(dto.getNamaPelanggan()); // untuk guest / tanpa akun
        }

        // Lainnya
        t.setKasir(kasir);
        t.setLayanan(layanan);
        t.setBeratKg(dto.getBeratKg());
        t.setTotal(dto.getTotal());

        if (dto.getStatus() != null) {
            t.setStatus(Transaksi.Status.valueOf(dto.getStatus()));
        }

        t.setTanggalTransaksi(dto.getTanggalTransaksi());
        return t;
    }

    public static TransaksiDTO toDTO(Transaksi t) {
        if (t == null)
            return null;

        TransaksiDTO dto = new TransaksiDTO();
        dto.setId(t.getId());

        if (t.getPelanggan() != null) {
            dto.setPelangganId(t.getPelanggan().getId());
            dto.setNamaPelanggan(t.getPelanggan().getFullName());
        } else {
            dto.setPelangganId(null);
            dto.setNamaPelanggan(t.getNamaPelanggan()); // fallback ke nama manual
        }

        dto.setKasirId(t.getKasir() != null ? t.getKasir().getId() : null);
        dto.setLayananId(t.getLayanan() != null ? t.getLayanan().getId() : null);
        dto.setBeratKg(t.getBeratKg());
        dto.setTotal(t.getTotal());
        dto.setStatus(t.getStatus() != null ? t.getStatus().name() : null);
        dto.setTanggalTransaksi(t.getTanggalTransaksi());

        return dto;
    }

    // Pembayaran
    public static Pembayaran toEntity(PembayaranDTO dto, Transaksi transaksi) {
        if (dto == null)
            return null;
        Pembayaran p = new Pembayaran();
        p.setId(dto.getId() != null ? dto.getId() : UUID.randomUUID().toString());
        p.setTransaksi(transaksi);
        p.setWaktuBayar(LocalDateTime.now());
        if (dto.getJumlah() != null) {
            p.setTotalBayar(BigDecimal.valueOf(dto.getJumlah()));
        }
        return p;
    }

    public static PembayaranDTO toDTO(Pembayaran entity) {
        if (entity == null)
            return null;
        PembayaranDTO dto = new PembayaranDTO();
        dto.setId(entity.getId());
        dto.setTransaksiId(entity.getTransaksi() != null ? entity.getTransaksi().getId() : null);
        if (entity.getTotalBayar() != null) {
            dto.setJumlah(entity.getTotalBayar().doubleValue());
        }
        return dto;
    }

    // PaymentHistory
    public static PaymentHistory toEntity(PaymentHistoryDTO dto, Pembayaran pembayaran) {
        if (dto == null)
            return null;
        PaymentHistory ph = new PaymentHistory();
        ph.setId(dto.getId() != null ? dto.getId() : UUID.randomUUID().toString());
        ph.setPembayaran(pembayaran);
        ph.setJumlahBayar(dto.getJumlah() != null ? BigDecimal.valueOf(dto.getJumlah()) : null);
        ph.setTanggalBayar(LocalDateTime.now());
        // Jika ingin set metodePembayaran dan keterangan, bisa ditambahkan di sini
        return ph;
    }

    public static PaymentHistoryDTO toDTO(PaymentHistory entity) {
        if (entity == null)
            return null;
        PaymentHistoryDTO dto = new PaymentHistoryDTO();
        dto.setId(entity.getId());
        dto.setPembayaranId(entity.getPembayaran() != null ? entity.getPembayaran().getId() : null);
        dto.setJumlah(entity.getJumlahBayar() != null ? entity.getJumlahBayar().doubleValue() : null);
        return dto;
    }

    // Feedback
    public static Feedback toEntity(FeedbackDTO dto, User user) {
        if (dto == null)
            return null;
        Feedback f = new Feedback();
        f.setId(dto.getId() != null ? dto.getId() : UUID.randomUUID().toString());
        f.setUser(user);
        f.setKomentar(dto.getIsi()); // <-- ini yang benar
        f.setDeletedAt(null); // misal default belum dihapus
        f.setTanggalFeedback(LocalDateTime.now()); // bisa di-set sekarang
        return f;
    }

    public static FeedbackDTO toDTO(Feedback entity) {
        if (entity == null)
            return null;
        FeedbackDTO dto = new FeedbackDTO();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUser() != null ? entity.getUser().getId() : null);
        dto.setIsi(entity.getKomentar()); // <-- ini yang benar
        return dto;
    }
}
