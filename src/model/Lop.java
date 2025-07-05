/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class Lop {
    private String maLop;
    private String tenLop;
    private String maNganh;
    private String maGV;

    public Lop() {}

    public Lop(String maLop, String tenLop, String maNganh, String maGV) {
        this.maLop = maLop;
        this.tenLop = tenLop;
        this.maNganh = maNganh;
        this.maGV = maGV;
    }

    // Getters and setters
    public String getMaLop() { return maLop; }
    public void setMaLop(String maLop) { this.maLop = maLop; }

    public String getTenLop() { return tenLop; }
    public void setTenLop(String tenLop) { this.tenLop = tenLop; }

    public String getMaNganh() { return maNganh; }
    public void setMaNganh(String maNganh) { this.maNganh = maNganh; }

    public String getMaGV() { return maGV; }
    public void setMaGV(String maGV) { this.maGV = maGV; }
}

