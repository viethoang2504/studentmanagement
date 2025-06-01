package com.example.studentmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateStudentActivity extends AppCompatActivity {
    EditText editName, editMSSV, editEmail, editPhone;
    Button buttonSave;
    Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update_student);

        editName = findViewById(R.id.editName);
        editMSSV = findViewById(R.id.editMSSV);
        editEmail = findViewById(R.id.editEmail);
        editPhone = findViewById(R.id.editPhone);
        buttonSave = findViewById(R.id.buttonSave);

        student = (Student) getIntent().getSerializableExtra("student");
        if (student != null) {
            editName.setText(student.getName());
            editMSSV.setText(student.getMssv());
            editEmail.setText(student.getEmail());
            editPhone.setText(student.getPhone());
        }

        buttonSave.setOnClickListener(v -> {
            String name = editName.getText().toString().trim();
            String mssv = editMSSV.getText().toString().trim();
            String email = editEmail.getText().toString().trim();
            String phone = editPhone.getText().toString().trim();

            if (name.isEmpty() || mssv.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập Họ tên và MSSV", Toast.LENGTH_SHORT).show();
                return;
            }

            Student updatedStudent = new Student(name, mssv, email, phone);
            Intent intent = new Intent();
            intent.putExtra("student", updatedStudent);
            setResult(RESULT_OK, intent);
            finish();
        });
    }
}
