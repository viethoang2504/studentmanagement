package com.example.studentmanagement;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.PopupMenu;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<Student> studentList;
    StudentAdapter adapter;
    DatabaseHelper dbHelper;
    static final int ADD_STUDENT_REQUEST = 1;
    static final int UPDATE_STUDENT_REQUEST = 2;
    int selectedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Thiết lập Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Student Management");
        } else {
            Log.e("MainActivity", "Toolbar not found in layout");
        }

        // Khởi tạo ListView và các thành phần khác
        listView = findViewById(R.id.listViewStudents);
        dbHelper = new DatabaseHelper(this);
        studentList = dbHelper.getAllStudents(); // Lấy danh sách từ SQLite

        // Nếu danh sách rỗng, thêm dữ liệu mẫu (chỉ chạy lần đầu)
        if (studentList.isEmpty()) {
            addSampleData();
            studentList = dbHelper.getAllStudents(); // Cập nhật lại danh sách
        }

        adapter = new StudentAdapter(this, studentList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            selectedPosition = position;
            showPopupMenu(view, position);
        });
    }

    private void addSampleData() {
        // Dữ liệu mẫu
        dbHelper.addStudent(new Student("Nguyễn Văn A", "SV001", "a.nguyen@example.com", "0912345678"));
        dbHelper.addStudent(new Student("Trần Thị B", "SV002", "b.tran@example.com", "0912345679"));
        dbHelper.addStudent(new Student("Lê Văn C", "SV003", "c.le@example.com", "0912345680"));
        dbHelper.addStudent(new Student("Phạm Thị D", "SV004", "d.pham@example.com", "0912345681"));
        dbHelper.addStudent(new Student("Hoàng Văn E", "SV005", "e.hoang@example.com", "0912345682"));
        dbHelper.addStudent(new Student("Đỗ Thị F", "SV006", "f.do@example.com", "0912345683"));
        dbHelper.addStudent(new Student("Vũ Văn G", "SV007", "g.vu@example.com", "0912345684"));
        dbHelper.addStudent(new Student("Bùi Thị H", "SV008", "h.bui@example.com", "0912345685"));
        dbHelper.addStudent(new Student("Đặng Văn I", "SV009", "i.dang@example.com", "0912345686"));
        dbHelper.addStudent(new Student("Lý Thị K", "SV010", "k.ly@example.com", "0912345687"));
    }

    private void showPopupMenu(View view, int position) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.getMenuInflater().inflate(R.menu.student_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(item -> {
            Student student = studentList.get(position);
            int itemId = item.getItemId();

            if (itemId == R.id.menu_update) {
                Intent intent = new Intent(this, UpdateStudentActivity.class);
                intent.putExtra("student", student);
                startActivityForResult(intent, UPDATE_STUDENT_REQUEST);
                return true;
            } else if (itemId == R.id.menu_delete) {
                confirmDelete(position);
                return true;
            } else if (itemId == R.id.menu_call) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + student.getPhone()));
                startActivity(callIntent);
                return true;
            } else if (itemId == R.id.menu_email) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:" + student.getEmail()));
                startActivity(emailIntent);
                return true;
            } else {
                return false;
            }
        });

        popup.show();
    }

    private void confirmDelete(int position) {
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận")
                .setMessage("Bạn có chắc muốn xóa sinh viên này?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    dbHelper.deleteStudent(studentList.get(position).getMssv()); // Xóa từ SQLite
                    studentList.remove(position); // Xóa từ ArrayList
                    adapter.notifyDataSetChanged();
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        Log.d("MainActivity", "Menu inflated");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_add_student) {
            Intent intent = new Intent(this, AddStudentActivity.class);
            startActivityForResult(intent, ADD_STUDENT_REQUEST);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            Student student = (Student) data.getSerializableExtra("student");
            Log.d("MainActivity", "Received student: " + (student != null ? student.getName() : "null"));

            if (requestCode == ADD_STUDENT_REQUEST) {
                dbHelper.addStudent(student); // Thêm vào SQLite
                studentList.add(student); // Thêm vào ArrayList
            } else if (requestCode == UPDATE_STUDENT_REQUEST && selectedPosition != -1) {
                Log.d("MainActivity", "Updating student at position: " + selectedPosition);
                dbHelper.updateStudent(student); // Cập nhật trong SQLite
                studentList.set(selectedPosition, student); // Cập nhật trong ArrayList
            } else {
                Log.d("MainActivity", "Invalid request or position: " + selectedPosition);
            }

            adapter.notifyDataSetChanged();
        } else {
            Log.d("MainActivity", "Result not OK or data is null");
        }
    }
}