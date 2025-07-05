package view;

import controller.MonHocController;
import model.MonHoc;

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
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(2, 4, 10, 5));

        tfMaMon = new JTextField();
        tfTenMon = new JTextField();
        tfSoTC = new JTextField();

        inputPanel.add(new JLabel("Mã Môn:")); inputPanel.add(tfMaMon);
        inputPanel.add(new JLabel("Tên Môn:")); inputPanel.add(tfTenMon);
        inputPanel.add(new JLabel("Số TC:")); inputPanel.add(tfSoTC);

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
        buttonPanel.add(new JLabel("Tìm: ")); buttonPanel.add(tfSearch); buttonPanel.add(btnSearch);

        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"Mã Môn", "Tên Môn", "Số TC"});
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        loadTable(MonHocController.getAll());

        btnAdd.addActionListener(e -> {
            MonHoc mh = getFromForm();
            if (MonHocController.insert(mh)) {
                JOptionPane.showMessageDialog(this, "Thêm thành công!");
                loadTable(MonHocController.getAll());
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại!");
            }
        });

        btnUpdate.addActionListener(e -> {
            MonHoc mh = getFromForm();
            if (MonHocController.update(mh)) {
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                loadTable(MonHocController.getAll());
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
            }
        });

        btnDelete.addActionListener(e -> {
            String maMon = tfMaMon.getText();
            if (MonHocController.delete(maMon)) {
                JOptionPane.showMessageDialog(this, "Xóa thành công!");
                loadTable(MonHocController.getAll());
            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại!");
            }
        });

        btnRefresh.addActionListener(e -> loadTable(MonHocController.getAll()));

        btnSearch.addActionListener(e -> {
            String keyword = tfSearch.getText();
            loadTable(MonHocController.search(keyword));
        });

        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                tfMaMon.setText(String.valueOf(tableModel.getValueAt(row, 0)));
                tfTenMon.setText(String.valueOf(tableModel.getValueAt(row, 1)));
                tfSoTC.setText(String.valueOf(tableModel.getValueAt(row, 2)));
            }
        });
    }

    private void loadTable(List<MonHoc> list) {
        tableModel.setRowCount(0);
        if (list != null) {
            for (MonHoc mh : list) {
                tableModel.addRow(new Object[]{mh.getMaMon(), mh.getTenMon(), mh.getSoTC()});
            }
        }
    }

    private MonHoc getFromForm() {
        return new MonHoc(
                tfMaMon.getText(),
                tfTenMon.getText(),
                Integer.parseInt(tfSoTC.getText())
        );
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MonHocView().setVisible(true));
    }
}
