package view;

import controller.GiangVienController;
import controller.LopController;
import controller.LopHPController;
import controller.MonHocController;
import model.GiangVien;
import model.Lop;
import model.LopHP;
import model.MonHoc;
import util.FileExportUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

     

public class LopHPView extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField tfMaHP, tfTenHP, tfSearch;
    private JComboBox<String> cbMaLop, cbMaMon, cbMaGV;
    private Map<String, String> mapTenLop = new HashMap<>();
    private Map<String, String> mapTenMon = new HashMap<>();
    private Map<String, String> mapTenGV = new HashMap<>();

    public LopHPView() {
        setTitle("Quản Lý Lớp Học Phần");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Input
        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Thông tin lớp học phần"));

        tfMaHP = new JTextField();
        tfTenHP = new JTextField();
        cbMaLop = new JComboBox<>();
        cbMaMon = new JComboBox<>();
        cbMaGV = new JComboBox<>();

        inputPanel.add(new JLabel("Mã HP:")); inputPanel.add(tfMaHP);
        inputPanel.add(new JLabel("Tên HP:")); inputPanel.add(tfTenHP);
        inputPanel.add(new JLabel("Mã lớp:")); inputPanel.add(cbMaLop);
        inputPanel.add(new JLabel("Mã môn:")); inputPanel.add(cbMaMon);
        inputPanel.add(new JLabel("Mã GV:")); inputPanel.add(cbMaGV);

        loadComboBoxData();

        // Table
        tableModel = new DefaultTableModel(new String[]{"STT", "Mã HP", "Tên HP", "Mã lớp", "Mã môn", "Mã GV"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton btnAdd = new JButton("Thêm");
        JButton btnUpdate = new JButton("Cập nhật");
        JButton btnDelete = new JButton("Xoá");
        JButton btnRefresh = new JButton("Làm mới");
        JButton btnExport = new JButton("Xuất CSV");
        tfSearch = new JTextField(15);
        JButton btnSearch = new JButton("Tìm kiếm");

        buttonPanel.add(btnAdd); buttonPanel.add(btnUpdate); buttonPanel.add(btnDelete);
        buttonPanel.add(btnRefresh); buttonPanel.add(btnExport);
        buttonPanel.add(new JLabel("Tìm:")); buttonPanel.add(tfSearch); buttonPanel.add(btnSearch);

        // Main layout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(inputPanel);
        mainPanel.add(buttonPanel);
        mainPanel.add(scrollPane);
        add(mainPanel);

        loadTable(LopHPController.getAll());

        // Events
        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                tfMaHP.setText(tableModel.getValueAt(row, 1).toString());
                tfTenHP.setText(tableModel.getValueAt(row, 2).toString());
                cbMaLop.setSelectedItem(tableModel.getValueAt(row, 3).toString());
                cbMaMon.setSelectedItem(tableModel.getValueAt(row, 4).toString());
                cbMaGV.setSelectedItem(tableModel.getValueAt(row, 5).toString());
            }
        });

        btnAdd.addActionListener(e -> {
            String maHP = tfMaHP.getText().trim();
            if (LopHPController.getById(maHP) != null) {
                JOptionPane.showMessageDialog(this, "Mã học phần đã tồn tại!");
                return;
            }
            LopHP lhp = getFormData();
            if (LopHPController.insert(lhp)) {
                JOptionPane.showMessageDialog(this, "Thêm thành công!");
                loadTable(LopHPController.getAll());
            }
        });

        btnUpdate.addActionListener(e -> {
            LopHP lhp = getFormData();
            if (LopHPController.update(lhp)) {
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                loadTable(LopHPController.getAll());
            }
        });

        btnDelete.addActionListener(e -> {
            String maHP = tfMaHP.getText().trim();
            if (LopHPController.delete(maHP)) {
                JOptionPane.showMessageDialog(this, "Xoá thành công!");
                loadTable(LopHPController.getAll());
            }
        });

        btnRefresh.addActionListener(e -> loadTable(LopHPController.getAll()));

        btnSearch.addActionListener(e -> {
            String keyword = tfSearch.getText().trim();
            loadTable(LopHPController.search(keyword));
        });

        btnExport.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            int opt = chooser.showSaveDialog(this);
            if (opt == JFileChooser.APPROVE_OPTION) {
                String path = chooser.getSelectedFile().getAbsolutePath();
                FileExportUtil.exportTableToCSV(table, path);
                JOptionPane.showMessageDialog(this, "Đã xuất CSV!");
            }
        });
    }

    private LopHP getFormData() {
        return new LopHP(
                tfMaHP.getText().trim(),
                tfTenHP.getText().trim(),
                (String) cbMaLop.getSelectedItem(),
                (String) cbMaMon.getSelectedItem(),
                (String) cbMaGV.getSelectedItem()
        );
    }

    private void loadTable(List<LopHP> list) {
        tableModel.setRowCount(0);
        int stt = 1;
        for (LopHP l : list) {
            tableModel.addRow(new Object[]{
                    stt++, l.getMaHP(), l.getTenHP(), l.getMaLop(), l.getMaMon(), l.getMaGV()
            });
        }
    }

    private void loadComboBoxData() {
        for (Lop l : LopController.getAll()) {
            cbMaLop.addItem(l.getMaLop());
            mapTenLop.put(l.getMaLop(), l.getTenLop());
        }
        for (MonHoc m : MonHocController.getAll()) {
            cbMaMon.addItem(m.getMaMon());
            mapTenMon.put(m.getMaMon(), m.getTenMon());
        }
        for (GiangVien gv : GiangVienController.getAll()) {
            cbMaGV.addItem(gv.getMaGV());
            mapTenGV.put(gv.getMaGV(), gv.getHoTen());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LopHPView().setVisible(true));
    }
}
