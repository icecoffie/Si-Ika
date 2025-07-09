import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class FormMahasiswa extends JFrame {

    private JTextField txtNim, txtNama, txtTugas, txtUts, txtUas;
    private JButton btnTambah, btnEdit, btnHapus, btnHitung;
    private JTable table;
    private DefaultTableModel model;

    private MahasiswaService service = new MahasiswaService();

    public FormMahasiswa() {
        setTitle("Si Ika - Sistem Informasi Akademik");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.decode("#E3F2FD"));

        // Tabel
        model = new DefaultTableModel(new String[]{"NIM", "Nama", "Tugas", "UTS", "UAS", "Nilai Akhir"}, 0);
        table = new JTable(model);
        table.setRowHeight(26);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Form input
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formPanel.setBackground(Color.decode("#BBDEFB"));

        txtNim = new JTextField();
        txtNama = new JTextField("Malika");
        txtTugas = new JTextField();
        txtUts = new JTextField();
        txtUas = new JTextField();

        formPanel.add(new JLabel("NIM"));
        formPanel.add(txtNim);
        formPanel.add(new JLabel("Nama"));
        formPanel.add(txtNama);
        formPanel.add(new JLabel("Nilai Tugas"));
        formPanel.add(txtTugas);
        formPanel.add(new JLabel("Nilai UTS"));
        formPanel.add(txtUts);
        formPanel.add(new JLabel("Nilai UAS"));
        formPanel.add(txtUas);

        // Tombol
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Color.decode("#90CAF9"));

        btnTambah = new JButton("Tambah");
        btnEdit = new JButton("Edit");
        btnHapus = new JButton("Hapus");
        btnHitung = new JButton("Hitung Nilai");

        Color primary = Color.decode("#1976D2");
        for (JButton btn : new JButton[]{btnTambah, btnEdit, btnHapus, btnHitung}) {
            btn.setBackground(primary);
            btn.setForeground(Color.BLACK);
            btn.setFocusPainted(false);
        }
        btnHapus.setBackground(Color.decode("#D32F2F"));
        btnHitung.setBackground(Color.decode("#0D47A1"));

        buttonPanel.add(btnTambah);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnHapus);
        buttonPanel.add(btnHitung);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(formPanel, BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);

        // Event
        btnTambah.addActionListener(e -> tambahData());
        btnEdit.addActionListener(e -> editData());
        btnHapus.addActionListener(e -> hapusData());
        btnHitung.addActionListener(e -> tampilNilaiAkhir());

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                txtNim.setText(model.getValueAt(row, 0).toString());
                txtNama.setText(model.getValueAt(row, 1).toString());
                txtTugas.setText(model.getValueAt(row, 2).toString());
                txtUts.setText(model.getValueAt(row, 3).toString());
                txtUas.setText(model.getValueAt(row, 4).toString());
            }
        });

        setVisible(true);
    }

    private void tampilData() {
        model.setRowCount(0);
        for (Mahasiswa m : service.getAll()) {
            model.addRow(new Object[]{
                    m.getNim(), m.getNama(),
                    m.getTugas(), m.getUts(), m.getUas(),
                    String.format("%.2f", m.hitungNilaiAkhir())
            });
        }
    }

    private void tambahData() {
        try {
            Mahasiswa m = ambilDataInput();
            service.tambah(m);
            tampilData();
            clearForm();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Masukkan nilai dengan benar.");
        }
    }

    private void editData() {
        int index = table.getSelectedRow();
        if (index >= 0) {
            try {
                Mahasiswa m = ambilDataInput();
                service.edit(index, m);
                tampilData();
                clearForm();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Masukkan data valid untuk edit.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pilih data yang akan diedit.");
        }
    }

    private void hapusData() {
        int index = table.getSelectedRow();
        if (index >= 0) {
            service.hapus(index);
            tampilData();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Pilih data yang akan dihapus.");
        }
    }

    private void tampilNilaiAkhir() {
        StringBuilder sb = new StringBuilder("Nilai Akhir Mahasiswa:\n");
        for (Mahasiswa m : service.getAll()) {
            sb.append(m.getNama()).append(" (").append(m.getNim()).append("): ")
              .append(String.format("%.2f", m.hitungNilaiAkhir())).append("\n");
        }
        JOptionPane.showMessageDialog(this, sb.toString());
    }

    private Mahasiswa ambilDataInput() {
        String nim = txtNim.getText();
        String nama = txtNama.getText();
        double tugas = Double.parseDouble(txtTugas.getText());
        double uts = Double.parseDouble(txtUts.getText());
        double uas = Double.parseDouble(txtUas.getText());
        return new Mahasiswa(nim, nama, tugas, uts, uas);
    }

    private void clearForm() {
        txtNim.setText("");
        txtNama.setText("Malika");
        txtTugas.setText("");
        txtUts.setText("");
        txtUas.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FormMahasiswa());
    }
}
