package LaundryWeb.KlinKlin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import LaundryWeb.KlinKlin.dto.PembayaranDTO;
import LaundryWeb.KlinKlin.model.Transaksi;
import LaundryWeb.KlinKlin.repository.TransaksiRepository;
import LaundryWeb.KlinKlin.service.PembayaranService;

@Controller
@RequestMapping("/pelanggan/pembayaran")
public class PelangganPembayaranController {

    @Autowired
    private PembayaranService pembayaranService;

    @Autowired
    private TransaksiRepository transaksiRepository;

    // Tampilkan form pembayaran
    @GetMapping("/create")
    public String formPembayaran(@RequestParam("transaksiId") String transaksiId, Model model) {
        Transaksi transaksi = transaksiRepository.findById(transaksiId)
                .orElseThrow(() -> new RuntimeException("Transaksi tidak ditemukan"));

        PembayaranDTO dto = new PembayaranDTO();
        dto.setTransaksiId(transaksiId);
        dto.setNamaPelanggan(transaksi.getNamaPelanggan());
        dto.setBerat(transaksi.getBeratKg());
        dto.setTanggalTransaksi(transaksi.getTanggalTransaksi());
        dto.setJumlah(transaksi.getTotal() != null ? transaksi.getTotal().doubleValue() : 0.0);

        model.addAttribute("pembayaran", dto);
        return "pelanggan/pembayaran/create"; // pastikan ini sesuai dengan nama file HTML
    }

    // Proses simpan pembayaran
    @PostMapping("/simpan")
    public String simpanPembayaran(@ModelAttribute("pembayaran") PembayaranDTO dto,
            RedirectAttributes redirectAttributes) {
        try {
            pembayaranService.save(dto);
            redirectAttributes.addFlashAttribute("success", "Pembayaran berhasil disimpan.");
            return "redirect:/pelanggan/transaksi"; // redirect ke halaman daftar transaksi
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Gagal menyimpan pembayaran: " + e.getMessage());
            return "redirect:/pelanggan/pembayaran/create?transaksiId=" + dto.getTransaksiId();
        }
    }
}
