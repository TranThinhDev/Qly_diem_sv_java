package view;

import controller.LopController;
import controller.SinhVienController;
import model.Lop;
import model.SinhVien;
import util.FileExportUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SinhVienView extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField tfMaSV, tfHoTen, tfNgaySinh, tfEmail, tfSDT, tfDiaChi, tfTrangThai, tfKhoaHoc, tfTenLop, tfSearch;
    private JComboBox<String> cbMaLop, cbGioiTinh;
    private Map<String, String> maTenLopMap = new HashMap<>();

    public SinhVienView() {
        setTitle("Quản Lý Sinh Viên");
        setSize(1000, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // ===== Panel nhập liệu =====
        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Thông tin sinh viên"));

        tfMaSV = new JTextField();
        tfHoTen = new JTextField();
        cbMaLop = new JComboBox<>();
        tfTenLop = new JTextField(); tfTenLop.setEditable(false);
        tfNgaySinh = new JTextField();
        cbGioiTinh = new JComboBox<>(new String[]{"Nam", "Nữ"});
        tfEmail = new JTextField();
        tfSDT = new JTextField();
        tfDiaChi = new JTextField();
        tfTrangThai = new JTextField();
        tfKhoaHoc = new JTextField();

        inputPanel.add(new JLabel("Mã SV:")); inputPanel.add(tfMaSV);
        inputPanel.add(new JLabel("Họ tên:")); inputPanel.add(tfHoTen);
        inputPanel.add(new JLabel("Mã lớp:")); inputPanel.add(cbMaLop);
        inputPanel.add(new JLabel("Tên lớp:")); inputPanel.add(tfTenLop);
        inputPanel.add(new JLabel("Ngày sinh:")); inputPanel.add(tfNgaySinh);
        inputPanel.add(new JLabel("Giới tính:")); inputPanel.add(cbGioiTinh);
        inputPanel.add(new JLabel("Email:")); inputPanel.add(tfEmail);
        inputPanel.add(new JLabel("SĐT:")); inputPanel.add(tfSDT);
        inputPanel.add(new JLabel("Địa chỉ:")); inputPanel.add(tfDiaChi);
        inputPanel.add(new JLabel("Trạng thái:")); inputPanel.add(tfTrangThai);
        inputPanel.add(new JLabel("Khóa học:")); inputPanel.add(tfKhoaHoc);

        // ===== Load lớp vào combobox =====
        List<Lop> dsLop = LopController.getAll();
        for (Lop l : dsLop) {
            cbMaLop.addItem(l.getMaLop());
            maTenLopMap.put(l.getMaLop(), l.getTenLop());
        }
        cbMaLop.addActionListener(e -> {
            String ma = (String) cbMaLop.getSelectedItem();
            tfTenLop.setText(maTenLopMap.getOrDefault(ma, ""));
        });
        if (cbMaLop.getItemCount() > 0) cbMaLop.setSelectedIndex(0);

        // ===== Table =====
        tableModel = new DefaultTableModel(new String[]{
            "STT", "Mã SV", "Họ tên", "Mã lớp", "Tên lớp", "Ngày sinh",
            "Giới tính", "Email", "SĐT", "Địa chỉ", "Trạng thái", "Khóa học"
        }, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // ===== Panel nút =====
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnAdd = new JButton("Thêm");
        JButton btnUpdate = new JButton("Cập nhật");
        JButton btnDelete = new JButton("Xóa");
        JButton btnRefresh = new JButton("Làm mới");
        JButton btnExport = new JButton("Xuất CSV");
        tfSearch = new JTextField(15);
        JButton btnSearch = new JButton("Tìm");

        buttonPanel.add(btnAdd); buttonPanel.add(btnUpdate); buttonPanel.add(btnDelete);
        buttonPanel.add(btnRefresh); buttonPanel.add(btnExport);
        buttonPanel.add(new JLabel("Tìm kiếm:")); buttonPanel.add(tfSearch); buttonPanel.add(btnSearch);

        // ===== Main layout =====
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(inputPanel);
        mainPanel.add(buttonPanel);
        mainPanel.add(scrollPane);

        add(new JScrollPane(mainPanel));
        loadTable(SinhVienController.getAll());

        // ===== Events =====
        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                tfMaSV.setText(tableModel.getValueAt(row, 1).toString());
                tfHoTen.setText(tableModel.getValueAt(row, 2).toString());
                cbMaLop.setSelectedItem(tableModel.getValueAt(row, 3).toString());
                tfTenLop.setText(tableModel.getValueAt(row, 4).toString());
                tfNgaySinh.setText(tableModel.getValueAt(row, 5).toString());
                cbGioiTinh.setSelectedItem(tableModel.getValueAt(row, 6).toString());
                tfEmail.setText(tableModel.getValueAt(row, 7).toString());
                tfSDT.setText(tableModel.getValueAt(row, 8).toString());
                tfDiaChi.setText(tableModel.getValueAt(row, 9).toString());
                tfTrangThai.setText(tableModel.getValueAt(row, 10).toString());
                tfKhoaHoc.setText(tableModel.getValueAt(row, 11).toString());
            }
        });

        btnAdd.addActionListener(e -> {
            SinhVien sv = getFormData();
            if (SinhVienController.insert(sv)) {
                JOptionPane.showMessageDialog(this, "Thêm thành công!");
                loadTable(SinhVienController.getAll());
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại!");
            }
        });

        btnUpdate.addActionListener(e -> {
            SinhVien sv = getFormData();
            if (SinhVienController.update(sv)) {
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                loadTable(SinhVienController.getAll());
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
            }
        });

        btnDelete.addActionListener(e -> {
            String ma = tfMaSV.getText();
            if (SinhVienController.delete(ma)) {
                JOptionPane.showMessageDialog(this, "Xóa thành công!");
                loadTable(SinhVienController.getAll());
            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại!");
            }
        });

        btnRefresh.addActionListener(e -> loadTable(SinhVienController.getAll()));

        btnSearch.addActionListener(e -> {
            String keyword = tfSearch.getText();
            loadTable(SinhVienController.search(keyword));
        });

        btnExport.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            int option = chooser.showSaveDialog(SinhVienView.this);
            if (option == JFileChooser.APPROVE_OPTION) {
                String path = chooser.getSelectedFile().getAbsolutePath();
                FileExportUtil.exportTableToCSV(table, path);
                JOptionPane.showMessageDialog(this, "Đã xuất file CSV!");
            }
        });
    }

    private SinhVien getFormData() {
        return new SinhVien(
                tfMaSV.getText().trim(),
                tfHoTen.getText().trim(),
                (String) cbMaLop.getSelectedItem(),
                tfNgaySinh.getText().trim(),
                (String) cbGioiTinh.getSelectedItem(),
                tfEmail.getText().trim(),
                tfSDT.getText().trim(),
                tfDiaChi.getText().trim(),
                tfTrangThai.getText().trim(),
                tfKhoaHoc.getText().trim()
        );
    }

    private void loadTable(List<SinhVien> list) {
        tableModel.setRowCount(0);
        int stt = 1;
        if (list != null) {
            for (SinhVien sv : list) {
                String maSV = sv.getMaSV() != null ? sv.getMaSV() : "";
                String hoTen = sv.getHoTen() != null ? sv.getHoTen() : "";
                String maLop = sv.getMaLop() != null ? sv.getMaLop() : "";
                String tenLop = maTenLopMap.getOrDefault(maLop, "");
                String ngaySinh = sv.getNgaySinh() != null ? sv.getNgaySinh() : "";
                String gioiTinhCode = sv.getGioiTinh();
                String gioiTinh = "Nữ"; // mặc định
                if ("1".equals(gioiTinhCode)) gioiTinh = "Nam";
                else if ("0".equals(gioiTinhCode)) gioiTinh = "Nữ";
                else if (gioiTinhCode == null || gioiTinhCode.isEmpty()) gioiTinh = "";

                String email = sv.getEmail() != null ? sv.getEmail() : "";
                String sdt = sv.getSdt() != null ? sv.getSdt() : "";
                String diaChi = sv.getDiaChi() != null ? sv.getDiaChi() : "";
                String trangThai = sv.getTrangThai() != null ? sv.getTrangThai() : "";
                String khoaHoc = sv.getKhoaHoc() != null ? sv.getKhoaHoc() : "";

                tableModel.addRow(new Object[]{
                        stt++, maSV, hoTen, maLop, tenLop,
                        ngaySinh, gioiTinh, email, sdt, diaChi, trangThai, khoaHoc
                });
            }
        }
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SinhVienView().setVisible(true));
    }
}
