package service;

import model.Anggota;
import model.Peminjaman;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test Kalkulator Denda")
public class KalkulatorDendaTest {

    private KalkulatorDenda kalkulatorDenda;
    private Anggota anggotaMahasiswa;
    private Anggota anggotaDosen;
    private Anggota anggotaUmum;

    @BeforeEach
    void setUp() {
        kalkulatorDenda = new KalkulatorDenda();
        anggotaMahasiswa = new Anggota("M001", "John Student", "john@student.ac.id",
                "081234567890", Anggota.TipeAnggota.MAHASISWA);
        anggotaDosen = new Anggota("D001", "Dr. Faculty", "faculty@univ.ac.id",
                "081234567891", Anggota.TipeAnggota.DOSEN);
        anggotaUmum = new Anggota("U001", "Public User", "public@email.com",
                "081234567892", Anggota.TipeAnggota.UMUM);
    }

    @Test
    @DisplayName("Tidak ada denda untuk peminjaman yang tidak terlambat")
    void testTidakAdaDendaUntukPeminjamanTidakTerlambat() {
        LocalDate tanggalPinjam = LocalDate.now().minusDays(3);
        LocalDate tanggalJatuhTempo = LocalDate.now().plusDays(2);

        Peminjaman peminjaman = new Peminjaman("P001", "M001", "1234567890",
                tanggalPinjam, tanggalJatuhTempo);

        double denda = kalkulatorDenda.hitungDenda(peminjaman, anggotaMahasiswa);

        assertEquals(0.0, denda, "Denda harus 0 untuk peminjaman yang tidak terlambat");
    }

    @Test
    @DisplayName("Hitung denda mahasiswa 3 hari terlambat")
    void testHitungDendaMahasiswaTigaHariTerlambat() {
        LocalDate tanggalPinjam = LocalDate.now().minusDays(10);
        LocalDate tanggalJatuhTempo = LocalDate.now().minusDays(3); // 3 hari terlambat

        Peminjaman peminjaman = new Peminjaman("P001", "M001", "1234567890",
                tanggalPinjam, tanggalJatuhTempo);

        double dendaAktual = kalkulatorDenda.hitungDenda(peminjaman, anggotaMahasiswa);
        assertEquals(3000.0, dendaAktual, "3 hari × 1000 harus sama dengan 3000");
    }

    @Test
    @DisplayName("Hitung denda dosen 5 hari terlambat")
    void testHitungDendaDosen() {
        LocalDate tanggalPinjam = LocalDate.now().minusDays(15);
        LocalDate tanggalJatuhTempo = LocalDate.now().minusDays(5); // 5 hari terlambat

        Peminjaman peminjaman = new Peminjaman("P001", "D001", "1234567890",
                tanggalPinjam, tanggalJatuhTempo);

        double dendaAktual = kalkulatorDenda.hitungDenda(peminjaman, anggotaDosen);
        assertEquals(10000.0, dendaAktual, "5 hari × 2000 harus sama dengan 10000");
    }

    @Test
    @DisplayName("Hitung denda umum 2 hari terlambat")
    void testHitungDendaUmum() {
        LocalDate tanggalPinjam = LocalDate.now().minusDays(10);
        LocalDate tanggalJatuhTempo = LocalDate.now().minusDays(2); // 2 hari terlambat

        Peminjaman peminjaman = new Peminjaman("P001", "U001", "1234567890",
                tanggalPinjam, tanggalJatuhTempo);

        double dendaAktual = kalkulatorDenda.hitungDenda(peminjaman, anggotaUmum);
        assertEquals(7000.0, dendaAktual, "2 hari × 3500 harus sama dengan 7000");
    }

    @Test
    @DisplayName("Denda tidak boleh melebihi batas maksimal")
    void testDendaTidakMelebihiBatasMaksimal() {
        // Peminjaman sangat terlambat (100 hari)
        LocalDate tanggalPinjam = LocalDate.now().minusDays(10);
        LocalDate tanggalJatuhTempo = LocalDate.now().minusDays(100);

        Peminjaman peminjaman = new Peminjaman("P001", "M001", "1234567890",
                tanggalPinjam, tanggalJatuhTempo);

        double dendaAktual = kalkulatorDenda.hitungDenda(peminjaman, anggotaMahasiswa);
        assertEquals(50000.0, dendaAktual, "Denda tidak boleh melebihi batas maksimal mahasiswa");

        // Test untuk dosen
        double dendaDosen = kalkulatorDenda.hitungDenda(peminjaman, anggotaDosen);
        assertEquals(70000.0, dendaDosen, "Denda tidak boleh melebihi batas maksimal dosen");

        // Test untuk umum
        double dendaUmum = kalkulatorDenda.hitungDenda(peminjaman, anggotaUmum);
        assertEquals(100000.0, dendaUmum, "Denda tidak boleh melebihi batas maksimal umum");
    }

    @Test
    @DisplayName("Exception untuk parameter null")
    void testExceptionParameterNull() {
        assertThrows(IllegalArgumentException.class,
                () -> kalkulatorDenda.hitungDenda(null, anggotaMahasiswa),
                "Harus throw exception untuk peminjaman null");

        Peminjaman peminjaman = new Peminjaman("P001", "M001", "1234567890",
                LocalDate.now(), LocalDate.now().plusDays(1));

        assertThrows(IllegalArgumentException.class,
                () -> kalkulatorDenda.hitungDenda(peminjaman, null),
                "Harus throw exception untuk anggota null");
    }

