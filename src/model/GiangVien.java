package model;

public class GiangVien {
    private String maGV;
    private String hoTen;
    private String diaChi;
    private String email;
    private String sdt;
    private String thanhTich;
    private String khenThuong;

    public GiangVien() {}

    public GiangVien(String maGV, String hoTen, String diaChi, String email, String sdt, String thanhTich, String khenThuong) {
        this.maGV = maGV;
        this.hoTen = hoTen;
        this.diaChi = diaChi;
        this.email = email;
        this.sdt = sdt;
        this.thanhTich = thanhTich;
        this.khenThuong = khenThuong;
    }

    // Getters and setters
    public String getMaGV() { return maGV; }
    public void setMaGV(String maGV) { this.maGV = maGV; }

    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }

    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSdt() { return sdt; }
    public void setSdt(String sdt) { this.sdt = sdt; }

    public String getThanhTich() { return thanhTich; }
    public void setThanhTich(String thanhTich) { this.thanhTich = thanhTich; }

    public String getKhenThuong() { return khenThuong; }
    public void setKhenThuong(String khenThuong) { this.khenThuong = khenThuong; }
}
