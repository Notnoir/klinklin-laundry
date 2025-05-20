package LaundryWeb.KlinKlin.service;

import LaundryWeb.KlinKlin.dto.FeedbackDTO;
import LaundryWeb.KlinKlin.model.Feedback;
import LaundryWeb.KlinKlin.model.User;
import LaundryWeb.KlinKlin.repository.FeedbackRepository;
import LaundryWeb.KlinKlin.repository.UserRepository;
import LaundryWeb.KlinKlin.util.MapperUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private UserRepository userRepository;

    public FeedbackDTO save(FeedbackDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User tidak ditemukan"));

        Feedback feedback = MapperUtil.toEntity(dto, user);
        Feedback saved = feedbackRepository.save(feedback);
        return MapperUtil.toDTO(saved);
    }

    public FeedbackDTO findById(String id) {
        return feedbackRepository.findById(id)
                .map(MapperUtil::toDTO)
                .orElse(null);
    }

    public List<FeedbackDTO> findAll() {
        return feedbackRepository.findAll()
                .stream()
                .map(MapperUtil::toDTO)
                .collect(Collectors.toList());
    }

    public void deleteById(String id) {
        feedbackRepository.findById(id).ifPresent(feedback -> {
            feedback.setDeletedAt(LocalDateTime.now());
            feedbackRepository.save(feedback);
        });
    }
}
