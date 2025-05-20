package LaundryWeb.KlinKlin.controller;

import LaundryWeb.KlinKlin.dto.FeedbackDTO;
import LaundryWeb.KlinKlin.service.FeedbackService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/feedbacks")
@Validated
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping
    public ResponseEntity<FeedbackDTO> create(@Valid @RequestBody FeedbackDTO dto) {
        FeedbackDTO saved = feedbackService.save(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeedbackDTO> getById(@PathVariable String id) {
        FeedbackDTO dto = feedbackService.findById(id);
        if (dto == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<FeedbackDTO>> getAll() {
        List<FeedbackDTO> list = feedbackService.findAll();
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        feedbackService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
