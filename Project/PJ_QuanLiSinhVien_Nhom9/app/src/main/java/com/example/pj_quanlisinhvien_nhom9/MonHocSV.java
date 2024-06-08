package com.example.pj_quanlisinhvien_nhom9;

public class MonHocSV {
    String tenMonHoc;
    int diemMonHoc;

    public String XuatMonHoc() {
        return this.tenMonHoc + ": " + this.diemMonHoc + " điểm";
    }

    public MonHocSV() {
        this.tenMonHoc = "";
        this.diemMonHoc = -1;
    }
    public MonHocSV(String tenMonHoc, int diemMonHoc) {
        this.tenMonHoc = tenMonHoc;
        this.diemMonHoc = diemMonHoc;
    }

    public String getTenMonHoc() {
        return tenMonHoc;
    }

    public void setTenMonHoc(String tenMonHoc) {
        this.tenMonHoc = tenMonHoc;
    }

    public int getDiemMonHoc() {
        return diemMonHoc;
    }

    public void setDiemMonHoc(int diemMonHoc) {
        this.diemMonHoc = diemMonHoc;
    }
}
