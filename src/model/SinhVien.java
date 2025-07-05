package model;

public class SinhVien {
    private String maSV;
    private String hoTen;
    private String maLop;
    private String ngaySinh;
    private String gioiTinh;
    private String email;
    private String sdt;
    private String diaChi;
    private String trangThai;
    private String khoaHoc;

    // Constructors, getters, setters
    public SinhVien() {}

    public SinhVien(String maSV, String hoTen, String maLop, String ngaySinh, String gioiTinh,
                    String email, String sdt, String diaChi, String trangThai, String khoaHoc) {
        this.maSV = maSV;
        this.hoTen = hoTen;
        this.maLop = maLop;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.email = email;
        this.sdt = sdt;
        this.diaChi = diaChi;
        this.trangThai = trangThai;
        this.khoaHoc = khoaHoc;
    }

    // Getters and setters ...

    public String getMaSV() {
        return maSV;
    }

    public void setMaSV(String maSV) {
        this.maSV = maSV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getMaLop() {
        return maLop;
    }

    public void setMaLop(String maLop) {
        this.maLop = maLop;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getKhoaHoc() {
        return khoaHoc;
    }

    public void setKhoaHoc(String khoaHoc) {
        this.khoaHoc = khoaHoc;
    }
}
