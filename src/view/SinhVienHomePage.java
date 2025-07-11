package view;

import javax.swing.*;
import java.awt.*;

public class SinhVienHomePage extends JFrame {
    public SinhVienHomePage() {
        setTitle("Trang chủ Sinh viên");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel label = new JLabel("Chào mừng sinh viên!", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 20));

        JButton btnXemDiem = new JButton("Xem bảng điểm");
        btnXemDiem.addActionListener(e -> {
            // Gọi lớp XemDiemView hoặc một form xem bảng điểm
            JOptionPane.showMessageDialog(this, "Chức năng đang phát triển...");
        });

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(label, BorderLayout.NORTH);
        panel.add(btnXemDiem, BorderLayout.CENTER);

        add(panel);
        setVisible(true);
    }
}
