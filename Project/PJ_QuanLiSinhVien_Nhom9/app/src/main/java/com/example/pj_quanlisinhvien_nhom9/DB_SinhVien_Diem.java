package com.example.pj_quanlisinhvien_nhom9;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DB_SinhVien_Diem extends SQLiteOpenHelper {
    private String maSV;
    private  String hocKy;
    public DB_SinhVien_Diem(@Nullable Context context) {
        super(context, "dbSinhVien_Diem", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "Create table tbDanhSachSinhVien (maSV TEXT, hoTenSV TEXT, lop TEXT)";
        db.execSQL(sql);
    }

    public List<SinhVien> DocDanhSachSinhVien() {
        List<SinhVien> data = new ArrayList<>();
        String sql = "Select * from tbDanhSachSinhVien";
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        if (cursor.moveToFirst())
        {
            do{
                SinhVien sv = new SinhVien();
                sv.setMaSV(cursor.getString(0).toString());
                sv.setHoTenSV(cursor.getString(1).toString());
                sv.setLop(cursor.getString(2).toString());
                data.add(sv);
            }
            while (cursor.moveToNext());
        }
        return data;
    }

    public void ThemSinhVien(SinhVien sv)
    {
        String sql = "Insert into tbDanhSachSinhVien values(?, ?, ?)";
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL(sql, new String[] {sv.getMaSV(), sv.getHoTenSV(), sv.getLop()});
    }

    public void XoaSinhVienBangMa(String maSV)
    {
        String sql = "Delete from tbDanhSachSinhVien where maSV = ?";
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL(sql, new String[] {maSV});
    }

    public void SuaSinhVien(SinhVien svMoi, SinhVien svCu)
    {
        String sql = "Update tbDanhSachSinhVien set maSV = ?, hoTenSV = ?, lop = ? where maSV = ?";
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL(sql, new String[] {svMoi.getMaSV(), svMoi.getHoTenSV(), svMoi.getLop(), svCu.getMaSV()});
    }

    // Điểm
    public List<MonHocSV> taoBangDiemChoSinhVien(String maSV, String hocKy) //tạo bảng bang ma sv
    {
        this.maSV = maSV;
        this.hocKy = hocKy;
        try {

            String sql = "CREATE TABLE tbDiem" + this.maSV + this.hocKy + " (tenMon TEXT, diem INTEGER)";
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL(sql);
            List<MonHocSV> data = new ArrayList<>();
            return data;
        } catch (SQLiteException e) {
            return DocDanhSachDiem();
        }
    }

    public List<MonHocSV> DocDanhSachDiem() {
        List<MonHocSV> data = new ArrayList<>();
        String sql = "Select * from tbDiem" + this.maSV + this.hocKy;
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        if (cursor.moveToFirst())
        {
            do{

                MonHocSV mhSV = new MonHocSV();
                mhSV.setTenMonHoc(cursor.getString(0).toString());
                mhSV.setDiemMonHoc(Integer.parseInt(cursor.getString(1)));
                data.add(mhSV);
            }
            while (cursor.moveToNext());
        }
        return data;
    }

    public void ThemMonDiem(MonHocSV mhSV)
    {
        String sql = "INSERT INTO tbDiem" + this.maSV + this.hocKy + " VALUES (?, ?)";
        SQLiteDatabase sqLiteDatabase = getWritableDatabase(); //ghi du lieu
        sqLiteDatabase.execSQL(sql, new String[] {mhSV.getTenMonHoc(), String.valueOf(mhSV.getDiemMonHoc())});
    }

    public void XoaMonDiem(String tenMon) {
        String sql = "Delete from tbDiem" + this.maSV + this.hocKy + " where tenMon = ?";
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL(sql, new String[] {tenMon});
    }

    public void SuaMonDiem(MonHocSV monDiemMoi, String tenMonCu) {
        String sql = "Update tbDiem" + this.maSV + this.hocKy + " set tenMon = ?, diem = ? where tenMon = ?";
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL(sql, new String[] {monDiemMoi.getTenMonHoc(), String.valueOf(monDiemMoi.getDiemMonHoc()), tenMonCu});
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
