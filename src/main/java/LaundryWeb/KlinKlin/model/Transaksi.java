package LaundryWeb.KlinKlin.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "transaksi")
public class Transaksi {

    @Id
    @Column(length = 36)
    private String id;

    @ManyToOne
    @JoinColumn(name = "pelanggan_id")
    private User pelanggan;

    private String namaPelanggan;

    @ManyToOne
    @JoinColumn(name = "layanan_id", nullable = false)
    private Layanan layanan;

    private BigDecimal beratKg;

    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime tanggalTransaksi;

    @ManyToOne
    @JoinColumn(name = "kasir_id", nullable = false)
    private User kasir;

    private LocalDateTime deletedAt;

    public enum Status {
        DITERIMA, DICUCI, DIJEMUR, DISETRIKA, SELESAI
    }

    // getters & setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getPelanggan() {
        return pelanggan;
    }

    public void setPelanggan(User pelanggan) {
        this.pelanggan = pelanggan;
    }

    public String getNamaPelanggan() {
        return namaPelanggan;
    }

    public void setNamaPelanggan(String namaPelanggan) {
        this.namaPelanggan = namaPelanggan;
    }

    public Layanan getLayanan() {
        return layanan;
    }

    public void setLayanan(Layanan layanan) {
        this.layanan = layanan;
    }

    public BigDecimal getBeratKg() {
        return beratKg;
    }

    public void setBeratKg(BigDecimal beratKg) {
        this.beratKg = beratKg;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getTanggalTransaksi() {
        return tanggalTransaksi;
    }

    public void setTanggalTransaksi(LocalDateTime tanggalTransaksi) {
        this.tanggalTransaksi = tanggalTransaksi;
    }

    public User getKasir() {
        return kasir;
    }

    public void setKasir(User kasir) {
        this.kasir = kasir;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }
}

