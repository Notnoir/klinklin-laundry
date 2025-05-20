package LaundryWeb.KlinKlin.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Data
public class PembayaranDTO {

    private String id;

    @NotBlank(message = "ID transaksi tidak boleh kosong")
    private String transaksiId;

    @NotNull(message = "Jumlah pembayaran wajib diisi")
    @Positive(message = "Jumlah harus positif")
    private Double jumlah;
}
