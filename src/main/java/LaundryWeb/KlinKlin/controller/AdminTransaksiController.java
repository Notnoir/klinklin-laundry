package LaundryWeb.KlinKlin.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import LaundryWeb.KlinKlin.dto.TransaksiDTO;
import LaundryWeb.KlinKlin.model.Layanan;
import LaundryWeb.KlinKlin.model.User;
import LaundryWeb.KlinKlin.service.TransaksiService;
import LaundryWeb.KlinKlin.repository.LayananRepository;
import LaundryWeb.KlinKlin.repository.UserRepository;
import LaundryWeb.KlinKlin.model.User.Role;
import LaundryWeb.KlinKlin.model.Transaksi.Status;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/transaksi")
@RequiredArgsConstructor
public class AdminTransaksiController {

    private final TransaksiService transaksiService;
    private final LayananRepository layananRepository;
    private final UserRepository userRepository;

    // Menampilkan semua transaksi
    @GetMapping
    public String listTransaksi(Model model) {
        model.addAttribute("transaksiList", transaksiService.findAll());
        return "admin/transaksi/list";
    }

    // Menampilkan detail transaksi tertentu
    @GetMapping("/{id}")
    public String detailTransaksi(@PathVariable String id, Model model) {
        TransaksiDTO transaksi = transaksiService.findById(id);
        if (transaksi == null) {
            return "redirect:/admin/transaksi?error=notfound";
        }
        model.addAttribute("transaksi", transaksi);
        return "admin/transaksi/detail";
    }

    // Form tambah transaksi
    @GetMapping("/create")
    public String formTambahTransaksi(Model model) {
        model.addAttribute("transaksiDTO", new TransaksiDTO());

        List<Layanan> layananList = layananRepository.findAll();
        List<User> kasirList = userRepository.findAllByRole(Role.KASIR);

        model.addAttribute("layananList", layananList);
        model.addAttribute("kasirList", kasirList);

        return "admin/transaksi/create";
    }

    // Proses simpan transaksi
    @PostMapping("/save")
    public String simpanTransaksi(@ModelAttribute("transaksiDTO") TransaksiDTO dto) {
        transaksiService.save(dto);
        return "redirect:/admin/transaksi";
    }

    // Form edit transaksi
    @GetMapping("/edit/{id}")
    public String formEditTransaksi(@PathVariable String id, Model model) {
        TransaksiDTO transaksi = transaksiService.findById(id);
        if (transaksi == null) {
            return "redirect:/admin/transaksi?error=notfound";
        }
        model.addAttribute("transaksiDTO", transaksi);

        List<Layanan> layananList = layananRepository.findAll();
        List<User> kasirList = userRepository.findAllByRole(Role.KASIR);
        List<User> pelangganList = userRepository.findAllByRole(Role.PELANGGAN);
        model.addAttribute("pelangganList", pelangganList);

        model.addAttribute("layananList", layananList);
        model.addAttribute("kasirList", kasirList);
        model.addAttribute("statusList", Status.values());

        return "admin/transaksi/edit";
    }

    // Proses update transaksi
    @PostMapping("/update")
    public String updateTransaksi(@ModelAttribute("transaksiDTO") TransaksiDTO dto) {
        transaksiService.update(dto);
        return "redirect:/admin/transaksi";
    }

    // Hapus transaksi (soft delete)
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        transaksiService.deleteById(id);
        return "redirect:/admin/transaksi";
    }
}
