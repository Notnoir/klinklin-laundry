package LaundryWeb.KlinKlin.service;

import LaundryWeb.KlinKlin.dto.PaymentHistoryDTO;
import LaundryWeb.KlinKlin.model.PaymentHistory;
import LaundryWeb.KlinKlin.model.Pembayaran;
import LaundryWeb.KlinKlin.repository.PaymentHistoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

import LaundryWeb.KlinKlin.repository.PembayaranRepository;
import LaundryWeb.KlinKlin.util.MapperUtil;

@Service
public class PaymentHistoryService {

    @Autowired
    private PaymentHistoryRepository paymentHistoryRepository;

    @Autowired
    private PembayaranRepository pembayaranRepository;

    public PaymentHistoryDTO save(PaymentHistoryDTO dto) {
        Pembayaran pembayaran = pembayaranRepository.findById(dto.getPembayaranId())
                .orElseThrow(() -> new RuntimeException("Pembayaran tidak ditemukan"));

        PaymentHistory paymentHistory = MapperUtil.toEntity(dto, pembayaran);
        PaymentHistory saved = paymentHistoryRepository.save(paymentHistory);
        return MapperUtil.toDTO(saved);
    }

    public PaymentHistoryDTO findById(String id) {
        return paymentHistoryRepository.findById(id)
                .map(MapperUtil::toDTO)
                .orElse(null);
    }

    public List<PaymentHistoryDTO> findAll() {
        return paymentHistoryRepository.findAll()
                .stream()
                .map(MapperUtil::toDTO)
                .collect(Collectors.toList());
    }

    public void deleteById(String id) {
        paymentHistoryRepository.findById(id).ifPresent(ph -> {
            // Jika mau soft delete, tambahkan field deleted di entity PaymentHistory
            // dan update di sini, contoh:
            // ph.setDeleted(true);
            // paymentHistoryRepository.save(ph);

            // Kalau tidak ada soft delete, bisa langsung hapus permanen
            paymentHistoryRepository.delete(ph);
        });
    }
}
