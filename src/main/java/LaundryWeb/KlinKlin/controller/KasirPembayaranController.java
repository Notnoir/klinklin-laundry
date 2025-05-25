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
import org.springframework.web.bind.annotation.RequestParam;

import LaundryWeb.KlinKlin.dto.PembayaranDTO;
import LaundryWeb.KlinKlin.model.Pembayaran;
import LaundryWeb.KlinKlin.service.PembayaranService;
import LaundryWeb.KlinKlin.service.TransaksiService;

@Controller
@RequestMapping("/kasir/pembayaran")
public class KasirPembayaranController {
    @Autowired
    private PembayaranService pembayaranService;

    @Autowired
    private TransaksiService transaksiService;

    @GetMapping
    public String listPembayaran(Model model) {
        List<PembayaranDTO> listPembayaran = pembayaranService.findAll();
        model.addAttribute("listPembayaran", listPembayaran);
        return "kasir/pembayaran/list";
    }

    @GetMapping("/create")
    public String showCreateForm(
            @RequestParam(value = "transaksiId", required = false) String transaksiId,
            Model model) {

        PembayaranDTO pembayaranDTO = new PembayaranDTO();

        if (transaksiId != null) {
            pembayaranDTO.setTransaksiId(transaksiId); // Isi otomatis field transaksi
        }

        model.addAttribute("pembayaranDTO", pembayaranDTO);
        model.addAttribute("listTransaksi", transaksiService.findAll());
        model.addAttribute("metodePembayaranOptions", Arrays.asList(Pembayaran.MetodePembayaran.values()));
        model.addAttribute("statusPembayaranOptions", Arrays.asList(Pembayaran.StatusPembayaran.values()));
        return "kasir/pembayaran/create";
    }

    @PostMapping("/save")
    public String savePembayaran(
            @ModelAttribute PembayaranDTO dto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("listTransaksi", transaksiService.findAll());
            model.addAttribute("metodePembayaranOptions", Arrays.asList(Pembayaran.MetodePembayaran.values()));
            model.addAttribute("statusPembayaranOptions", Arrays.asList(Pembayaran.StatusPembayaran.values()));
            return "kasir/pembayaran/create";
        }

        PembayaranDTO saved = pembayaranService.save(dto); // Menyimpan dan mendapatkan ID
        return "redirect:/kasir/pembayaran/" + saved.getId() + "/detail";
    }

    @GetMapping("/{id}/detail")
    public String detailPembayaran(@PathVariable String id, Model model) {
        PembayaranDTO pembayaran = pembayaranService.findById(id);
        if (pembayaran == null) {
            return "redirect:/kasir/pembayaran?error=notfound";
        }
        model.addAttribute("pembayaran", pembayaran);
        return "kasir/pembayaran/detail";
    }

    @GetMapping("/{id}/struk")
    public String cetakStruk(@PathVariable String id, Model model) {
        PembayaranDTO pembayaran = pembayaranService.findById(id);
        if (pembayaran == null) {
            return "redirect:/kasir/pembayaran?error=notfound";
        }
        model.addAttribute("pembayaran", pembayaran);
        return "kasir/pembayaran/struk";
    }
}
