package model;

public class MonHoc {
    private String maMon;
    private String tenMon;
    private int soTC;

    public MonHoc() {}

    public MonHoc(String maMon, String tenMon, int soTC) {
        this.maMon = maMon;
        this.tenMon = tenMon;
        this.soTC = soTC;
    }

    public String getMaMon() { return maMon; }
    public void setMaMon(String maMon) { this.maMon = maMon; }

    public String getTenMon() { return tenMon; }
    public void setTenMon(String tenMon) { this.tenMon = tenMon; }

    public int getSoTC() { return soTC; }
    public void setSoTC(int soTC) { this.soTC = soTC; }
}
