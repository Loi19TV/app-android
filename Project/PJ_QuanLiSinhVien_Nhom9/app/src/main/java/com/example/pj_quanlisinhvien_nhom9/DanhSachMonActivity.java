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
import androidx.appcompat.app.AppCompatActivity;

public class DanhSachMonActivity extends AppCompatActivity {
    ListView lvDanhSachMon;
    Adapter_Mon adapter_DSM;
    EditText edtNhapTenMon;
    EditText edtNhapSoTinChi;
    Button btnThem;
    Button btnXoa;
    Button btnSua;
    Button btnSapXep;
    TextView tvTongSoMon;
    static int demSLMon = 0;
    int index = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_mon);
        setControl();
        setEvent();
    }

    private void setControl() {
        lvDanhSachMon = findViewById(R.id.lvDanhSachMon);
        edtNhapTenMon = findViewById(R.id.edtNhapTenMon);
        edtNhapSoTinChi = findViewById(R.id.edtNhapSoTinChi);
        btnThem = findViewById(R.id.btnThem);
        btnXoa = findViewById(R.id.btnXoa);
        btnSua = findViewById(R.id.btnSua);
        btnSapXep = findViewById(R.id.btnSapXep);
        tvTongSoMon = findViewById(R.id.tvTongSoMon);
    }
    private void setEvent() {
        //set back drawer
        // Sử dụng layout tùy chỉnh làm tiêu đề cho ActionBar
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_menu_drawer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DBKhoaMon dbKhoaMon = new DBKhoaMon(this);
        Intent intent = getIntent();
        String tenKhoa = intent.getStringExtra("tenKhoa");
        demSLMon = dbKhoaMon.taoBangMon(tenKhoa).size();

        //set adapter, listview
        adapter_DSM = new Adapter_Mon(DanhSachMonActivity.this, R.layout.layout_custom_mon, dbKhoaMon.DocDanhSachMon());
        lvDanhSachMon.setAdapter(adapter_DSM);

        //Tổng số môn
        tvTongSoMon.setText("Tổng số môn: " + demSLMon);

        //Set onclick khi click vào item bất kỳ trong listview
        lvDanhSachMon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                index = position;
            }
        });

        //Click Button Thêm để thêm
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edtNhapTenMon.getText().toString()) || TextUtils.isEmpty(edtNhapSoTinChi.getText().toString()))
                {
                    Toast.makeText(DanhSachMonActivity.this, "Vui lòng nhập đầy đủ tên môn và số tín chỉ!", Toast.LENGTH_LONG).show();
                }
                else
                {
                    for (MonHoc mh : dbKhoaMon.DocDanhSachMon())
                    {
                        if (mh.getTenMon().equals(edtNhapTenMon.getText().toString()))
                        {
                            Toast.makeText(DanhSachMonActivity.this, "Tên môn đã tồn tại!", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                    MonHoc mh = new MonHoc(edtNhapTenMon.getText().toString(), edtNhapSoTinChi.getText().toString());
                    dbKhoaMon.ThemMon(mh);
                    adapter_DSM.clear();
                    adapter_DSM.addAll(dbKhoaMon.DocDanhSachMon());
                    adapter_DSM.notifyDataSetChanged();
                    demSLMon++;
                    tvTongSoMon.setText("Tổng số môn: " + demSLMon);
                }
            }
        });

        //Click Button Xóa để xóa môn
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index == -1)
                {
                    Toast.makeText(DanhSachMonActivity.this, "Vui lòng chọn môn để xóa!", Toast.LENGTH_LONG).show();
                }
                else
                {
                    dbKhoaMon.XoaMon(dbKhoaMon.DocDanhSachMon().get(index));
                    adapter_DSM.clear();
                    adapter_DSM.addAll(dbKhoaMon.DocDanhSachMon());
                    adapter_DSM.notifyDataSetChanged();
                    index = -1;
                    demSLMon--;
                    tvTongSoMon.setText("Tổng số môn: " + demSLMon);
                }
            }
        });

        //Click Button Sửa để sửa môn
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edtNhapTenMon.getText().toString()) || TextUtils.isEmpty(edtNhapSoTinChi.getText().toString()))
                {
                    Toast.makeText(DanhSachMonActivity.this, "Vui lòng nhập đầy đủ tên môn và số tín chỉ để sửa!", Toast.LENGTH_LONG).show();
                }
                else
                {
                    if (index == -1)
                    {
                        Toast.makeText(DanhSachMonActivity.this, "Vui lòng chọn môn để sửa!", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        MonHoc newMonHoc = new MonHoc(edtNhapTenMon.getText().toString(), edtNhapSoTinChi.getText().toString());
                        dbKhoaMon.SuaMon(newMonHoc, dbKhoaMon.DocDanhSachMon().get(index));
                        adapter_DSM.clear();
                        adapter_DSM.addAll(dbKhoaMon.DocDanhSachMon());
                        adapter_DSM.notifyDataSetChanged();
                        index = -1;
                    }
                }
            }
        });

        //Click button sắp xếp để sắp xếp môn theo tên A - Z
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(DanhSachMonActivity.this, ChoseDepartmentActivity.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

}