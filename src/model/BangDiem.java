package model;

public class BangDiem {
    private String maSV;
    private int tongSoTC;
    private double diemHe10;
    private double diemHe4;

    public BangDiem() {}

    public BangDiem(String maSV, int tongSoTC, double diemHe10, double diemHe4) {
        this.maSV = maSV;
        this.tongSoTC = tongSoTC;
        this.diemHe10 = diemHe10;
        this.diemHe4 = diemHe4;
    }

    // Getters và Setters
    public String getMaSV() {
        return maSV;
    }

    public void setMaSV(String maSV) {
        this.maSV = maSV;
    }

    public int getTongSoTC() {
        return tongSoTC;
    }

    public void setTongSoTC(int tongSoTC) {
        this.tongSoTC = tongSoTC;
    }

    public double getDiemHe10() {
        return diemHe10;
    }

    public void setDiemHe10(double diemHe10) {
        this.diemHe10 = diemHe10;
    }

    public double getDiemHe4() {
        return diemHe4;
    }

    public void setDiemHe4(double diemHe4) {
        this.diemHe4 = diemHe4;
    }
}
