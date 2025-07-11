package view;

import controller.AccountController;
import model.TaiKhoan;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AccountView extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField tfTenDN, tfMatKhau, tfHoTen, tfSearch;
    private JComboBox<String> cbQuyen;

    public AccountView() {
        setTitle("Quản Lý Tài Khoản");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // ===== Panel nhập liệu =====
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Thông tin tài khoản"));

        tfTenDN = new JTextField();
        tfMatKhau = new JTextField();
        tfHoTen = new JTextField();
        cbQuyen = new JComboBox<>(new String[]{"Sinhvie", "Giangvie", "Quanly"});

        inputPanel.add(new JLabel("Tên đăng nhập:")); inputPanel.add(tfTenDN);
        inputPanel.add(new JLabel("Mật khẩu:")); inputPanel.add(tfMatKhau);
        inputPanel.add(new JLabel("Họ tên:")); inputPanel.add(tfHoTen);
        inputPanel.add(new JLabel("Quyền:")); inputPanel.add(cbQuyen);

        // ===== Table =====
        tableModel = new DefaultTableModel(new String[]{"STT", "Tên đăng nhập", "Mật khẩu", "Họ tên", "Quyền"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // ===== Panel nút bấm =====
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnAdd = new JButton("Thêm");
        JButton btnUpdate = new JButton("Cập nhật");
        JButton btnDelete = new JButton("Xóa");
        JButton btnRefresh = new JButton("Làm mới");
        tfSearch = new JTextField(15);
        JButton btnSearch = new JButton("Tìm");

        buttonPanel.add(btnAdd); buttonPanel.add(btnUpdate); buttonPanel.add(btnDelete);
        buttonPanel.add(btnRefresh);
        buttonPanel.add(new JLabel("Tìm kiếm:")); buttonPanel.add(tfSearch); buttonPanel.add(btnSearch);

        // ===== Main Panel =====
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(inputPanel);
        mainPanel.add(buttonPanel);
        mainPanel.add(scrollPane);

        add(new JScrollPane(mainPanel));
        loadTable(AccountController.getAll());

        // ===== Sự kiện =====
        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                tfTenDN.setText(getValue(row, 1));
                tfMatKhau.setText(getValue(row, 2));
                tfHoTen.setText(getValue(row, 3));
                cbQuyen.setSelectedItem(getValue(row, 4));
            }
        });

        btnAdd.addActionListener(e -> {
            TaiKhoan tk = getFormData();
            if (AccountController.insert(tk)) {
                JOptionPane.showMessageDialog(this, "Thêm thành công!");
                loadTable(AccountController.getAll());
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại!");
            }
        });

        btnUpdate.addActionListener(e -> {
            TaiKhoan tk = getFormData();
            if (AccountController.update(tk)) {
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                loadTable(AccountController.getAll());
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
            }
        });

        btnDelete.addActionListener(e -> {
            String ten = tfTenDN.getText();
            if (AccountController.delete(ten)) {
                JOptionPane.showMessageDialog(this, "Xóa thành công!");
                try {
                    loadTable(AccountController.getAll());
                } catch (Exception ex) {
                    Logger.getLogger(AccountView.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại!");
            }
        });

        btnRefresh.addActionListener(e -> {
            try {
                loadTable(AccountController.getAll());
            } catch (Exception ex) {
                Logger.getLogger(AccountView.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnSearch.addActionListener(e -> {
            String keyword = tfSearch.getText().trim();
            List<TaiKhoan> result = AccountController.search(keyword); // cần có hàm search
            loadTable(result);
        });
    }

    private void loadTable(List<TaiKhoan> list) {
        tableModel.setRowCount(0);
        int stt = 1;
        if (list != null) {
            for (TaiKhoan tk : list) {
                tableModel.addRow(new Object[]{
                        stt++, tk.getTenDN(), tk.getMatKhau(), tk.getHoTen(), tk.getQuyen()
                });
            }
        }
    }

    private TaiKhoan getFormData() {
        return new TaiKhoan(
                tfTenDN.getText().trim(),
                tfMatKhau.getText().trim(),
                tfHoTen.getText().trim(),
                (String) cbQuyen.getSelectedItem()
        );
    }

    private String getValue(int row, int col) {
        Object val = tableModel.getValueAt(row, col);
        return val == null ? "" : val.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AccountView().setVisible(true));
    }
}
