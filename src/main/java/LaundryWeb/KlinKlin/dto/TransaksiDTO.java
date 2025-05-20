package LaundryWeb.KlinKlin.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;
import jakarta.validation.constraints.NotNull;

@Data
public class TransaksiDTO {

    private String id;

    private String namaPelanggan;

    private String pelangganId;

    @NotNull(message = "ID layanan tidak boleh kosong")
    private String layananId;

    @NotNull(message = "ID kasir tidak boleh kosong")
    private String kasirId;

    @NotNull(message = "Berat cucian wajib diisi")
    private BigDecimal beratKg;

    private BigDecimal total; // bisa dihitung otomatis di service

    private String status; // enum: DITERIMA, DICUCI, dll (bisa di-set default di backend)

    private LocalDateTime tanggalTransaksi;
}
