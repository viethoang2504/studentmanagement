package com.example.studentmanagement;

import java.io.Serializable;

public class Student  implements Serializable {
    private String name;
    private String mssv;
    private String email;
    private String phone;

    public Student(String name, String mssv, String email, String phone) {
        this.name = name;
        this.mssv = mssv;
        this.email = email;
        this.phone = phone;
    }

    // Getters và setters
    // Getter và setter đầy đủ
    public String getName() { return name; }
    public String getMssv() { return mssv; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }

    public void setName(String name) { this.name = name; }
    public void setMssv(String mssv) { this.mssv = mssv; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setEmail(String email) { this.email = email; }
}

