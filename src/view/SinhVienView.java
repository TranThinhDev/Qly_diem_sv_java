package view;

import controller.SinhVienController;
import model.SinhVien;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SinhVienView extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField tfMaSV, tfHoTen, tfMaLop, tfNgaySinh, tfGioiTinh, tfEmail, tfSDT, tfDiaChi, tfTrangThai, tfKhoaHoc, tfSearch;

    public SinhVienView() {
        setTitle("Quản Lý Sinh Viên");
        setSize(1000, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(5, 4, 10, 5));

        tfMaSV = new JTextField();
        tfHoTen = new JTextField();
        tfMaLop = new JTextField();
        tfNgaySinh = new JTextField();
        tfGioiTinh = new JTextField();
        tfEmail = new JTextField();
        tfSDT = new JTextField();
        tfDiaChi = new JTextField();
        tfTrangThai = new JTextField();
        tfKhoaHoc = new JTextField();

        inputPanel.add(new JLabel("Mã SV:")); inputPanel.add(tfMaSV);
        inputPanel.add(new JLabel("Họ tên:")); inputPanel.add(tfHoTen);
        inputPanel.add(new JLabel("Mã lớp:")); inputPanel.add(tfMaLop);
        inputPanel.add(new JLabel("Ngày sinh:")); inputPanel.add(tfNgaySinh);
        inputPanel.add(new JLabel("Giới tính:")); inputPanel.add(tfGioiTinh);
        inputPanel.add(new JLabel("Email:")); inputPanel.add(tfEmail);
        inputPanel.add(new JLabel("SĐT:")); inputPanel.add(tfSDT);
        inputPanel.add(new JLabel("Địa chỉ:")); inputPanel.add(tfDiaChi);
        inputPanel.add(new JLabel("Trạng thái:")); inputPanel.add(tfTrangThai);
        inputPanel.add(new JLabel("Khóa học:")); inputPanel.add(tfKhoaHoc);

        JPanel buttonPanel = new JPanel();
        JButton btnAdd = new JButton("Thêm");
        JButton btnUpdate = new JButton("Cập nhật");
        JButton btnDelete = new JButton("Xóa");
        JButton btnRefresh = new JButton("Làm mới");
        tfSearch = new JTextField(15);
        JButton btnSearch = new JButton("Tìm kiếm");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnRefresh);
        buttonPanel.add(new JLabel("Tìm:")); buttonPanel.add(tfSearch); buttonPanel.add(btnSearch);

        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{
                "Mã SV", "Họ tên", "Mã lớp", "Ngày sinh", "Giới tính",
                "Email", "SĐT", "Địa chỉ", "Trạng thái", "Khóa học"
        });

        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        loadTable(SinhVienController.getAll());

        btnAdd.addActionListener(e -> {
            SinhVien sv = getSinhVienFromForm();
            if (SinhVienController.insert(sv)) {
                JOptionPane.showMessageDialog(this, "Thêm thành công!");
                loadTable(SinhVienController.getAll());
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại!");
            }
        });

        btnUpdate.addActionListener(e -> {
            SinhVien sv = getSinhVienFromForm();
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

        table.getSelectionModel().addListSelectionListener(e -> {
        int row = table.getSelectedRow();
        if (row >= 0) {
            try {
                tfMaSV.setText(String.valueOf(tableModel.getValueAt(row, 0)));
                tfHoTen.setText(String.valueOf(tableModel.getValueAt(row, 1)));
                tfMaLop.setText(String.valueOf(tableModel.getValueAt(row, 2)));
                tfNgaySinh.setText(String.valueOf(tableModel.getValueAt(row, 3)));
                tfGioiTinh.setText(String.valueOf(tableModel.getValueAt(row, 4)));
                tfEmail.setText(getSafeValueAt(row, 5));
                tfSDT.setText(String.valueOf(tableModel.getValueAt(row, 6)));
                tfDiaChi.setText(String.valueOf(tableModel.getValueAt(row, 7)));
                tfTrangThai.setText(String.valueOf(tableModel.getValueAt(row, 8)));
                tfKhoaHoc.setText(String.valueOf(tableModel.getValueAt(row, 9)));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    });

    }
    private String getSafeValueAt(int row, int col) {
        Object val = tableModel.getValueAt(row, col);
        return val == null ? "" : val.toString();
    }


    private void loadTable(List<SinhVien> list) {
    tableModel.setRowCount(0);
    if (list != null) {
        for (SinhVien sv : list) {
            String gioiTinh = sv.getGioiTinh().equalsIgnoreCase("1") ? "Nam" :
                              sv.getGioiTinh().equalsIgnoreCase("0") ? "Nữ" :
                              sv.getGioiTinh(); // fallback nếu đã là "Nam"/"Nữ"
            tableModel.addRow(new Object[]{
                    sv.getMaSV(), sv.getHoTen(), sv.getMaLop(), sv.getNgaySinh(), gioiTinh,
                    sv.getEmail(), sv.getSdt(), sv.getDiaChi(), sv.getTrangThai(), sv.getKhoaHoc()
            });
        }
    }
}


    private SinhVien getSinhVienFromForm() {
        return new SinhVien(
                tfMaSV.getText(),
                tfHoTen.getText(),
                tfMaLop.getText(),
                tfNgaySinh.getText(),
                tfGioiTinh.getText(),
                tfEmail.getText(),
                tfSDT.getText(),
                tfDiaChi.getText(),
                tfTrangThai.getText(),
                tfKhoaHoc.getText()
        );
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SinhVienView().setVisible(true));
    }
}
