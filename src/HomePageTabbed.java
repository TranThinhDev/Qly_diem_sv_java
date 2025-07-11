import javax.swing.*;
import java.awt.*;

public class HomePageTabbed extends JFrame {
    private String role;

    public HomePageTabbed(String role) {
        this.role = role;
        setTitle("Hệ thống quản lý sinh viên - Trang chủ (TabbedPane)");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();

        if ("quanly".equals(role)) {
            tabs.add("QL Lớp", new JLabel("Giao diện quản lý lớp"));
            tabs.add("QL Sinh viên", new JLabel("Giao diện quản lý sinh viên"));
            tabs.add("QL Giảng viên", new JLabel("Giao diện quản lý giảng viên"));
            tabs.add("QL Môn học", new JLabel("Giao diện quản lý môn học"));
            tabs.add("QL Học phần", new JLabel("Giao diện quản lý học phần"));
            tabs.add("QL Tài khoản", new JLabel("Giao diện quản lý tài khoản"));
        } else if ("giangvie".equals(role)) {
            tabs.add("Lớp phụ trách", new JLabel("Danh sách lớp giảng dạy"));
            tabs.add("Kết quả", new JLabel("Cập nhật điểm kết quả"));
            tabs.add("Bảng điểm", new JLabel("Xem bảng điểm"));
        } else if ("sinhvie".equals(role)) {
            tabs.add("Thông tin cá nhân", new JLabel("Thông tin sinh viên"));
            tabs.add("Kết quả học tập", new JLabel("Xem kết quả học tập"));
        }

        add(tabs);
        setVisible(true);
    }

    public static void main(String[] args) {
        // Giả lập đăng nhập: role là "quanly", "giangvie", hoặc "sinhvie"
        SwingUtilities.invokeLater(() -> new HomePageTabbed("quanly"));
    }
}