    @Test
    @DisplayName("Get tarif denda harian sesuai tipe anggota")
    void testGetTarifDendaHarian() {
        assertEquals(1000.0, kalkulatorDenda.getTarifDendaHarian(Anggota.TipeAnggota.MAHASISWA));
        assertEquals(2000.0, kalkulatorDenda.getTarifDendaHarian(Anggota.TipeAnggota.DOSEN));
        assertEquals(3500.0, kalkulatorDenda.getTarifDendaHarian(Anggota.TipeAnggota.UMUM));

        assertThrows(IllegalArgumentException.class,
                () -> kalkulatorDenda.getTarifDendaHarian(null));
    }

    @Test
    @DisplayName("Get denda maksimal sesuai tipe anggota")
    void testGetDendaMaksimal() {
        assertEquals(50000.0, kalkulatorDenda.getDendaMaksimal(Anggota.TipeAnggota.MAHASISWA));
        assertEquals(70000.0, kalkulatorDenda.getDendaMaksimal(Anggota.TipeAnggota.DOSEN));
        assertEquals(100000.0, kalkulatorDenda.getDendaMaksimal(Anggota.TipeAnggota.UMUM));

        assertThrows(IllegalArgumentException.class,
                () -> kalkulatorDenda.getDendaMaksimal(null));
    }

    @Test
    @DisplayName("Cek ada denda")
    void testAdaDenda() {
        // Peminjaman terlambat
        LocalDate tanggalJatuhTempo = LocalDate.now().minusDays(1);
        Peminjaman peminjamanTerlambat = new Peminjaman("P001", "M001", "1234567890",
                LocalDate.now().minusDays(5), tanggalJatuhTempo);

        assertTrue(kalkulatorDenda.adaDenda(peminjamanTerlambat));

        // Peminjaman tidak terlambat
        Peminjaman peminjamanTidakTerlambat = new Peminjaman("P002", "M001", "1234567890",
                LocalDate.now().minusDays(1), LocalDate.now().plusDays(1));

        assertFalse(kalkulatorDenda.adaDenda(peminjamanTidakTerlambat));

        // Peminjaman null
        assertFalse(kalkulatorDenda.adaDenda(null));
    }

    @Test
    @DisplayName("Deskripsi denda sesuai jumlah")
    void testDeskripsiDenda() {
        assertEquals("Tidak ada denda", kalkulatorDenda.getDeskripsiDenda(0.0));
        assertEquals("Denda ringan", kalkulatorDenda.getDeskripsiDenda(5000.0));
        assertEquals("Denda sedang", kalkulatorDenda.getDeskripsiDenda(25000.0));
        assertEquals("Denda berat", kalkulatorDenda.getDeskripsiDenda(75000.0));
    }

    // Add these test methods to KalkulatorDendaTest.java

    @Test
    @DisplayName("Test hitung denda dengan peminjaman null")
    void testHitungDendaWithNullPeminjaman() {
        assertThrows(IllegalArgumentException.class,
                () -> kalkulatorDenda.hitungDenda(null, anggotaMahasiswa));
    }

    @Test
    @DisplayName("Test hitung denda dengan anggota null")
    void testHitungDendaWithNullAnggota() {
        Peminjaman peminjaman = new Peminjaman("P001", "M001", "1234567890",
                LocalDate.now(), LocalDate.now().plusDays(1));

        assertThrows(IllegalArgumentException.class,
                () -> kalkulatorDenda.hitungDenda(peminjaman, null));
    }

    @Test
    @DisplayName("Test getTarifDendaHarian dengan tipe null")
    void testGetTarifDendaHarianWithNullType() {
        assertThrows(IllegalArgumentException.class,
                () -> kalkulatorDenda.getTarifDendaHarian(null));
    }

    @Test
    @DisplayName("Test getDendaMaksimal dengan tipe null")
    void testGetDendaMaksimalWithNullType() {
        assertThrows(IllegalArgumentException.class,
                () -> kalkulatorDenda.getDendaMaksimal(null));
    }

    @Test
    @DisplayName("Test peminjaman yang sudah dikembalikan terlambat")
    void testPeminjamanSudahDikembalikanTerlambat() {
        LocalDate tanggalPinjam = LocalDate.now().minusDays(10);
        LocalDate tanggalJatuhTempo = LocalDate.now().minusDays(5);
        LocalDate tanggalKembali = LocalDate.now().minusDays(2); // 3 hari terlambat

        Peminjaman peminjaman = new Peminjaman("P001", "M001", "1234567890",
                tanggalPinjam, tanggalJatuhTempo);
        peminjaman.setTanggalKembali(tanggalKembali);
        peminjaman.setSudahDikembalikan(true);

        double denda = kalkulatorDenda.hitungDenda(peminjaman, anggotaMahasiswa);
        assertEquals(3000.0, denda, "3 hari × 1000 harus sama dengan 3000");
    }
}