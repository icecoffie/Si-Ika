import java.util.ArrayList;

public class MahasiswaService {
    private ArrayList<Mahasiswa> list = new ArrayList<>();

    public void tambah(Mahasiswa mhs) {
        list.add(mhs);
    }

    public void edit(int index, Mahasiswa mhs) {
        if (index >= 0 && index < list.size()) {
            list.set(index, mhs);
        }
    }

    public void hapus(int index) {
        if (index >= 0 && index < list.size()) {
            list.remove(index);
        }
    }

    public ArrayList<Mahasiswa> getAll() {
        return list;
    }
}
