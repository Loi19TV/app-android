package com.example.pj_quanlisinhvien_nhom9;

import java.util.ArrayList;
import java.util.List;

public class SinhVien {
    String maSV, hoTenSV, lop;
    List<MonHoc> danhSachMonHoc = new ArrayList<>();
    public static String hocKy = "HK1";

    public String XuatSinhVien() {
        return "MSSV: " + maSV + "\n" + "Họ tên: " + hoTenSV + "\n" + "Lớp: " + lop;
    }

    public SinhVien(String maSV, String hoTenSV, String lop, List<MonHoc> danhSachMonHoc) {
        this.maSV = maSV;
        this.hoTenSV = hoTenSV;
        this.lop = lop;
        this.danhSachMonHoc = danhSachMonHoc;
    }

    public SinhVien() {
    }

    public String getMaSV() {
        return maSV;
    }

    public void setMaSV(String maSV) {
        this.maSV = maSV;
    }

    public String getHoTenSV() {
        return hoTenSV;
    }

    public void setHoTenSV(String hoTenSV) {
        this.hoTenSV = hoTenSV;
    }

    public String getLop() {
        return lop;
    }

    public void setLop(String lop) {
        this.lop = lop;
    }

    public List<MonHoc> getMonHoc() {
        return danhSachMonHoc;
    }

    public void setMonHoc(List<MonHoc> danhSachMonHoc) {
        this.danhSachMonHoc = danhSachMonHoc;
    }
}
