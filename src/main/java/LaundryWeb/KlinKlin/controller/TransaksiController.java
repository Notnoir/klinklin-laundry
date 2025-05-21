package LaundryWeb.KlinKlin.controller;

import LaundryWeb.KlinKlin.dto.TransaksiDTO;
import LaundryWeb.KlinKlin.repository.LayananRepository;
import LaundryWeb.KlinKlin.repository.UserRepository;
import LaundryWeb.KlinKlin.service.TransaksiService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/transaksis")
@Validated
public class TransaksiController {

    @Autowired
    private TransaksiService transaksiService;

    @Autowired
    private LayananRepository layananRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<TransaksiDTO> create(@Valid @RequestBody TransaksiDTO dto) {
        TransaksiDTO saved = transaksiService.save(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransaksiDTO> getById(@PathVariable String id) {
        TransaksiDTO dto = transaksiService.findById(id);
        if (dto == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<TransaksiDTO>> getAll() {
        List<TransaksiDTO> list = transaksiService.findAll();
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        transaksiService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
