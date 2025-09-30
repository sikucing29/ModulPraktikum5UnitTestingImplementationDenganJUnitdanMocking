package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test Kelas Peminjaman")
public class PeminjamanTest {

    private Peminjaman peminjaman;
    private LocalDate today;
    private LocalDate yesterday;
    private LocalDate tomorrow;

    @BeforeEach
    void setUp() {
        today = LocalDate.now();
        yesterday = today.minusDays(1);
        tomorrow = today.plusDays(1);

        peminjaman = new Peminjaman("P001", "A001", "1234567890",
                today.minusDays(5), today.plusDays(5));
    }

    @Test
    @DisplayName("Test constructor dengan parameter")
    void testConstructorWithParameters() {
        assertEquals("P001", peminjaman.getIdPeminjaman());
        assertEquals("A001", peminjaman.getIdAnggota());
        assertEquals("1234567890", peminjaman.getIsbnBuku());
        assertFalse(peminjaman.isSudahDikembalikan());
        assertNull(peminjaman.getTanggalKembali());
    }

    @Test
    @DisplayName("Test constructor default")
    void testDefaultConstructor() {
        Peminjaman defaultPeminjaman = new Peminjaman();
        assertNull(defaultPeminjaman.getIdPeminjaman());
        assertNull(defaultPeminjaman.getIdAnggota());
        assertNull(defaultPeminjaman.getIsbnBuku());
        assertNull(defaultPeminjaman.getTanggalPinjam());
        assertNull(defaultPeminjaman.getTanggalJatuhTempo());
        assertFalse(defaultPeminjaman.isSudahDikembalikan());
    }

    @Test
    @DisplayName("Test setter dan getter")
    void testSettersAndGetters() {
        peminjaman.setIdPeminjaman("P002");
        assertEquals("P002", peminjaman.getIdPeminjaman());

        peminjaman.setIdAnggota("A002");
        assertEquals("A002", peminjaman.getIdAnggota());

        peminjaman.setIsbnBuku("9876543210");
        assertEquals("9876543210", peminjaman.getIsbnBuku());

        LocalDate newDate = LocalDate.of(2023, 1, 1);
        peminjaman.setTanggalPinjam(newDate);
        assertEquals(newDate, peminjaman.getTanggalPinjam());

        peminjaman.setTanggalJatuhTempo(newDate.plusDays(7));
        assertEquals(newDate.plusDays(7), peminjaman.getTanggalJatuhTempo());

        peminjaman.setTanggalKembali(newDate.plusDays(5));
        assertEquals(newDate.plusDays(5), peminjaman.getTanggalKembali());
        assertTrue(peminjaman.isSudahDikembalikan());

        peminjaman.setSudahDikembalikan(false);
        assertFalse(peminjaman.isSudahDikembalikan());
        assertNull(peminjaman.getTanggalKembali());
    }

    @Test
    @DisplayName("Test isTerlambat - belum dikembalikan dan tidak terlambat")
    void testIsTerlambatNotReturnedNotLate() {
        peminjaman.setTanggalJatuhTempo(tomorrow);
        assertFalse(peminjaman.isTerlambat());
    }

    @Test
    @DisplayName("Test isTerlambat - belum dikembalikan dan terlambat")
    void testIsTerlambatNotReturnedLate() {
        peminjaman.setTanggalJatuhTempo(yesterday);
        assertTrue(peminjaman.isTerlambat());
    }

    @Test
    @DisplayName("Test isTerlambat - sudah dikembalikan dan tidak terlambat")
    void testIsTerlambatReturnedNotLate() {
        peminjaman.setTanggalJatuhTempo(tomorrow);
        peminjaman.setTanggalKembali(today);
        assertFalse(peminjaman.isTerlambat());
    }

    @Test
    @DisplayName("Test isTerlambat - sudah dikembalikan dan terlambat")
    void testIsTerlambatReturnedLate() {
        peminjaman.setTanggalJatuhTempo(yesterday);
        peminjaman.setTanggalKembali(today);
        assertTrue(peminjaman.isTerlambat());
    }

    @Test
    @DisplayName("Test isTerlambat dengan tanggal null")
    void testIsTerlambatWithNullDates() {
        Peminjaman emptyPeminjaman = new Peminjaman();
        assertFalse(emptyPeminjaman.isTerlambat());
    }

    @Test
    @DisplayName("Test getHariTerlambat - tidak terlambat")
    void testGetHariTerlambatNotLate() {
        peminjaman.setTanggalJatuhTempo(tomorrow);
        assertEquals(0, peminjaman.getHariTerlambat());
    }

    @Test
    @DisplayName("Test getHariTerlambat - terlambat 3 hari belum dikembalikan")
    void testGetHariTerlambatLateNotReturned() {
        peminjaman.setTanggalJatuhTempo(today.minusDays(3));
        assertEquals(3, peminjaman.getHariTerlambat());
    }

