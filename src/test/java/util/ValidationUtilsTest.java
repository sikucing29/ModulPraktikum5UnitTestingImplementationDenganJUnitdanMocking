package util;

import model.Buku;
import model.Anggota;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import util.ValidationUtils;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test Validasi Utils")
public class ValidationUtilsTest {

    @Test
    @DisplayName("Email valid harus mengembalikan true")
    void testEmailValid() {
        assertTrue(ValidationUtils.isValidEmail("mahasiswa@univ.ac.id"));
        assertTrue(ValidationUtils.isValidEmail("test@email.com"));
        assertTrue(ValidationUtils.isValidEmail("user123@domain.org"));
    }

    @util.ParameterizedTest
    @util.ValueSource(strings = {"", " ", "email-tanpa-at.com", "email0", "@domain.com", "email@domain"})
    @DisplayName("Email tidak valid harus mengembalikan false")
    void testEmailTidakValid(String emailTidakValid) {
        assertFalse(ValidationUtils.isValidEmail(emailTidakValid));
    }

    @Test
    @DisplayName("Email null harus mengembalikan false")
    void testEmailNull() {
        assertFalse(ValidationUtils.isValidEmail(null));
    }

    @Test
    @DisplayName("Nomor telepon valid harus mengembalikan true")
    void testNomorTeleponValid() {
        assertTrue(ValidationUtils.isValidNomorTelepon("08123456789"));
        assertTrue(ValidationUtils.isValidNomorTelepon("+628123456789"));
        assertTrue(ValidationUtils.isValidNomorTelepon("0812-3456-7890"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "123456789", "07123456789", "081234", "+67123456789"})
    @DisplayName("Nomor telepon tidak valid harus mengembalikan false")
    void testNomorTeleponTidakValid(String teleponTidakValid) {
        assertFalse(ValidationUtils.isValidNomorTelepon(teleponTidakValid));
    }

    @Test
    @DisplayName("ISBN valid harus mengembalikan true")
    void testISBNValid() {
        assertTrue(ValidationUtils.isValidISBN("1234567890"));
        assertTrue(ValidationUtils.isValidISBN("123456789-23"));
        assertTrue(ValidationUtils.isValidISBN("123-456-789-0"));
    }

    @Test
    @DisplayName("Buku valid harus mengembalikan true")
    void testBukuValid() {
        Buku buku = new Buku("1234567890", "Pemrograman Java", "John Doe", 5, 150000.0);
        assertTrue(ValidationUtils.isValidBuku(buku));
    }

    @Test
    @DisplayName("Buku dengan data tidak valid harus mengembalikan false")
    void testBukuTidakValid() {
        // Buku null
        assertFalse(ValidationUtils.isValidBuku(null));

        // ISBN tidak valid
        Buku bukuIsbnTidakValid = new Buku("123", "Judul", "Pengarang", 5, 100000.0);
        assertFalse(ValidationUtils.isValidBuku(bukuIsbnTidakValid));

        // Jumlah negatif
        Buku bukuJumlahNegatif = new Buku("1234567890", "Judul", "Pengarang", -1, 100000.0);
        assertFalse(ValidationUtils.isValidBuku(bukuJumlahNegatif));

        // Harga negatif
        Buku bukuHargaNegatif = new Buku("1234567890", "Judul", "Pengarang", 5, -100000.0);
        assertFalse(ValidationUtils.isValidBuku(bukuHargaNegatif));
    }

    @Test
    @DisplayName("Anggota valid harus mengembalikan true")
    void testAnggotaValid() {
        Anggota anggota = new Anggota("A001", "John Doe", "JohnD@univ.ac.id", "081234567890", Anggota.TipeAnggota.MAHASISWA);
        assertTrue(ValidationUtils.isValidAnggota(anggota));
    }

    @Test
    @DisplayName("String valid harus mengembalikan true")
    void testStringValid() {
        assertTrue(ValidationUtils.isValidString("teks"));
        assertTrue(ValidationUtils.isValidString(" teks dengan spasi "));
        assertFalse(ValidationUtils.isValidString(""));
        assertFalse(ValidationUtils.isValidString("   "));
        assertFalse(ValidationUtils.isValidString(null));
    }

    // Tambahkan method test berikut ke ValidationUtilsTest.java

    @Test
    @DisplayName("Test validasi anggota dengan data null")
    void testAnggotaNull() {
        assertFalse(ValidationUtils.isValidAnggota(null));
    }

    @Test
    @DisplayName("Test validasi anggota dengan data tidak valid")
    void testAnggotaInvalid() {
        Anggota anggotaInvalid = new Anggota("", "", "", "", null);
        assertFalse(ValidationUtils.isValidAnggota(anggotaInvalid));
    }

    @Test
    @DisplayName("Test validasi angka positif boundary")
    void testAngkaPositifBoundary() {
        assertTrue(ValidationUtils.isAngkaPositif(0.1));
        assertFalse(ValidationUtils.isAngkaPositif(0.0));
        assertFalse(ValidationUtils.isAngkaPositif(-0.1));
    }

    @Test
    @DisplayName("Test validasi angka non-negatif boundary")
    void testAngkaNonNegatifBoundary() {
        assertTrue(ValidationUtils.isAngkaNonNegatif(0.0));
        assertTrue(ValidationUtils.isAngkaNonNegatif(0.1));
        assertFalse(ValidationUtils.isAngkaNonNegatif(-0.1));
    }

    // Add these test methods to ValidationUtilsTest.java

    @Test
    @DisplayName("Test isValidNomorTelepon dengan format berbeda")
    void testIsValidNomorTeleponVariousFormats() {
        assertTrue(ValidationUtils.isValidNomorTelepon("0812-3456-7890"));
        assertTrue(ValidationUtils.isValidNomorTelepon("0812 3456 7890"));
        assertTrue(ValidationUtils.isValidNomorTelepon("+628123456789"));
        assertFalse(ValidationUtils.isValidNomorTelepon("123")); // Too short
        assertFalse(ValidationUtils.isValidNomorTelepon("081234567890123")); // Too long
    }

    @Test
    @DisplayName("Test isValidISBN dengan berbagai format")
    void testIsValidISBNVariousFormats() {
        assertTrue(ValidationUtils.isValidISBN("123-456-789-0"));
        assertTrue(ValidationUtils.isValidISBN("1234567890123"));
        assertFalse(ValidationUtils.isValidISBN("123")); // Too short
        assertFalse(ValidationUtils.isValidISBN("123-456-789")); // Too short with hyphens
    }

    @Test
    @DisplayName("Test isValidBuku dengan boundary values")
    void testIsValidBukuBoundaryValues() {
        // Test with zero values (should be valid for non-negative)
        Buku bukuZero = new Buku("1234567890", "Judul", "Pengarang", 0, 0.0);
        assertTrue(ValidationUtils.isValidBuku(bukuZero));

        // Test with negative values
        Buku bukuNegative = new Buku("1234567890", "Judul", "Pengarang", -1, 100000.0);
        assertFalse(ValidationUtils.isValidBuku(bukuNegative));
    }

    @Test
    @DisplayName("Test isValidAnggota dengan berbagai kondisi")
    void testIsValidAnggotaVariousConditions() {
        // Test with invalid email
        Anggota anggotaInvalidEmail = new Anggota("A001", "John", "invalid-email", "081234567890", Anggota.TipeAnggota.MAHASISWA);
        assertFalse(ValidationUtils.isValidAnggota(anggotaInvalidEmail));

        // Test with invalid phone
        Anggota anggotaInvalidPhone = new Anggota("A001", "John", "john@test.com", "123", Anggota.TipeAnggota.MAHASISWA);
        assertFalse(ValidationUtils.isValidAnggota(anggotaInvalidPhone));

        // Test with null type
        Anggota anggotaNullType = new Anggota("A001", "John", "john@test.com", "081234567890", null);
        assertFalse(ValidationUtils.isValidAnggota(anggotaNullType));
    }

    @Test
    @DisplayName("Angka positif dan non-negatif harus valid")
    void testValidasiAngka() {
        assertTrue(ValidationUtils.isAngkaPositif(1.0));
        assertTrue(ValidationUtils.isAngkaPositif(100.5));
        assertFalse(ValidationUtils.isAngkaPositif(0.0));
        assertFalse(ValidationUtils.isAngkaPositif(-1.0));

        assertTrue(ValidationUtils.isAngkaNonNegatif(0.0));
        assertTrue(ValidationUtils.isAngkaNonNegatif(1.0));
        assertFalse(ValidationUtils.isAngkaNonNegatif(-0.1));
    }
}