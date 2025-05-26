package LaundryWeb.KlinKlin.controller;

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

import LaundryWeb.KlinKlin.dto.LayananDTO;
import LaundryWeb.KlinKlin.service.LayananService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/kasir/layanan")
public class KasirLayananController {
    @Autowired
    private LayananService layananService;

    @GetMapping
    public String listLayanan(Model model) {
        List<LayananDTO> listLayanan = layananService.findAll();
        model.addAttribute("listLayanan", listLayanan);
        return "kasir/layanan/list";
    }
}
