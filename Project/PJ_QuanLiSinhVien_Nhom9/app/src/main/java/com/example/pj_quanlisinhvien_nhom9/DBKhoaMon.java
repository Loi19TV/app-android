package com.example.pj_quanlisinhvien_nhom9;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBKhoaMon extends SQLiteOpenHelper {
    private String tenKhoa;

    public DBKhoaMon(@Nullable Context context) {
        super(context,"dbKhoaAndMon", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "Create table tbDanhSachKhoa (tenKhoa TEXT)";
        db.execSQL(sql);
    }

    //Môn
    public List<MonHoc> taoBangMon(String tenKhoaChuaXoaKhoangTrang) //tạo bảng môn của 1 khoa dự vào tên khoa
    {
        String tenKhoa = tenKhoaChuaXoaKhoangTrang.replace(" ", "").trim();
        this.tenKhoa = tenKhoa;
        try {

            String sql = "CREATE TABLE tbMon" + tenKhoa + " (tenMon TEXT, soTinChi TEXT)";
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL(sql);
            List<MonHoc> data = new ArrayList<>();
            return data;
        } catch (SQLiteException e) {
            return DocDanhSachMon();
        }
    }

    public List<MonHoc> DocDanhSachMon() {
        List<MonHoc> data = new ArrayList<>();
        String sql = "Select * from tbMon" + this.tenKhoa;
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        if (cursor.moveToFirst())
        {
            do{
                MonHoc mh = new MonHoc(cursor.getString(0).toString(), cursor.getString(1).toString());
                data.add(mh);
            }
            while (cursor.moveToNext());
        }
        return data;
    }

    public void ThemMon(MonHoc mh)
    {
        String sql = "INSERT INTO tbMon" + this.tenKhoa + " VALUES (?, ?)";
        SQLiteDatabase sqLiteDatabase = getWritableDatabase(); //ghi du lieu
        sqLiteDatabase.execSQL(sql, new String[] {mh.getTenMon(), mh.getSoTinChi()});
    }

    public void XoaMon(MonHoc mh) {
        String sql = "Delete from tbMon" + this.tenKhoa + " where tenMon = ?";
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL(sql, new String[] {mh.getTenMon()});
    }

    public void SuaMon(MonHoc monMoi, MonHoc monCu) {
        String sql = "Update tbMon"+ this.tenKhoa +" set tenMon = ?, soTinChi = ? where tenMon = ?";
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL(sql, new String[] {monMoi.getTenMon(), monMoi.getSoTinChi(), monCu.getTenMon()});
    }

    //Khoa
    public List<String> DocDanhSachTenKhoa() {
        List<String> data = new ArrayList<>();
        String sql = "Select tenKhoa from tbDanhSachKhoa";
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        if (cursor.moveToFirst())
        {
            do{
                data.add(cursor.getString(0).toString());
            }
            while (cursor.moveToNext());
        }
        return data;
    }

    public void ThemKhoa(String tenKhoa)
    {
        String sql = "INSERT INTO tbDanhSachKhoa VALUES (?)";
        SQLiteDatabase sqLiteDatabase = getWritableDatabase(); //ghi du lieu
        sqLiteDatabase.execSQL(sql, new String[] {tenKhoa});
    }

    public void XoaKhoa(String tenKhoa) { //xóa phần tử trong bảng dựa vào tên khoa
        String sql = "Delete from tbDanhSachKhoa where tenKhoa = ?";
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL(sql, new String[] {tenKhoa});
        XoaBangMon(tenKhoa);
    }

    public void XoaBangMon(String tenKhoaCuaBang) //Xóa 1 bảng môn bằng tên bảng đó
    {
        String tenKhoaCuaBangDaXoaSpace = tenKhoaCuaBang.replace(" ", "").trim();
        String sql = "DROP TABLE IF EXISTS tbMon" + tenKhoaCuaBangDaXoaSpace;
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL(sql);
    }

    public void SuaKhoa(String tenKhoaMoi, String tenKhoaCu) { //Sửa tên phần tử trong bảng dựa vào tên khoa mới và tên khoa cũ (tên khoa muốn sửa)
        String sql = "Update tbDanhSachKhoa set tenKhoa = ? where tenKhoa = ?";
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL(sql, new String[] {tenKhoaMoi, tenKhoaCu});
        ChuyenDuLieuBang(tenKhoaCu, tenKhoaMoi);
    }

    public void ChuyenDuLieuBang(String tenBangCu, String tenBangMoi) //Chuyển dữ liệu 2 bảng và xóa bảng cũ
    {
        String tenBangCuDaXoaSpace = tenBangCu.replace(" ", "").trim();
        String tenBangMoiDaXoaSpace = tenBangMoi.replace(" ", "").trim();

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        String sql = "CREATE TABLE tbMon" + tenBangMoiDaXoaSpace + " (tenMon TEXT, soTinChi TEXT)";
        sqLiteDatabase.execSQL(sql);

        String sqlCopyData = "INSERT INTO tbMon" + tenBangMoiDaXoaSpace + " SELECT * FROM tbMon" + tenBangCuDaXoaSpace;
        sqLiteDatabase.execSQL(sqlCopyData);

        // 3. Xóa bảng cũ
        String sqlDropOldTable = "DROP TABLE IF EXISTS tbMon" + tenBangCuDaXoaSpace;
        sqLiteDatabase.execSQL(sqlDropOldTable);

        sqLiteDatabase.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
