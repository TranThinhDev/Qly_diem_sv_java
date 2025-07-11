package view;

import controller.SinhVienController;
import model.SinhVien;
import model.TaiKhoan;
import util.Session;

import javax.swing.*;
import java.awt.*;

public class SinhVienHomePage extends JFrame {
    public SinhVienHomePage() {
        setTitle("Trang chủ Sinh viên");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        TaiKhoan tk = Session.getLoggedInUser();
        if (tk == null) {
            JOptionPane.showMessageDialog(this, "Không xác định sinh viên!");
            dispose();
            return;
        }

        // Lấy thông tin sinh viên từ DAO
        SinhVien sv = SinhVienController.getById(tk.getTenDN());

        // ==== GIAO DIỆN ====
        JLabel title = new JLabel("Chào mừng sinh viên!", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));

        JPanel infoPanel = new JPanel(new GridLayout(0, 2, 10, 5));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Thông tin cá nhân"));

        if (sv != null) {
            infoPanel.add(new JLabel("Mã sinh viên:"));
            infoPanel.add(new JLabel(sv.getMaSV()));
            infoPanel.add(new JLabel("Họ tên:"));
            infoPanel.add(new JLabel(sv.getHoTen()));
            infoPanel.add(new JLabel("Lớp:"));
            infoPanel.add(new JLabel(sv.getMaLop()));
            infoPanel.add(new JLabel("Email:"));
            infoPanel.add(new JLabel(sv.getEmail()));
            infoPanel.add(new JLabel("SĐT:"));
            infoPanel.add(new JLabel(sv.getSdt()));
        } else {
            infoPanel.add(new JLabel("Không tìm thấy thông tin sinh viên."));
        }

        JButton btnXemDiem = new JButton("Xem bảng điểm");
        btnXemDiem.setFont(new Font("Arial", Font.PLAIN, 14));
        btnXemDiem.addActionListener(e -> new BangDiemSinhVienView());


        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.add(infoPanel, BorderLayout.CENTER);
        centerPanel.add(btnXemDiem, BorderLayout.SOUTH);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        add(title, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        setVisible(true);
    }
}
