package com.example.studentmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddStudentActivity extends AppCompatActivity {
    EditText editName, editMSSV, editEmail, editPhone;
    Button buttonSave;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update_student);

        // Khởi tạo các thành phần giao diện
        editName = findViewById(R.id.editName);
        editMSSV = findViewById(R.id.editMSSV);
        editEmail = findViewById(R.id.editEmail);
        editPhone = findViewById(R.id.editPhone);
        buttonSave = findViewById(R.id.buttonSave);

        // Khởi tạo DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        buttonSave.setOnClickListener(v -> {
            String name = editName.getText().toString().trim();
            String mssv = editMSSV.getText().toString().trim();
            String email = editEmail.getText().toString().trim();
            String phone = editPhone.getText().toString().trim();

            // Kiểm tra dữ liệu đầu vào
            if (name.isEmpty() || mssv.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập Họ tên và MSSV", Toast.LENGTH_SHORT).show();
                return;
            }

            // Kiểm tra MSSV đã tồn tại
            if (isMssvExists(mssv)) {
                Toast.makeText(this, "MSSV đã tồn tại, vui lòng nhập MSSV khác", Toast.LENGTH_SHORT).show();
                return;
            }

            // Tạo đối tượng Student và trả về kết quả
            Student student = new Student(name, mssv, email, phone);
            Intent intent = new Intent();
            intent.putExtra("student", student);
            setResult(RESULT_OK, intent);
            finish();
        });
    }

    // Hàm kiểm tra MSSV đã tồn tại trong cơ sở dữ liệu
    private boolean isMssvExists(String mssv) {
        for (Student student : dbHelper.getAllStudents()) {
            if (student.getMssv().equals(mssv)) {
                return true;
            }
        }
        return false;
    }
}