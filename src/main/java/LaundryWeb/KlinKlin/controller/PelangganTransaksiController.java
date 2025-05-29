package LaundryWeb.KlinKlin.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import LaundryWeb.KlinKlin.dto.TransaksiDTO;
import LaundryWeb.KlinKlin.model.User;
import LaundryWeb.KlinKlin.repository.LayananRepository;
import LaundryWeb.KlinKlin.repository.UserRepository;
import LaundryWeb.KlinKlin.service.ReservasiService;
import LaundryWeb.KlinKlin.service.TransaksiService;
import LaundryWeb.KlinKlin.service.UserService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/pelanggan/transaksi")
@RequiredArgsConstructor
public class PelangganTransaksiController {

    private final TransaksiService transaksiService;
    private final UserRepository userRepository;

    @GetMapping
    public String listTransaksiUser(Model model, Principal principal) {
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("User tidak ditemukan"));

        List<TransaksiDTO> listTransaksi = transaksiService.findByPelangganId(user.getId());
        model.addAttribute("listTransaksi", listTransaksi);
        return "pelanggan/transaksi/list"; // arahkan ke file HTML/Thymeleaf yang kamu buat
    }
}
