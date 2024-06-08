package com.example.pj_quanlisinhvien_nhom9;

public class MonHoc {
    private String tenMon, soTinChi;

    @Override
    public String toString() {
        return "Môn: " + this.tenMon + "\nSố tín chỉ: " + this.soTinChi;
    }

    public MonHoc(String tenMon, String soTinChi) {
        this.tenMon = tenMon;
        this.soTinChi = soTinChi;
    }

    public MonHoc() {
    }

    public String getTenMon() {
        return tenMon;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }

    public String getSoTinChi() {
        return soTinChi;
    }

    public void setSoTinChi(String soTinChi) {
        this.soTinChi = soTinChi;
    }
}
