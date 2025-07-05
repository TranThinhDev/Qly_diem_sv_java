package view;

import controller.NganhController;
import model.Nganh;
import util.FileExportUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class NganhView extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField tfMaNganh, tfTenNganh, tfTenKhoa, tfSearch;

    public NganhView() {
        setTitle("Quản Lý Ngành");
        setSize(950, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // ===== Panel nhập liệu =====
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        tfMaNganh = new JTextField();
        tfTenNganh = new JTextField();
        tfTenKhoa = new JTextField();

        inputPanel.setBorder(BorderFactory.createTitledBorder("Thông tin ngành"));

        inputPanel.add(new JLabel("Mã ngành:")); inputPanel.add(tfMaNganh);
        inputPanel.add(new JLabel("Tên ngành:")); inputPanel.add(tfTenNganh);
        inputPanel.add(new JLabel("Tên khoa:")); inputPanel.add(tfTenKhoa);

        // ===== Table =====
        tableModel = new DefaultTableModel(new String[]{"Mã ngành", "Tên ngành", "Tên khoa"}, 0);
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

        // ===== Main Panel chứa tất cả =====
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(inputPanel);
        mainPanel.add(buttonPanel);
        mainPanel.add(scrollPane);

        add(new JScrollPane(mainPanel));  // Cho cuộn nếu cần

        loadTable(NganhController.getAll());

        // ===== Sự kiện =====
        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                tfMaNganh.setText(getValue(row, 0));
                tfTenNganh.setText(getValue(row, 1));
                tfTenKhoa.setText(getValue(row, 2));
            }
        });

        btnAdd.addActionListener(e -> {
            Nganh ng = getFormData();
            if (NganhController.insert(ng)) {
                JOptionPane.showMessageDialog(this, "Thêm thành công!");
                loadTable(NganhController.getAll());
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại!");
            }
        });

        btnUpdate.addActionListener(e -> {
            Nganh ng = getFormData();
            if (NganhController.update(ng)) {
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                loadTable(NganhController.getAll());
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
            }
        });

        btnDelete.addActionListener(e -> {
            String ma = tfMaNganh.getText();
            if (NganhController.delete(ma)) {
                JOptionPane.showMessageDialog(this, "Xóa thành công!");
                loadTable(NganhController.getAll());
            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại!");
            }
        });

        btnRefresh.addActionListener(e -> loadTable(NganhController.getAll()));

        btnSearch.addActionListener(e -> {
            String keyword = tfSearch.getText();
            loadTable(NganhController.search(keyword));
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

    private Nganh getFormData() {
        return new Nganh(
                tfMaNganh.getText().trim(),
                tfTenNganh.getText().trim(),
                tfTenKhoa.getText().trim()
        );
    }

    private void loadTable(List<Nganh> list) {
        tableModel.setRowCount(0);
        if (list != null) {
            for (Nganh ng : list) {
                tableModel.addRow(new Object[]{
                        ng.getMaNganh(), ng.getTenNganh(), ng.getTenKhoa()
                });
            }
        }
    }

    private String getValue(int row, int col) {
        Object val = tableModel.getValueAt(row, col);
        return val == null ? "" : val.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new NganhView().setVisible(true));
    }
}
