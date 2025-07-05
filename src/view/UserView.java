package view;

import controller.UserController;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class UserView extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private JTextField idField = new JTextField(5);
    private JTextField nameField = new JTextField(10);
    private JTextField emailField = new JTextField(15);

    public UserView() {
        setTitle("User Manager");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Table setup
        model = new DefaultTableModel(new Object[]{"ID", "Name", "Email"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Click row to fill fields
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    idField.setText(model.getValueAt(row, 0).toString());
                    nameField.setText(model.getValueAt(row, 1).toString());
                    emailField.setText(model.getValueAt(row, 2).toString());
                }
            }
        });

        // Input form
        JPanel form = new JPanel();
        form.add(new JLabel("ID:")); form.add(idField);
        form.add(new JLabel("Name:")); form.add(nameField);
        form.add(new JLabel("Email:")); form.add(emailField);
        add(form, BorderLayout.NORTH);

        // Buttons
        JPanel btns = new JPanel();
        JButton load = new JButton("Load All");
        JButton get = new JButton("Get By ID");
        JButton add = new JButton("Add");
        JButton update = new JButton("Update");
        JButton delete = new JButton("Delete");

        load.addActionListener(e -> loadAll());
        get.addActionListener(e -> getById());
        add.addActionListener(e -> {
            addUser();
            loadAll(); // tự load lại
        });
        update.addActionListener(e -> {
            updateUser();
            loadAll();
        });
        delete.addActionListener(e -> {
            deleteUser();
            loadAll();
        });

        btns.add(load); btns.add(get); btns.add(add); btns.add(update); btns.add(delete);
        add(btns, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        loadAll(); // auto load khi mở
    }

    private void loadAll() {
        try {
            List<User> list = UserController.getAllUsers();
            model.setRowCount(0);
            for (User u : list) {
                model.addRow(new Object[]{u.getId(), u.getName(), u.getEmail()});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi tải danh sách", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void getById() {
        try {
            int id = Integer.parseInt(idField.getText());
            User u = UserController.getUserById(id);
            model.setRowCount(0);
            if (u != null)
                model.addRow(new Object[]{u.getId(), u.getName(), u.getEmail()});
            else
                JOptionPane.showMessageDialog(this, "Không tìm thấy user", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi lấy user theo ID", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addUser() {
        try {
            User u = new User(0, nameField.getText(), emailField.getText());
            boolean ok = UserController.insertUser(u);
            JOptionPane.showMessageDialog(this, ok ? "✅ Thêm thành công" : "❌ Thêm thất bại");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi thêm user", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateUser() {
        try {
            int id = Integer.parseInt(idField.getText());
            User u = new User(id, nameField.getText(), emailField.getText());
            boolean ok = UserController.updateUser(u);
            JOptionPane.showMessageDialog(this, ok ? "✅ Cập nhật thành công" : "❌ Cập nhật thất bại");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi cập nhật user", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteUser() {
        try {
            int id = Integer.parseInt(idField.getText());
            boolean ok = UserController.deleteUser(id);
            JOptionPane.showMessageDialog(this, ok ? "✅ Xoá thành công" : "❌ Xoá thất bại");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi xoá user", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
