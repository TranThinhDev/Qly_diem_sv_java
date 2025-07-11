package view;

import com.google.gson.Gson;
import model.BangDiem;
import model.TaiKhoan;
import util.Session;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class BangDiemSinhVienView extends JFrame {

    private JLabel lbMaSV, lbTongTC, lbHe10, lbHe4;

    public BangDiemSinhVienView() {
        setTitle("Bảng điểm sinh viên");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // đóng chứ không thoát ứng dụng

        TaiKhoan tk = Session.getLoggedInUser();
        if (tk == null || !tk.getQuyen().equalsIgnoreCase("sinhvie")) {
            JOptionPane.showMessageDialog(this, "Vui lòng đăng nhập bằng tài khoản sinh viên.");
            dispose();
            return;
        }

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Mã sinh viên:"));
        lbMaSV = new JLabel();
        panel.add(lbMaSV);

        panel.add(new JLabel("Tổng số tín chỉ:"));
        lbTongTC = new JLabel();
        panel.add(lbTongTC);

        panel.add(new JLabel("Điểm trung bình hệ 10:"));
        lbHe10 = new JLabel();
        panel.add(lbHe10);

        panel.add(new JLabel("Điểm trung bình hệ 4:"));
        lbHe4 = new JLabel();
        panel.add(lbHe4);

        JButton btnDong = new JButton("Đóng");
        btnDong.addActionListener(e -> dispose());
        panel.add(new JLabel());
        panel.add(btnDong);

        add(panel);
        loadBangDiem(tk.getTenDN());

        setVisible(true);
    }

    private void loadBangDiem(String maSV) {
        try {
            String apiURL = "http://localhost:8080/MyWebApp/api/bangdiem?maSV=" + maSV;
            HttpURLConnection conn = (HttpURLConnection) new URL(apiURL).openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() == 200) {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream(), "UTF-8"));
                BangDiem bd = new Gson().fromJson(reader, BangDiem.class);

                if (bd != null) {
                    lbMaSV.setText(bd.getMaSV());
                    lbTongTC.setText(String.valueOf(bd.getTongSoTC()));
                    lbHe10.setText(String.format("%.2f", bd.getDiemHe10()));
                    lbHe4.setText(String.format("%.2f", bd.getDiemHe4()));
                } else {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy dữ liệu bảng điểm.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi khi gọi API: " + conn.getResponseCode());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Không thể kết nối đến API.");
        }
    }
}
