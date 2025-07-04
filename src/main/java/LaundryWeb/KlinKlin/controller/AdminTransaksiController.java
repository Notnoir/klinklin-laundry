package LaundryWeb.KlinKlin.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import LaundryWeb.KlinKlin.dto.ReservasiDTO;
import LaundryWeb.KlinKlin.dto.TransaksiDTO;
import LaundryWeb.KlinKlin.model.Layanan;
import LaundryWeb.KlinKlin.model.User;
import LaundryWeb.KlinKlin.service.ReservasiService;
import LaundryWeb.KlinKlin.service.TransaksiService;
import LaundryWeb.KlinKlin.service.UserService;
import LaundryWeb.KlinKlin.repository.LayananRepository;
import LaundryWeb.KlinKlin.repository.UserRepository;
import LaundryWeb.KlinKlin.model.User.Role;
import LaundryWeb.KlinKlin.model.Transaksi.Status;
import org.springframework.data.domain.Page;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/transaksi")
@RequiredArgsConstructor
public class AdminTransaksiController {

    private final TransaksiService transaksiService;
    private final LayananRepository layananRepository;
    private final UserRepository userRepository;
    private final ReservasiService reservasiService;
    private final UserService userService;

    // Menampilkan semua transaksi
    @GetMapping
    public String listTransaksi(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "keyword", required = false) String keyword,
            Model model) {

        int pageSize = 5;
        Page<TransaksiDTO> transaksiPage;

        if (keyword != null && !keyword.trim().isEmpty()) {
            transaksiPage = transaksiService.searchTransaksi(keyword.trim(), page, pageSize);
            model.addAttribute("keyword", keyword);
        } else {
            transaksiPage = transaksiService.findPaginated(page, pageSize);
        }

        model.addAttribute("transaksiList", transaksiPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", transaksiPage.getTotalPages());

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

    @GetMapping("/from-reservasi/{reservasiId}")
    public String buatTransaksiDariReservasi(@PathVariable String reservasiId, Model model) {
        ReservasiDTO reservasiDTO = reservasiService.getReservasiById(reservasiId);
        if (reservasiDTO == null) {
            return "redirect:/admin/reservasi/list";
        }

        TransaksiDTO transaksiDTO = new TransaksiDTO();
        transaksiDTO.setNamaPelanggan(reservasiDTO.getNama());
        transaksiDTO.setPelangganId(reservasiDTO.getUserId());
        transaksiDTO.setReservasiId(reservasiDTO.getId());
        transaksiDTO.setLayananId(reservasiDTO.getLayananId()); // <== Set otomatis dari reservasi

        model.addAttribute("transaksiDTO", transaksiDTO);
        model.addAttribute("layananList", layananRepository.findByDeletedAtIsNull());
        model.addAttribute("kasirList", userService.getAllKasir());
        return "admin/transaksi/create-from-reservasi";
    }

    // Proses simpan transaksi
    @PostMapping("/save")
    public String simpanTransaksi(@ModelAttribute("transaksiDTO") TransaksiDTO dto) {
        transaksiService.save(dto);
        return "redirect:/admin/transaksi";
    }

    @PostMapping("/save-from-reservasi")
    public String simpanTransaksiReservasi(@ModelAttribute("transaksiDTO") TransaksiDTO dto) {
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
