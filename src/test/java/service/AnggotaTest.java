package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test Kelas Anggota")
public class AnggotaTest {

    private Anggota anggota;

    @BeforeEach
    void setUp() {
        anggota = new Anggota("A001", "John Doe", "john@example.com",
                "081234567890", Anggota.TipeAnggota.MAHASISWA);
    }

    @Test
    @DisplayName("Test constructor default")
    void testDefaultConstructor() {
        Anggota defaultAnggota = new Anggota();
        assertNull(defaultAnggota.getIdAnggota());
        assertNull(defaultAnggota.getNama());
        assertNull(defaultAnggota.getEmail());
        assertNull(defaultAnggota.getTelepon());
        assertNull(defaultAnggota.getTipeAnggota());
        assertTrue(defaultAnggota.isAktif());
        assertEquals(0, defaultAnggota.getJumlahBukuDipinjam());
    }

    @Test
    @DisplayName("Test constructor dengan parameter")
    void testConstructorWithParameters() {
        assertEquals("A001", anggota.getIdAnggota());
        assertEquals("John Doe", anggota.getNama());
        assertEquals("john@example.com", anggota.getEmail());
        assertEquals("081234567890", anggota.getTelepon());
        assertEquals(Anggota.TipeAnggota.MAHASISWA, anggota.getTipeAnggota());
        assertTrue(anggota.isAktif());
        assertEquals(0, anggota.getJumlahBukuDipinjam());
    }

    @Test
    @DisplayName("Test setter dan getter")
    void testSettersAndGetters() {
        anggota.setIdAnggota("A002");
        assertEquals("A002", anggota.getIdAnggota());

        anggota.setNama("Jane Doe");
        assertEquals("Jane Doe", anggota.getNama());

        anggota.setEmail("jane@example.com");
        assertEquals("jane@example.com", anggota.getEmail());

        anggota.setTelepon("081298765432");
        assertEquals("081298765432", anggota.getTelepon());

        anggota.setTipeAnggota(Anggota.TipeAnggota.DOSEN);
        assertEquals(Anggota.TipeAnggota.DOSEN, anggota.getTipeAnggota());

        anggota.setAktif(false);
        assertFalse(anggota.isAktif());

        List<String> idBuku = Arrays.asList("B001", "B002");
        anggota.setIdBukuDipinjam(idBuku);
        assertEquals(2, anggota.getJumlahBukuDipinjam());
        assertTrue(anggota.getIdBukuDipinjam().contains("B001"));
        assertTrue(anggota.getIdBukuDipinjam().contains("B002"));
    }

    @Test
    @DisplayName("Test getBatasPinjam untuk semua tipe")
    void testGetBatasPinjam() {
        // Test MAHASISWA
        assertEquals(3, anggota.getBatasPinjam());

        // Test DOSEN
        anggota.setTipeAnggota(Anggota.TipeAnggota.DOSEN);
        assertEquals(10, anggota.getBatasPinjam());

        // Test UMUM
        anggota.setTipeAnggota(Anggota.TipeAnggota.UMUM);
        assertEquals(2, anggota.getBatasPinjam());
    }

    @Test
    @DisplayName("Test bolehPinjamLagi - aktif dan belum penuh")
    void testBolehPinjamLagiActiveNotFull() {
        assertTrue(anggota.bolehPinjamLagi());
    }

    @Test
    @DisplayName("Test bolehPinjamLagi - tidak aktif")
    void testBolehPinjamLagiNotActive() {
        anggota.setAktif(false);
        assertFalse(anggota.bolehPinjamLagi());
    }

    @Test
    @DisplayName("Test bolehPinjamLagi - aktif tapi penuh")
    void testBolehPinjamLagiActiveButFull() {
        // Mahasiswa batas 3 buku
        anggota.tambahBukuDipinjam("B001");
        anggota.tambahBukuDipinjam("B002");
        anggota.tambahBukuDipinjam("B003");
        assertFalse(anggota.bolehPinjamLagi());
    }

    // Add these test methods to AnggotaTest.java

    @Test
    @DisplayName("Test getBatasPinjam dengan tipe anggota null")
    void testGetBatasPinjamWithNullType() {
        Anggota anggotaNullType = new Anggota();
        anggotaNullType.setTipeAnggota(null);

        assertEquals(0, anggotaNullType.getBatasPinjam());
    }

    @Test
    @DisplayName("Test tambahBukuDipinjam dengan ID buku null")
    void testTambahBukuDipinjamWithNullId() {
        anggota.tambahBukuDipinjam(null);
        assertEquals(0, anggota.getJumlahBukuDipinjam());
    }

    @Test
    @DisplayName("Test hapusBukuDipinjam dengan ID buku null")
    void testHapusBukuDipinjamWithNullId() {
        anggota.tambahBukuDipinjam("B001");
        anggota.hapusBukuDipinjam(null);
        assertEquals(1, anggota.getJumlahBukuDipinjam()); // Should remain unchanged
    }

    @Test
    @DisplayName("Test tambahBukuDipinjam")
    void testTambahBukuDipinjam() {
        anggota.tambahBukuDipinjam("B001");
        assertEquals(1, anggota.getJumlahBukuDipinjam());
        assertTrue(anggota.getIdBukuDipinjam().contains("B001"));

        // Test tambah duplikat
        anggota.tambahBukuDipinjam("B001");
        assertEquals(1, anggota.getJumlahBukuDipinjam());
    }

    @Test
    @DisplayName("Test hapusBukuDipinjam")
    void testHapusBukuDipinjam() {
        anggota.tambahBukuDipinjam("B001");
        anggota.tambahBukuDipinjam("B002");

        anggota.hapusBukuDipinjam("B001");
        assertEquals(1, anggota.getJumlahBukuDipinjam());
        assertFalse(anggota.getIdBukuDipinjam().contains("B001"));
        assertTrue(anggota.getIdBukuDipinjam().contains("B002"));

        // Test hapus buku yang tidak ada
        anggota.hapusBukuDipinjam("B999");
        assertEquals(1, anggota.getJumlahBukuDipinjam());
    }

    @Test
    @DisplayName("Test getJumlahBukuDipinjam")
    void testGetJumlahBukuDipinjam() {
        assertEquals(0, anggota.getJumlahBukuDipinjam());
        anggota.tambahBukuDipinjam("B001");
        assertEquals(1, anggota.getJumlahBukuDipinjam());
        anggota.tambahBukuDipinjam("B002");
        assertEquals(2, anggota.getJumlahBukuDipinjam());
    }

    @Test
    @DisplayName("Test equals dan hashCode")
    void testEqualsAndHashCode() {
        Anggota anggota1 = new Anggota("A001", "John", "john@test.com", "08111", Anggota.TipeAnggota.MAHASISWA);
        Anggota anggota2 = new Anggota("A001", "Jane", "jane@test.com", "08222", Anggota.TipeAnggota.DOSEN);
        Anggota anggota3 = new Anggota("A002", "John", "john@test.com", "08111", Anggota.TipeAnggota.MAHASISWA);

        assertEquals(anggota1, anggota2);
        assertNotEquals(anggota1, anggota3);
        assertEquals(anggota1.hashCode(), anggota2.hashCode());
        assertNotEquals(anggota1, null);
        assertNotEquals(anggota1, "string");
    }

    @Test
    @DisplayName("Test toString")
    void testToString() {
        String result = anggota.toString();
        assertTrue(result.contains("A001"));
        assertTrue(result.contains("John Doe"));
        assertTrue(result.contains("MAHASISWA"));
    }
}