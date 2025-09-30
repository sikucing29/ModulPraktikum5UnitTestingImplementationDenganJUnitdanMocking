package repository;

import model.Buku;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Implementasi mock manual untuk repository buku
 * Digunakan untuk testing tanpa dependency database
 */
public class MockRepositoryBuku {
    private final Map<String, Buku> repository;

    public MockRepositoryBuku() {
        this.repository = new ConcurrentHashMap<>();
    }

    /**
     * Menyimpan buku ke repository
     * @param buku buku yang akan disimpan
     * @return true jika berhasil, false jika gagal
     */
    public boolean simpan(Buku buku) {
        if (buku == null || buku.getIsbn() == null || buku.getIsbn().trim().isEmpty()) {
            return false;
        }

        repository.put(buku.getIsbn(), buku);
        return true;
    }

    /**
     * Mencari buku berdasarkan ISBN
     * @param isbn ISBN buku yang dicari
     * @return Optional berisi buku jika ditemukan, empty jika tidak
     */
    public Optional<Buku> cariByIsbn(String isbn) {
        if (isbn == null || isbn.trim().isEmpty()) {
            return Optional.empty();
        }

        return Optional.ofNullable(repository.get(isbn));
    }

    /**
     * Mencari buku berdasarkan judul (case insensitive, partial match)
     * @param judul judul atau bagian judul yang dicari
     * @return list buku yang sesuai
     */
    public List<Buku> cariByJudul(String judul) {
        if (judul == null || judul.trim().isEmpty()) {
            return new ArrayList<>();
        }

        String judulLower = judul.toLowerCase();
        return repository.values().stream()
                .filter(buku -> buku.getJudul() != null &&
                        buku.getJudul().toLowerCase().contains(judulLower))
                .collect(Collectors.toList());
    }

    /**
     * Mencari buku berdasarkan pengarang
     * @param pengarang nama pengarang
     * @return list buku yang sesuai
     */
    public List<Buku> cariByPengarang(String pengarang) {
        if (pengarang == null || pengarang.trim().isEmpty()) {
            return new ArrayList<>();
        }

        String pengarangLower = pengarang.toLowerCase();
        return repository.values().stream()
                .filter(buku -> buku.getPengarang() != null &&
                        buku.getPengarang().toLowerCase().contains(pengarangLower))
                .collect(Collectors.toList());
    }

    /**
     * Menghapus buku berdasarkan ISBN
     * @param isbn ISBN buku yang akan dihapus
     * @return true jika berhasil dihapus, false jika tidak ditemukan
     */
    public boolean hapus(String isbn) {
        if (isbn == null || isbn.trim().isEmpty()) {
            return false;
        }

        return repository.remove(isbn) != null;
    }

    /**
     * Mengupdate jumlah tersedia buku
     * @param isbn ISBN buku
     * @param jumlahTersedia jumlah tersedia baru
     * @return true jika berhasil, false jika gagal
     */
    public boolean updateJumlahTersedia(String isbn, int jumlahTersedia) {
        if (isbn == null || jumlahTersedia < 0) {
            return false;
        }

        Buku buku = repository.get(isbn);
        if (buku == null) {
            return false;
        }

        // Validasi: jumlah tersedia tidak boleh melebihi jumlah total
        if (jumlahTersedia > buku.getJumlahTotal()) {
            return false;
        }

        buku.setJumlahTersedia(jumlahTersedia);
        return true;
    }

    /**
     * Mengembalikan semua buku dalam repository
     * @return list semua buku
     */
    public List<Buku> cariSemua() {
        return new ArrayList<>(repository.values());
    }

    /**
     * Membersihkan repository (menghapus semua data)
     */
    public void bersihkan() {
        repository.clear();
    }

    /**
     * Mendapatkan jumlah buku dalam repository
     * @return jumlah buku
     */
    public int ukuran() {
        return repository.size();
    }

    /**
     * Mengecek apakah repository mengandung buku dengan ISBN tertentu
     * @param isbn ISBN yang dicek
     * @return true jika ada, false jika tidak
     */
    public boolean mengandung(String isbn) {
        return repository.containsKey(isbn);
    }
}