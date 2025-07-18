package view;

import controller.MonHocController;
import model.MonHoc;
import util.FileExportUtil;
import util.NumberUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MonHocView extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField tfMaMon, tfTenMon, tfSoTC, tfSearch;

    public MonHocView() {
        setTitle("Quản Lý Môn Học");
        setSize(850, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // ===== Panel nhập liệu =====
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Thông tin môn học"));

        tfMaMon = new JTextField();
        tfTenMon = new JTextField();
        tfSoTC = new JTextField();

        inputPanel.add(new JLabel("Mã môn:")); inputPanel.add(tfMaMon);
        inputPanel.add(new JLabel("Tên môn:")); inputPanel.add(tfTenMon);
        inputPanel.add(new JLabel("Số tín chỉ:")); inputPanel.add(tfSoTC);

        // ===== Bảng dữ liệu =====
        tableModel = new DefaultTableModel(new String[]{"STT", "Mã môn", "Tên môn", "Số TC"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // ===== Panel nút điều khiển =====
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

        // ===== Layout chính =====
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(inputPanel);
        mainPanel.add(buttonPanel);
        mainPanel.add(scrollPane);

        add(mainPanel);
        loadTable(MonHocController.getAll());

        // ===== Sự kiện =====
        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                tfMaMon.setText(getSafe(row, 1));
                tfTenMon.setText(getSafe(row, 2));
                tfSoTC.setText(getSafe(row, 3));
            }
        });

        btnAdd.addActionListener(e -> {
            if (!NumberUtil.isInteger(tfSoTC.getText())) {
                JOptionPane.showMessageDialog(this, "Số tín chỉ phải là số nguyên!");
                return;
            }
            MonHoc mh = getFromForm();
            if (MonHocController.insert(mh)) {
                JOptionPane.showMessageDialog(this, "Thêm thành công!");
                loadTable(MonHocController.getAll());
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại!");
            }
        });

        btnUpdate.addActionListener(e -> {
            if (!NumberUtil.isInteger(tfSoTC.getText())) {
                JOptionPane.showMessageDialog(this, "Số tín chỉ phải là số nguyên!");
                return;
            }
            MonHoc mh = getFromForm();
            if (MonHocController.update(mh)) {
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                loadTable(MonHocController.getAll());
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
            }
        });

        btnDelete.addActionListener(e -> {
            String ma = tfMaMon.getText().trim();
            if (MonHocController.delete(ma)) {
                JOptionPane.showMessageDialog(this, "Xóa thành công!");
                loadTable(MonHocController.getAll());
            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại!");
            }
        });

        btnRefresh.addActionListener(e -> loadTable(MonHocController.getAll()));

        btnSearch.addActionListener(e -> {
            String keyword = tfSearch.getText().trim();
            loadTable(MonHocController.search(keyword));
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

    private void loadTable(List<MonHoc> list) {
        tableModel.setRowCount(0);
        int stt = 1;
        if (list != null) {
            for (MonHoc mh : list) {
                tableModel.addRow(new Object[]{
                        stt++, safe(mh.getMaMon()), safe(mh.getTenMon()), mh.getSoTC()
                });
            }
        }
    }

    private MonHoc getFromForm() {
        return new MonHoc(
                tfMaMon.getText().trim(),
                tfTenMon.getText().trim(),
                Integer.parseInt(tfSoTC.getText().trim())
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
        SwingUtilities.invokeLater(() -> new MonHocView().setVisible(true));
    }
}
