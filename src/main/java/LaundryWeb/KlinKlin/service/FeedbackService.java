package LaundryWeb.KlinKlin.service;

import LaundryWeb.KlinKlin.model.Feedback;
import LaundryWeb.KlinKlin.repository.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;

    public List<Feedback> getAll() {
        return feedbackRepository.findAllByDeletedAtIsNull();
    }

    public List<Feedback> getByUserId(String userId) {
        return feedbackRepository.findByUser_IdAndDeletedAtIsNull(userId);
    }

    public Feedback save(Feedback feedback) {
        feedback.setId(UUID.randomUUID().toString());
        feedback.setTanggalFeedback(LocalDateTime.now());
        return feedbackRepository.save(feedback);
    }

    public void softDelete(String id) {
        feedbackRepository.findById(id).ifPresent(f -> {
            f.setDeletedAt(LocalDateTime.now());
            feedbackRepository.save(f);
        });
    }
}
