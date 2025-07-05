/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import model.TaiKhoan;

import javax.swing.*;
import java.awt.*;

public class AdminView extends JFrame {
    public AdminView(TaiKhoan user) {
        setTitle("Quản trị viên: " + user.getTenDN());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Chào mừng admin " + user.getTenDN(), JLabel.CENTER);
        add(label, BorderLayout.CENTER);

        JButton logoutBtn = new JButton("Đăng xuất");
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginView(); // quay lại màn hình login
        });

        add(logoutBtn, BorderLayout.SOUTH);
        setSize(400, 200);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}

