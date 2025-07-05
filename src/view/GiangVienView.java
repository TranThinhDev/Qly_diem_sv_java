package view;

import controller.GiangVienController;
import model.GiangVien;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class GiangVienView extends JFrame {
    private JTextField txtMa, txtTen, txtEmail, txtDiaChi, txtSDT, txtThanhTich, txtKhenThuong, txtSearch;
    private JTable table;
    private DefaultTableModel model;

    public GiangVienView() {
        setTitle("Quản lý giảng viên");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Form input
        JPanel form = new JPanel(new GridLayout(7, 2));
        txtMa = new JTextField();
        txtTen = new JTextField();
        txtEmail = new JTextField();
        txtDiaChi = new JTextField();
        txtSDT = new JTextField();
        txtThanhTich = new JTextField();
        txtKhenThuong = new JTextField();
        form.add(new JLabel("Mã GV:")); form.add(txtMa);
        form.add(new JLabel("Họ tên:")); form.add(txtTen);
        form.add(new JLabel("Email:")); form.add(txtEmail);
        form.add(new JLabel("Địa chỉ:")); form.add(txtDiaChi);
        form.add(new JLabel("SĐT:")); form.add(txtSDT);
        form.add(new JLabel("Thành tích:")); form.add(txtThanhTich);
        form.add(new JLabel("Khen thưởng:")); form.add(txtKhenThuong);

        add(form, BorderLayout.NORTH);

        // Table
        model = new DefaultTableModel(new String[]{"Mã", "Tên", "Email", "Địa chỉ", "SĐT", "Thành tích", "Khen thưởng"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Control
        JPanel control = new JPanel();
        JButton btnAdd = new JButton("Thêm");
        JButton btnUpdate = new JButton("Sửa");
        JButton btnDelete = new JButton("Xoá");
        txtSearch = new JTextField(10);
        JButton btnSearch = new JButton("Tìm");

        control.add(btnAdd); control.add(btnUpdate); control.add(btnDelete);
        control.add(txtSearch); control.add(btnSearch);
        add(control, BorderLayout.SOUTH);

        // Events
        btnAdd.addActionListener(e -> {
            GiangVien gv = getInput();
            if (GiangVienController.add(gv)) {
                JOptionPane.showMessageDialog(this, "Đã thêm!");
                loadTable();
            }
        });

        btnUpdate.addActionListener(e -> {
            GiangVien gv = getInput();
            if (GiangVienController.update(gv)) {
                JOptionPane.showMessageDialog(this, "Đã cập nhật!");
                loadTable();
            }
        });

        btnDelete.addActionListener(e -> {
            String ma = txtMa.getText();
            if (GiangVienController.delete(ma)) {
                JOptionPane.showMessageDialog(this, "Đã xoá!");
                loadTable();
            }
        });

        btnSearch.addActionListener(e -> {
            String keyword = txtSearch.getText();
            loadTable(keyword);
        });

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                txtMa.setText(model.getValueAt(row, 0).toString());
                txtTen.setText(model.getValueAt(row, 1).toString());
                txtEmail.setText(model.getValueAt(row, 2).toString());
                txtDiaChi.setText(model.getValueAt(row, 3).toString());
                txtSDT.setText(model.getValueAt(row, 4).toString());
                txtThanhTich.setText(model.getValueAt(row, 5).toString());
                txtKhenThuong.setText(model.getValueAt(row, 6).toString());
            }
        });

        loadTable();
        setVisible(true);
    }

    private void loadTable() {
        loadTable(null);
    }

    private void loadTable(String keyword) {
        model.setRowCount(0);
        List<GiangVien> list = keyword == null || keyword.isEmpty()
                ? GiangVienController.getAll()
                : GiangVienController.search(keyword);
        for (GiangVien gv : list) {
            model.addRow(new Object[]{
                gv.getMaGV(), gv.getHoTen(), gv.getEmail(), gv.getDiaChi(),
                gv.getSdt(), gv.getThanhTich(), gv.getKhenThuong()
            });
        }
    }

    private GiangVien getInput() {
        return new GiangVien(
            txtMa.getText(),
            txtTen.getText(),
            txtDiaChi.getText(),
            txtEmail.getText(),
            txtSDT.getText(),
            txtThanhTich.getText(),
            txtKhenThuong.getText()
        );
    }

    public static void main(String[] args) {
        new GiangVienView();
    }
}
