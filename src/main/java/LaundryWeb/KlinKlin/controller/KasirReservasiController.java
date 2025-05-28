package LaundryWeb.KlinKlin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import LaundryWeb.KlinKlin.dto.ReservasiDTO;
import LaundryWeb.KlinKlin.model.Layanan;
import LaundryWeb.KlinKlin.model.User;
import LaundryWeb.KlinKlin.repository.LayananRepository;
import LaundryWeb.KlinKlin.service.ReservasiService;
import LaundryWeb.KlinKlin.service.UserService;

@Controller
@RequestMapping("/kasir/reservasi")
@PreAuthorize("hasRole('KASIR')")
public class KasirReservasiController {
    @Autowired
    private ReservasiService reservasiService;

    @Autowired
    private UserService userService;

    @Autowired
    private LayananRepository layananRepository;

    // List semua reservasi
    @GetMapping("/list")
    public String listAllReservasi(Model model) {
        List<ReservasiDTO> semuaReservasi = reservasiService.getAllReservasi();
        model.addAttribute("reservasiList", semuaReservasi);
        return "kasir/reservasi/list";
    }

    // Detail reservasi by id
    @GetMapping("/detail/{id}")
    public String detailReservasi(@PathVariable String id, Model model) {
        ReservasiDTO reservasiDTO = reservasiService.getReservasiById(id);
        if (reservasiDTO == null) {
            return "error/404";
        }
        model.addAttribute("reservasi", reservasiDTO);
        return "kasir/reservasi/detail";
    }

    // Tampilkan halaman form buat reservasi baru
    @GetMapping("/create")
    public String tampilkanFormReservasi(Model model) {
        model.addAttribute("reservasiDTO", new ReservasiDTO());
        List<Layanan> layananList = layananRepository.findByDeletedAtIsNull();
        model.addAttribute("layananList", layananList);

        List<User> pelangganList = userService.getAllPelanggan(); // method ini harus ada di userService
        model.addAttribute("pelangganList", pelangganList);

        return "kasir/reservasi/create";
    }

    // Proses submit form reservasi baru
    @PostMapping("/save")
    public String submitReservasi(
            @ModelAttribute ReservasiDTO reservasiDTO,
            Model model) {

        User user = userService.getById(reservasiDTO.getUserId()); // ambil user dari id yg dipilih di form

        if (user == null) {
            model.addAttribute("error", "Pelanggan tidak ditemukan");
            return "kasir/reservasi/create";
        }

        reservasiService.buatReservasi(reservasiDTO, user);
        return "redirect:/kasir/reservasi/list";
    }

    // Update status reservasi
    @PostMapping("/{id}/status")
    public String updateStatusReservasiKasir(
            @PathVariable String id,
            @RequestParam String status) {
        reservasiService.updateStatus(id, status);
        return "redirect:/kasir/reservasi/list"; // arahkan kembali ke list reservasi
    }
}
