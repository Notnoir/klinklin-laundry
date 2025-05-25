package LaundryWeb.KlinKlin.controller;

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
public class KasirPembayaranController {

}
