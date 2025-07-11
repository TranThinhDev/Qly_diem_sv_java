import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class HomePageMenu extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField tfSearch, tfDiem;
    private JComboBox<String> cbMaLop, cbMaSV, cbMaMon, cbLanThi, cbHeSo;
    private JRadioButton rdTrue, rdFalse;

    public HomePageMenu() {
        setTitle("MARK MANAGER SYSTEM");
        setSize(1100, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ========== MENU TRÁI ==========
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setPreferredSize(new Dimension(180, 0));
        leftPanel.setBackground(Color.decode("#e6f0ff"));

        String[] menuItems = {
            "Nhập Sinh Viên", "Nhập Giáo Viên", "Nhập Lớp",
            "Nhập Khoa", "Nhập Môn Học", "Nhập Điểm", "Tính Điểm"
        };

        for (String item : menuItems) {
            JButton btn = new JButton(item, new ImageIcon("plus.png")); // icon mẫu
            btn.setFocusPainted(false);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMaximumSize(new Dimension(160, 35));
            leftPanel.add(Box.createVerticalStrut(10));
            leftPanel.add(btn);
        }

        // ========== HEADER ==========
        JLabel header = new JLabel("MARK MANAGER SYSTEM", JLabel.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 26));
        header.setForeground(Color.BLUE);

        // ========== PANEL TRUNG TÂM ==========
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());

        // --- Tabs tìm kiếm ---
        JTabbedPane tabSearch = new JTabbedPane();
        JPanel tab1 = new JPanel(new FlowLayout());
        JPanel tab2 = new JPanel(new FlowLayout());

        tfSearch = new JTextField(15);
        JButton btnSearch = new JButton("Search");

        tab1.add(new JLabel("Nhập Mã Sinh Viên:"));
        tab1.add(tfSearch);
        tab1.add(btnSearch);

        tab2.add(new JLabel("Nhập Mã Lớp:"));
        tab2.add(new JTextField(15));
        tab2.add(new JButton("Search"));

        tabSearch.add("Tìm Sinh Viên Theo Mã", tab1);
        tabSearch.add("Tìm Theo Mã Lớp", tab2);

        // --- Bảng điểm ---
        String[] col = {"Mã SV", "Mã Môn", "Lần thi", "Hệ số", "Điểm", "Trạng thái"};
        tableModel = new DefaultTableModel(col, 0);
        table = new JTable(tableModel);
        JScrollPane scroll = new JScrollPane(table);

        centerPanel.add(tabSearch, BorderLayout.NORTH);
        centerPanel.add(scroll, BorderLayout.CENTER);

        // ========== FORM NHẬP DỮ LIỆU ==========
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(4, 4, 10, 10));
        formPanel.setBorder(new TitledBorder("Thông Tin Điểm"));
        formPanel.setPreferredSize(new Dimension(0, 150));

        cbMaLop = new JComboBox<>(new String[]{"CN1", "CN2"});
        cbMaSV = new JComboBox<>(new String[]{"DL1", "DL2"});
        cbMaMon = new JComboBox<>(new String[]{"MH001", "MH002"});
        cbLanThi = new JComboBox<>(new String[]{"1", "2"});
        cbHeSo = new JComboBox<>(new String[]{"1", "2", "3", "4"});
        tfDiem = new JTextField();

        rdTrue = new JRadioButton("Bật");
        rdFalse = new JRadioButton("Tắt");
        ButtonGroup group = new ButtonGroup();
        group.add(rdTrue);
        group.add(rdFalse);

        formPanel.add(new JLabel("Mã Lớp:")); formPanel.add(cbMaLop);
        formPanel.add(new JLabel("Mã SV:")); formPanel.add(cbMaSV);
        formPanel.add(new JLabel("Mã Môn:")); formPanel.add(cbMaMon);
        formPanel.add(new JLabel("Lần Thi:")); formPanel.add(cbLanThi);
        formPanel.add(new JLabel("Hệ Số:")); formPanel.add(cbHeSo);
        formPanel.add(new JLabel("Điểm:")); formPanel.add(tfDiem);
        formPanel.add(new JLabel("Trạng Thái:"));
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusPanel.add(rdTrue); statusPanel.add(rdFalse);
        formPanel.add(statusPanel);

        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");

        JPanel btnPanel = new JPanel();
        btnPanel.add(btnUpdate);
        btnPanel.add(btnDelete);

        // ========== ADD TO MAIN ==========
        add(header, BorderLayout.NORTH);
        add(leftPanel, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);
        add(formPanel, BorderLayout.SOUTH);
        add(btnPanel, BorderLayout.EAST);

        // ========== DUMMY DATA ==========
        tableModel.addRow(new Object[]{"DL1", "MH003", "1", "1", "10.0", "true"});
        tableModel.addRow(new Object[]{"DL1", "MH004", "2", "4", "9.0", "true"});
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HomePageMenu().setVisible(true));
    }
}
