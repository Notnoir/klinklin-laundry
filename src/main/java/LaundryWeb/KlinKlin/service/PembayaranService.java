package LaundryWeb.KlinKlin.service;

import LaundryWeb.KlinKlin.dto.PembayaranDTO;
import LaundryWeb.KlinKlin.model.Pembayaran;
import LaundryWeb.KlinKlin.model.Transaksi;
import LaundryWeb.KlinKlin.repository.PembayaranRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

import LaundryWeb.KlinKlin.repository.TransaksiRepository;
import LaundryWeb.KlinKlin.util.MapperUtil;

@Service
public class PembayaranService {

    @Autowired
    private PembayaranRepository pembayaranRepository;

    @Autowired
    private TransaksiRepository transaksiRepository;

    public PembayaranDTO save(PembayaranDTO dto) {
        Transaksi transaksi = transaksiRepository.findById(dto.getTransaksiId())
                .orElseThrow(() -> new RuntimeException("Transaksi tidak ditemukan"));

        Pembayaran pembayaran = MapperUtil.toEntity(dto, transaksi);
        Pembayaran saved = pembayaranRepository.save(pembayaran);
        return MapperUtil.toDTO(saved);
    }

    public PembayaranDTO findById(String id) {
        return pembayaranRepository.findById(id)
                .map(MapperUtil::toDTO)
                .orElse(null);
    }

    public List<PembayaranDTO> findAll() {
        return pembayaranRepository.findAll()
                .stream()
                .map(MapperUtil::toDTO)
                .collect(Collectors.toList());
    }
}
