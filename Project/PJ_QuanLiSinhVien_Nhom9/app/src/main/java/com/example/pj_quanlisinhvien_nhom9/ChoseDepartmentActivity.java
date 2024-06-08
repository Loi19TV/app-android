package com.example.pj_quanlisinhvien_nhom9;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class ChoseDepartmentActivity extends AppCompatActivity {
    ListView lvDanhSachKhoa;
    Adapter_Khoa adapter_DSK;
    EditText edtNhapTenKhoa;
    Button btnThem;
    Button btnXoa;
    Button btnSua;
    Button btnSapXep;
    TextView tvTongSoKhoa;
    static int demSLKhoa = 0;
    int index = -1;
    DrawerLayout drawerLayout;
    NavigationView navView;
    ActionBarDrawerToggle drawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chose_department);
        setControl();
        setEvent();
    }
    private void setControl() {
        lvDanhSachKhoa = findViewById(R.id.lvDanhSachKhoa);
        edtNhapTenKhoa = findViewById(R.id.edtNhapTenKhoa);
        btnThem = findViewById(R.id.btnThem);
        btnXoa = findViewById(R.id.btnXoa);
        btnSua = findViewById(R.id.btnSua);
        btnSapXep = findViewById(R.id.btnSapXep);
        tvTongSoKhoa = findViewById(R.id.tvTongSoKhoa);
        drawerLayout = findViewById(R.id.drawerLayout);
        navView = findViewById(R.id.navView);
    }

    private void setEvent() {
        //<< set drawer
        // Sử dụng layout tùy chỉnh làm tiêu đề cho ActionBar
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_menu_drawer);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.mnStartScreen){
                    Intent intent = new Intent(ChoseDepartmentActivity.this, StartActivity.class);
                    startActivity(intent);
                    Toast.makeText(ChoseDepartmentActivity.this, "Đã chọn màn hình chính", Toast.LENGTH_LONG).show();
                }
                if (item.getItemId() == R.id.mnKhoa){
                    Toast.makeText(ChoseDepartmentActivity.this, "Đã chọn màn hình khoa", Toast.LENGTH_LONG).show();
                }
                if (item.getItemId() == R.id.mnSinhVien){
                    Toast.makeText(ChoseDepartmentActivity.this, "Đã chọn màn hình vinh viên", Toast.LENGTH_LONG).show();
                }
                if (item.getItemId() == R.id.mnDiem){
                    Toast.makeText(ChoseDepartmentActivity.this, "Đã chọn màn hình điểm", Toast.LENGTH_LONG).show();
                }
                if (item.getItemId() == R.id.mnCaiDat){
                    Toast.makeText(ChoseDepartmentActivity.this, "Đã chọn màn hình cài đặt", Toast.LENGTH_LONG).show();
                }
                drawerLayout.closeDrawers();
                return false;
            }
        });
        //>>

        DBKhoaMon dbKhoaMon = new DBKhoaMon(this);
        demSLKhoa = dbKhoaMon.DocDanhSachTenKhoa().size();
        //set adapter, listview
        adapter_DSK = new Adapter_Khoa(this, R.layout.layout_custom_khoa, dbKhoaMon.DocDanhSachTenKhoa());
        lvDanhSachKhoa.setAdapter(adapter_DSK);

        //Tổng số khoa
        tvTongSoKhoa.setText("Tổng số khoa: " + demSLKhoa);

        //set onclick short
        lvDanhSachKhoa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                index = position;
            }
        });

        //Set onclick khi click vào item bất kỳ trong listview
        lvDanhSachKhoa.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ChoseDepartmentActivity.this, DanhSachMonActivity.class);
                intent.putExtra("tenKhoa", dbKhoaMon.DocDanhSachTenKhoa().get(position));
                startActivity(intent);
                return false;
            }
        });

        //Click Button Thêm để thêm khoa
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edtNhapTenKhoa.getText().toString()))
                {
                    Toast.makeText(ChoseDepartmentActivity.this, "Vui lòng nhập tên khoa!", Toast.LENGTH_LONG).show();
                }
                else
                {
                    for (String tenKhoa : dbKhoaMon.DocDanhSachTenKhoa())
                    {
                        if (tenKhoa.equals(edtNhapTenKhoa.getText().toString()))
                        {
                            Toast.makeText(ChoseDepartmentActivity.this, "Tên khoa đã tồn tại!", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                    dbKhoaMon.ThemKhoa(edtNhapTenKhoa.getText().toString());
                    adapter_DSK.clear();
                    adapter_DSK.addAll(dbKhoaMon.DocDanhSachTenKhoa()); //update lại List trong adapter
                    adapter_DSK.notifyDataSetChanged();
                    demSLKhoa++;
                    tvTongSoKhoa.setText("Tổng số khoa: " + demSLKhoa);
                }
            }
        });

        //Click Button Xóa để xóa khoa
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index == -1)
                {
                    Toast.makeText(ChoseDepartmentActivity.this, "Vui lòng chọn khoa để xóa!", Toast.LENGTH_LONG).show();
                }
                else
                {
                    dbKhoaMon.XoaKhoa(dbKhoaMon.DocDanhSachTenKhoa().get(index));
                    adapter_DSK.clear();
                    adapter_DSK.addAll(dbKhoaMon.DocDanhSachTenKhoa());
                    adapter_DSK.notifyDataSetChanged();
                    index = -1;
                    demSLKhoa--;
                    tvTongSoKhoa.setText("Tổng số khoa: " + demSLKhoa);
                }
            }
        });

        //Click Button Sửa để sửa khoa
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edtNhapTenKhoa.getText().toString()))
                {
                    Toast.makeText(ChoseDepartmentActivity.this, "Vui lòng nhập tên khoa!", Toast.LENGTH_LONG).show();
                }
                else
                {
                    if (index == -1)
                    {
                        Toast.makeText(ChoseDepartmentActivity.this, "Vui lòng chọn khoa để sửa!", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        dbKhoaMon.SuaKhoa(edtNhapTenKhoa.getText().toString(), dbKhoaMon.DocDanhSachTenKhoa().get(index).toString());
                        adapter_DSK.clear();
                        adapter_DSK.addAll(dbKhoaMon.DocDanhSachTenKhoa());
                        adapter_DSK.notifyDataSetChanged();
                        index = -1;
                    }
                }
            }
        });

        //Click button sắp xếp để sắp xếp khoa theo tên A - Z
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}