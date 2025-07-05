package model;

public class Nganh {
    private String maNganh;
    private String tenNganh;
    private String tenKhoa;

    public Nganh() {}

    public Nganh(String maNganh, String tenNganh, String tenKhoa) {
        this.maNganh = maNganh;
        this.tenNganh = tenNganh;
        this.tenKhoa = tenKhoa;
    }

    // Getters and Setters
    public String getMaNganh() { return maNganh; }
    public void setMaNganh(String maNganh) { this.maNganh = maNganh; }

    public String getTenNganh() { return tenNganh; }
    public void setTenNganh(String tenNganh) { this.tenNganh = tenNganh; }

    public String getTenKhoa() { return tenKhoa; }
    public void setTenKhoa(String tenKhoa) { this.tenKhoa = tenKhoa; }
}
