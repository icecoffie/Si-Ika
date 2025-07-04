public class Mahasiswa {
    private String nim, nama;
    private double tugas, uts, uas;

    public Mahasiswa(String nim, String nama, double tugas, double uts, double uas) {
        this.nim = nim;
        this.nama = nama;
        this.tugas = tugas;
        this.uts = uts;
        this.uas = uas;
    }

    public double hitungNilaiAkhir() {
        return (tugas * 0.3) + (uts * 0.3) + (uas * 0.4);
    }

    public String getNim() { return nim; }
    public String getNama() { return nama; }
    public double getTugas() { return tugas; }
    public double getUts() { return uts; }
    public double getUas() { return uas; }

    public void setNim(String nim) { this.nim = nim; }
    public void setNama(String nama) { this.nama = nama; }
    public void setTugas(double tugas) { this.tugas = tugas; }
    public void setUts(double uts) { this.uts = uts; }
    public void setUas(double uas) { this.uas = uas; }
}
