package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test Kelas Buku")
public class BukuTest {

    private Buku buku;

    @BeforeEach
    void setUp() {
        buku = new Buku("1234567890", "Pemrograman Java", "John Doe", 5, 150000.0);
    }

    @Test
    @DisplayName("Test constructor default")
    void testDefaultConstructor() {
        Buku defaultBuku = new Buku();
        assertNull(defaultBuku.getIsbn());
        assertNull(defaultBuku.getJudul());
        assertNull(defaultBuku.getPengarang());
        assertEquals(0, defaultBuku.getJumlahTotal());
        assertEquals(0, defaultBuku.getJumlahTersedia());
        assertEquals(0.0, defaultBuku.getHarga());
    }

    @Test
    @DisplayName("Test constructor dengan parameter")
    void testConstructorWithParameters() {
        assertEquals("1234567890", buku.getIsbn());
        assertEquals("Pemrograman Java", buku.getJudul());
        assertEquals("John Doe", buku.getPengarang());
        assertEquals(5, buku.getJumlahTotal());
        assertEquals(5, buku.getJumlahTersedia());
        assertEquals(150000.0, buku.getHarga());
    }

    @Test
    @DisplayName("Test setter dan getter")
    void testSettersAndGetters() {
        buku.setIsbn("9876543210");
        assertEquals("9876543210", buku.getIsbn());

        buku.setJudul("Java Programming");
        assertEquals("Java Programming", buku.getJudul());

        buku.setPengarang("Jane Smith");
        assertEquals("Jane Smith", buku.getPengarang());

        buku.setJumlahTotal(10);
        assertEquals(10, buku.getJumlahTotal());

        buku.setJumlahTersedia(8);
        assertEquals(8, buku.getJumlahTersedia());

        buku.setHarga(200000.0);
        assertEquals(200000.0, buku.getHarga());
    }

    @Test
    @DisplayName("Test isTersedia - tersedia")
    void testIsTersediaAvailable() {
        buku.setJumlahTersedia(3);
        assertTrue(buku.isTersedia());
    }

    @Test
    @DisplayName("Test isTersedia - tidak tersedia")
    void testIsTersediaNotAvailable() {
        buku.setJumlahTersedia(0);
        assertFalse(buku.isTersedia());
    }

    @Test
    @DisplayName("Test equals dan hashCode")
    void testEqualsAndHashCode() {
        Buku buku1 = new Buku("1234567890", "Java", "Author", 1, 100000.0);
        Buku buku2 = new Buku("1234567890", "Java2", "Author2", 2, 200000.0);
        Buku buku3 = new Buku("9876543210", "Java", "Author", 1, 100000.0);

        assertEquals(buku1, buku2);
        assertNotEquals(buku1, buku3);
        assertEquals(buku1.hashCode(), buku2.hashCode());
        assertNotEquals(buku1, null);
        assertNotEquals(buku1, "string");
    }

    @Test
    @DisplayName("Test toString")
    void testToString() {
        String result = buku.toString();
        assertTrue(result.contains("1234567890"));
        assertTrue(result.contains("Pemrograman Java"));
        assertTrue(result.contains("John Doe"));
        assertTrue(result.contains("5"));
        assertTrue(result.contains("150000.0"));
    }
}