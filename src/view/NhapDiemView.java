package view;

import controller.KetQuaController;
import controller.LopHPController;
import model.KetQua;
import model.LopHP;
import model.TaiKhoan;
import util.Session;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class NhapDiemView extends JFrame {
    private JTable tableLopHP, tableKetQua;
    private DefaultTableModel modelLopHP, modelKetQua;
    private JTextField tfSearch;
    private String maGV;

    public NhapDiemView() {
        setTitle("Nhập điểm học phần");
        setSize(1100, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        TaiKhoan user = Session.getLoggedInUser();
        
        maGV = user.getTenDN();

        // Panel tìm kiếm
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tfSearch = new JTextField(15);
        JButton btnSearch = new JButton("Tìm lớp");
        JButton btnReload = new JButton("Tải lại");

        topPanel.add(new JLabel("Tìm kiếm:"));
        topPanel.add(tfSearch);
        topPanel.add(btnSearch);
        topPanel.add(btnReload);
        add(topPanel, BorderLayout.NORTH);

        // === BẢNG LỚP HỌC PHẦN ===
        modelLopHP = new DefaultTableModel(new String[]{"Mã HP", "Tên HP", "Mã lớp", "Mã môn", "GV"}, 0);
        tableLopHP = new JTable(modelLopHP);
        JScrollPane scrollLeft = new JScrollPane(tableLopHP);

        // === BẢNG NHẬP ĐIỂM ===
        String[] columns = {"Mã SV", "Điểm CC", "Điểm KT", "Điểm Thi 1", "Điểm Thi 2", "Học kỳ", "Ghi chú", "Điểm TK", "Đánh giá"};
        modelKetQua = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return col >= 1 && col <= 6; // Cho phép sửa từ cột 1 đến 6
            }
        };
        tableKetQua = new JTable(modelKetQua);
        tableKetQua.setRowHeight(28);
        JScrollPane scrollRight = new JScrollPane(tableKetQua);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollLeft, scrollRight);
        splitPane.setResizeWeight(0.35);
        add(splitPane, BorderLayout.CENTER);

        JButton btnSave = new JButton("Lưu điểm");
        add(btnSave, BorderLayout.SOUTH);

        // Sự kiện
        btnSearch.addActionListener(e -> loadLopHP(tfSearch.getText()));
        btnReload.addActionListener(e -> loadLopHP(null));

        tableLopHP.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = tableLopHP.getSelectedRow();
                if (row >= 0) {
                    String maHP = tableLopHP.getValueAt(row, 0).toString();
                    loadKetQua(maHP);
                }
            }
        });

        btnSave.addActionListener(e -> {
            int rowLHP = tableLopHP.getSelectedRow();
            if (rowLHP < 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn lớp học phần!");
                return;
            }
            String maHP = tableLopHP.getValueAt(rowLHP, 0).toString();

            for (int i = 0; i < modelKetQua.getRowCount(); i++) {
                try {
                    String maSV = modelKetQua.getValueAt(i, 0).toString();
                    double cc = parseDouble(modelKetQua.getValueAt(i, 1));
                    double kt = parseDouble(modelKetQua.getValueAt(i, 2));
                    double thi1 = parseDouble(modelKetQua.getValueAt(i, 3));
                    double thi2 = parseDouble(modelKetQua.getValueAt(i, 4));
                    String hocKy = modelKetQua.getValueAt(i, 5).toString();
                    String ghiChu = modelKetQua.getValueAt(i, 6).toString();

                    double diemTK = cc * 0.1 + kt * 0.3 + thi1 * 0.6;
                    String danhGia = diemTK >= 4 ? "Đạt" : "Không đạt";

                    // Cập nhật vào bảng
                    modelKetQua.setValueAt(diemTK, i, 7);
                    modelKetQua.setValueAt(danhGia, i, 8);

                    KetQua kq = new KetQua(maSV, maHP, hocKy, ghiChu, 1, cc, kt, thi1, thi2);
                    KetQuaController.update(kq);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            JOptionPane.showMessageDialog(this, "Đã lưu điểm thành công!");
        });

        // Load ban đầu
        loadLopHP(null);
        setVisible(true);
    }

    private void loadLopHP(String keyword) {
        modelLopHP.setRowCount(0);
        List<LopHP> list = (keyword == null || keyword.isEmpty())
                ? LopHPController.getByGiangVien(maGV)
                : LopHPController.searchByGiangVien(maGV, keyword);

        for (LopHP lhp : list) {
            modelLopHP.addRow(new Object[]{
                    lhp.getMaHP(), lhp.getTenHP(), lhp.getMaLop(), lhp.getMaMon(), lhp.getMaGV()
            });
        }

        if (modelLopHP.getRowCount() > 0) {
            tableLopHP.setRowSelectionInterval(0, 0);
            String maHP = tableLopHP.getValueAt(0, 0).toString();
            loadKetQua(maHP);
        } else {
            modelKetQua.setRowCount(0);
        }
    }

    private void loadKetQua(String maHP) {
        modelKetQua.setRowCount(0);
        List<KetQua> list = KetQuaController.getByMaHP(maHP);
        for (KetQua kq : list) {
            double diemTK = kq.getDiemCC() * 0.1 + kq.getDiemKT() * 0.3 + kq.getDiemThiLan1() * 0.6;
            String danhGia = diemTK >= 4 ? "Đạt" : "Không đạt";

            modelKetQua.addRow(new Object[]{
                    kq.getMaSV(),
                    kq.getDiemCC(),
                    kq.getDiemKT(),
                    kq.getDiemThiLan1(),
                    kq.getDiemThiLan2(),
                    kq.getHocKy(),
                    kq.getGhiChu(),
                    diemTK,
                    danhGia
            });
        }
    }

    private double parseDouble(Object val) {
        try {
            return Double.parseDouble(val.toString());
        } catch (Exception e) {
            return 0;
        }
    }

    public static void main(String[] args) {
        Session.setLoggedInUser(new TaiKhoan("GV00001", "73DCTT22419", "Thinh", "Giangvie"));
        SwingUtilities.invokeLater(NhapDiemView::new);
    }
}
