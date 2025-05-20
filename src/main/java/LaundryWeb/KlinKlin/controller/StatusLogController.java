package LaundryWeb.KlinKlin.controller;

import LaundryWeb.KlinKlin.model.StatusLog;
import LaundryWeb.KlinKlin.service.StatusLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/status-log")
@RequiredArgsConstructor
public class StatusLogController {

    private final StatusLogService statusLogService;

    @GetMapping("/transaksi/{transaksiId}")
    public List<StatusLog> getByTransaksi(@PathVariable String transaksiId) {
        return statusLogService.getByTransaksi(transaksiId);
    }
}