    @Test
    @DisplayName("Test getHariTerlambat - terlambat 2 hari sudah dikembalikan")
    void testGetHariTerlambatLateReturned() {
        peminjaman.setTanggalJatuhTempo(today.minusDays(5));
        peminjaman.setTanggalKembali(today.minusDays(3));
        assertEquals(2, peminjaman.getHariTerlambat());
    }

    @Test
    @DisplayName("Test getDurasiPeminjaman - belum dikembalikan")
    void testGetDurasiPeminjamanNotReturned() {
        LocalDate pinjamDate = today.minusDays(7);
        peminjaman.setTanggalPinjam(pinjamDate);
        assertEquals(7, peminjaman.getDurasiPeminjaman());
    }

    @Test
    @DisplayName("Test getDurasiPeminjaman - sudah dikembalikan")
    void testGetDurasiPeminjamanReturned() {
        LocalDate pinjamDate = today.minusDays(10);
        LocalDate kembaliDate = today.minusDays(2);
        peminjaman.setTanggalPinjam(pinjamDate);
        peminjaman.setTanggalKembali(kembaliDate);
        assertEquals(8, peminjaman.getDurasiPeminjaman());
    }

    @Test
    @DisplayName("Test getDurasiPeminjaman dengan tanggal pinjam null")
    void testGetDurasiPeminjamanWithNullPinjam() {
        Peminjaman emptyPeminjaman = new Peminjaman();
        assertEquals(0, emptyPeminjaman.getDurasiPeminjaman());
    }

    @Test
    @DisplayName("Test equals dan hashCode")
    void testEqualsAndHashCode() {
        Peminjaman peminjaman1 = new Peminjaman("P001", "A001", "123", today, tomorrow);
        Peminjaman peminjaman2 = new Peminjaman("P001", "A002", "456", yesterday, today);
        Peminjaman peminjaman3 = new Peminjaman("P002", "A001", "123", today, tomorrow);

        assertEquals(peminjaman1, peminjaman2);
        assertNotEquals(peminjaman1, peminjaman3);
        assertEquals(peminjaman1.hashCode(), peminjaman2.hashCode());
        assertNotEquals(peminjaman1, null);
        assertNotEquals(peminjaman1, "string");
    }

    // Add these test methods to PeminjamanTest.java

    @Test
    @DisplayName("Test setSudahDikembalikan dengan parameter true")
    void testSetSudahDikembalikanTrue() {
        LocalDate returnDate = LocalDate.now();
        peminjaman.setTanggalKembali(returnDate);
        peminjaman.setSudahDikembalikan(true);

        assertTrue(peminjaman.isSudahDikembalikan());
        assertEquals(returnDate, peminjaman.getTanggalKembali());
    }

    @Test
    @DisplayName("Test setSudahDikembalikan dengan parameter false")
    void testSetSudahDikembalikanFalse() {
        peminjaman.setTanggalKembali(LocalDate.now());
        peminjaman.setSudahDikembalikan(true);

        peminjaman.setSudahDikembalikan(false);

        assertFalse(peminjaman.isSudahDikembalikan());
        assertNull(peminjaman.getTanggalKembali());
    }

    @Test
    @DisplayName("Test getHariTerlambat dengan tanggal kembali sebelum jatuh tempo")
    void testGetHariTerlambatReturnedBeforeDue() {
        LocalDate pinjam = LocalDate.now().minusDays(10);
        LocalDate jatuhTempo = LocalDate.now().minusDays(2);
        LocalDate kembali = LocalDate.now().minusDays(3); // returned 1 day before due

        peminjaman.setTanggalPinjam(pinjam);
        peminjaman.setTanggalJatuhTempo(jatuhTempo);
        peminjaman.setTanggalKembali(kembali);
        peminjaman.setSudahDikembalikan(true);

        assertEquals(0, peminjaman.getHariTerlambat());
    }

    @Test
    @DisplayName("Test getDurasiPeminjaman dengan tanggal kembali sama dengan pinjam")
    void testGetDurasiPeminjamanSameDay() {
        LocalDate pinjam = LocalDate.now();
        LocalDate kembali = LocalDate.now();

        peminjaman.setTanggalPinjam(pinjam);
        peminjaman.setTanggalKembali(kembali);
        peminjaman.setSudahDikembalikan(true);

        assertEquals(0, peminjaman.getDurasiPeminjaman());
    }

    @Test
    @DisplayName("Test toString")
    void testToString() {
        String result = peminjaman.toString();
        assertTrue(result.contains("P001"));
        assertTrue(result.contains("A001"));
        assertTrue(result.contains("1234567890"));
    }
}