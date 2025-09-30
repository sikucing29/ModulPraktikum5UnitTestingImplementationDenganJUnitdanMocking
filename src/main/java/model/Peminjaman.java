package model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class Peminjaman {
    private String idPeminjaman;
    private String idAnggota;
    private String isbnBuku;
    private LocalDate tanggalPinjam;
    private LocalDate tanggalJatuhTempo;
    private LocalDate tanggalKembali;
    private boolean sudahDikembalikan;

    public Peminjaman() {
    }

    public Peminjaman(String idPeminjaman, String idAnggota, String isbnBuku,
                      LocalDate tanggalPinjam, LocalDate tanggalJatuhTempo) {
        this.idPeminjaman = idPeminjaman;
        this.idAnggota = idAnggota;
        this.isbnBuku = isbnBuku;
        this.tanggalPinjam = tanggalPinjam;
        this.tanggalJatuhTempo = tanggalJatuhTempo;
        this.sudahDikembalikan = false;
    }

    // Getters dan Setters
    public String getIdPeminjaman() {
        return idPeminjaman;
    }

    public void setIdPeminjaman(String idPeminjaman) {
        this.idPeminjaman = idPeminjaman;
    }

    public String getIdAnggota() {
        return idAnggota;
    }

    public void setIdAnggota(String idAnggota) {
        this.idAnggota = idAnggota;
    }

    public String getIsbnBuku() {
        return isbnBuku;
    }

    public void setIsbnBuku(String isbnBuku) {
        this.isbnBuku = isbnBuku;
    }

    public LocalDate getTanggalPinjam() {
        return tanggalPinjam;
    }

    public void setTanggalPinjam(LocalDate tanggalPinjam) {
        this.tanggalPinjam = tanggalPinjam;
    }

    public LocalDate getTanggalJatuhTempo() {
        return tanggalJatuhTempo;
    }

    public void setTanggalJatuhTempo(LocalDate tanggalJatuhTempo) {
        this.tanggalJatuhTempo = tanggalJatuhTempo;
    }

    public LocalDate getTanggalKembali() {
        return tanggalKembali;
    }

    public void setTanggalKembali(LocalDate tanggalKembali) {
        this.tanggalKembali = tanggalKembali;
        if (tanggalKembali != null) {
            this.sudahDikembalikan = true;
        }
    }

    public boolean isSudahDikembalikan() {
        return sudahDikembalikan;
    }

    public void setSudahDikembalikan(boolean sudahDikembalikan) {
        this.sudahDikembalikan = sudahDikembalikan;
        if (!sudahDikembalikan) {
            this.tanggalKembali = null;
        }
    }

    public boolean isTerlambat() {
        if (tanggalPinjam == null || tanggalJatuhTempo == null) {
            return false;
        }

        if (sudahDikembalikan) {
            return tanggalKembali != null && tanggalKembali.isAfter(tanggalJatuhTempo);
        }
        return LocalDate.now().isAfter(tanggalJatuhTempo);
    }

    public long getHariTerlambat() {
        if (!isTerlambat()) {
            return 0;
        }

        if (sudahDikembalikan && tanggalKembali != null) {
            return Math.max(0, ChronoUnit.DAYS.between(tanggalJatuhTempo, tanggalKembali));
        } else {
            return Math.max(0, ChronoUnit.DAYS.between(tanggalJatuhTempo, LocalDate.now()));
        }
    }

    public long getDurasiPeminjaman() {
        if (tanggalPinjam == null) {
            return 0;
        }

        LocalDate tanggalAkhir = sudahDikembalikan && tanggalKembali != null ?
                tanggalKembali : LocalDate.now();
        return Math.max(0, ChronoUnit.DAYS.between(tanggalPinjam, tanggalAkhir));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Peminjaman that = (Peminjaman) o;
        return Objects.equals(idPeminjaman, that.idPeminjaman);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPeminjaman);
    }

    @Override
    public String toString() {
        return "Peminjaman{" +
                "idPeminjaman='" + idPeminjaman + '\'' +
                ", idAnggota='" + idAnggota + '\'' +
                ", isbnBuku='" + isbnBuku + '\'' +
                ", tanggalPinjam=" + tanggalPinjam +
                ", tanggalJatuhTempo=" + tanggalJatuhTempo +
                ", tanggalKembali=" + tanggalKembali +
                ", sudahDikembalikan=" + sudahDikembalikan +
                '}';
    }
}