/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author dell
 */
public class TaiKhoan {
    private String tenDN;
    private String matKhau;
    private String hoTen;
    private String quyen;

    public TaiKhoan(String tenDN, String matKhau, String hoTen, String quyen) {
        this.tenDN = tenDN;
        this.matKhau = matKhau;
        this.hoTen = hoTen;
        this.quyen = quyen;
    }

    public void setTenDN(String tenDN) {
        this.tenDN = tenDN;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public void setQuyen(String quyen) {
        this.quyen = quyen;
    }

    
    // Getters và setters (nếu cần)
    public String getTenDN() { return tenDN; }
    public String getMatKhau() { return matKhau; }
    public String getHoTen() { return hoTen; }
    public String getQuyen() { return quyen; }
}
