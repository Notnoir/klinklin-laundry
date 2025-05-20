package LaundryWeb.KlinKlin.controller;

import LaundryWeb.KlinKlin.model.TransaksiLog;
import LaundryWeb.KlinKlin.service.TransaksiLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/transaksi-log")
@RequiredArgsConstructor
public class TransaksiLogController {

    private final TransaksiLogService transaksiLogService;

    @GetMapping("/transaksi/{transaksiId}")
    public List<TransaksiLog> getByTransaksi(@PathVariable String transaksiId) {
        return transaksiLogService.getByTransaksi(transaksiId);
    }
}
