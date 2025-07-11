package model;

public class LopHP {
    private String maHP;
    private String tenHP;
    private String maLop;
    private String maMon;
    private String maGV;

    public LopHP() {}

    public LopHP(String maHP, String tenHP, String maLop, String maMon, String maGV) {
        this.maHP = maHP;
        this.tenHP = tenHP;
        this.maLop = maLop;
        this.maMon = maMon;
        this.maGV = maGV;
    }

    public String getMaHP() { return maHP; }
    public void setMaHP(String maHP) { this.maHP = maHP; }

    public String getTenHP() { return tenHP; }
    public void setTenHP(String tenHP) { this.tenHP = tenHP; }

    public String getMaLop() { return maLop; }
    public void setMaLop(String maLop) { this.maLop = maLop; }

    public String getMaMon() { return maMon; }
    public void setMaMon(String maMon) { this.maMon = maMon; }

    public String getMaGV() { return maGV; }
    public void setMaGV(String maGV) { this.maGV = maGV; }
}
