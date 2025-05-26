package LaundryWeb.KlinKlin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import LaundryWeb.KlinKlin.dto.TransaksiDTO;
import LaundryWeb.KlinKlin.model.Transaksi;
import LaundryWeb.KlinKlin.service.TransaksiService;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/kasir/")
public class KasirDashboardController {
    @Autowired
    private TransaksiService transaksiService;

    @GetMapping("/dashboard")
    public String dashboardKasir(Model model) {
        int jumlahTransaksi = transaksiService.countTransaksiHariIni();
        int totalPemasukan = transaksiService.getTotalPemasukanHariIni();

        int menunggu = transaksiService.countByStatus(Transaksi.Status.DITERIMA);
        int proses = transaksiService.countByStatus(Transaksi.Status.DICUCI);
        int selesai = transaksiService.countByStatus(Transaksi.Status.SELESAI);

        List<TransaksiDTO> transaksiTerbaru = transaksiService.getTransaksiTerbaru(5);

        model.addAttribute("jumlahTransaksi", jumlahTransaksi);
        model.addAttribute("totalPemasukan", totalPemasukan);
        model.addAttribute("menunggu", menunggu);
        model.addAttribute("proses", proses);
        model.addAttribute("selesai", selesai);
        model.addAttribute("transaksiTerbaru", transaksiTerbaru);

        return "kasir/dashboard";
    }
}
