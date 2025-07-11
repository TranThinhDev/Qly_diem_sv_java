package model;

public class KetQua {
    private String maSV, maHP, hocKy, ghiChu;
    private int lanHoc;
    private double diemCC, diemKT, diemThiLan1, diemThiLan2;

    public KetQua() {}

    public KetQua(String maSV, String maHP, String hocKy, String ghiChu, int lanHoc, double diemCC, double diemKT, double diemThiLan1, double diemThiLan2) {
        this.maSV = maSV;
        this.maHP = maHP;
        this.hocKy = hocKy;
        this.ghiChu = ghiChu;
        this.lanHoc = lanHoc;
        this.diemCC = diemCC;
        this.diemKT = diemKT;
        this.diemThiLan1 = diemThiLan1;
        this.diemThiLan2 = diemThiLan2;
    }


    // Getters + Setters
    // ...

    public String getMaSV() {
        return maSV;
    }

    public void setMaSV(String maSV) {
        this.maSV = maSV;
    }

    public String getMaHP() {
        return maHP;
    }

    public void setMaHP(String maHP) {
        this.maHP = maHP;
    }

    public String getHocKy() {
        return hocKy;
    }

    public void setHocKy(String hocKy) {
        this.hocKy = hocKy;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public int getLanHoc() {
        return lanHoc;
    }

    public void setLanHoc(int lanHoc) {
        this.lanHoc = lanHoc;
    }

    public double getDiemCC() {
        return diemCC;
    }

    public void setDiemCC(double diemCC) {
        this.diemCC = diemCC;
    }

    public double getDiemKT() {
        return diemKT;
    }

    public void setDiemKT(double diemKT) {
        this.diemKT = diemKT;
    }

    public double getDiemThiLan1() {
        return diemThiLan1;
    }

    public void setDiemThiLan1(double diemThiLan1) {
        this.diemThiLan1 = diemThiLan1;
    }

    public double getDiemThiLan2() {
        return diemThiLan2;
    }

    public void setDiemThiLan2(double diemThiLan2) {
        this.diemThiLan2 = diemThiLan2;
    }
}
