package view;

import model.TaiKhoan;
import util.Session;

import javax.swing.*;
import java.awt.*;

public class GiangVienHomePage extends JFrame {

    public GiangVienHomePage() {
        setTitle("Trang chủ Giảng viên");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        TaiKhoan gv = Session.getLoggedInUser(); // Lấy giảng viên đã đăng nhập

        // ==== HEADER ====
        JLabel titleLabel = new JLabel("🎓 Trang chủ Giảng viên", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 102, 204));

        // ==== THÔNG TIN GIẢNG VIÊN ====
        JPanel infoPanel = new JPanel(new GridLayout(3, 1));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Thông tin giảng viên"));
        infoPanel.add(new JLabel("🔑 Mã giảng viên: " + gv.getTenDN()));
        infoPanel.add(new JLabel("👤 Họ tên: " + gv.getHoTen()));
        infoPanel.add(new JLabel("🔐 Quyền truy cập: " + gv.getQuyen()));

        // ==== NÚT CHỨC NĂNG ====
        JButton btnNhapDiem = new JButton("✏️ Nhập điểm");
        btnNhapDiem.setFont(new Font("Arial", Font.BOLD, 16));
        btnNhapDiem.setBackground(new Color(0, 153, 76));
        btnNhapDiem.setForeground(Color.WHITE);
        btnNhapDiem.setFocusPainted(false);
        btnNhapDiem.setPreferredSize(new Dimension(200, 40));
        btnNhapDiem.addActionListener(e -> new NhapDiemView());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnNhapDiem);

        // ==== MAIN PANEL ====
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(infoPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }
}
