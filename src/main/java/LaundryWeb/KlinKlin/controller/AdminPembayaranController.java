package LaundryWeb.KlinKlin.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import LaundryWeb.KlinKlin.dto.PembayaranDTO;
import LaundryWeb.KlinKlin.model.Pembayaran;
import LaundryWeb.KlinKlin.service.PembayaranService;
import LaundryWeb.KlinKlin.service.TransaksiService;

@Controller
@RequestMapping("/admin/pembayaran")
public class AdminPembayaranController {
    @Autowired
    private PembayaranService pembayaranService;

    @Autowired
    private TransaksiService transaksiService;

    @GetMapping
    public String listPembayaran(Model model) {
        List<PembayaranDTO> listPembayaran = pembayaranService.findAll();
        model.addAttribute("listPembayaran", listPembayaran);
        return "admin/pembayaran/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("pembayaranDTO", new PembayaranDTO());
        model.addAttribute("listTransaksi", transaksiService.findAll()); // untuk pilihan transaksi
        model.addAttribute("metodePembayaranOptions", Arrays.asList(Pembayaran.MetodePembayaran.values()));
        model.addAttribute("statusPembayaranOptions", Arrays.asList(Pembayaran.StatusPembayaran.values()));
        return "admin/pembayaran/create";
    }

    @PostMapping("/save")
    public String savePembayaran(
            @ModelAttribute PembayaranDTO dto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("listTransaksi", transaksiService.findAll());
            model.addAttribute("metodePembayaranOptions", Arrays.asList(Pembayaran.MetodePembayaran.values()));
            model.addAttribute("statusPembayaranOptions", Arrays.asList(Pembayaran.StatusPembayaran.values()));
            return "admin/pembayaran/create";
        }
        pembayaranService.save(dto);
        // redirectAttrs.addFlashAttribute("successMessage", "Pembayaran berhasil
        // ditambahkan");
        return "redirect:/admin/pembayaran";
    }

    @GetMapping("/{id}/detail")
    public String detailPembayaran(@PathVariable String id, Model model) {
        PembayaranDTO pembayaran = pembayaranService.findById(id);
        if (pembayaran == null) {
            return "redirect:/admin/pembayaran?error=notfound";
        }
        model.addAttribute("pembayaran", pembayaran);
        return "admin/pembayaran/detail";
    }

    @GetMapping("/{id}/struk")
    public String cetakStruk(@PathVariable String id, Model model) {
        PembayaranDTO pembayaran = pembayaranService.findById(id);
        if (pembayaran == null) {
            return "redirect:/admin/pembayaran?error=notfound";
        }
        model.addAttribute("pembayaran", pembayaran);
        return "admin/pembayaran/struk";
    }

}
