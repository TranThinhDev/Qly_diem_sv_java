/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import controller.LoginController;
import model.TaiKhoan;
import util.Session;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {
    private JTextField usernameField = new JTextField(15);
    private JPasswordField passwordField = new JPasswordField(15);
    private JButton loginButton = new JButton("Đăng nhập");

    public LoginView() {
        setTitle("Đăng nhập hệ thống");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("Tên đăng nhập:"));
        panel.add(usernameField);
        panel.add(new JLabel("Mật khẩu:"));
        panel.add(passwordField);
        panel.add(new JLabel());
        panel.add(loginButton);

        add(panel, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        loginButton.addActionListener(e -> login());
    }

    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        TaiKhoan tk = LoginController.login(username, password);
        if (tk != null) {
            Session.setLoggedInUser(tk);
            JOptionPane.showMessageDialog(this, "✅ Đăng nhập thành công với quyền: " + tk.getQuyen());

            dispose(); // đóng màn hình login

            // Mở màn hình tương ứng
            switch (tk.getQuyen().toLowerCase()) {
                case "quanly":
                    new HomePage().setVisible(true); break;// đúng
                case "giangvie":
                    new GiangVienHomePage(); break;
                case "sinhvie":
                    new SinhVienHomePage(); break;
                default:
                    JOptionPane.showMessageDialog(this, "❌ Quyền không hợp lệ.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "❌ Sai tên đăng nhập hoặc mật khẩu");
        }
    }
}

