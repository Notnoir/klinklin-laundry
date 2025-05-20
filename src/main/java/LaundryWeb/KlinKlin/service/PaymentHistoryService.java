package LaundryWeb.KlinKlin.service;

import LaundryWeb.KlinKlin.model.PaymentHistory;
import LaundryWeb.KlinKlin.repository.PaymentHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import LaundryWeb.KlinKlin.repository.PembayaranRepository;

@Service
@RequiredArgsConstructor
public class PaymentHistoryService {

    private final PaymentHistoryRepository paymentHistoryRepository;
    private final PembayaranRepository pembayaranRepository;

    public List<PaymentHistory> getByPembayaran(String pembayaranId) {
        return paymentHistoryRepository.findByPembayaran_Id(pembayaranId);
    }

    public PaymentHistory save(PaymentHistory history) {
        history.setId(UUID.randomUUID().toString());
        history.setTanggalBayar(LocalDateTime.now());
        return paymentHistoryRepository.save(history);
    }
}
