package view;

import controller.LopController;
import controller.NganhController;
import model.Lop;
import model.Nganh;
import util.FileExportUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LopView extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField tfMaLop, tfTenLop, tfMaGV, tfSearch, tfTenNganh;
    private JComboBox<String> cbMaNganh;
    private Map<String, String> maTenNganhMap = new HashMap<>();

    public LopView() {
        setTitle("Quản Lý Lớp");
        setSize(950, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // ===== Panel nhập liệu =====
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        tfMaLop = new JTextField();
        tfTenLop = new JTextField();
        cbMaNganh = new JComboBox<>();
        tfTenNganh = new JTextField(); tfTenNganh.setEditable(false);
        tfMaGV = new JTextField();

        inputPanel.setBorder(BorderFactory.createTitledBorder("Thông tin lớp"));

        inputPanel.add(new JLabel("Mã lớp:")); inputPanel.add(tfMaLop);
        inputPanel.add(new JLabel("Tên lớp:")); inputPanel.add(tfTenLop);
        inputPanel.add(new JLabel("Mã ngành:")); inputPanel.add(cbMaNganh);
        inputPanel.add(new JLabel("Tên ngành:")); inputPanel.add(tfTenNganh);
        inputPanel.add(new JLabel("Mã GV:")); inputPanel.add(tfMaGV);

        // ===== Load ngành vào ComboBox =====
        List<Nganh> dsNganh = NganhController.getAll();
        for (Nganh ng : dsNganh) {
            cbMaNganh.addItem(ng.getMaNganh());
            maTenNganhMap.put(ng.getMaNganh(), ng.getTenNganh());
        }
        cbMaNganh.addActionListener(e -> {
            String ma = (String) cbMaNganh.getSelectedItem();
            tfTenNganh.setText(maTenNganhMap.getOrDefault(ma, ""));
        });
        if (cbMaNganh.getItemCount() > 0) cbMaNganh.setSelectedIndex(0);

        // ===== Table =====
        tableModel = new DefaultTableModel(new String[]{"Mã lớp", "Tên lớp", "Mã ngành", "Tên ngành", "Mã GV"}, 0);
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
        loadTable(LopController.getAll());

        // ===== Sự kiện =====
        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                tfMaLop.setText(tableModel.getValueAt(row, 0).toString());
                tfTenLop.setText(tableModel.getValueAt(row, 1).toString());
                cbMaNganh.setSelectedItem(tableModel.getValueAt(row, 2).toString());
                tfTenNganh.setText(tableModel.getValueAt(row, 3).toString());
                tfMaGV.setText(tableModel.getValueAt(row, 4).toString());
            }
        });

        btnAdd.addActionListener(e -> {
            Lop l = getFormData();
            if (LopController.insert(l)) {
                JOptionPane.showMessageDialog(this, "Thêm thành công!");
                loadTable(LopController.getAll());
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại!");
            }
        });

        btnUpdate.addActionListener(e -> {
            Lop l = getFormData();
            if (LopController.update(l)) {
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                loadTable(LopController.getAll());
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
            }
        });

        btnDelete.addActionListener(e -> {
            String ma = tfMaLop.getText();
            if (LopController.delete(ma)) {
                JOptionPane.showMessageDialog(this, "Xóa thành công!");
                loadTable(LopController.getAll());
            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại!");
            }
        });

        btnRefresh.addActionListener(e -> loadTable(LopController.getAll()));

        btnSearch.addActionListener(e -> {
            String keyword = tfSearch.getText();
            loadTable(LopController.search(keyword));
        });

        btnExport.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            int option = chooser.showSaveDialog(LopView.this);
            if (option == JFileChooser.APPROVE_OPTION) {
                String path = chooser.getSelectedFile().getAbsolutePath();
                FileExportUtil.exportTableToCSV(table, path);
                JOptionPane.showMessageDialog(this, "Đã xuất file CSV!");
            }
        });
    }

    private Lop getFormData() {
        return new Lop(
                tfMaLop.getText(),
                tfTenLop.getText(),
                (String) cbMaNganh.getSelectedItem(),
                tfMaGV.getText()
        );
    }

    private void loadTable(List<Lop> list) {
        tableModel.setRowCount(0);
        if (list != null) {
            for (Lop l : list) {
                String tenNganh = maTenNganhMap.getOrDefault(l.getMaNganh(), "");
                tableModel.addRow(new Object[]{
                        l.getMaLop(), l.getTenLop(), l.getMaNganh(), tenNganh, l.getMaGV()
                });
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LopView().setVisible(true));
    }
}
