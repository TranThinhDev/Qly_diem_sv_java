package view;

import controller.AccountController;
import model.TaiKhoan;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AccountView extends JFrame {
    private JTable table;
    private JTextField txtUser = new JTextField(10);
    private JTextField txtPass = new JTextField(10);
    private JTextField txtName = new JTextField(15);
    private JTextField txtRole = new JTextField(10);
    private DefaultTableModel model;

    public AccountView() {
        setTitle("Quản lý tài khoản");
        setLayout(new BorderLayout());

        model = new DefaultTableModel(new Object[]{"Tên đăng nhập", "Mật khẩu", "Họ tên", "Quyền"}, 0);
        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        add(scroll, BorderLayout.CENTER);

        JPanel form = new JPanel();
        form.add(new JLabel("Tên ĐN:")); form.add(txtUser);
        form.add(new JLabel("Mật khẩu:")); form.add(txtPass);
        form.add(new JLabel("Họ tên:")); form.add(txtName);
        form.add(new JLabel("Quyền:")); form.add(txtRole);
        add(form, BorderLayout.NORTH);

        JPanel btns = new JPanel();
        JButton btnLoad = new JButton("Tải lại");
        JButton btnAdd = new JButton("Thêm");
        JButton btnUpdate = new JButton("Sửa");
        JButton btnDelete = new JButton("Xoá");

        btns.add(btnLoad); btns.add(btnAdd); btns.add(btnUpdate); btns.add(btnDelete);
        add(btns, BorderLayout.SOUTH);

        btnLoad.addActionListener(e -> loadData());
        btnAdd.addActionListener(e -> insert());
        btnUpdate.addActionListener(e -> update());
        btnDelete.addActionListener(e -> delete());

        table.getSelectionModel().addListSelectionListener(e -> {
            int i = table.getSelectedRow();
            if (i >= 0) {
                txtUser.setText(model.getValueAt(i, 0).toString());
                txtPass.setText(model.getValueAt(i, 1).toString());
                txtName.setText(model.getValueAt(i, 2).toString());
                txtRole.setText(model.getValueAt(i, 3).toString());
            }
        });

        setSize(600, 400);
        setLocationRelativeTo(null);
        setVisible(true);

        loadData();
    }

    private void loadData() {
        try {
            List<TaiKhoan> list = AccountController.getAll();
            model.setRowCount(0);
            for (TaiKhoan tk : list) {
                model.addRow(new Object[]{tk.getTenDN(), tk.getMatKhau(), tk.getHoTen(), tk.getQuyen()});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void insert() {
        TaiKhoan tk = new TaiKhoan(txtUser.getText(), txtPass.getText(), txtName.getText(), txtRole.getText());
        boolean ok = AccountController.insert(tk);
        if (ok) loadData();
    }

    private void update() {
        TaiKhoan tk = new TaiKhoan(txtUser.getText(), txtPass.getText(), txtName.getText(), txtRole.getText());
        boolean ok = AccountController.update(tk);
        if (ok) loadData();
    }

    private void delete() {
        String tenDN = txtUser.getText();
        boolean ok = AccountController.delete(tenDN);
        if (ok) loadData();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AccountView::new);
    }
}
