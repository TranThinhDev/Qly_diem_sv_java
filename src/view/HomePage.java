package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.TaiKhoan;


public class HomePage extends JFrame {

    public HomePage() {
        setTitle("Hệ thống Quản lý điểm sinh viên");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("HỆ THỐNG QUẢN LÝ ĐIỂM SINH VIÊN", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(3, 4, 20, 20));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Thêm các nút
        buttonPanel.add(createButton("icons/student.png", "Sinh viên", e -> new SinhVienView().setVisible(true)));
        buttonPanel.add(createButton("icons/subject.png", "Môn học", e -> new MonHocView().setVisible(true)));
        buttonPanel.add(createButton("icons/nganh.png", "Ngành", e -> new NganhView().setVisible(true)));
        buttonPanel.add(createButton("icons/lop.png", "Lớp", e -> new LopView().setVisible(true)));
        buttonPanel.add(createButton("icons/lophp.png", "Lớp HP", e -> new LopView().setVisible(true)));
        buttonPanel.add(createButton("icons/teacher.png", "Giảng viên", e -> new GiangVienView().setVisible(true)));
        buttonPanel.add(createButton("icons/account.png", "Tài khoản", e -> new AccountView().setVisible(true)));
        //buttonPanel.add(createButton("icons/user.png", "Người dùng", e -> new UserView().setVisible(true)));
       //buttonPanel.add(createButton("icons/admin.png", "Admin", e -> {TaiKhoan adminAccount = new TaiKhoan(); // Tạo đối tượng TaiKhoan new AdminView(adminAccount).setVisible(true);

        buttonPanel.add(createButton("icons/exit.png", "Thoát", e -> System.exit(0)));

        add(buttonPanel, BorderLayout.CENTER);
    }

    private JButton createButton(String iconPath, String text, ActionListener action) {
        ImageIcon icon = new ImageIcon(iconPath);
        Image scaledImg = icon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
        icon = new ImageIcon(scaledImg);

        JButton button = new JButton(text, icon);
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(120, 120));
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        button.addActionListener(action);
        return button;
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new HomePage().setVisible(true);
        });
    }
}