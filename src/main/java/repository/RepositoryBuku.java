package repository;

import model.Buku;
import java.util.List;
import java.util.Optional;

public interface RepositoryBuku {
    boolean simpan (Buku buku);
    Optional<Buku> cariByIsbn (String isbn);
    List<Buku> cariByJudul (String Judul);
    List<Buku> cariByPengarang (String Pengarang);
    boolean hapus (String Isbn);
    boolean updateJumlahTersedia(String isbn, int jumlahTersediaBaru);
    List<Buku> cariSemua();

}