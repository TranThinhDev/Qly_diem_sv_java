package view;

import controller.GiangVienController;
import model.GiangVien;
import util.FileExportUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class GiangVienView extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField tfMaGV, tfHoTen, tfEmail, tfDiaChi, tfSDT, tfThanhTich, tfKhenThuong, tfSearch;

    public GiangVienView() {
        setTitle("Quản Lý Giảng Viên");
        setSize(950, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // ===== Panel nhập liệu =====
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Thông tin giảng viên"));

        tfMaGV = new JTextField();
        tfHoTen = new JTextField();
        tfEmail = new JTextField();
        tfDiaChi = new JTextField();
        tfSDT = new JTextField();
        tfThanhTich = new JTextField();
        tfKhenThuong = new JTextField();

        inputPanel.add(new JLabel("Mã GV:")); inputPanel.add(tfMaGV);
        inputPanel.add(new JLabel("Họ tên:")); inputPanel.add(tfHoTen);
        inputPanel.add(new JLabel("Email:")); inputPanel.add(tfEmail);
        inputPanel.add(new JLabel("Địa chỉ:")); inputPanel.add(tfDiaChi);
        inputPanel.add(new JLabel("SĐT:")); inputPanel.add(tfSDT);
        inputPanel.add(new JLabel("Thành tích:")); inputPanel.add(tfThanhTich);
        inputPanel.add(new JLabel("Khen thưởng:")); inputPanel.add(tfKhenThuong);

        // ===== Table =====
        tableModel = new DefaultTableModel(new String[]{
                "STT", "Mã GV", "Họ tên", "Email", "Địa chỉ", "SĐT", "Thành tích", "Khen thưởng"
        }, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // ===== Panel nút bấm =====
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
        loadTable(GiangVienController.getAll());

        // ===== Sự kiện =====
        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                tfMaGV.setText(getSafe(row, 1));
                tfHoTen.setText(getSafe(row, 2));
                tfEmail.setText(getSafe(row, 3));
                tfDiaChi.setText(getSafe(row, 4));
                tfSDT.setText(getSafe(row, 5));
                tfThanhTich.setText(getSafe(row, 6));
                tfKhenThuong.setText(getSafe(row, 7));
            }
        });

        btnAdd.addActionListener(e -> {
            GiangVien gv = getFormData();
            if (GiangVienController.add(gv)) {
                JOptionPane.showMessageDialog(this, "Thêm thành công!");
                loadTable(GiangVienController.getAll());
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại!");
            }
        });

        btnUpdate.addActionListener(e -> {
            GiangVien gv = getFormData();
            if (GiangVienController.update(gv)) {
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                loadTable(GiangVienController.getAll());
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
            }
        });

        btnDelete.addActionListener(e -> {
            String ma = tfMaGV.getText().trim();
            if (GiangVienController.delete(ma)) {
                JOptionPane.showMessageDialog(this, "Xóa thành công!");
                loadTable(GiangVienController.getAll());
            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại!");
            }
        });

        btnRefresh.addActionListener(e -> loadTable(GiangVienController.getAll()));

        btnSearch.addActionListener(e -> {
            String keyword = tfSearch.getText().trim();
            loadTable(GiangVienController.search(keyword));
        });

        btnExport.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            int option = chooser.showSaveDialog(this);
            if (option == JFileChooser.APPROVE_OPTION) {
                String path = chooser.getSelectedFile().getAbsolutePath();
                FileExportUtil.exportTableToCSV(table, path);
                JOptionPane.showMessageDialog(this, "Đã xuất file CSV!");
            }
        });
    }

    private void loadTable(List<GiangVien> list) {
        tableModel.setRowCount(0);
        int stt = 1;
        if (list != null) {
            for (GiangVien gv : list) {
                tableModel.addRow(new Object[]{
                        stt++, safe(gv.getMaGV()), safe(gv.getHoTen()), safe(gv.getEmail()),
                        safe(gv.getDiaChi()), safe(gv.getSdt()), safe(gv.getThanhTich()), safe(gv.getKhenThuong())
                });
            }
        }
    }

    private GiangVien getFormData() {
        return new GiangVien(
                tfMaGV.getText().trim(),
                tfHoTen.getText().trim(),
                tfDiaChi.getText().trim(),
                tfEmail.getText().trim(),
                tfSDT.getText().trim(),
                tfThanhTich.getText().trim(),
                tfKhenThuong.getText().trim()
        );
    }

    private String safe(String s) {
        return s == null ? "" : s;
    }

    private String getSafe(int row, int col) {
        Object val = tableModel.getValueAt(row, col);
        return val == null ? "" : val.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GiangVienView().setVisible(true));
    }
}
